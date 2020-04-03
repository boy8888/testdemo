package com.hummingbird.maaccount.vo;

import java.util.Date;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.RealNameAuth;

//查询现金账户余额接口返回数据
public class CashAccountBalanceVO {
	/*{
	    "errcode":0,"errmsg":"查询余额成功",
	    "account":{
	        "mobileNum":"13912345678",
	        "accountId":"139123456781234",
	        "balance":5000
	    }
	}*/
	private String mobileNum;
	private String accountId;
	private Long balance;
	
	public String toString() {
		return "CashAccountBalanceVO [mobileNum=" + mobileNum + ", accountId=" + accountId
				+ ", balance=" + balance  + "]";
	}
	
	public CashAccountBalanceVO(String mobileNum,CashAccount account){
		this.mobileNum = mobileNum;
		accountId=account.getAccountId();
		balance=account.getBalance();
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	
	
}
