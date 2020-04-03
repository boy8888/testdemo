/**
 * 
 * UserService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ToolsException;
import com.hummingbird.common.util.DESUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.commonbiz.vo.UserToken;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.SmsMTDelay;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.mapper.SmsMTDelayMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.ITokenService;
import com.hummingbird.maaccount.service.UserAttrService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.BatchProcessReporter;
import com.hummingbird.maaccount.util.PospEncyptUtil;
import com.hummingbird.maaccount.util.SmsSendUtil;
import com.hummingbird.maaccount.vo.BatchAddUserVO;
import com.hummingbird.maaccount.vo.RegisterUserVO;

/**
 * @author john huang
 * 2015年3月1日 下午8:26:09
 * 本类主要做为
 */
@Service
public class UserService {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	@Autowired(required = true)
	private UserMapper userDao;
	@Autowired(required = true)
	private ITokenService tokensrv;
	@Autowired(required = true)
	private UserAttrService userAttrSrv;
	@Autowired(required = true)
	private SmsMTDelayMapper smsDelayDao;
	/**
	 * 创建用户
	 * @param user
	 * @throws MaAccountException 
	 */
	public void createUser(User user,String appId) throws MaAccountException{
		userDao.insert(user);
		userDao.insertAppId(user.getUserId(), appId);
		AccountFactory.createAccounts(user.getUserId());
//		UserToken createToken = tokensrv.createToken(appId, user.getUserId());
	}
	
	/**
	 * 查找用户
	 * @param mobileNum
	 * @return
	 */
	public User getUserByMobile(String mobileNum){
		User selectByMobile = userDao.selectByMobile(mobileNum);
		return selectByMobile;
	}
	/**
	 * 查找用户
	 * @param mobileNum
	 * @return
	 */
	public User getUserByUserId(int userId){
		User selectByMobile = userDao.selectByPrimaryKey(userId);
		return selectByMobile;
	}

	/**
	 * @param user
	 * @throws MaAccountException 
	 */
	public void updateUser(User user) throws MaAccountException {
		int updatecount = userDao.updateByPrimaryKey(user);
		if(updatecount!=1){
			throw new MaAccountException(MaAccountException.ERR_USER_EXCEPTION,"用户更新失败："+(updatecount==0?"没有更新到用户":String.format("更新了%s个用户",updatecount)));
		}
		
	}

