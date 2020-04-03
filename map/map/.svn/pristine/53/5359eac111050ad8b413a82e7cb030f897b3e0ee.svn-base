package com.hummingbird.maaccount.entity;

import java.util.Date;

import com.hummingbird.maaccount.face.Account;

/**
 * 红包账户表
 */
public class RedPaperAccount implements Account{
	/**
     * 周期结束
     */
    public static final String STATUS_END = "END";
    /**
     * 正常
     */
    public static final String STATUS_OK = "OK#";
    /**
   
    /**
     * 红包编号
     */
    private String accountId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * OK#-正常，END-生命周期结束
     */
    private String status;

    /**
     * 面额，单位为分
     */
    private Long amount;

    /**
     * 有效期-开始时间
     */
    private Date startTime;

    /**
     * 有效期-结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date insertTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String productId;

    /**
     * 防篡改签名值
     */
    private String signature;
    
    private String activity;

    /**
     * @return 红包编号
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountid 
	 *            红包编号
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

   

    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return OK#-正常，END-生命周期结束
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            OK#-正常，END-生命周期结束
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 面额，单位为分
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            面额，单位为分
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * @return 有效期-开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param starttime 
	 *            有效期-开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return 有效期-结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endtime 
	 *            有效期-结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return 创建时间
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * @param inserttime 
	 *            创建时间
     */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updatetime 
	 *            更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * @return 防篡改签名值
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature 
	 *            防篡改签名值
     */
    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

	@Override
	public String getAccountCode() {
		return Account.ACCOUNT_REDPAPAER;
	}

	@Override
	public String getAccountName() {
		
		return "红包";
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Long getBalance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkBalanceLimit(long limit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVirtualAccount() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getBalanceValidateString() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RedPaperAccount [accountId=" + accountId + ", userId=" + userId
				+ ", remark=" + remark + ", status=" + status + ", amount="
				+ amount + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", insertTime=" + insertTime + ", updateTime=" + updateTime
				+ ", productId=" + productId + ", signature=" + signature + "]";
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
    
    
    
    
    
}