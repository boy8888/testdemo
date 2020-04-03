/**
 * 
 * UserAttrServiceImpl.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.maaccount.entity.AttrInfo;
import com.hummingbird.maaccount.entity.UserAttr;
import com.hummingbird.maaccount.mapper.AttrInfoMapper;
import com.hummingbird.maaccount.mapper.UserAttrMapper;
import com.hummingbird.maaccount.service.UserAttrService;

/**
 * @author john huang
 * 2015年7月23日 下午10:12:40
 * 本类主要做为
 */
@Service
public class UserAttrServiceImpl implements UserAttrService {

	@Autowired
	protected UserAttrMapper userAttrDao;
	@Autowired
	protected AttrInfoMapper attrDao;
	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	
	
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.UserAttrService#getUserAttr(java.lang.String, java.lang.String)
	 */
	@Override
	public UserAttr getUserAttr(String mobileNum, String attr) {
		
		return userAttrDao.selectUserAttr(mobileNum,attr);
	}
	
	/**
	 * 添加用户属性
	 * @param userId
	 * @param attrs
	 * @return 
	 * @throws DataInvalidException 
	 */
	public List<UserAttr> addAttr(Integer userId, String[] attrs) throws DataInvalidException{
		List<UserAttr> uas = new ArrayList<UserAttr>();
		for (int i = 0; i < attrs.length; i++) {
			String attr = attrs[i];
			UserAttr addAttr = addAttr(userId,attr);
			uas.add(addAttr);
		}
		return uas;
	}
	
	/**
	 * 添加用户属性
	 * @param userId
	 * @param attrs
	 * @return 
	 * @throws DataInvalidException 
	 */
	public UserAttr addAttr(Integer userId, String attr) throws DataInvalidException{
		AttrInfo attrInfo = attrDao.selectByPrimaryKey(attr);
		if(attrInfo==null){
			if (log.isDebugEnabled()) {
				log.debug(String.format("属性%s未定义",attrInfo));
			}
			throw  ValidateException.ERROR_PARAM_NOTEXIST.clone(null, String.format("属性%s未定义",attrInfo));
		}
		userAttrDao.deleteUserAttr(userId,attr);
		UserAttr ua = new UserAttr();
		ua.setUserId(userId);
		ua.setAttrId(attr);
		ua.setInsertTime(new Date());
		userAttrDao.insert(ua);
		return ua;
	}

}
