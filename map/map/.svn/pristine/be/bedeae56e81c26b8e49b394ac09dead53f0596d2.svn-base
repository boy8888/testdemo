package com.hummingbird.maaccount.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.entity.JifenAccount;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Pagingnation;
import com.hummingbird.maaccount.mapper.JifenAccountMapper;
import com.hummingbird.maaccount.service.JifenAccountService;
@Service
public class JifenAccountServiceImpl implements JifenAccountService{
	@Autowired
	JifenAccountMapper jifenAccountDao;
	public List<JifenAccount> queryJifenAccount(String mobileNum,
			String jifenProductId,Pagingnation page) throws MaAccountException {
		
		if(jifenProductId==null){
			jifenProductId="";
		}
		String[] Ids=jifenProductId.split("\\|");
		List<String> productIds=new ArrayList<String>();
		for(String id:Ids){
			if(StringUtils.isNotBlank(id))
			productIds.add(id);
			
			
		}
		Integer total = jifenAccountDao.queryJifenAccountByTotal(mobileNum, productIds);
		page.setTotalCount(total);
		return jifenAccountDao.queryJifenAccount(mobileNum,productIds,page);
		
	}
	@Override
	public JifenAccount selectAccountByProductId(String mobileNum,
			String productId,String appId) throws MaAccountException {
		return jifenAccountDao.selectAccountByProductId(mobileNum,productId,appId);
	}
	@Override
	public boolean updateAccount(JifenAccount record) {
		int updatesuccess = jifenAccountDao.updateByPrimaryKey(record);
		return 1==updatesuccess;
	}

}
