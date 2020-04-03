package com.hummingbird.maaccount.vo;

public class TokenCheckVO {
	 /*"check":{
		    "startDate":"20150505", "endDate":"20150506"
		}*/
	private String startDate;
	private String endDate;
	@Override
	public String toString() {
		return "TokenCheckVO [startDate=" + startDate  +" endDate=" + endDate  + "]";
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
