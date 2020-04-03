/**
 * 
 * AccountBaseController.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.controller;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.exception.AuthorityException;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.SignatureException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.CertificateUtils;
import com.hummingbird.common.util.JsonUtil;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.SignatureUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.AppLog;
import com.hummingbird.maaccount.entity.RealNameAuth;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Consumer;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.mapper.AppLogMapper;
import com.hummingbird.maaccount.mapper.CashAccountMapper;
import com.hummingbird.maaccount.mapper.CashAccountOrderMapper;
import com.hummingbird.maaccount.mapper.RealNameAuthMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.AppInfoService;
import com.hummingbird.maaccount.service.OrderService;
import com.hummingbird.maaccount.service.impl.AppMethodService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.OrderValidateUtil;
import com.hummingbird.maaccount.vo.BaseTransOrderVO;
import com.hummingbird.maaccount.vo.IOrderConsumerVO;
import com.hummingbird.maaccount.vo.IOrderVO;
import com.hummingbird.maaccount.vo.OfflineOpencardOrderVO;
import com.hummingbird.maaccount.vo.OrderVO;
import com.hummingbird.maaccount.vo.OrderdetailOutputBaseVO;
import com.hummingbird.maaccount.vo.TransOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;
import com.hummingbird.maaccount.vo.TransOrderWithConsumerVO;
import com.hummingbird.maaccount.vo.UndoRedPaperListVO;
import com.hummingbird.maaccount.vo.UndoRedPaperTransOrderVO;

/**
 * @author huangjiej_2
 * 2014年12月29日 下午11:43:48
 * 本类主要做为帐户操作的基础类
 */
public abstract class AccountBaseController extends BaseController {

	@Autowired(required = true)
	protected AppInfoService appService;
	@Autowired(required = true)
	protected AppMethodService authoritySrv;
//	@Autowired(required = true)
//	protected IAuthenticationService authService;
	@Autowired(required = true)
	protected OrderService orderSrv;
	@Autowired
	protected UserMapper userdao;
	@Autowired
	protected AppLogMapper logdao;
	@Autowired
	protected RealNameAuthMapper realNamedao;
	@Autowired
	protected CashAccountOrderMapper caodao;
	@Autowired
	protected CashAccountMapper cadao;
	
	/**
	 * 日志的本地线程
	 */
	protected static ThreadLocal<AppLog> applogTL = new ThreadLocal<AppLog>();
	
	/**
	 * 设置是否跳过权限校验，
	 * 针对投资帐户的登记存款和提取进行处理
	 */
	protected boolean skipAuthorize = false;
	
	/**
	 * 构造函数
	 */
	public AccountBaseController() {
	}
	
	/**
	 * 准备工作
	 * @param transorder
	 * @param request
	 * @throws DataInvalidException 
	 * @throws AuthorityException 
	 * @throws MaAccountException 
	 * @throws SignatureException 
	 */
	public void prepare(TransOrderVO transorder, HttpServletRequest request) throws DataInvalidException, AuthorityException, MaAccountException, SignatureException{
		
		logWithBegin(transorder,request);
		transorder.selfvalidate();
		//获取url以作为method的内容
		String requestURI = request.getRequestURI();
		requestURI=requestURI.replace(request.getContextPath(), "");
		transorder.setMethod(requestURI);
		//检查appid是否在数据中存在
		ValidateResult validate = appService.validate(transorder.getApp());
//		AppInfo app = new AppInfo();
//		app.setAppId(transorder.getApp().getAppId());
//		ValidateResult validate = new ValidateResult();
//		validate.setValidateObj(app);
		//验证order签名
		validateOrderSign(transorder);
		//验证transOrderVO签名
		validateTransOrderSign(transorder);
		//验证手机号码,并且验证对应的用户是否存在
		ValidateUtil.validateMobile(transorder.getOrder().getMobileNum());
		User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
		if(user==null){
			throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
		}
		//校验订单是否正常
		OrderValidateUtil.validateOrder(transorder);
//		//验证权限
//		if(!skipAuthorize){
//			
//			authoritySrv.authorize(transorder.getApp().getAppId(),requestURI);
//		}
//		else{
//			if (log.isDebugEnabled()) {
//				log.debug(String.format("跳过了权限校验"));
//			}
//		}
	}
	public void prepare(TransOrderWithConsumerVO<? extends IOrderConsumerVO> transorder, HttpServletRequest request) throws DataInvalidException, AuthorityException, MaAccountException, SignatureException{
		
		logWithBegin(transorder.getApp().getAppId(),request);
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
		//校验订单是否正常
//		OrderValidateUtil.validateOrder(transorder);
	}
	

