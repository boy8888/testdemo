package com.hummingbird.maaccount.entity;

/**
 * 帐户终端消费描述表，描述消费的设置
 */
public class AccountPayAllow {
    private Integer id;

    /**
     * 帐号id
     */
    private String accountId;

    /**
     * 组id，一个组可以生成多条记录
     */
    private String groupId;

    /**
     * 终端编码多个终端以逗号分隔
     */
    private String terminalids;

    /**
     * 消费允许时间,0-6 表示 0点到6点, 7,10 表示7点和10点可以消费,多个以逗号分隔
     */
    private String periodtime;

    /**
     * 记录可消费的产品，多个用逗号隔开
     */
    private String zjproduct;

    /**
     * 描述
     */
    private String description;

    /**
     * 周期类型,DAY 天,MON 月 ,WEK 周
     */
    private String cycletype;

    /**
     * 周期, 如类型为周,0,1,2 分别为 周日,周一,周二 ,类型为月, 22,14 则为 14号,22号 , 类型为日时 ,则忽略此值
     */
    private String cycledate;

    /**
     * 状态,END已结束,OK#正常
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * @return 终端编码多个终端以逗号分隔
     */
    public String getTerminalids() {
        return terminalids;
    }

    /**
     * @param terminalids 
	 *            终端编码多个终端以逗号分隔
     */
    public void setTerminalids(String terminalids) {
        this.terminalids = terminalids == null ? null : terminalids.trim();
    }

    /**
     * @return 消费允许时间,0-6 表示 0点到6点, 7,10 表示7点和10点可以消费,多个以逗号分隔
     */
    public String getPeriodtime() {
        return periodtime;
    }

    /**
     * @param periodtime 
	 *            消费允许时间,0-6 表示 0点到6点, 7,10 表示7点和10点可以消费,多个以逗号分隔
     */
    public void setPeriodtime(String periodtime) {
        this.periodtime = periodtime == null ? null : periodtime.trim();
    }

    /**
     * @return 记录可消费的产品，多个用逗号隔开
     */
    public String getZjproduct() {
        return zjproduct;
    }

    /**
     * @param zjproduct 
	 *            记录可消费的产品，多个用逗号隔开
     */
    public void setZjproduct(String zjproduct) {
        this.zjproduct = zjproduct == null ? null : zjproduct.trim();
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return 周期类型,DAY 天,MON 月 ,WEK 周
     */
    public String getCycletype() {
        return cycletype;
    }

    /**
     * @param cycletype 
	 *            周期类型,DAY 天,MON 月 ,WEK 周
     */
    public void setCycletype(String cycletype) {
        this.cycletype = cycletype == null ? null : cycletype.trim();
    }

    /**
     * @return 周期, 如类型为周,0,1,2 分别为 周日,周一,周二 ,类型为月, 22,14 则为 14号,22号 , 类型为日时 ,则忽略此值
     */
    public String getCycledate() {
        return cycledate;
    }

    /**
     * @param cycledate 
	 *            周期, 如类型为周,0,1,2 分别为 周日,周一,周二 ,类型为月, 22,14 则为 14号,22号 , 类型为日时 ,则忽略此值
     */
    public void setCycledate(String cycledate) {
        this.cycledate = cycledate == null ? null : cycledate.trim();
    }

    /**
     * @return 状态,END已结束,OK#正常
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态,END已结束,OK#正常
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	/**
	 * 帐号id 
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * 帐号id 
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * 组id，一个组可以生成多条记录 
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * 组id，一个组可以生成多条记录 
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccountPayAllow [id=" + id + ", accountId=" + accountId + ", groupId=" + groupId + ", terminalids="
				+ terminalids + ", periodtime=" + periodtime + ", zjproduct=" + zjproduct + ", description="
				+ description + ", cycletype=" + cycletype + ", cycledate=" + cycledate + ", status=" + status + "]";
	}
}