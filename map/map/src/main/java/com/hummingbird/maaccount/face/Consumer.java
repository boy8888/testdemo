/**
 * 
 * Consumer.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.face;

import com.hummingbird.maaccount.constant.AccountConst;
import com.hummingbird.maaccount.constant.OrderConst;

/**
 * @author john huang
 * 2015年2月13日 下午3:28:24
 * 本类主要做为消费者接口，它可以是手机号或其它的帐户编号
 */
public interface Consumer {

	
	public static String CONSUMER_TYPE_MOBILE="mobileNum";
	public static String CONSUMER_TYPE_CASHACCOUNT=AccountConst.ACCOUNT_TYPE_CASH;
	public static String CONSUMER_TYPE_INVESTMENTACCOUNT=AccountConst.ACCOUNT_TYPE_INVESTMENT;
	public static String CONSUMER_TYPE_OILCARD=AccountConst.ACCOUNT_TYPE_OILCARD;
	public static String CONSUMER_TYPE_DISCOUNTCARD=AccountConst.ACCOUNT_TYPE_DISCOUNTCARD;
	public static String CONSUMER_TYPE_VOLUMECARD=AccountConst.ACCOUNT_TYPE_VOLUMECARD;
	public static String MOBILE_PAY_OFFLINE="MOBILE_PAY_OFFLINE";
	/**
	 * 消费的类型，有电话号码-mobileNum,现金帐户-CA# ,投资帐户-IA#，分期卡-OCA等
	 * @return
	 */
	public String getConsumerType();
	
	/**
	 * 消费者标识
	 * @return
	 */
	public String getConsumerId();
	
	
}
