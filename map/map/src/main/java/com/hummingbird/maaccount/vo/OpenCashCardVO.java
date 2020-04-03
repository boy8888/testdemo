package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;
//获取现金账户开通接口参数
public class OpenCashCardVO extends AppBaseVO implements AppMobileDecidable{
	private CashCardVO open;

	@Override
	public String getAppId() {
		// TODO Auto-generated method stub
		return app.getAppId();
	}

	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return open.getMobileNum();
	}
	
	public CashCardVO getOpen() {
		return open;
	}

	public void setOpen(CashCardVO open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "OpenCashCardVO [open=" + open + ", app=" + app + "]";
	}
}
