/**
 * 
 * OfflinePayOrderVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;

/**
 * @author john huang
 * 2015年2月6日 下午11:37:17
 * 本类主要做为 线下开卡order部分
 */
public class OfflineOpencardDeliveryOrderVO  implements IOrderVO {

	protected String orderId;
	protected String accountType;
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#getPaintText()
	 */
	@Override
	@JsonIgnore
	public String getPaintText() {
		
		return ValidateUtil.sortbyValues(orderId,accountType);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#selfvalidate()
	 */
	@Override
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.orderId, "订单号");
		ValidateUtil.assertNull(this.accountType, "帐户类型");
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "OfflineOpencardOrderVO [orderId=" + orderId + ", accountType="
				+ accountType + "]";
	}

	@Override
	@JsonIgnore
	public String getMobileNum() {
		return null;
	}




}
