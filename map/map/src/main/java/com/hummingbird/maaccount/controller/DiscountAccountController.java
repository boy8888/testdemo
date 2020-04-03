package com.hummingbird.maaccount.controller;



import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.DESUtil;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.MoneyUtil;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SmsSenderUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.util.TripleDESUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.commonbiz.service.IAuthenticationService;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.DiscountCardAccountOrder;
import com.hummingbird.maaccount.entity.DiscountCardProduct;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.SmsCode;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.DiscountCardProductMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.UserSmsCodeMapper;
import com.hummingbird.maaccount.service.DiscountCardAccountService;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.util.PospEncyptUtil;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.DiscountCardOrderVO;
import com.hummingbird.maaccount.vo.DiscountCardResultVO;
import com.hummingbird.maaccount.vo.QueryOpenResultVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;
import com.hummingbird.maaccount.vo.YouMeDiscountCardOrderVO;

/**
 * 折扣帐户相关controller
 * 
 * @author Administrator
 */
@Controller
@RequestMapping("/discountCard")
public class DiscountAccountController extends AccountBaseController {

	@Autowired
	DiscountCardProductMapper dcproDao;
	@Autowired
	DiscountCardAccountService disSer;
	@Autowired
	ProductMapper productDao;
	@Autowired
	UserService userSrv;
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	@Autowired(required = true)
	private UserSmsCodeMapper smscodemapper;
	@Autowired(required = true)
	private IAuthenticationService authService;
	@Autowired(required = true)
	private SmsSendService smsSender;

