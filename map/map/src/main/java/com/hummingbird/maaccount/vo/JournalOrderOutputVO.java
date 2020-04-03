/**
 * 
 * SpendOrderOutputVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author john huang
 * 2015年7月3日 上午8:29:37
 * 本类主要做为 流水帐记录
 */
@JsonIgnoreProperties({"remark","accountType","sellerName","sellerId"})
public class JournalOrderOutputVO extends SpendOrderOutputVO {

	/**
	 * 产品id
	 */
	protected String accountProductId;
	/**
	 * 应用订单号
	 */
	protected String appOrderId;
	/**
	 * 渠道订单号
	 */
	protected String channelOrderId;
	/**
	 * 产品id 
	 */
	public String getAccountProductId() {
		return accountProductId;
	}
	/**
	 * 产品id 
	 */
	public void setAccountProductId(String accountProductId) {
		this.accountProductId = accountProductId;
	}
	/**
	 * 应用订单号 
	 */
	public String getAppOrderId() {
		return appOrderId;
	}
	/**
	 * 应用订单号 
	 */
	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}
	/**
	 * 渠道订单号 
	 */
	public String getChannelOrderId() {
		return channelOrderId;
	}
	/**
	 * 渠道订单号 
	 */
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}
	/**
	 * 余额
	 */
	private Long balance;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JournalOrderOutputVO [accountProductId=" + accountProductId + ", appOrderId=" + appOrderId
				+ ", channelOrderId=" + channelOrderId + ", balance=" + balance + ", sellerName=" + sellerName
				+ ", productName=" + productName + ", productId=" + productId + ", storeName=" + storeName
				+ ", insertTime=" + insertTime + ", productPrice=" + productPrice + ", productQuantity="
				+ productQuantity + ", appName=" + appName + ", appId=" + appId + ", status=" + status + ", type="
				+ type + ", typeName=" + typeName + ", accountId=" + accountId + ", accountType=" + accountType
				+ ", mobileNum=" + mobileNum + ", orderId=" + orderId + ", remark=" + remark + ", sellerId=" + sellerId
				+ ", storeId=" + storeId + ", terminalId=" + terminalId + ", terminalOrderId=" + terminalOrderId
				+ ", batchNo=" + batchNo + ", operatorId=" + operatorId + ", shiftInfo=" + shiftInfo + "]";
	}
	/**
	 * 余额 
	 */
	public Long getBalance() {
		return balance;
	}
	/**
	 * 余额 
	 */
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	
	
}
