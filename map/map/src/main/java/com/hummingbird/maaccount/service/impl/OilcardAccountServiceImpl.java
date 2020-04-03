/**
 * 
 * InvestmentAccountService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.common.event.EventListenerContainer;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.MoneyUtil;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.util.TripleDESUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.util.NoGenerationUtil;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.CashAccountOrder;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.event.OpenCardEvent;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.CashAccountMapper;
import com.hummingbird.maaccount.mapper.CashAccountOrderMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountProductMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.CashAccountService;
import com.hummingbird.maaccount.service.OilcardAccountService;
import com.hummingbird.maaccount.service.OrderService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.util.BatchProcessReporter;
import com.hummingbird.maaccount.util.IndexObject;
import com.hummingbird.maaccount.util.OilcardReturnUtil;
import com.hummingbird.maaccount.util.SmsSendUtil;
import com.hummingbird.maaccount.vo.AntiPayoffline;
import com.hummingbird.maaccount.vo.AppVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineResultDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineResultVO;
import com.hummingbird.maaccount.vo.DefaultReceipt;
import com.hummingbird.maaccount.vo.IOrderVO;
import com.hummingbird.maaccount.vo.OfflinePayOrderVO;
import com.hummingbird.maaccount.vo.OilcardTrans2CashOrderVO;
import com.hummingbird.maaccount.vo.OrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午9:52:32
 * 本类主要做为 分期帐户service
 */
@Service("oilcardAccountServiceImpl")
public class OilcardAccountServiceImpl extends DefaultAccountService implements OilcardAccountService {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	@Autowired
	OilcardAccountMapper caDao;
	@Autowired
	UserMapper userDao;
	@Autowired
	UserService userSrv;
	@Autowired(required = true)
	protected OrderService orderSrv;
	@Autowired
	ProductMapper productDao;
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	@Autowired
	OilcardAccountProductMapper ocpDao;

