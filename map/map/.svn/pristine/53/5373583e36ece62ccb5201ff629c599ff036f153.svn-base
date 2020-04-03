package com.hummingbird.maaccount.controller;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.exception.SignatureException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.CertificateUtils;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.service.IAuthenticationService;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.AppInfoService;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.LoginType;
import com.hummingbird.maaccount.vo.SynUserPasswordVO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Controller
@RequestMapping("/synUserInfo")
public class SynUserPasswordController extends BaseController{
	
	@Autowired(required = true)
    private IAuthenticationService authService;
	
	@Autowired(required = true)
	private UserService userSrv;
	
	@Autowired(required = true)
    private UserMapper userDao;
	
	@Autowired(required = true)
	private AppInfoService appService;
	
	@RequestMapping("/password")
	@ResponseBody
	public Object synPassword(@RequestBody SynUserPasswordVO synUserPasswordVO){
		log.info("同步密码接口开始.....");
		log.info(String.format("请求的参数为:", synUserPasswordVO));
		ResultModel rm = new ResultModel();
		try{
		    //验证参数
			synUserPasswordVO.validate();
		    //验证APP签名
			authService.validateAuth(synUserPasswordVO);
		    //验证证书签名
			String plaintext=ValidateUtil.sortbyValues(synUserPasswordVO.getFieldArray());
			String appId=synUserPasswordVO.getApp().getAppId();
			validateSignByCertificate(plaintext,appId,synUserPasswordVO.getSign());
			//验证用户是否存在
			User user=getUserByLoginType(synUserPasswordVO.getLoginType(),synUserPasswordVO.getUserName());
			if(user==null){
				log.info("未找到对应的用户");
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
			log.info("通过验证，进行密码同步");
		    //通过所有修改密码
			user.setPassword(synUserPasswordVO.getPassword());
			//更新数据库
            userSrv.updateUser(user);
            rm.setErrcode(0);
            rm.setErrmsg("同步密码成功");
		}catch (Exception e) {
			log.error("同步密码接口执行失败...",e);
			rm.mergeException(e);
		}
		log.info("同步密码接口结束.....");
		return rm;
	}

	//根据证书验证签名
	private void validateSignByCertificate(String plaintext, String appId,String sign) throws SignatureException {
		String publickeyStr = appService.getPublicKeyStr(appId);
		if(StringUtils.isBlank(publickeyStr)){
			log.info(String.format("app无公钥，无法进行验签"));
			throw ValidateException.ERROR_SIGNATURE_RSA;
		}
		try {
			PublicKey pkey = CertificateUtils.getPublicKeyFromCer(new ByteArrayInputStream(Base64.decodeBase64(publickeyStr)));
			boolean success = CertificateUtils.verifySignatureByPublicKey(plaintext.getBytes(), sign, pkey);
			if(!success){
				log.debug(String.format("请求签名验签不通过"));
				throw ValidateException.ERROR_SIGNATURE_RSA;
			}
		} catch (Exception e) {
			log.error(String.format("请求签名验签出错"),e);
			throw ValidateException.ERROR_SIGNATURE_RSA.clone(e);
		}
	}
	
	//根据登录类型获取用户
    private User getUserByLoginType(String loginType,String user) {
    	//当为空时默认为手机号
    	if(StringUtils.isBlank(loginType)){
    		loginType="MOBILE";
    	}
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

}
