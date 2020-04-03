package com.hummingbird.maaccount.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hummingbird.common.exception.BusinessException;
import com.hummingbird.maaccount.entity.TerminalList;
import com.hummingbird.maaccount.mapper.TerminalListMapper;
import com.hummingbird.maaccount.service.TerminalService;
import com.hummingbird.maaccount.vo.AddStoreBodyVO;

/**
 * @author 
 * @date 2015-11-17
 * @version 1.0
 *  service接口实现
 */
@Service
public class TerminalServiceImpl  implements TerminalService{

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());
	
	@Autowired
	TerminalListMapper dao;

	/**
	 * 油站通知接口
	 * @param appId 应用id
	 * @param body 参数
	 * @return 
	 * @throws BusinessException 
	 */
	 @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,value="txManager")
	public void addStore(String appId,AddStoreBodyVO body) throws BusinessException{
		if(log.isDebugEnabled()){
				log.debug("油站通知接口开始");
		}
		List<String> delTerminalIds = body.getDelTerminalIds();
		//删除要删除的记录
		if(delTerminalIds!=null){
			for (Iterator iterator = delTerminalIds.iterator(); iterator.hasNext();) {
				String termId = (String) iterator.next();
				dao.deleteByPrimaryKey(termId);
			}
		}
		List<String> addTerminalIds = body.getAddTerminalIds();
		if(addTerminalIds!=null){
			for (Iterator iterator = addTerminalIds.iterator(); iterator.hasNext();) {
				String term = (String) iterator.next();
				TerminalList tl= dao.selectByPrimaryKey(term);
				if(tl!=null){
					//原来已有数据先进行删除
					dao.deleteByPrimaryKey(term);
				}
				TerminalList addterm = new TerminalList();
				addterm.setSellerId    (body.getSellerId    ());
				addterm.setSellerName  (body.getSellerName  ());
				addterm.setStoreId     (body.getStoreId     ());
				addterm.setStoreName   (body.getStoreName   ());
				addterm.setTerminalId (term);
				addterm.setStatus      (body.getStatus());
				addterm.setDistrict    (body.getDistrict    ());
				addterm.setCity        (body.getCity        ());
				addterm.setProvince    (body.getProvince    ());
				addterm.setShortName   (body.getShortName   ());
				addterm.setSellerShortName(body.getSellerShortName());
				addterm.setStoreShortName(body.getStoreShortName());
				addterm.setSellerType  (body.getSellerType  ());
				addterm.setLongitude   (body.getLongitude   ());
				addterm.setLatitude    (body.getLatitude    ());
				addterm.setInsertTime(new Date());
				dao.insert(addterm);
			}
		}
		if(log.isDebugEnabled()){
				log.debug("油站通知接口完成");
		}
	}
		
		
		
}