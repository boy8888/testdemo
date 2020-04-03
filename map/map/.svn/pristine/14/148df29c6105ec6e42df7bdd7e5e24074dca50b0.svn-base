package com.hummingbird.maaccount.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.TripleDESUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.entity.RedPaperAccountOrder;
import com.hummingbird.maaccount.entity.RedPaperProduct;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.UserAccountCode;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.RedPaperAccountOrderMapper;
import com.hummingbird.maaccount.mapper.RedPaperProductMapper;
import com.hummingbird.maaccount.service.PaymentService;
import com.hummingbird.maaccount.service.RedPaperAccountService;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.AccountGenerationUtil;
import com.hummingbird.maaccount.util.OrderValidateUtil;
import com.hummingbird.maaccount.vo.QueryRedPaperOrderVO;
import com.hummingbird.maaccount.vo.RedPaperListResultVO;
import com.hummingbird.maaccount.vo.RedPaperOrderVO;
import com.hummingbird.maaccount.vo.RedPaperResultVO;
import com.hummingbird.maaccount.vo.RedPaperVO;
import com.hummingbird.maaccount.vo.SpendRedPaperOrderVO;
import com.hummingbird.maaccount.vo.SpentRedPaperResultVO;
import com.hummingbird.maaccount.vo.TransOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;
import com.hummingbird.maaccount.vo.UndoRedPaperListVO;
import com.hummingbird.maaccount.vo.UndoRedPaperTransOrderVO;

@Controller
@RequestMapping("/redPaper")
public class RedPaperController extends AccountBaseController{
	@Autowired
	RedPaperProductMapper productDao;
	@Autowired
	UserService userSrv;
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	@Autowired
	RedPaperAccountOrderMapper redPaperAccountOrderDao;
	
	@RequestMapping("/give")
	@AccessRequered(methodName="赠送红包")
	public @ResponseBody Object getRedPaper(HttpServletRequest request) {
		/*{
		    "app":
		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
		    "order":
		        {
		            "mobileNum":"13912345678",
		            "sum":100000, 
		            "redPaperProductId":"HONGBAO_YYD", 
		            "appOrderId":"123456789",
		            "remark":"某某为用户赠送有油贷1000元红包"},
		    "tsig":
		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
		}  */
		TransOrderVO2<RedPaperOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,RedPaperOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_ADD);
		String messagebase = "赠送红包";
		int baseerrcode=24100;
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
			
			ValidateUtil.validateMobile(transorder.getOrder().getMobileNum());
			
