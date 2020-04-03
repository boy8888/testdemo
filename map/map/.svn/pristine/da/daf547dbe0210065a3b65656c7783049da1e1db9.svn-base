/**
 * 
 * OrderServiceImpl.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.MoneyUtil;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.commonbiz.util.NoGenerationUtil;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AbstractOrder;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.CashAccountOrder;
import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.DiscountCardAccountOrder;
import com.hummingbird.maaccount.entity.DiscountCardProduct;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.InvestmentAccountObjectOrder;
import com.hummingbird.maaccount.entity.InvestmentAccountRemainingOrder;
import com.hummingbird.maaccount.entity.JifenAccount;
import com.hummingbird.maaccount.entity.JifenAccountOrder;
import com.hummingbird.maaccount.entity.JifenProduct;
import com.hummingbird.maaccount.entity.OfflineRecharge;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.entity.RedPaperAccountOrder;
import com.hummingbird.maaccount.entity.RedPaperProduct;
import com.hummingbird.maaccount.entity.TerminalList;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccountOrder;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.AccountOrderDao;
import com.hummingbird.maaccount.mapper.CashAccountOrderMapper;
import com.hummingbird.maaccount.mapper.DiscountCardAccountMapper;
import com.hummingbird.maaccount.mapper.DiscountCardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.InvestmentAccountObjectOrderMapper;
import com.hummingbird.maaccount.mapper.InvestmentAccountRemainingOrderMapper;
import com.hummingbird.maaccount.mapper.JifenAccountMapper;
import com.hummingbird.maaccount.mapper.JifenAccountOrderMapper;
import com.hummingbird.maaccount.mapper.JifenProductMapper;
import com.hummingbird.maaccount.mapper.OfflineRechargeMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.OrderMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.RedPaperAccountMapper;
import com.hummingbird.maaccount.mapper.RedPaperAccountOrderMapper;
import com.hummingbird.maaccount.mapper.RedPaperProductMapper;
import com.hummingbird.maaccount.mapper.TerminalListMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountOrderMapper;
import com.hummingbird.maaccount.service.AccountPayAllowService;
import com.hummingbird.maaccount.service.AccountService;
import com.hummingbird.maaccount.service.CashAccountService;
import com.hummingbird.maaccount.service.DiscountCardAccountService;
import com.hummingbird.maaccount.service.InvestmentAccountService;
import com.hummingbird.maaccount.service.JifenAccountService;
import com.hummingbird.maaccount.service.OilcardAccountService;
import com.hummingbird.maaccount.service.OrderService;
import com.hummingbird.maaccount.service.RedPaperAccountService;
import com.hummingbird.maaccount.service.VolumecardAccountService;
import com.hummingbird.maaccount.util.AccountGenerationUtil;
import com.hummingbird.maaccount.util.AccountServiceFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.util.OrderFactory;
import com.hummingbird.maaccount.vo.AppVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.CashAccountOrderVO;
import com.hummingbird.maaccount.vo.DefaultReceipt;
import com.hummingbird.maaccount.vo.DiscountCardOrderVO;
import com.hummingbird.maaccount.vo.JifenOrderVO;
import com.hummingbird.maaccount.vo.JournalOrderOutputVO;
import com.hummingbird.maaccount.vo.OfflineOpencardOrderVO;
import com.hummingbird.maaccount.vo.OfflinePayOrderConsumerVO;
import com.hummingbird.maaccount.vo.OfflinePayOrderVO;
import com.hummingbird.maaccount.vo.OfflinePayOrderVO2;
import com.hummingbird.maaccount.vo.OfflineRechargeOutputVO;
import com.hummingbird.maaccount.vo.OilcardOrderVO;
import com.hummingbird.maaccount.vo.OrderQueryTransVO;
import com.hummingbird.maaccount.vo.OrderVO;
import com.hummingbird.maaccount.vo.OrderWithdrawDetailOutputVO;
import com.hummingbird.maaccount.vo.OrderdetailOutputBaseVO;
import com.hummingbird.maaccount.vo.RechargeOutputVO;
import com.hummingbird.maaccount.vo.RedPaperOrderVO;
import com.hummingbird.maaccount.vo.SpendJifenOrderVO;
import com.hummingbird.maaccount.vo.SpendOrderOutputVO;
import com.hummingbird.maaccount.vo.SpendOrderQueryPagingVO;
import com.hummingbird.maaccount.vo.SpendRedPaperOrderVO;
import com.hummingbird.maaccount.vo.TokenCheckResultVO;
import com.hummingbird.maaccount.vo.TransOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;
import com.hummingbird.maaccount.vo.TransOrderWithConsumerVO;
import com.hummingbird.maaccount.vo.UndoRedPaperTransOrderVO;
import com.hummingbird.maaccount.vo.VolumecardOrderVO;
import com.hummingbird.maaccount.vo.YouMeDiscountCardOrderVO;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午6:19:55
 * 本类主要做为帐户转换的具体实现类
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	@Autowired
	OfflineRechargeMapper offineRechargeDao;
	@Autowired
	RedPaperAccountMapper rpaDao;
	@Autowired
	CashAccountOrderMapper caoDao;
	
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	OrderMapper orderdao;
	@Autowired
	protected TerminalListMapper tlDao;//终端列表
	
	@Autowired
	protected AccountPayAllowService accountPayAllowService;
	

	/**
	 * 投资帐户的购买有油贷订单处理
	 * @param transorder 原订单信息
	 * @param fromaccount  金额流出帐户
	 * @param toaccount    金额注入帐户
	 * @return 票据信息，如果是多表操作，返回第一步的操作票据
	 * @throws MaAccountException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOrderIaYYD(TransOrderVO transorder, Account fromaccount,
			Account toaccount, InvestmentAccountObjectOrder oriorder) throws MaAccountException{
		if (log.isDebugEnabled()) {
			log.debug(String.format("投资帐户的购买有油贷订单处理开始"));
		}
		//在投资账户的冻结金额中减少支付金额的数量；
//		在投资账户的标的订单中修改该订单状态为OK#；
//		在投资账户的标的总额增加支付金额数量；
		oriorder.setStatus(OrderConst.ORDER_STATUS_OK);
		oriorder.setUpdateTime(new Date());
		InvestmentAccountObjectOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(InvestmentAccountObjectOrderMapper.class);
		boolean updateordersuccess = 1==orderMapper.updateOrder(oriorder);
		if(!updateordersuccess){
			if (log.isDebugEnabled()) {
				log.debug(String.format("更新投资帐户标的订单失败"));
			}
			throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
		}
		AccountService fromaccSrv = getAccountService(fromaccount);
		InvestmentAccount account = (InvestmentAccount) fromaccount;
		account.setFrozensum(account.getFrozensum()-Math.abs(oriorder.getSum()));
		account.setObjectsum(account.getObjectsum()+Math.abs(oriorder.getSum()));
		AccountValidateUtil.updateAccountSignature(account);
		boolean fromsuccess =   fromaccSrv.updateAccount(account);
		if(!fromsuccess){
			if (log.isDebugEnabled()) {
				log.debug(String.format("更新订单失败，可能冻结金额不足以扣款"));
			}
			throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
		}
//		AccountService toaccSrv = getAccountService(toaccount);
//		((InvestmentAccount)toaccount).setObjectsum(((InvestmentAccount)toaccount).getObjectsum()+oriorder.getBalance());
//		toaccSrv.updateAccount(toaccount);
		//调整可用余额的订单的冻结金额
		InvestmentAccountRemainingOrderMapper remainingorderMapper = SpringBeanUtil.getInstance().getBean(InvestmentAccountRemainingOrderMapper.class);
		InvestmentAccountRemainingOrder remainorder = remainingorderMapper.selectByPrimaryKey(oriorder.getOriginalorderId());
		remainorder.setFrozenSumSnapshot(remainorder.getFrozenSumSnapshot()-Math.abs(oriorder.getSum()));
		remainorder.setObjectSumSnapshot(remainorder.getObjectSumSnapshot()+Math.abs(oriorder.getSum()));
		remainingorderMapper.updateOrder(remainorder);
		if (log.isDebugEnabled()) {
			log.debug(String.format("投资帐户的购买有油贷订单处理完成"));
		}
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(oriorder.getOrderId());
		receipt.setDealtime(oriorder.getUpdateTime());
		return receipt;
	}
	
	/**
	 * 订单处理
	 * @param transorder 原订单信息
	 * @param fromaccount  金额流 出帐户
	 * @param toaccount    金额注入帐户
	 * @return 
	 * @throws MaAccountException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOrder(TransOrderVO transorder, Account fromaccount,
			Account toaccount) throws MaAccountException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("订单处理开始"));
		}
		if (log.isTraceEnabled()) {
			log.trace(String.format("创建支付订单"));
		}
		Receipt receipt=null;
		Order expenseOrder = getExpenseOrder(transorder,fromaccount,toaccount);
		if(expenseOrder!=null){
			if(OrderConst.ORDER_OPERATION_FEB.equals(transorder.getOperationType())){
				if (log.isDebugEnabled()) {
					log.debug(String.format("当前订单类型为废标，设置支出订单（标的订单）的状态为冻结"));
				}
				((AbstractOrder)expenseOrder).setStatus(OrderConst.ORDER_STATUS_FROZEN);
			}
			transorder.setOriginalOrderId(expenseOrder.getOrderId());
			transorder.setOriginalTable(OrderFactory.getOrderTable(fromaccount));
			//投资帐户可用余额的订单，增加冻结和标的余额的快照，这里要判断对方的帐户是否也是投资帐户，如果是，则需要调整快照的值
			if (expenseOrder instanceof InvestmentAccountRemainingOrder) {
				InvestmentAccountRemainingOrder iaro = (InvestmentAccountRemainingOrder) expenseOrder;
				if (toaccount instanceof InvestmentAccount) {
					InvestmentAccount toiaacc = (InvestmentAccount) toaccount;
					if(toiaacc.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_FREEZE){
						//转移到冻结帐户
						iaro.setFrozenSumSnapshot(((InvestmentAccount)fromaccount).getFrozensum()+Math.abs(iaro.getSum()));
					}
					else if(toiaacc.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_OBJECT){
						//转移到标的帐户
						iaro.setObjectSumSnapshot(((InvestmentAccount)fromaccount).getObjectsum()+Math.abs(iaro.getSum()));
					}
					
				}
				
			}
			AccountService fromaccSrv = getAccountService(fromaccount);
			receipt=fromaccSrv.expense(expenseOrder);
		}
		else{
			if (log.isTraceEnabled()) {
				log.trace(String.format("支付订单为空，不处理"));
			}
		}
		if (log.isTraceEnabled()) {
			log.trace(String.format("创建收入订单"));
		}
		Order incomeOrder = getIncomeOrder(transorder,fromaccount,toaccount);
		if(incomeOrder!=null)
		{
			if(OrderConst.ORDER_OPERATION_TOB.equals(transorder.getOperationType())){
				if (log.isDebugEnabled()) {
					log.debug(String.format("当前订单类型为投标，设置收入订单（标的订单）的状态为冻结"));
				}
				((AbstractOrder)incomeOrder).setStatus(OrderConst.ORDER_STATUS_FROZEN);
			}
			//投资帐户可用余额的订单，增加冻结和标的余额的快照，这里要判断对方的帐户是否也是投资帐户，如果是，则需要调整快照的值
			if (incomeOrder instanceof InvestmentAccountRemainingOrder) {
				InvestmentAccountRemainingOrder iaro = (InvestmentAccountRemainingOrder) incomeOrder;
				if (fromaccount instanceof InvestmentAccount) {
					InvestmentAccount fromiaacc = (InvestmentAccount) fromaccount;
					if(fromiaacc.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_FREEZE){
						//转移到冻结帐户
						iaro.setFrozenSumSnapshot(((InvestmentAccount)toaccount).getFrozensum()-Math.abs(iaro.getSum()));
					}
					else if(fromiaacc.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_OBJECT){
						//转移到标的帐户
						iaro.setObjectSumSnapshot(((InvestmentAccount)toaccount).getObjectsum()-Math.abs(iaro.getSum()));
					}
					
				}
				
			}
			AccountService toaccSrv = getAccountService(toaccount);
			
			Receipt income = toaccSrv.income(incomeOrder);
			if(receipt==null){
				receipt = income;
			}
		}
		else{
			if (log.isTraceEnabled()) {
				log.trace(String.format("收入订单为空，不处理"));
			}
			
		}
		return receipt;
	}
	
	/**
	 * 提现申请订单处理，投资帐户间的互处理
	 * @param transorder 原订单信息
	 * @param fromaccount  金额流 出帐户
	 * @param toaccount    金额注入帐户
	 * @return 
	 * @throws MaAccountException 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOrder4withdraw_to_freeze(TransOrderVO transorder, Account fromaccount,
			Account toaccount) throws MaAccountException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("提现冻结,订单处理开始"));
		}
		if (log.isTraceEnabled()) {
			log.trace(String.format("创建支付订单"));
		}
		Receipt receipt=null;
		AbstractOrder expenseOrder = (AbstractOrder) getExpenseOrder(transorder,fromaccount,toaccount);
		InvestmentAccountRemainingOrder poundageorder = null;
		int poundage= 0;
		PropertiesUtil pu = new PropertiesUtil();
		int checkpoundage = pu.getInt("poundage.lt.money", 10000);
		if(expenseOrder!=null){
			//modify by huangjiej 如果提现金额少于100元,则要给出2元手续费,实现上要拆成2条订单,且手续费的订单的类型为手续费
			if(Math.abs(expenseOrder.getSum())<checkpoundage){
				if (log.isDebugEnabled()) {
					log.debug(String.format("订单%s的提现金额小于100元,收2元手续费",expenseOrder.getOrderId()));
				}
				poundage = pu.getInt("poundage.whenlt.money", 200);
				expenseOrder.setSum(expenseOrder.getSum()+poundage);//取值是负数
				try {
					poundageorder = (InvestmentAccountRemainingOrder) BeanUtils.cloneBean(expenseOrder);
				} catch (IllegalAccessException | InstantiationException
						| InvocationTargetException | NoSuchMethodException e) {
					log.error(String.format("复制支出订单失败"),e);
					throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"生成手续费订单出错",e);
				}
				poundageorder.setOrderId(NoGenerationUtil.genOrderNo());
				poundageorder.setInsertTime(DateUtil.dateAdd(poundageorder.getInsertTime(), Calendar.SECOND, -1));
				poundageorder.setSum(0-poundage);
				poundageorder.setFrozenSumSnapshot(((InvestmentAccount)fromaccount).getFrozensum()+poundage);
				poundageorder.setAppOrderId(poundageorder.getAppOrderId()+"_1");
				poundageorder.setBalance(((InvestmentAccount)fromaccount).getRemainingsum()-poundage);
				poundageorder.setExternalOrderId(expenseOrder.getOrderId());
				poundageorder.setExternalOrderTime(expenseOrder.getInsertTime());
				poundageorder.setType(OrderConst.ORDER_OPERATION_POUNDAGE);
				poundageorder.setRemark(String.format("提现%s元的手续费(%s元)",MoneyUtil.getMoneyStringDecimal2fen(transorder.getOrder().getSum()),MoneyUtil.getMoneyStringDecimal2fen(poundage)));
				poundageorder.setPeerAccountId(null);
//				expenseOrder.setRemark(expenseOrder.getRemark()+"(含"+MoneyUtil.getMoneyStringDecimal2fen(poundage)+"元手续费)");
			}
			
			//添加参数中的PeerAccountId ,PeerAccountUnit 作 为参数
			AbstractOrder ao = (AbstractOrder) expenseOrder;
			ao.setPeerAccountId(transorder.getOrder().getPeerAccountId());
			ao.setPeerAccountType("TA#");
			ao.setPeerAccountUnit(transorder.getOrder().getPeerAccountUnit());
			//冻结提现，可用余额减少
			((AbstractOrder)ao).setBalance(((InvestmentAccount)fromaccount).getRemainingsum()-Math.abs(transorder.getOrder().getSum()));
			InvestmentAccountRemainingOrder iaro = (InvestmentAccountRemainingOrder) expenseOrder;
			//InvestmentAccount toiaacc = (InvestmentAccount) toaccount;
			iaro.setFrozenSumSnapshot(((InvestmentAccount)fromaccount).getFrozensum()+Math.abs(transorder.getOrder().getSum()));
//			if (expenseOrder instanceof InvestmentAccountRemainingOrder) {
//				if (toaccount instanceof InvestmentAccount) {
//					if(toiaacc.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_FREEZE){
//						//转移到冻结帐户
//					}
//					else if(toiaacc.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_OBJECT){
//						//转移到标的帐户
//						iaro.setObjectSumSnapshot(((InvestmentAccount)fromaccount).getObjectsum()+Math.abs(iaro.getSum()));
//					}
//				}
//			}
			transorder.setOriginalOrderId(expenseOrder.getOrderId());
			transorder.setOriginalTable(OrderFactory.getOrderTable(fromaccount));
			AccountService fromaccSrv = getAccountService(fromaccount);
			if(poundageorder!=null){
				fromaccSrv.expense(poundageorder);
			}
			receipt	=fromaccSrv.expense(expenseOrder);
		}
		else{
			if (log.isTraceEnabled()) {
				log.trace(String.format("支付订单为空，不处理"));
			}
		}
		//提现申请不产生收入订单,但是过程需要,因为要在帐户中产生冻结收入,这时可以不用管手续费
		if (log.isTraceEnabled()) {
			log.trace(String.format("创建收入订单"));
		}
		AbstractOrder incomeOrder = (AbstractOrder) getIncomeOrder(transorder,fromaccount,toaccount);
		InvestmentAccountRemainingOrder poundageInorder = null;
		if(incomeOrder!=null)
		{
			//modify by huangjiej 如果提现金额少于100元,则要给出2元手续费,实现上要拆成2条订单,且手续费的订单的类型为手续费
			//冻结金额要减去2元
//			incomeOrder.setSum(incomeOrder.getSum()-poundage);
			//冻结提现，冻结余额增加
			((AbstractOrder)incomeOrder).setBalance(((InvestmentAccount)fromaccount).getRemainingsum()+Math.abs(transorder.getOrder().getSum()));
			//投资帐户可用余额的订单，增加冻结和标的余额的快照，这里要判断对方的帐户是否也是投资帐户，如果是，则需要调整快照的值
			if (incomeOrder instanceof InvestmentAccountRemainingOrder) {
				InvestmentAccountRemainingOrder iaro = (InvestmentAccountRemainingOrder) incomeOrder;
				if (fromaccount instanceof InvestmentAccount) {
					InvestmentAccount fromiaacc = (InvestmentAccount) fromaccount;
					if(fromiaacc.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_FREEZE){
						//转移到冻结帐户
						iaro.setFrozenSumSnapshot(((InvestmentAccount)toaccount).getFrozensum()-Math.abs(iaro.getSum()));
					}
					else if(fromiaacc.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_OBJECT){
						//转移到标的帐户
						iaro.setObjectSumSnapshot(((InvestmentAccount)toaccount).getObjectsum()-Math.abs(iaro.getSum()));
					}
					
				}
				
			}
			InvestmentAccountService toaccSrv = (InvestmentAccountService) getAccountService(toaccount);
			int incomeWithFrozen = toaccSrv.incomeWithFrozen(incomeOrder, toaccount);
			if(incomeWithFrozen==0){
				throw new MaAccountException(25301,"投资帐户冻结金额添加失败");
			}
		}
		else{
			if (log.isTraceEnabled()) {
				log.trace(String.format("收入订单为空，不处理"));
			}
			
		}
		return receipt;
	}
	/**
	 * 提现撤销申请订单处理，投资帐户间的互处理
	 * @param transorder 原订单信息
	 * @param fromaccount  金额流 出帐户
	 * @param toaccount    金额注入帐户
	 * @return 
	 * @throws MaAccountException 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOrder4withdraw_to_unfreeze(TransOrderVO transorder, Account fromaccount,
			Account toaccount) throws MaAccountException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("订单处理开始"));
		}
		if (log.isTraceEnabled()) {
			log.trace(String.format("创建支付订单"));
		}
		Receipt receipt=null;
		Order expenseOrder = getExpenseOrder(transorder,fromaccount,toaccount);
		if(expenseOrder!=null){
			//冻结提现撤销，冻结余额减少
			((AbstractOrder)expenseOrder).setBalance(((InvestmentAccount)fromaccount).getRemainingsum()+Math.abs(transorder.getOrder().getSum()));
			if (expenseOrder instanceof InvestmentAccountRemainingOrder) {
				InvestmentAccountRemainingOrder iaro = (InvestmentAccountRemainingOrder) expenseOrder;
				iaro.setFrozenSumSnapshot(((InvestmentAccount)fromaccount).getFrozensum()-Math.abs(iaro.getSum()));
				
			}
			InvestmentAccountService fromaccSrv = (InvestmentAccountService) getAccountService(fromaccount);
			int success = fromaccSrv.expenseWithFrozen(expenseOrder,fromaccount);
			if(success!=1){
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"投资帐户冻结金额扣减失败");
			}
		}

		if (log.isTraceEnabled()) {
			log.trace(String.format("创建收入订单"));
		}
		Order incomeOrder = getIncomeOrder(transorder,fromaccount,toaccount);
		if(incomeOrder!=null)
		{
			//冻结提现撤销，可用余额增加
			((AbstractOrder)incomeOrder).setBalance(((InvestmentAccount)fromaccount).getRemainingsum()+transorder.getOrder().getSum());
			if (incomeOrder instanceof InvestmentAccountRemainingOrder) {
				InvestmentAccountRemainingOrder iaro = (InvestmentAccountRemainingOrder) incomeOrder;
				if (fromaccount instanceof InvestmentAccount) {
					InvestmentAccount fromiaacc = (InvestmentAccount) fromaccount;
					if(fromiaacc.getSumTarget()==InvestmentAccount.SUM_TARGET_TYPE_FREEZE){
						//转移到冻结帐户
						iaro.setFrozenSumSnapshot(((InvestmentAccount)toaccount).getFrozensum()-Math.abs(iaro.getSum()));
					}
					
				}
				
			}
			InvestmentAccountService toaccSrv = (InvestmentAccountService) getAccountService(toaccount);
			receipt = toaccSrv.income(incomeOrder);
		}
		else{
			if (log.isTraceEnabled()) {
				log.trace(String.format("收入订单为空，不处理"));
			}
			
		}
		return receipt;
	}
	
	/**
	 * 提现成功接口
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOrder4withdraw_success(TransOrderVO transorder,
			InvestmentAccount fromaccount, Account toaccount) throws MaAccountException{
		if (log.isDebugEnabled()) {
			log.debug(String.format("提现成功,订单处理开始"));
		}
		if (log.isTraceEnabled()) {
			log.trace(String.format("创建支付订单"));
		}
		DefaultReceipt receipt=new DefaultReceipt();
		AbstractOrder expenseOrder = (AbstractOrder) getExpenseOrder(transorder,fromaccount,toaccount);
		if(expenseOrder!=null){
			
			transorder.setOriginalOrderId(expenseOrder.getOrderId());
			transorder.setOriginalTable(OrderFactory.getOrderTable(fromaccount));
			//冻结提现成功，可用余额不变
			((AbstractOrder)expenseOrder).setBalance(((InvestmentAccount)fromaccount).getRemainingsum());
			if (expenseOrder instanceof InvestmentAccountRemainingOrder) {
				InvestmentAccountRemainingOrder iaro = (InvestmentAccountRemainingOrder) expenseOrder;
						//转移到冻结帐户
				iaro.setFrozenSumSnapshot(((InvestmentAccount)fromaccount).getFrozensum()-Math.abs(iaro.getSum()));
				
			}
			InvestmentAccountService fromaccSrv = (InvestmentAccountService) getAccountService(fromaccount);
			int success = 1;
			success=fromaccSrv.expenseWithFrozen(expenseOrder, fromaccount);
			if(1==success){
				//更新订单
				int createOrdersuccess = fromaccSrv.createOrder(expenseOrder);
				receipt.setOrderNo(expenseOrder.getOrderId());
				receipt.setDealtime(new Date());
				if(createOrdersuccess!=1){
					throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
				}
			}
			else{
				//更新订单不成功
				if (log.isDebugEnabled()) {
					log.debug(String.format("订单支出，从帐户扣减金额不成功，帐户余额不足"));
				}
				throw new InsufficientAccountBalanceException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"帐户余额不足");
			}
		}
		else{
			if (log.isTraceEnabled()) {
				log.trace(String.format("支付订单为空，不处理"));
			}
		}
		return receipt;
	}
	
	/**
	 * 创建收入订单
	 * @param transorder
	 * @param toaccount
	 * @return
	 * @throws MaAccountException 
	 */
	private Order getIncomeOrder(TransOrderVO transorder,Account fromaccount, Account toaccount) throws MaAccountException {
		Order incomeorder = OrderFactory.createIncomeOrder(transorder,fromaccount,toaccount);
		return incomeorder;
	}

	/**
	 * 获取相应的帐户信息
	 * @param fromaccount
	 * @return
	 * @throws MaAccountException 
	 */
	private AccountService getAccountService(Account fromaccount) throws MaAccountException {
		return AccountServiceFactory.getAccountService(fromaccount); 
	}

	/**
	 * 创建支付订单
	 * @param transorder
	 * @param fromaccount
	 * @return
	 * @throws MaAccountException 
	 */
	public Order getExpenseOrder(TransOrderVO transorder, Account fromaccount, Account toaccount) throws MaAccountException
	{
		//检查帐户余额是否足够
		if(!fromaccount.checkBalanceLimit(transorder.getOrder().getSum())){
			throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,fromaccount.getAccountName()+"余额不足");
		}
		Order expenseOrder = OrderFactory.createExpenseOrder(transorder,fromaccount,toaccount);
		return expenseOrder;
	}
	
	/**
	 * 查询订单
	 * @param table
	 * @param orderId
	 * @return
	 */
	@Override
	public Order getOrder(String table,String orderId){
		if (log.isDebugEnabled()) {
			log.debug(String.format("根据表名查询订单开始"));
		}
		try{
			if("T_CASH_ACCOUNT_ORDER".equalsIgnoreCase(table)){
				CashAccountOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
				return orderMapper.selectByPrimaryKey(orderId);
			}
			else if("t_investment_account_object_order".equalsIgnoreCase(table)){
				InvestmentAccountObjectOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(InvestmentAccountObjectOrderMapper.class);
				return orderMapper.selectByPrimaryKey(orderId);
			}
			else if("t_investment_account_remaining_order".equalsIgnoreCase(table)){
				InvestmentAccountRemainingOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(InvestmentAccountRemainingOrderMapper.class);
				return orderMapper.selectByPrimaryKey(orderId);
			}
			else{
				
				if (log.isDebugEnabled()) {
					log.debug(String.format("表名%s在帐户中不存在，无法查询订单",table));
				}
			}
			
		}
		finally{
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("根据表名查询订单开始"));
		}
		return null;
	}
	
	/**
	 * 分页查询订单
	 * @param pagingnation
	 * @param filter
	 * @return
	 * @throws MaAccountException 获取不到相应的dao
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true,value="txManager")
	public List<OrderdetailOutputBaseVO> queryOrder(Pagingnation pagingnation,Map filter) throws MaAccountException{
		
		AccountOrderDao orderDao = OrderFactory.getOrderDao(ObjectUtils.toString(filter.get("accountCode")),ObjectUtils.toString( filter.get("accountadditional")));
		int total = orderDao.queryOrderTotalByPage(pagingnation, filter);
		pagingnation.setTotalCount(total);
		List<OrderdetailOutputBaseVO> orders = orderDao.queryOrderByPage(pagingnation, filter);
		return orders;
	}
	
	/**
	 * 获取以指定的orderid为订单的订单，需要指定在哪个订单表中查询
	 * @param ordertable
	 * @param oriorderid
	 * @return
	 */
	public List<Order> getRelaOrder(String table,String oriorderid){
		if(OrderConst.ORDER_TABLE_CASH.equalsIgnoreCase(table)){
			CashAccountOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
			return orderMapper.selectByOriginalOrderId(oriorderid);
		}
		else if(OrderConst.ORDER_TABLE_INVESTMENT_OBJECT.equalsIgnoreCase(table)){
			InvestmentAccountObjectOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(InvestmentAccountObjectOrderMapper.class);
			return orderMapper.selectByOriginalOrderId(oriorderid);
		}
		else if(OrderConst.ORDER_TABLE_INVESTMENT_REMAIN.equalsIgnoreCase(table)){
			InvestmentAccountRemainingOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(InvestmentAccountRemainingOrderMapper.class);
			return orderMapper.selectByOriginalOrderId(oriorderid);
		}
		else if(OrderConst.ORDER_TABLE_DIS_CARD.equalsIgnoreCase(table)){
			DiscountCardAccountOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(DiscountCardAccountOrderMapper.class);
			return orderMapper.selectByOriginalOrderId(oriorderid);
		}
		else if(OrderConst.ORDER_TABLE_VOLUME_CARD.equalsIgnoreCase(table)){
			VolumecardAccountOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(VolumecardAccountOrderMapper.class);
			return orderMapper.selectByOriginalOrderId(oriorderid);
		}
		return Collections.EMPTY_LIST;
	}
	
	/**
	 * 从余额订单中查询冻结的标的订单
	 * @param oriorderid
	 * @return
	 */
	public InvestmentAccountObjectOrder getFreezeObjectOrder(String remainOrderId){
		InvestmentAccountObjectOrderMapper orderMapper = SpringBeanUtil.getInstance().getBean(InvestmentAccountObjectOrderMapper.class);
		List<Order> orders = orderMapper.selectByOriginalOrderId(remainOrderId);
		for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
			InvestmentAccountObjectOrder order = (InvestmentAccountObjectOrder) iterator.next();
			//指定是冻结的订单
			if("/investmentAccount/freeze_yyd".equalsIgnoreCase(order.getMethod())||"/investmentAccount/auto_freeze_yyd".equalsIgnoreCase(order.getMethod())){
				return order;
			}
		}
		return null;
	}
	
	/**
	 * 查询提现申请
	 * @param pagingnation
	 * @param filter
	 * @return
	 */
	public List<OrderWithdrawDetailOutputVO> queryWithdrawOrder(
			Pagingnation pagingnation, Map filter){
		InvestmentAccountRemainingOrderMapper orderDao = SpringBeanUtil .getInstance().getBean(InvestmentAccountRemainingOrderMapper.class);
		int total = orderDao.queryFreezeOrderTotalByPage(pagingnation, filter);
		pagingnation.setTotalCount(total);
		List<OrderWithdrawDetailOutputVO> orders = orderDao.queryFreezeOrderByPage(pagingnation, filter);
		return orders;
	}

	/**
	 * 提交线下充值凭据
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt savingByUser(TransOrderVO transorder, Account sourceacc,
			InvestmentAccount descacc) throws MaAccountException{
		//在线下充值表中增加一条线下充值记录
		OfflineRecharge order=new OfflineRecharge();
		OrderVO ordervo = transorder.getOrder();
		AppVO app = transorder.getApp();
		String orderNo = NoGenerationUtil.genOrderNo();
		order.setAppId(app.getAppId());
		order.setAppName(app.getAppname());
		order.setAccountId(descacc.getAccountId());
		order.setInsertTime(new Date());
		order.setUpdateTime(new Date());
		order.setOrderId(orderNo);
		order.setSum(ordervo.getSum());
		order.setAppOrderId(ordervo.getAppOrderId());
		order.setRemark(ordervo.getRemark());
		order.setMethod(transorder.getMethod());
		order.setStatus(OrderConst.ORDER_STATUS_SAVING_WAIT);
		order.setPeerAccountId(ordervo.getPeerAccountId());
		order.setPeerAccountUnit(ordervo.getPeerAccountUnit());
		order.setExternalOrderId(ordervo.getExternalOrderId());
		if(StringUtils.isNotBlank(transorder.getOrder().getExternalOrderTime())){
			try {
				order.setExternalOrderTime(DateUtil.parse(ordervo.getExternalOrderTime()).getTime());
			} catch (ParseException e) {
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"外部交易时间格式不正确");
			}
			
		}
//		order.setAccountOrderId(accountOrderId);
		offineRechargeDao.createOrder(order);
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setDealtime(new Date());
		receipt.setOrderNo(orderNo);
		return receipt;
	}
	
	/**
	 * 分页查询线下充值
	 * @param pagingnation
	 * @param filter
	 * @return
	 */
	public List<com.hummingbird.maaccount.vo.OfflineRechargeOutputVO> queryOfflineRecharge(
			Pagingnation pagingnation, Map filter){
		int total = offineRechargeDao.queryOrderTotalByPage(pagingnation, filter);
		pagingnation.setTotalCount(total);
		List<OfflineRechargeOutputVO> orders = offineRechargeDao.queryOrderByPage(pagingnation, filter);
		return orders;
	}
	/**
	 * 分页查询线上、线下充值
	 * @param pagingnation
	 * @param filter
	 * @return
	 */
	@Override
	public List<RechargeOutputVO> queryRecharge(
			Pagingnation pagingnation, Map filter){
		int total = offineRechargeDao.queryRechargeOrderTotalByPage(pagingnation, filter);
		pagingnation.setTotalCount(total);
		List<RechargeOutputVO> orders = offineRechargeDao.queryRechargeOrderByPage(pagingnation, filter);
		return orders;
	}
	
	/**
	 * 线下充值成功
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt savingSuccess(TransOrderVO transorder, Account fromaccount,
			InvestmentAccount toaccount,OfflineRecharge oriorder) throws MaAccountException{
		
		transorder.getOrder().setSum(oriorder.getSum());
		//生成收入订单
		AbstractOrder incomeOrder = (AbstractOrder) getIncomeOrder(transorder,fromaccount,toaccount);
		DefaultReceipt receipt=null;
		if(incomeOrder!=null)
		{
			//线下充值成功，可用余额增加
			((AbstractOrder)incomeOrder).setBalance(((InvestmentAccount)toaccount).getRemainingsum()+oriorder.getSum());
			if (incomeOrder instanceof InvestmentAccountRemainingOrder) {
				InvestmentAccountRemainingOrder iaro = (InvestmentAccountRemainingOrder) incomeOrder;
				if (toaccount instanceof InvestmentAccount) {
					InvestmentAccount toiaacc = (InvestmentAccount) toaccount;
					iaro.setFrozenSumSnapshot(toiaacc.getFrozensum());
					iaro.setObjectSumSnapshot(toiaacc.getObjectsum());
				}
				
			}
			incomeOrder.setPeerAccountId(oriorder.getPeerAccountId());
			incomeOrder.setPeerAccountUnit(oriorder.getPeerAccountUnit());
			incomeOrder.setPeerAccountType("TA#");
			
			
			AccountService toaccSrv = getAccountService(toaccount);
			Receipt income = toaccSrv.income(incomeOrder);
			if(income==null){
				throw new MaAccountException(25901,"线下充值确认失败");
			}
//			InvestmentAccountService toaccSrv = (InvestmentAccountService) getAccountService(toaccount);
//			int incomeret = toaccSrv.income(incomeOrder, toaccount);
//			if(incomeret==0){
//				throw new MaAccountException(25901,"线下充值确认失败");
//			}
			//调整原来的订单为成功
			oriorder.setStatus(OrderConst.ORDER_STATUS_SAVING_SUCCESS);
			oriorder.setAccountOrderId(incomeOrder.getOrderId());
			oriorder.setUpdateTime(new Date());
			offineRechargeDao.updateOrder(oriorder);
//			receipt = new DefaultReceipt();
//			receipt.setDealtime(incomeOrder.getUpdateTime());
//			receipt.setOrderNo(incomeOrder.getOrderId());
			return income;
		}
		else{
			if (log.isTraceEnabled()) {
				log.trace(String.format("收入订单为空，抛出异常"));
			}
			throw new MaAccountException(25902,"线下充值确认失败:创建订单失败");
		}
//		return receipt;
	}
	/**
	 * 线下充值撤销
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt savingCancle(TransOrderVO transorder, Account sourceacc,
			InvestmentAccount descacc,OfflineRecharge oriorder) throws MaAccountException{
		if (log.isDebugEnabled()) {
			log.debug(String.format("线下充值撤销"));
		}
		//更改充值申请状态
		oriorder.setStatus(OrderConst.ORDER_STATUS_SAVING_CANCLE);
		oriorder.setUpdateTime(new Date());
		int updateOrderret = offineRechargeDao.updateOrder(oriorder);
		if(updateOrderret!=1){
			throw new MaAccountException(26001,"线下充值撤销失败");
		}
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setDealtime(new Date());
		receipt.setOrderNo(oriorder.getOrderId());
		return receipt;
	}
	
//	/**
//	 * 订单处理
//	 * @param transorder 原订单信息
//	 * @param fromaccount  金额流 出帐户
//	 * @param toaccount    金额注入帐户
//	 * @return 
//	 * @throws MaAccountException 
//	 */
//	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
//	public Receipt processOrder(TransOrderVO2 transorder, Account fromaccount,
//			Account toaccount) throws MaAccountException {
//		if (log.isDebugEnabled()) {
//			log.debug(String.format("订单处理开始"));
//		}
//		if (log.isTraceEnabled()) {
//			log.trace(String.format("创建支付订单"));
//		}
//		Receipt receipt=null;
//		Order expenseOrder = getExpenseOrder(transorder,fromaccount,toaccount);
//		if(expenseOrder!=null){
//			if(OrderConst.ORDER_OPERATION_FEB.equals(transorder.getOperationType())){
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("当前订单类型为废标，设置支出订单（标的订单）的状态为冻结"));
//				}
//				((AbstractOrder)expenseOrder).setStatus(OrderConst.ORDER_STATUS_FROZEN);
//			}
//			transorder.setOriginalOrderId(expenseOrder.getOrderId());
//			transorder.setOriginalTable(OrderFactory.getOrderTable(fromaccount));
//			AccountService fromaccSrv = getAccountService(fromaccount);
//			receipt=fromaccSrv.expense(expenseOrder);
//		}
//		else{
//			if (log.isTraceEnabled()) {
//				log.trace(String.format("支付订单为空，不处理"));
//			}
//		}
//		if (log.isTraceEnabled()) {
//			log.trace(String.format("创建收入订单"));
//		}
////		Order incomeOrder = getIncomeOrder(transorder,fromaccount,toaccount);
////		if(incomeOrder!=null)
////		{
////			if(OrderConst.ORDER_OPERATION_TOB.equals(transorder.getOperationType())){
////				if (log.isDebugEnabled()) {
////					log.debug(String.format("当前订单类型为投标，设置收入订单（标的订单）的状态为冻结"));
////				}
////				((AbstractOrder)incomeOrder).setStatus(OrderConst.ORDER_STATUS_FROZEN);
////			}
////			
////			AccountService toaccSrv = getAccountService(toaccount);
////			
////			Receipt income = toaccSrv.income(incomeOrder);
////			if(receipt==null){
////				receipt = income;
////			}
////		}
////		else{
////			if (log.isTraceEnabled()) {
////				log.trace(String.format("收入订单为空，不处理"));
////			}
////			
////		}
//		return receipt;
//	}

	/**
	 * @param transorder
	 * @param fromaccount
	 * @param toaccount
	 * @return
	 * @throws InsufficientAccountBalanceException 
	 */
	private Order getExpenseOrder(TransOrderVO2 transorder,
			Account fromaccount, Account toaccount) throws InsufficientAccountBalanceException {
//		IOrderVO order = transorder.getOrder();
//		if (order instanceof OrderVO) {
//			OrderVO ordervo = (OrderVO) order;
//			//检查帐户余额是否足够
//			if(!fromaccount.checkBalanceLimit(ordervo.getSum())){
//				throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,fromaccount.getAccountName()+"余额不足");
//			}
//		}
//		Order expenseOrder = OrderFactory.createExpenseOrder(transorder,fromaccount,toaccount);
//		return expenseOrder;
		throw new RuntimeException("此功能未实现");
	}
	
	/**
	 * 开卡
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws MaAccountException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOpenOilcard(TransOrderVO2<OilcardOrderVO> transorder,
			Account sourceacc, OilcardAccount descacc,OilcardAccountProduct product) throws MaAccountException {
		
		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		OilcardAccountMapper ocaccDao = SpringBeanUtil.getInstance().getBean(OilcardAccountMapper.class);
		AccountValidateUtil.updateAccountSignature(descacc);
		int createAccount = ocaccDao.createAccount(descacc);
		String orderNo=null;
		if(1==createAccount)
		{
			//添加订单
			OilcardAccountOrder oaOrder = new OilcardAccountOrder();
			OilcardOrderVO order = transorder.getOrder();
			AppVO app = transorder.getApp();
			orderNo = NoGenerationUtil.genOrderNo();
			oaOrder.setAppId(app.getAppId());
			oaOrder.setAppname(app.getAppname());
			oaOrder.setAccountId(descacc.getAccountId());
			oaOrder.setInsertTime(new Date());
			oaOrder.setUpdateTime(new Date());
			oaOrder.setOrderId(orderNo);
			oaOrder.setOriginalorderId(orderNo);
//		oaOrder.setProductName(product.getProductName());
			oaOrder.setSum(product.getReturnSum());
			oaOrder.setAppOrderId(order.getAppOrderId());
			oaOrder.setRemark(order.getRemark());
			oaOrder.setMethod(transorder.getMethod());
			oaOrder.setStatus("OK#");
			oaOrder.setBalance(commproduct.getProductAmount());
			oaOrder.setType(transorder.getOperationType());
			oaOrder.setPeerAccountId(null);
			oaOrder.setPeerAccountUnit(null);
			oaOrder.setPeerAccountType("TX#");
			oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			oaOrder.setChannelOrderId(order.getChannelOrderId());
			oaOrder.setProductName(commproduct.getProductName());
			oaOrder.setProductQuantity("1");
			oaOrder.setProductPrice(commproduct.getProductPrice().longValue());
			//更新订单
			OilcardAccountOrderMapper ocaccordDao = SpringBeanUtil.getInstance().getBean(OilcardAccountOrderMapper.class);
			int createOrdersuccess = ocaccordDao.createOrder(oaOrder);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(orderNo);
		return receipt;
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processBathOpenDiscountCard(
			TransOrderVO2<BatchOpenOnlineDetailVO> transorder,BatchOpenOnlineListVO order,
			Account sourceacc, DiscountCardAccount dcacc,
			DiscountCardProduct product) throws MaAccountException {
		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		DiscountCardAccountMapper daccDao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountMapper.class);
		AccountValidateUtil.updateAccountSignature(dcacc);
		int createAccount = daccDao.createAccount(dcacc);
		String orderNo=null;
		if(1==createAccount)
		{
			//添加订单
			DiscountCardAccountOrder oaOrder = new DiscountCardAccountOrder();
			AppVO app = transorder.getApp();
			orderNo = NoGenerationUtil.genOrderNo();
			oaOrder.setOrderId(orderNo);
			oaOrder.setAppId(app.getAppId());
			oaOrder.setAppname(app.getAppname());
			oaOrder.setAccountId(dcacc.getAccountId());
			oaOrder.setMethod(transorder.getMethod());
			oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			oaOrder.setSum(product.getReturnSum());
			oaOrder.setBalance(commproduct.getProductAmount());
			oaOrder.setPeerAccountType("DCA");//TODO,CONSTANT
			oaOrder.setPeerAccountId(null);
			oaOrder.setPeerAccountUnit(null);
			oaOrder.setRemark(order.getRemark());
			oaOrder.setOriginalorderId(orderNo);
			oaOrder.setStatus(Account.STATUS_OK);
			oaOrder.setInsertTime(new Date());
			oaOrder.setUpdateTime(new Date());
			oaOrder.setAppOrderId(transorder.getOrder().getAppOrderId());
			oaOrder.setType(transorder.getOperationType());
			oaOrder.setProductName(commproduct.getProductName());
			oaOrder.setProductPrice(commproduct.getProductPrice().longValue());
			oaOrder.setProductQuantity("1");
			oaOrder.setChannelOrderId(order.getChannelOrderId());
			
			//更新订单
			DiscountCardAccountOrderMapper dcaOrderDao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountOrderMapper.class);
			int createOrdersuccess = dcaOrderDao.createOrder(oaOrder);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(orderNo);
		return receipt;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOpenDiscountCard(
			TransOrderVO2<DiscountCardOrderVO> transorder, Account sourceacc,
			DiscountCardAccount dcacc, DiscountCardProduct product)
			throws MaAccountException {
		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		DiscountCardAccountMapper daccDao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountMapper.class);
		AccountValidateUtil.updateAccountSignature(dcacc);
		int createAccount = daccDao.createAccount(dcacc);
		String orderNo=null;
		if(1==createAccount)
		{
			//添加订单
			DiscountCardAccountOrder oaOrder = new DiscountCardAccountOrder();
			DiscountCardOrderVO order = transorder.getOrder();
			AppVO app = transorder.getApp();
			orderNo = NoGenerationUtil.genOrderNo();
			oaOrder.setOrderId(orderNo);
			oaOrder.setAppId(app.getAppId());
			oaOrder.setAppname(app.getAppname());
			oaOrder.setAccountId(dcacc.getAccountId());
			oaOrder.setMethod(transorder.getMethod());
			oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			oaOrder.setSum(product.getReturnSum());
			oaOrder.setBalance(commproduct.getProductAmount());
			oaOrder.setPeerAccountType("DCA");//TODO,CONSTANT
			oaOrder.setPeerAccountId(null);
			oaOrder.setPeerAccountUnit(null);
			oaOrder.setRemark(order.getRemark());
			oaOrder.setOriginalorderId(orderNo);
			oaOrder.setStatus(Account.STATUS_OK);
			oaOrder.setInsertTime(new Date());
			oaOrder.setUpdateTime(new Date());
			oaOrder.setAppOrderId(order.getAppOrderId());
			oaOrder.setType(transorder.getOperationType());
			oaOrder.setProductName(commproduct.getProductName());
			oaOrder.setProductPrice(commproduct.getProductPrice().longValue());
			oaOrder.setProductQuantity("1");
			oaOrder.setChannelOrderId(order.getChannelOrderId());
			
			//更新订单
			DiscountCardAccountOrderMapper dcaOrderDao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountOrderMapper.class);
			int createOrdersuccess = dcaOrderDao.createOrder(oaOrder);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(orderNo);
		return receipt;
	}
	
	/**
	 * 容量卡开卡
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws MaAccountException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOpenVolumecard(
			TransOrderVO2<VolumecardOrderVO> transorder, Account sourceacc,
			VolumecardAccount descacc, VolumecardAccountProduct product)
			throws MaAccountException {
		
		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		VolumecardAccountMapper ocaccDao = SpringBeanUtil.getInstance().getBean(VolumecardAccountMapper.class);
		AccountValidateUtil.updateAccountSignature(descacc);
		int createAccount = ocaccDao.createAccount(descacc);
		String orderNo=null;
		if(1==createAccount)
		{
			//添加订单
			VolumecardAccountOrder oaOrder = new VolumecardAccountOrder();
			VolumecardOrderVO order = transorder.getOrder();
			AppVO app = transorder.getApp();
			orderNo = NoGenerationUtil.genOrderNo();
			oaOrder.setAppId(app.getAppId());
			oaOrder.setAppname(transorder.getApp().getAppname());
			oaOrder.setAccountId(descacc.getAccountId());
			oaOrder.setInsertTime(new Date());
			oaOrder.setUpdateTime(new Date());
			oaOrder.setOrderId(orderNo);
//			oaOrder.setOriginalorderId(orderNo);
//		oaOrder.setProductName(product.getProductName());
			oaOrder.setSum(commproduct.getProductAmount());
			oaOrder.setAppOrderId(order.getAppOrderId());
			oaOrder.setRemark(order.getRemark());
			oaOrder.setMethod(transorder.getMethod());
			oaOrder.setStatus("OK#");
			oaOrder.setBalance(commproduct.getProductAmount());
			oaOrder.setType(transorder.getOperationType());
			oaOrder.setPeerAccountId(null);
			oaOrder.setPeerAccountUnit(null);
			oaOrder.setPeerAccountType("TX#");
			oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			oaOrder.setChannelOrderId(order.getChannelOrderId());
			oaOrder.setProductName(commproduct.getProductName());
			oaOrder.setProductQuantity("1");
			oaOrder.setProductPrice(commproduct.getProductAmount());
			//更新订单
			VolumecardAccountOrderMapper ocaccordDao = SpringBeanUtil.getInstance().getBean(VolumecardAccountOrderMapper.class);
			int createOrdersuccess = ocaccordDao.createOrder(oaOrder);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(orderNo);
		return receipt;
	}

	/**
	 * 添加红包
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws MaAccountException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processRedPaper(TransOrderVO2<RedPaperOrderVO> transorder,
			Account sourceacc, RedPaperAccount descacc,RedPaperProduct product)throws MaAccountException {
		
		RedPaperProductMapper productDao = SpringBeanUtil.getInstance().getBean(RedPaperProductMapper.class);
		RedPaperProduct commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		RedPaperAccountMapper ocaccDao = SpringBeanUtil.getInstance().getBean(RedPaperAccountMapper.class);
		int createAccount = ocaccDao.insert(descacc);
		String orderNo=null;
		if(1==createAccount)
		{
			//添加订单
			RedPaperAccountOrder rpOrder = new RedPaperAccountOrder();
			RedPaperOrderVO order = transorder.getOrder();
			AppVO app = transorder.getApp();
			orderNo = NoGenerationUtil.genOrderNo();
			rpOrder.setAppId(app.getAppId());
			rpOrder.setAppname(app.getAppname());
			rpOrder.setAccountId(descacc.getAccountId());
			rpOrder.setInsertTime(new Date());
			rpOrder.setUpdateTime(new Date());
			rpOrder.setOrderId(orderNo);
			rpOrder.setOriginalorderId(orderNo);
//		oaOrder.setProductName(product.getProductName());
			rpOrder.setSum(order.getSum());
			rpOrder.setAppOrderId(order.getAppOrderId());
			rpOrder.setRemark(order.getRemark());
			rpOrder.setMethod(transorder.getMethod());
			rpOrder.setStatus("OK#");
			
			rpOrder.setType(transorder.getOperationType());
			
			rpOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			rpOrder.setProductName(commproduct.getProductName());
			rpOrder.setProductQuantity("1");
			rpOrder.setProductPrice(order.getSum());
			//更新订单
			RedPaperAccountOrderMapper ocaccordDao = SpringBeanUtil.getInstance().getBean(RedPaperAccountOrderMapper.class);
			int createOrdersuccess = ocaccordDao.insert(rpOrder);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(orderNo);
		return receipt;
	}
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.OrderService#processOrder(com.hummingbird.maaccount.vo.TransOrderVO2, com.hummingbird.maaccount.face.Account, com.hummingbird.maaccount.entity.OilcardAccount)
	 */
	@Override
	public Receipt processOrder(TransOrderVO2<OilcardOrderVO> transorder,
			Account sourceacc, OilcardAccount descacc) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 线下支付（pos机支付),从现金帐户扣钱，如钱不足将会从分期卡扣钱
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,value="txManager")
	public Receipt payOffineByOilcard(TransOrderVO2<OfflinePayOrderVO2> transorder, CashAccount sourceacc,Account descacc) throws MaAccountException {
		//判断现金帐户的钱是否足够，不足从分期卡取
		Long requireSum = transorder.getOrder().getSum()-sourceacc.getBalance();
		if(requireSum>0){
			if (log.isDebugEnabled()) {
				log.debug(String.format("当前现金帐户【%s】余额不足，尝试从分期卡转移资金",sourceacc.getAccountId()));
			}
			OilcardAccountService oilcardAccountService = SpringBeanUtil.getInstance().getBean(OilcardAccountService.class);
			Receipt receipt = oilcardAccountService.transferOilcard2cashaccount(transorder,sourceacc, requireSum,true);
			boolean enoughmoney = BooleanUtils.toBoolean(ObjectUtils.toString(receipt.getExtValue("enouth"),"false"));
			if(!enoughmoney){
				if (log.isDebugEnabled()) {
					log.debug(String.format("当前现金帐户【%s】从分期卡转移资金后仍然余额不足",sourceacc.getAccountId()));
				}
				throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,"现金帐户余额不足");
			}
			sourceacc.setBalance(0L);
		}
		else{
			sourceacc.setBalance(sourceacc.getBalance()-transorder.getOrder().getSum());
			
		}
		//生成支出订单
		CashAccountOrder exorder = new CashAccountOrder();
		OfflinePayOrderVO order = transorder.getOrder();
		AppVO app = transorder.getApp();
		String orderNo = NoGenerationUtil.genOrderNo();
		exorder.setAppId(app.getAppId());
		exorder.setAppname(app.getAppname());
		exorder.setAccountId(sourceacc.getAccountId());
		exorder.setInsertTime(new Date());
		exorder.setUpdateTime(new Date());
		exorder.setOrderId(orderNo);
