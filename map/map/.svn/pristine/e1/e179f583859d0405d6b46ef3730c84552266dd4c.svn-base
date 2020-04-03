package com.hummingbird.maaccount.entity;

/**
 * 帐户终端消费油品记录表，记录具体的帐户（卡号）允许消费的情况
 */
public class AccountPayProduct {
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
     * 中经产品id
     */
    private String zjproductId;

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
     * @return 帐号id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountid 
	 *            帐号id
     */
    public void setAccountId(String accountid) {
        this.accountId = accountid == null ? null : accountid.trim();
    }

    /**
     * @return 组id，一个组可以生成多条记录
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupid 
	 *            组id，一个组可以生成多条记录
     */
    public void setGroupId(String groupid) {
        this.groupId = groupid == null ? null : groupid.trim();
    }

    /**
     * @return 中经产品id
     */
    public String getZjproductId() {
        return zjproductId;
    }

    /**
     * @param zjproductId 
	 *            中经产品id
     */
    public void setZjproductId(String zjproductId) {
        this.zjproductId = zjproductId == null ? null : zjproductId.trim();
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
		result = prime * result + ((zjproductId == null) ? 0 : zjproductId.hashCode());
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
		if (!(obj instanceof AccountPayProduct))
			return false;
		AccountPayProduct other = (AccountPayProduct) obj;
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
		if (zjproductId == null) {
			if (other.zjproductId != null)
				return false;
		} else if (!zjproductId.equals(other.zjproductId))
			return false;
		return true;
	}
}