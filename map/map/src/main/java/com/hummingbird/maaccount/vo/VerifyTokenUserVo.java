/**
 * 
 * VerifyTokenVo.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.common.util.ValidateUtil;


/**
 * @author john huang
 * 2015年4月8日 下午10:06:09
 * 本类主要做为 验证用户token 的用户token相关 参数对象
 */
public class VerifyTokenUserVo extends VerifyTokenAppVo {

	private String token;

	/**
	 * appvo
	 * @param appCode
	 */
	public void setAppCode(String appCode){
		this.appId = appCode;
		
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	protected String getPaintText(){
		return ValidateUtil.sortbyValues(appKey,appId,nonce,timeStamp,token);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VerifyTokenUserVo [token=" + token + ", appId=" + appId
				+ ", appKey=" + appKey + ", appname=" + appname
				+ ", timeStamp=" + timeStamp + ", nonce=" + nonce
				+ ", signature=" + signature + "]";
	}
	
	
}
