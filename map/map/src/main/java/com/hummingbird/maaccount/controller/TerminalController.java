package com.hummingbird.maaccount.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.event.EventListenerContainer;
import com.hummingbird.common.event.RequestEvent;
import com.hummingbird.common.ext.AccessRequered;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.vo.BaseTransVO;
import com.hummingbird.commonbiz.vo.BaseTransVO;
import com.hummingbird.commonbiz.vo.BaseTransVO;
import com.hummingbird.maaccount.service.TerminalService;
import com.hummingbird.maaccount.vo.AddStoreBodyVO;
import com.hummingbird.maaccount.vo.AddStoreBodyVO;


/**
 * @author 
 * @date 2015-11-17
 * @version 1.0
 *  
 */
@Controller
@RequestMapping(value="terminal",method=RequestMethod.POST)
public class TerminalController extends BaseController {
	@Autowired(required = true)
	protected TerminalService terminalService;

		
	/**
	 * 油站通知接口
	 * @return
	 */
	@RequestMapping(value="/addStore",method=RequestMethod.POST)
	@AccessRequered(methodName = "油站通知接口",isJson=true,codebase=240100,className="com.hummingbird.commonbiz.vo.BaseTransVO",genericClassName="com.hummingbird.maaccount.vo.AddStoreBodyVO",appLog=true)
	public @ResponseBody ResultModel addStore(HttpServletRequest request,HttpServletResponse response) {
		ResultModel rm = super.getResultModel();
		BaseTransVO<AddStoreBodyVO> transorder = (BaseTransVO<AddStoreBodyVO>) super.getParameterObject();
		String messagebase = "油站通知接口"; 
	
		RequestEvent qe=null ; //业务请求事件,当实现一些关键的业务时,需要生成该请求
		
		try {
			//业务数据必填等校验
			//业务数据逻辑校验
			if(log.isDebugEnabled()){
				log.debug("检验通过，获取请求");
			}
			terminalService.addStore(transorder.getApp().getAppId(),transorder.getBody());
		}catch (Exception e1) {
			log.error(String.format(messagebase + "失败"), e1);
			rm.mergeException(e1);
			if(qe!=null)
				qe.setSuccessed(false);
		} finally {
			if(qe!=null)
				EventListenerContainer.getInstance().fireEvent(qe);
		}
		return rm;
		
	}
	
	
    }