	/**
	 * 批量添加用户
	 * @param body
	 * @param app
	 * @return 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public BatchProcessReporter<Map> createUserBatch(BatchAddUserVO body, AppInfo app) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("批量添加用户开始"));
		}
		List<RegisterUserVO> users = body.getUsers();
		BatchProcessReporter reporter = new BatchProcessReporter();
		int index=1;
		PropertiesUtil pu = new PropertiesUtil();
		boolean needdes = pu.getBoolean("addBatchUser.des", true);//是否解密
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			RegisterUserVO registerUserVO = (RegisterUserVO) iterator.next();
			if (log.isDebugEnabled()) {
				log.debug(String.format("处理第%s条数据",index++));
			}
			try {
				//解密
				if(needdes){
					decryptUser(registerUserVO,app);
				}
				if(StringUtils.isBlank(registerUserVO.getMobileNum())){
					if (log.isDebugEnabled()) {
						log.debug(String.format("手机号码为空,不处理"));
					}
					continue;
				}
				String smstemplateid = "main";
				if(registerUserVO.getAttrs()!=null&&registerUserVO.getAttrs().length>0){
					smstemplateid = registerUserVO.getAttrs()[0];
				}
				User user = createUserIfNessrary(registerUserVO.getMobileNum(),registerUserVO.getName(),registerUserVO.getID(),app.getAppId(),true,false,false,true,smstemplateid);
				//测试用户属性
				if(registerUserVO.getAttrs()!=null&&registerUserVO.getAttrs().length>0){
					userAttrSrv.addAttr(user.getUserId(),registerUserVO.getAttrs());
				}
				reporter.addSuccess();
			} catch (Exception e) {
				log.error(String.format("添加用户%s失败",registerUserVO.getMobileNum()),e);
				Map errmsgmap = new HashMap();
				errmsgmap.put("mobileNum", registerUserVO.getMobileNum());
				errmsgmap.put("msg", e.getMessage());
				reporter.addFail(errmsgmap);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("批量添加用户完成"));
		}
		return reporter;
	}
	
	/**
	 * 创建用户
	 * @param mobileNum
	 * @param name
	 * @param identify
	 * @param appId
	 * @param password
	 * @param invest_passwd
	 * @param pospasswd
	 * @param sendsms
	 * @param smstemplateid 短信模板
	 * @return
	 * @throws MaAccountException
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public User createUserIfNessrary(String mobileNum,String name,String identify,String appId,boolean password,boolean invest_passwd,boolean pospasswd,boolean sendsms,String smstemplateid) throws MaAccountException{
		User userByMobile = getUserByMobile(mobileNum);
		PropertiesUtil pu = new PropertiesUtil();
		smstemplateid = StringUtils.defaultIfEmpty(smstemplateid, "main");
		String maintemplate = pu.getProperty("sms.newuser.template.main."+smstemplateid);
		if(userByMobile!=null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("手机%s的用户已存在,为其添加属性(如有的话)",mobileNum));
			}
		}
		else{
			
			User user = new User();
			user.setId(identify);
			user.setLoginType("MOBILE");
			user.setOrgType("PERSONAL");
			user.setInsertTime(new Date());
			user.setName(name);
			user.setMobilenum(mobileNum);
			String part="";
			if(password){
				String pw = RandomStringUtils.randomAlphabetic(8);
				user.setPassword(Md5Util.Encrypt(pw));
				String pwpart = pu.getProperty("sms.newuser.template.pw."+smstemplateid);
				part = StrUtil.replaceAllWithToken(pwpart, "password", pw);
			}
			maintemplate=maintemplate.replaceAll("\\$\\{sms.newuser.template.pw\\}", part);
			 part="";
			if(invest_passwd){
				String paymentcode = RandomStringUtils.randomAlphabetic(8);
				user.setPaymentcodemd5(Md5Util.Encrypt(paymentcode));
				String paypwpart = pu.getProperty("sms.newuser.template.paymentpw."+smstemplateid);
				part = StrUtil.replaceAllWithToken(paypwpart, "paymentCode", paymentcode);
			}
			maintemplate=maintemplate.replaceAll("\\$\\{sms.newuser.template.paymentpw\\}", part);
			 part="";
			if(pospasswd){
				String paymentcode = StrUtil.genRandomCode(6);
//				try {
				user.setPaymentCodeDES(Md5Util.Encrypt(paymentcode));
//				} catch (ToolsException e) {
//					log.error(String.format("pos支付密码加密出错"),e);
//					throw new MaAccountException(MaAccountException.ERRCODE_CONVERSION,"pos支付密码加密出错",e);
//				}	
				String pospwpart = pu.getProperty("sms.newuser.template.pospw."+smstemplateid);
				part = StrUtil.replaceAllWithToken(pospwpart, "posPaymentCode", paymentcode);
			}
			maintemplate=maintemplate.replaceAll("\\$\\{sms.newuser.template.pospw\\}", part);
			
			
			//创建用户
			userByMobile = user;
			//发短信
			userDao.insert(user);
			userDao.insertAppId(user.getUserId(), appId);
			SmsSendUtil.delaySmsSend(maintemplate, null, user.getMobilenum());
			AccountFactory.createAccounts(user.getUserId());
		}
		return userByMobile;
	}
	

	/**
	 * @param registerUserVO
	 * @param app
	 */
	private void decryptUser(RegisterUserVO registerUserVO, AppInfo app) {
		//对姓名,身份证,手机号解密
		if(StringUtils.isNotBlank(registerUserVO.getID())){
			String des = DESUtil.decodeDESwithCBC(registerUserVO.getID(), app.getAppKey());
			registerUserVO.setID(des);
		}
		if(StringUtils.isNotBlank(registerUserVO.getMobileNum())){
			String des = DESUtil.decodeDESwithCBC(registerUserVO.getMobileNum(), app.getAppKey());
			registerUserVO.setMobileNum(des);
		}
		if(StringUtils.isNotBlank(registerUserVO.getName())){
			String des = DESUtil.decodeDESwithCBC(registerUserVO.getName(), app.getAppKey());
			registerUserVO.setName(des);
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(RandomStringUtils.randomAlphabetic(8));
	}
	
	
}
