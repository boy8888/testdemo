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
import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.DiscountCardAccountOrder;
import com.hummingbird.maaccount.entity.DiscountCardProduct;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.event.OpenCardEvent;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.DefaultAccountDao;
import com.hummingbird.maaccount.mapper.DiscountCardAccountMapper;
import com.hummingbird.maaccount.mapper.DiscountCardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.DiscountCardProductMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.DiscountCardAccountService;
import com.hummingbird.maaccount.service.OrderService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.util.BatchProcessReporter;
import com.hummingbird.maaccount.util.IndexObject;
import com.hummingbird.maaccount.util.SmsSendUtil;
import com.hummingbird.maaccount.vo.AntiPayoffline;
import com.hummingbird.maaccount.vo.AppVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineResultDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineResultVO;
import com.hummingbird.maaccount.vo.DefaultReceipt;
import com.hummingbird.maaccount.vo.DiscountCardResultVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;

@Service
public class DiscountCardAccountServiceImpl extends DefaultAccountService implements DiscountCardAccountService {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	@Autowired(required = true)
	private SmsSendService smsSender;
	@Autowired
	ProductMapper productDao;
	@Autowired
	DiscountCardAccountMapper dcaDao;
	@Autowired
	protected UserMapper userdao;
	@Autowired
	UserService userSrv;
	@Autowired
	DiscountCardProductMapper dcproDao;
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	@Autowired
	DiscountCardAccountOrderMapper dcaorderDao;
	@Autowired(required = true)
	protected OrderService orderSrv;
	@Override
	public DiscountCardAccountOrder queryDiscountCardAccountByApporderId(
			String appId, String appOrderId) throws MaAccountException{
		DiscountCardAccountOrder order = dcaorderDao.selectByChannelOrderId(appId,appOrderId);
		if(order==null)
		{
			throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"根据channelOrderId["+appOrderId+"]获取不到订单");
		}
		Account account = dcaDao.getAccountByAccountId(order.getAccountId());
		order.setAccount((DiscountCardAccount) account);
		return order;
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


	/**
	 * 更新帐户
	 * @param oc
	 */
	
	public boolean updateAccount(DiscountCardAccount account){
		int updatesuccess = dcaDao.updateByPrimaryKey(account);;
		return 1==updatesuccess;
	}
	
