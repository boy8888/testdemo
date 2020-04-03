package com.hummingbird.maaccount.vo;

import com.hummingbird.maaccount.face.Pagingnation;

public class JifenVO {
	/* "query":{
        "pageSize":10,"pageIndex":1,
        "mobileNum":"13912345678",
        "jifenProductId":"HONGBAO_YYD|HONGBAO_YYD01|HONGBAO_YYD02",
    }*/
	protected Integer pageSize;
	protected Integer pageIndex;
	protected String mobileNum;
	protected String jifenProductId;
	protected String expireIn;
	
	@Override
	public String toString() {
		return "RedPaperVO [pageIndex=" + pageIndex + ", pageIndex=" + pageIndex + ", mobileNum=" + mobileNum + ", "
				+ "jifenProductId=" + jifenProductId + " expireIn=" + expireIn +"]";
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
	
	public String getJifenProductId() {
		return jifenProductId;
	}

	public void setJifenProductId(String jifenProductId) {
		this.jifenProductId = jifenProductId;
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
}
