package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

public class VerifyPaymentCodeMD5BaseVO extends AppBaseVO implements AppMobileDecidable{
	
    private VerifyPaymentCodeMD5VO verify;
	
	public String toString() {
		return "VerifyPaymentCodeMD5BaseVO [verify=" + verify + ", app=" + app + "]";
	}
	
	public String getAppId() {
		return app.getAppId();
	}

	@Override
	public String getMobileNum() {
		return verify.getMobileNum();
	}

    
    public VerifyPaymentCodeMD5VO getVerify() {
        return verify;
    }

    
    public void setVerify(VerifyPaymentCodeMD5VO verify) {
        this.verify = verify;
    }

}
