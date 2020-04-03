package com.hummingbird.maaccount.entity;

import java.util.Date;

public class User {

    private Integer userId;                 //主键

    private String  loginType = "MOBILE";   //登录方式 (MOBILE=手机号;EMAIL=邮件;USERNAME=普通用户名;ID=身份证号)

    private String  mobilenum;              //手机号

    private String  email;                  //邮件

    private String  userName;               //普通用户名

    private String  Id;                     //身份证号

    private String  name;                   //姓名

    private String  nickname;               //昵称

    private String  headimage;

    private Date    insertTime;

    private Date    updateTime;

    private String  password;

    private String  passwordPlaintext;

    private String  passwordFirst;

    private String  paymentcodemd5;

    /**
     * pos机对支付密码使用des加密
     */
    private String  paymentCodeDES;

    private String  orgType   = "PERSONAL"; //组织机构类型；SUPPLIER=商户；SHOP=门店；CORPORATION=企业客户；PERSONAL=个人用户，个人用户不与UserOrg对象发生关联

    private String  registerChannel;        //注册渠道(通过哪个渠道注册的)


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum == null ? null : mobilenum.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id == null ? null : Id.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage == null ? null : headimage.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPaymentcodemd5() {
        return paymentcodemd5;
    }

    public void setPaymentcodemd5(String paymentcodemd5) {
        this.paymentcodemd5 = paymentcodemd5 == null ? null : paymentcodemd5.trim();
    }

    /**
     * pos机对支付密码使用des加密
     */
    public String getPaymentCodeDES() {
        return paymentCodeDES;
    }

    /**
     * pos机对支付密码使用des加密
     */
    public void setPaymentCodeDES(String paymentCodeDES) {
        this.paymentCodeDES = paymentCodeDES;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegisterChannel() {
        return registerChannel;
    }

    public void setRegisterChannel(String registerChannel) {
        this.registerChannel = registerChannel;
    }

    public String getPasswordPlaintext() {
        return passwordPlaintext;
    }

    public void setPasswordPlaintext(String passwordPlaintext) {
        this.passwordPlaintext = passwordPlaintext;
    }

    public String getPasswordFirst() {
        return passwordFirst;
    }

    public void setPasswordFirst(String passwordFirst) {
        this.passwordFirst = passwordFirst;
    }

    /* (non-Javadoc)
     * 
     * @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "User [userId=" + userId + ", loginType=" + loginType + ", mobilenum=" + mobilenum + ", email=" + email + ", userName=" + userName + ", Id=" + Id + ", name=" + name + ", nickname="
                + nickname + ", headimage=" + headimage + ", insertTime=" + insertTime + ", updateTime=" + updateTime + ", password=" + password + ", passwordFirst=" + passwordFirst
                + ", passwordPlaintext=" + passwordPlaintext + ", paymentcodemd5=" + paymentcodemd5 + ", paymentCodeDES=" + paymentCodeDES + ", orgType=" + orgType + ", registerChannel="
                + registerChannel + "]";
    }

}