	@Override
	public Account getAccountByAccountId(String accountId)
			throws MaAccountException {
		return dcaDao.getAccountByAccountId(accountId);
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
	public Receipt antiPayOffineByDiscard(
			TransOrderVO2<AntiPayoffline> transorder, Account sourceacc,
			DiscountCardAccount descacc) throws MaAccountException {
		AntiPayoffline io = transorder.getOrder();
		String orderId = io.getOrderId();//原来的orderid
		DiscountCardAccountOrderMapper disdao = SpringBeanUtil.getInstance().getBean(DiscountCardAccountOrderMapper.class);
		DiscountCardAccountOrder disorder = disdao.selectByPrimaryKey(orderId);
		AppVO app = transorder.getApp();
		String orderNo = NoGenerationUtil.genOrderNo();
		long sum = Math.abs(disorder.getSum());//原订单是支出订单，是负值
		descacc.setBalance(descacc.getBalance()+sum);
		
		//收入订单
		DiscountCardAccountOrder inorder = new DiscountCardAccountOrder();
		
		inorder.setAppId(app.getAppId());
		inorder.setAppname(app.getAppname());
		inorder.setAccountId(descacc.getAccountId());
		inorder.setInsertTime(new Date());
		inorder.setUpdateTime(new Date());
		inorder.setOrderId(orderNo);
		inorder.setOriginalorderId(orderId);
		inorder.setOriginaltable(OrderConst.ORDER_TABLE_DIS_CARD);
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
			inorder.setStoreId(disorder.getStoreId());
		}
		else{
			inorder.setStoreId(io.getStoreId());
		}
		inorder.setSellerId(io.getSellerId());
		inorder.setBatchNo(io.getBatchNo());
		inorder.setAppOrderId(io.getAppOrderId());
		inorder.setProductName(disorder.getProductName());
		inorder.setProductPrice(disorder.getProductPrice());
		inorder.setProductQuantity(disorder.getProductQuantity());
		inorder.setShiftInfo(disorder.getShiftInfo());
		
		DiscountCardAccountService disSrv = SpringBeanUtil.getInstance().getBean(DiscountCardAccountService.class);
		AccountValidateUtil.updateAccountSignature(descacc);
		boolean updateAccountsucc = disSrv.updateAccount(descacc);
		if(!updateAccountsucc){
			log.error(String.format("帐户[%s]更新不成功",descacc));
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		int createOrdersucc = disdao.createOrder(inorder);
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
	
	@Override
	public DefaultAccountDao getAccountDao() {
		return dcaDao;
	}

	//@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public ResultModel createUserBatch(
			TransOrderVO2<BatchOpenOnlineDetailVO> transorder, AppInfo app) {
		
		if (log.isDebugEnabled()) {
			log.debug(String.format("折扣卡批量开卡开始"));
		}
		String accountId =null;
		ResultModel rm = new ResultModel();
		List<BatchOpenOnlineListVO> orders=transorder.getOrder().getOrders();
		String channelkey = TripleDESUtil.decryptBased3Des(transorder.getOrder().getChannelKey(),app.getAppKey());

		BatchProcessReporter reporter = new BatchProcessReporter();
		List<BatchOpenOnlineResultVO> resultList=new ArrayList<BatchOpenOnlineResultVO>();
		Map<String, DiscountCardProduct> productmap = new HashMap<String, DiscountCardProduct>();
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
				User user= userdao.selectByMobile(order.getMobileNum());
				//判断渠道号是否被处理了
				DiscountCardAccountOrder selectByChannelOrderId = dcaorderDao.selectByChannelOrderId(transorder.getApp().getAppId(),order.getChannelOrderId());
				if(selectByChannelOrderId!=null){
					if (log.isDebugEnabled()) {
						log.debug(String.format("渠道订单号%s已存在，即已进行过开卡，不处理",order.getChannelOrderId()));
					}
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"开卡请求已处理，不重复开卡");
				}
				//根据productId查询卡类型
				String productId = order.getProductId();
				DiscountCardProduct product = productmap.computeIfAbsent(productId, new Function<String, DiscountCardProduct>() {
					@Override
					public DiscountCardProduct apply(String productId) {
						return dcproDao.selectByPrimaryKey(productId);
					}
				});
				Product commproduct = commproductmap.computeIfAbsent(productId, new Function<String, Product>() {

					@Override
					public Product apply(String t) {
						return productDao.selectByPrimaryKey(productId);
					}
				});
//				DiscountCardProduct product = dcproDao.selectByPrimaryKey(productId);
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
					throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "折扣卡产品已下线");
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
					SmsSendUtil.delaySmsSend(mobileNum, maintemplate, null,  app.getAppId(), "开卡添加客户");
//					SmsSendUtil.delaySmsSend(maintemplate, null, user.getMobilenum());
					AccountFactory.createAccounts(user.getUserId());
					
				}
				
				//创建折扣卡
				DiscountCardAccount dcacc = createDiscountCardAccount(transorder.getOrder().getChannelNo(),order,product,commproduct);
				accountId = dcacc.getAccountId();
				Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,order.getMobileNum());
				ValidateUtil.assertNull(dcacc, "折扣卡帐户");
				ValidateUtil.assertNull(sourceacc, "应用帐户");
				if(log.isDebugEnabled()){
					log.debug("检验通过，获取请求");
				}
				Receipt processOrder = orderSrv.processBathOpenDiscountCard(transorder,order, sourceacc, dcacc, product);
				
				//返回折扣卡信息
				DiscountCardResultVO dccardResultVO = new DiscountCardResultVO(dcacc, product);
				result.setChannelOrderId(order.getChannelOrderId());
				resultCard.setBalance(dccardResultVO.getBalance());
				resultCard.setAccountId(dccardResultVO.getAccountId());
				resultCard.setAmount(dccardResultVO.getAmount());
				resultCard.setEndTime(dccardResultVO.getEndTime());
				resultCard.setStartTime(dccardResultVO.getStartTime());
				resultCard.setStatus(dccardResultVO.getStatus());
				result.setCard(resultCard);
				result.setMobile(order.getMobileNum());
				result.setOrderId(processOrder.getOrderNo());
				resultList.add(result);
				
				//发送开卡成功的短信通知
				Map smsparam;
				try {
					smsparam = BeanUtils.describe(dccardResultVO);
					smsparam.put("productName", product.getProductName());
					smsparam.put("amount", MoneyUtil.getMoneyStringDecimal2fen(dcacc.getAmount()));
					String content = new PropertiesUtil().getProperty("sms.online_opendiscountcard.success");
					content = StrUtil.replaceAll(content,smsparam);
					String mobileNum = transorder.getOrder().getMobileNum();
					smsSender.send(mobileNum, content, app.getAppId(),SmsSendService.ACTION_NAME_OPENCARD);
//					SmsSendUtil.delaySmsSend(content, null, user.getMobilenum());
				} catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
					log.error("发送开卡成功的短信通知失败", e);
				} 
				//添加开卡结果通知
				OpenCardEvent oce = new OpenCardEvent(result,product,commproduct,channelkey,transorder.getOrder().getChannelNo());
				EventListenerContainer.getInstance().fireEvent(oce);
				reporter.addSuccess();
				
			}catch (Exception e) {
				log.error(String.format("折扣卡开卡%s失败",order.getMobileNum()),e);
				Map errmsgmap = new HashMap();
				errmsgmap.put("mobileNum", order.getMobileNum());
				errmsgmap.put("msg", e.getMessage());
				errmsgmap.put("channelNo", order.getChannelOrderId());
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
	public DiscountCardAccount createDiscountCardAccount(String channleNo,
			BatchOpenOnlineListVO order, DiscountCardProduct product, Product commonproduct)
			throws DataInvalidException, MaAccountException {
		DiscountCardAccount account = new DiscountCardAccount();
		if(commonproduct==null){
			
			commonproduct= productDao.selectByPrimaryKey(product.getProductId());
		}
		User user = userSrv.getUserByMobile(order.getMobileNum());
		if(user==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户[手机号%s]不存在",order.getMobileNum()));
			}
			throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
		}
		String accountid = accountIdSrv.prepareGetAccountId(order.getProductId());
		account.setAccountId(accountid);
		account.setRemark(order.getRemark());
		account.setUserId(user.getUserId());
		account.setProductId(order.getProductId());
		account.setRestAmount(0L);//account.setRestAmount(commonproduct.getProductPrice()-product.getReturnSum());
		account.setAmount(commonproduct.getProductAmount());
		account.setRestStages(0);//account.setRestStages(product.getTotalStages()-1);
		account.setBalance(commonproduct.getProductAmount());
    	Date insertTime = new Date();
    	insertTime=(DateUtils.truncate(insertTime, Calendar.SECOND));
    	
		account.setInsertTime(insertTime);
		account.setUpdateTime(insertTime);
		account.setChannelNo(channleNo);
		//线上开卡现在就可以用
		Date startdate = insertTime;
		Date dayStart = DateUtil.toDayStart(startdate);
		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
		account.setStartTime(startdate);
		account.setEndTime(dayend);
		account.setStatus(Account.STATUS_OK);
		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}

}
