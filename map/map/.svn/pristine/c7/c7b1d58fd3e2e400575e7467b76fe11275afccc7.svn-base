/**
 * 
 * Order.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.face;

import java.util.Date;


/**
 * @author huangjiej_2
 * 2014年12月18日 下午11:00:46
 * 本类主要做为 订单接口
 */
public interface Order<ACCOUNT extends Account> {

	/**
	 * 订单对应的帐户
	 * @return
	 */
	ACCOUNT getAccount();
	
	/**
	 * 订单金额
	 * @return
	 */
	long getSum();

	public abstract String getAppOrderId();

	public abstract String getStatus();

	public abstract String getOriginalorderId();

	public abstract String getAppId();

	public abstract String getOrderId();

	public abstract Date getInsertTime();

	public abstract String getAccountId();

	public abstract long getBalance();

	public abstract Date getExternalOrderTime();

	public abstract String getExternalOrderId();

	public abstract String getType();

	public abstract String getPeerAccountUnit();

	public abstract String getPeerAccountId();

	public abstract String getPeerAccountType();

	public abstract String getFlowDirection();

	public abstract String getRemark();

	
	
}
