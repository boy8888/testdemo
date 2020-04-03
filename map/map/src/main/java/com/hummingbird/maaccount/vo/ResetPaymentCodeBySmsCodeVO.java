package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

public class ResetPaymentCodeBySmsCodeVO extends AppBaseVO implements AppMobileDecidable{
	private ResetPaymentCodeVO reset;
	@Override
	public String getAppId() {
		// TODO Auto-generated method stub
		return app.getAppId();
	}
	public String toString() {
		return "ResetPaymentCodeBySmsCodeVO [reset=" + reset + ", app=" + app + "]";
	}
	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return reset.getMobileNum();
	}

	public ResetPaymentCodeVO getReset() {
		return reset;
	}

	public void setReset(ResetPaymentCodeVO reset) {
		this.reset = reset;
	}
	
}
