package com.hummingbird.maaccount.vo;

public class BatchOpenOnlineResultDetailVO {
	/* "card":{
	    "accountId":"1234123412341234",
	    "amount":400000,
	    "balance":300000,
	    "startTime":"2015-01-01 00:00:00",
	    "endTime":"2015-05-31 24:00:00",
	    "status":"正常"
		}
	}*/
	private String accountId;
	private Long amount;
	private Long balance;
	private String startTime;
	private String endTime;
	private String status;

	
	
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
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchOpenOnlineResultDetailVO [accountId=" + accountId
				+ ", amount=" + amount + ", balance=" + balance
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", status=" + status + "]";
	}
	
}
