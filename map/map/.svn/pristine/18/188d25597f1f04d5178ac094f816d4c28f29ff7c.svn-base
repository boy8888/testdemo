/**
 * 
 * VerifyTokenVo.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.Map;

import com.hummingbird.common.exception.SignatureException;

/**
 * @author john huang
 * 2015年4月8日 下午10:06:09
 * 本类主要做为 验证用户token 参数对象
 */
public class VerifyTokenVo extends VerifyTokenAppVo {

	private VerifyTokenUserVo userToken;

	/**
	 * @return the userToken
	 */
	public VerifyTokenUserVo getUserToken() {
		return userToken;
	}

	/**
	 * @param userToken the userToken to set
	 */
	public void setUserToken(VerifyTokenUserVo userToken) {
		this.userToken = userToken;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.vo.AppVO#setOtherParam(java.util.Map)
	 */
	@Override
	public void setOtherParam(Map map) {
		userToken.setOtherParam(map);
		super.setOtherParam(map);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.vo.AppVO#isAuthed()
	 */
	@Override
	public boolean isAuthed() throws SignatureException {
		
		return super.isAuthed()&&userToken.isAuthed();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VerifyTokenVo [userToken=" + userToken + ", appId=" + appId
				+ ", appKey=" + appKey + ", appname=" + appname
				+ ", timeStamp=" + timeStamp + ", nonce=" + nonce
				+ ", signature=" + signature + "]";
	}

	
	
	
}
