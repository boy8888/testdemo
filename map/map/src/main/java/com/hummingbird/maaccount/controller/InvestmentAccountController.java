package com.hummingbird.maaccount.controller;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.exception.RequestException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.AbstractOrder;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.InvestmentAccountObjectOrder;
import com.hummingbird.maaccount.entity.OfflineRecharge;
import com.hummingbird.maaccount.exception.InsufficientAccountBalanceException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Order;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.mapper.OfflineRechargeMapper;
import com.hummingbird.maaccount.service.InvestmentAccountService;
import com.hummingbird.maaccount.service.OrderService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.OrderFactory;
import com.hummingbird.maaccount.util.OrderValidateUtil;
import com.hummingbird.maaccount.vo.OfflineRechargeOutputVO;
import com.hummingbird.maaccount.vo.OrderQueryVO;
import com.hummingbird.maaccount.vo.OrderWithdrawDetailOutputVO;
import com.hummingbird.maaccount.vo.OrderdetailOutputBaseVO;
import com.hummingbird.maaccount.vo.RechargeOutputVO;
import com.hummingbird.maaccount.vo.RemainingOrderQueryVO;
import com.hummingbird.maaccount.vo.TransOrderVO;

/**
 * 营销帐户相关controller
 * 
 * @author huangjiej_2 2014年11月10日 下午11:42:07
 */
@Controller
@RequestMapping("/investmentAccount")
public class InvestmentAccountController extends AccountBaseController {
	@Autowired
	protected InvestmentAccountService investSrv;

