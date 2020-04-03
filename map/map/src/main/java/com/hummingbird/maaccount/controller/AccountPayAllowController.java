package com.hummingbird.maaccount.controller;
/**
 * @author YJY 
 * @since 2015年10月30日15:54:4
 * */
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.event.EventListenerContainer;
import com.hummingbird.common.event.RequestEvent;
import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.util.CollectionTools;
import com.hummingbird.common.util.JsonUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.vo.BaseTransVO;
import com.hummingbird.maaccount.entity.AccountPayAllow;
import com.hummingbird.maaccount.entity.AccountPayProduct;
import com.hummingbird.maaccount.entity.AccountPayTerminal;
import com.hummingbird.maaccount.entity.AccountPayTime;
import com.hummingbird.maaccount.entity.AppLog;
import com.hummingbird.maaccount.mapper.AccountPayAllowMapper;
import com.hummingbird.maaccount.mapper.AccountPayProductMapper;
import com.hummingbird.maaccount.mapper.AccountPayTerminalMapper;
import com.hummingbird.maaccount.mapper.AccountPayTimeMapper;
import com.hummingbird.maaccount.mapper.AppLogMapper;
import com.hummingbird.maaccount.service.AccountPayAllowService;
import com.hummingbird.maaccount.vo.AccountPayAllowAddProductVO;
import com.hummingbird.maaccount.vo.AccountPayAllowAddTerminalVO;
import com.hummingbird.maaccount.vo.AccountPayAllowAddTimeVO;
import com.hummingbird.maaccount.vo.AccountPayAllowDeleteListVO;
import com.hummingbird.maaccount.vo.AccountPayAllowListVO;
@Controller
@RequestMapping(value="/paySetting"		 ,method=RequestMethod.POST)
public class AccountPayAllowController extends BaseController  {
	@Autowired
	protected AccountPayAllowService accountPayAllowService;
	@Autowired
	protected AccountPayAllowMapper accountPayAllowDao;
	
	@Autowired
	protected AccountPayProductMapper accountPayProductDao;//油品
	@Autowired
	protected AccountPayTerminalMapper accountPayTerminalDao;//终端
	@Autowired
	protected AccountPayTimeMapper accountPayTimeDao;//时间

	@Autowired(required = true)
	protected AppLogMapper applogDao;
	
	@RequestMapping(value="/getAccountPayAllowList",method=RequestMethod.POST)
	@AccessRequered(methodName = "查询帐户消费允许列表")
	public @ResponseBody ResultModel QueryMessageList(HttpServletRequest request,HttpServletResponse response) {
		int basecode = 293100;//待定
		String messagebase = "查询帐户消费允许列表";
		BaseTransVO<AccountPayAllowListVO> transorder = null;
		ResultModel rm = new ResultModel();
		rm.setBaseErrorCode(basecode);
		try {
			String jsonstr  = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr,BaseTransVO.class, AccountPayAllowListVO.class);
		} catch (Exception e) {
			log.error(String.format("获取参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "参数异常"));
			return rm;
		}
		rm.setErrmsg(messagebase + "成功");
		RequestEvent qe=null ;		
		AppLog rnr = new AppLog();
		rnr.setAppid(transorder.getApp().getAppId());
		rnr.setRequest(ObjectUtils.toString(request.getAttribute("rawjson")));
		rnr.setInserttime(new Date());
		rnr.setMethod("/paySetting/getAccountPayAllowList");
		
		List<AccountPayAllow> list=new ArrayList<AccountPayAllow>();
		try {
			com.hummingbird.common.face.Pagingnation page = transorder.getBody().toPagingnation();
			String accountId = transorder.getBody().getAccountId();
			
			list = accountPayAllowService.selectAllowListByAccountId(accountId,page);
			List<Map> nos = CollectionTools.convertCollection(list, Map.class, new CollectionTools.CollectionElementConvertor<AccountPayAllow, Map>() {

				@Override
				public Map convert(AccountPayAllow ori) {
					
					try {
						Map row= BeanUtils.describe(ori);
						
						row.remove("class");
						return row;
						
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						log.error(String.format("转换为map对象出错"),e);
						return null;
					}
				}
			});
			mergeListOutput(rm, page, nos);
			
		}catch (Exception e1) {
			log.error(String.format(messagebase + "失败"), e1);
			rm.mergeException(e1);
			if(qe!=null)
				qe.setSuccessed(false);
		} finally {
			
			try {
				rnr.setRespone(StringUtils.substring(JsonUtil.convert2Json(rm),0,2000));
				applogDao.insert(rnr);
			} catch (DataInvalidException e) {
				log.error(String.format("日志处理出错"),e);
			}
			
			if(qe!=null)
				EventListenerContainer.getInstance().fireEvent(qe);
		}
		return rm;
	}  
	
	
	
	/**
	 * 根据accountid删除允许列表 
	 * @return
	 */
	@RequestMapping(value="/deleteAccountPayAllowList",method=RequestMethod.POST)
	@AccessRequered(methodName = "删除帐户消费允许列表")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public @ResponseBody ResultModel deleteAccountPayAllowList(HttpServletRequest request,
			HttpServletResponse response) {
		String messagebase = "删除帐户消费允许列表";
		int basecode = 293200;
		AccountPayAllowDeleteListVO transorder = null;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, AccountPayAllowDeleteListVO.class);
		} catch (Exception e) {
			log.error(String.format("获取%s参数出错",messagebase),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, messagebase+"参数"));
			return rm;
		}
