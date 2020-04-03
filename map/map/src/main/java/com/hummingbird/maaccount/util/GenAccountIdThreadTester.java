/**
 * 
 * a.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import java.util.Map;

import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;

/**
 * @author john huang
 * 2015年8月26日 下午9:12:12
 * 本类主要做为
 */
public class GenAccountIdThreadTester extends Thread{

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	/**
	 * 1000条
	 */
	int left = 1000;
	
	AccountIdServiceImpl bean;
	
	Map singalmap ;
	
	String productId;
	
	/**
	 * 构造函数
	 */
	public GenAccountIdThreadTester(SpringBeanUtil instance, Map map,String productId,int count) {
		bean = instance.getBean(AccountIdServiceImpl.class);
		singalmap = map;
		left = count;
		this.productId = productId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		if (log.isDebugEnabled()) {
			log.debug(String.format("线程开始"));
		}
		while(left>0){
			left--;
			System.out.println(left);
			String prepareGetAccountId = null;
			try {
				prepareGetAccountId = bean.prepareGetAccountId(productId);
			} catch (MaAccountException e) {
				log.error("获取帐户出错"+e.getMessage());
			}
			if(singalmap.containsKey(prepareGetAccountId)){
				log.error("====================="+prepareGetAccountId+"重复,停止");
				break;
			}
			else{
				singalmap.put(prepareGetAccountId, null);
				
			}
		}
	}
	
	
	
}