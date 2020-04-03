/**
 * 
 * AccountCodeable.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

/**
 * @author john huang
 * 2015年4月23日 下午5:46:10
 * 本类主要做为
 */
public interface AccountCodeable {

	/**
	 * 获取帐户验证码
	 */
	String getAccountCode();
	/**
	 * 手机号
	 * @return
	 */
	String getMobileNum();
	
//	/**
//	 * 应用id
//	 * @return
//	 */
//	String getAppId();
}
