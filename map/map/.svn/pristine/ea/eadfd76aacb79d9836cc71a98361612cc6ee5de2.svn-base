/**
 * 
 * OfflinePayOrderVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Consumer;
import com.hummingbird.maaccount.util.ConsumerFactory;

/**
 * @author john huang
 * 2015年2月6日 下午11:37:17
 * 本类主要做为
 */
public class OfflinePayOrderConsumerVO extends BasePosVO implements IOrderConsumerVO {

	
	
	protected String consumerId;
	protected String paymentCodeDES;
	protected Long sum;
	protected String operatorId;
	protected String remark;
	protected String appOrderId;
	protected String productName;
	protected Long productPrice;
	protected String productQuantity;
	
	
	
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#getPaintText()
	 */
	@Override
	@JsonIgnore
	public String getPaintText() {
		
		return ValidateUtil.sortbyValues(consumerId,paymentCodeDES,sum.toString(),sellerId,storeId,terminalId,operatorId,remark,appOrderId,terminalOrderId,productName,shiftInfo);
	}


	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#selfvalidate()
	 */
	@Override
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.paymentCodeDES, "支付密码");
		ValidateUtil.assertNull(this.sum, "支付金额");
		ValidateUtil.assertNull(this.terminalId, "终端编码");
//		ValidateUtil.assertNull(this.operatorId, "操作员编码");
//		ValidateUtil.assertNull(this.storeId, "门店编码");
		ValidateUtil.assertNull(this.sellerId, "商户编码");
		ValidateUtil.assertNull(this.terminalOrderId, "订单流水号");
		ValidateUtil.assertNull(this.batchNo, "批次号");
	}

	/**
	 * @return the paymentCodeDES
	 */
	public String getPaymentCodeDES() {
		return paymentCodeDES;
	}

	/**
	 * @param paymentCodeDES the paymentCodeDES to set
	 */
	public void setPaymentCodeDES(String paymentCodeDES) {
		this.paymentCodeDES = paymentCodeDES;
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
	 * @return the sellerId
	 */
	public String getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the operatorId
	 */
	public String getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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

	/**
	 * @param mobileNum the mobileNum to set
	 */
	public void setMobileNum(String mobileNum) {
		this.consumerId = mobileNum;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OfflinePayOrderVO [mobileNum=" + consumerId
				+ ", paymentCodeDES=" + paymentCodeDES + ", sum=" + sum
				+ ", sellerId=" + sellerId + ", storeId=" + storeId
				+ ", terminalId=" + terminalId + ", operatorId=" + operatorId
				+ ", remark=" + remark + ", appOrderId=" + appOrderId
				+ ", productName=" + productName + ", terminalOrderId="
				+ terminalOrderId + ", batchNo=" + batchNo + "]";
	}

	/**
	 * @return the terminalOrderId
	 */
	public String getTerminalOrderId() {
		return terminalOrderId;
	}

	/**
	 * @param terminalOrderId the terminalOrderId to set
	 */
	public void setTerminalOrderId(String terminalOrderId) {
		this.terminalOrderId = terminalOrderId;
	}

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the productPrice
	 */
	public Long getProductPrice() {
		return productPrice;
	}

	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * @return the productQuantity
	 */
	public String getProductQuantity() {
		return productQuantity;
	}

	/**
	 * @param productQuantity the productQuantity to set
	 */
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}

	/**
	 * @return the consumerId
	 */
	public String getConsumerId() {
		return consumerId;
	}

	/**
	 * @param consumerId the consumerId to set
	 */
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}


	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderConsumerVO#getConsumer()
	 */
	@Override
	@JsonIgnore
	public Consumer getConsumer() throws DataInvalidException, MaAccountException {
		
		return ConsumerFactory.getConsumer(consumerId);
	}

}
