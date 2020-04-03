/**
 * 
 * OrderFactory.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.commonbiz.util.NoGenerationUtil;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AbstractOrder;
import com.hummingbird.maaccount.entity.CashAccountOrder;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.InvestmentAccountObjectOrder;
import com.hummingbird.maaccount.entity.InvestmentAccountRemainingOrder;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.mapper.AccountOrderDao;
import com.hummingbird.maaccount.mapper.CashAccountOrderMapper;
import com.hummingbird.maaccount.mapper.InvestmentAccountObjectOrderMapper;
import com.hummingbird.maaccount.mapper.InvestmentAccountRemainingOrderMapper;
import com.hummingbird.maaccount.service.OrderService;
import com.hummingbird.maaccount.vo.AppVO;
import com.hummingbird.maaccount.vo.OilcardOrderVO;
import com.hummingbird.maaccount.vo.OrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午9:06:22
 * 本类主要做为订单处理工厂类
 */
public abstract class OrderFactory {

	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(OrderFactory.class);
	
	/**
	 * 创建支出订单
	 * @param transorder
	 * @param fromaccount
	 * @throws MaAccountException 
	 */
	public static Order createExpenseOrder(TransOrderVO transorder,
			Account fromaccount,Account toaccount) throws MaAccountException {
		String accountCode =fromaccount.getAccountCode();
		AbstractOrder order=null;
		switch (accountCode) {
		case Account.ACCOUNT_CASH:
			
			CashAccountOrder caOrder = new CashAccountOrder();
			copyfromTransOrderVo(caOrder,transorder,fromaccount);
			caOrder.setBalance(fromaccount.getBalance()-transorder.getOrder().getSum());
			caOrder.setSum(0-caOrder.getSum());//为负值
			order= caOrder;
			if (log.isTraceEnabled()) {
				log.trace(String.format("创建现金帐户支出订单", caOrder));
			}
			break;
			
		case Account.ACCOUNT_INVESTMENT:
			InvestmentAccount inveaccount = (InvestmentAccount) fromaccount;
			if(inveaccount.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_OBJECT){
				InvestmentAccountObjectOrder iaOrder = new InvestmentAccountObjectOrder();
				copyfromTransOrderVo(iaOrder,transorder,fromaccount);
				iaOrder.setBalance(inveaccount.getObjectsum()-transorder.getOrder().getSum());
				iaOrder.setSum(0-iaOrder.getSum());
				order= iaOrder;
				if (log.isTraceEnabled()) {
					log.trace(String.format("创建投资帐户标的支出订单", iaOrder));
				}
			}
			else if(inveaccount.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_REMAINING){
				InvestmentAccountRemainingOrder iaOrder = new InvestmentAccountRemainingOrder();
				copyfromTransOrderVo(iaOrder,transorder,fromaccount);
				iaOrder.setBalance(inveaccount.getRemainingsum()-transorder.getOrder().getSum());
				iaOrder.setFrozenSumSnapshot(inveaccount.getFrozensum());
				iaOrder.setObjectSumSnapshot(inveaccount.getObjectsum());
				iaOrder.setSum(0-iaOrder.getSum());
				order= iaOrder;
				if (log.isTraceEnabled()) {
					log.trace(String.format("创建投资帐户剩余金额支出订单", iaOrder));
				}
			}
			else if(inveaccount.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_FREEZE){
				if(inveaccount.getOrderTarget()==InvestmentAccount.SUM_TARGET_TYPE_REMAINING){
					InvestmentAccountRemainingOrder iaOrder = new InvestmentAccountRemainingOrder();
					copyfromTransOrderVo(iaOrder,transorder,fromaccount);
					iaOrder.setBalance(inveaccount.getRemainingsum()-transorder.getOrder().getSum());
					iaOrder.setSum(0-iaOrder.getSum());
					iaOrder.setFrozenSumSnapshot(inveaccount.getFrozensum()+Math.abs(transorder.getOrder().getSum()));
					iaOrder.setObjectSumSnapshot(inveaccount.getObjectsum());
					order= iaOrder;
					if (log.isTraceEnabled()) {
						log.trace(String.format("创建投资帐户余额冻结支出订单", iaOrder));
					}
				}
				else if(inveaccount.getOrderTarget()==InvestmentAccount.SUM_TARGET_TYPE_NONE){
					if (log.isTraceEnabled()) {
						log.trace(String.format("创建投资帐户冻结金额不产生支出订单"));
					}
					
					//TODO 
					InvestmentAccountRemainingOrder iaOrder = new InvestmentAccountRemainingOrder();
					iaOrder.setBalance(inveaccount.getObjectsum()-transorder.getOrder().getSum());
					iaOrder.setSum(0-transorder.getOrder().getSum());
					iaOrder.setFrozenSumSnapshot(inveaccount.getFrozensum());
					iaOrder.setObjectSumSnapshot(inveaccount.getObjectsum());
					order= iaOrder;
					
				}
				else{
					
					InvestmentAccountObjectOrder iaOrder = new InvestmentAccountObjectOrder();
					copyfromTransOrderVo(iaOrder,transorder,fromaccount);
					iaOrder.setBalance(inveaccount.getObjectsum()-transorder.getOrder().getSum());
					iaOrder.setSum(0-iaOrder.getSum());
					order= iaOrder;
					if (log.isTraceEnabled()) {
						log.trace(String.format("创建投资帐户标的冻结支出订单", iaOrder));
					}
				}
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"投资帐户支出订单创建失败");
			}
			break;

		default:
			//as= SpringBeanUtil.getInstance().getBean(DefaultAccountService.class);
			break;
		}
		if(order!=null){
			order.setAccount(fromaccount);
			order.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
			
			order.setPeerAccountId(toaccount.isVirtualAccount()?transorder.getOrder().getPeerAccountId():toaccount.getAccountId());
			order.setPeerAccountType(toaccount.isVirtualAccount()?ObjectUtils.toString(transorder.getOrder().getPeerAccountType(),"TA#"):AccountFactory.getAccountFlag4Order(toaccount.getAccountCode()));
			order.setPeerAccountUnit(toaccount.isVirtualAccount()?transorder.getOrder().getPeerAccountUnit():"营销账户平台");
			order.setExternalOrderId(transorder.getOrder().getExternalOrderId());
			if(StringUtils.isNotBlank(transorder.getOrder().getExternalOrderTime())){
				try {
					order.setExternalOrderTime(DateUtil.parse(transorder.getOrder().getExternalOrderTime()).getTime());
				} catch (ParseException e) {
					throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"外部交易时间格式不正确");
				}
				
			}
		}
		return order;
		
	}

	/**
	 * @param caOrder
	 * @param transorder
	 * @param fromaccount 
	 */
	public static void copyfromTransOrderVo(AbstractOrder caOrder,
			TransOrderVO transorder, Account fromaccount) {
		OrderVO order = transorder.getOrder();
		AppVO app = transorder.getApp();
		String orderNo = NoGenerationUtil.genOrderNo();
		caOrder.setAppId(app.getAppId());
		caOrder.setAppname(app.getAppname());
		caOrder.setAccountId(fromaccount.getAccountId());
		caOrder.setInsertTime(new Date());
		caOrder.setUpdateTime(new Date());
		caOrder.setOrderId(orderNo);
		caOrder.setOriginalorderId(orderNo);
		caOrder.setProductName(order.getProductName());
		caOrder.setSum(order.getSum());
		caOrder.setAppOrderId(order.getAppOrderId());
		caOrder.setRemark(order.getRemark());
		caOrder.setMethod(transorder.getMethod());
		caOrder.setOriginalorderId(transorder.getOriginalOrderId());
		caOrder.setOriginaltable(transorder.getOriginalTable());
		caOrder.setStatus("OK#");
		caOrder.setType(transorder.getOperationType());
		if (caOrder instanceof CashAccountOrder) {
			CashAccountOrder cao = (CashAccountOrder) caOrder;
			cao.setPayOrderId(order.getPayOrderId());
		}
		if (caOrder instanceof InvestmentAccountRemainingOrder) {
			InvestmentAccountRemainingOrder iaro = (InvestmentAccountRemainingOrder) caOrder;
			iaro.setPayOrderId(order.getPayOrderId());
		}
	}

	/**
	 * 创建收入订单
	 * @param transorder
	 * @param toaccount
	 * @return
	 * @throws MaAccountException 
	 */
	public static Order createIncomeOrder(TransOrderVO transorder,Account fromaccount,
			Account toaccount) throws MaAccountException {
		String accountCode =toaccount.getAccountCode();
		AbstractOrder order=null;
		switch (accountCode) {
		case Account.ACCOUNT_CASH:
			CashAccountOrder caOrder = new CashAccountOrder();
			copyfromTransOrderVo(caOrder,transorder,toaccount);
			caOrder.setBalance(toaccount.getBalance()+transorder.getOrder().getSum());
			order= caOrder;
			if (log.isTraceEnabled()) {
				log.trace(String.format("创建现金帐户收入订单", order));
			}
			break;
			
		case Account.ACCOUNT_INVESTMENT:
			InvestmentAccount inveaccount = (InvestmentAccount) toaccount;
			if(inveaccount.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_OBJECT){
				InvestmentAccountObjectOrder iaOrder = new InvestmentAccountObjectOrder();
				copyfromTransOrderVo(iaOrder,transorder,toaccount);
				iaOrder.setBalance(inveaccount.getObjectsum()+transorder.getOrder().getSum());
				order= iaOrder;
				if (log.isTraceEnabled()) {
					log.trace(String.format("创建投资帐户标的金额收入订单", order));
				}
			}
			else if(inveaccount.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_REMAINING){
				InvestmentAccountRemainingOrder iaOrder = new InvestmentAccountRemainingOrder();
				copyfromTransOrderVo(iaOrder,transorder,toaccount);
				iaOrder.setBalance(inveaccount.getRemainingsum()+transorder.getOrder().getSum());
				iaOrder.setFrozenSumSnapshot(inveaccount.getFrozensum());
				iaOrder.setObjectSumSnapshot(inveaccount.getObjectsum());
				order= iaOrder;
				if (log.isTraceEnabled()) {
					log.trace(String.format("创建投资帐户剩余金额收入订单", order));
				}
			}
			else if(inveaccount.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_FREEZE){
				if(inveaccount.getOrderTarget()==InvestmentAccount.SUM_TARGET_TYPE_REMAINING){
					InvestmentAccountRemainingOrder iaOrder = new InvestmentAccountRemainingOrder();
					copyfromTransOrderVo(iaOrder,transorder,fromaccount);
					iaOrder.setBalance(inveaccount.getRemainingsum()+transorder.getOrder().getSum());
					iaOrder.setSum(iaOrder.getSum());
					iaOrder.setFrozenSumSnapshot(inveaccount.getFrozensum());
					iaOrder.setObjectSumSnapshot(inveaccount.getObjectsum());
					order= iaOrder;
					if (log.isTraceEnabled()) {
						log.trace(String.format("创建投资帐户余额冻结收入订单", iaOrder));
					}
				}
				else if(inveaccount.getOrderTarget()==InvestmentAccount.SUM_TARGET_TYPE_NONE){
					if (log.isTraceEnabled()) {
						log.trace(String.format("创建投资帐户冻结金额不产生收入订单"));
					}
					InvestmentAccountRemainingOrder iaOrder = new InvestmentAccountRemainingOrder();
					//iaOrder.setBalance(inveaccount.getObjectsum()+transorder.getOrder().getSum());
					iaOrder.setSum(transorder.getOrder().getSum());
					order= iaOrder;
				}
				else{
					
					InvestmentAccountObjectOrder iaOrder = new InvestmentAccountObjectOrder();
					copyfromTransOrderVo(iaOrder,transorder,toaccount);
					iaOrder.setBalance(inveaccount.getObjectsum()+transorder.getOrder().getSum());
					order= iaOrder;
					if (log.isTraceEnabled()) {
						log.trace(String.format("创建投资帐户标的金额收入冻结订单", order));
					}
				}
			}
			else{
				throw new MaAccountException(9002,"投资帐户收入订单创建失败");
			}
			
			break;

		default:
			//as= SpringBeanUtil.getInstance().getBean(DefaultAccountService.class);
			break;
		}
		if(order!=null){
			order.setAccount(toaccount);
			order.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			order.setPeerAccountId(fromaccount.isVirtualAccount()?transorder.getOrder().getPeerAccountId():fromaccount.getAccountId());
			order.setPeerAccountType(fromaccount.isVirtualAccount()?ObjectUtils.toString(transorder.getOrder().getPeerAccountType(),"TA#"):AccountFactory.getAccountFlag4Order(fromaccount.getAccountCode()));
			order.setPeerAccountUnit(fromaccount.isVirtualAccount()?transorder.getOrder().getPeerAccountUnit():"营销账户平台");
			order.setExternalOrderId(transorder.getOrder().getExternalOrderId());
			if(StringUtils.isNotBlank(transorder.getOrder().getExternalOrderTime())){
				try {
					order.setExternalOrderTime(DateUtil.parse(transorder.getOrder().getExternalOrderTime()).getTime());
				} catch (ParseException e) {
					throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"外部交易时间格式不正确");
				}
				
			}
		}
		
		return order;
	}
	
	
	/**
	 * 获取订单dao
	 * @param accountCode
	 * @param accountadditional 如果是投资帐户，需要添加此内容以标识是 标的订单dao或剩余订单dao
	 * @return
	 * @throws MaAccountException
	 */
	public static AccountOrderDao getOrderDao(String accountCode,String accountadditional) throws MaAccountException{
		switch (accountCode) {
		case Account.ACCOUNT_CASH:
			return SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
		case Account.ACCOUNT_INVESTMENT:
			if(String.valueOf(InvestmentAccount.SUM_TARGET_TYPE_OBJECT).equals(accountadditional)){
				return SpringBeanUtil.getInstance().getBean(InvestmentAccountObjectOrderMapper.class);
			}
			else if(String.valueOf(InvestmentAccount.SUM_TARGET_TYPE_REMAINING).equals(accountadditional)){
				return SpringBeanUtil.getInstance().getBean(InvestmentAccountRemainingOrderMapper.class);
			}
			else if(String.valueOf(InvestmentAccount.SUM_TARGET_TYPE_FREEZE).equals(accountadditional)){
				return SpringBeanUtil.getInstance().getBean(InvestmentAccountObjectOrderMapper.class);
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"获取订单对应的dao出错，投资帐户的何种订单标识不明确:"+accountadditional);
			}

		default:
			throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"获取订单对应的dao出错，帐户标识未能识别:"+accountCode);
		}
		
		
	}

	/**
	 * 获取订单的所在表格
	 * @param fromaccount
	 * @return
	 * @throws MaAccountException 
	 */
	public static String getOrderTable(Account fromaccount) throws MaAccountException {
		String accountCode =fromaccount.getAccountCode();
		switch (accountCode) {
		case Account.ACCOUNT_CASH:
			return "t_cash_account_order";
		case Account.ACCOUNT_INVESTMENT:
			InvestmentAccount inveaccount = (InvestmentAccount) fromaccount;
			if(inveaccount.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_OBJECT){
				return "t_investment_account_object_order";
			}
			else if(inveaccount.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_REMAINING){
				return "t_investment_account_remaining_order";
			}
			else if(inveaccount.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_FREEZE){
				if(inveaccount.getOrderTarget()==InvestmentAccount.SUM_TARGET_TYPE_OBJECT){
					return "t_investment_account_object_order";
				}
				else if(inveaccount.getOrderTarget()==InvestmentAccount.SUM_TARGET_TYPE_REMAINING){
					return "t_investment_account_remaining_order";
				}
				else{
					//先默认给出一个可用余额表
					return "t_investment_account_remaining_order";
				}
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"获取订单所在表名出错");
			}

		default:
			//as= SpringBeanUtil.getInstance().getBean(DefaultAccountService.class);
			break;
		}
		
		return null;
	}

	/**
	 * 检查是否已冲正（投资帐户至银行卡），在冲正接口调用
	 * @param oriorderid
	 * @param sourceacc 银行卡
	 * @param descacc   投资帐户
	 * @throws DataInvalidException 
	 */
	public static void checkHadantiTrans2Bankcard(String oriorderid,
			Account sourceacc, InvestmentAccount descacc) throws DataInvalidException {
		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		String sql = "select count(*) from t_investment_account_remaining_order i where originalOrderId=? and i.accountId=? and status=? and type=?";
		//查询以指定的订单号为原始订单号，投资帐户输出，订单状态为正常，类型为冲正的订单
		Number count =jdbc.queryForObject(sql,new String[]{oriorderid,descacc.getAccountId(),OrderConst.ORDER_STATUS_OK,OrderConst.ORDER_OPERATION_CZH},Integer.class);	;
		if(count.intValue()>0){
			throw ValidateException.ERROR_EXISTING_ORDERNO_EXISTS.clone(null,"订单已冲正");
		}
	}
	
	
	/**
	 * 判断是否已完成提现的动作
	 * @param oriorderid
	 * @param sourceacc
	 * @param descacc
	 * @throws DataInvalidException
	 */
	public static void checkWithdraw(String oriorderid,
			Account sourceacc, Account descacc)throws DataInvalidException {
		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		
		String sql = "select count(*) from t_investment_account_remaining_order i where originalOrderId=? and i.accountId=? and status=? and type=?";
		//查询以指定的订单号为原始订单号，投资帐户输出，订单状态为正常，类型为冲正的订单
		if (log.isDebugEnabled()) {
			log.debug(String.format("检测是否完成了提现,sql=%s,参数=%s",sql,oriorderid));
		}
		Number count =jdbc.queryForObject(sql,new String[]{oriorderid,sourceacc.getAccountId(),OrderConst.ORDER_STATUS_OK,OrderConst.ORDER_OPERATION_TX},Integer.class);	;
		if(count.intValue()>0){
			if (log.isDebugEnabled()) {
				log.debug(String.format("查询到有数据,不能再提现成功或撤销"));
			}
			throw ValidateException.ERROR_EXISTING_ORDERNO_EXISTS.clone(null,"提现已完成或已撤销");
		}
	}
	
	/**
	 * 检查冻结金额是否已被处理（解冻接口，购买接口使用）
	 * @param oriorderid
	 * @param sourceacc 投资帐户
	 * @param descacc   投资帐户
	 * @return 
	 * @throws DataInvalidException 
	 */
	public static InvestmentAccountObjectOrder checkAntiFreeze(String oriorderid,String ordertable,
			Account sourceacc, InvestmentAccount descacc) throws DataInvalidException {
//		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
//		//查询有无解冻
//		String sql = "select count(*) from t_investment_account_remaining_order i,t_investment_account_object_order o where o.orderid=i.originalOrderId and o.originalOrderId=? and i.accountId=? and o.accountId=? and i.status=? and i.type=?";
//		//查询以指定的订单号为原始订单号，投资帐户输出，订单状态为正常，类型为冲正的订单
//		Number count =jdbc.queryForObject(sql,new String[]{oriorderid,descacc.getAccountId(),sourceacc.getAccountId(),OrderConst.ORDER_STATUS_OK,OrderConst.ORDER_OPERATION_FEB},Integer.class);	;
//		if(count.intValue()>0){
//			throw ValidateException.ERROR_EXISTING_ORDERNO_EXISTS.clone(null,"订单已解冻");
//		}
		OrderService orderSrv = SpringBeanUtil.getInstance().getBean(OrderService.class);
		//查询余额订单
		Order remainorder = orderSrv.getOrder(ordertable, oriorderid);
		if(remainorder==null){
			throw ValidateException.ERROR_MATCH_ORDERNO.clone(null,"原订单不存在");
		}
		//订单id为冻结时的可用余额订单id，需要找到对应的标的订单id,
		InvestmentAccountObjectOrder oriorder = (InvestmentAccountObjectOrder) orderSrv.getFreezeObjectOrder(oriorderid);
		if(oriorder==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("找不到对应的标的冻结订单"));
			}
			throw ValidateException.ERROR_MATCH_ORDERNO.clone(null,"原订单不存在");
		}
//		//如果订单状态不是fRz，则该订单已被处理，不能操作
		if(!OrderConst.ORDER_STATUS_FROZEN.equals(oriorder.getStatus())){
			if (log.isDebugEnabled()) {
				log.debug(String.format("冻结的标的订单的状态为%s，非冻结状态，不能操作",oriorder.getStatus()));
			}
			throw ValidateException.ERROR_MATCH_ORDERNO.clone(null,"冻结订单已被解冻或已购买有油贷产品");
		}
		return oriorder;
		
	}

	
	 

	
	
	
	
}
