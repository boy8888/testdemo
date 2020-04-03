package com.hummingbird.maaccount.controller;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.vo.UserToken;
import com.hummingbird.maaccount.entity.AppLog;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.UserAttr;
import com.hummingbird.maaccount.mapper.AppLogMapper;
import com.hummingbird.maaccount.mapper.BatchAddUserResultDetailMapper;
import com.hummingbird.maaccount.mapper.BatchAddUserResultMapper;
import com.hummingbird.maaccount.mapper.RealNameAuthMapper;
import com.hummingbird.maaccount.service.ITokenService;
import com.hummingbird.maaccount.service.UserAttrService;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.EncryptUtil;
import com.hummingbird.maaccount.util.NumRandom;
import com.hummingbird.maaccount.util.sign.SignUtil;
import com.hummingbird.maaccount.vo.WdffResultModel;
import com.hummingbird.maaccount.vo.WdffUserVO;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:万达非凡 项目专用接口处理类
 *         </td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年5月18日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
@Controller
@RequestMapping("/wdffUserAuth")
public class WdffAuthController extends BaseController {

    @Autowired(required = true)
    private UserService                      userSrv;

    @Autowired(required = true)
    private ITokenService                    tokensrv;

    @Autowired
    protected RealNameAuthMapper             realNamedao;

    @Autowired
    protected AppLogMapper                   logdao;

    @Autowired
    protected UserAttrService                userAttrSrv;

    @Autowired
    protected BatchAddUserResultDetailMapper batchAdduserResultDetailDao;

    @Autowired
    protected BatchAddUserResultMapper       batchAdduserResultDao;

    @Autowired
    protected SmsSendService                 smsSender;

    /**
     * 日志的本地线程
     */
    protected static ThreadLocal<AppLog>     applogTL = new ThreadLocal<AppLog>();

    private Gson                             gson     = new Gson();

    private static final String              KEY      = "R2B0F9FS7D2F8M84V1U68KE76GV803H8";


