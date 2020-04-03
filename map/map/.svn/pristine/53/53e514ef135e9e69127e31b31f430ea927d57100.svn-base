package com.hummingbird.maaccount.vo;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.hummingbird.common.util.convertor.JacksonDateTimeSerializer;

public class QueryUserCardListBaseResultVO {
	protected String accountId;
	protected Long amount;
	protected Date startTime;
	protected Date endTime;
	protected String status;
	
	@Override
	public String toString() {
		return "QueryUserCardListBaseResultVO [accountId=" + accountId + 
				", amount"+amount+",startTime"+startTime+
				",endTime"+endTime+",status"+status+"]";
	}
	/**
	 * 构造函数
	 */
	public QueryUserCardListBaseResultVO() {
		super();
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	@JsonSerialize(using = JacksonDateTimeSerializer.class)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonSerialize(using = JacksonDateTimeSerializer.class)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
