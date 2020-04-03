package com.hummingbird.maaccount.vo;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

public class BindMobileVO extends AppBaseVO implements AppMobileDecidable{
    
    private BindMobile bind;
    
    

    
    @Override
    public String toString() {
        return "BindMobileVO [bind=" + bind + "  app="+app+"]";
    }


    public BindMobile getBind() {
        return bind;
    }

    
    public void setBind(BindMobile bind) {
        this.bind = bind;
    }
    
    public boolean validate(){
        if(bind==null){
            return false;
        }
        if(StringUtils.isBlank(bind.getLogin_type())){
            return false;
        }
        if(StringUtils.isBlank(bind.getMobileNum())){
            return false;
        }
        if(StringUtils.isBlank(bind.getUser())){
            return false;
        }
        if(StringUtils.isBlank(bind.getSmsCode())){
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
        
        return bind.getMobileNum();
    }
}
