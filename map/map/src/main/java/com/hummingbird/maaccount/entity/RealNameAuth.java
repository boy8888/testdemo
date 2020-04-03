package com.hummingbird.maaccount.entity;

import java.util.Date;

/**
 * 实名认证表
 */
public class RealNameAuth {
    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 证件类型，ID#-身份证，CO#-军官证，PP#-护照
     */
    private String idtype;

    /**
     * 证件号码
     */
    private String idcode;

    /**
     * 证件图片
     */
    private String picture;

    /**
     * 证件图片2
     */
    private String picture2;

    /**
     * 状态，OK#-已经通过实名认证，CHK-正在实名审核，NRN-创建/未实名认证，FLS-实名认证失败
     */
    private String status;

    /**
     * 审核失败原因
     */
    private String reason;

    /**
     * 审核时间
     */
    private Date checktime;

    /**
     * 创建时间
     */
    private Date inserttime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 备注，用于申请时，填写一些备注信息
     */
    private String remark;

    /**
     * 实名认证申请时间
     */
    private Date applytime;

    /**
     * @return 用户ID
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            用户ID
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return 真实姓名
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            真实姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 证件类型，ID#-身份证，CO#-军官证，PP#-护照
     */
    public String getIdtype() {
        return idtype;
    }

    /**
     * @param idtype 
	 *            证件类型，ID#-身份证，CO#-军官证，PP#-护照
     */
    public void setIdtype(String idtype) {
        this.idtype = idtype == null ? null : idtype.trim();
    }

    /**
     * @return 证件号码
     */
    public String getIdcode() {
        return idcode;
    }

    /**
     * @param idcode 
	 *            证件号码
     */
    public void setIdcode(String idcode) {
        this.idcode = idcode == null ? null : idcode.trim();
    }

    /**
     * @return 证件图片
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture 
	 *            证件图片
     */
    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    /**
     * @return 证件图片2
     */
    public String getPicture2() {
        return picture2;
    }

    /**
     * @param picture2 
	 *            证件图片2
     */
    public void setPicture2(String picture2) {
        this.picture2 = picture2 == null ? null : picture2.trim();
    }

    /**
     * @return 状态，OK#-已经通过实名认证，CHK-正在实名审核，NRN-创建/未实名认证，FLS-实名认证失败
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态，OK#-已经通过实名认证，CHK-正在实名审核，NRN-创建/未实名认证，FLS-实名认证失败
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 审核失败原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason 
	 *            审核失败原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * @return 审核时间
     */
    public Date getChecktime() {
        return checktime;
    }

    /**
     * @param checktime 
	 *            审核时间
     */
    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    /**
     * @return 创建时间
     */
    public Date getInserttime() {
        return inserttime;
    }

    /**
     * @param inserttime 
	 *            创建时间
     */
    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime 
	 *            更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * @return 备注，用于申请时，填写一些备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注，用于申请时，填写一些备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return 实名认证申请时间
     */
    public Date getApplytime() {
        return applytime;
    }

    /**
     * @param applytime 
	 *            实名认证申请时间
     */
    public void setApplytime(Date applytime) {
        this.applytime = applytime;
    }
}