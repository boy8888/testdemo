package com.hummingbird.maaccount.controller;



import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.event.EventListenerContainer;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ToolsException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.MoneyUtil;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SignatureUtil;
import com.hummingbird.common.util.SmsSenderUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.util.TripleDESUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.maaccount.constant.AccountConst;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.entity.OilcardAccountOrder;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.OpenCardDeliveryFailEntity;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.SmsCode;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.event.OpenCardEvent;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.OilcardAccountOrderMapper;
import com.hummingbird.maaccount.mapper.OilcardAccountProductMapper;
import com.hummingbird.maaccount.mapper.OpenCardDeliveryFailEntityMapper;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.mapper.UserSmsCodeMapper;
import com.hummingbird.maaccount.service.OilcardAccountService;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.util.OilcardReturnUtil;
import com.hummingbird.maaccount.util.PospEncyptUtil;
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.IOrderVO;
import com.hummingbird.maaccount.vo.OfflineOpencardDeliveryOrderVO;
import com.hummingbird.maaccount.vo.OfflineOpencardOrderVO;
import com.hummingbird.maaccount.vo.OilcardOrderVO;
import com.hummingbird.maaccount.vo.OilcardResultVO;
import com.hummingbird.maaccount.vo.OilcardTrans2CashOrderVO;
import com.hummingbird.maaccount.vo.OpenCardFailDeliveryDetailVO;
import com.hummingbird.maaccount.vo.OpenCardFailDeliveryVO;
import com.hummingbird.maaccount.vo.QueryOilcardOpenResultVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;


/**
 * 分期卡帐户相关controller
 * 
 * @author huangjiej_2 2014年11月10日 下午11:42:07
 */
@Controller
@RequestMapping("/oilcard")
public class OilcardAccountController extends AccountBaseController {

	@Autowired(required = true)
	private SmsSendService smsSender;
//	@Autowired(required = true)
//	private IOrderPayService orderPayService;
//	@Autowired(required = true)
//	private AppInfoServiceImpl appService;
//	@Autowired(required = true)
//	private AccountMethodService authoritySrv;
////	@Autowired(required = true)
////	private IAuthenticationService authService;
	@Autowired(required = true)
	private OilcardAccountOrderMapper oilcardOrderDao;
	@Autowired
	OilcardAccountService oilcardSrv;
	@Autowired
	OilcardAccountProductMapper ocproDao;
	@Autowired
	ProductMapper productDao;
	@Autowired
	OpenCardDeliveryFailEntityMapper opencardDeliveryFailDao;
	@Autowired
	UserService userSrv;
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	@Autowired(required = true)
	private UserSmsCodeMapper smscodemapper;
	

	

