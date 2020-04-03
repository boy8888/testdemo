/**
 * 
 * VerifyTokenVo.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppVO;

/**
 * @author john huang
 * 2015年4月8日 下午10:06:09
 * 本类主要做为 验证用户token 的app相关 参数对象
 */
public class VerifyTokenAppVo extends AppVO {


	/**
	 * appvo
	 * @param appCode
	 */
	public void setAppCode(String appCode){
		this.appId = appCode;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VerifyTokenAppVo [appId=" + appId + ", appKey=" + appKey
				+ ", appname=" + appname + ", timeStamp=" + timeStamp
				+ ", nonce=" + nonce + ", signature=" + signature + "]";
	}
	
	
}
