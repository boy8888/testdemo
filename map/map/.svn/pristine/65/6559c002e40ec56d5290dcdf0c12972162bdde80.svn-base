package com.hummingbird.maaccount.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.HuitongCardAccount;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Receipt;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.util.OrderFactory;
import com.hummingbird.maaccount.vo.TransOrderVO;

/**
 * 现金帐户相关controller
 * 
 * @author huangjiej_2 2014年11月10日 下午11:42:07
 */
@Controller
@RequestMapping("/huitongCard")
public class HuitongAccountController extends AccountBaseController {

	/**
	 * 汇通卡转帐至现金帐户
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/transfer_to_cashAccount")
	@AccessRequered(methodName="从汇通卡转账到现金账户")
	public @ResponseBody Object trans2cash(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
//		    "order":
//		        {"mobileNum":"13912345678","sum":500000,"remark":"从汇通卡转账5000元到现金账户（汇通卡号6211 2345 5678 1234）", "appOrderId":"AO201412122344888444"},
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
		String messagebase = "从汇通卡转账到现金账户";
		rm.setBaseErrorCode(25100);
		rm.setErrmsg(messagebase+"成功");
		try {
			prepare(transorder,request);
			//备注字段和app自定义订单号必填
			ValidateUtil.assertEmpty(transorder.getOrder().getRemark(), "备注");
			ValidateUtil.assertEmpty(transorder.getOrder().getAppOrderId(), "app自定义订单号");
			Account descacc= AccountFactory.getAccount(Account.ACCOUNT_CASH,transorder.getOrder().getMobileNum());
			Account sourceacc= getAccount(transorder.getOrder().getMobileNum());
			ValidateUtil.assertNull(descacc, "现金帐户");
			ValidateUtil.assertNull(sourceacc, "汇通卡帐户");
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
	 * 根据手机号码获取本类指定的帐户信息
	 * @param mobile
	 * @return
	 * @throws MaAccountException
	 */
	protected  Account getAccount(String mobile) throws MaAccountException{
		return new HuitongCardAccount();
	}
	

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.controller.AccountBaseController#getAccountType()
	 */
	@Override
	protected String getAccountType() {
		return Account.ACCOUNT_HUITONGCARD;
	}
	
//	/**
//	 * 获取任务
//	 * @param getsmsvo
//	 * @return
//	 */
//	@RequestMapping("/get_task")
//	@AccessRequered(methodName="手机app（肉鸡）获取任务")
//	public @ResponseBody Object getTask(@CookieValue("mobileNum") String mobileNum) {
////		{"mobileNum":"13912345678"}
//		if(log.isDebugEnabled()){
//			log.debug("获取任务的手机号码为："+mobileNum);
//		}
//		//捕捉所有异常,不要由于有异常而不返回信息
//		ResultModel rm = new ResultModel();
//		rm.setErrmsg("获取任务成功");
//		try {
//			ValidateUtil.validateMobile(mobileNum);
//			//校验手机号码是否在肉鸡列表中
//			Rouji rj = roujiDao.selectByPrimaryKey(mobileNum);
//			if(rj==null){
//				throw new ValidateException(ValidateException.ERRCODE_MOBILE_INVALID, "手机号码不存在，非法用户访问");
//			}
//			if(log.isDebugEnabled()){
//				log.debug("检验通过，获取请求");
//			}
////			Task qt = new Task();
////			qt.setIssueId(Integer.parseInt("123"));
////			qt.setStatus(TaskStatusConst.TASK_STATUS_CREATE);
////			List<Task> tasks = taskMapper.selectUnsendTask(qt);
////			tasks=tasks.subList(0, 2);
//			List<ITask> tasks = smstaskSrv.getTasks(rj);
//			SmsTaskVo out = new SmsTaskVo(tasks);
//			if (log.isTraceEnabled()) {
//				log.trace(String.format("输出任务内容%s", new JSONObject(out).toString()));
//			}
//			
//			return out;
//		} catch (Exception e1) {
//			log.error(String.format("获取任务处理失败"),e1);
//			rm.mergeException(e1);
//			return rm;
//		}
//		finally{
//			
//		}
//	}
//	
//	/**
//	 * 上报短信发送任务执行
//	 * @param getsmsvo
//	 * @return
//	 */
//	@RequestMapping("/update_task_status")
//	@AccessRequered(methodName="上报短信发送任务执行情况")
//	public @ResponseBody Object updateTaskStatus(@CookieValue("mobileNum") String mobileNum,@RequestBody TaskFeeBackVO taskFeeBackVO) {
//		if(log.isDebugEnabled()){
//			log.debug("上报短信发送任务执行："+taskFeeBackVO+",app手机号码为"+mobileNum);
//		}
//		//捕捉所有异常,不要由于有异常而不返回信息
//		BatchResultModel brm = new BatchResultModel();
//		brm.setErrmsg("上报短信任务执行信息成功");
//		brm.setCountName("updatecount");
//		
//		try {
//			//检验手机
//			ValidateUtil.validateMobile(mobileNum);
//			//校验手机号码是否在肉鸡列表中
//			Rouji rj = roujiDao.selectByPrimaryKey(mobileNum);
//			if(rj==null){
//				throw new ValidateException(2004, "手机号码不存在，非法用户访问");
//			}
//			
//			List errortask = smstaskSrv.updateTaskStatus(mobileNum,taskFeeBackVO);
//			for (Iterator iterator = errortask.iterator(); iterator
//					.hasNext();) {
//				ResultModel rm = (ResultModel) iterator.next();
//				if(!rm.isSuccessed()){
//					brm.addResultModel(rm);
//				}
//			}
//		} catch (Exception e1) {
//			log.error(String.format("上报短信任务执行信息出错[%s]",taskFeeBackVO),e1);
//			brm.mergeException(e1);
//			brm.setErrcode(1040);
//			//brm.setErr(1040,"上报短信任务执行信息失败，其它原因");
//		}
//		finally{
//			return brm;
//		}
//	}
//	
//	/**
//	 * 生成短链
//	 * @param getsmsvo
//	 * @return
//	 */
//	@RequestMapping("/get_short_link")
//	@AccessRequered(methodName="生成短链")
//	public @ResponseBody Object getShortLink(@RequestBody HashMap params) {
//		
//		String roujimobile= ObjectUtils.toString(params.get("roujimobile"));
//		String mobilenum= ObjectUtils.toString(params.get("mobilenum"));
//		String issueId = ObjectUtils.toString(params.get("issueId"));
//		//捕捉所有异常,不要由于有异常而不返回信息
//		ResultModel brm = new ResultModel();
//		brm.setErrmsg("生成短链成功");
//		try {
//			String painttext = roujimobile+mobilenum+issueId;
//			brm.put("明文数据", painttext);
//			if (log.isTraceEnabled()) {
//				log.trace(String.format("明文数据%s", painttext));
//			}
//			String genShortUrl = ShortUrlGenerator.genShortUrl(painttext);
//			brm.put("短链suffer", genShortUrl);
//		} catch (Exception e1) {
//			log.error(String.format("生成短链出错"),e1);
//			brm.mergeException(e1);
//			brm.setErrcode(1040);
//			//brm.setErr(1040,"上报短信任务执行信息失败，其它原因");
//		}
//		finally{
//			return brm;
//		}
//	}
}
