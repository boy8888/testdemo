/**
 * 
 */
package com.hummingbird.maaccount.vo;


/**
 * 登录vo
 * @author huangjiej_2
 * 2014年10月18日 下午10:09:58
 */
public class LoginDetailByPasswdVo {

	/**
	 * 手机号
	 */
	private String mobileNum;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 手机号
	 */
	public String getMobileNum() {
		return mobileNum;
	}
	/**
	 * 手机号
	 */
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	/**
	 * 密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginDetailByPasswdVo [mobileNum=" + mobileNum + ", password="
				+ password + "]";
	}
	
	
	
	
	
	
}
