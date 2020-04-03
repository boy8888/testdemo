package com.hummingbird.maaccount.util.synuserinfo.vo.result;

public class GetTokenResultVO {

	// 令牌
	private String access_token;

	// 过期时间，单位秒
	private Integer expires_in;

	// Token类型
	private String token_type;

	// 权限范围
	private String scope;

	// 刷新token超时时间为15天
	private String refresh_token;

	// 错误码
	private String error;

	// 错误信息解释
	private String error_description;
	
	public boolean isSuccess(){
		if(access_token==null||"".equals(access_token.trim())){
			return false;
		}
		return true;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

}