	/**
	 * 线上开卡
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/open_online")
	@AccessRequered(methodName="线上开卡")
	public @ResponseBody Object openDiscountOnline(HttpServletRequest request) {
		
		TransOrderVO2<DiscountCardOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,DiscountCardOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_OPEN_CARD);
		String messagebase = "开卡";
		int baseerrcode=25900;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		String accountId =null;
		try {
			logWithBegin(transorder,request);
			transorder.selfvalidate();
			//获取url以作为method的内容
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			transorder.setMethod(requestURI);
			
			
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			boolean need3des = "true".equals(new PropertiesUtil().getProperty("openonline.tripledes"));
			if(need3des){
				//对姓名，身份证和手机进行解密
				decrypt(transorder,(AppInfo)validate.getValidateObj());
			}
			
			ValidateUtil.validateMobile(transorder.getOrder().getMobileNum());
			//检查手机号码是否已经注册过，如果未注册，则注册该用户，并用新用户交易密码短息模板下行短信；
			User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
			//必填判断
			//验证短信验证码
			if(StringUtils.isNotBlank(transorder.getOrder().getSmsCode())){
				if(!validateAuthCode(transorder.getApp().getAppId(),transorder.getOrder().getMobileNum(),transorder.getOrder().getSmsCode())){
					throw ValidateException.ERROR_MATCH_SMSCODE;
				}
			}
			//根据productId查询卡类型
			String productId = transorder.getOrder().getProductId();
			DiscountCardProduct product = dcproDao.selectByPrimaryKey(productId);
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
					log.debug(String.format("手机号码%s并没有注册，这里为其创建用户",transorder.getOrder().getMobileNum()));
				}
				user = new User();
				user.setInsertTime(new Date());
				DiscountCardOrderVO disCardOrderVO = transorder.getOrder();
				user.setName(disCardOrderVO.getName());
				user.setId(disCardOrderVO.getID());
				String mobileNum = transorder.getOrder().getMobileNum();
				user.setMobilenum(mobileNum);
//				user.setPassword(Md5Util.Encrypt(mobileNum.substring(mobileNum.length()-6)));
				//随机支付密码
				String paymentcode = StrUtil.genRandomCode(6);
				//user.setPaymentcodemd5(Md5Util.Encrypt(paymentcode));
				user.setPaymentCodeDES(Md5Util.Encrypt(paymentcode));
				
				userSrv.createUser(user,transorder.getApp().getAppId());
				//下发通知信息
				String content = new PropertiesUtil().getProperty("sms.newuser");
				content = StrUtil.replaceAllWithToken(content, "paymentCode", paymentcode);
				// 调用webservice 发送模板
				if (log.isDebugEnabled()) {
					log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
				}
				
				try {
					smsSender.send(mobileNum,content, transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_OPENCARD);
//					SmsSenderUtil.sendSms(mobileNum, content);
					if (log.isDebugEnabled()) {
						log.debug("向用户发送新用户创建成功的短信通知完成");
					}
					
				} catch (Exception e) {
					log.error("向用户发送新用户创建成功的短信通知出错:", e);
					rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
				}
				
			}
			//创建折扣卡
			DiscountCardAccount dcacc = createDiscountCardAccount(transorder,product);
			accountId = dcacc.getAccountId();
			Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(dcacc, "折扣卡帐户");
			ValidateUtil.assertNull(sourceacc, "应用帐户");
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processOpenDiscountCard(transorder, sourceacc, dcacc, product);
			rm.put("orderId", processOrder.getOrderNo());
			//返回折扣卡信息
			DiscountCardResultVO dccardResultVO = new DiscountCardResultVO(dcacc, product);
			rm.put("card",dccardResultVO );
			//发送开卡成功的短信通知
			Map smsparam = BeanUtils.describe(dccardResultVO);
			smsparam.put("productName", product.getProductName());
			smsparam.put("amount", MoneyUtil.getMoneyStringDecimal2fen(dcacc.getAmount()));
			String content = new PropertiesUtil().getProperty("sms.online_opendiscountcard.success");
			content = StrUtil.replaceAll(content,smsparam);
			String mobileNum = transorder.getOrder().getMobileNum();
			// 调用webservice 发送模板
			if (log.isDebugEnabled()) {
				log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
			}
			
			try {
				smsSender.send(mobileNum,content, transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_OPENCARD);
//				SmsSenderUtil.sendSms(mobileNum, content);
				if (log.isDebugEnabled()) {
					log.debug("向用户发送新用户创建成功的短信通知完成");
				}
				
			} catch (Exception e) {
				log.error("向用户发送新用户创建成功的短信通知出错:", e);
				rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
			}
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
//			if(accountId!=null){
//				try {
//					accountIdSrv.returnAccountId(accountId);
//				} catch (MaAccountException e) {
//					log.error(String.format("帐号归还失败"),e);
//				}
//				
//			}
		}
		finally{
			//记录日志
			post(rm);
		}
		
		return rm;
		
	}
	

	/**
	 * 查询线上开卡结果
	 * @return
	 */
	@RequestMapping("/get")
	@AccessRequered(methodName="查询线上开卡结果")
	public @ResponseBody Object queryOnlineOpenResult(HttpServletRequest request) {
		QueryOpenResultVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, QueryOpenResultVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		String messagebase = "查询开折扣卡结果";
		rm.setBaseErrorCode(26000);
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder.getApp().getAppId(), request);
			ValidateUtil.assertNull(transorder.getAppOrderId(), "应用订单号");
			DiscountCardAccountService oaSrv = SpringBeanUtil.getInstance().getBean(DiscountCardAccountService.class);
			DiscountCardAccountOrder order = oaSrv.queryDiscountCardAccountByApporderId(transorder.getApp().getAppId(), transorder.getAppOrderId());
			rm.put("orderId", order.getOrderId());
			DiscountCardAccount account = order.getAccount();
			rm.put("card", new DiscountCardResultVO(account));
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
			post(rm);
		}
		
		return rm;
		
	}
	
	/**
	 * @param transorder
	 * @param appInfo 
	 */
	private void decrypt(TransOrderVO2<DiscountCardOrderVO> transorder, AppInfo appInfo) {
		DiscountCardOrderVO cardopenvo = transorder.getOrder();
		cardopenvo.setID(TripleDESUtil.decryptBased3Des(cardopenvo.getID(), appInfo.getAppKey()));
		cardopenvo.setName(TripleDESUtil.decryptBased3Des(cardopenvo.getName(), appInfo.getAppKey()));
		cardopenvo.setMobileNum(TripleDESUtil.decryptBased3Des(cardopenvo.getMobileNum(), appInfo.getAppKey()));
		cardopenvo.setRemark(TripleDESUtil.decryptBased3Des(cardopenvo.getRemark(), appInfo.getAppKey()));
		
	}
	private void decrypt(BatchOpenOnlineDetailVO batchOpenOrder, AppInfo appInfo) {
		for(BatchOpenOnlineListVO order:batchOpenOrder.getOrders()){
			order.setID(TripleDESUtil.decryptBased3Des(order.getID(), appInfo.getAppKey()));
			order.setName(TripleDESUtil.decryptBased3Des(order.getName(), appInfo.getAppKey()));
			order.setMobileNum(TripleDESUtil.decryptBased3Des(order.getMobileNum(), appInfo.getAppKey()));
			order.setRemark(TripleDESUtil.decryptBased3Des(order.getRemark(), appInfo.getAppKey()));
		}
	}
	/**
	 * @param transorder
	 * @param product 
	 * @return
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	private DiscountCardAccount createDiscountCardAccount(
			TransOrderVO2<? extends DiscountCardOrderVO> transorder, DiscountCardProduct product) throws DataInvalidException, MaAccountException {
		DiscountCardOrderVO order = transorder.getOrder();
		DiscountCardAccount account = new DiscountCardAccount();
		Product commonproduct= productDao.selectByPrimaryKey(product.getProductId());
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
		//由于帐户签名会因为日期中的毫秒数造成签名不一致,所以进行处理,去毫秒去掉
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		Date starttime = cal.getTime();
		account.setInsertTime(starttime);
		account.setUpdateTime(starttime);
		account.setChannelNo(order.getChannelNo());
		//线上开卡现在就可以用
//		Date startdate = new Date();
//		Date dayStart = DateUtil.toDayStart(startdate);
//		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
		account.setStartTime(starttime);
//		account.setEndTime(dayend);
		account.setStatus(Account.STATUS_OK);
		//如果是油我发起的卡,需要再调整帐户信息
		if (order instanceof YouMeDiscountCardOrderVO) {
			YouMeDiscountCardOrderVO youmecardorder = (YouMeDiscountCardOrderVO) order;
			account.setBalance(youmecardorder.getAmount());
			account.setRestAmount(youmecardorder.getAmount());
			account.setAmount(youmecardorder.getAmount());
			if(youmecardorder.getStartTimeDate()!=null){
				account.setStartTime(youmecardorder.getStartTimeDate());
			}
			if(youmecardorder.getEndTimeDate()!=null){
				account.setEndTime(youmecardorder.getEndTimeDate());
			}
			else{
				Date dayend = DateUtils.add(starttime, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
				account.setEndTime(dayend);
			}
			account.setChannelNo(order.getAppOrderId());
		}
		//其它 的折扣卡暂不知道结束 时间是如何
		
		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.controller.AccountBaseController#getAccountType()
	 */
	@Override
	protected String getAccountType() {
		return Account.ACCOUNT_DISCOUNT_CARD;
	}
	
	/**
	 * 校验手机验证码
	 * @param appId
	 * @param mobileNum
	 * @param smsCode
	 * @return
	 */
	private boolean validateAuthCode(String appId, String mobileNum,
			String authCode) {
		SmsCode query = new SmsCode();
		query.setAppId(appId);
		query.setMobilenum(mobileNum);
		SmsCode code = smscodemapper.getAuthCode(query);
		if (log.isTraceEnabled()) {
			log.trace("手机验证码信息是：" + code);
			
		}
		if (code != null
				&& code.getSmscode().equals(authCode)
				&& (code.getSendTime().getTime() + code.getExpirein() * 1000) > System
						.currentTimeMillis()) {
			// 删除验证码
			smscodemapper.deleteAuthCode(query);
			return true;
		}
		return false;
	}
	
	/**
	 * 线上批量开卡
	 * @author liudou
	 * @date 2015-7-28
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/batch_open_online")
	@AccessRequered(methodName="折扣卡线上批量开卡")
	public @ResponseBody Object batchOpenOnline(HttpServletRequest request) {
		/*{
		    "app":
		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
		    "order":
		    {
		           "channelNo":"channel001",
		            "appOrderId":"123456789", 
		            orders:[{
		            "ID":"515411244445444444X", 
		            "name":"李四", 
		            "mobileNum":"13912345678", 
		            "channelOrderId":"channel1234567890",
		            "productId":"10",
		            "remark":"某某渠道为用户13912345678开卡"}]
		    },
		    "tsig":
		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
		}  */
		TransOrderVO2<BatchOpenOnlineDetailVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,BatchOpenOnlineDetailVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_OPEN_CARD);
		String messagebase = "折扣卡线上批量开卡";
		int baseerrcode=28100;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		String accountId =null;
		try {
			logWithBegin(transorder,request);
			//transorder.selfvalidate();
			//获取url以作为method的内容
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			transorder.setMethod(requestURI);
			
			
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			
			rm.setErr(0, "接收成功");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					disSer.createUserBatch(transorder, (AppInfo)validate.getValidateObj());
					
				}
			}).start();
			
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
			post(rm);
		}
		
		return rm;
		
	}
	
	
	/**
	 * 线上开通油我发起的加油卡
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/openYouMeCardOnline")
	@AccessRequered(methodName="线上开油我发起的加油卡")
	public @ResponseBody Object openYoumeDiscountOnline(HttpServletRequest request) {
		
		TransOrderVO2<YouMeDiscountCardOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			if (log.isDebugEnabled()) {
				log.debug(String.format("线上开油我发起的加油卡:%s",jsonstr));
			}
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,YouMeDiscountCardOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_OPEN_CARD);
		String messagebase = "开油我发起加油卡";
		int baseerrcode=25900;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		String accountId =null;
		try {
			logWithBegin(transorder,request);
			transorder.selfvalidate();
			//获取url以作为method的内容
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			transorder.setMethod(requestURI);
			
			
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			AppInfo appinfo = (AppInfo)validate.getValidateObj();
			YouMeDiscountCardOrderVO order = transorder.getOrder();
			//进行des解密
			order.setID(DESUtil.decodeDESwithCBC(order.getID(), appinfo.getAppKey()));
			order.setName(DESUtil.decodeDESwithCBC(order.getName(), appinfo.getAppKey()));
			order.setMobileNum(DESUtil.decodeDESwithCBC(order.getMobileNum(), appinfo.getAppKey()));

			//如果channelId为空,则使用apporderid
			if(StringUtils.isBlank(order.getChannelOrderId())){
				order.setChannelOrderId(order.getAppOrderId());
			}
			
			ValidateUtil.validateMobile(transorder.getOrder().getMobileNum());
			//检查手机号码是否已经注册过，如果未注册，则注册该用户，并用新用户交易密码短息模板下行短信；
			User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
			//必填判断
			//验证短信验证码
			if(StringUtils.isNotBlank(transorder.getOrder().getSmsCode())){
				if(!validateAuthCode(transorder.getApp().getAppId(),transorder.getOrder().getMobileNum(),transorder.getOrder().getSmsCode())){
					throw ValidateException.ERROR_MATCH_SMSCODE;
				}
			}
			//根据productId查询卡类型
			String productId = transorder.getOrder().getProductId();
			DiscountCardProduct product = dcproDao.selectByPrimaryKey(productId);
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
					log.debug(String.format("手机号码%s并没有注册，这里为其创建用户",transorder.getOrder().getMobileNum()));
				}
				user = new User();
				user.setInsertTime(new Date());
				YouMeDiscountCardOrderVO disCardOrderVO = transorder.getOrder();
				user.setName(disCardOrderVO.getName());
				user.setId(disCardOrderVO.getID());
				String mobileNum = transorder.getOrder().getMobileNum();
				user.setMobilenum(mobileNum);
//				user.setPassword(Md5Util.Encrypt(mobileNum.substring(mobileNum.length()-6)));
				//随机支付密码
				String paymentcode = StrUtil.genRandomCode(6);
				//user.setPaymentcodemd5(Md5Util.Encrypt(paymentcode));
				user.setPaymentCodeDES(Md5Util.Encrypt(paymentcode));
				
				userSrv.createUser(user,transorder.getApp().getAppId());
				//下发通知信息
				String content = new PropertiesUtil().getProperty("sms.newuser");
				content = StrUtil.replaceAllWithToken(content, "paymentCode", paymentcode);
				// 调用webservice 发送模板
				if (log.isDebugEnabled()) {
					log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
				}
				
				try {
					SmsSenderUtil.sendSms(mobileNum, content);
					if (log.isDebugEnabled()) {
						log.debug("向用户发送新用户创建成功的短信通知完成");
					}
					
				} catch (Exception e) {
					log.error("向用户发送新用户创建成功的短信通知出错:", e);
					rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
				}
				
			}
			//创建折扣卡
			DiscountCardAccount dcacc = createDiscountCardAccount(transorder,product);
			//调整帐户的
			accountId = dcacc.getAccountId();
			if(StringUtils.isBlank(accountId)){
				log.error("油我发起卡号获取失败");
				throw ValidateException.ERROR_PARAM_NULL.clone(null, "油我发起卡号获取失败");
			}
			Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(dcacc, "折扣卡帐户");
			ValidateUtil.assertNull(sourceacc, "应用帐户");
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processOpenDiscountCardAndSetting(transorder, sourceacc, dcacc, product);
			rm.put("orderId", processOrder.getOrderNo());
			//返回折扣卡信息
			DiscountCardResultVO dccardResultVO = new DiscountCardResultVO(dcacc, product);
			rm.put("card",dccardResultVO );
			//发送开卡成功的短信通知
			Map smsparam = BeanUtils.describe(dccardResultVO);
			smsparam.put("productName", product.getProductName());
			smsparam.put("amount", MoneyUtil.getMoneyStringDecimal2fen(dcacc.getAmount()));
			String content = new PropertiesUtil().getProperty("sms.online_opendiscountcard.success");
			content = StrUtil.replaceAll(content,smsparam);
			String mobileNum = transorder.getOrder().getMobileNum();
			// 调用webservice 发送模板
			if (log.isDebugEnabled()) {
				log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
			}
			
			try {
				SmsSenderUtil.sendSms(mobileNum, content);
				if (log.isDebugEnabled()) {
					log.debug("向用户发送新用户创建成功的短信通知完成");
				}
				
			} catch (Exception e) {
				log.error("向用户发送新用户创建成功的短信通知出错:", e);
				rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
			}
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
//			if(accountId!=null){
//				try {
//					accountIdSrv.returnAccountId(accountId);
//				} catch (MaAccountException e) {
//					log.error(String.format("帐号归还失败"),e);
//				}
//				
//			}
		}
		finally{
			//记录日志
			post(rm);
		}
		
		return rm;
		
	}
	
	
	
}
