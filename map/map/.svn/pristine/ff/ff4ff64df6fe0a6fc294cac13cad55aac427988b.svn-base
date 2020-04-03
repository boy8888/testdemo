package com.hummingbird.maaccount.controller;



import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.exception.SignatureException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.DESUtil;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SignatureUtil;
import com.hummingbird.common.util.SmsSenderUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.RealNameAuth;
import com.hummingbird.maaccount.entity.SmsCode;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.ZJProduct;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.UserSmsCodeMapper;
import com.hummingbird.maaccount.service.CashAccountService;
import com.hummingbird.maaccount.service.WxRechargeLimitService;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountValidateUtil;
import com.hummingbird.maaccount.util.PospEncyptUtil;
import com.hummingbird.maaccount.vo.CashAccountBalanceVO;
import com.hummingbird.maaccount.vo.CashAccountInfoVO;
import com.hummingbird.maaccount.vo.CashAccountOrderVO;
import com.hummingbird.maaccount.vo.CashAccountVO;
import com.hummingbird.maaccount.vo.DayTokenCheckVO;
import com.hummingbird.maaccount.vo.GetTradeOrderVO;
import com.hummingbird.maaccount.vo.OpenCashCardVO;
import com.hummingbird.maaccount.vo.OrderQueryVO;
import com.hummingbird.maaccount.vo.OrderVO;
import com.hummingbird.maaccount.vo.OrderdetailOutputBaseVO;
import com.hummingbird.maaccount.vo.QueryCashAccountOrderVO;
import com.hummingbird.maaccount.vo.TokenCheckResultVO;
import com.hummingbird.maaccount.vo.TransOrderVO;

/**
 * 现金帐户相关controller
 * 
 * @author huangjiej_2 2014年11月10日 下午11:42:07
 */
@Controller
@RequestMapping("/cashAccount")
public class CashAccountController extends AccountBaseController {

//	@Autowired(required = true)
//	private IOrderPayService orderPayService;
//	@Autowired(required = true)
//	private AppInfoServiceImpl appService;
//	@Autowired(required = true)
//	private AccountMethodService authoritySrv;
////	@Autowired(required = true)
////	private IAuthenticationService authService;
//	@Autowired(required = true)
//	private OrderService orderSrv;
	
	@Autowired
	private CashAccountService caSer;
	
