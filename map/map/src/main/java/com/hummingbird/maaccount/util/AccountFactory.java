/**
 * 
 * AccountFactory.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.maaccount.constant.AccountConst;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Consumer;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.AccountService;
import com.hummingbird.maaccount.service.InvestmentAccountService;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午11:13:00
 * 本类主要做为帐户的工具类来使用
 */
public class AccountFactory {
	
	
	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(AccountFactory.class);
	
	public static Map<String,String> accountFlagMap = new HashMap<String,String>(); 
	static{
		accountFlagMap.put(Account.ACCOUNT_CASH, AccountConst.ACCOUNT_TYPE_CASH);
		accountFlagMap.put(Account.ACCOUNT_INVESTMENT, AccountConst.ACCOUNT_TYPE_INVESTMENT);
		accountFlagMap.put(Account.ACCOUNT_BANK, AccountConst.ACCOUNT_TYPE_OTHER);
		accountFlagMap.put(Account.ACCOUNT_APP, AccountConst.ACCOUNT_TYPE_OTHER);
		accountFlagMap.put(Account.ACCOUNT_HUITONGCARD, AccountConst.ACCOUNT_TYPE_OTHER);
		accountFlagMap.put(Account.ACCOUNT_OIL_CARD, AccountConst.ACCOUNT_TYPE_OILCARD);
		accountFlagMap.put(Account.ACCOUNT_DISCOUNT_CARD, AccountConst.ACCOUNT_TYPE_DISCOUNTCARD);
		
	}
	
	/**
	 * 获取订单标识
	 * @param accountType
	 * @return
	 */
	public static String getAccountFlag4Order(String accountType){
		return accountFlagMap.get(accountType);
	}
	

	/**
	 * 获取帐户
	 * @param accountType
	 * @param mobileNum
	 * @return
	 * @throws MaAccountException 
	 */
	public static Account getAccount(String accountType, String mobile) throws MaAccountException {
		
		AccountService accountService = AccountServiceFactory.getAccountService(accountType);
		Account acc = accountService.getAccount(mobile);
		if(acc==null)
		{
			//创建帐户
			if (log.isDebugEnabled()) {
				log.debug(String.format("相关帐户不存在，尝试创建帐户"));
			}
			acc=accountService.createAccountByMobileNum(mobile);
		}
		return acc;
	}
	

	/**
	 * 创建帐户
	 * @param userId
	 * @throws MaAccountException 
	 */
	public static void createAccounts(Integer userId) throws MaAccountException {
		//创建现金帐户
//		try {
			AccountService cashaccService = AccountServiceFactory.getAccountService(Account.ACCOUNT_CASH);
			cashaccService.createAccount(userId);
//		} catch (MaAccountException e) {
//			log.error(String.format(""),e);
//		}
			AccountService inveaccService = AccountServiceFactory.getAccountService(Account.ACCOUNT_INVESTMENT);
			inveaccService.createAccount(userId);
			//此帐户是加油卡产品，所以不能在这里进行创建
			
//			AccountService ocaccService = AccountServiceFactory.getAccountService(Account.ACCOUNT_OIL_CARD);
//			ocaccService.createAccount(userId);
	}


	/**
	 * @param accountInvestment
	 * @param accountId
	 * @return
	 * @throws MaAccountException 
	 */
	public static Account getAccountByAccountId(
			String accountCode, String accountId) throws MaAccountException {
		AccountService accountService = AccountServiceFactory.getAccountService(accountCode);
		Account acc = accountService.getAccountByAccountId(accountId);
		
		return acc;
	}
	
	/**
	 * @param accountInvestment
	 * @param accountId
	 * @return
	 * @throws MaAccountException 
	 */
	public static Account getAccountByConsumer(
			Consumer consumer) throws MaAccountException {
		Account ac;
		switch (consumer.getConsumerType()) {
		case Consumer.CONSUMER_TYPE_MOBILE:
//			//默认取现金帐户
//			UserMapper userDao = SpringBeanUtil.getInstance().getBean(UserMapper.class);
//			User user = userDao.selectByMobile(consumer.getConsumerId());
//			if(user==null)
//			{
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("用户[手机号%s]不存在",consumer.getConsumerId()));
//				}
//				throw new MaAccountException(ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.getErrcode(),"用户不存在");
//			}
//			ac=getAccount(Account.ACCOUNT_CASH,user.getUserId().toString() );
			ac=getAccount(Account.ACCOUNT_CASH, consumer.getConsumerId());
			break;
		case Consumer.CONSUMER_TYPE_CASHACCOUNT:
			ac=getAccountByAccountId(Account.ACCOUNT_CASH, consumer.getConsumerId());
			break;
		case Consumer.CONSUMER_TYPE_INVESTMENTACCOUNT:
			ac=getAccountByAccountId(Account.ACCOUNT_INVESTMENT, consumer.getConsumerId());
			break;
		case Consumer.CONSUMER_TYPE_OILCARD:
			ac=getAccountByAccountId(Account.ACCOUNT_OIL_CARD, consumer.getConsumerId());
			break;
		case Consumer.CONSUMER_TYPE_DISCOUNTCARD:
			ac=getAccountByAccountId(Account.ACCOUNT_DISCOUNT_CARD, consumer.getConsumerId());
			break;
		case Consumer.CONSUMER_TYPE_VOLUMECARD:
			ac=getAccountByAccountId(Account.ACCOUNT_VOLUME_CARD, consumer.getConsumerId());
			break;
			
		default:
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"通过消费号码无法获取帐户");
		}
		return ac;
	}

}
