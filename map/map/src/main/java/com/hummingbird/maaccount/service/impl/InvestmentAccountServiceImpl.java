/**
 * 
 * InvestmentAccountService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.commonbiz.util.NoGenerationUtil;
import com.hummingbird.maaccount.constant.AccountConst;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.InvestmentAccountRemainingOrder;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.AccountOrderDao;
import com.hummingbird.maaccount.mapper.DefaultAccountDao;
import com.hummingbird.maaccount.mapper.InvestmentAccountMapper;
import com.hummingbird.maaccount.mapper.InvestmentAccountObjectOrderMapper;
import com.hummingbird.maaccount.mapper.InvestmentAccountRemainingOrderMapper;
import com.hummingbird.maaccount.service.InvestmentAccountService;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.vo.DefaultReceipt;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午9:52:32
 * 本类主要做为
 */
@Service
public class InvestmentAccountServiceImpl extends DefaultAccountService implements InvestmentAccountService {

	/**
	 * 使用的订单类型,类型值参考本类的静态变量
	 */
	private int orderTarget;
	
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	
	@Autowired
	InvestmentAccountMapper iaDao;
	@Autowired
	InvestmentAccountRemainingOrderMapper iaroDao;
	
	/**
	 * 标的订单
	 */
	@Autowired
	InvestmentAccountObjectOrderMapper iaObjDao;
	
	/**
	 * 剩余金额订单
	 */
	@Autowired
	InvestmentAccountRemainingOrderMapper iaRemDao;
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.AccountService#income(com.hummingbird.maaccount.face.Order)
	 */
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
		int success=0;
		//判断帐户是否投资帐户，是的话进行分别处理
		if (account instanceof InvestmentAccount) {
			InvestmentAccount ia = (InvestmentAccount) account;
			if(ORDER_TARGET_TYPE_OBJECT==orderTarget){
				success = incomeWithObject(order, account);
			}
			else if(ORDER_TARGET_TYPE_REMAINING==orderTarget){
				success = incomeWithRemain(order, account);
			}
			else if(ORDER_TARGET_TYPE_FREEZE ==orderTarget){
				success = incomeWithFrozen(order, account);
			}
		}
		else{
			
			success = getAccountDao().income(account,order);
		}
		if(1==success){
			if(getOrderTarget()==InvestmentAccount.SUM_TARGET_TYPE_NONE){
				if (log.isDebugEnabled()) {
					log.debug(String.format("投资帐户设置不创建订单"));
				}
			}
			//更新订单
			int createOrdersuccess = createOrder(order);
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
			log.debug(String.format("订单收入完成"));
		}
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(order.getOrderId());
		return receipt;
	}

	/**
	 * @param order
	 * @param account
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public int incomeWithFrozen(Order order, Account account) {
		return iaDao.incomeWithFrozen(account, order);
	}

	/**
	 * @param order
	 * @param account
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public int incomeWithRemain(Order order, Account account) {
		return iaDao.incomeWithRemain(account, order);
	}

	/**
	 * @param order
	 * @param account
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public int incomeWithObject(Order order, Account account) {
		return iaDao.incomeWithObject(account, order);
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
		int success=0;
		//判断帐户是否投资帐户，是的话进行分别处理
		if (account instanceof InvestmentAccount) {
			InvestmentAccount ia = (InvestmentAccount) account;
			if(ORDER_TARGET_TYPE_OBJECT==orderTarget){
				success = expenseWithObject(order, account);
			}
			else if(ORDER_TARGET_TYPE_REMAINING==orderTarget){
				success = expenseWithRemain(order, account);
			}
			else if(ORDER_TARGET_TYPE_FREEZE==orderTarget){
				success = expenseWithFrozen(order, account);
			}
		}
		else{
			
			success = getAccountDao().expense(account,order);
		}
		if(1==success){
			//更新订单
			int createOrdersuccess = createOrder(order);
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
	 * @param order
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public int createOrder(Order order) {
		return getAccountOrderDao().createOrder(order);
	}

	/**
	 * @param order
	 * @param account
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public int expenseWithFrozen(Order order, Account account) {
		return iaDao.expenseWithFrozen(account, order);
	}

	/**
	 * @param order
	 * @param account
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public int expenseWithRemain(Order order, Account account) {
		return iaDao.expenseWithRemain(account, order);
	}

	/**
	 * @param order
	 * @param account
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public int expenseWithObject(Order order, Account account) {
		return iaDao.expenseWithObject(account, order);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.impl.DefaultAccountService#getAccountDao()
	 */
	@Override
	public DefaultAccountDao getAccountDao() {
		return iaDao;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.impl.DefaultAccountService#getAccountOrderDao()
	 */
	@Override
	public AccountOrderDao getAccountOrderDao() {
		if(ORDER_TARGET_TYPE_OBJECT==orderTarget){
			return iaObjDao;
		}
		else if(ORDER_TARGET_TYPE_REMAINING==orderTarget){
			
			return iaRemDao;
		}
		else if(ORDER_TARGET_TYPE_FREEZE==orderTarget){
			
			return iaObjDao;
		}
		return null;
	}

	/**
	 * 使用的订单类型,类型值参考本类的静态变量
	 */
	public int getOrderTarget() {
		return orderTarget;
	}

	/**
	 * 使用的订单类型,类型值参考本类的静态变量
	 */
	public void setOrderTarget(int orderTarget) {
		this.orderTarget = orderTarget;
	}
	
	public Account createAccount(Integer userId) throws MaAccountException{
		Account acc = getAccountDao().getAccountByUserId(userId);
		String accountId = null;
		if(acc==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("为用户%s创建投资帐户",userId));
			}
			InvestmentAccount ca = new InvestmentAccount();
			ca.setUserId(userId);
			accountId = accountIdSrv.prepareGetAccountId("9700");
//			accountId = accountIdSrv.prepareGetAccountIdByaccountType(AccountConst.ACCOUNT_TYPE_INVESTMENT);
			ca.setAccountId(accountId);
//			ca.setAccountId(NoGenerationUtil.genNO("inve_"+userId+"_",6));
			ca.setObjectsum(0L);
			ca.setRemainingsum(0L);
			ca.setFrozensum(0L);
			ca.setStatus("OK#");
			AccountValidateUtil.updateAccountSignature(ca);
			try {
				getAccountDao().createAccount(ca);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("创建投资帐户出错"),e);
				}
				//还原帐户
//				accountIdSrv.returnAccountId(accountId);
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"帐户创建失败");
			}
			acc = ca;
		}else{
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户%s投资帐户已创建",userId));
			}
		}
		return acc;
	}

	@Override
	public long onlineRechargeAmount(Map filter) {
		return iaroDao.onlineRechargeAmount(filter);
	}

	@Override
	public long offlineRechargeAmount(Map filter) {
		return iaroDao.offlineRechargeAmount(filter);
	}
	
	@Override
	public long statBillingSum(Map filter) {
		return iaroDao.statBillingSum(filter);
	}

}
