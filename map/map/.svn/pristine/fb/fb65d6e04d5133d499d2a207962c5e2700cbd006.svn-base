/**
 * 
 * BaseAccountDao.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.mapper;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;

/**
 * @author huangjiej_2
 * 2014年12月27日 上午1:16:02
 * 本类主要做为基础的帐户信息处理类
 */
public interface DefaultAccountDao {

	/**
	 * 帐户支出，支付时先尝试acout减总额，如果不够减将不会更新成功
	 * @param account
	 * @param order
	 * @return 1-更新成功，0-更新失败，不够减
	 */
	int expense(@Param("account") Account account,@Param("order") Order order);
	
	/**
	 * 帐户收入
	 * @param account
	 * @param order
	 * @return
	 */
	int income(@Param("account") Account account,@Param("order") Order order);
	
	/**
	 * 获取帐户信息
	 * @param mobile
	 * @return
	 */
	Account getAccountByMobile(String mobile );
	/**
	 * 获取帐户信息
	 * @param mobile
	 * @return
	 */
	Account getAccountByAccountId(String accountId );


	/**
	 * 创建帐户
	 * @param ca
	 */
	int createAccount(Account ca);

	/**
	 * 根据userid获取帐户
	 * @param userId
	 * @return
	 */
	Account getAccountByUserId(Integer userId);

	/**
	 * 更新帐户信息
	 * @param account
	 */
	int updateAccount(Account account);
	
}