	/**
	 * 线上开卡
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/open_online")
	@AccessRequered(methodName="线上开卡")
	public @ResponseBody Object openOilcarOnline(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		
		TransOrderVO2<OilcardOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,OilcardOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_OPEN_CARD);
		String messagebase = "开卡";

		int baseerrcode=24900;//27000
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		String accountId =null;
		AppInfo appinfo=null;
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
			appinfo = (AppInfo)validate.getValidateObj();
			if(need3des){
				//对姓名，身份证和手机进行解密
				decrypt(transorder,appinfo);
			}
			OilcardAccountOrder selectByChannelOrderId = oilcardOrderDao.selectByChannelOrderId(transorder.getApp().getAppId(), transorder.getOrder().getChannelOrderId());
			if(selectByChannelOrderId!=null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("渠道订单号%s已存在，即已进行过开卡，线上开卡失败",transorder.getOrder().getChannelOrderId()));
				}
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"开卡请求已处理，不能重复开卡");
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
			OilcardAccountProduct product = ocproDao.selectByPrimaryKey(productId);
			if(product==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据类型%s查询分期卡，结果为空",productId));
				}
				throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS.clone(null, "找不到对应的产品");
			}
			if(!"OK#".equals(product.getStatus()))
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("分期卡%s已下线",productId));
				}
				throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "分期卡产品已下线");
			}
			createUserIfNessary(transorder, rm, baseerrcode, user,transorder.getApp().getAppId());
			//创建加油卡
			Product commonproduct= productDao.selectByPrimaryKey(product.getProductId());
			OilcardAccount descacc=createOilcardAccountImme(transorder, product, commonproduct);
			accountId = descacc.getAccountId();
//			OilcardAccount descacc=(OilcardAccount) AccountFactory.getAccount(Account.ACCOUNT_OIL_CARD,transorder.getOrder().getMobileNum());
			Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "分期卡帐户");
			ValidateUtil.assertNull(sourceacc, "应用帐户");

			AccountValidateUtil.validateAccount(descacc);
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			
			

			Receipt processOrder = orderSrv.processOpenOilcard(transorder,sourceacc,descacc,product);
			rm.put("orderId", processOrder.getOrderNo());
			//返回分期卡信息
			OilcardResultVO oilcardResultVO = new OilcardResultVO(descacc, product);
			rm.put("card",oilcardResultVO );
			//发送开卡成功的短信通知
			Map smsparam = BeanUtils.describe(oilcardResultVO);
			smsparam.put("productName", product.getProductName());

			smsparam.put("amount", MoneyUtil.getMoneyStringDecimal2fen(descacc.getAmount()));
			smsparam.put("returnSum", MoneyUtil.getMoneyStringDecimal2fen(product.getReturnSum()));

			String content = new PropertiesUtil().getProperty("sms.online_openoilcard.success");
			content = StrUtil.replaceAll(content,smsparam);
			String mobileNum = transorder.getOrder().getMobileNum();
			sendbysms(rm, baseerrcode, mobileNum, content,transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_OPENCARD);
			//添加开卡结果通知
			OpenCardEvent oce = new OpenCardEvent(transorder,rm,product,commonproduct,appinfo.getAppKey(),transorder.getOrder().getChannelNo());
			EventListenerContainer.getInstance().fireEvent(oce);
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
//			}
			//添加开卡结果通知
			if(appinfo!=null){
				
				OpenCardEvent oce = new OpenCardEvent(transorder.getOrder().getMobileNum(),transorder.getOrder().getChannelOrderId(),transorder.getOrder().getChannelNo(),e1.getMessage(),appinfo.getAppKey());
				EventListenerContainer.getInstance().fireEvent(oce);
			}
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
	private void decrypt(TransOrderVO2<OilcardOrderVO> transorder, AppInfo appInfo) {
		OilcardOrderVO cardopenvo = transorder.getOrder();
		cardopenvo.setID(TripleDESUtil.decryptBased3Des(cardopenvo.getID(), appInfo.getAppKey()));
		cardopenvo.setName(TripleDESUtil.decryptBased3Des(cardopenvo.getName(), appInfo.getAppKey()));
		cardopenvo.setMobileNum(TripleDESUtil.decryptBased3Des(cardopenvo.getMobileNum(), appInfo.getAppKey()));
		cardopenvo.setRemark(TripleDESUtil.decryptBased3Des(cardopenvo.getRemark(), appInfo.getAppKey()));
	}
	/**
	 * 被另一个方法替代
	 * @deprecated
	 * @param transorder
	 * @param product 
	 * @return
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	private OilcardAccount createOilcardAccount(
			TransOrderVO2<OilcardOrderVO> transorder, OilcardAccountProduct product) throws DataInvalidException, MaAccountException {
		OilcardOrderVO order = transorder.getOrder();
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
		account.setInsertTime(new Date());
		account.setUpdateTime(new Date());
		account.setChannelNo(order.getChannelNo());
		//线上开卡现在就可以用
		Date startdate = new Date();
		Date dayStart = DateUtil.toDayStart(startdate);
		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
		account.setStartTime(dayStart);
		account.setEndTime(dayend);
		account.setStatus(OilcardAccount.STATUS_OK);
		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}
	/**
	 * 转账（到现金账户）接口
	 * @return
	 */
	@RequestMapping("/transfer_to_cashAccount")
	@AccessRequered(methodName="分期卡转账到现金账户")
	public @ResponseBody Object oilcarTrans2Cash(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		TransOrderVO2<OilcardTrans2CashOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,OilcardTrans2CashOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		String messagebase = "从分期卡账户转账到现金账户";
		rm.setBaseErrorCode(25000);
		rm.setErrmsg(messagebase+"成功");
		transorder.setStrictCheck(true);

		try {
			prepare(transorder,request);
			//必填判断
			CashAccount descacc= (CashAccount) AccountFactory.getAccount(Account.ACCOUNT_CASH,transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "现金帐户");
			AccountValidateUtil.validateAccount(descacc);

			if(log.isDebugEnabled()){
				log.debug("检验通过，获取求");
			}
			OilcardAccountService oaSrv = SpringBeanUtil.getInstance().getBean(OilcardAccountService.class);
			Receipt receipt = oaSrv.transferOilcard2cashaccount(transorder, descacc,transorder.getOrder().getSum(),false);
			rm.put("orderid", receipt.getOrderNo());
			List<Account> inAccounts = receipt.getInAccounts();
			CashAccount account = (CashAccount) inAccounts.get(0);
			Map accountmap = new HashMap();
			accountmap.put("accountId", account.getAccountId());
			accountmap.put("balance", account.getBalance());
			accountmap.put("status","正常");
			rm.put("account",accountmap);
			//返回分期卡信息
			List<Account> outAccounts = receipt.getOutAccounts();
			OilcardAccount account2 = (OilcardAccount) outAccounts.get(0);
			rm.put("card", new OilcardResultVO(account2));
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
	 * 查询开分期卡结果
	 * @return
	 */
	@RequestMapping("/get")
	@AccessRequered(methodName="查询开分期卡结果")
	public @ResponseBody Object queryOilcardOpenResult(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
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
		String messagebase = "查询开分期卡结果";
		rm.setBaseErrorCode(26500);
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder.getApp().getAppId(), request);
			ValidateUtil.assertNull(transorder.getAppOrderId(), "应用订单号");
			OilcardAccountService oaSrv = SpringBeanUtil.getInstance().getBean(OilcardAccountService.class);
			OilcardAccountOrder oilcardAccountOrder = oaSrv.queryOilcardAccountByapporderId(transorder.getApp().getAppId(), transorder.getAppOrderId());
			rm.put("orderId", oilcardAccountOrder.getOrderId());
			OilcardAccount account = oilcardAccountOrder.getAccount();
			rm.put("card", new OilcardResultVO(account));
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
	 * 该接口仅提供给MAP.FRONT，前置可以使用该接口立即开通卡片。
	 * @return
	 */
	@RequestMapping(value="/open_offline_delivery_turbo",method=RequestMethod.POST)
	@AccessRequered(methodName="立即开通卡片")
	public @ResponseBody Object OpenOilcardImme(HttpServletRequest request) {
			TransOrderVO2<OfflineOpencardOrderVO> transorder;
			ResultModel rm = new ResultModel();
			try {
				String jsonstr = RequestUtil.getRequestPostData(request);
				request.setAttribute("rawjson", jsonstr);
				transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,OfflineOpencardOrderVO.class);	} catch (Exception e) {
				log.error(String.format("获取订单参数出错"),e);
				rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
				return rm;
			}
			String accountId=null;
			String messagebase = "开卡";
			int baseerrcode = 25200;
			rm.setBaseErrorCode(baseerrcode);
			rm.setErrmsg(messagebase+"成功");
			transorder.setOperationType(OrderConst.ORDER_OPERATION_OPEN_CARD_OFFILNE);
			try {
				prepare(transorder,request,false);
				//检查是否已经开过卡了,getAppOrderId应对posp的订单
				List<OilcardAccountOrder> selectByChannelOrderId = oilcardOrderDao.selectByAppOrderId(transorder.getOrder().getAppOrderId());
				if(selectByChannelOrderId!=null&&!selectByChannelOrderId.isEmpty()){
					if (log.isDebugEnabled()) {
						log.debug(String.format("posp订单号%s已存在，即已进行过开卡，线下开卡失败",transorder.getOrder().getAppOrderId()));
					}
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"开卡请求已处理，不能重复开卡");
				}
				
				//3des 解密
				//检查手机号码是否已经注册过，如果未注册，则注册该用户，并用新用户交易密码短息模板下行短信；
				User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
				//必填判断
				//根据cardtype查询卡类型
				String cardType = transorder.getOrder().getProductId();
				OilcardAccountProduct product = ocproDao.selectByPrimaryKey(cardType);
				if(product==null)
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("根据类型%s查询分期卡，结果为空",cardType));
					}
					throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS.clone(null, "找不到对应的产品");
				}
				if(!"OK#".equals(product.getStatus()))
				{
					if (log.isDebugEnabled()) {
						log.debug(String.format("分期卡%s已下线",cardType));
					}
					throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "分期卡产品已下线");
				}
				Product commonproduct= productDao.selectByPrimaryKey(product.getProductId());
				//modify by 3.6 如果是拆单，则返回报错
				Long paySum = transorder.getOrder().getPaySum();
				if(paySum!=null){
					//pos传的应该是购买的金额，不是产品的面额
					if(paySum.longValue()!=commonproduct.getProductPrice()){
						if (log.isDebugEnabled()) {
							log.debug(String.format("开卡数据[%s]传递支付金额与产品金额%s不一致，可能进行拆单，系统返回失败。",transorder.getOrder(),commonproduct.getProductPrice()));
						}
						throw new MaAccountException(MaAccountException.ERR_PRODUCT_EXCEPTION,"支付金额无法匹配到产品价格");
					}
				}
				
				createUserIfNessary(transorder, rm, baseerrcode, user,transorder.getApp().getAppId());
				//创建加油卡
				
				OilcardAccount descacc=createOilcardAccountImme(transorder,product,commonproduct);
				accountId = descacc.getAccountId();
