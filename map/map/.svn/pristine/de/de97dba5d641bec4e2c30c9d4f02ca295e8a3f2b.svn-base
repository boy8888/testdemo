package com.hummingbird.maaccount.controller;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.service.IAuthenticationService;
import com.hummingbird.commonbiz.vo.UserToken;
import com.hummingbird.maaccount.entity.SmsCode;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.mapper.UserSmsCodeMapper;
import com.hummingbird.maaccount.service.ITokenService;
import com.hummingbird.maaccount.service.UserAttrService;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.EncryptUtil;
import com.hummingbird.maaccount.util.LoginType;
import com.hummingbird.maaccount.util.NumRandom;
import com.hummingbird.maaccount.util.OrgType;
import com.hummingbird.maaccount.util.synuserinfo.SynUserCenterData;
import com.hummingbird.maaccount.vo.BindMobileVO;
import com.hummingbird.maaccount.vo.CheDuiRegistVO;
import com.hummingbird.maaccount.vo.LoginByTypeVO;
import com.hummingbird.maaccount.vo.ResetPWByOldPWVO;

@Controller
@RequestMapping("/che_dui")
public class CheDuiController extends BaseController{
    
    @Autowired(required = true)
    private UserSmsCodeMapper smscodemapper;
    
    @Autowired(required = true)
    private IAuthenticationService authService;
    
    @Autowired(required = true)
    private UserService userSrv;
    
    @Autowired(required = true)
    private UserMapper userDao;
    
    @Autowired
    protected UserAttrService  userAttrSrv;
    
    @Autowired
    protected SmsSendService  smsSender;
    
    @Autowired(required = true)
    private ITokenService tokensrv;

    
    
