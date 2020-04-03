/**
 * 
 * AccountIdServiceImplTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.test.service.BaseTestService;

/**
 * @author john huang
 * 2015年8月26日 上午6:55:05
 * 本类主要做为
 */
public class AccountIdServiceImplTest extends BaseTestService {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		System.out.println("初始化"); 
		jdbcTemplate.execute("delete from t_factory_account_id where productId = '8888'");
		jdbcTemplate.batchUpdate("insert into t_factory_account_id(accountId, status, accountType, insertTime, ver, productId) values (?,'NUS','XCA',now(),0,'8888')", new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				String accid = "888801000000"+StringUtils.leftPad(String.valueOf(i), 4, '0');
				System.out.println(i+"="+accid);
				ps.setString(1,accid );
				
			}
			
			@Override
			public int getBatchSize() {
				return 1000;
			}
		});
		System.out.println("初始化完成"); 
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.hummingbird.maaccount.service.impl.AccountIdServiceImpl#prepareGetAccountId(java.lang.String)}.
	 */
	@Test
	public void testPrepareGetAccountId() {
		//多线程并发测试
		AccountIdServiceImpl bean = SpringBeanUtil.getInstance().getBean(AccountIdServiceImpl.class);
//		String prepareGetAccountId=null;
//		try {
//			prepareGetAccountId = bean.prepareGetAccountId("8888");
//		} catch (MaAccountException e) {
//			e.printStackTrace();
//			fail("获取帐户出错"+e.getMessage());
//		}
//		assertNotNull(prepareGetAccountId);
//		assertEquals("888801000000", prepareGetAccountId.substring(0,12));
		System.out.println("并发获取帐户"+System.currentTimeMillis());
		final Map map = new HashMap();
		
//		new GenAccountIdThread(SpringBeanUtil.getInstance(),map).start();
//		new GenAccountIdThread(SpringBeanUtil.getInstance(),map).start();
//		new GenAccountIdThread(SpringBeanUtil.getInstance(),map).start();
//		new GenAccountIdThread(SpringBeanUtil.getInstance(),map).start();
//		new GenAccountIdThread(SpringBeanUtil.getInstance(),map).start();
		new GenAccountIdThread(SpringBeanUtil.getInstance(),map).run();
//		while(map.size()<5){
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		System.out.println("finish"+System.currentTimeMillis());
		
	}

	
	
	
}

class GenAccountIdThread extends Thread{

	/**
	 * 1000条
	 */
	int left = 1000;
	
	AccountIdServiceImpl bean;
	
	Map singalmap ;
	
	/**
	 * 构造函数
	 */
	public GenAccountIdThread(SpringBeanUtil instance, Map map) {
		bean = instance.getBean(AccountIdServiceImpl.class);
		singalmap = map;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while(left>0){
			left--;
			System.out.println(left);
			String prepareGetAccountId = null;
			try {
				prepareGetAccountId = bean.prepareGetAccountId("2077");
			} catch (MaAccountException e) {
				e.printStackTrace();
				fail("获取帐户出错"+e.getMessage());
			}
			if(singalmap.containsKey(prepareGetAccountId)){
				fail(prepareGetAccountId+"重复");
			}
			singalmap.put(prepareGetAccountId, null);
		}
	}
	
	
	
}
