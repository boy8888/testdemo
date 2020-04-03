package com.hummingbird.maaccount.vo;
/**
 * @author YJY 
 * @since 2015年10月30日15:54:4
 * */
public class AccountPayAllowAddProductBodyVO {
	
	private String accountId;
	private String groupId;
	private String[] zjproducts;
	
	
	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public String getGroupId() {
		return groupId;
	}


	public String[] getZjproducts() {
		return zjproducts;
	}


	public void setZjproducts(String[] zjproducts) {
		this.zjproducts = zjproducts;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	@Override
	public String toString() {
		return " AccountPayAllowAddProductBodyVO [accountId=" + accountId + ", groupId=" + groupId +"]";
	}
}
