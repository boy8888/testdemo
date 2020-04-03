/**
 * 
 * SmsSendUtilTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.entity.SmsMTDelay;
import com.hummingbird.test.service.BaseTestService;

/**
 * @author john huang
 * 2015年7月30日 上午11:15:32
 * 本类主要做为
 */
public class SmsSendUtilTest  extends BaseTestService{

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
	 * Test method for {@link com.hummingbird.maaccount.util.SmsSendUtil#setSendtime(com.hummingbird.maaccount.entity.SmsMTDelay)}.
	 * @throws ParseException 
	 */
	@Test
	public void testSetSendtime() throws ParseException {
		String ds = "2015-07-30 01:12:34";
		String sendtime = getSenttime(ds);
		System.out.println("插入时间:"+ds+",发送时间:"+sendtime);
		assertEquals("2015-07-30 08:12:34", sendtime);
		
		ds = "2015-07-30 08:12:34";
		sendtime = getSenttime(ds);
		System.out.println("插入时间:"+ds+",发送时间:"+sendtime);
		assertEquals("2015-07-30 08:12:34", sendtime);
		
		ds = "2015-07-30 12:12:34";
		sendtime = getSenttime(ds);
		System.out.println("插入时间:"+ds+",发送时间:"+sendtime);
		assertEquals("2015-07-30 12:12:34", sendtime);
		
		ds = "2015-07-30 21:12:34";
		sendtime = getSenttime(ds);
		System.out.println("插入时间:"+ds+",发送时间:"+sendtime);
		assertEquals("2015-07-31 08:12:34", sendtime);
		
		ds = "2015-07-30 23:12:34";
		sendtime = getSenttime(ds);
		System.out.println("插入时间:"+ds+",发送时间:"+sendtime);
		assertEquals("2015-07-31 08:12:34", sendtime);
	}

	/**
	 * @param ds
	 * @return
	 * @throws ParseException
	 */
	public String getSenttime(String ds) throws ParseException {
		SmsMTDelay smsmt=new SmsMTDelay();
		Date date = DateUtil.parse2date(ds, "yyyy-MM-dd HH:mm:ss");
		smsmt.setSendTime(date);
		SmsSendUtil.setSendtime(smsmt);
		String sendtime = DateUtil.formatCommonDate(smsmt.getSendTime());
		return sendtime;
	}

	/**
	 * Test method for {@link com.hummingbird.maaccount.util.SmsSendUtil#delaySmsSend(java.lang.String, java.util.Date, java.lang.String[])}.
	 */
	@Test
	public void testDelaySmsSend() {
	}

}
