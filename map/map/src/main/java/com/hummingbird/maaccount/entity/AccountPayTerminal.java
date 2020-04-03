package com.hummingbird.maaccount.entity;

/**
 * 帐户终端消费记录表，记录具体的帐户（卡号）允许消费的情况
 */
public class AccountPayTerminal {
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
     * 终端编码，如果终端编码为ALL_TERMINAL_CAN时，表示所有终端都可以消费
     */
    private String terminalId;


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
     * @return 终端编码，如果终端编码为ALL_TERMINAL_CAN时，表示所有终端都可以消费
     */
    public String getTerminalId() {
        return terminalId;
    }

    /**
     * @param terminalId 
	 *            终端编码，如果终端编码为ALL_TERMINAL_CAN时，表示所有终端都可以消费
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId == null ? null : terminalId.trim();
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((terminalId == null) ? 0 : terminalId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AccountPayTerminal))
			return false;
		AccountPayTerminal other = (AccountPayTerminal) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (terminalId == null) {
			if (other.terminalId != null)
				return false;
		} else if (!terminalId.equals(other.terminalId))
			return false;
		return true;
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
}