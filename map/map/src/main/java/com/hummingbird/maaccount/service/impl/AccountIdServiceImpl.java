/**
 * 
 * AccountIdGenerateServiceImpl.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.common.util.LuhnUtils;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.FactoryAccountId;
import com.hummingbird.maaccount.entity.FactoryProcess;
import com.hummingbird.maaccount.entity.FactoryTask;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.mapper.FactoryAccountIdMapper;
import com.hummingbird.maaccount.mapper.FactoryProcessMapper;
import com.hummingbird.maaccount.mapper.FactoryTaskMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.util.GenAccountIdThreadTester;
import com.hummingbird.maaccount.vo.FactoryProcessResult;

/**
 * @author john huang
 * 2015年2月16日 下午11:57:52
 * 本类主要做为帐户Id相关 service，包括生成和使用
 */
@Service
public class AccountIdServiceImpl {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	static int batchcount =10000;//1次只能生成20000条记录
	
	@Autowired
	FactoryProcessMapper processDao;
	@Autowired
	FactoryAccountIdMapper accountDao;
	@Autowired
	ProductMapper productDao;
	@Autowired
	FactoryTaskMapper taskDao;
	
//	/**
//	 * 生成帐户ID
//	 * @param accountType
//	 * @param quantity
//	 * @throws MaAccountException 
//	 */
//	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
//	public FactoryProcessResult generateAccountId(String productId,String unitId,Long quantity) throws MaAccountException{
//		Product product = productDao.selectByPrimaryKey(productId);
//		if(product==null){
//			throw new MaAccountException(MaAccountException.ERR_PRODUCT_EXCEPTION,"产品["+productId+"]");
//		}
//		//获取要创建的帐户进度,根据产品id和发卡机构
//		FactoryProcess process= processDao.selectProcess(productId,unitId);
//		if(process==null)
//		{
//			//创建新的process
//			process = new FactoryProcess();
//			String startstr = productId+unitId+"0000000000000000";
//			startstr = startstr.substring(0,16);
//			process.setCouter(NumberUtils.toLong(startstr));
//			process.setProductId(productId);
//			process.setUnitId(unitId);
//			processDao.insert(process);
//		}
//		//分批处理，1组2w个
//		//因为是数字，有起始位置和结束位置，通过insert into select来生成
//		long start = 1+process.getCouter();
//		long end = quantity +process.getCouter();
//		List<Long> insertlist = new ArrayList(2000);
//		while(quantity>0){
//			insertlist.add(start++);
//			quantity--;
//			
//		}
//		
//		accountDao.createAccounts(process, product, insertlist);
//		//更新进程的最大帐户值
//		process.setCouter(end);
//		processDao.updateByPrimaryKey(process);
//		return new FactoryProcessResult(process,quantity);
//	}

	/**
	 * 根据任务创建帐号（此方法不会修改任务的值）
	 * @param factoryTask
	 * @throws MaAccountException 
	 */
	public FactoryProcessResult generateAccountId(FactoryTask factoryTask) throws MaAccountException {
		
		if(factoryTask.getStatus().equals(OrderConst.ORDER_STATUS_OK)||factoryTask.getStatus().equals(OrderConst.ORDER_STATUS_FAIL)||factoryTask.getStatus().equals("DEL"))
		{
			throw new MaAccountException(1,"任务状态["+factoryTask.getStatus()+"]不正确");
		}
		String productId=factoryTask.getProductId();
		Product product = productDao.selectByPrimaryKey(productId);
		if(product==null){
			throw new MaAccountException(MaAccountException.ERR_PRODUCT_EXCEPTION,"产品["+productId+"]");
		}
		String unitId=factoryTask.getUnitId();
		FactoryProcess process= processDao.selectProcess(productId,unitId);
		//获取要创建的帐户进度,根据产品id和发卡机构
		if(process==null)
		{
			//创建新的process
			process = new FactoryProcess();
			String startstr = productId+unitId+"0000000000000000";
			startstr = startstr.substring(0,15);
			process.setCouter(NumberUtils.toLong(startstr));
			process.setProductId(productId);
			process.setUnitId(unitId);
			processDao.insert(process);
		}
		long amount = factoryTask.getAmount();
		long left=amount-factoryTask.getCounter();
		if(left<=0){
			if (log.isDebugEnabled()) {
				log.debug(String.format("待创建的帐户数<=0，不进行创建"));
			}
			return new FactoryProcessResult(process, 0);
		}
		FactoryProcessResult proresult=null;
		while(left>0){
			long abatch=left>batchcount?batchcount:left;
			proresult=generateAccountId(process,abatch,product);
			left-=abatch;
			factoryTask.setCounter(factoryTask.getCounter()+abatch);
			//更新任务当前进度
			taskDao.updateByPrimaryKey(factoryTask);
		}
		proresult.setQuantity(factoryTask.getCounter());
		return  proresult;
		
	}
	
