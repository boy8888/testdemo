/**
 * 
 * BasePosVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

/**
 * @author john huang
 * 2015年2月23日 下午10:44:59
 * 本类主要做为
 */
public class BasePosVO {

	protected String sellerId;
	protected String storeId;
	protected String terminalId;
	protected String terminalOrderId;
	protected String batchNo;
	protected String operatorId;
	/**
	 * 班次信息
	 */
	protected String shiftInfo;

	/**
	 * 构造函数
	 */
	public BasePosVO() {
		super();
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasePosVO [sellerId=" + sellerId + ", storeId=" + storeId
				+ ", terminalId=" + terminalId + ", terminalOrderId="
				+ terminalOrderId + ", batchNo=" + batchNo + "]";
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
	 * @return the shiftInfo
	 */
	public String getShiftInfo() {
		return shiftInfo;
	}

	/**
	 * @param shiftInfo the shiftInfo to set
	 */
	public void setShiftInfo(String shiftInfo) {
		this.shiftInfo = shiftInfo;
	}

}