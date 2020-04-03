package com.hummingbird.maaccount.controller;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SmsSenderUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.util.TripleDESUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.SmsCode;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccountOrder;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.UserSmsCodeMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountMapper;
import com.hummingbird.maaccount.mapper.VolumecardAccountProductMapper;
import com.hummingbird.maaccount.service.DiscountCardAccountService;
import com.hummingbird.maaccount.service.VolumecardAccountService;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.util.PospEncyptUtil;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.QueryOilcardOpenResultVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;
import com.hummingbird.maaccount.vo.VolumecardOrderVO;
import com.hummingbird.maaccount.vo.VolumecardResultVO;

/**
 * 油量卡帐户相关controller
 * 
 * @author  2015年3月25日 上午11:26:07
 */
@Controller
@RequestMapping("/volumecard")
public class VolumecardAccountController extends AccountBaseController{
	@Autowired(required = true)
	private SmsSendService smsSender;
	@Autowired
	VolumecardAccountProductMapper ocproDao;
	@Autowired
	ProductMapper productDao;
	@Autowired
	UserService userSrv;
	@Autowired
	VolumecardAccountService volSer;
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	@Autowired(required = true)
	private UserSmsCodeMapper smscodemapper;
	@Autowired(required = true)
	VolumecardAccountMapper vaDao;
	/**
	 * 线上开卡
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/open_online")
	@AccessRequered(methodName="线上开卡")
	public @ResponseBody Object openVolumecarOnline(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		
		
		TransOrderVO2<VolumecardOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,VolumecardOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_OPEN_CARD);
		String messagebase = "开卡";
		int baseerrcode=26100;//27000
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
			//VolumecardAccountProduct product = ocproDao.selectByPrimaryKey(productId);
			VolumecardAccountProduct product = ocproDao.selectByPrimaryKey(productId);
			if(product==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据类型%s查询容量卡，结果为空",productId));
				}
				throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS.clone(null, "找不到对应的产品");
			}
			if(!"OK#".equals(product.getStatus()))
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("容量卡%s已下线",productId));
				}
				throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "容量卡产品已下线");
			}
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册，这里为其创建用户",transorder.getOrder().getMobileNum()));
				}
				user = new User();
				user.setInsertTime(new Date());
				VolumecardOrderVO volumecardOrderVO = transorder.getOrder();
				user.setName(volumecardOrderVO.getName());
				user.setId(volumecardOrderVO.getID());
				String mobileNum = transorder.getOrder().getMobileNum();
				user.setMobilenum(mobileNum);
				user.setPassword(Md5Util.Encrypt(mobileNum.substring(mobileNum.length()-6)));
				//随机支付密码
				String paymentcode = StrUtil.genRandomCode(6);
//				user.setPaymentcodemd5(Md5Util.Encrypt(paymentcode));
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
					smsSender.send(mobileNum,content, transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_REGISTER);
//					SmsSenderUtil.sendSms(mobileNum, content);
					if (log.isDebugEnabled()) {
						log.debug("向用户发送新用户创建成功的短信通知完成");
					}
					
				} catch (Exception e) {
					log.error("向用户发送新用户创建成功的短信通知出错:", e);
					rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
				}
				
			}
			//创建加油卡
			
			
			VolumecardAccount descacc=createVolumecardAccount(transorder,product);
			accountId = descacc.getAccountId();
//			OilcardAccount descacc=(OilcardAccount) AccountFactory.getAccount(Account.ACCOUNT_OIL_CARD,transorder.getOrder().getMobileNum());
			Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "容量卡帐户");
			ValidateUtil.assertNull(sourceacc, "应用帐户");
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			
			
			Receipt processOrder = orderSrv.processOpenVolumecard(transorder,sourceacc,descacc,product);
			rm.put("orderId", processOrder.getOrderNo());
			//返回容量卡信息
			VolumecardResultVO volumecardResultVO = new VolumecardResultVO(descacc, product);
			rm.put("card",volumecardResultVO );
			//发送开卡成功的短信通知
			Map smsparam = BeanUtils.describe(volumecardResultVO);
			smsparam.put("productName", product.getProductName());
			smsparam.put("amount", new DecimalFormat("0.00").format((double)(descacc.getAmount()/1000)));
//			smsparam.put("returnSum", MoneyUtil.getMoneyStringDecimal2(product.getReturnSum()));
			String content = new PropertiesUtil().getProperty("sms.online_openvolumecard.success");
			content = StrUtil.replaceAll(content,smsparam);
			String mobileNum = transorder.getOrder().getMobileNum();
			// 调用webservice 发送模板
			if (log.isDebugEnabled()) {
				log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
			}
			
			try {
				smsSender.send(mobileNum,content, transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_REGISTER);
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
	 * 查询开容量卡结果
	 * @return
	 */
	@RequestMapping("/get")
	@AccessRequered(methodName="查询开容量卡结果")
	public @ResponseBody Object queryVolumecardOpenResult(HttpServletRequest request) {
 
		QueryOilcardOpenResultVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, QueryOilcardOpenResultVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		String messagebase = "查询开容量卡结果";
		rm.setBaseErrorCode(26200);//
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder.getApp().getAppId(), request);
			ValidateUtil.assertNull(transorder.getAppOrderId(), "应用订单号");
			VolumecardAccountService vaSrv = SpringBeanUtil.getInstance().getBean(VolumecardAccountService.class);
			VolumecardAccountOrder volumecardAccountOrder = vaSrv.queryVolumecardAccountByapporderId(transorder.getApp().getAppId(),transorder.getAppOrderId());
			rm.put("orderId", volumecardAccountOrder.getOrderId());
			String accountId = volumecardAccountOrder.getAccountId();
			VolumecardAccount account = vaDao.selectByPrimaryKey(accountId);
			rm.put("card", new VolumecardResultVO(account));
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
	private void decrypt(TransOrderVO2<VolumecardOrderVO> transorder, AppInfo appInfo) {
		VolumecardOrderVO cardopenvo = transorder.getOrder();
		cardopenvo.setID(TripleDESUtil.decryptBased3Des(cardopenvo.getID(), appInfo.getAppKey()));
		cardopenvo.setName(TripleDESUtil.decryptBased3Des(cardopenvo.getName(), appInfo.getAppKey()));
		cardopenvo.setMobileNum(TripleDESUtil.decryptBased3Des(cardopenvo.getMobileNum(), appInfo.getAppKey()));
		cardopenvo.setRemark(TripleDESUtil.decryptBased3Des(cardopenvo.getRemark(), appInfo.getAppKey()));
		
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
	 * @param transorder
	 * @param product 
	 * @return
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	private VolumecardAccount createVolumecardAccount(
			TransOrderVO2<VolumecardOrderVO> transorder, VolumecardAccountProduct product) throws DataInvalidException, MaAccountException {
		VolumecardOrderVO order = transorder.getOrder();
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
		//由于帐户签名会因为日期中的毫秒数造成签名不一致,所以进行处理,去毫秒去掉
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		Date starttime = cal.getTime();
		account.setInsertTime(starttime);
		account.setUpdateTime(starttime);
		account.setChannelNo(order.getChannelNo());
		//线上开卡现在就可以用
		Date startdate = starttime;
		Date dayStart = DateUtil.toDayStart(startdate);
		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
		account.setStartTime(dayStart);
		account.setEndTime(dayend);
		account.setStatus(VolumecardAccount.STATUS_OK);
		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.controller.AccountBaseController#getAccountType()
	 */
	@Override
	protected String getAccountType() {
		return Account.ACCOUNT_VOLUME_CARD;
	}

	/**
	 * 线上批量开卡
	 * @author liudou
	 * @date 2015-7-28
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/batch_open_online")
	@AccessRequered(methodName="容量卡线上批量开卡")
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
		String messagebase = "容量卡线上批量开卡";
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
					volSer.createUserBatch(transorder, (AppInfo)validate.getValidateObj());
					
				}
			}).start();
			
//			rm.putAll(volSer.createUserBatch(transorder, (AppInfo)validate.getValidateObj()));
			
			
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
}
