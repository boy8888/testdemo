package com.hummingbird.maaccount.vo;

import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.mapper.CashAccountMapper;

public class BaseConsumerVO {
	
    private String accountId;

    private Integer userId;
    private Long balance;
    private String remark;
    private String productId;
    private String consumerType; 
    /**
	 * 构造函数
	 */
	public BaseConsumerVO() {
		super();
	}
    public BaseConsumerVO(DiscountCardAccount account) {
		
		this.accountId = account.getAccountId();
		this.userId = account.getUserId();
		this.balance = account.getBalance();
		this.remark = account.getRemark();
		this.consumerType = "DCA";
		
		
	}
    public BaseConsumerVO(VolumecardAccount account) {
		
		this.accountId = account.getAccountId();
		this.userId = account.getUserId();
		this.balance = account.getBalance();
		this.remark = account.getRemark();
		this.consumerType = "VCA";
		
		
	}

	public BaseConsumerVO(OilcardAccount account) {
		
		this.accountId = account.getAccountId();
		this.userId = account.getUserId();
		this.balance = account.getBalance();
		this.remark = account.getRemark();
		this.consumerType = "OCA";
		
		
	}
	
	public BaseConsumerVO(CashAccount account) {
		
		this.accountId = account.getAccountId();
		this.userId = account.getUserId();
		this.balance = account.getBalance();
		this.remark = account.getRemark();
		productId=null;
		this.consumerType = "CA#";
		
		
	}
	
public BaseConsumerVO(InvestmentAccount account) {
		
		this.accountId = account.getAccountId();
		this.userId = account.getUserId();
		this.balance = account.getBalance();
		this.remark = account.getRemark();
		productId=null;
		this.consumerType = "IA#";
		
		
	}
    
    
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Integer getUserId() {
		return userId;
	}
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
		this.remark = remark;
	}
	
	
	public String getConsumerType() {
		return consumerType;
	}
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
    
}
