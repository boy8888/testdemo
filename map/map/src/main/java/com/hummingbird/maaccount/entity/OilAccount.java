package com.hummingbird.maaccount.entity;

import java.util.Date;

import com.hummingbird.maaccount.constant.AccountConst;
import com.hummingbird.maaccount.face.Account;

public class OilAccount implements Account {
    private Integer userId;

    private String accountId;

    private Integer oilsum;

    private String remark;

    private String status;
    
    private String signature;

    private Long amount;
    
    private Long restAmount;
    
    private Long restStages;
    
    private Date startTime;
    
    private Date endTime;
    
    private Date insertTime;
    
    private Date updateTime;
    
    private String productId;
    
    private String channelNo;
    
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

    public Integer getOilsum() {
        return oilsum;
    }

    public void setOilsum(Integer oilsum) {
        this.oilsum = oilsum;
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
    @Override
	public boolean isVirtualAccount() {
		return true;
	}

    
    
	public Long getAmount() {
		return amount;
	}
 
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getRestAmount() {
		return restAmount;
	}

	public void setRestAmount(Long restAmount) {
		this.restAmount = restAmount;
	}

	public Long getRestStages() {
		return restStages;
	}

	public void setRestStages(Long restStages) {
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
		this.productId = productId;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getAccountCode()
	 */
	@Override
	public String getAccountCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getAccountName()
	 */
	@Override
	public String getAccountName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getBalance()
	 */
	@Override
	public Long getBalance() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#checkBalanceLimit(long)
	 */
	@Override
	public boolean checkBalanceLimit(long limit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getBalanceValidateString() {
		return "OCA"+userId+accountId+status+amount+restAmount+restStages+startTime+endTime+insertTime+updateTime+productId+channelNo;
	}

	@Override
	public String getSignature() {
		
		return signature;
	}

	@Override
	public void setSignature(String signature) {
		this.signature = signature;
		
	}
	
}