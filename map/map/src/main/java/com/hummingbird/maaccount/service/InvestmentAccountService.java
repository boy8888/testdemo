/**
 * 
 * InvestmentAccountService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service;

import java.util.Map;

import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;

/**
 * @author huangjiej_2
 * 2014年12月29日 下午10:56:12
 * 本类主要做为
 */
public interface InvestmentAccountService extends AccountService {

	/**
	 * @param sumTarget
	 */
	void setOrderTarget(int sumTarget);
	public abstract int expenseWithObject(Order order, Account account);
	public abstract int expenseWithRemain(Order order, Account account);
	public abstract int expenseWithFrozen(Order order, Account account);
	public abstract int incomeWithObject(Order order, Account account);
	public abstract int incomeWithRemain(Order order, Account account);
	public abstract int incomeWithFrozen(Order order, Account account);
	public abstract int createOrder(Order order);
	/**
	 * 使用标的帐户订单
	 */
	public static final int ORDER_TARGET_TYPE_OBJECT=1;
	/**
	 * 使用剩余帐户订单
	 */
	public static final  int ORDER_TARGET_TYPE_REMAINING=2;
	/**
	 * 使用冻结帐户订单
	 */
	public static final  int ORDER_TARGET_TYPE_FREEZE=3;
	/**
	 * 查询在线充值总额
	 */
	public abstract long onlineRechargeAmount(Map filter);
	/**
	 * 查询线下充值总额
	 */
	public abstract long offlineRechargeAmount(Map filter);
	/**
	 * 查询余额订单交易总额
	 */
	public abstract long statBillingSum(Map filter);
}
