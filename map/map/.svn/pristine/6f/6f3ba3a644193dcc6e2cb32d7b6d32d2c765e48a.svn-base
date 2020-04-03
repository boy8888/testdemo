package com.hummingbird.maaccount.entity;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.maaccount.face.Account;

public class InvestmentAccount implements Account {
	
	/**
	 * 标记使用哪个帐户金额，金额类型请参考本类的静态变量
	 */
	private int sumTarget;
	
	/**
	 * 标记使用哪个订单，特别对应使用冻结金额时使用，因为冻结金额有时用于标的，有时用于余额，它的值也取用 InvestmentAccount.sumtarget的值
	 */
	private int orderTarget=0;
	
	/**
	 * 使用共同金额
	 */
	public static final int SUM_TARGET_TYPE_BOTH=0;
	/**
	 * 使用标的帐户金额
	 */
	public static final int SUM_TARGET_TYPE_OBJECT=1;
	/**
	 * 使用剩余帐户金额
	 */
	public static final  int SUM_TARGET_TYPE_REMAINING=2;
	/**
	 * 使用冻结帐户金额
	 */
	public static final  int SUM_TARGET_TYPE_FREEZE=3;

	/**
	 * 不生成订单
	 */
	public static final int SUM_TARGET_TYPE_NONE = -1;
	
	
    private Integer userId;

    private String accountId;

    private Long objectsum;
    
    private Long remainingsum;
    
    private String remark;

    private String status;

    private Long frozensum;
    
    private String signature;
    
    
    @Override
	public String getBalanceValidateString() {
    	
    	String str="000000000000000000000000"+frozensum.toString();
    	String frozensum2=str.substring(str.length()-24);
    	
    	
    	return "IA#"+userId+accountId+objectsum+remainingsum+status+frozensum2;
    	
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
    
    public Long getObjectsum() {
        return objectsum;
    }

    public void setObjectsum(Long objectsum) {
        this.objectsum = objectsum;
    }

    public Long getRemainingsum() {
        return remainingsum;
    }

    public void setRemainingsum(Long remainingsum) {
        this.remainingsum = remainingsum;
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
	
	public Long getFrozensum() {
        return frozensum;
    }

    public void setFrozensum(Long frozensum) {
        this.frozensum = frozensum;
    }

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.Account#getAccountCode()
	 */
	public String getAccountCode() {
		return Account.ACCOUNT_INVESTMENT;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.Account#getAccountName()
	 */
	public String getAccountName() {
		return "投资帐户";
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.Account#isEnabled()
	 */
	public boolean isEnabled() {
		return "OK".equalsIgnoreCase(status);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.Account#getBalance()
	 */
	@Override
	public Long getBalance() {
		switch (sumTarget) {
		case SUM_TARGET_TYPE_OBJECT:
			return objectsum;
		case SUM_TARGET_TYPE_REMAINING:
			return remainingsum;
		case SUM_TARGET_TYPE_FREEZE:
			return frozensum;
		}
		//使用共同
		long total =0;
		total+=(objectsum==null?0:objectsum);
		total+=(remainingsum==null?0:remainingsum);
		total+=(frozensum==null?0:frozensum);
		return total;
	}

	

	public int getSumTarget() {
		return sumTarget;
	}

	/**
	 * 标记使用哪个帐户金额，金额类型请参考本类的静态变量
	 */
	public void setSumTarget(int sumTarget) {
		this.sumTarget = sumTarget;
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

	/**
	 * @return the orderTarget
	 */
	public int getOrderTarget() {
		return orderTarget;
	}

	/**
	 * @param orderTarget the orderTarget to set
	 */
	public void setOrderTarget(int orderTarget) {
		this.orderTarget = orderTarget;
	}

	

	@Override
	public String getSignature() {
		// TODO Auto-generated method stub
		return signature;
	}

	@Override
	public void setSignature(String signature) {
		// TODO Auto-generated method stub
		this.signature=signature;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InvestmentAccount [sumTarget=" + sumTarget + ", orderTarget="
				+ orderTarget + ", userId=" + userId + ", accountId="
				+ accountId + ", objectsum=" + objectsum + ", remainingsum="
				+ remainingsum + ", remark=" + remark + ", status=" + status
				+ ", frozensum=" + frozensum + ", signature=" + signature + "]";
	}
}