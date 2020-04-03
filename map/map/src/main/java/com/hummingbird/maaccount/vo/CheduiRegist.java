package com.hummingbird.maaccount.vo;


public class CheduiRegist {
    private String mobileNum;

    private String user_name;


    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "CheduiRegist [mobileNum=" + mobileNum + ", user_name=" + user_name + "]";
    }
}
