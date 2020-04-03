/**
 * 
 * AbstractOrder.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.entity;

import java.util.Date;

import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午9:21:01
 * 本类主要为抽象订单
 */
public abstract class AbstractOrder<ACCOUNT extends Account> implements Order<ACCOUNT> {

	private String orderId;

    private String appId;

    private String appname;
    
    private String remark;

    private String originalorderId;
    
    private Date insertTime;

    private Date updateTime;

    private String status;
    
    private String productName;
    
    private long sum;
    
    private String appOrderId;
    
    private String accountId;
    
    private String method;

    private String originaltable;
	
	/**
	 * 帐户
	 */
	protected ACCOUNT account;
	
	/**
	 * 钱流向,IN#流入,OUT流出
	 */
	protected String flowDirection;
	/**
	 * 对方账户类型。CA#-现金账户，IA#-投资账户，TA#第三方账户/其他账户（比如银行卡等）
	 */
	protected String peerAccountType;
	/**
	 * 对方账户
	 */
	protected String peerAccountId;
	/**
	 * 对方的账户发行单位，比如建设银行，如果是营销账户平台管理的账户，填写为“营销账户平台”
	 */
	protected String peerAccountUnit;
	/**
	 * 类型：ZZ#-转账，FK#-付款，TOB-投标，FEB-废标， FB#-付本，SX#-收息，SB#-收本，TX#-提现，CZ#-充值，CZH-冲正
	 */
	protected String type;
	/**
	 * 外部交易流水号，用于线下充值、线下转账时，用于记录外部交易流水号，用于后续的对账和查账
	 */
	protected String externalOrderId;
	/**
	 * 外部交易时间
	 */
	protected Date externalOrderTime;
	
	/**
	 * 结存，即发生该交易后，该账户的结存
	 */
	protected long balance;

	/**
	 * @return the account
	 */
	public ACCOUNT getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(ACCOUNT account) {
		this.account = account;
	}
	
	@Override
	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    @Override
	public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }
    
    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    @Override
	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
	public String getOriginalorderId() {
        return originalorderId;
    }

    public void setOriginalorderId(String originalorderId) {
        this.originalorderId = originalorderId == null ? null : originalorderId.trim();
    }
    
	@Override
	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Override
	public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

	/**
	 * @return the appOrderId
	 */
	@Override
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
	@Override
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

    public String getOriginaltable() {
        return originaltable;
    }

    public void setOriginaltable(String originaltable) {
        this.originaltable = originaltable == null ? null : originaltable.trim();
    }

    /**
	 * 钱流向,IN#流入,OUT流出
	 */
	@Override
	public String getFlowDirection() {
		return flowDirection;
	}

	/**
	 * 钱流向,IN#流入,OUT流出
	 */
	public void setFlowDirection(String flowDirection) {
		this.flowDirection = flowDirection;
	}

	/**
	 * @return the peerAccountType
	 */
	@Override
	public String getPeerAccountType() {
		return peerAccountType;
	}

	/**
	 * @param peerAccountType the peerAccountType to set
	 */
	public void setPeerAccountType(String peerAccountType) {
		this.peerAccountType = peerAccountType;
	}

	/**
	 * @return the peerAccountId
	 */
	@Override
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
	@Override
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
	 * 类型：ZZ#-转账，FK#-付款，TOB-投标，FEB-废标， FB#-付本，SX#-收息，SB#-收本，TX#-提现，CZ#-充值，CZH-冲正
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * 类型：ZZ#-转账，FK#-付款，TOB-投标，FEB-废标， FB#-付本，SX#-收息，SB#-收本，TX#-提现，CZ#-充值，CZH-冲正
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the externalOrderId
	 */
	@Override
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
	@Override
	public Date getExternalOrderTime() {
		return externalOrderTime;
	}

	/**
	 * @param externalOrderTime the externalOrderTime to set
	 */
	public void setExternalOrderTime(Date externalOrderTime) {
		this.externalOrderTime = externalOrderTime;
	}

	/**
	 * @return the balance
	 */
	@Override
	public long getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(long balance) {
		this.balance = balance;
	}
    
}
