package com.hummingbird.maaccount.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





















import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.entity.RedPaperAccountOrder;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.mapper.RedPaperAccountMapper;
import com.hummingbird.maaccount.mapper.RedPaperAccountOrderMapper;
import com.hummingbird.maaccount.service.RedPaperAccountService;
@Service
public class RedPaperAccountServiceImpl implements RedPaperAccountService{

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	@Autowired
	RedPaperAccountMapper raDao;
	
	@Override
	public List<RedPaperAccount> queryRedPaperAccount(
			String mobileNum, String redPaperProductId, String expireIn,
			Pagingnation page,String status,String startTime,String endTime,String accountId,String appId) throws MaAccountException {
//		Date date=new Date();
//		Integer startPage=page.getPageSize()*(page.getCurrPage()-1);
//		Integer endPage=page.getPageSize()*page.getCurrPage();
		Date startDate=null;
		Date endDate=null;
		
		try{
			if(StringUtils.isNotBlank(startTime)){
				startDate = DateUtils.parseDate(startTime.trim(), new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});
			}
			if(StringUtils.isNotBlank(endTime)){
				endDate= DateUtils.parseDate(endTime.trim(), new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"});
			}
		}catch(Exception e){
			log.error(String.format("日期格式错误"),e);
			throw new IllegalArgumentException("日期格式错误",e);
		}
		if(redPaperProductId==null){
			redPaperProductId="";
		}
		if(accountId==null){
			accountId="";
		}
		String[] Ids=redPaperProductId.split("\\|");
		List<String> productIds=new ArrayList<String>();
		for(String id:Ids){
			if(StringUtils.isNotBlank(id)){
				productIds.add(id);
			}
		}
		
		String[] accIds=accountId.split("\\|");
		List<String> accountIds=new ArrayList<String>();
		for(String id:accIds){
			if(StringUtils.isNotBlank(id)){
				accountIds.add( id);
			}
		}
		
		Integer total = raDao.queryRedPaperAccountByTotal(mobileNum, productIds, expireIn, status,startDate,endDate,accountIds,appId);
		page.setTotalCount(total);
		return raDao.queryRedPaperAccount(mobileNum,productIds,expireIn,status,page,startDate,endDate,accountIds,appId);
//		if(expireIn.equals("ALL")){
//			return raDao.queryRedPaperAccount(mobileNum,productIds,startPage,endPage);
//		}else if(expireIn.equals("YES")){
//			return raDao.queryRedPaperAccountByeffective(mobileNum,productIds,date,startPage,endPage);
//		}else{
//			return raDao.queryRedPaperAccountByinvaild(mobileNum,productIds,date,startPage,endPage);
//		}
	}

	@Override
	public List<RedPaperAccount> selectAccountByAccountId(String mobileNum,List<String> redPaperIds,String appId)
			throws MaAccountException {
		
		
		return raDao.selectAccountByAccountId(mobileNum,redPaperIds,appId);
	}

	@Override
	public boolean updateAccount(RedPaperAccount record) {
		int updatesuccess = raDao.updateByPrimaryKey(record);
		return 1==updatesuccess;
	}
	
	

}
