/**
 * 
 * DefaultAccountService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.AccountOrderDao;
import com.hummingbird.maaccount.mapper.DefaultAccountDao;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.AccountService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.vo.DefaultReceipt;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午9:39:29
 * 本类主要做为
 */
public class DefaultAccountService implements AccountService {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	@Autowired
	UserMapper userDao; 
	
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.AccountService#income(com.hummingbird.maaccount.face.Order)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt income(Order order)  throws MaAccountException{
		
		if (log.isDebugEnabled()) {
			log.debug(String.format("订单收入开始"));
		}
		Account account = order.getAccount();
		int success = getAccountDao().income(account,order);
		if(1==success){
			//更新订单
			int createOrdersuccess = getAccountOrderDao().createOrder(order);
			if(createOrdersuccess==1){
				
			}
			else{
				
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("订单收入完成"));
		}
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(order.getOrderId());
		return receipt;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.AccountService#expense(com.hummingbird.maaccount.face.Order)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt expense(Order order) throws MaAccountException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("订单支出开始"));
		}
		Account account = order.getAccount();
		int success = getAccountDao().expense(account,order);
		if(1==success){
			//更新订单
			int createOrdersuccess = getAccountOrderDao().createOrder(order);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单支出，从帐户扣减金额不成功，帐户余额不足"));
			}
			throw new InsufficientAccountBalanceException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"帐户余额不足");
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("订单支出完成"));
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(order.getOrderId());
		receipt.setDealtime(order.getInsertTime());
		return receipt;
	}
	
	/**
	 * 帐户dao
	 * @return
	 */
	public DefaultAccountDao getAccountDao(){
		return null;
	}
	
	/**
	 * 订单dao
	 * @return
	 */
	public AccountOrderDao getAccountOrderDao(){
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.AccountService#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String mobileNum) {
		Account acc = getAccountDao().getAccountByMobile(mobileNum);
		return acc;
	}
	
	/**
	 * 更新帐户
	 * @param account
	 */
	@Override
	public boolean updateAccount(Account account){
		int updatesuccess = getAccountDao().updateAccount(account);
		return 1==updatesuccess;
	}

	/**
	 * 创建帐户
	 * @see com.hummingbird.maaccount.service.AccountService#createAccount(java.lang.Integer)
	 */
	@Override
	public Account createAccount(Integer userId)  throws MaAccountException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("创建帐户（虚拟帐户）默认方法，不处理"));
		}
		return null;
	}
	
	/**
	 * 根据帐户id查询帐户
	 * @param accountId
	 * @return
	 * @throws MaAccountException 
	 */
	public Account getAccountByAccountId(String accountId) throws MaAccountException{
		return getAccountDao().getAccountByAccountId(accountId);
	}
	
	/**
	 * 创建帐户
	 * @param mobile 手机号
	 */
	public Account createAccountByMobileNum(String mobileNum)  throws MaAccountException{
		User user = userDao.selectByMobile(mobileNum);
		if(user==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户%s不存在",mobileNum));
			}
			throw new MaAccountException(MaAccountException.ERR_USER_EXCEPTION,String.format("用户%s不存在",mobileNum));
		}
		return createAccount(user.getUserId());
	}

}
