package com.hummingbird.maaccount.vo;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

/**
 * 此类作为新的重置密码接口的VO使用
 */
public class ResetPWByOldPWVO extends AppBaseVO implements AppMobileDecidable{
    private ResetPWByOldPW reset;

    
    public ResetPWByOldPW getReset() {
        return reset;
    }

    
    public void setReset(ResetPWByOldPW reset) {
        this.reset = reset;
    }


    @Override
    public String toString() {
        return "ResetPWByOldPWVO [reset=" + reset + "  app="+app+"]";
    }
    
    public boolean validate(){
        if(reset==null){
            return false;
        }
        if(StringUtils.isBlank(reset.getLogin_type())){
            return false;
        }
        if(StringUtils.isBlank(reset.getUser())){
            return false;
        }
        if(StringUtils.isBlank(reset.getNew_password())){
            return false;
        }
        if(StringUtils.isBlank(reset.getOld_password())){
            return false;
        }
        return true;
    }


    @Override
    public String getAppId() {
        return app.getAppId();
    }


    @Override
    public String getMobileNum() {
        return null;
    }    
}
