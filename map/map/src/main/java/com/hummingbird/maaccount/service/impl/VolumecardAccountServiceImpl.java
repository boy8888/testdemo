package com.hummingbird.maaccount.service.impl;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.DiscountCardAccountOrder;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.entity.VolumecardProduct;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
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

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.util.TripleDESUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.util.NoGenerationUtil;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccountOrder;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.DefaultAccountDao;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountProductMapper;
import com.hummingbird.maaccount.service.OrderService;
import com.hummingbird.maaccount.service.VolumecardAccountService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.util.BatchProcessReporter;
import com.hummingbird.maaccount.util.IndexObject;
import com.hummingbird.maaccount.util.PospEncyptUtil;
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
import com.hummingbird.common.event.EventListenerContainer;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ToolsException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.SmsSenderUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.util.TripleDESUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.util.NoGenerationUtil;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccountOrder;
import com.hummingbird.maaccount.event.OpenCardEvent;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.DefaultAccountDao;
import com.hummingbird.maaccount.mapper.OilcardAccountMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountProductMapper;
import com.hummingbird.maaccount.service.VolumecardAccountService;

@Service("volumecardAccountServiceImpl")

public class VolumecardAccountServiceImpl extends DefaultAccountService implements VolumecardAccountService{
	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	@Autowired
	UserService userSrv;
	@Autowired
	VolumecardAccountMapper vaDao;
	@Autowired
	protected UserMapper userdao;
	@Autowired
	VolumecardAccountProductMapper vcpDao;
	@Autowired(required = true)
	protected OrderService orderSrv;
	@Autowired
	ProductMapper productDao;
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	/**
	 * 现金订单
	 */
	@Autowired
	VolumecardAccountOrderMapper vaorderDao;
	
	/**
	 * 根据第三方应用id查询开卡情况
	 * @param appId
	 * @param appOrderId
	 * @return 
	 * @throws MaAccountException 
	 */
	public VolumecardAccountOrder queryVolumecardAccountByapporderId(
			String appId,String appOrderId) throws MaAccountException {
		VolumecardAccountOrder order = vaorderDao.selectByChannelOrderId(appId,appOrderId);
		if(order==null)
		{
			throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"根据appOrderId["+appOrderId+"]获取不到订单");
		}
//		Account account = vaDao.getAccountByAccountId(order.getAccountId());
//		order.setAccount((VolumecardAccount) account);
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
	 * @param account
	 */
	
	public boolean updateAccount(Account account){
		int updatesuccess = getAccountDao().updateAccount(account);
		return 1==updatesuccess;
	}

