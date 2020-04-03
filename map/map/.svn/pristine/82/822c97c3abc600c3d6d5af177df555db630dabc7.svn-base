package com.hummingbird.maaccount.entity;


/**
 * 帐户终端消费时间记录表，记录具体的帐户（卡号）允许消费的情况
 */
public class AccountPayTime {
    /**
     * 自增序号
     */
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
     * 消费允许开始时间,格式为 hhmmss
     */
    private String begintime;

    /**
     * 消费允许结束时间,格式为 hhmmss
     */
    private String endtime;


    /**
     * 月开始时间,如无设置,则为0
     */
    private Integer monstart;

    /**
     * 月结束时间,如无设置,则为31
     */
    private Integer monend;

    /**
     * 周开始时间,如无设置,则为0
     */
    private Integer weekstart;

    /**
     * 周结束时间,如无设置,则为7
     */
    private Integer weekend;

    /**
     * @return 自增序号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            自增序号
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * @return 消费允许开始时间,格式为 hhmmss
     */
    public String getBegintime() {
        return begintime;
    }

    /**
     * @param begintime 
	 *            消费允许开始时间,格式为 hhmmss
     */
    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    /**
     * @return 消费允许结束时间,格式为 hhmmss
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * @param endtime 
	 *            消费允许结束时间,格式为 hhmmss
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }


    /**
     * @return 月开始时间,如无设置,则为0
     */
    public Integer getMonstart() {
        return monstart;
    }

    /**
     * @param monstart 
	 *            月开始时间,如无设置,则为0
     */
    public void setMonstart(Integer monstart) {
        this.monstart = monstart;
    }

    /**
     * @return 月结束时间,如无设置,则为31
     */
    public Integer getMonend() {
        return monend;
    }

    /**
     * @param monend 
	 *            月结束时间,如无设置,则为31
     */
    public void setMonend(Integer monend) {
        this.monend = monend;
    }

    /**
     * @return 周开始时间,如无设置,则为0
     */
    public Integer getWeekstart() {
        return weekstart;
    }

    /**
     * @param weekstart 
	 *            周开始时间,如无设置,则为0
     */
    public void setWeekstart(Integer weekstart) {
        this.weekstart = weekstart;
    }

    /**
     * @return 周结束时间,如无设置,则为7
     */
    public Integer getWeekend() {
        return weekend;
    }

    /**
     * @param weekend 
	 *            周结束时间,如无设置,则为7
     */
    public void setWeekend(Integer weekend) {
        this.weekend = weekend;
    }

	/**
	 * 判断当前对象 是否 涵盖了指定的对象
	 * @param apt
	 * @return
	 */
	public boolean include(AccountPayTime apt) {
		return this.getMonstart()<=apt.getMonstart()
				&& this.getMonend() >= apt.getMonend()
				&& this.weekstart <= apt.weekstart
				&& this.weekend >= apt.weekend
				&& this.begintime.compareTo(apt.begintime)<=0
				&& this.endtime.compareTo(apt.endtime)>0;
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
		return "AccountPayTime [id=" + id + ", accountId=" + accountId + ", groupId=" + groupId + ", begintime="
				+ begintime + ", endtime=" + endtime + ", monstart=" + monstart + ", monend=" + monend + ", weekstart="
				+ weekstart + ", weekend=" + weekend + "]";
	}
}