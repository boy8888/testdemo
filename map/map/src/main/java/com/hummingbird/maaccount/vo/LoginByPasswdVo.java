/**
 * 
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

/**
 * 登录vo(根据密码)
 * @author huangjiej_2
 * 2014年10月18日 下午10:09:58
 */
public class LoginByPasswdVo extends AppBaseVO implements AppMobileDecidable {

//	{"appId":"zjhtwallet","appKeyHash":"23werwere3erewfffereee","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"MD5(appId+appKey+nonce+timeStamp)",
//		"loginInfo":{"mobileNum":"13912345678","smsCode":"223344"},
//		"deviceInfo":{"mac":"3ere:eee:3434:34434","deviceId":"设备标识","imsi":"SIM卡设备号"}}  
	/**
	 * 验证码对象
	 */
	private  LoginDetailByPasswdVo login;
	
	/**
	 * 验证码对象
	 */
	public LoginDetailByPasswdVo getLogin() {
		return login;
	}
	/**
	 * 验证码对象
	 */
	public void setLogin(LoginDetailByPasswdVo loginInfo) {
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