	/**
	 * 投资的剩余帐户转出（到现金账户）接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/transfer_to_cashAccount")
	@AccessRequered(methodName="投资帐户可用余额转出至现金帐户")
	public @ResponseBody Object trans2CashAccount(HttpServletRequest request) {
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
		
		transorder.setOperationType(OrderConst.ORDER_OPERATION_ZZ);
		transorder.setStrictCheck(true);
		
		String messagebase = "从投资帐户转账到现金账户";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(21600);
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			Account descacc=AccountFactory.getAccount(Account.ACCOUNT_CASH,transorder.getOrder().getMobileNum());
			InvestmentAccount sourceacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "现金帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");

			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
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
	 * 返本接口接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/payback_capital")
	@AccessRequered(methodName="返本接口")
	public @ResponseBody Object paybackCapital(HttpServletRequest request) {
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_SB);
		String messagebase = "投资账户返本";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(21700);
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
//			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			InvestmentAccount descacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			InvestmentAccount sourceacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_OBJECT);
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");

			if(log.isDebugEnabled()){
				
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processOrder(transorder,sourceacc,descacc);
			rm.put("orderid", processOrder.getOrderNo());
		} 
		catch(InsufficientAccountBalanceException e1){
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，投标本金总额不足");
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
	 * 支付有油贷产品
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/pay_yyd")
	@AccessRequered(methodName="投资帐户支付有油贷产品")
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
		String messagebase = "从投资账户购买有油贷产品";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(21800);
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			InvestmentAccount sourceacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_FREEZE);
			sourceacc.setOrderTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			InvestmentAccount descacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_OBJECT);
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");

			String ordertable = OrderFactory.getOrderTable(sourceacc);
			String oriorderid = transorder.getOrder().getOrderId();
			// 检查原订单号是否存在
			if(StringUtils.isBlank(oriorderid)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS;
			}
			//查询余额订单
//			Order remainorder = orderSrv.getOrder(ordertable, oriorderid);
//			if(remainorder==null){
//				throw new MaAccountException(23801,"原订单不存在");
//			}
//			//订单id为冻结时的可用余额订单id，需要找到对应的标的订单id,
//			InvestmentAccountObjectOrder oriorder = (InvestmentAccountObjectOrder) orderSrv.getFreezeObjectOrder(oriorderid);
//			if(oriorder==null){
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("找不到对应的标的冻结订单"));
//				}
//				throw new MaAccountException(23801,"原订单不存在");
//			}
////			//如果订单状态不是fRz，则该订单已被处理，不能操作
//			if(OrderConst.ORDER_STATUS_FROZEN.equals(oriorder.getStatus())){
//				if (log.isDebugEnabled()) {
//					log.debug(String.format("冻结的标的订单的状态为%s，非冻结状态，不能操作",oriorder.getStatus()));
//				}
//				throw ValidateException.ERROR_EXISTING_ORDERNO_EXISTS.clone(null,"冻结订单已被解冻或已购买有油贷产品");
//			}
//			String oriorderaccountid = oriorder.getAccountId();
			InvestmentAccountObjectOrder frozenObjectOrder = OrderFactory.checkAntiFreeze(oriorderid,ordertable,sourceacc,descacc);
			
			//设置解冻的金额
			transorder.getOrder().setSum(Math.abs(frozenObjectOrder.getSum()));//原转出订单应该是负数，由投资帐户流出
			//把原转出的订单号记录下来
			transorder.setOriginalOrderId(oriorderid);
			transorder.setOriginalTable(ordertable);
			transorder.getOrder().setProductName(((AbstractOrder)frozenObjectOrder).getProductName());
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processOrderIaYYD(transorder,sourceacc,descacc,frozenObjectOrder);
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
	 * 返息接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/payback_interest")
	@AccessRequered(methodName="投资帐户返息")
	public @ResponseBody Object paybackInterest(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"登记存款5000，会计：李娜", "appOrderId":"AO201412122344888444"},
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_SX);
		String messagebase = "投资账户返息";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(21900);
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
//			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			Account sourceacc=AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			InvestmentAccount descacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");

			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
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
	 * 提交线下充值凭据,
	 * 用户可以在有油贷网站提交线下充值凭据，财务人员根据线下充值凭据查看银行资金往来，确认后，将在投资账户可用余额上增加充值金额。如果没有查到相关银行记录，财务人员将取消该提交线下充值凭据，不会对用户的投资账户进行任何操作。
	 * @return
	 */
	@RequestMapping("/savings_by_user")
	@AccessRequered(methodName="提交线下充值凭据")
	public @ResponseBody Object saving(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"登记存款5000，会计：李娜", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		skipAuthorize=true;//跳过校验，因为是从自己系统的页面进行访问
		
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_CZ);
		String messagebase = "提交线下充值凭据";
		rm.setBaseErrorCode(22000);
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			//检查外部订单字段
			ValidateUtil.assertNull(transorder.getOrder().getExternalOrderId(), "线下交易流水号");
			ValidateUtil.assertNull(transorder.getOrder().getExternalOrderTime(), "线下交易时间");
			try {
				DateUtils.parseDate(transorder.getOrder().getExternalOrderTime(),new String[]{ "yyyy-MM-dd HH:mm:ss"});
			} catch (Exception e) {
				log.error(String.format("线下交易时间非日期格式:"+transorder.getOrder().getExternalOrderTime()),e);
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"线下交易时间非日期格式");
			}
			ValidateUtil.assertNull(transorder.getOrder().getSum(), "金额");
			Account sourceacc=AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			InvestmentAccount descacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "应用帐户");

			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.savingByUser(transorder,sourceacc,descacc);
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
	 * 线下充值成功
	 * @return
	 */
	@RequestMapping("/offlineRecharge_success")
	@AccessRequered(methodName="线下充值确认成功接口")
	public @ResponseBody Object savingSuccess(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"登记存款5000，会计：李娜", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		skipAuthorize=true;//跳过校验，因为是从自己系统的页面进行访问
		
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_CZ);
		String messagebase = "线下充值确认";
		rm.setBaseErrorCode(22100);
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder,request);
			//从订单号中提取用户信息出来
			//从订单号中提取用户信息出来
			String oriorderid = transorder.getOrder().getOrderId();
			// 检查原订单号是否存在
			if(StringUtils.isBlank(oriorderid)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS;
			}
			ValidateResult validate = appService.validate(transorder.getApp());
			OfflineRechargeMapper offlineRechargeDao = SpringBeanUtil.getInstance().getBean(OfflineRechargeMapper.class);
			OfflineRecharge oriorder = offlineRechargeDao.selectByPrimaryKey(oriorderid);