//				OilcardAccount descacc=(OilcardAccount) AccountFactory.getAccount(Account.ACCOUNT_OIL_CARD,transorder.getOrder().getMobileNum());
				Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
				ValidateUtil.assertNull(descacc, "分期卡帐户");
				ValidateUtil.assertNull(sourceacc, "应用帐户");
				if(log.isDebugEnabled()){
					log.debug("检验通过，获取请求");
				}
				Receipt processOrder = orderSrv.processOfflineOpenOilcard(transorder,sourceacc,descacc,product);
				Map<String,Object> ordermap = new HashMap<String,Object>();
				
		        OfflineOpencardOrderVO order = transorder.getOrder();
				ordermap.put("remark",order.getRemark());
				ordermap.put("batchNo", order.getBatchNo());
				ordermap.put("operatorId", order.getOperatorId());
				ordermap.put("terminalOrderId", order.getTerminalOrderId());
				ordermap.put("terminalId", order.getTerminalId());
				ordermap.put("storeId", order.getStoreId());
				ordermap.put("sellerId", order.getSellerId());
				ordermap.put("mobileNum", order.getMobileNum());
				rm.put("order", ordermap);
				//返回分期卡信息
				Map<String,Object> cardmap = new HashMap<String, Object>();
				OilcardAccount returnaccount = descacc;
				cardmap.put("accountId", returnaccount.getAccountId());
				cardmap.put("amount", returnaccount.getAmount());
				cardmap.put("startTime", DateUtil.formatCommonDateorNull(returnaccount.getStartTime()));
				cardmap.put("endTime", DateUtil.formatCommonDateorNull(returnaccount.getEndTime()));
	        	String status2 = returnaccount.getStatus();
				switch(status2){
				case OilcardAccount.STATUS_FRZ:cardmap.put("status","冻结");break;
				case OilcardAccount.STATUS_NOP:cardmap.put("status","未开通");break;
				case OilcardAccount.STATUS_OK:cardmap.put("status","正常");break;
				case OilcardAccount.STATUS_OFF:cardmap.put("status","注销");break;
				}
				OilcardResultVO oilcardResultVO = new OilcardResultVO(descacc, product);
				rm.put("card",cardmap );
				//发送开卡成功的短信通知
				Map smsparam = BeanUtils.describe(oilcardResultVO);
				smsparam.put("productName",product.getProductName());
				smsparam.put("amount", MoneyUtil.getMoneyStringDecimal2fen(returnaccount.getAmount()));
				smsparam.put("returnSum", MoneyUtil.getMoneyStringDecimal2fen(product.getReturnSum()));
				String content = new PropertiesUtil().getProperty("sms.offline_openoilcard.success");
				content = StrUtil.replaceAll(content,smsparam);
				String mobileNum = transorder.getOrder().getMobileNum();
				sendbysms(rm, baseerrcode, mobileNum, content,transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_OPENCARD);
				//添加开卡结果通知
				AppInfo appinfo = appService.getAppByAppid(transorder.getApp().getAppId());
				OpenCardEvent oce = new OpenCardEvent(transorder,rm,product,commonproduct,appinfo.getAppKey());
				EventListenerContainer.getInstance().fireEvent(oce);
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
	 * 创建用户
	 * @param transorder
	 * @param rm
	 * @param baseerrcode
	 * @param user
	 * @throws ToolsException
	 * @throws MaAccountException
	 */
	public void createUserIfNessary(
			TransOrderVO2<? extends IOrderVO> transorder, ResultModel rm,
			int baseerrcode, User user,String appId) throws ToolsException,
			MaAccountException {
		if(user==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("手机号码%s并没有注册，这里为其创建用户",transorder.getOrder().getMobileNum()));
			}
			user = new User();
			user.setInsertTime(new Date());
			String mobileNum = transorder.getOrder().getMobileNum();
			user.setMobilenum(mobileNum);
			//user.setPassword(Md5Util.Encrypt(mobileNum.substring(mobileNum.length()-6)));
			//随机支付密码
			String paymentcode = StrUtil.genRandomCode(6);
