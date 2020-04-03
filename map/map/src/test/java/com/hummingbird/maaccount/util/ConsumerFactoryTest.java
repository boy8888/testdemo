/**
 * 
 * ConsumerFactoryTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Consumer;
import com.hummingbird.test.service.BaseTestService;

/**
 * @author john huang
 * 2015年3月1日 下午2:31:03
 * 本类主要做为
 */
public class ConsumerFactoryTest extends BaseTestService{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		SpringBeanUtil.init(applicationContext);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.hummingbird.maaccount.util.ConsumerFactory#getConsumer(java.lang.String)}.
	 */
	@Test
	public void testGetConsumer() {
		//产品类型
		String sql = "INSERT INTO `maccount`.`t_product` (`productId`, `productPrice`, `productName`, `productUrl`, `insertTime`, `updateTime`, `accountType`) VALUES ('9999', '100000', '乐驾包1000元套餐', NULL, '2015-02-18 09:42:52', '2015-02-18 09:42:52', 'OCA')";
		jdbcTemplate.execute(sql);
		Consumer consumer;
		try {
			consumer = ConsumerFactory.getConsumer("18922260815");
			assertEquals(Consumer.CONSUMER_TYPE_MOBILE, consumer.getConsumerType());
		} catch (DataInvalidException | MaAccountException e) {
			e.printStackTrace();
			fail();
		}
		try {
			consumer = ConsumerFactory.getConsumer("9999010000000001");
			assertEquals(Consumer.CONSUMER_TYPE_OILCARD, consumer.getConsumerType());
		} catch (DataInvalidException | MaAccountException e) {
			e.printStackTrace();
			fail();
		}
		try {
			consumer = ConsumerFactory.getConsumer("9500010000000001");
			assertEquals(Consumer.CONSUMER_TYPE_CASHACCOUNT, consumer.getConsumerType());
		} catch (DataInvalidException | MaAccountException e) {
			e.printStackTrace();
			fail();
		}
		try {
			consumer = ConsumerFactory.getConsumer("950001000000001");
			fail();
		} catch (DataInvalidException | MaAccountException e) {
			
		}
		try {
			consumer = ConsumerFactory.getConsumer("9599001000000001");
			fail();
		} catch (DataInvalidException | MaAccountException e) {
		}
		
		
		
	}

}