	@Autowired(required = true)
	private UserSmsCodeMapper smscodemapper;
	@Autowired(required = true)
	private UserService userSrv;
	@Autowired(required = true)
	private WxRechargeLimitService WxLimitSrv;
	@Autowired(required = true)
	private SmsSendService smsSender;
	/**
	 * 支付有油贷产品
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/pay_yyd")
	@Deprecated
	@AccessRequered(methodName="现金帐户支付有油贷产品")
	public @ResponseBody Object payYYD(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"qwerqerewtuuuu123wer","sum":500000,"productName":"","remark":"从投资账户转账5000元到现金账户", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		
		
		TransOrderVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		String messagebase = "从现金账户购买有油贷产品";
		rm.setBaseErrorCode(21300);
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			InvestmentAccount descacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_OBJECT);
			Account sourceacc= getAccount(transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "现金帐户");
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processOrder(transorder,sourceacc,descacc);
			rm.put("orderid", processOrder.getOrderNo());
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
	 * 支付有油贷产品
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/transfer_to_ia")
	@AccessRequered(methodName="现金帐户转账到投资账户")
	public @ResponseBody Object cash2Investment(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678", "sum":500000,"remark":"从现金账户转账5000元到投资账户", "appOrderId":"AO201412122344888444","accountCode":"231435","paymentCodeMD5":"w344dioeorreeoocWRT"},
//		    "tsig":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		}  
		
		
		TransOrderVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setStrictCheck(true);//需要验证帐户验证码和支付码
		transorder.setOperationType(OrderConst.ORDER_OPERATION_ZZ);
		String messagebase = "现金帐户转账到投资账户";
		rm.setBaseErrorCode(21400);
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			//备注字段必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			InvestmentAccount descacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			Account sourceacc= getAccount(transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "现金帐户");
		
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processOrder(transorder,sourceacc,descacc);
			rm.put("orderid", processOrder.getOrderNo());
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
	 * 查询交易订单记录
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/queryOrderListByUser")
	@AccessRequered(methodName="查询现金帐户交易订单记录")
	public @ResponseBody Object queryOrderListByUser(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
//		    "query":
//		        {"mobileNum":"13912345678","startDate":"2015-01-01","endDate":"2015-01-02" ,"pageSize":"10","pageIndex":"1"}
//		}  
		OrderQueryVO orderqueryvo;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			orderqueryvo = RequestUtil.convertJson2Obj(jsonstr, OrderQueryVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		
		String messagebase = "查询交易订单记录";
		rm.setBaseErrorCode(21500);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			Map filter =new HashMap();
			filter.put("accountCode", this.getAccountType());
			filter.put("appId", orderqueryvo.getApp().getAppId());
			filter.put("mobileNum", orderqueryvo.getQuery().getMobileNum());
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getEndDate())){
				filter.put("endDate", DateUtil.toDayEnd(orderqueryvo.getQuery().getEndDate()));
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getStartDate())){
				filter.put("startDate", DateUtil.toDayStart(orderqueryvo.getQuery().getStartDate()));
			}
			Pagingnation pagingnation = orderqueryvo.getQuery().toPagingnation();
			List<OrderdetailOutputBaseVO> orders = orderSrv.queryOrder(pagingnation, filter);
			mergeListOutput(rm,pagingnation,orders);
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
		}
		
		return rm;
		
	}
	
	/**
	 * 现金账户开通接口
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/open")
	@AccessRequered(methodName="现金账户开通")
	public @ResponseBody Object openCashAccount(HttpServletRequest request) {
		/*{
		    "app":{
		        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
		    },
		    "open":{
		        "mobileNum":"13912345678",
		        "smsCode":"2234",
		        "paymentCode":"223344"
		    }
		}  */ 
		OpenCashCardVO transorder;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, OpenCashCardVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		
		String messagebase = "现金账户开通";
		rm.setBaseErrorCode(27000);
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder.getApp().getAppId(), request);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//检查mobileNum
			ValidateUtil.validateMobile(transorder.getMobileNum());
			User user= userdao.selectByMobile(transorder.getMobileNum());
			//校验验证码
			boolean authCodeSuccess = validateAuthCode(transorder.getApp()
					.getAppId(), transorder.getMobileNum(),transorder.getOpen().getSmsCode());

			if (!authCodeSuccess) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("验证码不正确,%s", transorder));
				}
//				rm.setErr(ValidateException.ERRCODE_SIGNATURE_FAIL, "短信验证码不正确");
//				rm.setBaseErrorCode(2000);
				rm.mergeException(ValidateException.ERROR_MATCH_SMSCODE);
				return rm;
			}
			CashAccount cashAccount=new CashAccount();
			String mobileNum = transorder.getMobileNum();
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s没有注册，现在创建新用户",transorder.getMobileNum()));
				}
				// 创建用户
				user = new User();
				user.setInsertTime(new Date());
				user.setMobilenum(mobileNum);
				
//				user.setPassword(Md5Util.Encrypt(mobileNum.substring(mobileNum.length()-6)));
				PropertiesUtil pu = new PropertiesUtil();
				String key = pu.getProperty("wx.paycode.key");
