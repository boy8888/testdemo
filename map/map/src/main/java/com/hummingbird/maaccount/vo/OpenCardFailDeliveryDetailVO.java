/**
 * 
 */
package com.hummingbird.maaccount.vo;


/**
 * @author huangjiej_2
 * 开卡交割失败数据
 */
public class OpenCardFailDeliveryDetailVO extends OfflineOpencardOrderVO {

	//交易id|卡号|金额|流水号|参考号|商户号|终端号|批次号|交易日期|交易时间|身份证号|手机号|推荐人id|产品编号
	
	protected String payTime;

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	@Override
	public String toString() {
		return "OpenCardFailDeliveryDetailVO [payTime=" + payTime
				+ ", mobileNum=" + mobileNum + ", name=" + name + ", remark="
				+ remark + ", appOrderId=" + appOrderId + ", productId="
				+ productId + ", ID=" + ID + ", paySum=" + paySum
				+ ", sellerId=" + sellerId + ", storeId=" + storeId
				+ ", terminalId=" + terminalId + ", terminalOrderId="
				+ terminalOrderId + ", batchNo=" + batchNo + ", operatorId="
				+ operatorId + "]";
	}

	
	
	
}
