package com.hummingbird.maaccount.vo;

public class ResetPWByOldPW {

    private String login_type;

    private String user;

    private String old_password;

    private String new_password;


    @Override
    public String toString() {
        return "ResetPWByOldPW [login_type=" + login_type + ", user=" + user + ", old_password=" + old_password + ", new_password=" + new_password + "]";
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

}
