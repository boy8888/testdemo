package com.hummingbird.maaccount.vo;

import java.util.List;

import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.OilcardAccount;
import com.hummingbird.maaccount.face.Account;

public class QueryUserCardListResultVO extends QueryUserCardListBaseResultVO{
	/*"list":[{
	    "accountId":"1234123412341234",
	    "amount":400000,
	    "startTime":"2015-01-01 00:00:00",
	    "endTime":"2015-05-31 24:00:00",
	    "status":"OK#",
	    "description":"300元加油卡不限油品限厦门地区使用"
	    "ext":{}
	    }]
	}*/
	protected String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	protected List<String> ext;
	@Override
	public String toString() {
		return "QueryUserCardListResultVO [accountId=" + accountId + 
				", amount"+amount+
				",description"+description+",ext"+ext+",startTime"+startTime+
				",endTime"+endTime+",status"+status+"]";
	}
	/**
	 * 构造函数
	 */
	public QueryUserCardListResultVO() {
		super();
	}
	/**
	 * 构造函数
	 */
	public QueryUserCardListResultVO(QueryUserCardListBaseResultVO query) {
		accountId=query.getAccountId();
		amount=query.getAmount();
		description="";
		startTime=query.getStartTime();
		endTime=query.getEndTime();
		status=query.getStatus();
		ext=null;
	}
	/**
	 * 构造函数
	 */
	public QueryUserCardListResultVO(QueryUserCardListOilResultVO query) {
		accountId=query.getAccountId();
		amount=query.getAmount();
		StringBuilder sb = new StringBuilder();
		description=sb.append("一共有").append(query.getTotalStages()).append("期，剩余").append(query.getRestStages()).append("期待返还，剩余共").append(query.getRestAmount()).append("元。").toString();
		startTime=query.getStartTime();
		endTime=query.getEndTime();
		status=query.getStatus();
		ext=null;
	}
	
	public List<String> getExt() {
		return ext;
	}
	public void setExt(List<String> ext) {
		this.ext = ext;
	}
	
}
