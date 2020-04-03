package com.hummingbird.maaccount.entity;

import java.util.Date;

public class JifenAccountOrder extends DefaultOfflinePayOrder<JifenAccount>{

    /**
     * APP保留字段，可以从接口传入保存
     */
    private String appAlias;

    /**
     * APP保留字段，可以从接口传入保存
     */
    private String appAlias2;

    private String channelOrderId;

    /**
     * 与此次红包消费关联的订单号
     */
    private String associatedOrderId;
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RedPaperAccountOrder [appalias=" + appAlias + ", appalias2="
				+ appAlias2 + ", channelOrderId=" + channelOrderId
				+ ", storeId=" + storeId + ", operatorId=" + operatorId
				+ ", terminalId=" + terminalId + ", terminalOrderId="
				+ terminalOrderId + ", sellerId=" + sellerId + ", batchNo="
				+ batchNo + ", productPrice=" + productPrice
				+ ", productQuantity=" + productQuantity + ", account="
				+ account + ", flowDirection=" + flowDirection
				+ ", peerAccountType=" + peerAccountType + ", peerAccountId="
				+ peerAccountId + ", peerAccountUnit=" + peerAccountUnit
				+ ", type=" + type + ", externalOrderId=" + externalOrderId
				+ ", externalOrderTime=" + externalOrderTime +", associatedOrderId="
				+ associatedOrderId + ", balance="
				+ balance + "]";
	}
    
	public String getAppAlias() {
		return appAlias;
	}

	public void setAppAlias(String appAlias) {
		this.appAlias = appAlias;
	}

	public String getAppAlias2() {
		return appAlias2;
	}

	public void setAppAlias2(String appAlias2) {
		this.appAlias2 = appAlias2;
	}

	public String getChannelOrderId() {
		return channelOrderId;
	}

	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	public String getAssociatedOrderId() {
		return associatedOrderId;
	}

	public void setAssociatedOrderId(String associatedOrderId) {
		this.associatedOrderId = associatedOrderId;
	}
}