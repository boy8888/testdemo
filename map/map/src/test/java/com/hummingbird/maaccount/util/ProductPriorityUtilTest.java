/**
 * 
 * ProductPriorityUtilTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hummingbird.maaccount.face.Consumer;
import com.hummingbird.maaccount.vo.ProductPriority;
import com.hummingbird.test.service.BaseTestService;

/**
 * @author john huang
 * 2015年8月19日 下午2:59:28
 * 本类主要做为
 */
public class ProductPriorityUtilTest extends BaseTestService {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.hummingbird.maaccount.util.ProductPriorityUtil#getProductPriorities(java.lang.String)}.
	 */
	@Test
	public void testGetProductPriorities() {
		String str_accounts = "OCA,CA#,VCA";
		List<ProductPriority> productPriorities = ProductPriorityUtil.getProductPriorities(str_accounts);
		assertEquals(3, productPriorities.size());
		assertEquals(Consumer.CONSUMER_TYPE_OILCARD, productPriorities.get(0).getConsumerType());
		assertEquals(Consumer.CONSUMER_TYPE_CASHACCOUNT, productPriorities.get(1).getConsumerType());
		assertEquals(Consumer.CONSUMER_TYPE_VOLUMECARD, productPriorities.get(2).getConsumerType());
		
		str_accounts = "OCA,DA#,VCA";
		productPriorities = ProductPriorityUtil.getProductPriorities(str_accounts);
		assertEquals(2, productPriorities.size());
		assertEquals(Consumer.CONSUMER_TYPE_OILCARD, productPriorities.get(0).getConsumerType());
		assertEquals(Consumer.CONSUMER_TYPE_VOLUMECARD, productPriorities.get(1).getConsumerType());
		
		str_accounts = "OCA,3001,VCA,2001";
		productPriorities = ProductPriorityUtil.getProductPriorities(str_accounts);
		assertEquals(4, productPriorities.size());
		assertEquals(Consumer.CONSUMER_TYPE_OILCARD, productPriorities.get(0).getConsumerType());
		assertTrue(productPriorities.get(0).getDenies().contains("2001"));
		assertEquals(Consumer.CONSUMER_TYPE_DISCOUNTCARD, productPriorities.get(1).getConsumerType());
		assertEquals("3001", productPriorities.get(1).getAllow());
		
		assertEquals(Consumer.CONSUMER_TYPE_VOLUMECARD, productPriorities.get(2).getConsumerType());
		assertEquals(Consumer.CONSUMER_TYPE_OILCARD, productPriorities.get(3).getConsumerType());
		assertEquals("2001", productPriorities.get(3).getAllow());
		
		str_accounts = "OCA,2002,VCA,2001,3002";
		productPriorities = ProductPriorityUtil.getProductPriorities(str_accounts);
		assertEquals(5, productPriorities.size());
		assertEquals(Consumer.CONSUMER_TYPE_OILCARD, productPriorities.get(0).getConsumerType());
		assertTrue(productPriorities.get(0).getDenies().contains("2002"));
		assertTrue(productPriorities.get(0).getDenies().contains("2001"));
		assertEquals(Consumer.CONSUMER_TYPE_OILCARD, productPriorities.get(1).getConsumerType());
		assertEquals("2002", productPriorities.get(1).getAllow());
		assertTrue(productPriorities.get(1).getDenies().isEmpty());
		assertEquals(Consumer.CONSUMER_TYPE_VOLUMECARD, productPriorities.get(2).getConsumerType());
		assertEquals(Consumer.CONSUMER_TYPE_OILCARD, productPriorities.get(3).getConsumerType());
		assertEquals("2001", productPriorities.get(3).getAllow());
		assertEquals(Consumer.CONSUMER_TYPE_DISCOUNTCARD, productPriorities.get(4).getConsumerType());
		assertEquals("3002", productPriorities.get(4).getAllow());
		
		
	}

}
