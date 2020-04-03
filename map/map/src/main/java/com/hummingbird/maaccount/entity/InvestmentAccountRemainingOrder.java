package com.hummingbird.maaccount.entity;

import java.util.Date;
/**
 * 
 * @author huangjiej_2
 * 2014年12月28日 下午1:12:20
 * 本类主要做为 投资帐户剩余金额订单
 */
public class InvestmentAccountRemainingOrder  extends AbstractOrder<InvestmentAccount> {

    private String appalias;

    private String appalias2;
    
    private String payOrderId;
    
    private Long frozenSumSnapshot;
    
    private Long objectSumSnapshot;


	public Long getFrozenSumSnapshot() {
		return frozenSumSnapshot;
	}

	public void setFrozenSumSnapshot(Long frozenSumSnapshot) {
		this.frozenSumSnapshot = frozenSumSnapshot;
	}

	public Long getObjectSumSnapshot() {
		return objectSumSnapshot;
	}

	public void setObjectSumSnapshot(Long objectSumSnapshot) {
		this.objectSumSnapshot = objectSumSnapshot;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	/**
	 * @return the appalias
	 */
	public String getAppalias() {
		return appalias;
	}

	/**
	 * @param appalias the appalias to set
	 */
	public void setAppalias(String appalias) {
		this.appalias = appalias;
	}

	/**
	 * @return the appalias2
	 */
	public String getAppalias2() {
		return appalias2;
	}

	/**
	 * @param appalias2 the appalias2 to set
	 */
	public void setAppalias2(String appalias2) {
		this.appalias2 = appalias2;
	}
	
	

	@Override
	public String toString() {
		return "InvestmentAccountRemainingOrder [appalias=" + appalias
				+ ", appalias2=" + appalias2 + ", payOrderId=" + payOrderId
				+ ", frozenSumSnapshot=" + frozenSumSnapshot
				+ ", objectSumSnapshot=" + objectSumSnapshot + ", account="
				+ account + ", flowDirection=" + flowDirection
				+ ", peerAccountType=" + peerAccountType + ", peerAccountId="
				+ peerAccountId + ", peerAccountUnit=" + peerAccountUnit
				+ ", type=" + type + ", externalOrderId=" + externalOrderId
				+ ", externalOrderTime=" + externalOrderTime + ", balance="
				+ balance + "]";
	}

}