//			oriaccount = accounts
			if(oriorder==null){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS.clone(null, "充值凭据不存在");
			}
			if(oriorder.getStatus().equals(OrderConst.ORDER_STATUS_SAVING_SUCCESS)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS.clone(null, "充值已成功");
			}
			else if(oriorder.getStatus().equals(OrderConst.ORDER_STATUS_SAVING_CANCLE)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS.clone(null, "充值已撤销");
			}
			
			//判断该订单有没有被处理
			transorder.selfvalidate();
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			//获取url以作为method的内容
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			transorder.setMethod(requestURI);
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			Account sourceacc=AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			InvestmentAccount descacc=(InvestmentAccount)AccountFactory.getAccountByAccountId(Account.ACCOUNT_INVESTMENT,oriorder.getAccountId());
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "应用帐户");
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.savingSuccess(transorder,sourceacc,descacc,oriorder);
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
	 * 线下充值接口接口(已弃用)
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/savings_by_accountant")
	@AccessRequered(methodName="线下充值接口")
	@Deprecated
	public @ResponseBody Object savingByAccountant(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"登记存款5000，会计：李娜", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		skipAuthorize=true;//跳过校验，因为是从自己系统的页面进行访问
		
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_CZ);
		String messagebase = "线下充值接口";
		rm.setBaseErrorCode(25200);
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			//检查外部订单字段
			ValidateUtil.assertNull(transorder.getOrder().getExternalOrderId(), "线下交易流水号");
			ValidateUtil.assertNull(transorder.getOrder().getExternalOrderTime(), "线下交易时间");
			try {
				DateUtils.parseDate(transorder.getOrder().getExternalOrderTime(),new String[]{ "yyyy-MM-dd HH:mm:ss"});
			} catch (Exception e) {
				log.error(String.format("线下交易时间非日期格式:"+transorder.getOrder().getExternalOrderTime()),e);
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"线下交易时间非日期格式");
			}
			ValidateUtil.assertNull(transorder.getOrder().getSum(), "金额");
			Account sourceacc=AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			InvestmentAccount descacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "应用帐户");

			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
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
	 * 线下充值撤销
	 * @return
	 */
	@RequestMapping("/offlineRecharge_to_cancel")
	@AccessRequered(methodName="线下充值确认撤销接口")
	public @ResponseBody Object savingCancle(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"登记存款5000，会计：李娜", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		skipAuthorize=true;//跳过校验，因为是从自己系统的页面进行访问
		
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_CZ);
		String messagebase = "线下充值撤销";
		rm.setBaseErrorCode(22200);
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder,request);
			//从订单号中提取用户信息出来
			//从订单号中提取用户信息出来
			String oriorderid = transorder.getOrder().getOrderId();
			// 检查原订单号是否存在
			if(StringUtils.isBlank(oriorderid)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS;
			}
			ValidateResult validate = appService.validate(transorder.getApp());
			OfflineRechargeMapper offlineRechargeDao = SpringBeanUtil.getInstance().getBean(OfflineRechargeMapper.class);
			OfflineRecharge oriorder = offlineRechargeDao.selectByPrimaryKey(oriorderid);
//			oriaccount = accounts
			if(oriorder==null){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS.clone(null, "充值凭据不存在");
			}
			if(oriorder.getStatus().equals(OrderConst.ORDER_STATUS_SAVING_SUCCESS)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS.clone(null, "充值已成功");
			}
			else if(oriorder.getStatus().equals(OrderConst.ORDER_STATUS_SAVING_CANCLE)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS.clone(null, "充值已撤销");
			}
			
			//判断该订单有没有被处理
			transorder.selfvalidate();
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			//获取url以作为method的内容
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			transorder.setMethod(requestURI);
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			Account sourceacc=AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
			InvestmentAccount descacc=(InvestmentAccount)AccountFactory.getAccountByAccountId(Account.ACCOUNT_INVESTMENT,oriorder.getAccountId());
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "应用帐户");
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.savingCancle(transorder,sourceacc,descacc,oriorder);
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
	 * 登记提现申请接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/withdraw_to_freeze")
	@AccessRequered(methodName="投资帐户提现申请")
	public @ResponseBody Object withdraw_freeze(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"登记存款5000，会计：李娜", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		skipAuthorize=true;//跳过校验，因为是从自己系统的页面进行访问
		
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_TX);
		transorder.setOrderStatus(OrderConst.ORDER_STATUS_FROZEN);
		transorder.setStrictCheck(true);
		rm.setBaseErrorCode(22300);
		String messagebase = "提现申请";
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			
			ValidateUtil.assertNull(transorder.getOrder().getSum(), "金额");
			InvestmentAccount descacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			InvestmentAccount sourceacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");
			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);//使用冻结金额
			sourceacc.setOrderTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);//生成余额订单
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_FREEZE);//使用冻结金额
			descacc.setOrderTarget(InvestmentAccount.SUM_TARGET_TYPE_NONE);//不生成订单
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processOrder4withdraw_to_freeze(transorder,sourceacc,descacc);
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
	 * 投资帐户提现成功
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/withdraw_success")
	@AccessRequered(methodName="投资帐户提现成功")
	public @ResponseBody Object withdraw_success(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"登记存款5000，会计：李娜", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		
		skipAuthorize=true;//跳过校验，因为是从自己系统的页面进行访问
		
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_TX);
		rm.setBaseErrorCode(22400);
		String messagebase = "提现成功";
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder,request);
			//从订单号中提取用户信息出来
			String ordertable = "t_investment_account_remaining_order";
			String oriorderid = transorder.getOrder().getOrderId();
			// 检查原订单号是否存在
			if(StringUtils.isBlank(oriorderid)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS;
			}
			ValidateResult validate = appService.validate(transorder.getApp());
			Order oriorder = orderSrv.getOrder(ordertable, oriorderid);
