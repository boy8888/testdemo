package com.hummingbird.maaccount.vo;

import java.util.Date;

public class QueryUserCardListOilResultVO extends QueryUserCardListBaseResultVO{
	protected Integer restStages;//剩余分期数
	protected Long restAmount;//剩余金额
	protected Integer totalStages;//总分期数
	@Override
	public String toString() {
		return "QueryUserCardListOilResultVO [accountId=" + accountId + 
				", amount"+amount+",startTime"+startTime+
				",endTime"+endTime+",status"+status+
				",restStages"+restStages+",totalStages"+totalStages+
				",restAmount"+restAmount+"]";
	}
	public Integer getRestStages() {
		return restStages;
	}
	public void setRestStages(Integer restStages) {
		this.restStages = restStages;
	}
	
	public Long getRestAmount() {
		return restAmount;
	}
	public void setRestAmount(Long restAmount) {
		this.restAmount = restAmount;
	}
	public Integer getTotalStages() {
		return totalStages;
	}
	public void setTotalStages(Integer totalStages) {
		this.totalStages = totalStages;
	}
	
}