    /**
     * 车队用户注册
     * @param cheDuiRegistVO
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Object cheDuiRegist(@RequestBody CheDuiRegistVO cheDuiRegistVO){
        log.debug(String.format("车队注册接口请求参数为%s", cheDuiRegistVO));
        ResultModel rm = new ResultModel();
        //验证参数是否正确
        if(!cheDuiRegistVO.validate()){
            log.error("车队注册接口参数不正确:"+cheDuiRegistVO);
            rm.setErrcode(10101);
            rm.setErrmsg("车队注册接口请求参数不正确");
            return rm;
        }
        try {
            //验证app签名
            authService.validateAuth(cheDuiRegistVO);
            //检验手机
            ValidateUtil.validateMobile(cheDuiRegistVO.getRegist().getMobileNum());
            //通过用户名查询用户
            User user=userDao.selectByUserName(cheDuiRegistVO.getRegist().getUser_name());
            
            if(user!=null){
                log.error(String.format("用户名%s已经存在", cheDuiRegistVO.getRegist().getUser_name()));
                rm.setErrcode(10104);
                rm.setErrmsg("该用户名已经注册");
                return rm;
            }
            //通过手机号查询用户
            user = userDao.selectByMobile(cheDuiRegistVO.getRegist().getMobileNum());
            if(user==null){
                //新建一个用户
                user=createUser(cheDuiRegistVO.getRegist().getMobileNum(), cheDuiRegistVO.getRegist().getUser_name());
                //产生一个随机密码
                String pwd = NumRandom.getRandom(6);
                user.setPassword(Md5Util.Encrypt(pwd));
                userSrv.createUser(user, cheDuiRegistVO.getApp().getAppId());
                //新增用户属性
                userAttrSrv.addAttr(user.getUserId(), new String[]{"CHEDUI"});
                //发送短信到用户手机
                sendSMS(user.getMobilenum(),user.getUserName(),pwd,cheDuiRegistVO.getApp().getAppId());
            }else{
                //判断原本存在的手机号是否有用户名
                if(StringUtils.isNotBlank(user.getUserName())){
                    log.error(String.format("手机号%s的用户名已经存在,无法修改其用户名", cheDuiRegistVO.getRegist().getMobileNum()));
                    rm.setErrcode(10104);
                    rm.setErrmsg(String.format("手机号%s的用户名已经存在,无法修改其用户名", cheDuiRegistVO.getRegist().getMobileNum()));
                    return rm;
                }
                //增加用户名到该用户内
                user.setUserName(cheDuiRegistVO.getRegist().getUser_name());
                userSrv.updateUser(user);
            }
            // 创建帐户
            AccountFactory.createAccounts(user.getUserId());
        } catch (Exception e) {
            log.error(String.format("创建车队用户[%s]，处理失败", cheDuiRegistVO), e);
            rm.setErrcode(10801);
            rm.mergeException(e);
        }
        if(rm.getErrcode()==0){
            rm.setErrmsg("注册成功");
        }
        return rm;
    }
    
    
    /**
     * 根据登录类型进行登录
     * @param loginByTypeVO
     * @return
     */
    @RequestMapping("/loginByType")
    @ResponseBody
    public Object loginByLoginType(@RequestBody LoginByTypeVO loginByTypeVO){
        log.debug(String.format("根据登录类型登录接口请求参数为%s", loginByTypeVO));
        ResultModel rm = new ResultModel();
        if(!loginByTypeVO.validate()){
            log.error("根据登录类型登录接口参数不正确:"+loginByTypeVO);
            rm.setErrcode(10101);
            rm.setErrmsg("根据登录类型登录接口请求参数不正确");
            return rm;
        }
        try {
            //验证app签名
            authService.validateAuth(loginByTypeVO);
            User user=getUserByLoginType(loginByTypeVO.getLogin().getLogin_type(),loginByTypeVO.getLogin().getUser());
            if(user==null){
                log.error(String.format("用户%s不存在", loginByTypeVO.getLogin().getUser()));
                rm.setErrcode(10103);
                rm.setErrmsg("该用户不存在");
                return rm;
            }
            if(!loginByTypeVO.getLogin().getPassword().equals(user.getPassword())){
                log.error("用户登录密码不正确");
                rm.setErrcode(10204);
                rm.setErrmsg("用户登录密码不正确");
                return rm;
            }
            UserToken selectToken = tokensrv.getOrCreateToken(loginByTypeVO.getApp().getAppId(), user.getUserId());
            rm.put("token", selectToken.getToken());
            rm.put("expireIn", selectToken.getExpirein());
            rm.put("orgType", user.getOrgType());
            // 创建帐户，避免新开其它帐户时，旧用户没有创建该帐户而造成问题
            AccountFactory.createAccounts(user.getUserId());
        } catch (Exception e) {
            log.error(String.format("根据登录类型登录[%s]，处理失败", loginByTypeVO), e);
            rm.setErrcode(10801);
            rm.mergeException(e);
        }
        if(rm.getErrcode()==0){
            rm.setErrmsg("登录成功");
        }
        return rm;
    }
    

    
    /**
     * 绑定手机号
     * @param bindMobileVO
     * @return
     */
    @RequestMapping("/bindMobile")
    @ResponseBody
    public Object bindMobile(@RequestBody BindMobileVO bindMobileVO){
        log.debug(String.format("绑定手机接口请求参数为%s", bindMobileVO));
        ResultModel rm = new ResultModel();
        if(!bindMobileVO.validate()){
            log.error("绑定手机接口参数不正确:"+bindMobileVO);
            rm.setErrcode(10101);
            rm.setErrmsg("绑定手机接口请求参数不正确");
            return rm;
        }
        try {
            //验证app签名
            authService.validateAuth(bindMobileVO);
            if(LoginType.MOBILE.name().equals(bindMobileVO.getBind().getLogin_type())){
                log.error("登录类型为mobile无法进行手机绑定");
                rm.setErrcode(10801);
                rm.setErrmsg("登录类型为mobile无法进行手机绑定");
                return rm;
            }
            // 检验手机
            ValidateUtil.validateMobile(bindMobileVO.getBind().getMobileNum());
            //验证验证码
            boolean authCodeSuccess = validateSMSCode(bindMobileVO.getAppId(),bindMobileVO.getMobileNum(),bindMobileVO.getBind().getSmsCode());
            if(!authCodeSuccess){
                log.error(String.format("验证码不正确,验证码为:%s", bindMobileVO.getBind().getSmsCode()));
                rm.setErrcode(10203);
                rm.setErrmsg("验证码不正确");
                return rm;
            }
            //判断要绑定的手机号是否已经存在
            User user=userDao.selectByMobile(bindMobileVO.getBind().getMobileNum());
            if(user!=null){
                log.error(String.format("要绑定的手机号%s已经存在", bindMobileVO.getBind().getMobileNum()));
                rm.setErrcode(10801);
                rm.setErrmsg(String.format("要绑定的手机号%s已经存在", bindMobileVO.getBind().getMobileNum()));
                return rm;
            }
            //判断要绑定的用户是否存在
            user=getUserByLoginType(bindMobileVO.getBind().getLogin_type(),bindMobileVO.getBind().getUser());
            if(user==null){
                log.error(String.format("用户%s不存在", bindMobileVO.getBind().getUser()));
                rm.setErrcode(10103);
                rm.setErrmsg("该用户不存在");
                return rm;
            }
            //判断指向的用户是否已经有有手机号如果有则不允许其修改
            if(StringUtils.isNotBlank(user.getMobilenum())){
                log.error("用户原本已经有手机号不允许修改");
                rm.setErrcode(10801);
                rm.setErrmsg("用户原本已经有手机号不允许修改");
                return rm;
            }
            user.setMobilenum(bindMobileVO.getBind().getMobileNum());
            //更新数据
            userSrv.updateUser(user);
        } catch (Exception e) {
            log.error(String.format("绑定手机[%s]，处理失败", bindMobileVO), e);
            rm.setErrcode(10801);
            rm.mergeException(e);
        }
        if(rm.getErrcode()==0){
            rm.setErrmsg("绑定手机成功");
        }
        return rm;
    }
    
    
    /**
     * 根据旧密码重置密码
     * @param resetPWByOldPWVO
     * @return
     */
    @RequestMapping("/resetPwdByOldPwd")
    @ResponseBody
    public Object resetPWByOldPW(@RequestBody ResetPWByOldPWVO resetPWByOldPWVO){
        log.debug(String.format("重置密码接口请求参数为%s", resetPWByOldPWVO));
        ResultModel rm = new ResultModel();
        if(!resetPWByOldPWVO.validate()){
            log.error("重置密码接口参数不正确:"+resetPWByOldPWVO);
            rm.setErrcode(10101);
            rm.setErrmsg("重置密码接口参数不正确");
            return rm;
        }
        try {
            //验证APP签名
            authService.validateAuth(resetPWByOldPWVO);
            //验证用户是否存在
            User user=getUserByLoginType(resetPWByOldPWVO.getReset().getLogin_type(), resetPWByOldPWVO.getReset().getUser());
            if(user==null){
                log.error(String.format("用户%s不存在", resetPWByOldPWVO.getReset().getUser()));
                rm.setErrcode(10103);
                rm.setErrmsg("该用户不存在");
                return rm;
            }
            //验证旧登录密码是否正确(在验证参数的时候已经确认请求的老密码和新密码不会为空和空字符串)
            if(!resetPWByOldPWVO.getReset().getOld_password().equals(user.getPassword())){
                log.error("重置登录密码 旧密码错误");
                rm.setErrcode(10204);
                rm.setErrmsg("旧密码不正确");
                return rm; 
            }
            user.setPassword(resetPWByOldPWVO.getReset().getNew_password());
            //同步到用户中心修改密码
            boolean flag=SynUserCenterData.synUserPassword(user.getMobilenum(), user.getPassword());
            if(!flag){
            	log.info("同步密码到用户中心失败");
            	rm.setErrcode(10801);
                rm.setErrmsg("同步信息到用户中心失败,请重新尝试");
                return rm; 
            }
            //更新数据库
            userSrv.updateUser(user);
        } catch (Exception e) {
            log.error(String.format("根据旧密码重置密码[%s]，处理失败", resetPWByOldPWVO), e);
            rm.setErrcode(10801);
            rm.mergeException(e);
        }
        if(rm.getErrcode()==0){
            rm.setErrmsg("重置密码成功");
        }
        return rm;
    }


