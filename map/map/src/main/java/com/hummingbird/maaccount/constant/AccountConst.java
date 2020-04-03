/**
 * 
 * AccountStatus.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.constant;

import com.hummingbird.maaccount.face.Account;

/**
 * @author huangjiej_2
 * 2014年12月28日 上午10:20:24
 * 本类主要做为
 */
public abstract class AccountConst {

	/**
	 * 正常
	 */
	public static final String ACCOUNT_STATUS_OK = "ok#";
	/**
	 * 注销
	 */
	public static final String ACCOUNT_STATUS_OFF = "off";

	/**
	 * 现金帐户
	 */
	public static final String ACCOUNT_TYPE_CASH = "CA#";
	/**
	 * 投资帐户
	 */
	public static final String ACCOUNT_TYPE_INVESTMENT = "IA#";
	/**
	 * 分期卡
	 */
	public static final String ACCOUNT_TYPE_OILCARD="OCA";
	/**
	 * 折扣卡
	 */
	public static final String ACCOUNT_TYPE_DISCOUNTCARD="DCA";
	/**
	 * 容量卡
	 */
	public static final String ACCOUNT_TYPE_VOLUMECARD="VCA";
	/**
	 * 其它帐户
	 */
	public static final String ACCOUNT_TYPE_OTHER = "TA#";
	
	
	
	
}