//		exorder.setOriginalorderId(orderNo);
		exorder.setMethod(transorder.getMethod());
		exorder.setProductName(order.getProductName());
		exorder.setSum(0-order.getSum());
		exorder.setAppOrderId(order.getAppOrderId());
		exorder.setRemark(order.getRemark());
		exorder.setStatus("OK#");
		exorder.setBalance(sourceacc.getBalance());
		exorder.setType(OrderConst.ORDER_OPERATION_PAY_OFFILNE);
		exorder.setPeerAccountId("");
		exorder.setPeerAccountUnit("TA#");
		exorder.setPeerAccountType("");
		exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
		exorder.setTerminalOrderId(order.getTerminalOrderId());
		exorder.setTerminalId(order.getTerminalId());
		exorder.setOperatorId(order.getOperatorId());
		exorder.setStoreId(order.getStoreId());
		exorder.setSellerId(order.getSellerId());
		exorder.setBatchNo(order.getBatchNo());
		exorder.setProductPrice(order.getProductPrice());
		exorder.setProductQuantity(order.getProductQuantity());
		exorder.setShiftInfo(order.getShiftInfo());
		CashAccountService caSrv = SpringBeanUtil.getInstance().getBean(CashAccountService.class);
		CashAccountOrderMapper caorderDao = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
		AccountValidateUtil.updateAccountSignature(sourceacc);
		caSrv.updateAccount(sourceacc);
		caorderDao.createOrder(exorder);
		DefaultReceipt receipt= new DefaultReceipt();
		receipt.setDealtime(exorder.getInsertTime());
		receipt.setOrderNo(orderNo);
		
		return receipt;
	}
	
	/**
	 * 线下支付
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws InsufficientAccountBalanceException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt payOffineByOilcard(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			CashAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException{
		//由于钱都由定时任务转到现金帐户，所以直接从现金帐户中进行支付即可
				//生成支出订单
				CashAccountOrder exorder = new CashAccountOrder();
				OfflinePayOrderConsumerVO order = transorder.getOrder();
				if(sourceacc.getBalance()<order.getSum()){
					if (log.isDebugEnabled()) {
						log.debug(String.format("现金帐户%s的余额不足以进行支付",sourceacc.getAccountId()));
					}
					throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,"现金帐户余额不足");
				}
				sourceacc.setBalance(sourceacc.getBalance()-order.getSum());
				AppVO app = transorder.getApp();
				String orderNo = NoGenerationUtil.genOrderNo();
				exorder.setAppId(app.getAppId());
				exorder.setAppname(app.getAppname());
				exorder.setAccountId(sourceacc.getAccountId());
				exorder.setInsertTime(new Date());
				exorder.setUpdateTime(new Date());
				exorder.setOrderId(orderNo);
//				exorder.setOriginalorderId(orderNo);
				exorder.setMethod(transorder.getMethod());
				exorder.setProductName(order.getProductName());
				exorder.setSum(0-order.getSum());
				exorder.setAppOrderId(order.getAppOrderId());
				exorder.setRemark(order.getRemark());
				exorder.setStatus("OK#");
				exorder.setBalance(sourceacc.getBalance());
				exorder.setType(OrderConst.ORDER_OPERATION_PAY_OFFILNE);
				exorder.setPeerAccountId("");
				exorder.setPeerAccountUnit("TA#");
				exorder.setPeerAccountType("");
				exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
				exorder.setTerminalOrderId(order.getTerminalOrderId());
				exorder.setTerminalId(order.getTerminalId());
				exorder.setOperatorId(order.getOperatorId());
				exorder.setStoreId(order.getStoreId());
				exorder.setSellerId(order.getSellerId());
				exorder.setBatchNo(order.getBatchNo());
				exorder.setProductPrice(order.getProductPrice());
				exorder.setProductQuantity(order.getProductQuantity());
				exorder.setShiftInfo(order.getShiftInfo());
				CashAccountService caSrv = SpringBeanUtil.getInstance().getBean(CashAccountService.class);
				CashAccountOrderMapper caorderDao = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
				AccountValidateUtil.updateAccountSignature(sourceacc);
				caSrv.updateAccount(sourceacc);
				caorderDao.createOrder(exorder);
				DefaultReceipt receipt= new DefaultReceipt();
				receipt.setDealtime(exorder.getInsertTime());
				receipt.setOrderNo(orderNo);
				receipt.addOutAccount(sourceacc);
				receipt.addExt("exorder", exorder);
				return receipt;
	}
	/**
	 * 线下支付
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws InsufficientAccountBalanceException 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,value="txManager")
	public Receipt payOffineByCas(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			CashAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException{
				CashAccountOrder exorder = new CashAccountOrder();
				OfflinePayOrderConsumerVO order = transorder.getOrder();

				if(sourceacc.getBalance()<order.getSum()){
					if (log.isDebugEnabled()) {
						log.debug(String.format("现金帐户%s的余额不足以进行支付",sourceacc.getAccountId()));
					}
					throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,"现金帐户余额不足");
				}
				sourceacc.setBalance(sourceacc.getBalance()-order.getSum());
				AppVO app = transorder.getApp();
				String orderNo = NoGenerationUtil.genOrderNo();
				exorder.setAppId(app.getAppId());
				exorder.setAppname(app.getAppname());
				exorder.setAccountId(sourceacc.getAccountId());
				exorder.setInsertTime(new Date());
				exorder.setUpdateTime(new Date());
				exorder.setOrderId(orderNo);
//				exorder.setOriginalorderId(orderNo);
				exorder.setMethod(transorder.getMethod());
				exorder.setProductName(order.getProductName());
				exorder.setSum(0-order.getSum());
				exorder.setAppOrderId(order.getAppOrderId());
				exorder.setRemark(order.getRemark());
				exorder.setStatus("OK#");
				exorder.setBalance(sourceacc.getBalance());
				exorder.setType(OrderConst.ORDER_OPERATION_PAY_OFFILNE);
				exorder.setPeerAccountId("");
				exorder.setPeerAccountUnit("TA#");
				exorder.setPeerAccountType("");
				exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
				exorder.setTerminalOrderId(order.getTerminalOrderId());
				exorder.setTerminalId(order.getTerminalId());
				exorder.setOperatorId(order.getOperatorId());
				exorder.setStoreId(order.getStoreId());
				exorder.setSellerId(order.getSellerId());
				exorder.setBatchNo(order.getBatchNo());
				exorder.setProductPrice(order.getProductPrice());
				exorder.setProductQuantity(order.getProductQuantity());
				exorder.setShiftInfo(order.getShiftInfo());
				CashAccountService caSrv = SpringBeanUtil.getInstance().getBean(CashAccountService.class);
				CashAccountOrderMapper caorderDao = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
				AccountValidateUtil.updateAccountSignature(sourceacc);
				caSrv.updateAccount(sourceacc);
				caorderDao.createOrder(exorder);
				DefaultReceipt receipt= new DefaultReceipt();
				receipt.setDealtime(exorder.getInsertTime());
				receipt.setOrderNo(orderNo);
				receipt.addOutAccount(sourceacc);
				receipt.addExt("exorder", exorder);
				return receipt;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,value="txManager")
	public Receipt payOffineByDiscard(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			DiscountCardAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException{
		
				//生成支出订单
				DiscountCardAccountOrder exorder = new DiscountCardAccountOrder();
				OfflinePayOrderConsumerVO order = transorder.getOrder();
				if(sourceacc.getBalance()<order.getSum()){
					if (log.isDebugEnabled()) {
						log.debug(String.format("折扣卡帐户%s的余额不足以进行支付",sourceacc.getAccountId()));
					}
					throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,"折扣帐户余额不足");
				}
				sourceacc.setBalance(sourceacc.getBalance()-order.getSum());
				AppVO app = transorder.getApp();
				String orderNo = NoGenerationUtil.genOrderNo();
				exorder.setAppId(app.getAppId());
				exorder.setAppname(app.getAppname());
				exorder.setAccountId(sourceacc.getAccountId());
				exorder.setInsertTime(new Date());
				exorder.setUpdateTime(new Date());
				exorder.setOrderId(orderNo);
//				exorder.setOriginalorderId(orderNo);
				exorder.setMethod(transorder.getMethod());
				exorder.setProductName(order.getProductName());
				exorder.setSum(0-order.getSum());
				exorder.setAppOrderId(order.getAppOrderId());
				exorder.setRemark(order.getRemark());
				exorder.setStatus("OK#");
				exorder.setBalance(sourceacc.getBalance());
				exorder.setType(OrderConst.ORDER_OPERATION_PAY_OFFILNE);
				exorder.setPeerAccountId("");
				exorder.setPeerAccountUnit("");
				exorder.setPeerAccountType("TA#");
				exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
				exorder.setTerminalOrderId(order.getTerminalOrderId());
				exorder.setTerminalId(order.getTerminalId());
				exorder.setOperatorId(order.getOperatorId());
				exorder.setStoreId(order.getStoreId());
				exorder.setSellerId(order.getSellerId());
				exorder.setBatchNo(order.getBatchNo());
				exorder.setProductPrice(order.getProductPrice());
				exorder.setProductQuantity(order.getProductQuantity());
				exorder.setShiftInfo(order.getShiftInfo());
				DiscountCardAccountService disSrv = SpringBeanUtil.getInstance().getBean(DiscountCardAccountService.class);
				DiscountCardAccountOrderMapper disorderDao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountOrderMapper.class);
				AccountValidateUtil.updateAccountSignature(sourceacc);
				disSrv.updateAccount(sourceacc);
				disorderDao.createOrder(exorder);
				DefaultReceipt receipt= new DefaultReceipt();
				receipt.setDealtime(exorder.getInsertTime());
				receipt.setOrderNo(orderNo);
				receipt.addOutAccount(sourceacc);
				receipt.addExt("exorder", exorder);
				return receipt;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,value="txManager")
	public Receipt payOffineByVolcard(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			VolumecardAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException{
		
				//生成支出订单
				VolumecardAccountOrder exorder = new VolumecardAccountOrder();
				OfflinePayOrderConsumerVO order = transorder.getOrder();
//				Double productQuantity=1000*Double.valueOf(order.getProductQuantity());
				long productQuantity=Long.parseLong(order.getProductQuantity());
				if(sourceacc.getBalance()<productQuantity){
					if (log.isDebugEnabled()) {
						log.debug(String.format("容量卡帐户%s的余额不足以进行支付",sourceacc.getAccountId()));
					}
					throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,"容量帐户余额不足");
				}
				sourceacc.setBalance(sourceacc.getBalance()-productQuantity);
				AppVO app = transorder.getApp();
				String orderNo = NoGenerationUtil.genOrderNo();
				exorder.setAppId(app.getAppId());
				exorder.setAppname(app.getAppname());
				exorder.setAccountId(sourceacc.getAccountId());
				exorder.setInsertTime(new Date());
				exorder.setUpdateTime(new Date());
				exorder.setOrderId(orderNo);
//				exorder.setOriginalorderId(orderNo);
				exorder.setMethod(transorder.getMethod());
				exorder.setProductName(order.getProductName());
				exorder.setSum(0-productQuantity);
				exorder.setAppOrderId(order.getAppOrderId());
				exorder.setRemark(order.getRemark());
				exorder.setStatus("OK#");
				exorder.setBalance(sourceacc.getBalance());
				exorder.setType(OrderConst.ORDER_OPERATION_PAY_OFFILNE);
				exorder.setPeerAccountId("");
				exorder.setPeerAccountUnit("");
				exorder.setPeerAccountType("TA#");
				exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
				exorder.setTerminalOrderId(order.getTerminalOrderId());
				exorder.setTerminalId(order.getTerminalId());
				exorder.setOperatorId(order.getOperatorId());
				exorder.setStoreId(order.getStoreId());
				exorder.setSellerId(order.getSellerId());
				exorder.setBatchNo(order.getBatchNo());
				exorder.setProductPrice(order.getProductPrice());
				exorder.setProductQuantity(order.getProductQuantity());
				exorder.setShiftInfo(order.getShiftInfo());
				VolumecardAccountService volSrv = SpringBeanUtil.getInstance().getBean(VolumecardAccountService.class);
				VolumecardAccountOrderMapper volorderDao = SpringBeanUtil.getInstance().getBean(VolumecardAccountOrderMapper.class);
				AccountValidateUtil.updateAccountSignature(sourceacc);
				volSrv.updateAccount(sourceacc);
				volorderDao.createOrder(exorder);
				DefaultReceipt receipt= new DefaultReceipt();
				receipt.setDealtime(exorder.getInsertTime());
				receipt.setOrderNo(orderNo);
				receipt.addOutAccount(sourceacc);
				receipt.addExt("exorder", exorder);
				return receipt;
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,value="txManager")
	public Receipt payOffineByInv(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			InvestmentAccount sourceacc, Account descacc) throws InsufficientAccountBalanceException{
		
				//生成支出订单
				InvestmentAccountRemainingOrder exorder = new InvestmentAccountRemainingOrder();
				OfflinePayOrderConsumerVO order = transorder.getOrder();
				if(!sourceacc.getStatus().equals("OK#")){
					if (log.isDebugEnabled()) {
						log.debug(String.format("投资帐户%s现在无法使用",sourceacc.getAccountId())+"账户状态为："+sourceacc.getStatus());
					}
					throw new InsufficientAccountBalanceException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"投资账户无法使用");
				
				}
				if(sourceacc.getRemainingsum()<order.getSum()){
					if (log.isDebugEnabled()) {
						log.debug(String.format("投资卡帐户%s的可用余额不足以进行支付",sourceacc.getAccountId()));
					}
					throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,"投资帐户可用余额不足");
				}
				sourceacc.setRemainingsum(sourceacc.getBalance()-order.getSum());
				AppVO app = transorder.getApp();
				String orderNo = NoGenerationUtil.genOrderNo();
				exorder.setAppId(app.getAppId());
				exorder.setAppname(app.getAppname());
				exorder.setAccountId(sourceacc.getAccountId());
				exorder.setInsertTime(new Date());
				exorder.setUpdateTime(new Date());
				exorder.setOrderId(orderNo);
//				exorder.setOriginalorderId(orderNo);
				exorder.setMethod(transorder.getMethod());
				exorder.setProductName(order.getProductName());
				exorder.setSum(0-order.getSum());
				exorder.setAppOrderId(order.getAppOrderId());
				exorder.setRemark(order.getRemark());
				exorder.setStatus("OK#");
				exorder.setBalance(sourceacc.getBalance());
				exorder.setType(OrderConst.ORDER_OPERATION_PAY_OFFILNE);
				exorder.setPeerAccountId("");
				exorder.setPeerAccountUnit("TA#");
				exorder.setPeerAccountType("");
				exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
				/*exorder.setTerminalOrderId(order.getTerminalOrderId());
				exorder.setTerminalId(order.getTerminalId());
				exorder.setOperatorId(order.getOperatorId());
				exorder.setStoreId(order.getStoreId());
				exorder.setSellerId(order.getSellerId());
				exorder.setBatchNo(order.getBatchNo());
				exorder.setProductPrice(order.getProductPrice());
				exorder.setProductQuantity(order.getProductQuantity());*/
				InvestmentAccountService invSrv = SpringBeanUtil.getInstance().getBean(InvestmentAccountService.class);
				InvestmentAccountRemainingOrderMapper invorderDao = SpringBeanUtil.getInstance().getBean(InvestmentAccountRemainingOrderMapper.class);
				AccountValidateUtil.updateAccountSignature(sourceacc);
				invSrv.updateAccount(sourceacc);
				invorderDao.createOrder(exorder);
				DefaultReceipt receipt= new DefaultReceipt();
				receipt.setDealtime(exorder.getInsertTime());
				receipt.setOrderNo(orderNo);
				receipt.addOutAccount(sourceacc);
				receipt.addExt("exorder", exorder);
				return receipt;
	}

	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public List<RedPaperAccountOrder> spendByRedPaper(TransOrderVO2<SpendRedPaperOrderVO> transorder,
			List<RedPaperAccount> accounts) throws Exception {
		RedPaperProductMapper productDao = SpringBeanUtil.getInstance().getBean(RedPaperProductMapper.class);
		List<RedPaperAccountOrder> orders=new ArrayList<RedPaperAccountOrder>();
		//生成支出订单
		SpendRedPaperOrderVO order = transorder.getOrder();
		AppVO app = transorder.getApp();
		String orderNo;
		Date date=new Date();
		for(RedPaperAccount sourceacc:accounts){
			if(!sourceacc.getStatus().equals("OK#")){
				if (log.isDebugEnabled()) {
					log.debug(String.format("红包%s已经被使用",sourceacc.getAccountId())+"账户状态为："+sourceacc.getStatus());
					
				}
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"红包无法使用");
			
			}
			RedPaperProduct commproduct = productDao.selectByPrimaryKey(sourceacc.getProductId());
			if(commproduct==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("产品不存在",sourceacc.getAccountId()));
					
				}
				throw new MaAccountException(MaAccountException.ERR_PRODUCT_EXCEPTION,"产品没有定义");
			
			}
			
			if(sourceacc.getStartTime()!=null&&date.before(sourceacc.getStartTime())){
				if (log.isDebugEnabled()) {
					log.debug(String.format("红包还未到有效期:%s",sourceacc.getStartTime()));
					
				}
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"红包还未到有效期");
			
			}
			if(sourceacc.getEndTime()!=null&&date.after(sourceacc.getEndTime())){
				if (log.isDebugEnabled()) {
					log.debug(String.format("红包已过期:%s",sourceacc.getEndTime()));
					
				}
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"红包已过期");
			
			}
			orderNo = NoGenerationUtil.genOrderNo();
			RedPaperAccountOrder exorder = new RedPaperAccountOrder();
			exorder.setAppId(app.getAppId());
			exorder.setAppname(app.getAppname());
			exorder.setAccountId(sourceacc.getAccountId());
			exorder.setInsertTime(new Date());
			exorder.setUpdateTime(new Date());
			exorder.setOrderId(orderNo);
			exorder.setMethod(transorder.getMethod());
			exorder.setProductName(commproduct.getProductName());
			exorder.setSum(sourceacc.getAmount());
			exorder.setAppOrderId(order.getAppOrderId());
			exorder.setRemark(order.getRemark());
			exorder.setStatus("OK#");
			exorder.setBalance(0);
			exorder.setType(OrderConst.ORDER_OPERATION_SPEND);
			exorder.setPeerAccountId("");
			exorder.setPeerAccountUnit("TA#");
			exorder.setPeerAccountType("");
			exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
			exorder.setAssociatedOrderId(order.getAssociatedOrderId());
			RedPaperAccountService rpaSrv = SpringBeanUtil.getInstance().getBean(RedPaperAccountService.class);
			RedPaperAccountOrderMapper rpaorderDao = SpringBeanUtil.getInstance().getBean(RedPaperAccountOrderMapper.class);
			sourceacc.setStatus("USD");
			sourceacc.setUpdateTime(new Date());
			rpaSrv.updateAccount(sourceacc);
			orders.add(exorder);
			rpaorderDao.insert(exorder);
		}
		return orders;
	}
	
	/**
	 * 线下开卡 
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @param product
	 * @return
	 * @throws MaAccountException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOfflineOpenOilcard(
			TransOrderVO2<OfflineOpencardOrderVO> transorder,
			Account sourceacc, OilcardAccount descacc,
			OilcardAccountProduct product) throws MaAccountException {
		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
				OilcardAccountMapper ocaccDao = SpringBeanUtil.getInstance().getBean(OilcardAccountMapper.class);
				AccountValidateUtil.updateAccountSignature(descacc);
				int createAccount = ocaccDao.createAccount(descacc);
				String orderNo=null;
				if(1==createAccount)
				{
					//添加订单
					OilcardAccountOrder oaOrder = new OilcardAccountOrder();
					OfflineOpencardOrderVO order = transorder.getOrder();
					AppVO app = transorder.getApp();
					orderNo = NoGenerationUtil.genOrderNo();
					oaOrder.setAppId(app.getAppId());
					oaOrder.setAppname(app.getAppname());
					oaOrder.setAccountId(descacc.getAccountId());
					oaOrder.setInsertTime(new Date());
					oaOrder.setUpdateTime(new Date());
					oaOrder.setOrderId(orderNo);
					oaOrder.setOriginalorderId(orderNo);
//				oaOrder.setProductName(product.getProductName());
					oaOrder.setSum(commproduct.getProductAmount());
					oaOrder.setAppOrderId(order.getAppOrderId());
					oaOrder.setRemark(order.getRemark());
					oaOrder.setMethod(transorder.getMethod());
					oaOrder.setStatus("OK#");
					oaOrder.setBalance(commproduct.getProductAmount());
					oaOrder.setType(transorder.getOperationType());
					oaOrder.setPeerAccountId(null);
					oaOrder.setPeerAccountUnit(null);
					oaOrder.setPeerAccountType("TX#");
					oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
					oaOrder.setProductName(commproduct.getProductName());
					oaOrder.setProductQuantity("1");
					oaOrder.setProductPrice(commproduct.getProductPrice().longValue());
					oaOrder.setBatchNo(order.getBatchNo());
					oaOrder.setTerminalId(order.getTerminalId());
					oaOrder.setTerminalOrderId(order.getTerminalOrderId());
					oaOrder.setStoreId(order.getStoreId());
					oaOrder.setOperatorId(order.getOperatorId());
					oaOrder.setSellerId(order.getSellerId());
					
					
					//更新订单
					OilcardAccountOrderMapper ocaccordDao = SpringBeanUtil.getInstance().getBean(OilcardAccountOrderMapper.class);
					int createOrdersuccess = ocaccordDao.createOrder(oaOrder);
					if(createOrdersuccess==1){
						
					}
					else{
						
						throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
					}
				}
				else{
					//更新订单不成功
					if (log.isDebugEnabled()) {
						log.debug(String.format("订单收入不成功"));
					}
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
				}
				
				DefaultReceipt receipt = new DefaultReceipt();
				receipt.setOrderNo(orderNo);
				return receipt;

	}
	
	/**
	 * 线下开卡  容量卡
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @param product
	 * @return
	 * @throws MaAccountException 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,value="txManager")
	public Receipt processOfflineOpenVolcard(
			TransOrderVO2<OfflineOpencardOrderVO> transorder,
			Account sourceacc, VolumecardAccount descacc,
			VolumecardAccountProduct product) throws MaAccountException {
		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		        VolumecardAccountMapper ocaccDao = SpringBeanUtil.getInstance().getBean(VolumecardAccountMapper.class);
		        AccountValidateUtil.updateAccountSignature(descacc);
		        int createAccount = ocaccDao.createAccount(descacc);
				String orderNo=null;
				if(1==createAccount)
				{
					//添加订单
					VolumecardAccountOrder oaOrder = new VolumecardAccountOrder();
					OfflineOpencardOrderVO order = transorder.getOrder();
					AppVO app = transorder.getApp();
					orderNo = NoGenerationUtil.genOrderNo();
					oaOrder.setAppId(app.getAppId());
					oaOrder.setAppname(app.getAppname());
					oaOrder.setAccountId(descacc.getAccountId());
					oaOrder.setInsertTime(new Date());
					oaOrder.setUpdateTime(new Date());
					oaOrder.setOrderId(orderNo);
					oaOrder.setOriginalorderId(orderNo);
//				oaOrder.setProductName(product.getProductName());
					oaOrder.setSum(0);
					oaOrder.setAppOrderId(order.getAppOrderId());
					oaOrder.setRemark(order.getRemark());
					oaOrder.setMethod(transorder.getMethod());
					oaOrder.setStatus("OK#");
					oaOrder.setBalance(commproduct.getProductAmount());
					oaOrder.setType(transorder.getOperationType());
					oaOrder.setPeerAccountId(null);
					oaOrder.setPeerAccountUnit(null);
					oaOrder.setPeerAccountType("TX#");
					oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
					oaOrder.setProductName(commproduct.getProductName());
					oaOrder.setProductQuantity("1");
					oaOrder.setProductPrice(commproduct.getProductPrice().longValue());
					oaOrder.setBatchNo(order.getBatchNo());
					oaOrder.setTerminalId(order.getTerminalId());
					oaOrder.setTerminalOrderId(order.getTerminalOrderId());
					oaOrder.setStoreId(order.getStoreId());
					oaOrder.setOperatorId(order.getOperatorId());
					oaOrder.setSellerId(order.getSellerId());
					
					
					//更新订单
					VolumecardAccountOrderMapper ocaccordDao = SpringBeanUtil.getInstance().getBean(VolumecardAccountOrderMapper.class);
					int createOrdersuccess = ocaccordDao.createOrder(oaOrder);
					if(createOrdersuccess==1){
						
					}
					else{
						
						throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
					}
				}
				else{
					//更新订单不成功
					if (log.isDebugEnabled()) {
						log.debug(String.format("订单收入不成功"));
					}
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
				}
				
				DefaultReceipt receipt = new DefaultReceipt();
				receipt.setOrderNo(orderNo);
				return receipt;
	}
	/**
	 * 线下开卡 折扣卡
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @param product
	 * @return
	 * @throws MaAccountException 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,value="txManager")
	public Receipt processOfflineOpenDiscard(
			TransOrderVO2<OfflineOpencardOrderVO> transorder,
			Account sourceacc, DiscountCardAccount descacc,
			DiscountCardProduct product) throws MaAccountException {
		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		        DiscountCardAccountMapper ocaccDao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountMapper.class);
		        AccountValidateUtil.updateAccountSignature(descacc);
		        int createAccount = ocaccDao.createAccount(descacc);
				String orderNo=null;
				if(1==createAccount)
				{
					//添加订单
					DiscountCardAccountOrder oaOrder = new DiscountCardAccountOrder();
					OfflineOpencardOrderVO order = transorder.getOrder();
					AppVO app = transorder.getApp();
					orderNo = NoGenerationUtil.genOrderNo();
					oaOrder.setAppId(app.getAppId());
					oaOrder.setAppname(app.getAppname());
					oaOrder.setAccountId(descacc.getAccountId());
					oaOrder.setInsertTime(new Date());
					oaOrder.setUpdateTime(new Date());
					oaOrder.setOrderId(orderNo);
					oaOrder.setOriginalorderId(orderNo);
//				oaOrder.setProductName(product.getProductName());
					oaOrder.setSum(0);
					oaOrder.setAppOrderId(order.getAppOrderId());
					oaOrder.setRemark(order.getRemark());
					oaOrder.setMethod(transorder.getMethod());
					oaOrder.setStatus("OK#");
					oaOrder.setBalance(commproduct.getProductAmount());
					oaOrder.setType(transorder.getOperationType());
					oaOrder.setPeerAccountId(null);
					oaOrder.setPeerAccountUnit(null);
					oaOrder.setPeerAccountType("TX#");
					oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
					oaOrder.setProductName(commproduct.getProductName());
					oaOrder.setProductQuantity("1");
					oaOrder.setProductPrice(commproduct.getProductPrice().longValue());
					oaOrder.setBatchNo(order.getBatchNo());
					oaOrder.setTerminalId(order.getTerminalId());
					oaOrder.setTerminalOrderId(order.getTerminalOrderId());
					oaOrder.setStoreId(order.getStoreId());
					oaOrder.setOperatorId(order.getOperatorId());
					oaOrder.setSellerId(order.getSellerId());
					
					
					//更新订单
					DiscountCardAccountOrderMapper ocaccordDao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountOrderMapper.class);
					int createOrdersuccess = ocaccordDao.createOrder(oaOrder);
					if(createOrdersuccess==1){
						
					}
					else{
						
						throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
					}
				}
				else{
					//更新订单不成功
					if (log.isDebugEnabled()) {
						log.debug(String.format("订单收入不成功"));
					}
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
				}
				
				DefaultReceipt receipt = new DefaultReceipt();
				receipt.setOrderNo(orderNo);
				return receipt;

	}
	
	/**
	 * 调整旧帐户为新帐户
	 * @return 帐户更新记录
	 */
	public 
	int[] changeOldAccountIdToNewAccount() throws  Exception
	{
		//旧帐户为非卡号帐户
		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		int total=0;
		int totalcash=0;
		//查询现金帐户
		int updatecashcount = 0;
		List<Map<String, Object>> caseacclist = jdbc.queryForList("select accountid from t_cash_account where accountId not in (select accountid from t_factory_account_id where status='USD' and accounttype='CA#')");
		totalcash+=caseacclist.size();
		for (Iterator iterator = caseacclist.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			String accountid = ObjectUtils.toString(map.get("accountid"));
			try{
				changeOldAccountIdToNewAccount4cash(accountid);
				updatecashcount++;
			}
			catch(Exception e){
				log.error("更新帐户"+accountid+"到新帐户失败",e);
			}
			break;
		}
		//查询投资帐户
		int updateinvestcount=0;
		int totalinvest =0;
		List<Map<String, Object>> investacclist = jdbc.queryForList("select accountid from t_investment_account where accountId not in (select accountid from t_factory_account_id where status='USD' and accounttype='IA#')");
		totalinvest+=investacclist.size();
		for (Iterator iterator = investacclist.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			String accountid = ObjectUtils.toString(map.get("accountid"));
			try{
				changeOldAccountIdToNewAccount4investment(accountid);
				updateinvestcount++;
			}
			catch(Exception e){
				log.error("更新帐户"+accountid+"到新帐户失败",e);
			}
			break;
		}
		
		return new int[]{totalcash,totalinvest,updatecashcount,updateinvestcount};
		
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public void changeOldAccountIdToNewAccount4cash(String oldaccountid) throws Exception{
		if (log.isDebugEnabled()) {
			log.debug(String.format("更新现金帐户%s",oldaccountid));
		}
		String updatecashorder = "update t_cash_account_order set accountid=? where accountid=?";
		String updatecashacc = "update t_cash_account set accountid=? where accountid=?";
//		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		String newaccountid = jdbc.queryForObject("select get_account_id('CA#')", String.class);
		if(newaccountid==null){
			throw new Exception("无新帐号");
		}
		else{
			if(log.isDebugEnabled())
			{
				log.debug("新现金帐户帐号为"+newaccountid);
			}
		}
		try {
			jdbc.update(updatecashorder,new Object[]{newaccountid,oldaccountid});
			jdbc.update(updatecashacc,new Object[]{newaccountid,oldaccountid});
		} catch (Exception e) {
			//返还帐户
//			jdbc.update("update t_factory_account_id set status='NUS' where accountid=?",newaccountid);
			throw e;
		}
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public void changeOldAccountIdToNewAccount4investment(String oldaccountid) throws Exception{
		if (log.isDebugEnabled()) {
			log.debug(String.format("更新投资帐户%s",oldaccountid));
		}
		String updatecashorder = "update t_investment_account_remaining_order set accountid=? where accountid=?";
		String updatecashobjectorder = "update t_investment_account_object_order set accountid=? where accountid=?";
		String updatecashacc = "update t_investment_account set accountid=? where accountid=?";
//		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		String newaccountid = jdbc.queryForObject("select get_account_id('IA#')", String.class);
		if(newaccountid==null){
			throw new Exception("无新帐号");
		}
		else{
			if(log.isDebugEnabled())
			{
				log.debug("新投资帐户帐号为"+newaccountid);
			}
		}
		try {
			jdbc.update(updatecashorder,new Object[]{newaccountid,oldaccountid});
			jdbc.update(updatecashacc,new Object[]{newaccountid,oldaccountid});
			jdbc.update(updatecashobjectorder,new Object[]{newaccountid,oldaccountid});
		} catch (Exception e) {
			//返还帐户
//			jdbc.update("update t_factory_account_id set status='NUS' where accountid=?",newaccountid);
			throw e;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public void undoRedPaper(UndoRedPaperTransOrderVO transorder,List<String> orderIds) throws Exception {
		
		RedPaperAccountOrderMapper rpaOrderDao = SpringBeanUtil.getInstance().getBean(RedPaperAccountOrderMapper.class);
		
		RedPaperAccount rpa=new RedPaperAccount();
		RedPaperAccountOrder rpaOrder=new RedPaperAccountOrder();
		AppVO app = transorder.getApp();
		String orderNo;
		RedPaperAccountOrder exorder = new RedPaperAccountOrder();
		for(String orderId:orderIds){
			
			rpaOrder=rpaOrderDao.selectByPrimaryKey(orderId);
			if(rpaOrder==null){
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,orderId+"订单不存在");
			}
			if(rpaOrder.getFlowDirection().equals("OUT")){
				orderNo = NoGenerationUtil.genOrderNo();
				rpa=rpaDao.getAccountByAccountId(rpaOrder.getAccountId());
				if(rpa.getStatus().equals("OK#")){
					if (log.isDebugEnabled()) {
						log.debug(String.format("红包%s未使用，无法撤销红包消费",rpaOrder.getAccountId()));
					}throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,rpaOrder.getAccountId()+"未使用");
				}
				if(rpa.getEndTime().before(new Date())){
					if (log.isDebugEnabled()) {
						log.debug(String.format("红包%s已经过期，无法撤销",rpaOrder.getAccountId()));
					}throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,rpaOrder.getAccountId()+"已过期");
				}
				
				if (log.isDebugEnabled()) {
					log.debug(String.format("更新红包%s",rpaOrder.getAccountId()));
				}
				
				exorder.setAppId(app.getAppId());
				exorder.setAppname(app.getAppname());
				exorder.setAccountId(rpaOrder.getAccountId());
				exorder.setInsertTime(new Date());
				exorder.setUpdateTime(new Date());
				exorder.setOrderId(orderNo);
				exorder.setMethod(transorder.getMethod());
				
				exorder.setProductName(rpaOrder.getProductName());
				exorder.setSum(0);
				exorder.setAppOrderId(rpaOrder.getAppOrderId());
				exorder.setRemark("");
				exorder.setStatus("OK#");
				exorder.setBalance(rpa.getAmount());
				exorder.setType(OrderConst.ORDER_OPERATION_CANCEL);
				exorder.setPeerAccountId("");
				exorder.setPeerAccountUnit("TA#");
				exorder.setPeerAccountType("");
				exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
				
				RedPaperAccountService rpaSrv = SpringBeanUtil.getInstance().getBean(RedPaperAccountService.class);
				RedPaperAccountOrderMapper rpaorderDao = SpringBeanUtil.getInstance().getBean(RedPaperAccountOrderMapper.class);
				rpa.setStatus("OK#");
				rpa.setUpdateTime(new Date());
				boolean createAccountsuccess =rpaSrv.updateAccount(rpa);
				if(createAccountsuccess){
					
				}
				else{
					
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新红包账户失败");
				}
				int createOrdersuccess =rpaorderDao.insert(exorder);
				if(createOrdersuccess==1){
					
				}
				else{
					
					throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
				}
			}else{
				if (log.isDebugEnabled()) {
					log.debug(String.format("红包%s未使用，无法撤销",rpaOrder.getAccountId()));
				}throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,rpaOrder.getAccountId()+"未使用");
			}
		
		}
	}

	@Override
	public Receipt processJifen(TransOrderVO2<JifenOrderVO> transorder,User user,
			JifenProduct product)
			throws MaAccountException {
		JifenOrderVO order = transorder.getOrder();
		JifenProductMapper productDao = SpringBeanUtil.getInstance().getBean(JifenProductMapper.class);
		JifenProduct commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		JifenAccountMapper ocaccDao = SpringBeanUtil.getInstance().getBean(JifenAccountMapper.class);
		JifenAccount jfa=ocaccDao.selectByProductId(user.getUserId(),order.getJifenProductId());
		int success=0;
		if(jfa!=null){
			jfa.setBalance(jfa.getBalance()+order.getSum());
			jfa.setUpdateTime(new Date());
			success =ocaccDao.updateByPrimaryKey(jfa);
			
		}else{
			jfa = new JifenAccount();
			//开卡时，还要经过审核操作，在此之前不能使用，也不会进行分期操作。
//			account.setAccountId(NoGenerationUtil.genNO("oc_"));//卡号
			String accountid = AccountGenerationUtil.genJifenAccountNo();
			jfa.setAccountId(accountid);
			jfa.setRemark(order.getRemark());
			jfa.setUserId(user.getUserId());
			jfa.setProductId(order.getJifenProductId());
			jfa.setBalance(order.getSum());
			jfa.setInsertTime(new Date());
			jfa.setUpdateTime(new Date());
			jfa.setStatus(JifenAccount.STATUS_OK);
			success = ocaccDao.insert(jfa);
		}
		String orderNo=null;
		if(1==success)
		{
			//添加订单
			JifenAccountOrder rpOrder = new JifenAccountOrder();
			AppVO app = transorder.getApp();
			orderNo = NoGenerationUtil.genOrderNo();
			rpOrder.setAppId(app.getAppId());
			rpOrder.setAppname(app.getAppname());
			rpOrder.setAccountId(jfa.getAccountId());
			rpOrder.setInsertTime(new Date());
			rpOrder.setUpdateTime(new Date());
			rpOrder.setOrderId(orderNo);
			rpOrder.setOriginalorderId(orderNo);
//		oaOrder.setProductName(product.getProductName());
			rpOrder.setSum(order.getSum());
			rpOrder.setAppOrderId(order.getAppOrderId());
			rpOrder.setRemark(order.getRemark());
			rpOrder.setMethod(transorder.getMethod());
			rpOrder.setStatus("OK#");
			
			rpOrder.setType(transorder.getOperationType());
			
			rpOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			rpOrder.setProductName(commproduct.getProductName());
			rpOrder.setProductQuantity("1");
			rpOrder.setProductPrice(order.getSum());
			//更新订单
			JifenAccountOrderMapper ocaccordDao = SpringBeanUtil.getInstance().getBean(JifenAccountOrderMapper.class);
			int createOrdersuccess = ocaccordDao.insert(rpOrder);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(orderNo);
		receipt.addOutAccount(jfa);
		return receipt;
	}

	@Override
	public JifenAccountOrder spendByJifen(
			TransOrderVO2<SpendJifenOrderVO> transorder, JifenAccount sourceacc)
			throws Exception {
		JifenProductMapper productDao = SpringBeanUtil.getInstance().getBean(JifenProductMapper.class);
		
		//生成支出订单
		JifenAccountOrder exorder = new JifenAccountOrder();
		SpendJifenOrderVO order = transorder.getOrder();
		AppVO app = transorder.getApp();
		String orderNo = NoGenerationUtil.genOrderNo();
		
			if(!sourceacc.getStatus().equals("OK#")){
				if (log.isDebugEnabled()) {
					log.debug(String.format("积分%s无效",sourceacc.getAccountId())+"账户状态为："+sourceacc.getStatus());
					
				}
				throw new InsufficientAccountBalanceException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"积分无法使用");
			
			}
			//兑换积分小于积分余额
			if(sourceacc.getBalance()<order.getSum()){
				if (log.isDebugEnabled()) {
					log.debug(String.format("积分%s余额不足",sourceacc.getAccountId()));
				}
				throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,"积分余额不足");
			}
			JifenProduct commproduct = productDao.selectByPrimaryKey(sourceacc.getProductId());
			exorder.setAppId(app.getAppId());
			exorder.setAppname(app.getAppname());
			exorder.setAccountId(sourceacc.getAccountId());
			exorder.setInsertTime(new Date());
			exorder.setUpdateTime(new Date());
			exorder.setOrderId(orderNo);
			exorder.setMethod(transorder.getMethod());
			exorder.setProductName(commproduct.getProductName());
			exorder.setSum(order.getSum());
			exorder.setAppOrderId(order.getAppOrderId());
			exorder.setRemark(order.getRemark());
			exorder.setStatus("OK#");
			exorder.setBalance(sourceacc.getBalance()-order.getSum());
			exorder.setType(OrderConst.ORDER_OPERATION_SPEND);
			exorder.setPeerAccountId("");
			exorder.setPeerAccountUnit("TA#");
			exorder.setPeerAccountType("");
			exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
			exorder.setAssociatedOrderId(order.getAssociatedOrderId());
			JifenAccountService rpaSrv = SpringBeanUtil.getInstance().getBean(JifenAccountService.class);
			JifenAccountOrderMapper rpaorderDao = SpringBeanUtil.getInstance().getBean(JifenAccountOrderMapper.class);
			sourceacc.setBalance(sourceacc.getBalance()-order.getSum());
			sourceacc.setUpdateTime(new Date());
			rpaSrv.updateAccount(sourceacc);
			rpaorderDao.insert(exorder);
		
		return exorder;
	}

	@Override
	public Receipt rechargeCashAccount(TransOrderVO transorder,
			CashAccount sourceacc) throws MaAccountException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("订单处理开始"));
		}
		if (log.isTraceEnabled()) {
			log.trace(String.format("创建收入订单"));
		}
		DefaultReceipt receipt = new DefaultReceipt();
		//生成支出订单
		CashAccountOrder exorder = new CashAccountOrder();
		
		AppVO app = transorder.getApp();
		String orderNo = NoGenerationUtil.genOrderNo();
		if(!sourceacc.getStatus().equals("OK#")){
			if (log.isDebugEnabled()) {
				log.debug(String.format("现金账户%s无法使用",sourceacc.getAccountId())+"账户状态为："+sourceacc.getStatus());
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"现金账户无法使用");
		}
		exorder.setAppId(app.getAppId());
		exorder.setAppname(app.getAppname());
		exorder.setAccountId(sourceacc.getAccountId());
		exorder.setInsertTime(new Date());
		exorder.setUpdateTime(new Date());
		exorder.setOrderId(orderNo);
		exorder.setMethod(transorder.getMethod());
		exorder.setSum(transorder.getOrder().getSum());
		exorder.setAppOrderId(transorder.getOrder().getAppOrderId());
		exorder.setRemark(transorder.getOrder().getRemark());
		exorder.setStatus("OK#");
		exorder.setBalance(sourceacc.getBalance()+transorder.getOrder().getSum());
		//标识为充值
		exorder.setType(OrderConst.ORDER_OPERATION_CZ);
		//保存对方账户的信息，比如微信账号
		exorder.setPeerAccountId(transorder.getOrder().getPeerAccountId());
		
		exorder.setPeerAccountUnit(transorder.getOrder().getPeerAccountUnit());
		exorder.setPeerAccountType(transorder.getOrder().getPeerAccountType());
		exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
		exorder.setExternalOrderId(transorder.getOrder().getExternalOrderId());
		//交易时间
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");//小写的mm表示的是分钟  
		  
		Date externalOrderTime=null;
		try {
			if(StringUtils.isNotBlank(transorder.getOrder().getExternalOrderTime())){
				externalOrderTime = sdf.parse(transorder.getOrder().getExternalOrderTime());
			}
		} catch (ParseException e) {
			if (log.isTraceEnabled()) {
				log.trace(String.format("交易日期格式错误：%s",transorder.getOrder().getExternalOrderTime()));
			}throw new MaAccountException(MaAccountException.ERRCODE_REQUEST,"日期格式错误");
		}
		exorder.setExternalOrderTime(externalOrderTime);
		CashAccountService rpaSrv = SpringBeanUtil.getInstance().getBean(CashAccountService.class);
		CashAccountOrderMapper rpaorderDao = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
		sourceacc.setBalance(sourceacc.getBalance()+transorder.getOrder().getSum());
		AccountValidateUtil.updateAccountSignature(sourceacc);
		rpaSrv.updateAccount(sourceacc);
		rpaorderDao.createOrder(exorder);		
		receipt.setOrderNo(orderNo);
		return receipt;
	}

	@Override
	public List<TokenCheckResultVO> checkTokenOrder(Date startDate,Date endDate) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug(String.format("交易对账处理开始"));
		}
		try{
			List<TokenCheckResultVO> list=new ArrayList<TokenCheckResultVO>();
			Map dateMap =new HashMap();
			Calendar calendar = Calendar.getInstance();
			
			calendar.setTime(startDate);
			dateMap.put("startDate",startDate);
			
			for(;calendar.getTime().before(endDate);){
				TokenCheckResultVO tokenCheckResultVO=new TokenCheckResultVO();
				//记录查询的日期
				tokenCheckResultVO.setCheckDate(DateUtil.format(calendar.getTime(), "yyyyMMdd"));
				calendar.add(calendar.DATE,1);
				dateMap.put("endDate", calendar.getTime());
				//查询这段时间的充值笔数
				tokenCheckResultVO.setChargeCount(caoDao.queryCheckTokenTotal(dateMap));
				//查询这段时间的充值总额
				tokenCheckResultVO.setChargeSum(caoDao.queryCheckTokenSum(dateMap));
				//查询这段时间的撤单笔数
				tokenCheckResultVO.setCancelCount(caoDao.queryCancelCount(dateMap));
				//查询这段时间的撤单总额
				tokenCheckResultVO.setCancelSum(caoDao.queryCancelSum(dateMap));
				//新一轮循环，让这一次的开始时间为上一次查询的结束时间
				dateMap.put("startDate",calendar.getTime());
				list.add(tokenCheckResultVO);
			}
			if (log.isDebugEnabled()) {
				log.debug(String.format("交易对账处理完成"));
			}
			return list;
				
		}catch(Exception e){
			if (log.isDebugEnabled()) {
				log.debug(String.format("交易对账处理失败"));
			}
			throw e;
		}finally{}
		
	}

	@Override
	public List<CashAccountOrderVO> queryCashAccountOrder(
			Pagingnation pagingnation, Map filter)
			throws MaAccountException {
		//AccountOrderDao orderDao = OrderFactory.getOrderDao(ObjectUtils.toString(filter.get("accountCode")),ObjectUtils.toString( filter.get("accountadditional")));
		int total = caoDao.queryCashAccountTotal(pagingnation, filter);
		pagingnation.setTotalCount(total);
		List<CashAccountOrderVO> orders = caoDao.queryCashAccountOrder(pagingnation, filter);
		return orders;
	}

	
	/**
	 * 查询消费订单
	 * @param orderqueryvo
	 * @return
	 */
	public List<SpendOrderOutputVO> querySpendOrder(
			OrderQueryTransVO<SpendOrderQueryPagingVO> orderqueryvo, Pagingnation pagingnation){
		if(pagingnation!=null){
			int total = orderdao.querySpendOrderTotalByPage(orderqueryvo.getQuery());
			pagingnation.setTotalCount(total);
		}
		List<SpendOrderOutputVO> result = orderdao.querySpendOrderByPage(pagingnation, orderqueryvo.getQuery());
		return result;
		
	}

	@Override
	public Long rechargeTotal() {
		if (log.isInfoEnabled()) {
			log.info("计算当天现金充值总额！");
		}
		Map dateMap =new HashMap();
		dateMap.put("startDate",DateUtil.toDayStart(new Date()));
		dateMap.put("endDate",new Date());
		
		return caoDao.queryRechargeTotal(dateMap,"app_map_wx")==null?0:caoDao.queryCheckTokenSum(dateMap);
	}

	@Override
	public Receipt processBathOpenVolumecard(
			TransOrderVO2<BatchOpenOnlineDetailVO> transorder,
			BatchOpenOnlineListVO order, Account sourceacc,
			VolumecardAccount descacc, VolumecardAccountProduct product)
			throws MaAccountException {
		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		VolumecardAccountMapper ocaccDao = SpringBeanUtil.getInstance().getBean(VolumecardAccountMapper.class);
		AccountValidateUtil.updateAccountSignature(descacc);
		int createAccount = ocaccDao.createAccount(descacc);
		String orderNo=null;
		if(1==createAccount)
		{
			//添加订单
			VolumecardAccountOrder oaOrder = new VolumecardAccountOrder();
			//VolumecardOrderVO order = transorder.getOrder();
			AppVO app = transorder.getApp();
			orderNo = NoGenerationUtil.genOrderNo();
			oaOrder.setAppId(app.getAppId());
			oaOrder.setAppname(transorder.getApp().getAppname());
			oaOrder.setAccountId(descacc.getAccountId());
			oaOrder.setInsertTime(new Date());
			oaOrder.setUpdateTime(new Date());
			oaOrder.setOrderId(orderNo);
//			oaOrder.setOriginalorderId(orderNo);
//		oaOrder.setProductName(product.getProductName());
			oaOrder.setSum(commproduct.getProductAmount());
			oaOrder.setAppOrderId(transorder.getOrder().getAppOrderId());
			oaOrder.setRemark(order.getRemark());
			oaOrder.setMethod(transorder.getMethod());
			oaOrder.setStatus("OK#");
			oaOrder.setBalance(commproduct.getProductAmount());
			oaOrder.setType(transorder.getOperationType());
			oaOrder.setPeerAccountId(null);
			oaOrder.setPeerAccountUnit(null);
			oaOrder.setPeerAccountType("TX#");
			oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			oaOrder.setChannelOrderId(order.getChannelOrderId());
			oaOrder.setProductName(commproduct.getProductName());
			oaOrder.setProductQuantity("1");
			oaOrder.setProductPrice(commproduct.getProductAmount());
			//更新订单
			VolumecardAccountOrderMapper ocaccordDao = SpringBeanUtil.getInstance().getBean(VolumecardAccountOrderMapper.class);
			int createOrdersuccess = ocaccordDao.createOrder(oaOrder);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(orderNo);
		return receipt;
	}

	@Override
	public Receipt processBathOpenOilcard(
			TransOrderVO2<BatchOpenOnlineDetailVO> transorder,
			BatchOpenOnlineListVO order, Account sourceacc,
			OilcardAccount descacc, OilcardAccountProduct product)
			throws MaAccountException {
		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		OilcardAccountMapper ocaccDao = SpringBeanUtil.getInstance().getBean(OilcardAccountMapper.class);
		AccountValidateUtil.updateAccountSignature(descacc);
		int createAccount = ocaccDao.createAccount(descacc);
		String orderNo=null;
		if(1==createAccount)
		{
			//添加订单
			OilcardAccountOrder oaOrder = new OilcardAccountOrder();
			//VolumecardOrderVO order = transorder.getOrder();
			AppVO app = transorder.getApp();
			orderNo = NoGenerationUtil.genOrderNo();
			oaOrder.setAppId(app.getAppId());
			oaOrder.setAppname(transorder.getApp().getAppname());
			oaOrder.setAccountId(descacc.getAccountId());
			oaOrder.setInsertTime(new Date());
			oaOrder.setUpdateTime(new Date());
			oaOrder.setOrderId(orderNo);
//			oaOrder.setOriginalorderId(orderNo);
//		oaOrder.setProductName(product.getProductName());
			oaOrder.setSum(commproduct.getProductAmount());
			oaOrder.setAppOrderId(transorder.getOrder().getAppOrderId());
			oaOrder.setRemark(order.getRemark());
			oaOrder.setMethod(transorder.getMethod());
			oaOrder.setStatus("OK#");
			oaOrder.setBalance(commproduct.getProductAmount());
			oaOrder.setType(transorder.getOperationType());
			oaOrder.setPeerAccountId(null);
			oaOrder.setPeerAccountUnit(null);
			oaOrder.setPeerAccountType("TX#");
			oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			oaOrder.setChannelOrderId(order.getChannelOrderId());
			oaOrder.setProductName(commproduct.getProductName());
			oaOrder.setProductQuantity("1");
			oaOrder.setProductPrice(commproduct.getProductAmount());
			//更新订单
			OilcardAccountOrderMapper ocaccordDao = SpringBeanUtil.getInstance().getBean(OilcardAccountOrderMapper.class);
			int createOrdersuccess = ocaccordDao.createOrder(oaOrder);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(orderNo);
		return receipt;
	}

	
	/**
	 * 查询订单,包含开卡充值消费撤销等
	 * @param orderqueryvo
	 * @param pagingnation 
	 * @param yesterdaylimit 是否限定在昨天的数据 
	 * @return
	 */
	public List<JournalOrderOutputVO> queryJournalOrder(
			OrderQueryTransVO<SpendOrderQueryPagingVO> orderqueryvo, Pagingnation pagingnation,boolean yesterdaylimit){
		List<JournalOrderOutputVO> result;
		if(!yesterdaylimit){
			
			if(pagingnation!=null){
				int total = orderdao.queryJournalOrderTotalByPage(orderqueryvo.getQuery());
				pagingnation.setTotalCount(total);
			}
			result = orderdao.queryJournalOrderByPage(pagingnation, orderqueryvo.getQuery());
		}
		else{
			
			if(pagingnation!=null){
				int total = orderdao.queryYesterdayJournalOrderTotalByPage(orderqueryvo.getQuery());
				pagingnation.setTotalCount(total);
			}
			result = orderdao.queryYesterdayJournalOrderByPage(pagingnation, orderqueryvo.getQuery());
		}
		return result;
		
		
		
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt processOpenDiscountCardAndSetting(TransOrderVO2<YouMeDiscountCardOrderVO> transorder,
			Account sourceacc, DiscountCardAccount dcacc,DiscountCardProduct product)throws MaAccountException {

		ProductMapper productDao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		Product commproduct = productDao.selectByPrimaryKey(product.getProductId());
		//增加帐户信息
		DiscountCardAccountMapper daccDao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountMapper.class);
		AccountValidateUtil.updateAccountSignature(dcacc);
		int createAccount = daccDao.createAccount(dcacc);
		String orderNo=null;
		YouMeDiscountCardOrderVO body = transorder.getBody();
		if(1==createAccount)
		{
			//添加订单
			DiscountCardAccountOrder oaOrder = new DiscountCardAccountOrder();
			YouMeDiscountCardOrderVO order = transorder.getOrder();
			AppVO app = transorder.getApp();
			orderNo = NoGenerationUtil.genOrderNo();
			oaOrder.setOrderId(orderNo);
			oaOrder.setAppId(app.getAppId());
			oaOrder.setAppname(app.getAppname());
			oaOrder.setAccountId(dcacc.getAccountId());
			oaOrder.setMethod(transorder.getMethod());
			oaOrder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
			oaOrder.setSum(dcacc.getAmount());
			oaOrder.setBalance(dcacc.getBalance());
			oaOrder.setPeerAccountType("DCA");//TODO,CONSTANT
			oaOrder.setPeerAccountId(null);
			oaOrder.setPeerAccountUnit(null);
			oaOrder.setRemark(order.getRemark());
			oaOrder.setOriginalorderId(orderNo);
			oaOrder.setStatus(Account.STATUS_OK);
			oaOrder.setInsertTime(new Date());
			oaOrder.setUpdateTime(new Date());
			oaOrder.setAppOrderId(order.getAppOrderId());
			oaOrder.setType(transorder.getOperationType());
			oaOrder.setProductName(commproduct.getProductName());
			oaOrder.setProductPrice(order.getProductPrice());//使用传入的购买价格
			oaOrder.setProductQuantity("1");
			oaOrder.setChannelOrderId(order.getChannelOrderId());
			oaOrder.setStoreId(body.getStoreIds().get(0));
			List<TerminalList> tllist = tlDao.selectByStoreId(oaOrder.getStoreId());
			if(tllist!=null&&!tllist.isEmpty())
			{
				oaOrder.setSellerId(tllist.get(0).getSellerId());
			}
			
			//更新订单
			DiscountCardAccountOrderMapper dcaOrderDao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountOrderMapper.class);
			int createOrdersuccess = dcaOrderDao.createOrder(oaOrder);
			if(createOrdersuccess==1){
				
			}
			else{
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");
			}
			//设置时间
			try {
				accountPayAllowService.addPayProducts(dcacc.getAccountId(), dcacc.getAccountId(), order.getZjProducts().toArray(new String[]{}));
				accountPayAllowService.addPayStores(dcacc.getAccountId(), dcacc.getAccountId(), order.getStoreIds().toArray(new String[]{}), "");
				int ceend = NumberUtils.toInt(order.getCustomEndTime().substring(0, order.getCustomEndTime().indexOf(":")));
				int cestart = NumberUtils.toInt(StringUtils.substringBefore(order.getCustomStartTime(), ":"));
				accountPayAllowService.addPayTimes(dcacc.getAccountId(), dcacc.getAccountId(), new String[]{ order.getCustomStartTime()+"-"+order.getCustomEndTime()}, "DAY", "", "");
//				if(true) throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"设置账户的允许消费参数出错");
			} catch (DataInvalidException e) {
				log.error(String.format("设置账户的允许消费参数出错"),e);
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"设置账户的允许消费参数出错:"+e.getMessage()	);
			}
		}
		else{
			//更新订单不成功
			if (log.isDebugEnabled()) {
				log.debug(String.format("订单收入不成功"));
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		
		DefaultReceipt receipt = new DefaultReceipt();
		receipt.setOrderNo(orderNo);
		return receipt;
	
		
	}
	
	


}