    //发送注册成功短信
    private void sendSMS(String mobilenum, String userName, String pwd,String appId) {
        
        String content="尊敬的用户您好，您已在油我发起成功开通了会员。登录账户为%s，登录密码为%s，请妥善保管。为了安全，请尽快登录油我发起进行密码修改，详询可咨询4006630666。";
        content=String.format(content, userName,pwd);
        try {
            
            ResultModel resultModel = smsSender.send(mobilenum, content,appId, SmsSendService.ACTION_NAME_REGISTER);
            
            if (resultModel != null && resultModel.isSuccessed()) {
                log.debug("短信发送成功");
            } else {
                log.debug("短信发送失败," + resultModel != null ? resultModel.getErrmsg() : "短信访问出错");
            }
        
        } catch (Exception e) {
            log.error("发送短信失败",e);
        }

    }

    //创建用户，其中密码在外面赋值
    private User createUser(String mobileNum,String user_name){
        User user=new User();
        user.setMobilenum(mobileNum);
        user.setUserName(user_name);
        user.setLoginType(LoginType.USERNAME.name());
        user.setOrgType(OrgType.PERSONAL.name());
        user.setInsertTime(new Date());
        return user;
    }
    
    //根据登录类型获取用户
    private User getUserByLoginType(String loginType,String user) {
        LoginType logintype=LoginType.valueOf(loginType);
        if(logintype==null){
            return null;
        }
        switch(logintype){
            case MOBILE:
                return userDao.selectByMobile(user);
            case USERNAME:
                return userDao.selectByUserName(user);
            case EMAIL:
                return userDao.selectByEmail(user);
        }
        return null;
    }
    
    /**
     * 验证验证码
     * @param appId
     * @param mobileNum
     * @param authCode
     * @return
     */
    private boolean validateSMSCode(String appId, String mobileNum,String authCode) {
        SmsCode query = new SmsCode();
        query.setAppId(appId);
        query.setMobilenum(mobileNum);
        SmsCode code = smscodemapper.getAuthCode(query);
        if (log.isTraceEnabled()) {
            log.trace("手机验证码信息是：" + code);
        }
        try{
            if (code != null&& code.getSmscode().equals(authCode)
                    && (code.getSendTime().getTime() + code.getExpirein() * 1000) > System
                            .currentTimeMillis()) {
                return true;
            }
            return false;
        }
        finally{
            // 删除验证码
            smscodemapper.deleteAuthCode(query);
            
        }
    }
}