//			oriaccount = accounts
			if(oriorder==null){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS.clone(null, "原订单不存在");
			}
			
			transorder.selfvalidate();
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			//获取url以作为method的内容
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			transorder.setMethod(requestURI);
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			//验证手机号码,并且验证对应的用户是否存在
//			ValidateUtil.validateMobile(transorder.getOrder().getMobileNum());
//			User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
//			if(user==null){
//				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
//			}
			//校验订单是否正常
			OrderValidateUtil.validateOrder(transorder);
			Account descacc=AccountFactory.getAccount(Account.ACCOUNT_APP,transorder.getOrder().getMobileNum());
//			InvestmentAccount sourceacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			InvestmentAccount sourceacc=(InvestmentAccount)AccountFactory.getAccountByAccountId(Account.ACCOUNT_INVESTMENT,oriorder.getAccountId());
			
			ValidateUtil.assertNull(descacc, "应用帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");
			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_FREEZE);//使用冻结金额
			sourceacc.setOrderTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);//生成余额订单
			//判断该订单有没有被处理
			OrderFactory.checkWithdraw(oriorderid, sourceacc, descacc);
			String oriorderaccountid = oriorder.getAccountId();
			//设置解冻的金额
			transorder.getOrder().setSum(Math.abs(oriorder.getSum()));//原转出订单应该是负数，由投资帐户流出
			//把原转出的订单号记录下来
			transorder.setOriginalOrderId(oriorderid);
			transorder.setOriginalTable(ordertable);
			transorder.getOrder().setProductName(((AbstractOrder)oriorder).getProductName());
			
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processOrder4withdraw_success(transorder,sourceacc,descacc);
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
	 * 投资帐户提现申请撤销
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/withdraw_to_unfreeze")
	@AccessRequered(methodName="投资帐户提现申请撤销")
	public @ResponseBody Object withdraw_unfreeze(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"登记存款5000，会计：李娜", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		
		skipAuthorize=true;//跳过校验，因为是从自己系统的页面进行访问
		
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_TX);
		rm.setBaseErrorCode(22500);
		String messagebase = "提现申请撤销";
		rm.setErrmsg(messagebase+"成功");
		try {
			logWithBegin(transorder,request);
			//从订单号中提取用户信息出来
			String ordertable = "t_investment_account_remaining_order";
			String oriorderid = transorder.getOrder().getOrderId();
			// 检查原订单号是否存在
			if(StringUtils.isBlank(oriorderid)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS;
			}
			ValidateResult validate = appService.validate(transorder.getApp());
			Order oriorder = orderSrv.getOrder(ordertable, oriorderid);
//			oriaccount = accounts
			if(oriorder==null){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS.clone(null, "原订单不存在");
			}
			transorder.selfvalidate();
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			//获取url以作为method的内容
			String requestURI = request.getRequestURI();
			requestURI=requestURI.replace(request.getContextPath(), "");
			transorder.setMethod(requestURI);
			//验证order签名
			validateOrderSign(transorder);
			//验证transOrderVO签名
			validateTransOrderSign(transorder);
			//验证手机号码,并且验证对应的用户是否存在
//			ValidateUtil.validateMobile(transorder.getOrder().getMobileNum());
//			User user= userdao.selectByMobile(transorder.getOrder().getMobileNum());
//			if(user==null){
//				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS;
//			}
			//校验订单是否正常
			OrderValidateUtil.validateOrder(transorder);
			InvestmentAccount sourceacc=(InvestmentAccount)AccountFactory.getAccountByAccountId(Account.ACCOUNT_INVESTMENT,oriorder.getAccountId());
			InvestmentAccount descacc=(InvestmentAccount)AccountFactory.getAccountByAccountId(Account.ACCOUNT_INVESTMENT,oriorder.getAccountId());
			
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);//使用冻结金额
			descacc.setOrderTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);//生成余额订单
			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_FREEZE);//使用冻结金额
			sourceacc.setOrderTarget(InvestmentAccount.SUM_TARGET_TYPE_NONE);//不生成余额订单
			OrderFactory.checkWithdraw(oriorderid, sourceacc, descacc);
			//设置解冻的金额
			transorder.getOrder().setSum(Math.abs(oriorder.getSum()));//原转出订单应该是负数，由投资帐户流出
			//把原转出的订单号记录下来
			transorder.setOriginalOrderId(oriorderid);
			transorder.setOriginalTable(ordertable);
			transorder.getOrder().setProductName(((AbstractOrder)oriorder).getProductName());
			
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			Receipt processOrder = orderSrv.processOrder4withdraw_to_unfreeze(transorder,sourceacc,descacc);
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
	 * 投资帐户可用余额转出（到银行卡）接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/transfer_to_bankCard")
	@AccessRequered(methodName="投资帐户可用余额转出（到银行卡）接口")
	public @ResponseBody Object trans2Bankcard(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"从投资账户转账5000元到银行卡（卡号6211 2345 5678 1234）", "appOrderId":"AO201412122344888444"},
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
		transorder.setStrictCheck(true);
		transorder.setOperationType(OrderConst.ORDER_OPERATION_ZZ);
		String messagebase = "从投资账户转账到银行卡";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(22600);
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			Account descacc=AccountFactory.getAccount(Account.ACCOUNT_BANK,transorder.getOrder().getMobileNum());
			InvestmentAccount sourceacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			ValidateUtil.assertNull(descacc, "银行帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");

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
	 * 投资帐户可用余额转出（到银行卡）的冲正接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/transfer_to_bankCard_undo")
	@AccessRequered(methodName="投资帐户可用余额转出（到银行卡）的冲正")
	public @ResponseBody Object antiTrans2Bankcard(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"从投资账户转账5000元到银行卡（卡号6211 2345 5678 1234）", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		//冲正参数本来是没有金额的需要从订单中拿到金额进行处理
		
		TransOrderVO transorder;
		ResultModel rm = new ResultModel();
		String jsonstr;
		try {
			jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_CZH);
		String messagebase = "从投资账户转账到银行卡冲正";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(22700);
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			Account sourceacc=AccountFactory.getAccount(Account.ACCOUNT_BANK,transorder.getOrder().getMobileNum());
			InvestmentAccount descacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "银行帐户");

			String ordertable = OrderFactory.getOrderTable(descacc);
			String oriorderid = transorder.getOrder().getOrderId();
			// 检查原订单号是否存在
			if(StringUtils.isBlank(oriorderid)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS;
			}
			Order oriorder = orderSrv.getOrder(ordertable, oriorderid);