//		// 预设的一些信息
		
		rm.setBaseErrorCode(basecode);
		rm.setErrmsg(messagebase + "成功");
		RequestEvent qe=null ;
		
		
		AppLog rnr = new AppLog();
		rnr.setAppid(transorder.getApp().getAppId());
		rnr.setRequest(ObjectUtils.toString(request.getAttribute("rawjson")));
		rnr.setInserttime(new Date());
		rnr.setMethod("/paySetting/deleteAccountPayAllowList");
		
		try {
			String accountId = transorder.getBody().getAccountId();
			String groupId  = transorder.getBody().getGroupId();
			int i= 0;
			if(StringUtils.isNotBlank(accountId)&&StringUtils.isNotBlank(groupId)){
				 i = accountPayAllowDao.deleteByAccountIdAndGroupId(accountId, groupId);
			}
			if(i<= 0){
				rm.setErrmsg("数据未修改！");
			}else{
				rm.setErrmsg(messagebase + "成功");
			}
			
			
		}catch (Exception e1) {
			log.error(String.format(messagebase + "失败"), e1);
			rm.mergeException(e1);
			if(qe!=null)
				qe.setSuccessed(false);
		} finally {
			try {
				rnr.setRespone(StringUtils.substring(JsonUtil.convert2Json(rm),0,2000));
				applogDao.insert(rnr);
			} catch (DataInvalidException e) {
				log.error(String.format("日志处理出错"),e);
			}
			
			if(qe!=null)
				EventListenerContainer.getInstance().fireEvent(qe);
		}
		return rm;
		
	}
	
	/**
	 * 添加帐户消费允许油品 
	 * @return
	 */
	@RequestMapping(value="/addAccountPayAllowProduct",method=RequestMethod.POST)
	@AccessRequered(methodName = "添加帐户消费允许油品")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public @ResponseBody ResultModel addAccountPayAllowProduct(HttpServletRequest request,
			HttpServletResponse response) {
		String messagebase = "添加帐户消费允许油品";
		int basecode = 293300;
		AccountPayAllowAddProductVO transorder = null;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, AccountPayAllowAddProductVO.class);
		} catch (Exception e) {
			log.error(String.format("获取%s参数出错",messagebase),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, messagebase+"参数"));
			return rm;
		}
//		// 预设的一些信息
		
		rm.setBaseErrorCode(basecode);
		rm.setErrmsg(messagebase + "成功");
		RequestEvent qe=null ;
		
		
		AppLog rnr = new AppLog();
		rnr.setAppid(transorder.getApp().getAppId());
		rnr.setRequest(ObjectUtils.toString(request.getAttribute("rawjson")));
		rnr.setInserttime(new Date());
		rnr.setMethod("/paySetting/addAccountPayAllowProduct");
		
		try {
			String accountId = transorder.getBody().getAccountId();
			String groupId  = transorder.getBody().getGroupId();
			String[] Zjproducts  = transorder.getBody().getZjproducts();
			
			ValidateUtil.assertNullnoappend(accountId, "帐户不存在");
			ValidateUtil.assertNullnoappend(Zjproducts, "添加油品不存在");
			if(ArrayUtils.isEmpty(Zjproducts)){
				throw ValidateException.ERROR_PARAM_NULL.cloneAndAppend(null, "添加油品不存在");
			}
			accountPayAllowService.addPayProducts(accountId,groupId,Zjproducts);
			
		}catch (Exception e1) {
			log.error(String.format(messagebase + "失败"), e1);
			rm.mergeException(e1);
			if(qe!=null)
				qe.setSuccessed(false);
		} finally {
			try {
				rnr.setRespone(StringUtils.substring(JsonUtil.convert2Json(rm),0,2000));
				applogDao.insert(rnr);
			} catch (DataInvalidException e) {
				log.error(String.format("日志处理出错"),e);
			}
			
			if(qe!=null)
				EventListenerContainer.getInstance().fireEvent(qe);
		}
		return rm;
		
	}
	
	/**
	 * 添加帐户消费允许消费时间 
	 * @return
	 */
	@RequestMapping(value="/addAccountPayAllowTime",method=RequestMethod.POST)
	@AccessRequered(methodName = "添加帐户消费允许消费时间")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public @ResponseBody ResultModel addAccountPayAllowTime(HttpServletRequest request,
			HttpServletResponse response) {
		String messagebase = "添加帐户消费允许消费时间";
		int basecode = 293400;
		AccountPayAllowAddTimeVO transorder = null;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, AccountPayAllowAddTimeVO.class);
		} catch (Exception e) {
			log.error(String.format("获取%s参数出错",messagebase),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, messagebase+"参数"));
			return rm;
		}