	/**
	 * 现金订单
	 */
	@Autowired
	OilcardAccountOrderMapper caorderDao;
	@Autowired
	OilcardAccountProductMapper  oilcardprodDao;
	@Autowired
	ProductMapper  prodDao;
	
	
	/**
	 * 分期卡转钱计算
	 * @param mobile
	 * @param requireAmount 还需要转多少钱
	 * @param notenoughContinue 
	 * @param accountId 分期卡帐户id
	 * @return 
	 * @throws MaAccountException 
	 */
	public Map<OilcardAccount, Long> transferOutFromOilcard(String mobile,long requireAmount, boolean notenoughContinue, String accountId) throws MaAccountException{
		
		long tempamount = requireAmount;
		//查询用户下所有的分期卡
		Map<OilcardAccount,Long> transmap = new HashMap<OilcardAccount,Long>();
		List<OilcardAccount> accounts;
		if(StringUtils.isNotBlank(accountId)){
			accounts = new ArrayList<OilcardAccount>();
			OilcardAccount accountByAccountId = (OilcardAccount) caDao.getAccountByAccountId(accountId);
			if(accountByAccountId==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("帐户id[%s]无法找到分期卡",accountId));
				}
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,String.format("帐户id[%s]无法找到分期卡",accountId));
			}
			accounts.add(accountByAccountId);
		}
		else{
			accounts=caDao.selectUseableOilcard(mobile);
			
		}
			for (Iterator iterator = accounts.iterator(); iterator.hasNext();) {
				OilcardAccount oilcardAccount = (OilcardAccount) iterator
						.next();
				if(tempamount==0){
					//已经足够，不需要再转钱
					break;
				}
				Long cardbalance = oilcardAccount.getBalance();
				if(cardbalance>0){
					if(cardbalance>=tempamount){
						//卡足够钱
						cardbalance=cardbalance-tempamount;
						//oilcardAccount.setBalance(cardbalance);
						transmap.put(oilcardAccount, tempamount);
						tempamount=0;
						if(cardbalance==0){
							//无钱了
							
						}
					}
					else{
						//卡钱不足
						tempamount=tempamount-cardbalance;
						transmap.put(oilcardAccount, cardbalance);
						//oilcardAccount.setBalance(0L);
					}
				}
		}
		if(tempamount>0&&!notenoughContinue){
			throw new InsufficientAccountBalanceException(MaAccountException.ERR_ORDER_EXCEPTION,"分期卡可用余额不足");
		}
			
		return transmap;
		
	}
	
	/**
	 * 更新帐户
	 * @param oc
	 */
	public void updateAccount(OilcardAccount oc){
		AccountValidateUtil.updateAccountSignature(oc);
		caDao.updateByPrimaryKey(oc);
	}
	
	/**
	 * 从分期卡转钱到现金帐户，会实现帐户金额的变更和订单的生成
	 * @param mobile
	 * @param requireAmount 还需要转多少钱
	 * @return 是否转帐足够
	 * @throws MaAccountException 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt transferOilcard2cashaccount(TransOrderVO2<? extends IOrderVO> transorder,
			CashAccount descacc, Long requireSum,boolean notenoughContinue) throws MaAccountException{
		AccountValidateUtil.validateAccount(descacc);
		
		
		IOrderVO order = transorder.getOrder();
		OilcardAccountOrderMapper oaorderDao = SpringBeanUtil.getInstance().getBean(OilcardAccountOrderMapper.class);
		CashAccountService caSrv = SpringBeanUtil.getInstance().getBean(CashAccountService.class);
		CashAccountOrderMapper caorderDao = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
		DefaultReceipt dr = new DefaultReceipt();
		Map<OilcardAccount, Long> transferOutFromOilcard;
		if (order instanceof OilcardTrans2CashOrderVO) {
			OilcardTrans2CashOrderVO o2corder = (OilcardTrans2CashOrderVO) order;
			String accountId = o2corder.getAccountId();
			transferOutFromOilcard = transferOutFromOilcard(order.getMobileNum(),requireSum,notenoughContinue,accountId);
		}
		else{
			transferOutFromOilcard = transferOutFromOilcard(order.getMobileNum(),requireSum,notenoughContinue,null);
			
		}
		
		for (Iterator iterator = transferOutFromOilcard.keySet().iterator(); iterator.hasNext();) {
			OilcardAccount oc = (OilcardAccount) iterator.next();
			if(AccountValidateUtil.validateAccountSignature(oc)==1){
				
				dr.addOutAccount(oc);
				Long minussum = transferOutFromOilcard.get(oc);
				requireSum=requireSum-minussum;
				oc.setBalance(oc.getBalance()-minussum);
				oc.setUpdateTime(new Date());
				descacc.setBalance(descacc.getBalance()+minussum);
				//生成收入订单和支出订单
				OilcardAccountOrder exorder = createExpendOrder4cash(transorder,oc,descacc,minussum);
				CashAccountOrder inorder =  createIncomeOrder4cash(transorder,oc,descacc,minussum,exorder);
//				AccountValidateUtil.updateAccountSignature(oc);
				updateAccount(oc);
				AccountValidateUtil.updateAccountSignature(descacc);
				caSrv.updateAccount(descacc);
				oaorderDao.createOrder(exorder);
				caorderDao.createOrder(inorder);
				dr.setOrderNo(exorder.getOrderId());
				dr.setDealtime(exorder.getInsertTime());
			}
		}
		dr.addInAccount(descacc);
		dr.addExt("enough", requireSum<=0);
		return dr;
	}
	
	/**
	 * 按期数把分期卡的钱转至可用余额
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public void transOilcard2UseableByDay(){
		if (log.isDebugEnabled()) {
			log.debug(String.format("按期数把分期卡的钱转至可用余额开始"));
		}
		StopWatch sw = new StopWatch();
		sw.start();
//		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		Integer exeresult=0;
		try{
//		exeresult = jdbc.execute(new CallableStatementCreator() {
//			
//			@Override
//			public CallableStatement createCallableStatement(Connection con)
//					throws SQLException {
//				String storedProc = "{call transFromOilcard2use (?)}";// 调用的sql 
//				CallableStatement cs = con.prepareCall(storedProc);  
//		        cs.registerOutParameter(1,Types.INTEGER);// 注册输出参数的类型  
//		        return cs;  
//			}
//		}, new CallableStatementCallback<Integer>() {
//
//			@Override
//			public Integer doInCallableStatement(CallableStatement cs)
//					throws SQLException, DataAccessException {
//				cs.execute();  
//		        return cs.getInt(1);// 获取输出参数的值
//			}
//		}) ;
		
			//查询符合条件的数据
			List<OilcardAccount> accountlist = caDao.selectTimeToReturn();
			exeresult = accountlist.size();
			//遍历出来,根据
			String smsnotify = new PropertiesUtil().getProperty("sms.oilcard.return.notify");
			for (Iterator iterator = accountlist.iterator(); iterator.hasNext();) {
				OilcardAccount oilcardAccount = (OilcardAccount) iterator.next();
				try {
					returnMoneyFromOilcardInvestment(oilcardAccount,smsnotify);
				} catch (Exception e) {
					log.error(String.format("帐户%s处理出错，跳过 ",oilcardAccount),e);
				}
			}
		}catch(Exception e){
			log.error("按期数把分期卡的钱转至可用余额出错",e);
		}
		finally{
			
			sw.stop();
			if (log.isDebugEnabled()) {
				log.debug(String.format("按期数把分期卡的钱转至可用余额，执行结果为%s,耗时%s秒",exeresult<0?"失败":("成功，更新"+exeresult+"个帐户"),sw.getTime()/1000));
			}
		}
	}

		/**
		 * 把分期卡的投资的钱转出来
		 * @param oilcardAccount
		 * @param smsnotify 
		 * @throws MaAccountException 
		 * @throws DataInvalidException 
		 */
		private void returnMoneyFromOilcardInvestment(OilcardAccount oilcardAccount, String smsnotify) throws MaAccountException, DataInvalidException {
//			update t_oilcard_account set balance= balance+(select  p.returnSum  from t_oilcard_product p where p.productId=t_oilcard_account.productId)
//					,restStages=restStages-1,restAmount=restAmount- (select  p.returnSum  from t_oilcard_product p where p.productId=t_oilcard_account.productId)
//					,updateTime=now()
//					,signature=md5(CONCAT('OCA',userId,accountId,status,balance,amount,restAmount,restStages,ifnull(date_format(startTime,'%Y-%m-%d %H:%i:%s'),0)
//					,ifnull(date_format(endTime,'%Y-%m-%d %H:%i:%s'),0),ifnull(date_format(insertTime,'%Y-%m-%d %H:%i:%s'),0),ifnull(date_format(updateTime,'%Y-%m-%d %H:%i:%s'),0),productId,channelNo))
			if (log.isDebugEnabled()) {
				log.debug(String.format("对帐户%s进行处理",oilcardAccount));
			}
			//检查是否被串改
			AccountValidateUtil.validateAccount(oilcardAccount);
			
			//获取对应的产品
			OilcardAccountProduct product = oilcardprodDao.selectByPrimaryKey(oilcardAccount.getProductId());
			boolean doReturn = OilcardReturnUtil.doReturn(oilcardAccount, product);
			if(!doReturn){
				if (log.isDebugEnabled()) {
					log.debug(String.format("没有执行返还操作"));
				}
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"没有执行返还");
			}
			Long returnSum = product.getReturnSum();
