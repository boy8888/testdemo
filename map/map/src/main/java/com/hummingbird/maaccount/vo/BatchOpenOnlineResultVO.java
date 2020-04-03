package com.hummingbird.maaccount.vo;

public class BatchOpenOnlineResultVO {
	/*{
	    "mobile":"139123478",
	    "orderId":"12345678901234567890",
	    "card":{
	        "accountId":"1234123412341234",
	        "amount":400000,
	        "balance":300000,
	        "startTime":"2015-01-01 00:00:00",
	        "endTime":"2015-05-31 24:00:00",
	        "status":"正常"
	    	}
	    }*/
	private String mobile;
	private String orderId;
	private BatchOpenOnlineResultDetailVO card;
	/**
	 * 渠道订单号
	 */
	private String channelOrderId;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BatchOpenOnlineResultDetailVO getCard() {
		return card;
	}
	public void setCard(BatchOpenOnlineResultDetailVO card) {
		this.card = card;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchOpenOnlineResultVO [mobile=" + mobile + ", orderId="
				+ orderId + ", card=" + card + ", channelOrderId="
				+ channelOrderId + "]";
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
	
}