			User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册",transorder.getOrder().getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.clone(null, "用户不存在");
			}
			//根据redPaperProductId查询卡类型 
			String productId = transorder.getOrder().getRedPaperProductId();
			RedPaperProduct product = productDao.selectByPrimaryKey(productId);
			if(product==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据类型%s查询红包，结果为空",productId));
				}
				throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS.clone(null, "找不到对应的产品");
			}
			if(!"OK#".equals(product.getStatus()))
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("红包%s已下线",productId));
				}
				throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "红包产品已下线");
			}
			
			//创建红包
			RedPaperAccount descacc=createRedPaperAccount(transorder,product);
			accountId = descacc.getAccountId();
			Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "红包");
			ValidateUtil.assertNull(sourceacc, "应用帐户");
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processRedPaper(transorder,sourceacc,descacc,product);
			
			//返回红包信息
			RedPaperResultVO redPaperOrderVO = new RedPaperResultVO(descacc,processOrder.getOrderNo());
			rm.put("order",redPaperOrderVO );
			
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
	
	@RequestMapping("/query")
	@AccessRequered(methodName="查询红包")
	public @ResponseBody Object queryRedPaper(HttpServletRequest request) {
		/*{
		    "app":{
		        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
		    },
		    "query":{
		        "pageSize":10,"pageIndex":1,
		        "mobileNum":"13912345678",
		        "redPaperProductId":"HONGBAO_YYD|HONGBAO_YYD01|HONGBAO_YYD02",
		        "expireIn":"ALL",
		        "status":"OK#"
		    }
		}*/
		QueryRedPaperOrderVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, QueryRedPaperOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		
		String messagebase = "查询红包";
		rm.setBaseErrorCode(24200);
		rm.setErrmsg(messagebase+"成功");
		String accountId =null;
		try {
			logWithBegin(transorder.getApp().getAppId(), request);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			
		
			ValidateUtil.validateMobile(transorder.getQuery().getMobileNum());
			RedPaperVO query=transorder.getQuery();
			User user= userdao.selectByMobile(query.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册",query.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.clone(null, "用户不存在");
			}
			
			RedPaperAccountService raSrv = SpringBeanUtil.getInstance().getBean(RedPaperAccountService.class);
			Pagingnation pagingnation = query.toPagingnation();
			List<RedPaperAccount> redPaperAccounts = raSrv.queryRedPaperAccount(query.getMobileNum(),query.getRedPaperProductId(),query.getExpireIn(),pagingnation,query.getStatus(),query.getStartTime(),query.getEndTime(),query.getRedPaperAccountId(),transorder.getApp().getAppId());
			List<RedPaperListResultVO> list=new ArrayList<RedPaperListResultVO>();
			for(RedPaperAccount rea:redPaperAccounts){
				list.add(new RedPaperListResultVO(rea));
			}
			rm.put("pageSize", pagingnation.getPageSize());
			rm.put("pageIndex", pagingnation.getCurrPage());
			rm.put("total", pagingnation.getTotalCount());
			rm.put("list", list);
			
			
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
	private List<String> getAccountIds(List<RedPaperAccount> redPaperAccounts) {
		List<String> result=new ArrayList<>(redPaperAccounts.size());
		for (RedPaperAccount redPaperAccount : redPaperAccounts) {
			result.add(redPaperAccount.getAccountId());
		}
		return result;
	}

	@RequestMapping("/spend")
	@AccessRequered(methodName="消费红包")
	public @ResponseBody Object spendRedPaper(HttpServletRequest request) {
		/*{
		    "app":
		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
		    "order":{
		        "mobileNum":"13912345678",
		        "redPaperId":"1234123412341234|1234123412341235",
		        "remark":"使用红包消费", 
		        "appOrderId":"AO201412122344888444",
		        "associatedOrderId":"AO201412122344888444",
		        "accountCode":"231435",
		        "paymentCodeMD5":"w344dioeorreeoocWRT"
		        },
		    "tsig":
		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
		}  */
		TransOrderVO2<SpendRedPaperOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,SpendRedPaperOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_SPEND);
		String messagebase = "消费红包";
		int baseerrcode=24300;
		rm.setBaseErrorCode(baseerrcode);
		rm.setErrmsg(messagebase+"成功");
		String accountId =null;
		try {
			logWithBegin(transorder,request);
			validateSpendOrder(transorder);
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
			
			ValidateUtil.validateMobile(transorder.getOrder().getMobileNum());
			
			User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册",transorder.getOrder().getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.clone(null, "用户不存在");
			}
			ValidateResult vr=null;
			if(StringUtils.isNotBlank(transorder.getOrder().getAccountCode())){
				vr=validateUserAccountCode(transorder);
			}
			//当传入的支付密码不为空时，验证该密码，如果为空则不进行验证
			if(transorder.getOrder().getPaymentCodeMD5()!=null){
				if(!transorder.getOrder().getPaymentCodeMD5().equals(user.getPaymentcodemd5())){
					if (log.isDebugEnabled()) {
						log.debug(String.format("用户【手机号%s】支付密码错",transorder.getOrder().getMobileNum()));
					}
					throw ValidateException.ERROR_MATCH_PAYMENT_CODE;
				}
			}
			SpendRedPaperOrderVO order=transorder.getOrder();
			RedPaperAccountService raSrv = SpringBeanUtil.getInstance().getBean(RedPaperAccountService.class);
			String[] ids=order.getRedPaperId().split("\\|");
			List<String> redPaperIds=new ArrayList<String>();
			
			for(String id:ids){
				redPaperIds.add(id);		
			}
			List<RedPaperAccount> accounts = raSrv.selectAccountByAccountId(order.getMobileNum(),redPaperIds,transorder.getApp().getAppId());
			if(accounts.size()!=ids.length){
				if (log.isDebugEnabled()) {
					log.debug(String.format("部分红包无效"));
					
				}
				throw new InsufficientAccountBalanceException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"红包无法使用");
			}
			
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			List<RedPaperAccountOrder> orders=new ArrayList<RedPaperAccountOrder>();
			orders=	orderSrv.spendByRedPaper(transorder,accounts);
			/*List<SpentRedPaperResultVO> list=new ArrayList<SpentRedPaperResultVO>();
			for(RedPaperAccountOrder or:orders){
				list.add(new SpentRedPaperResultVO(or));
			}*/
			
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			for(RedPaperAccountOrder or:orders){
				Map<String,Object> orderTemp=new HashMap<String,Object>();
				orderTemp.put("orderId", or.getOrderId());
				orderTemp.put("redPaperId", or.getAccountId());
				String activity=getActivity(accounts, or.getAccountId());
				if(activity!=null){
					orderTemp.put("activity", activity);
				}
				list.add(orderTemp);
			}
			rm.put("order", list);
			
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
	@RequestMapping("/undo")
	@AccessRequered(methodName="撤销红包消费")
	public @ResponseBody Object undoRedPaper(HttpServletRequest request) {
		/*{
		    "app":
		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
		    "order":[{
		        "orderId":"ud12345678901234567890"
		        },
		        {
		            "orderId":"ud12345678901234567890"
		        }
		    ]
		    "tsig":
		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
		}  */
		UndoRedPaperTransOrderVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, UndoRedPaperTransOrderVO.class);
			
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		/*transorder.setOperationType(OrderConst.ORDER_OPERATION_UNDO_REDPAPER);*/
		String messagebase = "撤销红包消费";
		int baseerrcode=24400;
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
			
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			List<String> orderIds=new ArrayList<String>();
			
			List<UndoRedPaperListVO> list=transorder.getOrder();
			for(UndoRedPaperListVO orderId:list){
				orderIds.add(orderId.getOrderId());
			}
			orderSrv.undoRedPaper(transorder,orderIds);
			
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
	protected String getAccountType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param transorder
	 * @param appInfo 
	 */
	private void decrypt(TransOrderVO2<RedPaperOrderVO> transorder, AppInfo appInfo) {
		RedPaperOrderVO cardopenvo = transorder.getOrder();
		cardopenvo.setMobileNum(TripleDESUtil.decryptBased3Des(cardopenvo.getMobileNum(), appInfo.getAppKey()));
		cardopenvo.setRemark(TripleDESUtil.decryptBased3Des(cardopenvo.getRemark(), appInfo.getAppKey()));
	}
	
	/**
	 * @param transorder
	 * @param product 
	 * @return
	 * @throws DataInvalidException 
	 * @throws MaAccountException 
	 */
	private RedPaperAccount createRedPaperAccount(
			TransOrderVO2<RedPaperOrderVO> transorder, RedPaperProduct product) throws DataInvalidException, MaAccountException {
		RedPaperOrderVO order = transorder.getOrder();
		RedPaperAccount account = new RedPaperAccount();
		RedPaperProduct commonproduct= productDao.selectByPrimaryKey(product.getProductId());
		User user = userSrv.getUserByMobile(order.getMobileNum());
		if(user==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("用户[手机号%s]不存在",order.getMobileNum()));
			}
			throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
		}
		//开卡时，还要经过审核操作，在此之前不能使用，也不会进行分期操作。