    /**
     * 登录接口
     */
    @RequestMapping("/login")
    @AccessRequered(methodName = "万达非凡用户登录")
    public @ResponseBody Object login(@RequestBody WdffUserVO wdffUserVO) {
        if (log.isDebugEnabled()) {
            log.debug("万达非凡用户登录：" + gson.toJson(wdffUserVO));
        }
        // 捕捉所有异常,不要由于有异常而不返回信息
        WdffResultModel rm = new WdffResultModel();
        int baseerrcode = 0;
        rm.setStatus(baseerrcode);
        rm.setMessage("登录成功");
        if (wdffUserVO.getUserNameType() != 1) {//对于营销账户系统来讲，此参数无意义，但双方遵守共同规则，故增加此虚拟参数
            rm.setStatus(1131);
            rm.setMessage("用户登录类型userNameType必须为1");
            return rm;
        }
        if (!wdffUserVO.getActivityId().equals("10081")) {//对于营销账户系统来讲，此参数无意义，但双方遵守共同规则，故增加此虚拟参数
            rm.setStatus(1132);
            rm.setMessage("活动编号activityId必须为10081");
            return rm;
        }
        if (!wdffUserVO.getChannel().equals("WDFF")) {//对于营销账户系统来讲，此参数无意义，但双方遵守共同规则，故增加此虚拟参数
            rm.setStatus(1133);
            rm.setMessage("注册渠道channel必须为WDFF");
            return rm;
        }
        //Map<String, Object> map = beanToMap(wdffUserVO);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId", wdffUserVO.getApp().getAppId());
        map.put("userName", wdffUserVO.getUserName());
        map.put("userNameType", wdffUserVO.getUserNameType());
        map.put("activityId", wdffUserVO.getActivityId());
        map.put("channel", wdffUserVO.getChannel());
        String sign = SignUtil.computeSign(KEY, map, false, false);
        if (!sign.equals(wdffUserVO.getSign())) {
            rm.setStatus(1134);
            rm.setMessage("签名不正确");
            if (log.isDebugEnabled()) {
                log.debug(String.format("签名不正确,系统计算的sign=%s", sign));
            }
            return rm;
        }
        try {
            // 检验手机
            ValidateUtil.validateMobile(wdffUserVO.getUserName());
            //           ValidateUtil.assertNull(user, "手机号码已注册", 4121);
            User user = userSrv.getUserByMobile(wdffUserVO.getUserName());
            if (user != null) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("手机号码%s已注册为用户%s[%s]所用", wdffUserVO.getUserName(), user.getName(), user.getUserId()));
                }
                String[] attrs = { "WDFF" };//WDFF=万达非凡
                boolean setattrsuccess = try2setAttr(user, attrs);
                if (!setattrsuccess) {
                    rm.setStatus(0);
                    rm.setMessage("登录成功");
                    if (log.isDebugEnabled()) {
                        log.debug("登录成功;用户已存在，无需添加属性");
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug(String.format("登录成功;为用户添加了用户属性"));
                    }
                }
                rm.setStatus(0);
                rm.setMessage("登录成功");
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("校验通过，现在为手机号码%s创建帐户", wdffUserVO.getUserName()));
                }
                // 创建用户
                user = new User();
                user.setLoginType("MOBILE");
                user.setOrgType("PERSONAL");
                user.setInsertTime(new Date());
                user.setMobilenum(wdffUserVO.getUserName());
                user.setRegisterChannel(wdffUserVO.getChannel());
                String pwd = NumRandom.getRandom(6);
                String content = "尊敬的飞凡用户，您已成为中经油马APP会员（登录账户" + wdffUserVO.getUserName() + "，初始密码" + pwd + "）。中经油马APP加油有优惠，洗车、保养、购物、车险、积分一大波优惠等您来挑。";
                log.info("pwd = " + pwd + ",encode md5 pwd=" + EncryptUtil.EncodeMd5(pwd));
                //ResultModel resultModel = smsSender.send(wdffUserVO.getUserName(), content, wdffUserVO.getApp().getAppId(), SmsSendService.ACTION_NAME_REGISTER);
                //boolean sendresult = resultModel != null && resultModel.isSuccessed();
                /*if (log.isDebugEnabled()) {
                    if (sendresult) {
                        log.debug("短信发送成功");
                    } else {
                        log.debug("短信发送失败," + resultModel != null ? resultModel.getErrmsg() : "短信访问出错");
                    }
                }
                if (!sendresult) {
                    log.debug("发送短信失败" + resultModel != null ? resultModel.getErrmsg() : "短信访问出错");
                }*/
                user.setPassword(EncryptUtil.EncodeMd5(pwd));
                //user.setPaymentcodemd5(loginvo.getLogin().getPaymentCodeMD5());
                userSrv.createUser(user, wdffUserVO.getApp().getAppId());
                //测试用户属性
                String[] attrs = { "WDFF" };//WDFF=万达非凡
                userAttrSrv.addAttr(user.getUserId(), attrs);
            }
            UserToken selectToken = tokensrv.getOrCreateToken(wdffUserVO.getApp().getAppId(), user.getUserId());
            rm.setToken(selectToken.getToken());
            rm.setExpireIn(selectToken.getExpirein());
            rm.setOrgType(user.getOrgType());
            // 创建帐户，避免新开其它帐户时，旧用户没有创建该帐户而造成问题
            AccountFactory.createAccounts(user.getUserId());
        } catch (Exception e1) {
            log.error(String.format("万达非凡用户登录出错[%s]", gson.toJson(wdffUserVO)), e1);
            rm.setStatus(1104);
            rm.setMessage("系统处理异常，" + e1.getMessage());
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("万达非凡用户登录完成");
            }
        }
        return rm;
    }

    /**
     * 查询接口
     */
    @RequestMapping("/users")
    @AccessRequered(methodName = "万达非凡用户查询")
    public @ResponseBody Object users(@RequestBody WdffUserVO wdffUserVO) {
        if (log.isDebugEnabled()) {
            log.debug("用户查询：" + gson.toJson(wdffUserVO));
        }
        // 捕捉所有异常,不要由于有异常而不返回信息
        WdffResultModel rm = new WdffResultModel();
        int baseerrcode = 0;
        rm.setStatus(baseerrcode);
        rm.setMessage("查询成功");
        if (wdffUserVO.getUserNameType() != 1) {//对于营销账户系统来讲，此参数无意义，但双方遵守共同规则，故增加此虚拟参数
            rm.setStatus(1121);
            rm.setMessage("userNameType必须为1");
            return rm;
        }
        if (!wdffUserVO.getActivityId().equals("10081")) {//对于营销账户系统来讲，此参数无意义，但双方遵守共同规则，故增加此虚拟参数
            rm.setStatus(1122);
            rm.setMessage("activityId必须为10081");
            return rm;
        }
        if (!wdffUserVO.getChannel().equals("WDFF")) {//对于营销账户系统来讲，此参数无意义，但双方遵守共同规则，故增加此虚拟参数
            rm.setStatus(1123);
            rm.setMessage("channel必须为WDFF");
            return rm;
        }
        try {
            // 检验手机
            ValidateUtil.validateMobile(wdffUserVO.getUserName());
            User user = userSrv.getUserByMobile(wdffUserVO.getUserName());
            if (user == null) {
                rm.setStatus(1129);
                rm.setMessage("查询无此用户");
            }
        } catch (Exception e1) {
            log.error(String.format("用户查询出错[%s]", gson.toJson(wdffUserVO)), e1);
            rm.setStatus(1124);
            rm.setMessage("系统处理异常，" + e1.getMessage());
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("用户查询完成");
            }
        }
        return rm;
    }

    /**
     * 注册接口
     */
    @RequestMapping("/register")
    @AccessRequered(methodName = "万达非凡用户注册")
    public @ResponseBody Object register(@RequestBody WdffUserVO wdffUserVO) {
        if (log.isDebugEnabled()) {
            log.debug("新用户注册：" + gson.toJson(wdffUserVO));
        }
        // 捕捉所有异常,不要由于有异常而不返回信息
        WdffResultModel rm = new WdffResultModel();
        int baseerrcode = 0;
        rm.setStatus(baseerrcode);
        rm.setMessage("新用户注册成功");
        if (wdffUserVO.getUserNameType() != 1) {//对于营销账户系统来讲，此参数无意义，但双方遵守共同规则，故增加此虚拟参数
            rm.setStatus(1101);
            rm.setMessage("用户登录类型userNameType必须为1");
            return rm;
        }
        if (!wdffUserVO.getActivityId().equals("10081")) {//对于营销账户系统来讲，此参数无意义，但双方遵守共同规则，故增加此虚拟参数
            rm.setStatus(1102);
            rm.setMessage("活动编号activityId必须为10081");
            return rm;
        }
        if (!wdffUserVO.getChannel().equals("WDFF")) {//对于营销账户系统来讲，此参数无意义，但双方遵守共同规则，故增加此虚拟参数
            rm.setStatus(1103);
            rm.setMessage("注册渠道channel必须为WDFF");
            return rm;
        }
        try {
            // 检验手机
            ValidateUtil.validateMobile(wdffUserVO.getUserName());
            //           ValidateUtil.assertNull(user, "手机号码已注册", 4121);
            User user = userSrv.getUserByMobile(wdffUserVO.getUserName());
            if (user != null) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("手机号码%s已注册为用户%s[%s]所用", wdffUserVO.getUserName(), user.getName(), user.getUserId()));
                }
                String[] attrs = { "WDFF" };//WDFF=万达非凡
                boolean setattrsuccess = try2setAttr(user, attrs);
                if (!setattrsuccess) {
                    rm.setStatus(1109);
                    rm.setMessage("手机号已经被其他用户注册");
                    if (log.isDebugEnabled()) {
                        log.debug("用户已存在，无需添加属性");
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug(String.format("为用户添加了用户属性"));
                    }
                }
                rm.setStatus(1109);
                rm.setMessage("手机号已经被其他用户注册..");
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("校验通过，现在为手机号码%s创建帐户", wdffUserVO.getUserName()));
                }
                // 创建用户
                user = new User();
                user.setLoginType("MOBILE");
                user.setOrgType("PERSONAL");
                user.setInsertTime(new Date());
                user.setMobilenum(wdffUserVO.getUserName());
                user.setRegisterChannel(wdffUserVO.getChannel());
                String pwd = NumRandom.getRandom(6);
                String content = "尊敬的飞凡用户，您已成为中经油马APP会员（登录账户" + wdffUserVO.getUserName() + "，初始密码" + pwd + "）。中经油马APP加油有优惠，洗车、保养、购物、车险、积分一大波优惠等您来挑。";
                log.info("pwd = " + pwd + ",encode md5 pwd=" + EncryptUtil.EncodeMd5(pwd));
                //ResultModel resultModel = smsSender.send(wdffUserVO.getUserName(), content, wdffUserVO.getApp().getAppId(), SmsSendService.ACTION_NAME_REGISTER);
                //boolean sendresult = resultModel != null && resultModel.isSuccessed();
                /*if (log.isDebugEnabled()) {
                    if (sendresult) {
                        log.debug("短信发送成功");
                    } else {
                        log.debug("短信发送失败," + resultModel != null ? resultModel.getErrmsg() : "短信访问出错");
                    }
                }
                if (!sendresult) {
                    log.debug("发送短信失败" + resultModel != null ? resultModel.getErrmsg() : "短信访问出错");
                }*/
                user.setPassword(EncryptUtil.EncodeMd5(pwd));
                //user.setPaymentcodemd5(loginvo.getLogin().getPaymentCodeMD5());
                userSrv.createUser(user, wdffUserVO.getApp().getAppId());
                //测试用户属性
                String[] attrs = { "WDFF" };//WDFF=万达非凡
                userAttrSrv.addAttr(user.getUserId(), attrs);
            }

            UserToken selectToken = tokensrv.getOrCreateToken(wdffUserVO.getApp().getAppId(), user.getUserId());
            rm.setToken(selectToken.getToken());
            rm.setExpireIn(selectToken.getExpirein());
            rm.setOrgType(user.getOrgType());
            // 创建帐户，避免新开其它帐户时，旧用户没有创建该帐户而造成问题
            AccountFactory.createAccounts(user.getUserId());
        } catch (Exception e1) {
            log.error(String.format("新用户注册出错[%s]", gson.toJson(wdffUserVO)), e1);
            rm.setStatus(1104);
            rm.setMessage("系统处理异常，" + e1.getMessage());
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("新用户注册完成");
            }
        }
        return rm;
    }

    /**
     * 尝试为用户添加属性
     * @param user
     * @param attrs
     * @return
     * @throws ValidateException
     */
    private boolean try2setAttr(User user, String[] attrs) throws ValidateException {

        if (attrs != null && attrs.length > 0) {
            //          userAttrSrv.addAttr(user.getUserId(),loginvo.getLogin().getAttrs());
            boolean addedattr = false;
            for (int i = 0; i < attrs.length; i++) {
                String attr = attrs[i];
                UserAttr userAttr = userAttrSrv.getUserAttr(user.getMobilenum(), attr);
                if (userAttr == null) {
                    if (log.isDebugEnabled()) {
                        log.debug(String.format("为已存在用户添加用户属性%s", attr));
                    }
                    UserAttr added = userAttrSrv.addAttr(user.getUserId(), attr);
                    if (added == null) { throw ValidateException.ERROR_SYSTEM_INTERNAL.clone(null, "为用户添加属性失败"); }
                    addedattr = true;
                }
            }
            return addedattr;
        }
        return false;
    }

    private static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}
