package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;
//获取 现金账户信息查询参数
public class CashAccountInfoVO extends AppBaseVO implements AppMobileDecidable{
	private GetCashInfoVO get;

	
	

	public GetCashInfoVO getGet() {
		return get;
	}


	public void setGet(GetCashInfoVO get) {
		this.get = get;
	}


	@Override
	public String toString() {
		return "OpenCashCardVO [get=" + get + ", app=" + app + "]";
	}


	@Override
	public String getAppId() {
		// TODO Auto-generated method stub
		return app.getAppId();
	}


	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return get.getMobileNum();
	}
}