//			//暂时按固定金额反
//			oilcardAccount.setBalance(oilcardAccount.getBalance()+returnSum);
//			oilcardAccount.setRestStages(oilcardAccount.getRestStages()-1);
			boolean isend = false;
//			if(oilcardAccount.getRestStages()==0){
//				oilcardAccount.setStatus("END");//完结
//				isend=true;
//			}
			isend=("END".equals(oilcardAccount.getStatus()));
				
//			oilcardAccount.setRestAmount(oilcardAccount.getRestAmount()-returnSum);
//			oilcardAccount.setUpdateTime(new Date());
//			AccountValidateUtil.updateAccountSignature(oilcardAccount);
			caDao.updateAccount(oilcardAccount);
			//发送短信
			Map<String,String> param = new HashMap<String,String>();
			param.put("returnSum", MoneyUtil.getMoneyStringDecimal2fen(returnSum));
			param.put("cardNo", oilcardAccount.getAccountId());
			param.put("productName",product.getProductName());
			param.put("leftstage", isend?"已经完成返还":("剩下"+oilcardAccount.getRestStages()+"期待返还"));
			
			User user = userDao.selectByPrimaryKey(oilcardAccount.getUserId());
			SmsSendUtil.delaySmsSend(StrUtil.replaceAll(smsnotify, param), null, user.getMobilenum());
			
			if (log.isDebugEnabled()) {
				log.debug(String.format("帐户%s处理完成",oilcardAccount));
			}
		}


	/**
	 * 转换分期卡可用余额至现金帐户，会实现帐户金额的变更和订单的生成
	 * @param order
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt transferOilcard2cashaccount(OilcardAccount oc) throws MaAccountException{
		
		
		
		if(oc==null){
			if(log.isDebugEnabled()){
				log.debug("油卡帐户不存在，不处理");
			}
			return null;
		}
		
		//验证分期卡有没有被篡改
		AccountValidateUtil.validateAccount(oc);
		if(log.isDebugEnabled()){
			log.debug(String.format("转换分期卡[%s]到现金帐户",oc));
		}
		try{
			
			//查询现金帐户
			Integer userId = oc.getUserId();
			CashAccountMapper cadao = SpringBeanUtil.getInstance().getBean(CashAccountMapper.class);
			CashAccount descacc = (CashAccount) cadao.getAccountByUserId(userId);
			if(descacc==null){
				log.error("用户"+userId+"的现金帐户不存在，进行创建");
				try {
					AccountFactory.createAccounts(userId);
				} catch (MaAccountException e) {
					log.error(String.format("创建用户失败"),e);
					return null;
				}
				descacc = (CashAccount) cadao.getAccountByUserId(userId);
			}
			//验证查询出来的现金账户是否被篡改
			AccountValidateUtil.validateAccount(descacc);
			
			
			OilcardAccountOrderMapper oaorderDao = SpringBeanUtil.getInstance().getBean(OilcardAccountOrderMapper.class);
			CashAccountService caSrv = SpringBeanUtil.getInstance().getBean(CashAccountService.class);
			CashAccountOrderMapper caorderDao = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
			DefaultReceipt dr = new DefaultReceipt();
			Long minussum = oc.getBalance();
			descacc.setBalance(descacc.getBalance()+oc.getBalance());
			dr.addOutAccount(oc);
			oc.setBalance(0L);
			oc.setUpdateTime(new Date());
			//生成收入订单和支出订单
			TransOrderVO2<OrderVO> transorder = new TransOrderVO2<OrderVO >();
			AppVO appVO = new AppVO();
			appVO.setAppId("system");
			appVO.setAppname("系统自动");
			transorder.setApp(appVO);//无app
			OrderVO ordervo = new OrderVO();
			ordervo.setRemark("自动转移分期卡可用余额");
			transorder.setOrder(ordervo);
			
			OilcardAccountOrder exorder = createExpendOrder4cash(transorder,oc,descacc,minussum);
			CashAccountOrder inorder =  createIncomeOrder4cash(transorder,oc,descacc,minussum,exorder);
			AccountValidateUtil.updateAccountSignature(oc);
			updateAccount(oc);
			AccountValidateUtil.updateAccountSignature(descacc);
			caSrv.updateAccount(descacc);
			oaorderDao.createOrder(exorder);
			caorderDao.createOrder(inorder);
			dr.setOrderNo(exorder.getOrderId());
			dr.setDealtime(exorder.getInsertTime());
			dr.addInAccount(descacc);
			return dr;
		}
		catch(Exception e)
		{
			log.error("转换分期卡可用余额至现金帐户出错，不执行处理",e);
			return null;
		}
	}

	/**
	 * @param transorder
	 * @param oc
	 * @param descacc
	 * @param minussum
	 * @param exorder 
	 * @return
	 */
	private CashAccountOrder createIncomeOrder4cash(TransOrderVO2<? extends IOrderVO> transorder,
			OilcardAccount oc, CashAccount descacc, Long minussum, OilcardAccountOrder exorder) {
		CashAccountOrder inorder = new CashAccountOrder();
		IOrderVO io = transorder.getOrder();
		AppVO app = transorder.getApp();
		String orderNo = NoGenerationUtil.genOrderNo();
		if (io instanceof OfflinePayOrderVO) {
			OfflinePayOrderVO order = (OfflinePayOrderVO) io;
			inorder.setAppOrderId(order.getAppOrderId());
			inorder.setRemark(order.getRemark());
//			inorder.sett
		}
		else if (io instanceof OrderVO) {
			OrderVO order = (OrderVO) io;
			inorder.setAppOrderId(order.getAppOrderId());
			inorder.setRemark(order.getRemark());
		}
		inorder.setAppId(app.getAppId());
		inorder.setAppname(app.getAppname());
		inorder.setAccountId(descacc.getAccountId());
		inorder.setInsertTime(new Date());
		inorder.setUpdateTime(new Date());
		inorder.setOrderId(orderNo);
		inorder.setOriginalorderId(exorder.getOrderId());
		inorder.setOriginaltable(OrderConst.ORDER_TABLE_OIL_CARD);
		inorder.setMethod("/oilcard/transfer_to_cashAccount");
//	oaOrder.setProductName(product.getProductName());
		inorder.setSum(minussum);
		inorder.setStatus("OK#");
		inorder.setBalance(descacc.getBalance());
		inorder.setType(OrderConst.ORDER_OPERATION_ZZ);
		inorder.setPeerAccountId(oc.getAccountId());
		inorder.setPeerAccountUnit("营销账户平台");
		inorder.setPeerAccountType(AccountFactory.getAccountFlag4Order(oc.getAccountCode()));
		inorder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
		
		return inorder;
	}

	/**
	 * @param transorder
	 * @param oc
	 * @param descacc
	 * @param requireSum
	 * @return 
	 */
	private OilcardAccountOrder createExpendOrder4cash(TransOrderVO2<? extends IOrderVO> transorder,
			OilcardAccount oc, CashAccount descacc, Long minussum) {
		OilcardAccountOrder exorder = new OilcardAccountOrder();
		AppVO app = transorder.getApp();
		IOrderVO io = transorder.getOrder();
		String orderNo = NoGenerationUtil.genOrderNo();
		if (io instanceof OfflinePayOrderVO) {
			OfflinePayOrderVO order = (OfflinePayOrderVO) io;
			exorder.setAppOrderId(order.getAppOrderId());
			exorder.setRemark(order.getRemark());
		}
		else if (io instanceof OrderVO) {
			OrderVO order = (OrderVO) io;
			exorder.setAppOrderId(order.getAppOrderId());
			exorder.setRemark(order.getRemark());
		}
		exorder.setAppId(app.getAppId());
		exorder.setAppname(app.getAppname());
		exorder.setAccountId(oc.getAccountId());
		exorder.setInsertTime(new Date());
		exorder.setUpdateTime(new Date());
		exorder.setOrderId(orderNo);
		exorder.setOriginalorderId(orderNo);
		exorder.setMethod("/oilcard/transfer_to_cashAccount");
//	oaOrder.setProductName(product.getProductName());
		exorder.setSum(0-minussum);
		exorder.setStatus("OK#");
		exorder.setBalance(oc.getBalance());
		exorder.setType(OrderConst.ORDER_OPERATION_ZZ);
		exorder.setPeerAccountId(descacc.getAccountId());
		exorder.setPeerAccountUnit("营销账户平台");
		exorder.setPeerAccountType(AccountFactory.getAccountFlag4Order(descacc.getAccountCode()));
		exorder.setFlowDirection(OrderConst.FLOW_DIRECTION_OUT);
		return exorder;
	}
	
	/**
	 * 冲正/撤销 线下支付
	 * @param transorder
	 * @param sourceacc
	 * @param descacc
	 * @return
	 * @throws MaAccountException 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt antiPayOffineByOilcard(TransOrderVO2<AntiPayoffline> transorder,
			Account sourceacc, CashAccount descacc) throws MaAccountException{
		AntiPayoffline io = transorder.getOrder();
		String orderId = io.getOrderId();//原来的orderid
		CashAccountOrderMapper caodao = SpringBeanUtil.getInstance().getBean(CashAccountOrderMapper.class);
		CashAccountOrder caOrder = caodao.selectByPrimaryKey(orderId);
		AppVO app = transorder.getApp();
		String orderNo = NoGenerationUtil.genOrderNo();
		long sum = Math.abs(caOrder.getSum());//原订单是支出订单，是负值
		descacc.setBalance(descacc.getBalance()+sum);
		
		//生成现金帐户收入订单
		CashAccountOrder inorder = new CashAccountOrder();
		
		inorder.setAppId(app.getAppId());
		inorder.setAppname(app.getAppname());
		inorder.setAccountId(descacc.getAccountId());
		inorder.setInsertTime(new Date());
		inorder.setUpdateTime(new Date());
		inorder.setOrderId(orderNo);
		inorder.setOriginalorderId(orderId);
		inorder.setOriginaltable(OrderConst.ORDER_TABLE_CASH);
		inorder.setMethod(transorder.getMethod());
//	oaOrder.setProductName(product.getProductName());
		inorder.setSum(sum);
		inorder.setStatus("OK#");
		inorder.setBalance(descacc.getBalance());
		inorder.setType(transorder.getOperationType());
		inorder.setFlowDirection(OrderConst.FLOW_DIRECTION_IN);
		inorder.setTerminalOrderId(io.getTerminalOrderId());
		inorder.setTerminalId(io.getTerminalId());
		inorder.setOperatorId(io.getOperatorId());
		//如果撤销时,storeid为null或"null"时,从原来的订单获取storeid,因为pos机没有送storeid过来
		if(StringUtils.equalsIgnoreCase("null", io.getStoreId())||io.getStoreId()==null){
			inorder.setStoreId(caOrder.getStoreId());
		}
		else{
			inorder.setStoreId(io.getStoreId());
		}
		
		inorder.setSellerId(io.getSellerId());
		inorder.setBatchNo(io.getBatchNo());
		inorder.setAppOrderId(io.getAppOrderId());
		inorder.setProductName(caOrder.getProductName());
		inorder.setProductPrice(caOrder.getProductPrice());
		inorder.setProductQuantity(caOrder.getProductQuantity());
		inorder.setShiftInfo(caOrder.getShiftInfo());
		
		CashAccountService caSrv = SpringBeanUtil.getInstance().getBean(CashAccountService.class);
		AccountValidateUtil.updateAccountSignature(descacc);
		boolean updateAccountsucc = caSrv.updateAccount(descacc);
		if(!updateAccountsucc){
			log.error(String.format("帐户[%s]更新不成功",descacc));
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		int createOrdersucc = caodao.createOrder(inorder);
		if(createOrdersucc!=1){
			log.error(String.format("订单[%s]创建不成功",inorder));
			throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");

		}
		DefaultReceipt dr = new DefaultReceipt();
		dr.setDealtime(inorder.getInsertTime());
		dr.setOrderNo(orderNo);
		dr.addInAccount(descacc);
		dr.addExt("inorder", inorder);
		return dr;
		
	}

	/**
	 * 根据第三方应用id查询开卡情况
	 * @param appId
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public OilcardAccountOrder queryOilcardAccountByapporderId(String appId, String appOrderId) throws MaAccountException{
		OilcardAccountOrder order = caorderDao.selectByChannelOrderId(appId,appOrderId);
		if(order==null)
		{
			throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"根据channelOrderId["+appOrderId+"]获取不到订单");
		}
		Account account = caDao.getAccountByAccountId(order.getAccountId());
		order.setAccount((OilcardAccount) account);
		return order;
	}
	
	/**
	 * 交割
	 * @param appOrderId
	 * @return 
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	public OilcardAccount deliveryOilcard(String appOrderId) throws DataInvalidException, MaAccountException{
		List<OilcardAccountOrder> oilcards = caorderDao.selectByAppOrderId(appOrderId);
		if(oilcards==null||oilcards.isEmpty())
		{
			if(log.isDebugEnabled())
			{
				log.debug("执行分期卡开卡状态设置启用:"+appOrderId+",找不到对应的分期卡信息");
			}
			throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS.clone(null, "分期卡不存在");
		}
		//只取第一条
		OilcardAccountOrder oilcard = oilcards.get(0);
		String accountId = oilcard.getAccountId();
		OilcardAccount account = (OilcardAccount) caDao.getAccountByAccountId(accountId);
		if(account==null){
			if(log.isDebugEnabled())
			{
				log.debug("执行分期卡开卡状态设置启用:"+appOrderId+",找不到对应的分期卡帐户信息");
			}
			throw ValidateException.ERROR_EXISTING_ACCOUNT_NOT_EXISTS.clone(null, "分期卡不存在");
		}
		if(!OilcardAccount.STATUS_NOP.equals(account.getStatus())){
			if(log.isDebugEnabled())
			{
				log.debug("执行分期卡开卡状态设置启用:"+appOrderId+",帐户id="+account.getAccountId()+"，帐户状态非‘未开通’");
			}
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"分期卡状态不正确");
		}
		//设置为可用，并且返还第一期数据
		account.setStatus(OilcardAccount.STATUS_OK);
		
		OilcardAccountProduct product = oilcardprodDao.selectByPrimaryKey(account.getProductId());
		Product commonproduct = prodDao.selectByPrimaryKey(account.getProductId());
		account.setRestAmount(account.getRestAmount()-product.getReturnSum());
		account.setRestStages(account.getRestStages()-1);
		account.setBalance(account.getBalance()+product.getReturnSum());
		account.setUpdateTime(new Date());
		
		caDao.updateAccount(account);
		return account;
	}

	@Override
	public Account getAccount(String mobileNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Receipt income(Order order) throws MaAccountException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Receipt expense(Order order) throws MaAccountException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account createAccount(Integer userId) throws MaAccountException {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public Account getAccountByAccountId(String accountId)
			throws MaAccountException {
		return caDao.getAccountByAccountId(accountId);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public ResultModel createUserBatch(
			TransOrderVO2<BatchOpenOnlineDetailVO> transorder, AppInfo app) {
		
		if (log.isDebugEnabled()) {
			log.debug(String.format("分期卡批量开卡开始"));
		}
		String accountId =null;
		ResultModel rm = new ResultModel();
		List<BatchOpenOnlineListVO> orders=transorder.getOrder().getOrders();
		String channelkey = TripleDESUtil.decryptBased3Des(transorder.getOrder().getChannelKey(),app.getAppKey());
		
		BatchProcessReporter reporter = new BatchProcessReporter();
		List<BatchOpenOnlineResultVO> resultList=new ArrayList<BatchOpenOnlineResultVO>();
		Map<String, OilcardAccountProduct> productmap = new HashMap<String, OilcardAccountProduct>();
		Map<String, Product> commproductmap = new HashMap<String, Product>();
		int index=1;
		PropertiesUtil pu = new PropertiesUtil();
		boolean needdes = pu.getBoolean("addBatchUser.des", true);//是否解密
		for(BatchOpenOnlineListVO order:orders){
			BatchOpenOnlineResultVO result=new BatchOpenOnlineResultVO();
			BatchOpenOnlineResultDetailVO resultCard=new BatchOpenOnlineResultDetailVO();
			if (log.isDebugEnabled()) {
				log.debug(String.format("处理第%s条数据",index++));
			}
			try {
				//解密
				if(needdes){
					
					order.setID(TripleDESUtil.decryptBased3Des(order.getID(), app.getAppKey()));
					order.setName(TripleDESUtil.decryptBased3Des(order.getName(), app.getAppKey()));
					order.setMobileNum(TripleDESUtil.decryptBased3Des(order.getMobileNum(), app.getAppKey()));
					order.setRemark(TripleDESUtil.decryptBased3Des(order.getRemark(), app.getAppKey()));
				}
				ValidateUtil.validateMobile(order.getMobileNum());
				//检查手机号码是否已经注册过，如果未注册，则注册该用户，并用新用户交易密码短息模板下行短信；
				User user= userDao.selectByMobile(order.getMobileNum());
				//判断渠道号是否被处理了
				OilcardAccountOrder selectByChannelOrderId = caorderDao.selectByChannelOrderId(transorder.getApp().getAppId(),order.getChannelOrderId());
				if(selectByChannelOrderId!=null){
					if (log.isDebugEnabled()) {
						log.debug(String.format("渠道订单号%s已存在，即已进行过开卡，不处理",order.getChannelOrderId()));
					}
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"开卡请求已处理，不重复开卡");
				}
				//根据productId查询卡类型
				String productId = order.getProductId();
				OilcardAccountProduct product = productmap.computeIfAbsent(productId, new Function<String, OilcardAccountProduct>() {
					@Override
					public OilcardAccountProduct apply(String productId) {
						return ocpDao.selectByPrimaryKey(productId);
					}
				});
				Product commproduct = commproductmap.computeIfAbsent(productId, new Function<String, Product>() {

					@Override
					public Product apply(String t) {
						return productDao.selectByPrimaryKey(productId);
					}
				});
//				OilcardAccountProduct product = ocpDao.selectByPrimaryKey(productId);
				if(product==null)
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("根据类型%s查询折扣卡，结果为空",productId));
					}
					throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS.clone(null, "找不到对应的产品");
				}
				if(!"OK#".equals(product.getStatus()))
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("折扣卡%s已下线",productId));
					}
					throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "容量卡产品已下线");
				}
				if(user==null){
					if (log.isDebugEnabled()) {
						log.debug(String.format("手机号码%s并没有注册，这里为其创建用户",order.getMobileNum()));
					}
					String smstemplateid ="main";
					smstemplateid = StringUtils.defaultIfEmpty(smstemplateid, "main");
					String maintemplate = pu.getProperty("sms.newuser.template.main."+smstemplateid);
					user = new User();
					user.setInsertTime(new Date());
					user.setName(order.getName());
					user.setId(order.getID());
					String mobileNum = order.getMobileNum();
					user.setMobilenum(mobileNum);
					
					
					String part="";
					String pw = RandomStringUtils.randomAlphabetic(8);
					user.setPassword(Md5Util.Encrypt(pw));
					String pwpart = pu.getProperty("sms.newuser.template.pw."+smstemplateid);
					part = StrUtil.replaceAllWithToken(pwpart, "password", pw);
					
					maintemplate=maintemplate.replaceAll("\\$\\{sms.newuser.template.pw\\}", part);
					part="";
					
					maintemplate=maintemplate.replaceAll("\\$\\{sms.newuser.template.paymentpw\\}", part);
					part="";
					
					maintemplate=maintemplate.replaceAll("\\$\\{sms.newuser.template.pospw\\}", part);
					
					
					//发短信
					userDao.insert(user);
					userDao.insertAppId(user.getUserId(), app.getAppId());
					SmsSendUtil.delaySmsSend(maintemplate, null, user.getMobilenum());
					AccountFactory.createAccounts(user.getUserId());
					
				}
				
				//创建折扣卡
				OilcardAccount dcacc = createOilcardAccount(transorder.getOrder().getChannelNo(),order,product);
				accountId = dcacc.getAccountId();
				Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,order.getMobileNum());
				ValidateUtil.assertNull(dcacc, "容量卡帐户");
				ValidateUtil.assertNull(sourceacc, "应用帐户");
				if(log.isDebugEnabled()){
					log.debug("检验通过，获取请求");
				}
				Receipt processOrder = orderSrv.processBathOpenOilcard(transorder,order, sourceacc, dcacc, product);
				
				
				
				//返回折扣卡信息
				result.setChannelOrderId(order.getChannelOrderId());
				resultCard.setBalance(dcacc.getBalance());
				resultCard.setAccountId(dcacc.getAccountId());
				resultCard.setAmount(dcacc.getAmount());
				resultCard.setEndTime(dcacc.getEndTime().toString());
				resultCard.setStartTime(dcacc.getStartTime().toString());
				resultCard.setStatus(dcacc.getStatus());
				result.setCard(resultCard);
				result.setMobile(order.getMobileNum());
				result.setOrderId(processOrder.getOrderNo());
				resultList.add(result);
				
				//发送开卡成功的短信通知
				Map smsparam;
				try {
					smsparam = BeanUtils.describe(dcacc);
					smsparam.put("productName", product.getProductName());

					smsparam.put("amount", MoneyUtil.getMoneyStringDecimal2fen(dcacc.getAmount()));
					smsparam.put("returnSum", MoneyUtil.getMoneyStringDecimal2fen(product.getReturnSum()));

					String content = new PropertiesUtil().getProperty("sms.online_openoilcard.success");
					content = StrUtil.replaceAll(content,smsparam);
					SmsSendUtil.delaySmsSend(content, null, user.getMobilenum());
				} catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
					log.error("发送开卡成功的短信通知失败", e);
				} 
				reporter.addSuccess();
				//添加开卡结果通知
				OpenCardEvent oce = new OpenCardEvent(result,product,commproduct,channelkey,transorder.getOrder().getChannelNo());
				EventListenerContainer.getInstance().fireEvent(oce);
			}catch (DataInvalidException | MaAccountException  e) {
				log.error(String.format("折扣卡开卡%s失败",order.getMobileNum()),e);
				Map errmsgmap = new HashMap();
				errmsgmap.put("mobileNum", order.getMobileNum());
				errmsgmap.put("channelNo", order.getChannelOrderId());
				errmsgmap.put("msg", e.getMessage());
				reporter.addFail(errmsgmap);
				//添加开卡结果通知
				OpenCardEvent oce = new OpenCardEvent(order,transorder.getOrder(),e.getMessage(),channelkey);
				EventListenerContainer.getInstance().fireEvent(oce);
			}
			
		}
		rm.put("users", resultList);
		rm.put("total", reporter.getTotal());
		rm.put("successed", reporter.getSuccess());
		List fails = new ArrayList();
		List<IndexObject<Map>> failmsgs = reporter.getFailmsgs();
		for (Iterator iterator = failmsgs.iterator(); iterator.hasNext();) {
			IndexObject<Map> indexObject = (IndexObject<Map>) iterator
					.next();
			fails.add(indexObject.getObject());
		}
		rm.put("fails", fails);
		return rm;
	}

	@Override
	public OilcardAccount createOilcardAccount(String channleNo,
			BatchOpenOnlineListVO order, OilcardAccountProduct product)
			throws DataInvalidException, MaAccountException {
		OilcardAccount account = new OilcardAccount();
		Product commonproduct= productDao.selectByPrimaryKey(product.getProductId());
		User user = userSrv.getUserByMobile(order.getMobileNum());
		if(user==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户[手机号%s]不存在",order.getMobileNum()));
			}
			throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
		}
		//开卡时，还要经过审核操作，在此之前不能使用，也不会进行分期操作。
//		account.setAccountId(NoGenerationUtil.genNO("oc_"));//卡号
		String accountid = accountIdSrv.prepareGetAccountId(order.getProductId());
		account.setAccountId(accountid);
		account.setRemark(order.getRemark());
		account.setUserId(user.getUserId());
		account.setProductId(order.getProductId());
		
		account.setRestAmount(commonproduct.getProductAmount()-product.getReturnSum());
		account.setAmount(commonproduct.getProductAmount());
		account.setRestStages(product.getTotalStages()-1);
		account.setBalance(product.getReturnSum());
		Date insertTime = new Date();
    	insertTime=(DateUtils.truncate(insertTime, Calendar.SECOND));
		account.setInsertTime(insertTime);
		account.setUpdateTime(insertTime);
		account.setChannelNo(channleNo); 
//		account.setStartTime(new Date());
		//现在就可以用
		Date startdate =insertTime;
		Date dayStart = DateUtil.toDayStart(startdate);
		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
		account.setStartTime(dayStart);
		account.setEndTime(dayend);
		account.setStatus(OilcardAccount.STATUS_OK);
		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}

}