//		// 预设的一些信息
		
		rm.setBaseErrorCode(basecode);
		rm.setErrmsg(messagebase + "成功");
		RequestEvent qe=null ;
		
		
		AppLog rnr = new AppLog();
		rnr.setAppid(transorder.getApp().getAppId());
		rnr.setRequest(ObjectUtils.toString(request.getAttribute("rawjson")));
		rnr.setInserttime(new Date());
		rnr.setMethod("/paySetting/addAccountPayAllowTime");
	
		try {
			String accountId = transorder.getBody().getAccountId();
			String groupId  = transorder.getBody().getGroupId();
			String[] periodTime =  transorder.getBody().getPeriodTime();
			String cycleType = transorder.getBody().getCycleType();
			String cycleDate = transorder.getBody().getCycleDate();
			String description = transorder.getBody().getDescription();
			
			accountPayAllowService.addPayTimes(accountId,groupId,periodTime,cycleType,cycleDate,description);
		}catch (Exception e1) {
			log.error(String.format(messagebase + "失败"), e1);
			rm.mergeException(e1);
			if(qe!=null)
				qe.setSuccessed(false);
		} finally {
			try {
				rnr.setRespone(StringUtils.substring(JsonUtil.convert2Json(rm),0,2000));
				applogDao.insert(rnr);
			} catch (DataInvalidException e) {
				log.error(String.format("日志处理出错"),e);
			}
			
			if(qe!=null)
				EventListenerContainer.getInstance().fireEvent(qe);
		}
		return rm;
		
	}
	
	
	/**
	 * 添加帐户消费允许终端 
	 * @return
	 */
	@RequestMapping(value="/addAccountPayAllowTerminal",method=RequestMethod.POST)
	@AccessRequered(methodName = "添加帐户消费允许终端")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public @ResponseBody ResultModel addAccountPayAllowTerminal(HttpServletRequest request,
			HttpServletResponse response) {
		String messagebase = "添加帐户消费允许终端";
		int basecode = 293500;
		AccountPayAllowAddTerminalVO transorder = null;
		ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			request.setAttribute("rawjson", jsonstr);
			transorder = RequestUtil.convertJson2Obj(jsonstr, AccountPayAllowAddTerminalVO.class);
		} catch (Exception e) {
			log.error(String.format("获取%s参数出错",messagebase),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, messagebase+"参数"));
			return rm;
		}
//		// 预设的一些信息
		
		rm.setBaseErrorCode(basecode);
		rm.setErrmsg(messagebase + "成功");
		RequestEvent qe=null ;
		
		
		AppLog rnr = new AppLog();
		rnr.setAppid(transorder.getApp().getAppId());
		rnr.setRequest(ObjectUtils.toString(request.getAttribute("rawjson")));
		rnr.setInserttime(new Date());
		rnr.setMethod("/paySetting/addAccountPayAllowTerminal");
		
		try {
			String accountId = transorder.getBody().getAccountId();
			String groupId  = transorder.getBody().getGroupId();
			String[] terminalIds = transorder.getBody().getTerminalIds();
			String description  = transorder.getBody().getDescription();	
			
			accountPayAllowService.addPayTerminals(accountId,groupId,terminalIds,description);
			
			
			
		}catch (Exception e1) {
			log.error(String.format(messagebase + "失败"), e1);
			rm.mergeException(e1);
			if(qe!=null)
				qe.setSuccessed(false);
		} finally {
			try {
				rnr.setRespone(StringUtils.substring(JsonUtil.convert2Json(rm),0,2000));
				applogDao.insert(rnr);
			} catch (DataInvalidException e) {
				log.error(String.format("日志处理出错"),e);
			}
			
			if(qe!=null)
				EventListenerContainer.getInstance().fireEvent(qe);
		}
		return rm;
		
	}
	
	
}
