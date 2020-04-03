/**
 * 
 * AccountIdGenerateServiceImplTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 *//*
package com.hummingbird.maaccount.service.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.hummingbird.common.util.LuhnUtils;
import com.hummingbird.maaccount.entity.FactoryProcess;
import com.hummingbird.maaccount.entity.FactoryTask;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.mapper.FactoryProcessMapper;
import com.hummingbird.maaccount.mapper.FactoryTaskMapper;
import com.hummingbird.test.service.BaseTestService;

*//**
 * @author john huang
 * 2015年2月19日 下午9:30:15
 * 本类主要做为
 *//*
@ContextConfiguration({"classpath:applicationContext.xml","classpath:dataSource.xml"}) 
public class AccountIdGenerateServiceImplTest extends BaseTestService {

	@Autowired
	FactoryTaskService srv;
	@Autowired
	FactoryTaskMapper taskDao;
	@Autowired
	FactoryProcessMapper processDao;
	
	*//**
	 * @throws java.lang.Exception
	 *//*
	@Before
	public void setUp() throws Exception {
	}

	*//**
	 * @throws java.lang.Exception
	 *//*
	@After
	public void tearDown() throws Exception {
	}

	*//**
	 * Test method for {@link com.hummingbird.maaccount.service.impl.AccountIdServiceImpl#generateAccountId(java.lang.String, java.lang.String, int)}.
	 *//*
	@Test
	public void testGenerateAccountIdStringStringInt() {
		//添加一个任务
		jdbcTemplate.execute("insert into t_product(productid,productname,accounttype,productPrice,insertTime) values('9999','test','!AC',0,now());");
		jdbcTemplate.execute("insert into t_factory_task(idt_factory_task,productId,productName,taskname,amount,status,starttime,unitid,counter) values(9999,'9999','test','test',20000,'CRT',now(),'010',0)");
		//创建20000个帐户
		FactoryTask task = taskDao.selectByPrimaryKey(9999);
			srv.generateAccounts(task);
		FactoryTask aftertask = taskDao.selectByPrimaryKey(9999);
		assertEquals("OK#",aftertask.getStatus());
		FactoryProcess process = processDao.selectProcess("9999", "010");
		assertEquals(999901000020000L,process.getCouter().longValue());
		Number count=jdbcTemplate.queryForObject("select count(*) from t_factory_account_id where accounttype='!AC'", Number.class);
		assertEquals(20000,count.longValue());
		String maxaccount=jdbcTemplate.queryForObject("select max(accountId) from t_factory_account_id where accounttype='!AC'", String.class);
		int checkNum = LuhnUtils.getCheckNum("999901000020000");
		assertEquals("999901000020000"+checkNum,maxaccount);
		//再创建20000个帐户
		jdbcTemplate.execute("insert into t_factory_task(idt_factory_task,productId,productName,taskname,amount,status,starttime,unitid,counter) values(9998,'9999','test','test',50000,'CRT',now(),'010',10)");
		FactoryTask task1 = taskDao.selectByPrimaryKey(9998);
		srv.generateAccounts(task1);
		FactoryTask aftertask2 = taskDao.selectByPrimaryKey(9998);
		assertEquals("OK#",aftertask2.getStatus());
		FactoryProcess process2 = processDao.selectProcess("9999", "010");
		assertEquals(999901000069990L,process2.getCouter().longValue());
		Number count1=jdbcTemplate.queryForObject("select count(*) from t_factory_account_id where accounttype='!AC'", Number.class);
		assertEquals(69990,count1.longValue());
		
		String maxaccount2=jdbcTemplate.queryForObject("select max(accountId) from t_factory_account_id where accounttype='!AC'", String.class);
		int checkNum2 = LuhnUtils.getCheckNum("999901000069990");
		assertEquals("999901000069990"+checkNum2,maxaccount2);
		
	}

}
*/