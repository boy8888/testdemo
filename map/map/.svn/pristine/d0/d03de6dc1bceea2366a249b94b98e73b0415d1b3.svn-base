package com.hummingbird.maaccount.controller;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.event.RequestEvent;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.ValidateResult;
import com.hummingbird.commonbiz.vo.BaseTransVO;
import com.hummingbird.maaccount.entity.AppInfo;
import com.hummingbird.maaccount.entity.InvestmentAccount;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.mapper.AppInfoMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.AppInfoService;
import com.hummingbird.maaccount.service.QueryUserCardService;
import com.hummingbird.maaccount.util.AccountFactory;
import com.hummingbird.maaccount.vo.OrderQueryVO;
import com.hummingbird.maaccount.vo.QueryUserCardBodyVO;
import com.hummingbird.maaccount.vo.QueryUserCardListBodyVO;
import com.hummingbird.maaccount.vo.QueryUserCardListDetailVO;
import com.hummingbird.maaccount.vo.QueryUserCardListResultVO;

/**
 * 我的系列，帐户相关信息
 * 
 * @author huangjiej_2 2014年11月10日 下午11:42:07
 */
@Controller
@RequestMapping("/my")
public class MyController extends BaseController {

	@Autowired(required = true)
	private UserMapper userDao;
	@Autowired(required = true)
	protected AppInfoService appService;
	@Autowired
	AppInfoMapper appdao;
	@Autowired(required = true)
	protected QueryUserCardService queryUserCardSer;


	
	/**
	 * 查询用户在营销账户平台上的资产、红包、积分
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/queryUserAccountList")
	@AccessRequered(methodName="查询用户在营销账户平台上的资产、红包、积分")
	public @ResponseBody Object queryUserAccountList(HttpServletRequest request) {
//		{
//		    "app":
//		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
//		    "query":{
//		        "mobileNum":"13912345678"
//		    }
//		}
		
		OrderQueryVO orderqueryvo;
		ResultModel rm = new ResultModel();
		String messagebase = "查询我的明细";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(24000);
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			orderqueryvo = RequestUtil.convertJson2Obj(jsonstr, OrderQueryVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		try{
			JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
			String mobileNum = orderqueryvo.getQuery().getMobileNum();
			ValidateUtil.validateMobile(mobileNum);
			User user = userDao.selectByMobile(mobileNum);
			if(user==null)
			{
				rm.setErrmsg("手机号码并未在系统中注册");
				return rm;
			}
			//加载现金帐户
			String cashaccountCode = Account.ACCOUNT_CASH;
			Account cashaccount = AccountFactory.getAccount(cashaccountCode, mobileNum);
			Map cashaccountmap = new HashMap();
			cashaccountmap.put("accountId", cashaccount.getAccountId());
			cashaccountmap.put("sum", cashaccount.getBalance());
			rm.put("cashAccount", cashaccountmap);
			//加载投资帐户
			String inveaccountCode = Account.ACCOUNT_INVESTMENT;
			InvestmentAccount inveaccount = (InvestmentAccount) AccountFactory.getAccount(inveaccountCode,mobileNum);
			Map inveaccountmap = new HashMap();
			inveaccountmap.put("accountId", inveaccount.getAccountId());
			inveaccountmap.put("objectSum", inveaccount.getObjectsum());
			inveaccountmap.put("remainingSum", inveaccount.getRemainingsum());
			inveaccountmap.put("frozenSum", inveaccount.getFrozensum());
			inveaccountmap.put("total", inveaccount.getBalance());
//			investmentAccount.onlineRechargeAmount（在线充值总额）：
//			即银行卡转入投资账户总额，统计属于该用户的t_investment_account_remaining_order.method 为 /bankCard/transfer_to_investmentAccount 的记录的 sum 字段值的总和。
			Number onlineRechargeAmount = jdbc.queryForObject("select IFNULL(sum(abs(`sum`)),0) onlineRechargeAmount from t_investment_account_remaining_order where method='/bankCard/transfer_to_investmentAccount' and accountId=?",new Object[]{inveaccount.getAccountId()}, Number.class);
			inveaccountmap.put("onlineRechargeAmount",onlineRechargeAmount.longValue());
//			investmentAccount.offlineRechargeAmount（线下充值总额）：
//			统计属于该用户的 t_investment_account_remaining_order.method 为 /investmentAccount/offlineRecharge_success 的记录的 sum 字段值的总和。
			Number offlineRechargeAmount = jdbc.queryForObject("select IFNULL(sum(abs(`sum`)),0) onlineRechargeAmount from t_investment_account_remaining_order where method='/investmentAccount/offlineRecharge_success' and accountId=?",new Object[]{inveaccount.getAccountId()}, Number.class);
			inveaccountmap.put("offlineRechargeAmount",offlineRechargeAmount.longValue());
//			investmentAccount.rechargeAmount（充值总额）：
//			即该用户的在线充值总额和线下充值总额的和
			inveaccountmap.put("rechargeAmount",offlineRechargeAmount.longValue()+onlineRechargeAmount.longValue());
//			investmentAccount.frozenWithdrawAmount（提现冻结总额）
//			统计属于该用户的 t_investment_account_remaining_order.method 为 /investmentAccount/withdraw_to_freeze、/investmentAccount/withdraw_success、/investmentAccount/withdraw_to_unfreeze的记录的sum字段值的总和。
			Number frozenWithdrawAmount = jdbc.queryForObject("select IFNULL(abs(sum(if(method='/investmentAccount/withdraw_success',abs(`sum`),`sum`))),0) from t_investment_account_remaining_order where method in ('/investmentAccount/withdraw_to_freeze','/investmentAccount/withdraw_success','/investmentAccount/withdraw_to_unfreeze') and accountId=?",new Object[]{inveaccount.getAccountId()}, Number.class);
			inveaccountmap.put("frozenWithdrawAmount",frozenWithdrawAmount.longValue());
//			investmentAccount.withdrawAmount（提现总额）
//			统计属于该用户的 t_investment_account_remaining_order.method 为 /investmentAccount/withdraw_success 的记录的sum字段值的总和。
			Number withdrawAmount = jdbc.queryForObject("select IFNULL(sum(abs(`sum`)),0) from t_investment_account_remaining_order where method in ('/investmentAccount/withdraw_success') and accountId=?",new Object[]{inveaccount.getAccountId()}, Number.class);
			inveaccountmap.put("withdrawAmount",withdrawAmount.longValue());
//			investmentAccount.flowDirection_in（转入总额）：
//			统计属于该用户的 t_investment_account_remaining_order.flowDirection = IN# 的记录的 sum 字段值总和。对于外部进入投资帐户的都算入站
			Number flowDirection_in = jdbc.queryForObject("select sum(sum1) from (select ifnull(sum(`sum`),0) sum1 from  t_investment_account_remaining_order where flowDirection = 'IN#' and method in ('/bankCard/transfer_to_investmentAccount','/investmentAccount/offlineRecharge_success','/investmentAccount/payback_interest','/investmentAccount/payback_capital','/investmentAccount/unfreeze_yyd') and accountid=? union all select ifnull(sum(`sum`),0) sum1 from  t_investment_account_object_order where  method in ('/bankCard/pay_yyd','/cashAccount/pay_yyd') and accountid=?) a",new Object[]{inveaccount.getAccountId(),inveaccount.getAccountId()}, Number.class);
			inveaccountmap.put("flowDirection_in",flowDirection_in.longValue());
//			investmentAccount.flowDirection_out（转出总额）
//			统计属于该用户的 t_investment_account_remaining_order.flowDirection = OUT 的记录的 sum 字段值总和。
			Number flowDirection_out = jdbc.queryForObject("select ifnull(abs(sum(`sum`)),0) from  t_investment_account_remaining_order where  method in ('/investmentAccount/transfer_to_cashAccount','/investmentAccount/withdraw_success','/investmentAccount/transfer_to_bankCard','/investmentAccount/transfer_to_bankCard_undo','/investmentAccount/freeze_yyd') and accountid=?",new Object[]{inveaccount.getAccountId()}, Number.class);
			inveaccountmap.put("flowDirection_out",flowDirection_out.longValue());
//			investmentAccount.frozenObject（投标冻结金额）：
//			即该用户的t_investment_account.frozenSum。
//			统计属于该用户的 t_investment_account_object_order.method 为/investmentAccount/freeze_yyd、 /investmentAccount/unfreeze_yyd 和 /investmentAccount/pay_yyd 记录的sum字段值的总和。
			//从标的订单进行统计，投标成功，是标的的状态是ok，如果投标冻结了和投标撤销，状态是FRZ
			Number frozenObject = jdbc.queryForObject("select IFNULL(abs(sum(`sum`)),0) from t_investment_account_object_order where method in ('/investmentAccount/freeze_yyd','/investmentAccount/unfreeze_yyd') and status='FRZ' and accountId=?",new Object[]{inveaccount.getAccountId()}, Number.class);
			inveaccountmap.put("frozenObject",frozenObject.longValue());
			rm.put("inventmentAccount", inveaccountmap);
			Map totalAssets = new HashMap();
			totalAssets.put("total", cashaccount.getBalance()+inveaccount.getBalance());
			rm.put("totalAssets", totalAssets);
			rm.setErrmsg("成功");
		}
		catch(Exception e){
			log.error("查看我的帐户失败",e);
			//rm.setErr(0,"查询我的明细失败，其它错误");
			rm.mergeException(e);
		}
		return rm;
	}
	/**
	 * @author liudou
	 * @date 2015/7/23
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryUserCardList")
	@AccessRequered(methodName="查询我的卡")
	public @ResponseBody Object queryUserCardList(HttpServletRequest request) {
		/*{
		    "app":
		        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
		    "body":{
		        "pageSize":10,"pageIndex":1,
		        "mobileNum":"13912345678",
		        "startDate":"2015-07-01",
		        "endDate":"2015-07-10",
		        "types":["VCA","DCA","OCA"],
		        "status":["END","OK#","NOP","FRZ"],
		        "channelNo":"渠道",
		        "queryCardSource":true
		    }
		}*/
		QueryUserCardListBodyVO transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, QueryUserCardListBodyVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		
		String messagebase = "查询我的卡";
		rm.setBaseErrorCode(24200);
		rm.setErrmsg(messagebase+"成功");
		String accountId =null;
		try {
			
			//检查appid是否在数据中存在
			ValidateResult validate = appService.validate(transorder.getApp());
			
		
			ValidateUtil.validateMobile(transorder.getBody().getMobileNum());
			QueryUserCardListDetailVO query=transorder.getBody();
			User user= userDao.selectByMobile(query.getMobileNum());
			if(user==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("手机号码%s并没有注册",query.getMobileNum()));
				}
				throw ValidateException.ERROR_EXISTING_USER_NOT_EXISTS.clone(null, "用户不存在");
			}
			
			Pagingnation pagingnation = query.toPagingnation();
			List<String> typelist = query.getTypes();
			if(typelist==null||typelist.isEmpty()){
				query.setTypes(Arrays.asList("VCA","OCA","DCA"));
			}
		
			List<QueryUserCardListResultVO> querylist=queryUserCardSer.queryUserCard(query, pagingnation);
			int begin=(query.getPageIndex()-1)*query.getPageSize();
			int end=query.getPageIndex()*query.getPageSize();
			List<QueryUserCardListResultVO> list=null;
			if(querylist.size()>=end){
				list=querylist.subList(begin,end);
			} else if(querylist.size()>begin&&querylist.size()<end){
				list=new ArrayList<QueryUserCardListResultVO>();
				list=querylist.subList(begin,querylist.size());
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
		
		
		return rm;
	}
	
	/**
	 * @author john
	 * @date 2015/7/23
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryUserCard")
	@AccessRequered(methodName = "查询我的卡详情", isJson = true, codebase = 295000, className = "com.hummingbird.commonbiz.vo.BaseTransVO", genericClassName = "com.hummingbird.maaccount.vo.QueryUserCardBodyVO", appLog = true)
	public @ResponseBody Object queryUserCard(HttpServletRequest request) {
		ResultModel rm = super.getResultModel();
		BaseTransVO<QueryUserCardBodyVO> transorder = (BaseTransVO<QueryUserCardBodyVO>) super.getParameterObject();
		String messagebase = "查询我的卡详情"; 
		RequestEvent qe=null ; 
		String accountId =null;
		try {
			ValidateUtil.assertNullnoappend(transorder.getBody().getAccountId(), "卡账号为空");
			
			//检查appid是否在数据中存在
			ValidateResult vr = new ValidateResult();
			ValidateUtil.assertNull(transorder.getApp().getAppId(), "appId为空",ValidateException.ERROR_EXISTING_APP_NOT_EXISTS.getErrcode());
			AppInfo app = appdao.selectByPrimaryKey(transorder.getApp().getAppId());
			ValidateUtil.assertNull(app, "APP不存在",ValidateException.ERROR_EXISTING_APP_NOT_EXISTS.getErrcode());
			if(!"OK#".equals(app.getStatus())){
				throw ValidateException.ERROR_APP_OFFLINE;
			}
			transorder.getApp().setAppKey(app.getAppKey());
			transorder.getApp().setAppname(app.getAppname());
			vr.setValidateObj(app);
			
			QueryUserCardBodyVO query=transorder.getBody();
			QueryUserCardListResultVO result = queryUserCardSer.queryUserCardDetail(query);
			rm.put("result", result);
			
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
			
		}
		
		return rm;
	}

}
