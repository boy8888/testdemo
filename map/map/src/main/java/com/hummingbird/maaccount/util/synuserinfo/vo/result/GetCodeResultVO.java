package com.hummingbird.maaccount.util.synuserinfo.vo.result;

public class GetCodeResultVO {
	
	//本接口目的获取的数据code
	private String code;
	
	//当获取code失败时的错误信息
	private String error;
	
	//当获取code失败时的错误信息解释
	private String error_description;
	
	public boolean isSuccess(){
		if(code==null||"".equals(code.trim())){
			return false;
		}
		return true;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
