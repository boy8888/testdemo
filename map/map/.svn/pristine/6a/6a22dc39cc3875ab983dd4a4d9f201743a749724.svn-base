package com.hummingbird.maaccount.entity;

import com.hummingbird.maaccount.face.Account;

public class CashAccount implements Account {
    private Integer userId;

    private String accountId;

    private Long balance;

    private String remark;

    private String status;

    private String signature;
    
    @Override
	public String getBalanceValidateString() {
		return "CA#"+userId+accountId+balance+status;
	}
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
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

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.Account#getAccountCode()
	 */
	public String getAccountCode() {
		return Account.ACCOUNT_CASH;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.Account#getAccountName()
	 */
	public String getAccountName() {
		return "现金帐户";
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.Account#isEnabled()
	 */
	public boolean isEnabled() {
		return "OK".equalsIgnoreCase(status);
	}
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#checkBalanceLimit(long)
	 */
	@Override
	public boolean checkBalanceLimit(long limit) {
		return getBalance()>=limit;
	}
	@Override
	public boolean isVirtualAccount() {
		return false;
	}

	

	@Override
	public String getSignature() {
		
		return signature;
	}

	@Override
	public void setSignature(String signature) {
		this.signature=signature;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CashAccount [userId=" + userId + ", accountId=" + accountId
				+ ", balance=" + balance + ", remark=" + remark + ", status="
				+ status + ", signature=" + signature + "]";
	}
}