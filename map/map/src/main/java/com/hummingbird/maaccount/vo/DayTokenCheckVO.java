package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;
//获取交易对账接口参数
public class DayTokenCheckVO extends AppBaseVO implements AppMobileDecidable {
	private TokenCheckVO check;
	
	@Override
	public String toString() {
		return "DayTokenCheckVO [check=" + check + ", app=" + app + "]";
	}
	
	

	



	public TokenCheckVO getCheck() {
		return check;
	}







	public void setCheck(TokenCheckVO check) {
		this.check = check;
	}







	@Override
	public String getAppId() {
		// TODO Auto-generated method stub
		return app.getAppId();
	}

	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return null;
	}

}
