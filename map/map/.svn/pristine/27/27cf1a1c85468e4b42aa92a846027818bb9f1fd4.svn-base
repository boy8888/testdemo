package com.hummingbird.maaccount.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/*"bind":{
    "mobileNum":"13912345678",
    "bankName":"建设银行",
    "branchName":"支行名称"
    "cardNo":"5600101981111123456",
    "name":"张三",
    "ID":"5600101981111123456"
}*/
public class BindVO {
	
	protected String mobileNum;
	
	protected String bankName;
	protected String branchName;
	protected String cardNo;
	protected String name;
	protected String area;
	protected String ID;
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getID() {
		return ID;
	}
	
	@JsonProperty("ID")
	public void setID(String iD) {
		ID = iD;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BindVO [mobileNum=" + mobileNum + ", bankName="
				+ bankName + ", branchName=" + branchName + ", "
				+ "name=" + name + " cardNo=" + cardNo + "area=" +area+ ", ID=" + ID +"]";
	}
}