//		account.setAccountId(NoGenerationUtil.genNO("oc_"));//卡号
		String accountid = AccountGenerationUtil.genRedOrderAccountNo();
		account.setAccountId(accountid);
		account.setRemark(order.getRemark());
		account.setUserId(user.getUserId());
		account.setProductId(order.getRedPaperProductId());
		account.setAmount(order.getSum());
		account.setInsertTime(new Date());
		account.setUpdateTime(new Date());
		account.setActivity(order.getActivity());
		Date startdate = new Date();
//		Date dayStart = DateUtil.toDayStart(startdate);
		account.setStartTime(startdate);
		if(product.getExpiresType().equals("EL#")){
			Date dayend = DateUtils.addDays(startdate, product.getExpiresLength());
			
			account.setEndTime(dayend);
		}
		else if(product.getExpiresType().equals("ET#")){
			
			account.setEndTime(product.getExpireTime());
		}
		
		
		
		account.setStatus(RedPaperAccount.STATUS_OK);
		return account;
	}
	/**
	 * @param transorder
	 * @return 
	 * @throws DataInvalidException 
	 */
	private static ValidateResult validateUserAccountCode(TransOrderVO2<SpendRedPaperOrderVO> transorder) throws DataInvalidException {
		PaymentService paymentService = SpringBeanUtil.getInstance().getBean(PaymentService.class);
		UserAccountCode uac = new UserAccountCode();
		uac.setAppId(transorder.getApp().getAppId());
		uac.setMobileNum(transorder.getOrder().getMobileNum());
		uac.setSmscode(transorder.getOrder().getAccountCode());
		ValidateResult vr = paymentService.validateAccountCode(uac);
		return vr;
	}
	
	private String getActivity(List<RedPaperAccount> accounts,String accountId){
		if(accountId==null){
			return null;
		}
		for (RedPaperAccount redPaperAccount : accounts) {
			if(accountId.equals(redPaperAccount.getAccountId())){
				return redPaperAccount.getActivity();
			}
		}
		return null;
	}
	
	private void validateSpendOrder(TransOrderVO2<SpendRedPaperOrderVO> transorder) throws DataInvalidException{
		ValidateUtil.assertNull(transorder.getApp(), "app相关信息");
		ValidateUtil.assertNull(transorder.getApp().getAppId(), "appId");
		ValidateUtil.assertNull(transorder.getOrder(), "订单相关信息");
		SpendRedPaperOrderVO order=transorder.getOrder();
		//ValidateUtil.assertNull(order.getPaymentCodeMD5(), "支付密码");
		ValidateUtil.assertNull(order.getRemark(), "备注");
		ValidateUtil.assertNull(order.getRedPaperId(), "红包编号");
		ValidateUtil.assertNull(order.getMobileNum(), "电话号码");
		ValidateUtil.assertNull(order.getAssociatedOrderId(), "关联订单号");
		ValidateUtil.assertNull(order.getAppOrderId(), "app自定义订单号");
	}
}
