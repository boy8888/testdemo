/**
 * 
 * OrderVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.common.exception.DataInvalidException;

/**
 * @author huangjiej_2
 * 2014年12月25日 下午6:02:33
 * 本类主要做为订单的vo对象，主要是controller作为参数传入
 */
public class OrderVO implements IOrderVO {

	/**
	 * 订单id，这里由app传过来时，带过来的原有的营销帐户订单
	 */
	private String orderId;
	/**
	 * 手机号
	 */
	private String mobileNum;
	/**
	 * token
	 */
	private String userToken;
	/**
	 * 金额
	 */
	private Long sum;
	/**
	 * 产品
	 */
	private String productName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 应用订单号
	 */
	private String appOrderId;
	
	/**
	 * 账户验证码
	 */
	private String accountCode;
	
	/**
	 * 交易密码
	 */
	private String paymentCodeMD5;
	
	private String peerAccountId;
	
	private String peerAccountUnit;
	
	private String peerAccountType;
	
	/**
	 * 线下交易流水号，比如用户在柜台转账或网银转账产生的交易流水号
	 */
	private String externalOrderId;
	
	/**
	 * 线下交易时间，用户输入精确到天，提交时前端页面自动补上时分秒，默认为12:00:00
	 */
	private String externalOrderTime;
	
	/**
	 * 支付订单号
	 */
	private String payOrderId;
	
	
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	/**
	 * @return the mobileNum
	 */
	public String getMobileNum() {
		return mobileNum;
	}
	/**
	 * @param mobileNum the mobileNum to set
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	/**
	 * @return the userToken
	 */
	public String getUserToken() {
		return userToken;
	}
	/**
	 * @param userToken the userToken to set
	 */
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	/**
	 * @return the sum
	 */
	public Long getSum() {
		return sum;
	}
	/**
	 * @param sum the sum to set
	 */
	public void setSum(Long sum) {
		this.sum = sum;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the appOrderId
	 */
	public String getAppOrderId() {
		return appOrderId;
	}
	/**
	 * @param appOrderId the appOrderId to set
	 */
	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}
	@Override
	public String toString() {
		return "OrderVO [orderId=" + orderId + ", mobileNum=" + mobileNum
				+ ", userToken=" + userToken + ", sum=" + sum
				+ ", productName=" + productName + ", remark=" + remark
				+ ", appOrderId=" + appOrderId + ", accountCode=" + accountCode
				+ ", paymentCodeMD5=" + paymentCodeMD5 + ", peerAccountId="
				+ peerAccountId + ", peerAccountUnit=" + peerAccountUnit
				+ ", peerAccountType=" + peerAccountType + ", externalOrderId="
				+ externalOrderId + ", externalOrderTime=" + externalOrderTime
				+ ", payOrderId=" + payOrderId + "]";
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 账户验证码
	 */
	public String getAccountCode() {
		return accountCode;
	}
	/**
	 * 账户验证码
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	/**
	 * 交易密码
	 */
	public String getPaymentCodeMD5() {
		return paymentCodeMD5;
	}
	/**
	 * 交易密码
	 */
	public void setPaymentCodeMD5(String paymentCodeMD5) {
		this.paymentCodeMD5 = paymentCodeMD5;
	}
	/**
	 * @return the peerAccountId
	 */
	public String getPeerAccountId() {
		return peerAccountId;
	}
	/**
	 * @param peerAccountId the peerAccountId to set
	 */
	public void setPeerAccountId(String peerAccountId) {
		this.peerAccountId = peerAccountId;
	}
	/**
	 * @return the peerAccountUnit
	 */
	public String getPeerAccountUnit() {
		return peerAccountUnit;
	}
	/**
	 * @param peerAccountUnit the peerAccountUnit to set
	 */
	public void setPeerAccountUnit(String peerAccountUnit) {
		this.peerAccountUnit = peerAccountUnit;
	}
	/**
	 * @return the peerAccountType
	 */
	public String getPeerAccountType() {
		return peerAccountType;
	}
	/**
	 * @param peerAccountType the peerAccountType to set
	 */
	public void setPeerAccountType(String peerAccountType) {
		this.peerAccountType = peerAccountType;
	}
	/**
	 * 线下交易流水号，比如用户在柜台转账或网银转账产生的交易流水号
	 */
	public String getExternalOrderId() {
		return externalOrderId;
	}
	/**
	 * 线下交易流水号，比如用户在柜台转账或网银转账产生的交易流水号
	 */
	public void setExternalOrderId(String externalOrderId) {
		this.externalOrderId = externalOrderId;
	}
	/**
	 *线下交易时间，用户输入精确到天，提交时前端页面自动补上时分秒，默认为12:00:00
	 */
	public String getExternalOrderTime() {
		return externalOrderTime;
	}
	/**
	 *线下交易时间，用户输入精确到天，提交时前端页面自动补上时分秒，默认为12:00:00
	 */
	public void setExternalOrderTime(String externalOrderTime) {
		this.externalOrderTime = externalOrderTime;
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#getPaintText()
	 */
	@Override
	public String getPaintText() {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#selfvalidate()
	 */
	@Override
	public void selfvalidate() throws DataInvalidException {
		
		
	}
	
	 
	
	
}