//					user.setPaymentcodemd5(Md5Util.Encrypt(paymentcode));
			user.setPaymentCodeDES(Md5Util.Encrypt(paymentcode));				
			userSrv.createUser(user,transorder.getApp().getAppId());
			//下发通知信息
			String content = new PropertiesUtil().getProperty("sms.newuser");
			content = StrUtil.replaceAllWithToken(content, "paymentCode", paymentcode);
			sendbysms(rm, baseerrcode, mobileNum, content,appId,SmsSendService.ACTION_NAME_REGISTER);
			
		}
		else if(StringUtils.isBlank(user.getPaymentCodeDES())){
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户%s的pos支付密码为空，现在为其创建支付密码",user));
			}
			//随机支付密码
			String paymentcode = StrUtil.genRandomCode(6);
//					user.setPaymentcodemd5(Md5Util.Encrypt(paymentcode));
			user.setPaymentCodeDES(PospEncyptUtil.encypt(paymentcode, user.getMobilenum()));
			userSrv.createUser(user,transorder.getApp().getAppId());
			//下发通知信息
			String content = new PropertiesUtil().getProperty("sms.newuser");
			content = StrUtil.replaceAllWithToken(content, "paymentCode", paymentcode);
			sendbysms(rm, baseerrcode, user.getMobilenum(), content,appId,SmsSendService.ACTION_NAME_REGISTER);
		}
	}
	/**
	 * @param rm
	 * @param baseerrcode
	 * @param mobileNum
	 * @param content
	 */
	public void sendbysms(ResultModel rm, int baseerrcode, String mobileNum,
			String content,String appId,String action) {
		// 调用webservice 发送模板
		if (log.isDebugEnabled()) {
			log.debug("发送手机验证码至"+mobileNum+"请求:" + content);
		}
		
		try {
			smsSender.send(mobileNum,content,appId, action);
//			SmsSenderUtil.sendSms(mobileNum, content);
			if (log.isDebugEnabled()) {
				log.debug("向用户发送新用户创建成功的短信通知完成");
			}
			
		} catch (Exception e) {
			log.error("向用户发送新用户创建成功的短信通知出错:", e);
			rm.setErr(baseerrcode, "向用户发送新用户创建成功的短信通知失败，其它原因");
		}
	}
	
	/**
	 * 线下开卡交割失败记录同步接口，记录交割失败的记录
	 * @return
	 */
	@RequestMapping(value="/open_offline_deliveryfail_turbo",method=RequestMethod.POST)
	@AccessRequered(methodName="线下开卡交割失败记录同步接口")
	public @ResponseBody Object OpenOilcardDeliveryFail(HttpServletRequest request) {
		OpenCardFailDeliveryVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, OpenCardFailDeliveryVO.class);
		} catch (Exception e) {
			log.error(String.format("获取线下开卡交割失败记录参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		String messagebase = "线下开卡交割失败记录同步";
		rm.setBaseErrorCode(25300);
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder.getApp().getAppId(), request);
			transorder.selfvalidate();
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//验证order签名
			if (log.isDebugEnabled()) {
				log.debug(String.format("验证order的签名"));
			}
			boolean success = SignatureUtil.validateSignature(transorder.getTsig().getOrderMD5(), SignatureUtil.SIGNATURE_TYPE_MD5,transorder.getPaintText());
			if(!success)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("order验签不通过"));
				}
				throw ValidateException.ERROR_SIGNATURE_MD5;
			}
			else{
				if (log.isDebugEnabled()) {
					log.debug(String.format("order验签通过"));
				}
				
			}
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			//把失败的记录保存到库中
//			opencardDeliveryFailDao.recordOpenCardFail(transorder.getRecoderList());
			for (Iterator iterator = transorder.getRecoderList().iterator(); iterator.hasNext();) {
				OpenCardFailDeliveryDetailVO next = (OpenCardFailDeliveryDetailVO) iterator.next();
				OpenCardDeliveryFailEntity en = (OpenCardDeliveryFailEntity)new OpenCardDeliveryFailEntity();
				en.setBatchNo(next.getBatchNo());
				en.setMobileNum(next.getMobileNum());
				en.setIdCard(next.getID());
				en.setName(next.getName());
				en.setOperatorId(next.getOperatorId());
				en.setPaySum(next.getPaySum());
				en.setPayTime(DateUtil.parse(next.getPayTime()).getTime());
				en.setProductId(next.getProductId());
				en.setSellerId(next.getSellerId());
				en.setStatus("CRT");
				en.setStoreId(next.getStoreId());
				en.setTerminalId(next.getTerminalId());
				en.setTerminalOrderId(next.getTerminalOrderId());
				en.setOrderId(next.getAppOrderId());
				opencardDeliveryFailDao.insert(en);
			}
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
	

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.controller.AccountBaseController#getAccountType()
	 */
	@Override
	protected String getAccountType() {
		return Account.ACCOUNT_OIL_CARD;
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
			PropertiesUtil pu = new PropertiesUtil();
			boolean notdeleteaftervalidate = pu.getBoolean("smscode.fortest");
			if(!notdeleteaftervalidate){
				if (log.isDebugEnabled()) {
					log.debug(String.format("测试开启，验证后不删除短信验证码"));
				}
				smscodemapper.deleteAuthCode(query);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 创建分期卡帐户(马上可以启用）
	 * @param transorder
	 * @param product 
	 * @param commonproduct 
	 * @return
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	private OilcardAccount createOilcardAccountImme(
			TransOrderVO2<? extends IOrderVO> transorder, OilcardAccountProduct product, Product commonproduct) throws DataInvalidException, MaAccountException {
		IOrderVO order = transorder.getOrder();
		String productId;
		String remark;
		String channelno;
		if (order instanceof OfflineOpencardOrderVO) {
			OfflineOpencardOrderVO oorder = (OfflineOpencardOrderVO) order;
			productId = oorder.getProductId();
			channelno="";
			remark = oorder.getRemark();
		}
		else if (order instanceof OilcardOrderVO) {
			OilcardOrderVO oorder = (OilcardOrderVO) order;
			productId = oorder.getProductId();
			channelno=oorder.getChannelNo();
			remark = oorder.getRemark();
		}
		else{
			throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"不支持的订单数据");
		}
		
		OilcardAccount account = new OilcardAccount();
		
		User user = userSrv.getUserByMobile(order.getMobileNum());
		if(user==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户[手机号%s]不存在",order.getMobileNum()));
			}
			throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
		}
		//开卡时，还要经过审核操作，在此之前不能使用，也不会进行分期操作。
//		account.setAccountId(NoGenerationUtil.genNO("oc_"));//卡号
		account = OilcardReturnUtil.openOilCard(user.getUserId(),channelno, product, commonproduct,remark, true);
//		String accountid = accountIdSrv.prepareGetAccountId(productId);
//		account.setAccountId(accountid);
//		account.setRemark(remark);
//		account.setUserId(user.getUserId());
//		account.setProductId(productId);
//		
//		account.setRestAmount(commonproduct.getProductAmount()-product.getReturnSum());
//		account.setAmount(commonproduct.getProductAmount());
//		account.setRestStages(product.getTotalStages()-1);
//		account.setBalance(product.getReturnSum());
//		
//		account.setInsertTime(new Date());
//		account.setUpdateTime(new Date());
//		account.setChannelNo(channelno); 
////		account.setStartTime(new Date());
//		//现在就可以用
//		Date startdate = new Date();
//		Date dayStart = DateUtil.toDayStart(startdate);
//		Date dayend = DateUtils.add(dayStart, "MON".equals(product.getExpiresType())?Calendar.MONTH:Calendar.DAY_OF_MONTH, product.getExpiresLength());
//		account.setStartTime(dayStart);
//		account.setEndTime(dayend);
//		account.setStatus(OilcardAccount.STATUS_OK);
//		AccountValidateUtil.updateAccountSignature(account);
		return account;
	}
	
	

	/**
	 * 该接口仅提供给MAP.FRONT，前置可以使用该接口立即开通卡片。
	 * 由于线下开卡接口，pos机不调用，所以这个方法也不会调用
	 * @return
	 */
	@RequestMapping(value="/open_offline_delivery",method=RequestMethod.POST)
	@AccessRequered(methodName="交割设置卡片启用")
	@Deprecated
	public @ResponseBody Object changeOpenOilcardStatusByDelivery(HttpServletRequest request) {
			TransOrderVO2<OfflineOpencardDeliveryOrderVO> transorder;
			ResultModel rm = new ResultModel();
			try {
				String jsonstr = RequestUtil.getRequestPostData(request);
				request.setAttribute("rawjson", jsonstr);
				transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,OfflineOpencardDeliveryOrderVO.class);	} catch (Exception e) {
				log.error(String.format("获取订单参数出错"),e);
				rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
				return rm;
			}
			String accountId=null;
			String messagebase = "交割设置卡片启用";
			int baseerrcode = 26800;
			rm.setBaseErrorCode(baseerrcode);
			rm.setErrmsg(messagebase+"成功");
			
			try {
				
				
				
				logWithBegin(transorder.getApp().getAppId(), request);
				transorder.selfvalidate();
				//检查appid是否在数据中存在
				ValidateResult validate = appService.validate(transorder.getApp());
				//验证order签名
				validateOrderSign(transorder);
				//验证transOrderVO签名
				validateTransOrderSign(transorder);
				
				OfflineOpencardDeliveryOrderVO orderdelivery = transorder.getOrder();
				ValidateUtil.assertNull(orderdelivery, "交割内容");
				orderdelivery.selfvalidate();
				
				String accountType = orderdelivery.getAccountType();
				String orderId = orderdelivery.getOrderId();
				
				if(AccountConst.ACCOUNT_TYPE_DISCOUNTCARD.equals(accountType)){
					//折扣卡开卡
					if(log.isDebugEnabled())
					{
						log.debug("执行折扣卡开卡状态设置启用:"+orderId);
					}
					throw new RuntimeCryptoException("折扣卡开卡状态设置启用功能未实现");
				}
				else if(AccountConst.ACCOUNT_TYPE_OILCARD.equals(accountType)){
					if(log.isDebugEnabled())
					{
						log.debug("执行分期卡开卡状态设置启:"+orderId);
					}
					oilcardSrv.deliveryOilcard(orderId);
					
				}
				else{
					if(log.isDebugEnabled())
					{
						log.debug("未能识别的帐户类型"+accountType);
					}
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"未能识别的帐户类型"+accountType);
				}
				
				
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
	 * 线上批量开卡
	 * @author liudou
	 * @date 2015-7-28
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/batch_open_online")
	@AccessRequered(methodName="分期卡线上批量开卡")
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
			AppInfo app =(AppInfo) validate.getValidateObj();
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			rm.setErr(0, "接收成功");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					oilcardSrv.createUserBatch(transorder, (AppInfo)validate.getValidateObj());
					
				}
			}).start();
			//rm.putAll(oilcardSrv.createUserBatch(transorder, (AppInfo)validate.getValidateObj()));
			
			
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

