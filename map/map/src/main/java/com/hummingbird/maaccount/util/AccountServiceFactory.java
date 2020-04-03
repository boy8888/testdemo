/**
 * 
 * AccountServiceFactory.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.service.AccountService;
import com.hummingbird.maaccount.service.CashAccountService;
import com.hummingbird.maaccount.service.DiscountCardAccountService;
import com.hummingbird.maaccount.service.InvestmentAccountService;
import com.hummingbird.maaccount.service.OilcardAccountService;
import com.hummingbird.maaccount.service.VolumecardAccountService;
import com.hummingbird.maaccount.service.impl.AppAccountService;
import com.hummingbird.maaccount.service.impl.BankAccountService;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午9:31:42
 * 本类主要做为帐户服务类工厂
 */
public class AccountServiceFactory {

	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(AccountServiceFactory.class);
	
	
	/**
	 * 根据代号获取对应的帐户service
	 * @param accountcode
	 * @return
	 * @throws MaAccountException
	 */
	public static AccountService getAccountService(String accountcode) throws MaAccountException {
		AccountService as=null;
		switch (accountcode) {
		case Account.ACCOUNT_CASH:
			as= SpringBeanUtil.getInstance().getBean(CashAccountService.class );
			break;
		case Account.ACCOUNT_INVESTMENT:
			InvestmentAccountService accountService = SpringBeanUtil.getInstance().getBean(InvestmentAccountService.class);
			as=accountService;
			break;
		case Account.ACCOUNT_APP:
			as=new AppAccountService();
			break;
		case Account.ACCOUNT_BANK:
			as=new BankAccountService();
			break;
		case Account.ACCOUNT_DISCOUNT_CARD:
			as= SpringBeanUtil.getInstance().getBean(DiscountCardAccountService.class);
			break;
		case Account.ACCOUNT_OIL_CARD:
			as=  SpringBeanUtil.getInstance().getBean(OilcardAccountService.class);
			break;
		case Account.ACCOUNT_VOLUME_CARD:
			as=  SpringBeanUtil.getInstance().getBean(VolumecardAccountService.class);
			break;
			
			
//		case Account.ACCOUNT_OIL_CARD:
//			as= SpringBeanUtil.getInstance().getBean(OilcardAccountService.class );
//			break;
		default:
			//as= SpringBeanUtil.getInstance().getBean(DefaultAccountService.class);
			log.error("创建AccountServicer失败，不支持的帐户类型:"+accountcode);
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"创建Service失败，不支持的帐户类型");
		}
		
		
		return as;
	}
	
	
	/**
	 * 获取相关的帐户Service
	 * @param fromaccount
	 * @return
	 * @throws MaAccountException 
	 */
	public static AccountService getAccountService(Account fromaccount) throws MaAccountException {
		if(fromaccount==null){
			log.error("帐户为空，无法创建AccountService");
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"创建Service失败，帐户为空");
		}
		AccountService accountService = getAccountService(fromaccount.getAccountCode());
		if ( Account.ACCOUNT_INVESTMENT.equals(fromaccount.getAccountCode())) {
			InvestmentAccountService investaccSrv = (InvestmentAccountService) accountService;
			investaccSrv.setOrderTarget(((InvestmentAccount)fromaccount).getSumTarget());
			if (fromaccount instanceof InvestmentAccount) {
				InvestmentAccount ia = (InvestmentAccount) fromaccount;
				if(ia.getOrderTarget()==InvestmentAccount.SUM_TARGET_TYPE_REMAINING){
					investaccSrv.setOrderTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
				}
				else if(ia.getOrderTarget()==InvestmentAccount.SUM_TARGET_TYPE_OBJECT){
					investaccSrv.setOrderTarget(InvestmentAccount.SUM_TARGET_TYPE_OBJECT);
				}
			}
		}
		
		return accountService;
	}

	
	
	
}
