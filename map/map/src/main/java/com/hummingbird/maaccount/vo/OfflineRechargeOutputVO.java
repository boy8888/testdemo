/**
 * 
 * OfflineRechargeOutputVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.Date;

import com.hummingbird.maaccount.entity.OfflineRecharge;

/**
 * @author huangjiej_2
 * 2015年1月28日 下午9:57:15
 * 本类主要做为充值分页显示类
 */
public class OfflineRechargeOutputVO {

	String methodName;
	
	private String orderId;

    private String appId;

    private String appName;
    
    private String remark;

    private String insertTime;


    private String status;
    
    private long sum;
    
    private String appOrderId;
    
    private String accountId;
    
    /**
     * 对应帐户的订单
     */
    private String accountOrderId;
    
    private String method;

	/**
	 * 对方账户
	 */
	protected String peerAccountId;
	/**
	 * 对方的账户发行单位，比如建设银行，如果是营销账户平台管理的账户，填写为“营销账户平台”
	 */
	protected String peerAccountUnit;
	/**
	 * 外部交易流水号，用于线下充值、线下转账时，用于记录外部交易流水号，用于后续的对账和查账
	 */
	protected String externalOrderId;
	/**
	 * 外部交易时间
	 */
	protected String externalOrderTime;
	
	private String payOrderId;
	private String rechargeType="线下充值";
	

	public String getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    
	public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }
    
    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    
	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
	
	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    
	public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
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
	 * @return the accountId
	 */
	
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }


	/**
	 * @return the peerAccountId
	 */
	
	public String getPeerAccountId() {
		return peerAccountId;
	}

	/**
	 * @param peerAccountId the peerAccountId to set
	 */
	public void setPeerAccountId(String peerAccountId) {
		this.peerAccountId = peerAccountId;
	}

	/**
	 * @return the peerAccountUnit
	 */
	
	public String getPeerAccountUnit() {
		return peerAccountUnit;
	}

	/**
	 * @param peerAccountUnit the peerAccountUnit to set
	 */
	public void setPeerAccountUnit(String peerAccountUnit) {
		this.peerAccountUnit = peerAccountUnit;
	}


	/**
	 * @return the externalOrderId
	 */
	
	public String getExternalOrderId() {
		return externalOrderId;
	}

	/**
	 * @param externalOrderId the externalOrderId to set
	 */
	public void setExternalOrderId(String externalOrderId) {
		this.externalOrderId = externalOrderId;
	}

	/**
	 * @return the externalOrderTime
	 */
	
	public String getExternalOrderTime() {
		return externalOrderTime;
	}

	/**
	 * @param externalOrderTime the externalOrderTime to set
	 */
	public void setExternalOrderTime(String externalOrderTime) {
		this.externalOrderTime = externalOrderTime;
	}

	/**
	 * @return the 账户订单号
	 */
	public String getAccountOrderId() {
		return accountOrderId;
	}

	/**
	 * @param 账户订单号 to set
	 */
	public void setAccountOrderId(String accountOrderId) {
		this.accountOrderId = accountOrderId;
	}
	

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OfflineRechargeOutputVO [methodName=" + methodName
				+ ", accountId=" + accountId + ", peerAccountId=" + peerAccountId + ", peerAccountUnit="
				+ peerAccountUnit + ", externalOrderId=" + externalOrderId
				+ ", externalOrderTime=" + externalOrderTime + "]";
	}

	
	
	
}
