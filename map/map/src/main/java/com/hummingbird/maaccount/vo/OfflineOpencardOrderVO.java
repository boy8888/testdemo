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
public class OfflineOpencardOrderVO extends BasePosVO implements IOrderVO {

	protected String mobileNum;
	protected String name;
	protected String remark;
	protected String appOrderId;
	protected String productId;
	protected String ID; 
	/**
	 * 通过金额判断是否有做拆单 
	 */
	protected Long paySum;
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#getPaintText()
	 */
	@Override
	@JsonIgnore
	public String getPaintText() {
		
		return ValidateUtil.sortbyValues(mobileNum,ID,name,sellerId,storeId,terminalId,remark,appOrderId,terminalOrderId,productId,batchNo,paySum!=null?paySum.toString():null,operatorId);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#getMobileNum()
	 */
	@Override
	public String getMobileNum() {
		return mobileNum;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#selfvalidate()
	 */
	@Override
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.terminalId, "终端编码");
//		ValidateUtil.assertNull(this.operatorId, "操作员编码");
//		ValidateUtil.assertNull(this.storeId, "门店编码");
		ValidateUtil.assertNull(this.sellerId, "商户编码");
		ValidateUtil.assertNull(this.batchNo, "批次号");
		ValidateUtil.assertNull(this.terminalOrderId, "订单流水号");
		ValidateUtil.assertNull(this.productId, "油卡账户产品类型");
//		ValidateUtil.assertNull(this.name, "姓名");
		ValidateUtil.assertNull(this.ID, "身份证号");
		ValidateUtil.assertNull(this.remark, "备注");
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
		this.mobileNum = mobileNum;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	@JsonProperty("ID")
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * 通过金额判断是否有做拆单
	 */
	public Long getPaySum() {
		return paySum;
	}

	/**
	 * 通过金额判断是否有做拆单
	 */
	public void setPaySum(Long paySum) {
		this.paySum = paySum;
	}

	@Override
	public String toString() {
		return "OfflineOpencardOrderVO [mobileNum=" + mobileNum + ", name="
				+ name + ", remark=" + remark + ", appOrderId=" + appOrderId
				+ ", productId=" + productId + ", ID=" + ID + ", paySum="
				+ paySum + ", sellerId=" + sellerId + ", storeId=" + storeId
				+ ", terminalId=" + terminalId + ", terminalOrderId="
				+ terminalOrderId + ", batchNo=" + batchNo + ", operatorId="
				+ operatorId + "]";
	}


}
