package com.hummingbird.maaccount.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.constant.OrderConst;
import com.hummingbird.maaccount.entity.FactoryTask;
import com.hummingbird.maaccount.entity.InvestmentAccountRemainingOrder;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.mapper.InvestmentAccountRemainingOrderMapper;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.service.OrderService;
import com.hummingbird.maaccount.service.impl.AccountIdServiceImpl;
import com.hummingbird.maaccount.service.impl.FactoryTaskService;
import com.hummingbird.maaccount.util.PospEncyptUtil;
import com.hummingbird.maaccount.vo.FactoryTaskResult;
import com.hummingbird.maaccount.vo.OrderQueryVO;

/**
 * 管理相关的控制器
 * 
 * @author huangjiej_2 2014年11月10日 下午11:42:07
 */
@Controller
@RequestMapping("/console")
public class ConsoleController extends BaseController {

	@Autowired
	FactoryTaskService taskSrv;
	@Autowired
	AccountIdServiceImpl accountIdgenSrv;
	@Autowired
	OrderService orderSrv;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	UserMapper userDao;
	@Autowired
	InvestmentAccountRemainingOrderMapper irDao;


	
	/**
	 * 手工触发制卡任务
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/accountGenerationTrigger")
	@AccessRequered(methodName="手工触发制卡任务")
	public @ResponseBody Object accountGenerationTrigger(HttpServletRequest request) {
		
		OrderQueryVO orderqueryvo;
		ResultModel rm = new ResultModel();
		String messagebase = "手工触发制卡任务";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(29000);
		try{
			long count = 0;
			String message = "成功";
			int successtaskcount = 0;
			List<String> failtask = new ArrayList<String>();
			List<FactoryTask> tasks = taskSrv.selectTask4Gen();
			if (log.isDebugEnabled()) {
				log.debug(String.format("有%s项任务待处理",tasks.size()));
			}
			for (Iterator iterator = tasks.iterator(); iterator.hasNext();) {
				FactoryTask factoryTask = (FactoryTask) iterator.next();
				FactoryTaskResult result = taskSrv.generateAccounts(factoryTask);
				boolean issuccess = OrderConst.ORDER_STATUS_OK.equals(result.getStatus());
				if(issuccess){
					count+=result.getAmount();
					successtaskcount ++;
				}
				else{
					failtask.add(result.getTaskName()+"("+factoryTask.getIdt_factory_task()+")");
				}
			}
			rm.put("制卡任务执行数", tasks.size());
			rm.put("制卡任务成功数", successtaskcount);
			rm.put("制卡任务总制卡数", count);
			rm.put("制卡失败任务", failtask);
			rm.setErrmsg("成功");
		}
		catch(Exception e){
			log.error("手工触发制卡任务",e);
			//rm.setErr(0,"查询我的明细失败，其它错误");
			rm.mergeException(e);
		}
		return rm;
	}
	
	/**
	 * 手工触发制卡任务
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/convertDespaymentcode")
	@AccessRequered(methodName="把des支付密码进行修改")
	public @ResponseBody Object convertDespaymentcode(HttpServletRequest request) {
		
		OrderQueryVO orderqueryvo;
		ResultModel rm = new ResultModel();
		String messagebase = "把des支付密码进行修改";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(29100);
		try{
			
			List<User> userlist = userDao.select4Deschange();
			if (log.isDebugEnabled()) {
			
				log.debug(String.format("共有%s条记录",userlist.size()));
			}
			int count = 0;
			for (Iterator iterator = userlist.iterator(); iterator.hasNext();) {
				User user = (User) iterator.next();
				String decypt = PospEncyptUtil.decypt(user.getPaymentCodeDES(), user.getMobilenum());
				String paymentcodedesc = Md5Util.Encrypt(decypt);
				user.setPaymentCodeDES(paymentcodedesc);
				userDao.updateByPrimaryKey(user);
				count++;
			}

			rm.setErrmsg("成功处理"+count+"条记录");
		}
		catch(Exception e){
			log.error(messagebase+"出错",e);
			//rm.setErr(0,"查询我的明细失败，其它错误");
			rm.mergeException(e);
		}
		return rm;
	}
	
	@RequestMapping("/changeOldAccountIdToNewAccount")
	@AccessRequered(methodName="更换旧帐户到新帐户")
	public ResultModel changeOldAccountIdToNewAccount(){
		
		int[] updatecount;
		ResultModel rm = new ResultModel();
		StopWatch sw = new StopWatch();
		sw.start();
		try {
			String messagebase = "更换旧帐户到新帐户";
			rm.setErrmsg(messagebase);
			rm.setBaseErrorCode(29100);
			updatecount = changeOldAccountIdToNewAccountOneByOne();
			sw.stop();
			String msg = String.format("更换旧帐户到新帐户，现金帐户更新%s条(共%s条)，投资帐户更新%s条(共%s条)，用时%s秒",updatecount[2], updatecount[0],updatecount[3],updatecount[1],sw.getTime()/1000);
			rm.setErrmsg(msg);
			log.info(msg);
		} catch (Exception e) {
			log.error(String.format("更换旧帐户到新帐户失败"),e);
			rm.mergeException(e);
		}
		return rm;
	}
	
	@RequestMapping("/testAccountId")
	@AccessRequered(methodName="测试并发获取帐户")
	public ResultModel testGetAccountId(HttpServletRequest request){
		HashMap transorder;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, HashMap.class);
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
		String productId = ObjectUtils.toString(transorder.get("productId"),"2077");
		StopWatch sw = new StopWatch();
		sw.start();
		try {
			String messagebase = "测试并发获取帐户";
			rm.setErrmsg(messagebase);
			rm.setBaseErrorCode(29100);
			accountIdgenSrv.testGenAccountId(productId);
			sw.stop();
			String msg = String.format("%s,耗时%s秒",messagebase,sw.getTime()/1000);
			rm.setErrmsg(msg);
			log.info(msg);
		} catch (Exception e) {
			log.error(String.format("测试并发获取帐户"),e);
			rm.mergeException(e);
		}
		return rm;
	}
	
	public 
	int[] changeOldAccountIdToNewAccountOneByOne() throws  Exception
	{
		//旧帐户为非卡号帐户
		JdbcTemplate jdbc = SpringBeanUtil.getInstance().getBean(JdbcTemplate.class);
		int total=0;
		int totalcash=0;
		//查询现金帐户
		int updatecashcount = 0;
		List<Map<String, Object>> caseacclist = jdbc.queryForList("select accountid from t_cash_account where accountId not in (select accountid from t_factory_account_id where status='USD' and accounttype='CA#')");
		totalcash+=caseacclist.size();
		for (Iterator iterator = caseacclist.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			String accountid = ObjectUtils.toString(map.get("accountid"));
			try{
				orderSrv.changeOldAccountIdToNewAccount4cash(accountid);
				updatecashcount++;
			}
			catch(Exception e){
				log.error("更新帐户"+accountid+"到新帐户失败",e);
			}
		}
		//查询投资帐户
		int updateinvestcount=0;
		int totalinvest =0;
		List<Map<String, Object>> investacclist = jdbc.queryForList("select accountid from t_investment_account where accountId not in (select accountid from t_factory_account_id where status='USD' and accounttype='IA#')");
		totalinvest+=investacclist.size();
		for (Iterator iterator = investacclist.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			String accountid = ObjectUtils.toString(map.get("accountid"));
			try{
				orderSrv.changeOldAccountIdToNewAccount4investment(accountid);
				updateinvestcount++;
			}
			catch(Exception e){
				log.error("更新帐户"+accountid+"到新帐户失败",e);
			}
		}
		
		return new int[]{totalcash,totalinvest,updatecashcount,updateinvestcount};
		
	}
	
	/**
	 * 调整冻结进行投资时的资料错误
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/fix_freeze_yyd")
	@AccessRequered(methodName="调整冻结进行投资时的资料错误")
	public @ResponseBody Object fix_freeze_yyd(HttpServletRequest request) {
		
		String begintime = StringUtils.defaultIfEmpty(request.getParameter("begintime"),"2015-08-01");
		String endtime = StringUtils.defaultIfEmpty(request.getParameter("endtime"),"2015-08-01");
		
		
		OrderQueryVO orderqueryvo;
		ResultModel rm = new ResultModel();
		String messagebase = "调整冻结进行投资时的资料错误";
		rm.setErrmsg(messagebase+"成功");
		rm.setBaseErrorCode(29100);
		try{
			//查询冻结yyd的记录,并查找此记录之前的一条记录,判断这2条记录中,是否同时修改了2个余额的字段,如果是则进行调整,生成sql
			List<InvestmentAccountRemainingOrder> selectErrorFrozenYYD = irDao.selectErrorFrozenYYD(begintime,endtime);
			if (log.isDebugEnabled()) {
				log.debug(String.format("共有%s条记录可疑",selectErrorFrozenYYD.size()));
			}
			int count = 0;
			List list = new ArrayList<>();
			for (Iterator iterator = selectErrorFrozenYYD.iterator(); iterator.hasNext();) {
				InvestmentAccountRemainingOrder investmentAccountRemainingOrder = (InvestmentAccountRemainingOrder) iterator.next();
				//查询它的上一条的记录
				InvestmentAccountRemainingOrder earlierorder = irDao.selectErrorFrozenYYDsEarlier(investmentAccountRemainingOrder);
				
				if(earlierorder!=null){
					String fixsql = checkisError(investmentAccountRemainingOrder,earlierorder);
					if(fixsql!=null){
						list.add(fixsql);
					}
				}
			}

			rm.setErrmsg("成功处理"+count+"条记录");
			rm.put("sql", list);
		}
		catch(Exception e){
			log.error(messagebase+"出错",e);
			//rm.setErr(0,"查询我的明细失败，其它错误");
			rm.mergeException(e);
		}
		return rm;
	}

	/**
	 * 比较订单看是不是有错
	 * @param investmentAccountRemainingOrder
	 * @param earlierorder
	 * @return 
	 */
	private String checkisError(InvestmentAccountRemainingOrder frozenorder,
			InvestmentAccountRemainingOrder earlierorder) {
		if(Math.abs(frozenorder.getSum())+earlierorder.getObjectSumSnapshot()==frozenorder.getObjectSumSnapshot()
				&&Math.abs(frozenorder.getSum())+frozenorder.getBalance()==earlierorder.getBalance()){
			if (log.isDebugEnabled()) {
				log.debug(String.format("记录%s 与 它的上一条记录 %s 存在问题",frozenorder,earlierorder));
			}
			//生成修正数据
			String sqltemplate = "update t_investment_account_remaining_order set frozensumsnapshot=frozensumsnapshot+abs(`sum`) ,objectsumsnapshot=objectsumsnapshot-abs(`sum`) where orderid = '%s';";
			return String.format(sqltemplate, frozenorder.getOrderId());
		}
		return null;
	}
	
	

}
