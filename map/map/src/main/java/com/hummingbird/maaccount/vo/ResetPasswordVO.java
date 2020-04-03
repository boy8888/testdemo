/**
 * 
 * RegisterVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.hummingbird.common.exception.SignatureException;
import com.hummingbird.commonbiz.vo.AppMobileDecidable;

/**
 * @author john huang
 * 2015年2月5日 上午11:11:27
 * 本类主要做为重置密码对象
 */
public class ResetPasswordVO extends AppBaseVO implements AppMobileDecidable {

	/**
	 * 注册信息
	 */
	private  ResetPasswordDetailVO login;
	
	/**
	 *注册信息
	 */
	public ResetPasswordDetailVO getLogin() {
		return login;
	}
	/**
	 * 注册信息
	 */
	public void setLogin(ResetPasswordDetailVO loginInfo) {
		this.login = loginInfo;
	}
	
	public String getMobileNum(){
		if(login!=null){
			return login.getMobileNum();
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginVo [login=" + login + ", app=" + app + "]";
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.vo.AppMobileDecidable#getAppId()
	 */
	@Override
	public String getAppId() {
		
		return app.getAppId();
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.AppBaseVO#setOtherParam(java.util.Map)
	 */
	@Override
	public void setOtherParam(Map map) {
		if(map!=null)
		{
			if(map.containsKey("appKey"))
			{
				app.setAppKey(ObjectUtils.toString(map.get("appKey")));
			}
			login.setAppKey(ObjectUtils.toString(map.get("appKey")));
		}
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.AppBaseVO#isAuthed()
	 */
	@Override
	public boolean isAuthed() throws SignatureException {
		if( super.isAuthed())
		{
			if(login.isAuthed()){
				return true;
			}
		}
		return false;
	}
	
	
	
	
}