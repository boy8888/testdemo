package com.hummingbird.maaccount.vo;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;

/**
 * @author
 * 2015年3月26日 上午9:50:31
 * 本类主要做为 容量卡
 */
public class VolumecardOrderVO implements IOrderVO{
	
	 public String ID;
	 public String name;
	 public String mobileNum;
	 public String smsCode;
	 public String channelOrderId;
	 public String channelNo;
	 public String appOrderId;
	 public String productId;
	 public String remark;

	 
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getChannelOrderId() {
		return channelOrderId;
	}

	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getAppOrderId() {
		return appOrderId;
	}

	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	@Override
	public String getPaintText() {
		return ValidateUtil.sortbyValues(ID,name,mobileNum,channelOrderId,channelNo,appOrderId,remark,productId);
	}

	@Override
	public String getMobileNum() {
		return mobileNum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VolumecardOrderVO [ID=" + ID + ", name=" + name + ", mobileNum="
				+ mobileNum + ", smsCode=" + smsCode + ", channelOrderId="
				+ channelOrderId + ", channelNo=" + channelNo + ", appOrderId="
				+ appOrderId + ", productId=" + productId + ", remark=" + remark
				+ "]";
	}
	
	@Override
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.ID, "身份证号");
		ValidateUtil.assertNull(this.remark, "备注");
		ValidateUtil.assertNull(this.name, "姓名");
//		ValidateUtil.assertNull(this.smsCode, "短信验证码");
		ValidateUtil.assertNull(this.channelOrderId, "渠道自定义订单号");
		ValidateUtil.assertNull(this.channelNo, "渠道编码");
		ValidateUtil.assertNull(this.appOrderId, "app自定义订单号");
		ValidateUtil.assertNull(this.productId, "容量卡账户产品编号");
		
	}

}
