package com.hummingbird.maaccount.vo;

import org.apache.commons.lang.StringUtils;
import com.hummingbird.common.exception.AuthenticationException;
import com.hummingbird.commonbiz.vo.AppMobileDecidable;

public class SynUserPasswordVO extends AppBaseVO implements AppMobileDecidable{
	
	//账户登录类型
	private String loginType;

	//账户
	private String userName;

	//密码
	private String password;
	
	//时间戳
	private String timeStamp;
	
	//随机数
	private String nonce;

	//证书签名
	private String sign;
	
	public void validate() throws AuthenticationException{
		if(StringUtils.isBlank(userName)){
			throw new AuthenticationException(10101,"请求参数不正确,缺少userName字段");
		}
		if(StringUtils.isBlank(password)){
			throw new AuthenticationException(10101,"请求参数不正确,缺少password字段");
		}
		if(StringUtils.isBlank(timeStamp)){
			throw new AuthenticationException(10101,"请求参数不正确,缺少timeStamp字段");
		}
		if(StringUtils.isBlank(nonce)){
			throw new AuthenticationException(10101,"请求参数不正确,缺少nonce字段");
		}
		if(StringUtils.isBlank(sign)){
			throw new AuthenticationException(10101,"请求参数不正确,缺少sign字段");
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		return "SynUserPasswordVO [loginType=" + loginType + ", userName=" + userName + ", password=" + password
				+ ", timeStamp=" + timeStamp + ", nonce=" + nonce + ", sign=" + sign + ", app=" + app + "]";
	}
	
	public String[] getFieldArray(){
		return new String[]{loginType,userName,password,timeStamp,nonce};
	}

	@Override
	public String getAppId() {
		return app.getAppId();
	}

	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return null;
	}
}
