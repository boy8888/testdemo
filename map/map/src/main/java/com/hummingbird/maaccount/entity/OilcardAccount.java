package com.hummingbird.maaccount.entity;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.face.Account;

public class OilcardAccount implements Account{
	
	
	private Integer userId;

    private String accountId;

    private Long balance;

    private String remark;

    private String status;

    private Long amount;

    private String signature;
    
    /**
     * 剩余总额
     */
    private Long restAmount;

    /**
     * 剩余期数
     */
    private Integer restStages;

    private Date startTime;

    private Date endTime;

    private Date insertTime;

    private Date updateTime;

    private String productId;
    
    /**
     * 分销渠道编号
     */
    private String channelNo;
    
    /**
     * 初始金额返还标志,0-未返还,1-已返还
     */
    private int initSumReturned;

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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    /**
     * 剩余总额
     */
    public Long getRestAmount() {
        return restAmount;
    }
    /**
     * 剩余总额
     */
    public void setRestAmount(Long restAmount) {
        this.restAmount = restAmount;
    }
    /**
     * 剩余期数
     */
    public Integer getRestStages() {
        return restStages;
    }
    /**
     * 剩余期数
     */
    public void setRestStages(Integer restStages) {
        this.restStages = restStages;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getAccountCode()
	 */
	@Override
	public String getAccountCode() {
		return Account.ACCOUNT_OIL_CARD;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getAccountName()
	 */
	@Override
	public String getAccountName() {
		return "分期卡";
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getBalance()
	 */
	@Override
	public Long getBalance() {
		return balance;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#checkBalanceLimit(long)
	 */
	@Override
	public boolean checkBalanceLimit(long limit) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#isVirtualAccount()
	 */
	@Override
	public boolean isVirtualAccount() {
		return false;
	}

	/**
     * 分销渠道编号
     */
	public String getChannelNo() {
		return channelNo;
	}

	/**
     * 分销渠道编号
     */
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OilcardAccount [userId=" + userId + ", accountId=" + accountId
				+ ", balance=" + balance + ", remark=" + remark + ", status="
				+ status + ", amount=" + amount + ", signature=" + signature
				+ ", restAmount=" + restAmount + ", restStages=" + restStages
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", insertTime=" + insertTime + ", updateTime=" + updateTime
				+ ", productId=" + productId + ", channelNo=" + channelNo
				+ ", initSumReturned=" + initSumReturned + "]";
	}

	@Override
	public String getBalanceValidateString() {
		if(startTime!=null)
    		startTime=(DateUtils.truncate(startTime, Calendar.SECOND));
    	if(endTime!=null)
    		endTime=(DateUtils.truncate(endTime, Calendar.SECOND));
    	insertTime=(DateUtils.truncate(insertTime, Calendar.SECOND));
    	updateTime=(DateUtils.truncate(updateTime, Calendar.SECOND));
		return "OCA"+userId+accountId+status+balance+amount+restAmount+restStages
				+DateUtil.formatCommonDateorNull(startTime)
				+DateUtil.formatCommonDateorNull(endTime)
				+DateUtil.formatCommonDateorNull(insertTime)
				+DateUtil.formatCommonDateorNull(updateTime)+productId+channelNo+initSumReturned;
	}

	@Override
	public String getSignature() {
		return signature;
	}

	@Override
	public void setSignature(String signature) {
		this.signature=signature;
	}

	 /**
     * 初始金额返还标志,0-未返还,1-已返还
     */
	public int getInitSumReturned() {
		return initSumReturned;
	}

	 /**
     * 初始金额返还标志,0-未返还,1-已返还
     */
	public void setInitSumReturned(int initSumReturned) {
		this.initSumReturned = initSumReturned;
	}
}