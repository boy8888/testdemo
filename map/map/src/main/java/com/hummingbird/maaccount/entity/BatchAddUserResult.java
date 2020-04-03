package com.hummingbird.maaccount.entity;

import java.util.Date;

/**
 * 批量开卡结果表
 */
public class BatchAddUserResult {
    /**
     * 批次号
     */
    private Integer batchId;

    /**
     * 总开卡申请
     */
    private Integer totalRequest;

    /**
     * 成功开卡数
     */
    private Integer successdRequest;

    /**
     * 渠道号
     */
    private String channelNo;

    /**
     * 请求时间
     */
    private Date insertTime;

    /**
     * 处理结果,OK#-处理成功,FLS-处理失败,这里的失败指无法执行批量的情况
     */
    private String status;

    /**
     * 处理结果
     */
    private String errMsg;

    /**
     * 处理耗时,单位为秒
     */
    private Integer spentTime;

    private String channelName;

    /**
     * @return 批次号
     */
    public Integer getBatchId() {
        return batchId;
    }

    /**
     * @param batchId 
	 *            批次号
     */
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    /**
     * @return 总开卡申请
     */
    public Integer getTotalRequest() {
        return totalRequest;
    }

    /**
     * @param totalRequest 
	 *            总开卡申请
     */
    public void setTotalRequest(Integer totalRequest) {
        this.totalRequest = totalRequest;
    }

    /**
     * @return 成功开卡数
     */
    public Integer getSuccessdRequest() {
        return successdRequest;
    }

    /**
     * @param successdRequest 
	 *            成功开卡数
     */
    public void setSuccessdRequest(Integer successdRequest) {
        this.successdRequest = successdRequest;
    }

    /**
     * @return 渠道号
     */
    public String getChannelNo() {
        return channelNo;
    }

    /**
     * @param channelNo 
	 *            渠道号
     */
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo == null ? null : channelNo.trim();
    }

    /**
     * @return 请求时间
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * @param insertTime 
	 *            请求时间
     */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * @return 处理结果,OK#-处理成功,FLS-处理失败,这里的失败指无法执行批量的情况
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            处理结果,OK#-处理成功,FLS-处理失败,这里的失败指无法执行批量的情况
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 处理结果
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * @param errMsg 
	 *            处理结果
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }

    /**
     * @return 处理耗时,单位为秒
     */
    public Integer getSpentTime() {
        return spentTime;
    }

    /**
     * @param spentTime 
	 *            处理耗时,单位为秒
     */
    public void setSpentTime(Integer spentTime) {
        this.spentTime = spentTime;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }
}