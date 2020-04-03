/**
 * 
 * OilcardScheduleService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.schedule;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.mapper.OilcardAccountMapper;
import com.hummingbird.maaccount.service.OilcardAccountService;

/**
 * @author john huang
 * 2015年2月15日 下午12:45:27
 * 本类主要做为分期卡的定时处理类
 */
@Service("oilcardScheduleService")
public class OilcardScheduleService {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	@Autowired
	OilcardAccountService ocaSrv;
	
	@Autowired
	OilcardAccountMapper caDao;
	
	static java.util.concurrent.ThreadPoolExecutor pool = new ThreadPoolExecutor(50,100,30,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(3000));
	
	/**
	 * 定时把分期卡的可用余额移至现金帐户
	 * @throws InterruptedException 
	 */
	public void autoTransFromOilcard2cash() throws InterruptedException{
		if (log.isDebugEnabled()) {
			log.debug(String.format("定时把分期卡的可用余额移至现金帐户开始"));
		}
		StopWatch sw = new StopWatch();
		sw.start();
		try{
//			do{
				if(pool.getActiveCount()!=0){
					//如果未执行完，则停1分钟
					if (log.isDebugEnabled()) {
						log.debug(String.format("本批次还有%s个帐户等待转移",pool.getActiveCount()));
					}
					if(pool.getActiveCount()>500){
						if (log.isDebugEnabled()) {
							log.debug(String.format("本次定时任务中止以等待任务运行完成"));
						}
//						Thread.currentThread().sleep(60*1000);
//						break;
						return;
					}
				}
				//获取所有有可用余额的分期卡帐户
				List<OilcardAccount> selectAllUserableOilcard = caDao.selectAllUseableOilcard();
				if (log.isDebugEnabled()) {
					log.debug(String.format("查询一批次的可转移的帐户，获得记录%s条",selectAllUserableOilcard.size()));
				}
				if(selectAllUserableOilcard.isEmpty())
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("查询不到可转移的帐户数据，任务停止"));
					}
					return ;
				}
				//一个一个转
				for (Iterator iterator = selectAllUserableOilcard.iterator(); iterator
						.hasNext();) {
					OilcardAccount oilcardAccount = (OilcardAccount) iterator
							.next();
					pool.submit(new OilcardTrans2CashCallable(oilcardAccount));
				}
//			}while(true);
			
		}catch(Exception e){
			log.error("定时把分期卡的可用余额移至现金帐户出错",e);
		}
		finally{
			sw.stop();
			if (log.isDebugEnabled()) {
				log.debug(String.format("定时把分期卡的可用余额移至现金帐户完成，耗时%s秒",sw.getTime()/1000));
			}
			
		}
		
	}
	
	
	
}
