/**
 * 
 * RegisterVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

/**
 * @author john huang
 * 2015年2月5日 上午11:11:27
 * 本类主要做为登录对象
 */
public class RegisterVO extends AppBaseVO implements AppMobileDecidable {

	/**
	 * 注册信息
	 */
	private  RegisterDetailVO login;
	
	/**
	 *注册信息
	 */
	public RegisterDetailVO getLogin() {
		return login;
	}
	/**
	 * 注册信息
	 */
	public void setLogin(RegisterDetailVO loginInfo) {
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
	
	
	
	
}