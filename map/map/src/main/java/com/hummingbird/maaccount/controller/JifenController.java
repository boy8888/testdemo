package com.hummingbird.maaccount.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.JifenAccount;
import com.hummingbird.maaccount.entity.JifenAccountOrder;
import com.hummingbird.maaccount.entity.JifenProduct;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.UserAccountCode;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.JifenAccountMapper;
import com.hummingbird.maaccount.mapper.JifenProductMapper;
import com.hummingbird.maaccount.service.JifenAccountService;
import com.hummingbird.maaccount.service.PaymentService;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;
import com.hummingbird.maaccount.service.impl.UserService;
import com.hummingbird.maaccount.vo.JifenListResultVO;
import com.hummingbird.maaccount.vo.JifenOrderVO;
import com.hummingbird.maaccount.vo.JifenResultVO;
import com.hummingbird.maaccount.vo.JifenVO;
import com.hummingbird.maaccount.vo.QueryJifenOrderVO;
import com.hummingbird.maaccount.vo.QueryJifenResultVO;
import com.hummingbird.maaccount.vo.SpendJifenOrderVO;
import com.hummingbird.maaccount.vo.TransOrderVO2;

@Controller
@RequestMapping("/jifen")
public class JifenController extends AccountBaseController{
	@Autowired
	JifenProductMapper productDao;
	@Autowired
	JifenAccountMapper accountDao;
	@Autowired
	UserService userSrv;
	@Autowired
	AccountIdServiceImpl accountIdSrv;
	
	@RequestMapping("/add")
	public @ResponseBody Object getJifen(HttpServletRequest request) {
		/*{
		    "app":
		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
		    "order":
		        {
		            "mobileNum":"13912345678",
		            "sum":1000, 
		            "jifenProductId":"JF_YYD", 
		            "appOrderId":"123456789",
		            "remark":"增加1000个积分"},
		    "tsig":
		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
		} */ 
		TransOrderVO2<JifenOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,JifenOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_ADD);
		String messagebase = "增加积分";
		int baseerrcode=24500;
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
			//根据JifenProductId查询卡类型 
			String productId = transorder.getOrder().getJifenProductId();
			JifenProduct product = productDao.selectByPrimaryKey(productId);
			if(product==null)
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("根据类型%s查询积分，结果为空",productId));
				}
				throw ValidateException.ERROR_EXISTING_PRODUCT_NOT_EXISTS.clone(null, "找不到对应的产品");
			}
			if(!"OK#".equals(product.getStatus()))
			{
				if (log.isDebugEnabled()) {
					log.debug(String.format("积分%s已下线",productId));
				}
				throw ValidateException.ERROR_PRODUCT_OFFLINE.clone(null, "积分已下线");
			}
			JifenOrderVO order=transorder.getOrder();
		
			
			
			Receipt processOrder = orderSrv.processJifen(transorder,user,product);
				
			
			
			//返回积分信息
			JifenResultVO JifenOrderVO = new JifenResultVO((JifenAccount)processOrder.getOutAccounts().get(0),processOrder.getOrderNo(),order.getSum());
			rm.put("card",JifenOrderVO );
			
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
	@AccessRequered(methodName="查询积分")
	public @ResponseBody Object queryJifen(HttpServletRequest request) {
		/*{
    "app":{
        "appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"
    },
    "query":{
        "pageSize":10,"pageIndex":1,
        "mobileNum":"13912345678",
        "jifenProductId":"HONGBAO_YYD|HONGBAO_YYD01|HONGBAO_YYD02",
	    }
	}*/
		QueryJifenOrderVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, QueryJifenOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		
		String messagebase = "查询积分";
		rm.setBaseErrorCode(24600);
		rm.setErrmsg(messagebase+"成功");
		String accountId =null;
		try {
			logWithBegin(transorder.getApp().getAppId(), request);
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			
		
			ValidateUtil.validateMobile(transorder.getQuery().getMobileNum());
			JifenVO query=transorder.getQuery();
			User user= userdao.selectByMobile(query.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册",query.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.clone(null, "用户不存在");
			}
			
			JifenAccountService raSrv = SpringBeanUtil.getInstance().getBean(JifenAccountService.class);
			Pagingnation pagingnation = query.toPagingnation();
			List<JifenAccount> JifenAccounts = raSrv.queryJifenAccount(query.getMobileNum(),query.getJifenProductId(),pagingnation);
			
			List<JifenListResultVO> list=new ArrayList<JifenListResultVO>();
			for(JifenAccount rea:JifenAccounts){
				list.add(new JifenListResultVO(rea));
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
	
	
	@RequestMapping("/spend")
	@AccessRequered(methodName="兑换积分")
	public @ResponseBody Object spendJifen(HttpServletRequest request) {
		/*"app":
        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
    "order":{
        "mobileNum":"13912345678",
        "jifenProductId":"1234123412341234",
        "remark":"使用积分兑换", 
        "appOrderId":"AO201412122344888444",
        "associatedOrderId":"AO201412122344888444",
        "accountCode":"231435",
        "paymentCodeMD5":"w344dioeorreeoocWRT"
        },
    "tsig":
        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}*/
		TransOrderVO2<SpendJifenOrderVO> transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO2.class,SpendJifenOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_SPEND);
		String messagebase = "兑换积分";
		int baseerrcode=24700;
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
			ValidateResult vr=null;
			if(StringUtils.isNotBlank(transorder.getOrder().getAccountCode())){
				vr=validateUserAccountCode(transorder);
			}
//			if(!transorder.getOrder().getPaymentCodeMD5().equals(user.getPaymentcodemd5())){
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("用户【手机号%s】支付密码错",transorder.getOrder().getMobileNum()));
//				}
//				throw ValidateException.ERROR_MATCH_PAYMENT_CODE;
//			}
			SpendJifenOrderVO order=transorder.getOrder();
			JifenAccountService raSrv = SpringBeanUtil.getInstance().getBean(JifenAccountService.class);
			
			JifenAccount account = raSrv.selectAccountByProductId(order.getMobileNum(),order.getJifenProductId(),transorder.getApp().getAppId());
			if(account==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("用户该产品积分不存在"));
					
				}
				throw new InsufficientAccountBalanceException(MaAccountException.ERR_ACCOUNT_EXCEPTION,"积分无法使用");
			}
			
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			JifenAccountOrder jifenorder=orderSrv.spendByJifen(transorder,account);
			
			rm.put("orderId", jifenorder.getOrderId());
			
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
	 * @return 
	 * @throws DataInvalidException 
	 */
	private static ValidateResult validateUserAccountCode(TransOrderVO2<SpendJifenOrderVO> transorder) throws DataInvalidException {
		PaymentService paymentService = SpringBeanUtil.getInstance().getBean(PaymentService.class);
		UserAccountCode uac = new UserAccountCode();
		uac.setAppId(transorder.getApp().getAppId());
		uac.setMobileNum(transorder.getOrder().getMobileNum());
		uac.setSmscode(transorder.getOrder().getAccountCode());
		ValidateResult vr = paymentService.validateAccountCode(uac);
		return vr;
	}
	
}
