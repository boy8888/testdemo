package com.hummingbird.maaccount.vo;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

public class CheDuiRegistVO extends AppBaseVO implements AppMobileDecidable{

    private CheduiRegist regist;


    
    public CheduiRegist getRegist() {
        return regist;
    }


    
    public void setRegist(CheduiRegist regist) {
        this.regist = regist;
    }


    
    
    //验证属性是否正常
    public boolean validate(){
        if(regist==null){
            return false;
        }
        if(StringUtils.isBlank(regist.getMobileNum())){
            return false;
        }
        if(StringUtils.isBlank(regist.getUser_name())){
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "CheDuiRegistVO [regist=" + regist + "app="+app+"]";
    }



    @Override
    public String getAppId() {
        
        return app.getAppId();
    }



    @Override
    public String getMobileNum() {
        return regist.getMobileNum();
    }
    
    
}

