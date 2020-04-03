package com.hummingbird.maaccount.vo;
//交易对账接口返回数据
public class TokenCheckResultVO {
	/*{
	    "check":{
	    	"checkDate":
	        "chargeCount":1000,
	        "chargeSum":100000,
	        "cancelCount":1,
	        "cancelSum":100
	    }
	}*/
	private String checkDate;
	private Integer chargeCount;
	private Long chargeSum;
	private Integer cancelCount;
	private Long cancelSum;
	
	public String toString() {
		return "TokenCheckResultVO [checkDate="+checkDate+"chargeCount=" + chargeCount + ", chargeSum=" + chargeSum
				+ ", cancelCount=" + cancelCount + ", cancelSum=" + cancelSum + "]";
	}

	public Integer getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(Integer chargeCount) {
		this.chargeCount = chargeCount;
	}

	public Long getChargeSum() {
		return chargeSum;
	}

	public void setChargeSum(Long chargeSum) {
		this.chargeSum = chargeSum;
	}

	public Integer getCancelCount() {
		return cancelCount;
	}

	public void setCancelCount(Integer cancelCount) {
		this.cancelCount = cancelCount;
	}

	public Long getCancelSum() {
		return cancelSum;
	}

	public void setCancelSum(Long cancelSum) {
		this.cancelSum = cancelSum;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
	
}
