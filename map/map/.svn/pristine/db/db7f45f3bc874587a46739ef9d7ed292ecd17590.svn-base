/**
 * 
 * OilcardTrans2CashCallable.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.schedule;

import java.util.concurrent.Callable;

import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.service.OilcardAccountService;

/**
 * @author john huang
 * 2015年2月15日 下午10:31:31
 * 本类主要做为 分期卡可用余额转至现金帐户线程类
 */
public class OilcardTrans2CashCallable implements Callable<Receipt> {

	private OilcardAccount oc;


	public OilcardTrans2CashCallable(OilcardAccount oilcardAccount)
	{
		this.oc = oilcardAccount;
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Receipt call() throws Exception {
		try{
			
			OilcardAccountService ocaSrv = SpringBeanUtil.getInstance().getBean(OilcardAccountService.class);
			Receipt receipt = ocaSrv.transferOilcard2cashaccount(oc);
			
			
			return receipt;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
