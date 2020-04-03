package com.hummingbird.maaccount.util.synuserinfo.busniess;

import com.hummingbird.maaccount.util.NumRandom;
import com.hummingbird.maaccount.util.synuserinfo.config.SynUserCenterConfig;
import com.hummingbird.maaccount.util.synuserinfo.util.HttpHelper;
import com.hummingbird.maaccount.util.synuserinfo.util.JsonHelper;
import com.hummingbird.maaccount.util.synuserinfo.vo.result.GetCodeResultVO;
import com.hummingbird.maaccount.util.synuserinfo.vo.result.GetTokenResultVO;
import com.hummingbird.maaccount.util.synuserinfo.vo.result.UpdatePasswordWithVerifyResultVO;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SynUserCenterBusniess {
	
	private static final Log log = LogFactory.getLog(SynUserCenterBusniess.class);
	
	/**
	 * 获取授权码
	 * @return
	 */
	public static GetCodeResultVO getAccessCode(){
		Map<String,Object> form=new HashMap<>();
		form.put("client_id", SynUserCenterConfig.CLIENT_ID);
		form.put("state", NumRandom.getRandom(6));
		form.put("response_type", SynUserCenterConfig.CODE_DEFAULT_RESPONSE_TYPE);
		log.info(String.format("获取用户中心授权码请求参数为:%s", form));
		String result=HttpHelper.formPost(SynUserCenterConfig.GET_CODE_PATH,form);
		log.info(String.format("获取用户中心授权码返回结果为:%s", result));
		return JsonHelper.fromJson(result, GetCodeResultVO.class);
	}
	
	/**
	 * 获取访问accesstoken
	 * @param code  授权码
	 * @return
	 */
	public static GetTokenResultVO getAccessToken(String code){
		Map<String,Object> form=new HashMap<>();
		form.put("code", code);
		form.put("grant_type", SynUserCenterConfig.ACCESS_TOKEN_DEFAULT_GRANT_TYPE);
		log.info(String.format("获取用户中心访问令牌请求参数为:%s", form));
		String result=HttpHelper.formPostTakeUserInfo(SynUserCenterConfig.GET_ACCESS_TOKEN_PATH,form);
		log.info(String.format("获取用户中心访问令牌返回结果为:%s", result));
		return JsonHelper.fromJson(result, GetTokenResultVO.class);
	}
	
	/**
	 * 同步用户中心的密码
	 * @param userName   用户名
	 * @param password   MD5后的密码
	 * @param token      访问令牌
	 * @return
	 */
	public static UpdatePasswordWithVerifyResultVO updatePassword(String userName,String password,String token){
		Map<String,Object> param=new HashMap<>();
		param.put("mobile", userName);
		param.put("md5password", password);
		Map<String,Object> form=new HashMap<>();
		form.put("params",JsonHelper.toJson(param));
		form.put("access_token", token);
		log.info(String.format("同步用户中心用户密码请求参数为:%s", form));
		String result=HttpHelper.formPost(SynUserCenterConfig.UPDATE_PASSWORD_PATH,form);
		log.info(String.format("同步用户中心用户密码返回结果为:%s", result));
		return JsonHelper.fromJson(result, UpdatePasswordWithVerifyResultVO.class);
	}
	
	

}