	/**
	 * 创建一批帐户，帐户不得大于20000
	 * @param process
	 * @param product 
	 * @param abatch
	 * @return 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	private FactoryProcessResult generateAccountId(FactoryProcess process, long batchcount, Product product) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("创建一批次的帐户[%s]%s条",process.getCouter(),batchcount));
		}
		long quantity = batchcount;
		long start = 1+process.getCouter();
		long end = quantity +process.getCouter();
		List<String> insertlist = new ArrayList(AccountIdServiceImpl.batchcount);
		while(quantity>0){
			String cardNum =getCardNoWithCheckNum(String.valueOf(start));
//			int checkNum = LuhnUtils.getCheckNum(String.valueOf(start++));
//			//将检验码和15位卡号组合生成银行卡号，最后一位为检验码
//			String cardNum = String.valueOf(start)+String.valueOf(checkNum);
//			System.out.println("15位生成卡号为--"+start);
//			System.out.println("检验码为--"+checkNum);
//			System.out.println("生成银行卡号为--"+cardNum);
			insertlist.add(cardNum);
			start++;
			quantity--;
		}
		accountDao.createAccounts(process, product, insertlist);
		//更新进程的最大帐户值
		process.setCouter(end);
		processDao.updateByPrimaryKey(process);
		return new FactoryProcessResult(process,quantity);
	}
	
	/**
	 * 生成卡号
	 * @param cardno 卡号应该是15位的
	 * @return
	 */
	protected String getCardNoWithCheckNum(String cardno){
		//调用工具类生成检验码
		int checkNum = LuhnUtils.getCheckNum(cardno);
		return cardno+checkNum;
	}
	

	/**
	 * 第一阶段，根据产品获取一个可用的帐户，如不能则抛出异常
	 * @param accountType
	 * @return
	 * @throws MaAccountException 
	 */
	public String prepareGetAccountId(String productId) throws MaAccountException{
		Map map = new HashMap();
		map.put("productId", productId);
		String accountId=null;
//		String accountId = accountDao.selectUseableAccountIdWithProc(map);
//		accountId = (String) map.get("accountId");
//		if(accountId==null){
//			if (log.isDebugEnabled()) {
//				log.debug(String.format("获取空闲的产品ID为%s的帐户失败，没有空闲帐户",productId));
//			}
//			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"无空闲帐号");
//		}
		
		List<FactoryAccountId> selectUseableAccounts = accountDao.selectUseableAccount(productId);
		for (Iterator iterator = selectUseableAccounts.iterator(); iterator
				.hasNext();) {
			FactoryAccountId factoryAccountId = (FactoryAccountId) iterator.next();
			//使用乐观锁更新
			int updatecount = accountDao.useThisAccount(factoryAccountId);
			if(updatecount==1)
			{
				//获取到帐号
				if (log.isDebugEnabled()) {
					log.debug(String.format("获取到帐号%s",factoryAccountId.getAccountId()));
				}
				accountId=factoryAccountId.getAccountId();
				break;
			}
		}
		if(accountId==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("获取空闲的产品ID为%s的帐户失败，没有空闲帐户",productId));
			}
		}
		
		return accountId;
	}
	/**
	 * 第一阶段，根据帐户类型获取一个可用的帐户，如不能则抛出异常
	 * @param accountType
	 * @return
	 * @throws MaAccountException 
	 */
	public String prepareGetAccountIdByaccountType(String accountType) throws MaAccountException{
		
		String accountId = accountDao.selectUseableAccountIdByAccountType(accountType);
		if(accountId==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("获取空闲的帐户类型为%s的帐户失败，没有空闲帐户",accountType));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"无空闲帐号");
		}
		return accountId;
	}
	
	/**
	 * 归还帐户，主要是取出来后，程序在后续处理时有问题，无法继续，把该帐户还回去，方便后面使用
	 * @param accountId
	 * @return
	 * @throws MaAccountException 
	 */
	public void returnAccountId(String accountId) throws MaAccountException{
		FactoryAccountId fa = new FactoryAccountId();
		fa.setAccountId(accountId);
		fa.setStatus("NUS");
		int updatesuccess = accountDao.updateByPrimaryKeySelective(fa);
		if(updatesuccess!=1){
			log.error(String.format("更新帐户[%s]的状态为NUS，发现没有找到相应的记录",accountId));
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"归还帐户失败");
		}
	}

	/**
	 * @param productId
	 */
	public void testGenAccountId(String productId) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("并发测试获取帐户"));
		}
		Map totalmap = new HashMap();
		int threadcount = 5;
		int accountidcountprethread = 1000;
		for(int i=0;i<threadcount;i++){
			new GenAccountIdThreadTester(SpringBeanUtil.getInstance(),totalmap,productId,accountidcountprethread).start();
		}
		int tempsize = totalmap.size();
		while(totalmap.size()<threadcount*accountidcountprethread){
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(tempsize==totalmap.size()){
				if (log.isDebugEnabled()) {
					log.debug(String.format("总帐户数不再增长,认为完成"));
				}
				break;
			}
			else{
				tempsize = totalmap.size();
				if (log.isDebugEnabled()) {
					log.debug(String.format("目前总帐户数为%s",tempsize));
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("并发测试获取帐户完成"));
		}
	}
	
}


