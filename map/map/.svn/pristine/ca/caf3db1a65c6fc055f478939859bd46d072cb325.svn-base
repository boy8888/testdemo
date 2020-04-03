package com.hummingbird.maaccount.entity;


/**
 * 现金帐户订单
 * @author huangjiej_2
 * 2014年12月27日 下午9:24:48
 * 本类主要做为
 */
public class CashAccountOrder extends DefaultOfflinePayOrder<CashAccount> {
    


    private String appalias;

    private String appalias2;
    
    private String payOrderId;


    /**
     * 快照
     */
    private Integer balanceSnapshot;
	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

    public String getAppalias() {
        return appalias;
    }

    public void setAppalias(String appalias) {
        this.appalias = appalias == null ? null : appalias.trim();
    }

    public String getAppalias2() {
        return appalias2;
    }

    public void setAppalias2(String appalias2) {
        this.appalias2 = appalias2 == null ? null : appalias2.trim();
    }

	public CashAccountOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CashAccountOrder [appalias=" + appalias + ", appalias2="
				+ appalias2 + ", payOrderId=" + payOrderId + ", storeId="
				+ storeId + ", operatorId=" + operatorId + ", terminalId="
				+ terminalId + ", terminalOrderId=" + terminalOrderId
				+ ", sellerId=" + sellerId + ", batchNo=" + batchNo
				+ ", productPrice=" + productPrice + ", productQuantity="
				+ productQuantity + ", account=" + account + ", flowDirection="
				+ flowDirection + ", peerAccountType=" + peerAccountType
				+ ", peerAccountId=" + peerAccountId + ", peerAccountUnit="
				+ peerAccountUnit + ", type=" + type + ", externalOrderId="
				+ externalOrderId + ", externalOrderTime=" + externalOrderTime
				+ ", balance=" + balance + "]";
	}

	

    
    
    /**
     * @return 快照
     */
    public Integer getBalanceSnapshot() {
        return balanceSnapshot;
    }

    /**
     * @param balancesnapshot 
	 *            快照
     */
    public void setBalanceSnapshot(Integer balanceSnapshot) {
        this.balanceSnapshot = balanceSnapshot;
    }
}