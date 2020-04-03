/**
 * 
 * MethodService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.common.exception.AuthorityException;
import com.hummingbird.common.exception.BusinessException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.maaccount.entity.AppMethod;
import com.hummingbird.maaccount.mapper.AppMethodMapper;

/**
 * @author huangjiej_2
 * 2014年12月29日 下午11:35:42
 * 本类主要做为 权限判断service
 */
@Service
public class AppMethodService {

	@Autowired
	AppMethodMapper amDao;
	
	/**
	 * 权限判定
	 * @param appId
	 * @param methodid
	 * @throws AuthorityException
	 */
	public void authorize(String appId,String methodid) throws AuthorityException{
		AppMethod am = amDao.selectAppMethod(appId,methodid);
		if(null==am){
			throw ValidateException.ERROR_PREMISSION_DENIED;
//			throw new AuthorityException(BusinessException.ERRCODE_AUTHORITY,"没有权限进行此操作");
		}
	}
	
	
}
