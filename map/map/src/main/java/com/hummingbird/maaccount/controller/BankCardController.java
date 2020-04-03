package com.hummingbird.maaccount.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.vo.OrderDetailOutputVO;
import com.hummingbird.maaccount.vo.OrderQueryVO;
import com.hummingbird.maaccount.vo.TransOrderVO;

/**
 * 现金帐户相关controller
 * 
 * @author huangjiej_2 2014年11月10日 下午11:42:07
 */
@Controller
@RequestMapping("/bankCard")
public class BankCardController extends AccountBaseController {


	/**
	 * 银行卡支付有油贷产品
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/pay_yyd")
	@AccessRequered(methodName="银行卡支付有油贷产品")
	public @ResponseBody Object payYYD(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
//		    "order":
//		        {"mobileNum":"13912345678","sum":500000,"productName":"有油贷5000元产品","remark":"在有油贷网站够爱5000元有油贷产品", "appOrderId":"AO201412122344888444"},
//		    "tsig":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE","timeStamp":"TIMESTAMP","nonce":"NONCE"}
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
		String messagebase = "从银行卡购买有油贷产品";
		rm.setBaseErrorCode(23700);
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			InvestmentAccount descacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_OBJECT);
			Account sourceacc= getAccount(transorder.getOrder().getMobileNum());
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
	 * 银行卡转账到投资账户
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/transfer_to_investmentAccount")
	@AccessRequered(methodName="银行卡转账到投资账户")
	public @ResponseBody Object transtoInverstmentAccount(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
//		    "order":
//		        {"mobileNum":"13912345678","sum":500000,"productName":"有油贷5000元产品","remark":"在有油贷网站够爱5000元有油贷产品", "appOrderId":"AO201412122344888444"},
//		    "tsig":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE","timeStamp":"TIMESTAMP","nonce":"NONCE"}
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
		String messagebase = "银行卡转账到投资账户";
		rm.setBaseErrorCode(23800);
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			InvestmentAccount descacc=(InvestmentAccount) AccountFactory.getAccount(Account.ACCOUNT_INVESTMENT,transorder.getOrder().getMobileNum());
			descacc.setSumTarget(InvestmentAccount.SUM_TARGET_TYPE_REMAINING);
			Account sourceacc= getAccount(transorder.getOrder().getMobileNum());
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
	 * 银行卡转账到投资账户
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/transfer_to_cashAccount")
	@AccessRequered(methodName="银行卡转账到现金账户")
	public @ResponseBody Object transtoCashAccount(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
//		    "order":
//		        {"mobileNum":"13912345678","sum":500000,"productName":"有油贷5000元产品","remark":"在有油贷网站够爱5000元有油贷产品", "appOrderId":"AO201412122344888444"},
//		    "tsig":
//		        {"orderMD5":"ORDERMD5","signature":"SIGNATURE","timeStamp":"TIMESTAMP","nonce":"NONCE"}
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
		String messagebase = "银行卡转账到现金账户";
		rm.setBaseErrorCode(23900);
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			CashAccount descacc=(CashAccount) AccountFactory.getAccount(Account.ACCOUNT_CASH,transorder.getOrder().getMobileNum());
			Account sourceacc= getAccount(transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "现金帐户");
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
	
	

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.controller.AccountBaseController#getAccountType()
	 */
	@Override
	protected String getAccountType() {
		return Account.ACCOUNT_BANK;
	}
	
}
