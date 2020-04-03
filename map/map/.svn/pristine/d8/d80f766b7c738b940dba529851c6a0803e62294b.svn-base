/**
 * 
 * FactoryTaskService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.FactoryTask;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.mapper.FactoryTaskMapper;
import com.hummingbird.maaccount.vo.FactoryProcessResult;
import com.hummingbird.maaccount.vo.FactoryTaskResult;

/**
 * @author john huang
 * 2015年2月19日 下午9:05:43
 * 本类主要做为
 */
@Service
public class FactoryTaskService {
	
	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	@Autowired
	FactoryTaskMapper taskDao;
	@Autowired
	AccountIdServiceImpl accountIdgenSrv;

	/**
	 * 查询待运行的任务
	 * @return
	 */
	public List<FactoryTask> selectTask4Gen(){
		List<FactoryTask> selectTask4Gen = taskDao.selectTask4Gen();
		return selectTask4Gen;
	}

	/**
	 * @param factoryTask
	 * @return 
	 */
	public FactoryTaskResult generateAccounts(FactoryTask factoryTask) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("创建帐户任务%s开始",factoryTask));
		}
		StopWatch sw = new StopWatch();
		sw.start();
		//调整任务状态为创建中，
		FactoryTaskResult result=new FactoryTaskResult(factoryTask);
		try{
			factoryTask.setStatus("DNG");
			taskDao.updateByPrimaryKey(factoryTask);
			FactoryProcessResult prcres = accountIdgenSrv.generateAccountId(factoryTask);
			factoryTask.setStatus("OK#");
			result.setProcessResult(prcres);
			
		} catch (Exception e) {
			log.error(String.format("创建帐户任务出错"),e);
			factoryTask.setStatus(OrderConst.ORDER_STATUS_FAIL);
			
		}finally{
			
			//调整任务状态为创建完成
			taskDao.updateByPrimaryKey(factoryTask);
		}
		sw.stop();
		if (log.isDebugEnabled()) {
			log.debug(String.format("创建帐户任务完成，耗时%s秒",sw.getTime()/1000));
		}
		return result;
	}
	
	
	
}