	/**
	 * @param transorder
	 * @throws SignatureException 
	 */
	private void validateOrderSign(
			TransOrderWithConsumerVO<? extends IOrderConsumerVO> transorder) throws SignatureException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("验证order的签名"));
		}
		IOrderConsumerVO order = transorder.getOrder();
		boolean success = SignatureUtil.validateSignature(transorder.getTsig().getOrderMD5(), SignatureUtil.SIGNATURE_TYPE_MD5,order.getPaintText());
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
	}

	
	/**
	 * 准备工作
	 * @param transorder
	 * @param request
	 * @throws DataInvalidException 
	 * @throws AuthorityException 
	 * @throws MaAccountException 
	 * @throws SignatureException 
	 */
	public void prepare(TransOrderVO2 transorder, HttpServletRequest request) throws DataInvalidException, AuthorityException, MaAccountException, SignatureException{
		prepare(transorder,request,true);
	}
	
	
	/**
	 * 处理结束事件
	 * @param resultmodel
	 */
	public void post(Object resultmodel){
		logWithFinish(resultmodel);
	}
	
	/**
	 * 在开始时记录日志
	 * @param transorder
	 * @param request
	 */
	protected void logWithBegin(TransOrderVO transorder,
			HttpServletRequest request) {
		AppLog al = applogTL.get();
		if(al==null){
			
			al = new AppLog();
			al.setAppid(transorder.getApp().getAppId());
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			al.setMethod(requestURI);
			al.setInserttime(new Date());
			String requestjson = ObjectUtils.toString(request.getAttribute("rawjson"));
			if(StringUtils.isBlank(requestjson))
			{
				requestjson = new JSONObject(transorder).toString();
			}
			al.setRequest(requestjson);
		}
		logdao.insert(al);
		//通过本地线程绑定
		applogTL.set(al);
	}
	/**
	 * 在开始时记录日志
	 * @param transorder
	 * @param request
	 */
	protected void logWithBegin(TransOrderVO2 transorder,
			HttpServletRequest request) {
		AppLog al = applogTL.get();
		if(al==null){
			
			al = new AppLog();
			al.setAppid(transorder.getApp().getAppId());
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			al.setMethod(requestURI);
			al.setInserttime(new Date());
			String requestjson = ObjectUtils.toString(request.getAttribute("rawjson"));
			if(StringUtils.isBlank(requestjson))
			{
				requestjson = new JSONObject(transorder).toString();
			}
			al.setRequest(requestjson);
		}
		logdao.insert(al);
		//通过本地线程绑定
		applogTL.set(al);
	}
	/**
	 * 在开始时记录日志
	 * @param transorder
	 * @param request
	 */
	protected void logWithBegin(UndoRedPaperTransOrderVO transorder,
			HttpServletRequest request) {
		AppLog al = applogTL.get();
		if(al==null){
			
			al = new AppLog();
			al.setAppid(transorder.getApp().getAppId());
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			al.setMethod(requestURI);
			al.setInserttime(new Date());
			String requestjson = ObjectUtils.toString(request.getAttribute("rawjson"));
			if(StringUtils.isBlank(requestjson))
			{
				requestjson = new JSONObject(transorder).toString();
			}
			al.setRequest(requestjson);
		}
		logdao.insert(al);
		//通过本地线程绑定
		applogTL.set(al);
	}
	/**
	 * 在开始时记录日志
	 * @param transorder
	 * @param request
	 */
	protected void logWithBegin(String  appid,
			HttpServletRequest request) {
		AppLog al = applogTL.get();
		if(al==null){
			
			al = new AppLog();
			al.setAppid(appid);
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			al.setMethod(requestURI);
			al.setInserttime(new Date());
			String requestjson = ObjectUtils.toString(request.getAttribute("rawjson"));
			if(requestjson.length()>2000){
				requestjson = requestjson.substring(0,2000);
			}
			al.setRequest(requestjson);
		}
		logdao.insert(al);
		//通过本地线程绑定
		applogTL.set(al);
	}
	/**
	 * 处理完成更新日志
	 * @param transorder
	 * @param request
	 */
	protected void logWithFinish(Object obj) {
		AppLog al = applogTL.get();
		applogTL.remove();
		try {
			al.setRespone(StringUtils.substring((JsonUtil.convert2Json(obj)),0,2048));
		} catch (DataInvalidException e) {
			log.error(String.format("转换结果成json字符串失败"),e);
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("业务处理完成，返回结果为%S",al.getRespone()));
		}
		logdao.updateByPrimaryKey(al);
	}

	/**
	 * 验证order的签名
	 * @param transorder
	 * @throws SignatureException 
	 */
	protected void validateOrderSign(TransOrderVO2 transorder) throws SignatureException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("验证order的签名"));
		}
		IOrderVO order = transorder.getOrder();
		boolean success = SignatureUtil.validateSignature(transorder.getTsig().getOrderMD5(), SignatureUtil.SIGNATURE_TYPE_MD5,order.getPaintText());
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
	}
	/**
	 * @param transorder
	 * @throws SignatureException 
	 */
	protected void validateOrderSign(UndoRedPaperTransOrderVO transorder) throws SignatureException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("验证order的签名"));
		}
		UndoRedPaperTransOrderVO order = transorder;
		boolean success = SignatureUtil.validateSignature(transorder.getTsig().getOrderMD5(), SignatureUtil.SIGNATURE_TYPE_MD5,order.getPaintText());
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
	}
	/**
	 * 验证order的签名
	 * @param transorder
	 * @throws SignatureException 
	 */
	protected void validateOrderSign(TransOrderVO transorder) throws SignatureException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("验证order的签名"));
		}
		OrderVO order = transorder.getOrder();
		boolean success = SignatureUtil.validateSignature(transorder.getTsig().getOrderMD5(), SignatureUtil.SIGNATURE_TYPE_MD5,order.getMobileNum(),order.getAppOrderId(),order.getProductName(),order.getRemark(),ObjectUtils.toString(order.getSum()),order.getPaymentCodeMD5(),order.getAccountCode(),order.getOrderId(),order.getPeerAccountId(),order.getPeerAccountUnit(),order.getExternalOrderId(),order.getExternalOrderTime(),order.getPayOrderId() );
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
	}
	
	
	
	
	/**
	 * 验证TransOrder的签名
	 * @param transorder
	 * @throws SignatureException 
	 */
	protected void validateTransOrderSign(BaseTransOrderVO transorder) throws SignatureException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("验证TransOrder的签名"));
		}
		PropertiesUtil pu = new PropertiesUtil();
		boolean success;
		if("true".equals(pu.getProperty("verifybypublickey")))
		{
			String mingwen = ValidateUtil.sortbyValues(transorder.getTsig().getTimeStamp(),transorder.getTsig().getNonce(),transorder.getTsig().getOrderMD5(),transorder.getApp().getAppId());
			String signature = transorder.getTsig().getSignature();
			String publickeyStr = appService.getPublicKeyStr(transorder.getApp().getAppId());
			if(StringUtils.isBlank(publickeyStr))
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("app无公钥，无法进行验签"));
				}
				throw ValidateException.ERROR_SIGNATURE_RSA;
				//throw new SignatureException(ValidateException.ERRCODE_SIGNATURE_FAIL,"签名验签不通过,app无公钥");
			}
			try {
				PublicKey pkey = CertificateUtils.getPublicKeyFromCer(new ByteArrayInputStream(Base64.decodeBase64(publickeyStr)));
				
				success = CertificateUtils.verifySignatureByPublicKey(mingwen.getBytes(), signature, pkey);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.error(String.format("TransOrder请求签名验签出错"),e);
				}
				SignatureException e1 = ValidateException.ERROR_SIGNATURE_RSA.clone(e);
				throw e1;
