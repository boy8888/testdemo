package com.hummingbird.maaccount.util.synuserinfo.busniess;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hummingbird.maaccount.util.synuserinfo.vo.result.GetCodeResultVO;
import com.hummingbird.maaccount.util.synuserinfo.vo.result.GetTokenResultVO;

public class AccessTokenHandle {
	
	private static final Log log = LogFactory.getLog(AccessTokenHandle.class);

	//用户中心访问令牌
	private static String accessToken;
	
	//最后更新时间(时间戳)
	private static Long lastUpdateTime;
	
	//令牌超时时间(毫秒)
	private final static int EXPIRES =7200000;
	
	//更新延迟时间(毫秒)
	private final static int DELAY=30000;
	
	public static String getAccessToken(){
		if(isSuccessToken()){
			return accessToken;
		}
		//token已经超时，重新获取并返回
		return refreshAccessToken();
	}
	
	private static String refreshAccessToken() {
		synchronized (AccessTokenHandle.class) {
			if(isSuccessToken()){
				return accessToken;
			}
			log.info("刷新accessToken开始...");
			//获取授权码
			GetCodeResultVO getCodeResultVO=SynUserCenterBusniess.getAccessCode();
			if(!getCodeResultVO.isSuccess()){
				throw new RuntimeException("获取授权码失败");
			}
			//获取accessToken
			GetTokenResultVO getTokenResultVO=SynUserCenterBusniess.getAccessToken(getCodeResultVO.getCode());
			if(!getTokenResultVO.isSuccess()){
				throw new RuntimeException("获取访问令牌失败");
			}
			log.info("刷新accessToken成功...");
			accessToken=getTokenResultVO.getAccess_token();
			lastUpdateTime=System.currentTimeMillis();
		}
		log.info("返回正常的accessToken...");
		return accessToken;
	}

	//判断token是否正常
	private static boolean isSuccessToken(){
		if(accessToken==null){
			log.info("accessToken未初始化...");
			return false;
		}
		if(lastUpdateTime==null){
			lastUpdateTime=0L;
		}
		//判断上次更新的时间是否已经超时
		if((System.currentTimeMillis()-lastUpdateTime)>(EXPIRES-DELAY)){
			log.info(String.format("上次更新token的时间为:%s,token已经超时", new Date(lastUpdateTime)));
			return false;
		}
		log.info("accessToken状态正常直接返回...");
		return true;
	}
}
