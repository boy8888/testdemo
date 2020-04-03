package com.hummingbird.maaccount.vo;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;

/**
 * 折扣卡线上开卡VO
 */
public class DiscountCardOrderVO implements IOrderVO {

	
	 public String ID;
	 public String name;
	 public String mobileNum;
	 public String smsCode;
	 public String channelOrderId;
	 public String channelNo;
	 public String appOrderId;
	 public String productId;
	 public String remark;
	 
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#getPaintText()
	 */
	@JsonIgnore
	@Override
	public String getPaintText() {
		
		return ValidateUtil.sortbyValues(ID,name,mobileNum,channelOrderId,channelNo,appOrderId,remark,productId);
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
	public void setID(String iD) {
		ID = iD;
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
	 * @return the smsCode
	 */
	public String getSmsCode() {
		return smsCode;
	}

	/**
	 * @param smsCode the smsCode to set
	 */
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	/**
	 * @return the channelOrderId
	 */
	public String getChannelOrderId() {
		return channelOrderId;
	}

	/**
	 * @param channelOrderId the channelOrderId to set
	 */
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	/**
	 * @return the channelNo
	 */
	public String getChannelNo() {
		return channelNo;
	}

	/**
	 * @param channelNo the channelNo to set
	 */
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DiscountCardOrderVO [ID=" + ID + ", name=" + name + ", mobileNum="
				+ mobileNum + ", smsCode=" + smsCode + ", channelOrderId="
				+ channelOrderId + ", channelNo=" + channelNo + ", appOrderId="
				+ appOrderId + ", productId=" + productId + ", remark=" + remark
				+ "]";
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.IOrderVO#selfvalidate()
	 */
	@Override
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.ID, "身份证号");
		ValidateUtil.assertNull(this.remark, "备注");
		ValidateUtil.assertNull(this.name, "姓名");
//		ValidateUtil.assertNull(this.smsCode, "短信验证码");
		ValidateUtil.assertNull(this.channelOrderId, "渠道自定义订单号");
		ValidateUtil.assertNull(this.channelNo, "渠道编码");
		ValidateUtil.assertNull(this.appOrderId, "app自定义订单号");
		ValidateUtil.assertNull(this.productId, "折扣卡账户产品编号");
	}



}
