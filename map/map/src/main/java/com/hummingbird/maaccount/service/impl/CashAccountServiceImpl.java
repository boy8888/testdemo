/**
 * 
 * InvestmentAccountService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.commonbiz.util.NoGenerationUtil;
import com.hummingbird.maaccount.constant.AccountConst;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.AccountOrderDao;
import com.hummingbird.maaccount.mapper.CashAccountMapper;
import com.hummingbird.maaccount.mapper.CashAccountOrderMapper;
import com.hummingbird.maaccount.mapper.DefaultAccountDao;
import com.hummingbird.maaccount.service.CashAccountService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午9:52:32
 * 本类主要做为 现金帐户service
 */
@Service
public class CashAccountServiceImpl extends DefaultAccountService implements CashAccountService {

	
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	
	@Autowired
	CashAccountMapper caDao;
	
	/**
	 * 现金订单
	 */
	@Autowired
	CashAccountOrderMapper caorderDao;
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.AccountService#income(com.hummingbird.maaccount.face.Order)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt income(Order order) throws MaAccountException {
		return super.income(order);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.AccountService#expense(com.hummingbird.maaccount.face.Order)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt expense(Order order) throws MaAccountException {
		return super.expense(order);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.impl.DefaultAccountService#getAccountDao()
	 */
	@Override
	public DefaultAccountDao getAccountDao() {
		return caDao;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.impl.DefaultAccountService#getAccountOrderDao()
	 */
	@Override
	public AccountOrderDao getAccountOrderDao() {
		return caorderDao;
	}
	
	/**
	 * 创建现金帐户
	 * @throws MaAccountException 
	 * @see com.hummingbird.maaccount.service.impl.DefaultAccountService#createAccount(java.lang.Integer)
	 */
	public Account createAccount(Integer userId) throws MaAccountException{
		Account acc = getAccountDao().getAccountByUserId(userId);
		String accountId=null;
		if(acc==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("为用户%s创建现金帐户",userId));
			}
			CashAccount ca = new CashAccount();
			ca.setUserId(userId);
			accountId = accountIdSrv.prepareGetAccountId("9500");
			//accountId = accountIdSrv.prepareGetAccountIdByaccountType(AccountConst.ACCOUNT_TYPE_CASH);
			ca.setAccountId(accountId);
//			ca.setAccountId(NoGenerationUtil.genNO("cash_"+userId+"_",6));
			ca.setBalance(0L);
			ca.setStatus("OK#");
			AccountValidateUtil.updateAccountSignature(ca);
			try {
				getAccountDao().createAccount(ca);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("创建现金帐户出错"),e);
				}
				//还原帐户
//				accountIdSrv.returnAccountId(accountId);
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"帐户创建失败");
			}
			acc = ca;
		}else{
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户%s现金帐户已创建",userId));
			}
		}
		
		return acc;
	}

	
	public void open(CashAccount cashAccount) throws MaAccountException{
		
		
		int updateAccountsuccess =caDao.updateAccount(cashAccount);
		if(updateAccountsuccess==1){
			if (log.isDebugEnabled()) {
				log.debug(String.format("现金账户开通成功"));
			}
		}
		else{
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"现金账户开通失败");
		}
	}

}
