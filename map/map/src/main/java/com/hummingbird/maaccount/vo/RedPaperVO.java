package com.hummingbird.maaccount.vo;

import com.hummingbird.maaccount.face.Pagingnation;


public class RedPaperVO  {
	/*"query":{
    "pageSize":10,"pageIndex":1,
    "mobileNum":"13912345678",
    "redPaperProductId":"HONGBAO_YYD|HONGBAO_YYD01|HONGBAO_YYD02",
    "expireIn":"ALL"有效期内，YES-表示在有效期内的，NO#-表示不在有效期内的，ALL-包括YES和NO#的
}*/
	protected Integer pageSize;
	protected Integer pageIndex;
	protected String mobileNum;
	protected String redPaperProductId;
	protected String redPaperAccountId;
	protected String startTime;
	protected String endTime;
	protected String expireIn;
	protected String status;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RedPaperVO [pageSize=" + pageSize + ", pageIndex=" + pageIndex
				+ ", mobileNum=" + mobileNum + ", redPaperProductId="
				+ redPaperProductId +", redPaperAccountId"+redPaperAccountId+ ", expireIn=" + expireIn + ", startTime=" + startTime + ", endTime=" + endTime + ", status="
				+ status + "]";
	}
	
	
	public String getRedPaperAccountId() {
		return redPaperAccountId;
	}


	public void setRedPaperAccountId(String redPaperAccountId) {
		this.redPaperAccountId = redPaperAccountId;
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


	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getRedPaperProductId() {
		return redPaperProductId;
	}
	public void setRedPaperProductId(String redPaperProductId) {
		this.redPaperProductId = redPaperProductId;
	}
	public String getExpireIn() {
		return expireIn;
	}
	public void setExpireIn(String expireIn) {
		this.expireIn = expireIn;
	}

	/**
	 * @return
	 */
	public Pagingnation toPagingnation() {
		if(pageIndex<0){
			pageIndex=0;
		}
		if(pageSize<=0){
			pageSize=10;
		}
		if(pageSize>500){
			pageSize=500;
		}
		return  new Pagingnation(pageIndex, pageSize);
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
