package com.hummingbird.maaccount.vo;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

public class LoginByTypeVO extends AppBaseVO implements AppMobileDecidable{

    private LoginByType login;


    @Override
    public String toString() {
        return "LoginByTypeVO [login=" + login + "   app=" + app + "]";
    }

    public LoginByType getLogin() {
        return login;
    }

    public void setLogin(LoginByType login) {
        this.login = login;
    }

    public boolean validate() {
        if (login == null) { 
            return false; 
        }
        if (StringUtils.isBlank(login.getLogin_type())) { 
            return false; 
        }
        if (StringUtils.isBlank(login.getUser())) { 
            return false; 
        }
        if (StringUtils.isBlank(login.getPassword())) { 
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
        // TODO Auto-generated method stub
        return null;
    }

}
