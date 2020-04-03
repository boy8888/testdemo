/**
 * 
 * AccountIdGenerateScheduleService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.schedule;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.entity.FactoryTask;
import com.hummingbird.maaccount.mapper.FactoryTaskMapper;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;
import com.hummingbird.maaccount.service.impl.FactoryTaskService;

/**
 * @author john huang
 * 2015年2月16日 下午10:08:23
 * 本类主要做为 帐号ID产生器
 */
@Service("accountIdGen")
public class AccountIdGenerateScheduleService {

	
	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	@Autowired
	FactoryTaskService taskSrv;
	@Autowired
	AccountIdServiceImpl accountIdgenSrv;
	
	
	/**
	 * 预创建帐户ID信息
	 */
	public void genAccountId(){
		if (log.isDebugEnabled()) {
			log.debug(String.format("预创建帐户定时任务开始"));
		}
		StopWatch sw = new StopWatch();
		sw.start();
		//查询任务表的内容，来进行预生成帐号
		List<FactoryTask> tasks = taskSrv.selectTask4Gen();
		if (log.isDebugEnabled()) {
			log.debug(String.format("有%s项任务待处理",tasks.size()));
		}
		for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
			FactoryTask factoryTask = (FactoryTask) iterator.next();
			taskSrv.generateAccounts(factoryTask);
		}
		sw.stop();
		if (log.isDebugEnabled()) {
			log.debug(String.format("预创建帐户定时任务完成,耗时%s秒",sw.getTime()/1000));
		}
		
	}
	
	
	
}
