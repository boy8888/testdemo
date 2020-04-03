/**
 * 
 * OilcardQueryDetailVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.SignatureUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Consumer;
import com.hummingbird.maaccount.util.ConsumerFactory;

/**
 * @author john huang
 * 2015年2月10日 下午5:57:52
 * 本类主要做为线下查询余额参数
 */
public class OilcardQueryDetailVO implements IOrderConsumerVO {

	protected String storeId;
    protected String operatorId;
    protected String terminalId;
    protected String terminalOrderId;
    protected String sellerId;
    protected String remark;
    protected String consumerId;
    protected String batchNo;
    protected String paymentCodeDES;
	public String getPaymentCodeDES() {
		return paymentCodeDES;
	}
	public void setPaymentCodeDES(String paymentCodeDES) {
		this.paymentCodeDES = paymentCodeDES;
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
	@Override
	public String toString() {
		return "OilcardQueryDetailVO [storeId=" + storeId + ", operatorId="
				+ operatorId + ", terminalId=" + terminalId
				+ ", terminalOrderId=" + terminalOrderId + ", sellerId="
				+ sellerId + ", remark=" + remark + ", consumerId="
				+ consumerId + ", batchNo=" + batchNo + ", paymentCodeDES="
				+ paymentCodeDES + "]";
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#getPaintText()
	 */
	@Override
	public String getPaintText() {
		String mingwen=ValidateUtil.sortbyValues(storeId,operatorId,terminalId,terminalOrderId,sellerId,remark,consumerId,batchNo,paymentCodeDES);
		return mingwen;
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#selfvalidate()
	 */
	@Override
	public void selfvalidate() throws DataInvalidException {
		
		
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
	public Consumer getConsumer() throws DataInvalidException,
			MaAccountException {
		return ConsumerFactory.getConsumer(consumerId);
	}
    
    
    
}
