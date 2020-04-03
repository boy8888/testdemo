package com.hummingbird.maaccount.controller;



import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.JsonUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.entity.AppLog;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.mapper.AppLogMapper;
import com.hummingbird.maaccount.service.OrderService;
import com.hummingbird.maaccount.vo.JournalOrderOutputVO;
import com.hummingbird.maaccount.vo.OrderQueryTransVO;
import com.hummingbird.maaccount.vo.SpendOrderOutputVO;
import com.hummingbird.maaccount.vo.SpendOrderQueryPagingVO;

/**
 * 订单相关controller
 * 
 * @author huangjiej_2 2014年11月10日 下午11:42:07
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {


	@Autowired(required = true)
	private OrderService orderSrv;
	@Autowired
	protected AppLogMapper logdao;
	
	/**
	 * 日志的本地线程
	 */
	protected static ThreadLocal<AppLog> applogTL = new ThreadLocal<AppLog>();
	
	

	/**
	 * 支付有油贷产品
	 * @param getsmsvo
	 * @return
	 */
	@RequestMapping("/querySpendOrder")
	@AccessRequered(methodName="查询订单信息")
	public @ResponseBody Object querySpendOrder(HttpServletRequest request) {
		OrderQueryTransVO<SpendOrderQueryPagingVO> orderqueryvo;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			orderqueryvo = RequestUtil.convertJson2Obj(jsonstr, OrderQueryTransVO.class,SpendOrderQueryPagingVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		String messagebase = "查询订单信息";
		rm.setBaseErrorCode(27400);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			logWithBegin(orderqueryvo.getApp().getAppId(), request);
			SpendOrderQueryPagingVO query = orderqueryvo.getQuery();
			List<String> typelist = query.getType();
			if(typelist==null||typelist.isEmpty()){
				query.setType(Arrays.asList("XF#","CX#","CZH","XSK"));
			}
			else{
				if(typelist.contains("XFS")){
					typelist.clear();
					typelist.add("XF#");
					query.setJustSuccess(true);
				}
			}
			//检查日期
			try {
				if(query.getSearchStart()==null||query.getSearchEnd()==null){
					log.error(String.format("查询的起止日期不能为空"));
					throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null,"查询的起止日期不能为空");
				}
			} catch (ParseException e) {
				log.error(String.format("查询的起止日期格式不正确"),e);
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"查询的起止日期格式不正确");
			}
			if(query.getSellerIds()!=null&&query.getSellerIds().isEmpty()){
				query.setSellerIds(null);
			}
			if(query.getStoreIds()!=null&&query.getStoreIds().isEmpty()){
				query.setStoreIds(null);
			}
			if(query.getTerminalId()!=null&&query.getTerminalId().isEmpty()){
				query.setTerminalId(null);
			}
			
			Pagingnation pagingnation = orderqueryvo.getQuery().toPagingnation();
			List<SpendOrderOutputVO> orders = orderSrv.querySpendOrder(orderqueryvo,pagingnation);
			if(pagingnation!=null){
				rm.put("pageSize", pagingnation.getPageSize());
				rm.put("pageIndex", pagingnation.getCurrPage());
				rm.put("total", pagingnation.getTotalCount());
			}
			else{
				rm.put("pageSize", 1);
				rm.put("pageIndex", orders.size());
				rm.put("total", orders.size());
			}
			rm.put("list", orders);
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
//			post(rm);
		}
		
		return rm;
		
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
		if(al!=null){
			
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
	}

	/**
	 * 查询流水
	 * @param 
	 * @return
	 */
	@RequestMapping("/queryOrder")
	@AccessRequered(methodName="查询流水")
	public @ResponseBody Object queryOrder(HttpServletRequest request) {
		OrderQueryTransVO<SpendOrderQueryPagingVO> orderqueryvo;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			orderqueryvo = RequestUtil.convertJson2Obj(jsonstr, OrderQueryTransVO.class,SpendOrderQueryPagingVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		String messagebase = "查询流水";
		rm.setBaseErrorCode(28400);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			logWithBegin(orderqueryvo.getApp().getAppId(), request);
			SpendOrderQueryPagingVO query = orderqueryvo.getQuery();
			List<String> typelist = query.getType();
			if(typelist==null||typelist.isEmpty()){
				query.setType(Arrays.asList("XF#","CX#","CZH","XSK"));
			}
			else{
				if(typelist.contains("XFS")){
					typelist.clear();
					typelist.add("XF#");
					query.setJustSuccess(true);
				}
			}
			//检查日期
			try {
				if(query.getSearchStart()==null||query.getSearchEnd()==null){
					log.error(String.format("查询的起止日期不能为空"));
					throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null,"查询的起止日期不能为空");
				}
			} catch (ParseException e) {
				log.error(String.format("查询的起止日期格式不正确"),e);
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"查询的起止日期格式不正确");
			}
			if(query.getSellerIds()!=null&&query.getSellerIds().isEmpty()){
				query.setSellerIds(null);
			}
			if(query.getStoreIds()!=null&&query.getStoreIds().isEmpty()){
				query.setStoreIds(null);
			}
			if(query.getTerminalId()!=null&&query.getTerminalId().isEmpty()){
				query.setTerminalId(null);
			}
			
			Pagingnation pagingnation = orderqueryvo.getQuery().toPagingnation();
			List<JournalOrderOutputVO> orders = orderSrv.queryJournalOrder(orderqueryvo,pagingnation,false);
			if(pagingnation!=null){
				rm.put("pageSize", pagingnation.getPageSize());
				rm.put("pageIndex", pagingnation.getCurrPage());
				rm.put("total", pagingnation.getTotalCount());
			}
			else{
				rm.put("pageSize", 1);
				rm.put("pageIndex", orders.size());
				rm.put("total", orders.size());
			}
			rm.put("list", orders);
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
//			post(rm);
		}
		
		return rm;
		
	}
	/**
	 * 查询昨日流水
	 * @param 
	 * @return
	 */
	@RequestMapping("/queryOrderYesterday")
	@AccessRequered(methodName="查询昨日流水")
	public @ResponseBody Object queryYesterdayOrder(HttpServletRequest request) {
		OrderQueryTransVO<SpendOrderQueryPagingVO> orderqueryvo;
		
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			orderqueryvo = RequestUtil.convertJson2Obj(jsonstr, OrderQueryTransVO.class,SpendOrderQueryPagingVO.class);
		} catch (Exception e) {
			log.error(String.format("获取订单查询参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单查询参数"));
			return rm;
		}
		String messagebase = "查询昨日流水";
		rm.setBaseErrorCode(28500);
		rm.setErrmsg(messagebase+"成功");
		try {
//			prepare(transorder,request);
			logWithBegin(orderqueryvo.getApp().getAppId(), request);
			SpendOrderQueryPagingVO query = orderqueryvo.getQuery();
			List<String> typelist = query.getType();
			if(typelist==null||typelist.isEmpty()){
				query.setType(Arrays.asList("XF#","CX#","CZH","XSK"));
			}
			else{
				if(typelist.contains("XFS")){
					typelist.clear();
					typelist.add("XF#");
					query.setJustSuccess(true);
				}
			}
			//检查日期
//			try {
//				if(query.getSearchStart()==null||query.getSearchEnd()==null){
//					log.error(String.format("查询的起止日期不能为空"));
//					throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null,"查询的起止日期不能为空");
//				}
//			} catch (ParseException e) {
//				log.error(String.format("查询的起止日期格式不正确"),e);
//				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"查询的起止日期格式不正确");
//			}
			if(query.getSellerIds()!=null&&query.getSellerIds().isEmpty()){
				query.setSellerIds(null);
			}
			if(query.getStoreIds()!=null&&query.getStoreIds().isEmpty()){
				query.setStoreIds(null);
			}
			if(query.getTerminalId()!=null&&query.getTerminalId().isEmpty()){
				query.setTerminalId(null);
			}
			
			Pagingnation pagingnation = orderqueryvo.getQuery().toPagingnation();
			List<JournalOrderOutputVO> orders = orderSrv.queryJournalOrder(orderqueryvo,pagingnation,true);
			if(pagingnation!=null){
				rm.put("pageSize", pagingnation.getPageSize());
				rm.put("pageIndex", pagingnation.getCurrPage());
				rm.put("total", pagingnation.getTotalCount());
			}
			else{
				rm.put("pageSize", 1);
				rm.put("pageIndex", orders.size());
				rm.put("total", orders.size());
			}
			rm.put("list", orders);
		} catch (Exception e1) {
			log.error(String.format(messagebase+"失败"),e1);
			rm.mergeException(e1);
			rm.setErrmsg(messagebase+"失败，"+rm.getErrmsg());
		}
		finally{
			//记录日志
//			post(rm);
		}
		
		return rm;
		
	}
	
	
}