//			oriaccount = accounts
			if(oriorder==null){
				throw new MaAccountException(23801,"原订单不存在");
			}
			//检查是否已经冲正过
			if (log.isDebugEnabled()) {
				log.debug(String.format("检查是否已冲正"));
			}
			OrderFactory.checkHadantiTrans2Bankcard(oriorderid,sourceacc,descacc);
			
//			List<Order> relaorder = orderSrv.getRelaOrder(ordertable, oriorderid);
//			//原订单由可用余额转出到银行卡
//			checkRelaOrder(relaorder,descacc,sourceacc);
			
			String oriorderaccountid = oriorder.getAccountId();
			//设置冲正的金额
			transorder.getOrder().setSum(Math.abs(oriorder.getSum()));//原转出订单应该是负数，由投资帐户流出
			//把原转出的订单号记录下来
			transorder.setOriginalOrderId(oriorderid);
			transorder.setOriginalTable(ordertable);
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
	 * 检查关联订单是否已被处理
	 * @param relaorder
	 * @param descacc
	 * @param sourceacc
	 */
	private void checkRelaOrder(List<Order> relaorder,
			InvestmentAccount descacc, Account sourceacc) {
		for (Iterator iterator = relaorder.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			checkRelaOrder(order,descacc,sourceacc);
		}
		
	}
	/**
	 * 检查关联订单是否已被处理
	 * @param order
	 * @param descacc
	 * @param sourceacc
	 */
	private void checkRelaOrder(Order order, InvestmentAccount descacc,
			Account sourceacc) {
//		order.getAccountId()
	}
	
	
	
	
	
	
	/**
	 * 投资帐户可用余额转出（到银行卡）的冲正接口
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/freeze_yyd")
	@AccessRequered(methodName="从投资账户冻结标的资金")
	public @ResponseBody Object freezeYYD(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"从投资账户转账5000元到银行卡（卡号6211 2345 5678 1234）", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		//冲正参数本来是没有金额的需要从订单中拿到金额进行处理
		
		TransOrderVO transorder;
		ResultModel rm = new ResultModel();
		String jsonstr;
		try {
			jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_TOB);
		transorder.setOrderStatus(OrderConst.ORDER_STATUS_FROZEN);
		transorder.setStrictCheck(true);
		String messagebase = "从投资账户冻结标的资金";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(22800);
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			InvestmentAccount sourceacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			InvestmentAccount descacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_FREEZE);
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");
			
			//需要调整状态为 冻结
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
	 * 投资帐户标的金额解冻
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/unfreeze_yyd")
	@AccessRequered(methodName="从投资账户解冻标的资金")
	public @ResponseBody Object unfreezeYYD(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"从投资账户转账5000元到银行卡（卡号6211 2345 5678 1234）", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		//冲正参数本来是没有金额的需要从订单中拿到金额进行处理
		
		TransOrderVO transorder;
		ResultModel rm = new ResultModel();
		String jsonstr;
		try {
			jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_FEB);
		transorder.setOrderStatus(OrderConst.ORDER_STATUS_FROZEN);
		String messagebase = "从投资账户解冻标的资金";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(22900);
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			InvestmentAccount sourceacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			InvestmentAccount descacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_FREEZE);
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");
			
			String ordertable = OrderFactory.getOrderTable(descacc);
			String oriorderid = transorder.getOrder().getOrderId();
			// 检查原订单号是否存在
			if(StringUtils.isBlank(oriorderid)){
				throw ValidateException.ERROR_EXISTING_ORDERNO_NOT_EXISTS;
			}
			if (log.isDebugEnabled()) {
				log.debug(String.format("检查订单是否已解冻或使用"));
			}
			
			InvestmentAccountObjectOrder frozenObjectOrder = OrderFactory.checkAntiFreeze(oriorderid,ordertable,sourceacc,descacc);
			//设置解冻的金额
			transorder.getOrder().setSum(Math.abs(frozenObjectOrder.getSum()));//原转出订单应该是负数，由投资帐户流出
			//把原转出的订单号记录下来
			transorder.setOriginalOrderId(oriorderid);
			transorder.setOriginalTable(ordertable);
			transorder.getOrder().setProductName(((AbstractOrder)frozenObjectOrder).getProductName());
			//需要调整状态为 冻结
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
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.controller.AccountBaseController#getAccountType()
	 */
	@Override
	protected String getAccountType() {
		return Account.ACCOUNT_INVESTMENT;
	}
	
	/**
	 * 查询标的订单记录
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/queryObjectOrderListByUser")
	@AccessRequered(methodName="查询投资帐户余额订单记录")
	public @ResponseBody Object queryObjectOrderListByUser(HttpServletRequest request) {
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
		
		String messagebase = "查询余额订单记录";
		rm.setBaseErrorCode(23000);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			Map filter =new HashMap();
			filter.put("accountCode", this.getAccountType());
			filter.put("accountadditional", InvestmentAccount.SUM_TARGET_TYPE_OBJECT);
			filter.put("appId", orderqueryvo.getApp().getAppId());
			filter.put("mobileNum", orderqueryvo.getQuery().getMobileNum());
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getEndTime())){
				filter.put("endTime", DateUtil.toDayEnd(orderqueryvo.getQuery().getEndTime()));
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getStartTime())){
				filter.put("startTime", DateUtil.toDayStart(orderqueryvo.getQuery().getStartTime()));
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
	 * 查询余额订单记录
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/queryRemainingOrderListByUser")
	@AccessRequered(methodName="查询投资帐户余额订单记录")
	public @ResponseBody Object queryRemainingOrderListByUser(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
//		    "query":
//		        {"mobileNum":"13912345678","startDate":"2015-01-01","endDate":"2015-01-02" ,"pageSize":"10","pageIndex":"1"}
//		}  
		RemainingOrderQueryVO orderqueryvo;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			orderqueryvo = RequestUtil.convertJson2Obj(jsonstr, RemainingOrderQueryVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		
		String messagebase = "查询余额订单记录";
		rm.setBaseErrorCode(23100);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			Map filter =new HashMap();
			filter.put("accountCode", this.getAccountType());
			filter.put("accountadditional", InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			filter.put("appId", orderqueryvo.getApp().getAppId());
			filter.put("mobileNum", orderqueryvo.getQuery().getMobileNum());
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getEndTime())){
				filter.put("endTime", DateUtil.toDayEnd(orderqueryvo.getQuery().getEndTime()));
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getStartTime())){
				filter.put("startTime", DateUtil.toDayStart(orderqueryvo.getQuery().getStartTime()));
			}
			if(!StringUtils.isBlank( orderqueryvo.getQuery().getType())){
				
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getType())){
				
				filter.put("type", orderqueryvo.getQuery().getType());
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getPeerAccountType())){
				filter.put("peerAccountType", orderqueryvo.getQuery().getPeerAccountType());
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getFlowDirection())){
				filter.put("flowDirection", orderqueryvo.getQuery().getFlowDirection());
			}
			Pagingnation pagingnation = orderqueryvo.getQuery().toPagingnation();
			List<OrderdetailOutputBaseVO> orders = orderSrv.queryOrder(pagingnation, filter);
			long sum=investSrv.statBillingSum(filter);
			rm.put("pageSize", pagingnation.getPageSize());
			rm.put("pageIndex", pagingnation.getCurrPage());
			rm.put("total", pagingnation.getTotalCount());
			rm.put("sum", sum);
			rm.put("list", orders);
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
	 * 查询转账和充值记录接口 
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/queryRemainingFlowIn")
	@AccessRequered(methodName="查询转账和充值记录接口 ")
	public @ResponseBody Object queryRemainingFlowIn(HttpServletRequest request) {
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
		
		String messagebase = "查询余额订单记录";
		rm.setBaseErrorCode(23200);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			Map filter =new HashMap();
			filter.put("accountCode", this.getAccountType());
			filter.put("accountadditional", InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			filter.put("appId", orderqueryvo.getApp().getAppId());
			filter.put("mobileNum", orderqueryvo.getQuery().getMobileNum());
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getEndTime())){
				filter.put("endTime", DateUtil.toDayEnd(orderqueryvo.getQuery().getEndTime()));
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getStartTime())){
				filter.put("startTime", DateUtil.toDayStart(orderqueryvo.getQuery().getStartTime()));
			}
//			filter.put("type", orderqueryvo.getQuery().getType());
//			filter.put("peerAccountType", orderqueryvo.getQuery().getPeerAccountType());
//			filter.put("flowDirection", orderqueryvo.getQuery().getFlowDirection());
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
	 * 提现申请查询
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/query_withdrawalApplication")
	@AccessRequered(methodName="提现申请查询")
	public @ResponseBody Object queryWithdrawOrderListByUser(HttpServletRequest request) {
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
		
		String messagebase = "提现申请查询";
		rm.setBaseErrorCode(23300);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			Map filter =new HashMap();
			filter.put("accountCode", this.getAccountType());
			filter.put("accountadditional", InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			filter.put("appId", orderqueryvo.getApp().getAppId());
			filter.put("mobileNum", orderqueryvo.getQuery().getMobileNum());
//			filter.put("", value)//提现过滤条件
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getEndDate())){
				filter.put("endDate", DateUtil.toDayEnd(orderqueryvo.getQuery().getEndDate()));
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getStartDate())){
				filter.put("startDate", DateUtil.toDayStart(orderqueryvo.getQuery().getStartDate()));
			}
			Pagingnation pagingnation = orderqueryvo.getQuery().toPagingnation();
			List<OrderWithdrawDetailOutputVO> orders = orderSrv.queryWithdrawOrder(pagingnation, filter);
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
	 * 线下充值查询
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/query_offlineRecharge")
	@AccessRequered(methodName="线下充值查询")
	public @ResponseBody Object queryOfflineRechargeOrderListByUser(HttpServletRequest request) {
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
		
		String messagebase = "线下充值查询";
		rm.setBaseErrorCode(23400);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			Map filter =new HashMap();
			filter.put("appId", orderqueryvo.getApp().getAppId());
			filter.put("mobileNum", orderqueryvo.getQuery().getMobileNum());
//			filter.put("", value)//提现过滤条件
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getEndDate())){
				filter.put("endDate", DateUtil.toDayEnd(orderqueryvo.getQuery().getEndDate()));
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getStartDate())){
				filter.put("startDate", DateUtil.toDayStart(orderqueryvo.getQuery().getStartDate()));
			}
			
			
			Pagingnation pagingnation = orderqueryvo.getQuery().toPagingnation();
			List<OfflineRechargeOutputVO> orders = orderSrv.queryOfflineRecharge(pagingnation, filter);
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
			//记录日志
		}
		
		return rm;
		
	}
	
	/**
	 * 线上/线下充值查询
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/query_recharge")
	@AccessRequered(methodName="线上/线下充值查询")
	public @ResponseBody Object queryRechargeOrderListByUser(HttpServletRequest request) {
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
		
		String messagebase = "线下充值查询";
		rm.setBaseErrorCode(23500);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			Map filter =new HashMap();
			filter.put("appId", orderqueryvo.getApp().getAppId());
			filter.put("mobileNum", orderqueryvo.getQuery().getMobileNum());
//			filter.put("", value)//提现过滤条件
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getEndDate())){
				filter.put("endDate", DateUtil.toDayEnd(orderqueryvo.getQuery().getEndDate()));
			}
			if(StringUtils.isNotBlank(orderqueryvo.getQuery().getStartDate())){
				filter.put("startDate", DateUtil.toDayStart(orderqueryvo.getQuery().getStartDate()));
			}
			Pagingnation pagingnation = orderqueryvo.getQuery().toPagingnation();
			List<RechargeOutputVO> orders = orderSrv.queryRecharge(pagingnation, filter);
			rm.put("pageSize", pagingnation.getPageSize());
			rm.put("pageIndex", pagingnation.getCurrPage());
			rm.put("total", pagingnation.getTotalCount());
			
			//在线充值总额
			long onlineRechargeAmount=investSrv.onlineRechargeAmount(filter);
			//线下充值总额的统计
			long offlineRechargeAmount=investSrv.offlineRechargeAmount(filter);
			//线下充值总额的统计
			long totalRechargeAmount=onlineRechargeAmount+offlineRechargeAmount;
			rm.put("onlineRechargeAmount",onlineRechargeAmount);
			rm.put("offlineRechargeAmount",offlineRechargeAmount);
			rm.put("totalRechargeAmount",totalRechargeAmount);
			
			
			rm.put("list", orders);
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
	 * 推荐收益（到投资账户）
	 * @return
	 */
	@RequestMapping("/recommended_income")
	@AccessRequered(methodName="推荐收益（到投资账户）接口")
	public @ResponseBody Object transtoInverstmentAccount(HttpServletRequest request) {
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
		transorder.setOperationType(OrderConst.ORDER_OPERATION_RECOMMEND);
		String messagebase = "推荐收益（到投资账户）";
		rm.setBaseErrorCode(24100);
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			//备注字段必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			InvestmentAccount descacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			Account sourceacc= AccountFactory.getAccount(Account.ACCOUNT_BANK,transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "银行卡帐户");
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
	 * 投资帐户自动投标接口,用于与有油贷签订协议后,由有油贷为客户进行投标
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/auto_freeze_yyd")
	@AccessRequered(methodName="投资帐户自动投标")
	public @ResponseBody Object autoFreezeYYD(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},  
//		    "order":
//		        {"mobileNum":"13912345678","userToken":"","sum":500000,"productName":"","remark":"从投资账户转账5000元到银行卡（卡号6211 2345 5678 1234）", "appOrderId":"AO201412122344888444"},
//		    "TSIG":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE", "timeStamp":"TIMESTAMP","nonce":"NONCE"}
//		} 
		//冲正参数本来是没有金额的需要从订单中拿到金额进行处理
		
		TransOrderVO transorder;
		ResultModel rm = new ResultModel();
		String jsonstr;
		try {
			jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, TransOrderVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		transorder.setOperationType(OrderConst.ORDER_OPERATION_TOB);
		transorder.setOrderStatus(OrderConst.ORDER_STATUS_FROZEN);
		transorder.setStrictCheck(true);
		String messagebase = "投资帐户自动投标";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(28300);
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			InvestmentAccount sourceacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			InvestmentAccount descacc=(InvestmentAccount) getAccount(transorder.getOrder().getMobileNum());
			sourceacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_FREEZE);
			ValidateUtil.assertNull(descacc, "投资帐户");
			ValidateUtil.assertNull(sourceacc, "投资帐户");
			
			//需要调整状态为 冻结
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
	
	
	
	public static void main(String[] args) throws RequestException {
//		String data = "{  \"amount\" : 300,  \"alias\" : \"971\",  \"sellerOrderId\" : \"11722111732\",  \"billingType\" : \"AS\",  \"orderid\" : \"UD002015032514534200000354800771\",  \"payTime\" : \"2015-03-25 14:53:43\",  \"orderId\" : \"UD002015032514534200000354800771\",  \"mobile\" : null,  \"productId\" : \"BS01001\"}";
//		System.out.println(new HttpRequester().postRequest("http://183.232.65.122:8080/bs_buss/mobile/fengniao/12114000235629.jsp", data));
//		System.out.println(Md5Util.Encrypt("123456"));
		
	}
	
}
