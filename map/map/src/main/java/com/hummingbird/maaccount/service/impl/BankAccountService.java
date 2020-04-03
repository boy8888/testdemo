/**
 * 
 * AppAccountService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import com.hummingbird.maaccount.entity.AppAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;

/**
 * @author huangjiej_2
 * 2014年12月30日 上午12:45:49
 * 本类主要做为 银行帐户的service，不做任何事
 */
public class BankAccountService extends DefaultAccountService {

	/**
	 * 构造函数
	 */
	public BankAccountService() {
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.impl.DefaultAccountService#income(com.hummingbird.maaccount.face.Order)
	 */
	@Override
	public Receipt income(Order order) throws MaAccountException {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.impl.DefaultAccountService#expense(com.hummingbird.maaccount.face.Order)
	 */
	@Override
	public Receipt expense(Order order) throws MaAccountException {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.impl.DefaultAccountService#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String mobileNum) {
		return new AppAccount();
	}
	
	

}