//				String mima=DESUtil.encryptDes("223a44", key);
//				System.out.println("密码："+mima);
				String paymentcode = DESUtil.decodeDES(transorder.getOpen().getPaymentCode(),key);
				
				if(!NumberUtils.isNumber(paymentcode)||paymentcode.length()!=6){
					if(log.isDebugEnabled()){
						log.debug("密码应为6位数字。");
					}
					throw new MaAccountException(MaAccountException.ERRCODE_REQUEST,"密码应为6位数字");
				}
				
				//user.setPaymentcodemd5(Md5Util.Encrypt(paymentcode));
				user.setPaymentCodeDES(Md5Util.Encrypt(paymentcode));
				
				userSrv.createUser(user,transorder.getApp().getAppId());
				user= userdao.selectByMobile(transorder.getMobileNum());
				cashAccount=cadao.selectByPrimaryKey(user.getUserId());
				
			}
			else{

				cashAccount=cadao.selectByPrimaryKey(user.getUserId());
				//现金账户不存在，就创建，并重新获取账户信息
				if(cashAccount==null){
					caSer.createAccount(user.getUserId());
					cashAccount=(CashAccount) AccountFactory.getAccount(Account.ACCOUNT_CASH,transorder.getMobileNum());
					if(log.isDebugEnabled()){
						log.debug("现金账户不存在，创建现金账户");
					}
				}
				
				
				//如果现金账户已开通，就不往下执行
				if(cashAccount.getStatus().equals("OK#")){
					if(log.isDebugEnabled()){
						log.debug(String.format("现金账户%s已开通",cashAccount.getAccountId()));
					}
					throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"现金账户已经开通");
				}
				//现金账户没有开通，继续向下执行
				else{
					//开通现金账户
					cashAccount.setStatus("OK#");
					caSer.open(cashAccount);
				}
			}
			
			
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			//从实名认证表中取出记录
			RealNameAuth realuser=realNamedao.selectByPrimaryKey(user.getUserId());
			CashAccountVO cashAccountVO;
			//返回数据
			if(realuser!=null){
				cashAccountVO=new CashAccountVO(transorder.getMobileNum(),cashAccount,realuser,user);
			}else{
				cashAccountVO=new CashAccountVO(transorder.getMobileNum(),cashAccount,user);
			}
			rm.put("account", cashAccountVO);
			Map smsparam = new HashMap();
			smsparam.put("payTime", DateUtil.format(new Date(), "M月d日HH:mm"));
			String opencard = new PropertiesUtil().getProperty("sms.wxcard.open");
			String smscontent=StrUtil.replaceAll(opencard,smsparam);
			sendbysms(mobileNum,smscontent,transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_OPENCARD);
		}
		catch (Exception e1) {
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
	 * 现金账户信息查询接口
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/getAccountInfo")
	@AccessRequered(methodName="现金账户信息查询")
	public @ResponseBody Object getAccountInfo(HttpServletRequest request) {
		/*{
		    "app":{
		        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
		    },
		    "get":{
		        "mobileNum":"13912345678"
		    }
}    */ 
		CashAccountInfoVO transorder;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, CashAccountInfoVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		
		String messagebase = "现金账户信息查询";
		rm.setBaseErrorCode(26900);
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder.getApp().getAppId(), request);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//检查mobileNum
			ValidateUtil.validateMobile(transorder.getMobileNum());
			User user= userdao.selectByMobile(transorder.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册",transorder.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.clone(null, "用户不存在");
			}
			//从实名认证表中取出记录
			RealNameAuth realuser=realNamedao.selectByPrimaryKey(user.getUserId());
			
			CashAccount cashAccount=(CashAccount) AccountFactory.getAccount(Account.ACCOUNT_CASH,transorder.getMobileNum());
			ValidateUtil.assertNull(cashAccount, "现金帐户");
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			CashAccountVO cashAccountVO;
			//返回数据
			if(realuser!=null){
				cashAccountVO=new CashAccountVO(transorder.getMobileNum(),cashAccount,realuser,user);
			}else{
				cashAccountVO=new CashAccountVO(transorder.getMobileNum(),cashAccount,user);
			}
			rm.put("account", cashAccountVO);
			
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
	 * 查询余额接口
	 * 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/getBalance")
	@AccessRequered(methodName="查询余额接口")
	public @ResponseBody Object getBalance(HttpServletRequest request) {
		/*{
		    "app":{
		        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
		    },
		    "get":{
		        "mobileNum":"13912345678"
		    }
}    */ 
		CashAccountInfoVO transorder;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, CashAccountInfoVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		
		String messagebase = "查询余额";
		rm.setBaseErrorCode(26800);
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder.getApp().getAppId(), request);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			//检查mobileNum
			ValidateUtil.validateMobile(transorder.getMobileNum());
			User user= userdao.selectByMobile(transorder.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册",transorder.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.clone(null, "用户不存在");
			}
			
			CashAccount cashAccount=(CashAccount) AccountFactory.getAccount(Account.ACCOUNT_CASH,transorder.getMobileNum());
			ValidateUtil.assertNull(cashAccount, "现金帐户");
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			CashAccountBalanceVO cashAccountBalanceVO;
			//返回数据
			cashAccountBalanceVO=new CashAccountBalanceVO(transorder.getMobileNum(),cashAccount);
			
			rm.put("account", cashAccountBalanceVO);
			
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
	 * 充值（到现金账户）接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/recharge")
	@AccessRequered(methodName="充值（到现金账户）接口")
	public @ResponseBody Object rechargeCashAccount(HttpServletRequest request) {
/*//		{
			    "app":{
			        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
			    },  
			    "order":{
			        "mobileNum":"13912345678", 
			        "sum":500000,
			        "remark":"充值5000元到现金账户", 
			        "appOrderId":"AO201412122344888444",
			        "externalOrderId":"AO201412122344888444",
			        "externalOrderTime":"20150505123000",
			        "accountCode":"231435",
			        "peerAccountId":"",
			        "peerAccountUnit":"微信支付",
			        "peerAccountType":"WXP"
			    },
			    "tsig":{
			        "orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"
			    }
			}   */
		
		
		TransOrderVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setStrictCheck(true);//需要验证帐户验证码和支付码
		transorder.setOperationType(OrderConst.ORDER_OPERATION_ZZ);
		String messagebase = "充值到现金账户";
		rm.setBaseErrorCode(26700);
		rm.setErrmsg(messagebase+"成功");
		String mobileNum = transorder.getOrder().getMobileNum();
		try {
			prepare(transorder,request);
			//备注字段必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			if(transorder.getOrder().getSum()==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("充值金额不能为空",transorder.getOrder().getSum()));
				}
				throw ValidateException.ERROR_PARAM_NULL.cloneAndAppend(null, "充值金额不能为空");
			}
			Long rechargeTotal=orderSrv.rechargeTotal()+transorder.getOrder().getSum();
			Integer rechargeLimit=WxLimitSrv.selectByType("DAY");
			if(rechargeTotal>rechargeLimit){
				if (log.isDebugEnabled()) {
					log.debug(String.format("当天充值金额已达到上限"));
				}
				throw new MaAccountException(MaAccountException.ERR_ACCOUNT_EXCEPTION, "当天充值金额已达到上限");
			}
			User user= userdao.selectByMobile(mobileNum);
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册",transorder.getOrder().getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.clone(null, "用户不存在");
			}
			
			CashAccount sourceacc=(CashAccount) AccountFactory.getAccount(Account.ACCOUNT_CASH,transorder.getOrder().getMobileNum());
		
			ValidateUtil.assertNull(sourceacc, "现金帐户");
			AccountValidateUtil.validateAccount(sourceacc);
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			//判断实名验证类型是否为“OK#”，是的话可多次充值，不是只能充值一次
			
			//从实名认证表中取出记录
			RealNameAuth realuser=realNamedao.selectByPrimaryKey(user.getUserId());
			
			Receipt processOrder;
			if(realuser!=null&&realuser.getStatus().equals("OK#")){
				processOrder= orderSrv.rechargeCashAccount(transorder,sourceacc);
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s充值成功",transorder.getOrder().getMobileNum()));
				}
			}else{
				int total=caodao.countCashAccountOrder(transorder.getOrder().getMobileNum());
				
				if(total==0){
					if(transorder.getOrder().getSum()<=100000){
						processOrder=orderSrv.rechargeCashAccount(transorder,sourceacc);
						if (log.isDebugEnabled()) {
							log.debug(String.format("手机号码%s充值成功",transorder.getOrder().getMobileNum()));
						}
					}
					else{
						if (log.isDebugEnabled()) {
							log.debug(String.format("手机号码%s充值失败",transorder.getOrder().getMobileNum()));
						}throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"账户未实名验证，首次充值金额小于1000");
					}
				}else{
					if (log.isDebugEnabled()) {
						log.debug(String.format("手机号码%s充值失败",transorder.getOrder().getMobileNum()));
					}throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"账户未实名验证，不可进行二次充值");
				}
			}
			
			
			rm.put("orderId", processOrder.getOrderNo());
			Map smsparam = new HashMap();
			smsparam.put("payTime", DateUtil.format(new Date(), "M月d日HH:mm"));
			smsparam.put("unit", "元");
			smsparam.put("accountno",StringUtils.right(mobileNum, 4));
			smsparam.put("sum", new DecimalFormat("0.00").format((double)transorder.getOrder().getSum()/100));
			String rechargesms = new PropertiesUtil().getProperty("sms.wxcard.recharge");
			String smscontent=StrUtil.replaceAll(rechargesms,smsparam);
			sendbysms(mobileNum,smscontent,transorder.getApp().getAppId(),SmsSendService.ACTION_NAME_RECHARGE);
			
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
	@Override
	protected void validateOrderSign(TransOrderVO transorder)
			throws SignatureException {
		if(transorder.getMethod().equals("/cashAccount/recharge")){
			validateRechargeOrderSign(transorder);
		}else{
			super.validateOrderSign(transorder);
		}
	}

	/**
	 * 验证充值到投资接口的order的签名
	 * @param transorder
	 * @throws SignatureException 
	 */
	protected void validateRechargeOrderSign(TransOrderVO transorder) throws SignatureException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("验证order的签名"));
		}
		OrderVO order = transorder.getOrder();
		boolean success = SignatureUtil.validateSignature(transorder.getTsig().getOrderMD5(), SignatureUtil.SIGNATURE_TYPE_MD5,order.getMobileNum(),order.getAppOrderId(),order.getProductName(),order.getRemark(),ObjectUtils.toString(order.getSum()),order.getPaymentCodeMD5(),order.getAccountCode(),order.getOrderId(),order.getPeerAccountId(),order.getPeerAccountUnit(),order.getExternalOrderId(),order.getExternalOrderTime(),order.getPayOrderId(),order.getPeerAccountType() );
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
	 * 查询交易订单接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/getOrder")
	@AccessRequered(methodName="查询交易订单")
	public @ResponseBody Object getTradeOrder(HttpServletRequest request) {
/*		{
		    "app":{
		        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
		    },  
		    "order":{
		        "externalOrderId":"22224444445555556666"
		    }
		}    */
		GetTradeOrderVO getTradeOrder;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			getTradeOrder = RequestUtil.convertJson2Obj(jsonstr, GetTradeOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		
		String messagebase = "查询交易订单记录";
		rm.setBaseErrorCode(26600);
		rm.setErrmsg(messagebase+"成功");
		try {
//			logWithBegin(transorder.getApp().getAppId(), request);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(getTradeOrder.getApp());
			CashAccountOrderVO cashAccountOrderVO=caodao.selectByExternalOrderId(getTradeOrder.getOrder().getExternalOrderId());
			if(cashAccountOrderVO==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("订单不存在,外部订单号%s",getTradeOrder.getOrder().getExternalOrderId()));
				}
				throw new MaAccountException(MaAccountException.ERR_ORDER_EXCEPTION,"订单不存在");
				
			}
			rm.put("order", cashAccountOrderVO);
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
		}
		
		return rm;
		
	}
	
	/**
	 * 交易对账接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/dayCheck")
	@AccessRequered(methodName="交易对账接口")
	public @ResponseBody Object dayTokenCheck(HttpServletRequest request) {
	/*{
	    "app":{
	        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
		    },  
		    "check":{
		        "startDate":"20150505", "endDate":"20150506"
		    }
		}   */
		DayTokenCheckVO dayTokenCheckvo;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			dayTokenCheckvo = RequestUtil.convertJson2Obj(jsonstr, DayTokenCheckVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		
		String messagebase = "交易对账";
		rm.setBaseErrorCode(26500);
		rm.setErrmsg(messagebase+"成功");
		try {
			//限制传进来的日期格式
			if(dayTokenCheckvo.getCheck().getStartDate().length()!=8||dayTokenCheckvo.getCheck().getEndDate().length()!=8){
				if(log.isDebugEnabled()){
					log.debug(String.format("日期格式有误,格式样式为yyyyMMdd"));
				}
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "日期");
			}
			Date startDate=new Date();
			Date endDate= new Date();
			//标识有哪一个参数传进来了 i=1,endDate有值;i=2,startDate有值;i=3,都有值;
			int i=0;
			if(StringUtils.isNotBlank(dayTokenCheckvo.getCheck().getStartDate())){
				i+=2;
				startDate = DateUtil.toDayStart(DateUtil.parse2date(dayTokenCheckvo.getCheck().getStartDate(), "yyyyMMdd"));
				
			} 
			if(StringUtils.isNotBlank(dayTokenCheckvo.getCheck().getEndDate())){
				i+=1;
				endDate = DateUtil.toDayEnd(DateUtil.parse2date(dayTokenCheckvo.getCheck().getEndDate(), "yyyyMMdd"));
				
			}
			
			if((startDate==null&&endDate==null)){
				if (log.isDebugEnabled()) {
					log.debug(String.format("时间参数不能都为空"));
				}
				throw new MaAccountException(MaAccountException.ERRCODE_REQUEST,"时间参数不能都为空");
			}
			if((endDate.getTime() - startDate.getTime())/ (1000 * 60 * 60 * 24)>20){
				if (log.isDebugEnabled()) {
					log.debug(String.format("时间段不能超过20天"));
				}
				throw new MaAccountException(MaAccountException.ERRCODE_REQUEST,"时间段不能超过20天");
			}
			//i=1,endDate有值;i=2,startDate有值;i=3,都有值;
			if(i==1){
				//初始化为一天的开始
				startDate=DateUtil.toDayStart(endDate);
			}
			if(i==2){
				//初始化为一天的结束
				endDate=DateUtil.toDayEnd(startDate);
			}
			List<TokenCheckResultVO> check=new ArrayList<TokenCheckResultVO>();
			check=orderSrv.checkTokenOrder(startDate,endDate);
			rm.put("check",check);
		}catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
		}
		
		return rm;
		
	}
	/**
	 * 交易记录查询接口
	 * @param 
	 * @return
	 */
	@RequestMapping("/queryOrderList_xfcz")
	@AccessRequered(methodName="交易记录查询接口")
	public @ResponseBody Object queryOrderList(HttpServletRequest request) {
		/*{
		    "app":{
		        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
		    },
		    "query":{
		        "mobileNum":"13912345678",
		        "pageSize":10,"pageIndex":1
		    }
		}*/
		QueryCashAccountOrderVO queryCashAccountOrderVO;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			queryCashAccountOrderVO = RequestUtil.convertJson2Obj(jsonstr, QueryCashAccountOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		
		String messagebase = "查询余额订单记录";
		rm.setBaseErrorCode(26400);
		rm.setErrmsg(messagebase+"成功");
		try {
			Map filter =new HashMap();
			filter.put("accountCode", this.getAccountType());
			filter.put("mobileNum", queryCashAccountOrderVO.getQuery().getMobileNum());
			
			Pagingnation pagingnation = queryCashAccountOrderVO.getQuery().toPagingnation();
			List<CashAccountOrderVO> orders = orderSrv.queryCashAccountOrder(pagingnation, filter);
			rm.put("pageSize", pagingnation.getPageSize());
			rm.put("pageIndex", pagingnation.getCurrPage());
			rm.put("total", pagingnation.getTotalCount());
			rm.put("list", orders);
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志s
		}
		
		return rm;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.controller.AccountBaseController#getAccountType()
	 */
	@Override
	protected String getAccountType() {
		return Account.ACCOUNT_CASH;
	}
	/**
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
			;
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
	 * 发送短信
	 * @param mobileNum
	 * @param content
	 */
	public void sendbysms(String mobileNum,	String content,String appId,String action) {
		// 调用webservice 发送模板
		if (log.isTraceEnabled()) {
			log.trace("发送手机验证码至"+mobileNum+"请求:" + content);
		}
		
		try {
			smsSender.send(mobileNum,content, appId, action);
			//SmsSenderUtil.sendSms(mobileNum, content);
			if (log.isDebugEnabled()) {
				log.debug(action+"完成");
			}
		} catch (Exception e) {
			log.error(action+"出错", e);
		}
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		String paymentcode = DESUtil.decodeDES("809Ozeejbqw=","glwx0603");
		System.out.println(paymentcode);
		System.out.println(NumberUtils.isNumber(paymentcode));
	}
}
