package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

/**
 * 本类用于只需要手机号即可注册的接口
 */
public class RegistByPhoneOnlyVO extends AppBaseVO implements AppMobileDecidable {
    
    private RegistByPhoneOnlyDetailVO regist;

    @Override
    public String getAppId() {
        return app.getAppId();
    }

    @Override
    public String getMobileNum() {
        return regist.getMobileNum();
    }

    public RegistByPhoneOnlyDetailVO getRegist() {
        return regist;
    }

    public void setRegist(RegistByPhoneOnlyDetailVO regist) {
        this.regist = regist;
    }

    @Override
    public String toString() {
        return "RegistByPhoneOnlyVO [regist=" + regist + " app="+app+"]";
    }
    
    

}
