package com.hummingbird.maaccount.vo;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.entity.CashAccount;
import com.hummingbird.maaccount.entity.RealNameAuth;
import com.hummingbird.maaccount.entity.RedPaperAccount;
import com.hummingbird.maaccount.entity.User;
//
public class CustomerVO {
	/* "customer":{
		    "name":"张三",
		    "idType":"ID#",
		    "idCode":"441233343143432141413"
		}*/
	private String name;
	private String idType;
	private String idCode;
	public String toString() {
		return "CustomerVO [name=" + name + ", idType=" + idType
				+ ", idCode=" + idCode + "]";
	}
	public CustomerVO(RealNameAuth user){
		name = user.getName();
		idType=user.getIdtype();
		idCode=user.getIdcode();
	}
	public CustomerVO(){
		name = "";
		idType="";
		idCode="";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	
}
