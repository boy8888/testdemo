/**
 * 
 * UserAttrService.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service;

import java.util.List;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.maaccount.entity.UserAttr;

/**
 * @author john huang
 * 2015年7月23日 下午10:11:23
 * 本类主要做为
 */
public interface UserAttrService {

	/**
	 * 获取用户属性
	 * @param mobileNum
	 * @param attr
	 * @return
	 */
	UserAttr getUserAttr(String mobileNum,String attr);

	/**
	 * 添加用户属性
	 * @param userId
	 * @param attrs
	 * @return 
	 * @throws DataInvalidException 
	 */
	List<UserAttr> addAttr(Integer userId, String[] attrs) throws DataInvalidException;
	
	/**
	 * 添加用户属性
	 * @param userId
	 * @param attrs
	 * @return 
	 * @throws DataInvalidException 
	 */
	UserAttr addAttr(Integer userId, String attrs) throws DataInvalidException;
	
}
