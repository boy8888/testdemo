package com.hummingbird.maaccount.vo;

import org.codehaus.jackson.annotate.JsonProperty;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;

public class BatchOpenOnlineListVO {
	 /*orders:[{
         "ID":"515411244445444444X", 
         "name":"李四", 
         "mobileNum":"13912345678", 
         "channelOrderId":"channel1234567890",
         "productId":"10",
         "remark":"某某渠道为用户13912345678开卡"}]*/
	private String ID;
	private String name;
	private String mobileNum;
	private String channelOrderId;
	/**
	 * 前置应用id
	 */
	private String appOrderId;
	private String productId;
	private String remark;
	public String getPaintText() {
		
		return ValidateUtil.sortbyValues(ID,name,mobileNum,productId,channelOrderId,remark,appOrderId);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchOpenOnlineListVO [ID=" + ID + ", name=" + name + ", mobileNum=" + mobileNum + ", channelOrderId="
				+ channelOrderId + ", appOrderId=" + appOrderId + ", productId=" + productId + ", remark=" + remark
				+ "]";
	}
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.ID, "身份证号");
		ValidateUtil.assertNull(this.name, "姓名");
		ValidateUtil.assertNull(this.mobileNum, "电话号码");
		ValidateUtil.validateMobile(this.getMobileNum());
		ValidateUtil.assertNull(this.channelOrderId, "渠道自定义订单号");
		ValidateUtil.assertNull(this.productId, "产品编码");
		ValidateUtil.assertNull(this.remark, "备注");
	}
	public String getID() {
		return ID;
	}
	@JsonProperty("ID")
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getChannelOrderId() {
		return channelOrderId;
	}
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
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
	/**
	 * 前置应用id 
	 */
	public String getAppOrderId() {
		return appOrderId;
	}
	/**
	 * 前置应用id 
	 */
	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}
	
}
