package com.hummingbird.maaccount.util.xuanwuSMS;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import com.esms.MessageData;
import com.esms.common.entity.MTPack;
import com.esms.common.entity.MTPack.MsgType;
import com.esms.common.entity.MTPack.SendType;

public class MTPackBuilder {

    private UUID                batchID;          //批次号，可以不设置

    private String              batchName;        //批次名称，可以不设置

    private SendType            sendType;         //发送类型，默认值为群发

    private MsgType             msgType;          //消息类型，默认值为短信

    private Map<String, String> msgs;             //号码为key,消息为value。必须有值

    private int                 bizType      = -1;//信息业务类型,对应于BusinessType(业务类型)的ID，可以不设置

    private Boolean             distinctFlag;     //是否过滤重复号码（默认：false）

    private long                scheduleTime = -1;//计划发送时间，可以不设置

    private String              remark;           //备注，可以不设置

    private String              customNum;        //用户扩展码，可以不设置

    private long                deadline     = -1;//下发截至时间，可以不设置


    public static MTPackBuilder create() {
        return new MTPackBuilder();
    }

    public MTPack build() {
        if (msgs == null || msgs.size() == 0) { throw new RuntimeException("短信无地址和内容"); }
        MTPack pack = new MTPack();
        if (batchID != null) {
            pack.setBatchID(batchID);
        }
        if (batchName != null) {
            pack.setBatchName(batchName);
        }
        if (sendType != null) {
            pack.setSendType(sendType);
        }
        if (msgType != null) {
            pack.setMsgType(msgType);
        }
        if (bizType != -1) {
            pack.setBizType(bizType);
        }
        if (distinctFlag != null) {
            pack.setDistinctFlag(distinctFlag);
        }
        if (scheduleTime != -1) {
            pack.setScheduleTime(scheduleTime);
        }
        if (remark != null) {
            pack.setRemark(remark);
        }
        if (customNum != null) {
            pack.setCustomNum(customNum);
        }
        if (deadline != -1) {
            pack.setDeadline(deadline);
        }
        if (msgs != null && msgs.size() > 0) {
            ArrayList<MessageData> msgList = new ArrayList<MessageData>();
            for (String key : msgs.keySet()) {
                msgList.add(new MessageData(key, msgs.get(key)));
            }
            pack.setMsgs(msgList);
        }
        return pack;
    }

    public UUID getBatchID() {
        return batchID;
    }

    public MTPackBuilder setBatchID(UUID batchID) {
        this.batchID = batchID;
        return this;
    }

    public String getBatchName() {
        return batchName;
    }

    public MTPackBuilder setBatchName(String batchName) {
        this.batchName = batchName;
        return this;
    }

    public SendType getSendType() {
        return sendType;
    }

    public MTPackBuilder setSendType(SendType sendType) {
        this.sendType = sendType;
        return this;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public MTPackBuilder setMsgType(MsgType msgType) {
        this.msgType = msgType;
        return this;
    }

    public Map<String, String> getMsgs() {
        return msgs;
    }

    public MTPackBuilder setMsgs(Map<String, String> msgs) {
        this.msgs = msgs;
        return this;
    }

    public int getBizType() {
        return bizType;
    }

    public MTPackBuilder setBizType(int bizType) {
        this.bizType = bizType;
        return this;
    }

    public Boolean getDistinctFlag() {
        return distinctFlag;
    }

    public MTPackBuilder setDistinctFlag(Boolean distinctFlag) {
        this.distinctFlag = distinctFlag;
        return this;
    }

    public long getScheduleTime() {
        return scheduleTime;
    }

    public MTPackBuilder setScheduleTime(long scheduleTime) {
        this.scheduleTime = scheduleTime;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public MTPackBuilder setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getCustomNum() {
        return customNum;
    }

    public MTPackBuilder setCustomNum(String customNum) {
        this.customNum = customNum;
        return this;
    }

    public long getDeadline() {
        return deadline;
    }

    public MTPackBuilder setDeadline(long deadline) {
        this.deadline = deadline;
        return this;
    }

}
