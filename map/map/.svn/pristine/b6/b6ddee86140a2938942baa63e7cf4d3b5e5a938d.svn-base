package com.hummingbird.maaccount.util.synuserinfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hummingbird.maaccount.util.synuserinfo.busniess.AccessTokenHandle;
import com.hummingbird.maaccount.util.synuserinfo.busniess.SynUserCenterBusniess;
import com.hummingbird.maaccount.util.synuserinfo.vo.result.UpdatePasswordWithVerifyResultVO;

public class SynUserCenterData {
	
	private static final Log log = LogFactory.getLog(SynUserCenterData.class);
	
	/**
	 * 同步密码到用户中心
	 * @param userName
	 * @param password
	 * @return
	 */
	public static boolean synUserPassword(String userName,String password){
		try {
			//获取访问令牌
			String accesstoken=AccessTokenHandle.getAccessToken();
			//更新密码
			UpdatePasswordWithVerifyResultVO updatePasswordWithVerifyResultVO=SynUserCenterBusniess.updatePassword(userName, password, accesstoken);
			//return updatePasswordWithVerifyResultVO.isSuccess();
		} catch (Exception e) {
			log.error(e);
			//return false;
		}
		return true;
	}

}
