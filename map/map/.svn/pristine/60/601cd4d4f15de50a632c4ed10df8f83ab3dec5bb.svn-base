package com.hummingbird.maaccount.util.synuserinfo.vo.result;

public class UpdatePasswordWithVerifyResultVO {
	
	private UpdatePasswordWithVerifyResponseMessage responseMessage;
	
	private String data;
	
	public boolean isSuccess(){
		if(responseMessage==null){
			return false;
		}
		if(!"0".equals(responseMessage.getErrorType())){
			return false;
		}
		return true;
	}

	public UpdatePasswordWithVerifyResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(UpdatePasswordWithVerifyResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

}
