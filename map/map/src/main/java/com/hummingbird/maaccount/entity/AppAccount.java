/**
 * 
 * AppAccount.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.entity;

import com.hummingbird.maaccount.face.Account;

/**
 * @author huangjiej_2
 * 2014年12月30日 上午12:44:00
 * 本类主要做为 虚拟的app帐户，它不进行任何的操作
 */
public class AppAccount implements Account {

	/**
	 * 构造函数
	 */
	public AppAccount() {
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getAccountCode()
	 */
	@Override
	public String getAccountCode() {
		return Account.ACCOUNT_APP;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getAccountName()
	 */
	@Override
	public String getAccountName() {
		return "app虚拟帐户";
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
		return 999999999L;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getAccountId()
	 */
	@Override
	public String getAccountId() {
		return "app";
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#checkBalanceLimit(long)
	 */
	@Override
	public boolean checkBalanceLimit(long limit) {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#isVirtualAccount()
	 */
	@Override
	public boolean isVirtualAccount() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.face.Account#getUserId()
	 */
	@Override
	public Integer getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBalanceValidateString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSignature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSignature(String signature) {
		// TODO Auto-generated method stub
		
	}

}
