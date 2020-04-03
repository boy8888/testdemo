/**
 * 
 * InvestmentAccountServiceTest.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.maaccount.constant.AccountConst;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.service.InvestmentAccountService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.OrderFactory;
import com.hummingbird.maaccount.vo.AppVO;
import com.hummingbird.maaccount.vo.OrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO;
import com.hummingbird.test.service.BaseTestService;

/**
 * @author huangjiej_2
 * 2014年12月28日 下午3:15:07
 * 本类主要做为
 */
public class InvestmentAccountServiceTest extends BaseTestService {

	@Autowired
	InvestmentAccountService service;
	
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
	 * Test method for {@link com.hummingbird.maaccount.service.impl.InvestmentAccountService#income(com.hummingbird.maaccount.face.Order)}.
	 */
//	@Test
	public void testIncome() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.hummingbird.maaccount.service.impl.InvestmentAccountService#expense(com.hummingbird.maaccount.face.Order)}.
	 * @throws MaAccountException 
	 */
	@Test
	public void testExpense() throws MaAccountException  {
		jdbcTemplate.update("insert into t_user(userId,mobileNum) values(?,?)",10000,"88922260815");
		jdbcTemplate.update("insert into t_investment_account(userId,accountId,objectSum,remainingSum,remark,status,frozenSum ) values(?,?,?,?,?,?,?)",10000,"test_account",9999,1234,"remrk",AccountConst.ACCOUNT_STATUS_OK,0);
		jdbcTemplate.update("insert into t_cash_account values(?,?,?,?,?,?)",10000,"test_account",0,"remrk",AccountConst.ACCOUNT_STATUS_OK,Md5Util.Encrypt("CA#"+10000+"test_account"+0+AccountConst.ACCOUNT_STATUS_OK));
		//创建资金转移
		Account account = service.getAccount("88922260815");
		service.setOrderTarget(service.ORDER_TARGET_TYPE_REMAINING);
		((InvestmentAccount)account).setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
		Account cashacc = AccountFactory.getAccount(account.ACCOUNT_CASH, "88922260815");
		
		TransOrderVO tovo = new TransOrderVO();
		tovo.setMethod("/investmentAccount/transfer_to_cashAccount");
		tovo.setOriginalOrderId("originalOrderId");
		tovo.setOriginalTable("originalTable");
		OrderVO ordervo = new OrderVO();
		ordervo.setAppOrderId("appOrderId");
		ordervo.setSum(1000L);
		ordervo.setProductName("productName");
		ordervo.setMobileNum("88922260815");
		ordervo.setRemark("remark");
		AppVO app = new AppVO();
		app.setAppId("appid");
		app.setAppname("appName");
		tovo.setApp(app);
		tovo.setOrder(ordervo);
		
		Order expenseOrder;
			expenseOrder = OrderFactory.createExpenseOrder(tovo,account,cashacc);
		
		
		Receipt expense = service.expense(expenseOrder);
		InvestmentAccount afteracc = (InvestmentAccount)service.getAccount("88922260815");
		assertEquals(234L, (long)afteracc.getRemainingsum());
		assertEquals(9999, (long)afteracc.getObjectsum());
		Map<String, Object> row = jdbcTemplate.queryForMap("select * from t_investment_account_remaining_order where orderid=?",expense.getOrderNo());
		
		assertEquals("appid", row.get("appId"));
		assertEquals("appName", row.get("appName"));
		assertEquals("test_account", row.get("accountId"));
		assertEquals("/investmentAccount/transfer_to_cashAccount", row.get("method"));
		assertEquals(-1000, NumberUtils.toLong(ObjectUtils.toString(row.get("sum")),0));
		assertEquals("remark", row.get("remark"));
		assertEquals("originalOrderId",row.get("originalOrderId"));
		assertEquals("originalTable",row.get("originalTable"));
		assertEquals("OK#",row.get("status"));
		assertEquals(DateUtil.formatCommonDate(expense.getDealTime()),DateUtil.formatCommonDate((Date) row.get("insertTime")));
		assertEquals(DateUtil.formatCommonDate(expense.getDealTime()),DateUtil.formatCommonDate((Date) row.get("updateTime")));
		assertEquals("productName",row.get("productName"));
		assertEquals("appOrderId",row.get("appOrderId"));
		
		
		
	}

	/**
	 * Test method for {@link com.hummingbird.maaccount.service.impl.DefaultAccountService#getAccount(java.lang.String)}.
	 */
//	@Test
	public void testGetAccount() {
		//插入帐i户信息
		jdbcTemplate.update("insert into t_user(userId,mobileNum) values(?,?)",10000,"88922260815");
		jdbcTemplate.update("insert into t_investment_account values(?,?,?,?,?,?)",10000,"test_account",9999,1234,"remrk",AccountConst.ACCOUNT_STATUS_OK);
		
		Account account = service.getAccount("88922260815");
		Assert.assertNotNull(account);
		assertEquals("test_account", account.getAccountId());
		assertEquals(9999+1234, (long)account.getBalance());
		
	}

}