	@Override
	public Account getAccountByAccountId(String accountId)
			throws MaAccountException {
		return vaDao.getAccountByAccountId(accountId);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public Receipt antiPayOffine(
			TransOrderVO2<AntiPayoffline> transorder, Account sourceacc,
			VolumecardAccount vaccount) throws MaAccountException {
		AntiPayoffline io = transorder.getOrder();
		String orderId = io.getOrderId();//原来的orderid
		VolumecardAccountOrder disorder = vaorderDao.selectByPrimaryKey(orderId);
		AppVO app = transorder.getApp();
		String orderNo = NoGenerationUtil.genOrderNo();
		long sum = Math.abs(disorder.getSum());//原订单是支出订单，是负值
		vaccount.setBalance(vaccount.getBalance()+sum);
		
		//收入订单
		VolumecardAccountOrder inorder = new VolumecardAccountOrder();
		
		inorder.setAppId(app.getAppId());
		inorder.setAppname(app.getAppname());
		inorder.setAccountId(vaccount.getAccountId());
		inorder.setInsertTime(new Date());
		inorder.setUpdateTime(new Date());
		inorder.setOrderId(orderNo);
		inorder.setOriginalorderId(orderId);
		inorder.setOriginaltable(OrderConst.ORDER_TABLE_VOLUME_CARD);
		inorder.setMethod(transorder.getMethod());
//	oaOrder.setProductName(product.getProductName());
		inorder.setSum(sum);
		inorder.setStatus("OK#");
		inorder.setBalance(vaccount.getBalance());
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
		
		AccountValidateUtil.updateAccountSignature(vaccount);
		boolean updateAccountsucc = updateAccount(vaccount);
		if(!updateAccountsucc){
			log.error(String.format("帐户[%s]更新不成功",vaccount));
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"更新帐户失败");
		}
		int createOrdersucc = vaorderDao.createOrder(inorder);
		if(createOrdersucc!=1){
			log.error(String.format("订单[%s]创建不成功",inorder));
			throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"更新订单失败");

		}
		DefaultReceipt dr = new DefaultReceipt();
		dr.setDealtime(inorder.getInsertTime());
		dr.setOrderNo(orderNo);
		dr.addInAccount(vaccount);
		dr.addExt("inorder", inorder);
		return dr;
	}
	
	@Override
	public DefaultAccountDao getAccountDao() {
		return vaDao;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public ResultModel createUserBatch(
			TransOrderVO2<BatchOpenOnlineDetailVO> transorder, AppInfo app) {
		
		if (log.isDebugEnabled()) {
			log.debug(String.format("容量卡批量开卡开始"));
		}
		String accountId =null;
		ResultModel rm = new ResultModel();
		List<BatchOpenOnlineListVO> orders=transorder.getOrder().getOrders();
		String channelkey = TripleDESUtil.decryptBased3Des(transorder.getOrder().getChannelKey(),app.getAppKey());

		BatchProcessReporter reporter = new BatchProcessReporter();
		List<BatchOpenOnlineResultVO> resultList=new ArrayList<BatchOpenOnlineResultVO>();
		Map<String, VolumecardAccountProduct> productmap = new HashMap<String, VolumecardAccountProduct>();
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
				VolumecardAccountOrder selectByChannelOrderId = vaorderDao.selectByChannelOrderId(transorder.getApp().getAppId(),order.getChannelOrderId());
				if(selectByChannelOrderId!=null){
					if (log.isDebugEnabled()) {
						log.debug(String.format("渠道订单号%s已存在，即已进行过开卡，不处理",order.getChannelOrderId()));
					}
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"开卡请求已处理，不重复开卡");
				}
				//根据productId查询卡类型
				String productId = order.getProductId();
				VolumecardAccountProduct product = productmap.computeIfAbsent(productId, new Function<String, VolumecardAccountProduct>() {
					@Override
					public VolumecardAccountProduct apply(String productId) {
						return vcpDao.selectByPrimaryKey(productId);
					}
				});
				Product commproduct = commproductmap.computeIfAbsent(productId, new Function<String, Product>() {

					@Override
					public Product apply(String t) {
						return productDao.selectByPrimaryKey(productId);
					}
				});
//				VolumecardAccountProduct product = vcpDao.selectByPrimaryKey(productId);
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
				VolumecardAccount dcacc = createVolumecardAccount(transorder.getOrder().getChannelNo(),order,product);
				accountId = dcacc.getAccountId();
				Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,order.getMobileNum());
				ValidateUtil.assertNull(dcacc, "容量卡帐户");
				ValidateUtil.assertNull(sourceacc, "应用帐户");
				if(log.isDebugEnabled()){
					log.debug("检验通过，获取请求");
				}
				Receipt processOrder = orderSrv.processBathOpenVolumecard(transorder,order, sourceacc, dcacc, product);
				
				//返回折扣卡信息
				resultCard.setBalance(dcacc.getBalance());
				resultCard.setAccountId(dcacc.getAccountId());
				resultCard.setAmount(dcacc.getAmount());
				resultCard.setEndTime(dcacc.getEndTime().toString());
				resultCard.setStartTime(dcacc.getStartTime().toString());
				resultCard.setStatus(dcacc.getStatus());
				result.setCard(resultCard);
				result.setMobile(order.getMobileNum());
				result.setOrderId(processOrder.getOrderNo());
				result.setChannelOrderId(order.getChannelOrderId());
				resultList.add(result);
				
				
				//发送开卡成功的短信通知
				Map smsparam;
				try {
					smsparam = BeanUtils.describe(dcacc);
					smsparam.put("productName", product.getProductName());
					smsparam.put("amount", new DecimalFormat("0.00").format((double)(dcacc.getAmount()/1000)));
//					smsparam.put("returnSum", MoneyUtil.getMoneyStringDecimal2(product.getReturnSum()));
					String content = new PropertiesUtil().getProperty("sms.online_openvolumecard.success");
					content = StrUtil.replaceAll(content,smsparam);
					String mobileNum = transorder.getOrder().getMobileNum();
					SmsSendUtil.delaySmsSend(content, null, user.getMobilenum());
				} catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
					log.error("发送开卡成功的短信通知失败", e);
				} 
				//添加开卡结果通知
				OpenCardEvent oce = new OpenCardEvent(result,product,commproduct,channelkey,transorder.getOrder().getChannelNo());
				EventListenerContainer.getInstance().fireEvent(oce);
				
			}catch (DataInvalidException | MaAccountException e) {
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
	public VolumecardAccount createVolumecardAccount(String channleNo,
			BatchOpenOnlineListVO order, VolumecardAccountProduct product)
			throws DataInvalidException, MaAccountException {
		VolumecardAccount account = new VolumecardAccount();
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
//		account.setRestAmount(commonproduct.getProductPrice()-product.getReturnSum());
		account.setAmount(commonproduct.getProductAmount());
//		account.setRestStages(product.getTotalStages()-1);
		account.setBalance(commonproduct.getProductAmount());
		Date insertTime = new Date();
    	insertTime=(DateUtils.truncate(insertTime, Calendar.SECOND));
		account.setInsertTime(insertTime);
		account.setUpdateTime(insertTime);
		account.setChannelNo(channleNo);
		//线上开卡现在就可以用
		Date startdate =insertTime;
		Date dayStart = DateUtil.toDayStart(startdate);
		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
		account.setStartTime(dayStart);
		account.setEndTime(dayend);
		account.setStatus(VolumecardAccount.STATUS_OK);
		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}



}
