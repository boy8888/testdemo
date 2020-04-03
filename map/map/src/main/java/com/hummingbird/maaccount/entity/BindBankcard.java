package com.hummingbird.maaccount.entity;

/**
 * 绑定银行卡表
 */
public class BindBankcard {
    /**
     * 自增序号
     */
    private Integer id;

    /**
     * 用户内部ID
     */
    private Integer userId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 分行名称
     */
    private String branchName;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * @return 自增序号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 银行所在地区名称
     */
    private String area;
    
    /**
     * @param id 
	 *            自增序号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 用户内部ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userid 
	 *            用户内部ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return 银行名称
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankname 
	 *            银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * @return 分行名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchname 
	 *            分行名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    /**
     * @return 银行卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardno 
	 *            银行卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
    
}