//				throw new SignatureException(ValidateException.ERRCODE_SIGNATURE_FAIL,"签名验签不通过",e);
			}
			
			if(!success)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("TransOrder请求签名验签不通过"));
				}
				SignatureException e1 = ValidateException.ERROR_SIGNATURE_RSA;
				throw e1;
			}
			else{
				if (log.isDebugEnabled()) {
					log.debug(String.format("TransOrder请求签名验签通过"));
				}
				
			}
		}
		else{
			
			success=SignatureUtil.validateSignature(transorder.getTsig().getSignature(), SignatureUtil.SIGNATURE_TYPE_MD5,transorder.getTsig().getTimeStamp(),transorder.getTsig().getNonce(),transorder.getTsig().getOrderMD5(),transorder.getApp().getAppId() );
			if(!success)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("TransOrder请求签名验签不通过"));
				}
				SignatureException e1 = ValidateException.ERROR_SIGNATURE_MD5;
				throw e1;
			}
			else{
				if (log.isDebugEnabled()) {
					log.debug(String.format("TransOrder请求签名验签通过"));
				}
				
			}
		}
	}
//	/**
//	 * 验证TransOrder的签名
//	 * @param transorder
//	 * @throws SignatureException 
//	 */
//	protected void validateTransOrderSign(TransOrderVO2 transorder) throws SignatureException {
//		if (log.isDebugEnabled()) {
//			log.debug(String.format("验证TransOrder的签名"));
//		}
//		PropertiesUtil pu = new PropertiesUtil();
//		boolean success;
//		if("true".equals(pu.getProperty("verifybypublickey")))
//		{
//			String mingwen = ValidateUtil.sortbyValues(transorder.getTsig().getTimeStamp(),transorder.getTsig().getNonce(),transorder.getTsig().getOrderMD5(),transorder.getApp().getAppId());
//			String signature = transorder.getTsig().getSignature();
//			String publickeyStr = appService.getPublicKeyStr(transorder.getApp().getAppId());
//			if(StringUtils.isBlank(publickeyStr))
//			{
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("app无公钥，无法进行验签"));
//				}
//				throw ValidateException.ERROR_SIGNATURE_RSA;
//				//throw new SignatureException(ValidateException.ERRCODE_SIGNATURE_FAIL,"签名验签不通过,app无公钥");
//			}
//			try {
//				PublicKey pkey = CertificateUtils.getPublicKeyFromCer(new ByteArrayInputStream(Base64.decodeBase64(publickeyStr)));
//				
//				success = CertificateUtils.verifySignatureByPublicKey(mingwen.getBytes(), signature, pkey);
//			} catch (Exception e) {
//				if (log.isDebugEnabled()) {
//					log.error(String.format("TransOrder请求签名验签出错"),e);
//				}
//				SignatureException e1 = ValidateException.ERROR_SIGNATURE_RSA.clone(e);
//				throw e1;
////				throw new SignatureException(ValidateException.ERRCODE_SIGNATURE_FAIL,"签名验签不通过",e);
//			}
//			
//			if(!success)
//			{
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("TransOrder请求签名验签不通过"));
//				}
//				SignatureException e1 = ValidateException.ERROR_SIGNATURE_RSA;
//				throw e1;
//			}
//			else{
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("TransOrder请求签名验签通过"));
//				}
//				
//			}
//		}
//		else{
//			
//			success=SignatureUtil.validateSignature(transorder.getTsig().getSignature(), SignatureUtil.SIGNATURE_TYPE_MD5,transorder.getTsig().getTimeStamp(),transorder.getTsig().getNonce(),transorder.getTsig().getOrderMD5(),transorder.getApp().getAppId() );
//			if(!success)
//			{
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("TransOrder请求签名验签不通过"));
//				}
//				SignatureException e1 = ValidateException.ERROR_SIGNATURE_MD5;
//				throw e1;
//			}
//			else{
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("TransOrder请求签名验签通过"));
//				}
//				
//			}
//		}
//	}

	/**
	 * 根据手机号码获取本类指定的帐户信息
	 * @param mobile
	 * @return
	 * @throws MaAccountException
	 */
	protected  Account getAccount(String mobile) throws MaAccountException {
		Account acc=AccountFactory.getAccount(getAccountType(),mobile);
		return acc;
	}
	
	/**
	 * 获取帐户类型
	 * @return
	 */
	protected abstract String getAccountType();

	/**
	 * 合并输出
	 * @param rm
	 * @param pagingnation
	 * @param orders
	 */
	protected void mergeListOutput(ResultModel rm, Pagingnation pagingnation, List<? extends OrderdetailOutputBaseVO> orders) {
		rm.put("pageSize", pagingnation.getPageSize());
		rm.put("pageIndex", pagingnation.getCurrPage());
		rm.put("total", pagingnation.getTotalCount());
		rm.put("list", orders);
	} 
	
	/**
	 * 校验consumer并返回对应的帐户信息
	 * @param consumer
	 * @return
	 * @throws DataInvalidException
	 * @throws MaAccountException
	 */
	protected Account validateConsumer(Consumer consumer) throws DataInvalidException, MaAccountException
	{
		
		ValidateUtil.assertNull(consumer, "消费号码");
		Account acc;
		switch (consumer.getConsumerType()) {
		case Consumer.CONSUMER_TYPE_MOBILE:
			User user = userdao.selectByMobile(consumer.getConsumerId());
			ValidateUtil.assertNull(user, "用户");
			acc=AccountFactory.getAccount(Account.ACCOUNT_CASH, consumer.getConsumerId());
			 if(acc==null){
				 throw new MaAccountException(MaAccountException.ERR_CONSUMER_EXCEPTION,"通过消费号码"+consumer.getConsumerId()+"无法找到对应的帐户");
			 }
			break;
		case Consumer.CONSUMER_TYPE_CASHACCOUNT:
		case Consumer.CONSUMER_TYPE_INVESTMENTACCOUNT:
		case Consumer.CONSUMER_TYPE_DISCOUNTCARD:
		case Consumer.CONSUMER_TYPE_OILCARD:
			 acc = AccountFactory.getAccountByConsumer(consumer);
			 if(acc==null){
				 throw new MaAccountException(MaAccountException.ERR_CONSUMER_EXCEPTION,"通过消费号码"+consumer.getConsumerId()+"无法找到对应的帐户");
			 }
		default:
			if (log.isDebugEnabled()) {
				log.debug(String.format("消费号码%s不符合号码规则",consumer.getConsumerId()));
			}
			throw new MaAccountException(MaAccountException.ERR_CONSUMER_EXCEPTION,"消费号码"+consumer.getConsumerId()+"非法");
		}
		return acc;
		
	}
	
	/**
	 * @param transorder
	 * @param request
	 * @param 如果用户不存在，是否报错
	 * @throws DataInvalidException 
	 * @throws SignatureException 
	 */
	protected void prepare(TransOrderVO2 transorder,
			HttpServletRequest request, boolean validateuser) throws DataInvalidException, SignatureException {
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
		//验证手机号码,并且验证对应的用户是否存在
		ValidateUtil.validateMobile(transorder.getOrder().getMobileNum());
		if(validateuser){
			User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
			if(user==null){
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
			}
		}
		//校验订单是否正常
		OrderValidateUtil.validateOrder(transorder);
//		//验证权限
//		if(!skipAuthorize){
//			
//			authoritySrv.authorize(transorder.getApp().getAppId(),requestURI);
//		}
//		else{
//			if (log.isDebugEnabled()) {
//				log.debug(String.format("跳过了权限校验"));
//			}
//		}
		
	}

}
