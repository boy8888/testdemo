package com.hummingbird.maaccount.entity;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.face.Account;

/**
 * 容量卡账户表
 */

public class VolumecardAccount implements Account{
	private String accountId;

    private Integer userId;

    private Long balance;

    private String remark;

    private String status;

    private Long amount;

    private Date startTime;

    private Date endTime;

    private Date insertTime;

    private Date updateTime;

    private String productId;

    private String channelNo;

    private String signature;
    
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * @param userid 
	 *            用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo == null ? null : channelNo.trim();
    }

	@Override
	public String getAccountCode() {
		return Account.ACCOUNT_VOLUME_CARD;
	}

	@Override
	public String getAccountName() {
		return "容量卡";
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean checkBalanceLimit(long limit) {

		return false;
	}

	@Override
	public boolean isVirtualAccount() {

		
		return false;
	}

	@Override
	public Integer getUserId() {
		
		return userId;
	}

	@Override
	public String getBalanceValidateString() {
		if(startTime!=null)
    		startTime=(DateUtils.truncate(startTime, Calendar.SECOND));
    	if(endTime!=null)
    		endTime=(DateUtils.truncate(endTime, Calendar.SECOND));
    	insertTime=(DateUtils.truncate(insertTime, Calendar.SECOND));
    	updateTime=(DateUtils.truncate(updateTime, Calendar.SECOND));
		return "VCA"+userId+accountId+balance+status+amount+DateUtil.formatCommonDateorNull(startTime)
				+DateUtil.formatCommonDateorNull(endTime)
				+DateUtil.formatCommonDateorNull(insertTime)
				+DateUtil.formatCommonDateorNull(updateTime)+productId+channelNo;
	}

	@Override
	public String getSignature() {
		// TODO Auto-generated method stub
		return signature;
	}

	@Override
	public void setSignature(String signature) {
		this.signature=signature;
		
	}
public static void main(String[] args) {
/*System.out.println(new Date().toString());
	System.out.println(
			
			DateUtil.parseDateToTimestamp(new Date(), "yyyy-MM-dd HH:mm:ss")
			);*/
	System.out.println((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "VolumecardAccount [accountId=" + accountId + ", userId=" + userId
			+ ", balance=" + balance + ", remark=" + remark + ", status="
			+ status + ", amount=" + amount + ", startTime=" + startTime
			+ ", endTime=" + endTime + ", insertTime=" + insertTime
			+ ", updateTime=" + updateTime + ", productId=" + productId
			+ ", channelNo=" + channelNo + ", signature=" + signature + "]";
}
}