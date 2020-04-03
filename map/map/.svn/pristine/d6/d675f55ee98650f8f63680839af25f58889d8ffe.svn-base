package com.hummingbird.maaccount.vo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.face.Pagingnation;

public class QueryUserCardListDetailVO {
	/*"body":{
	        "pageSize":10,"pageIndex":1,
	        "mobileNum":"13912345678",
	        "startDate":"2015-07-01",
	        "endDate":"2015-07-10",
	        "types":["VCA","DCA","OCA"],
	        "status":["END","OK#","NOP","FRZ"],
	        "channelNo":"渠道",
	        "queryCardSource":true
	    }*/
	protected Integer pageSize;
	protected Integer pageIndex;
	protected String mobileNum;
	protected List<String> types;
	protected List<String> status;
	protected String channelNo;
	protected String startDate;
	protected String endDate;
	/**
	 * 产品 id 未实现 
	 */
	protected String productId;
	protected Boolean queryCardSource;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QueryUserCardListDetailVO [pageSize=" + pageSize + ", pageIndex=" + pageIndex + ", mobileNum="
				+ mobileNum + ", types=" + types + ", status=" + status + ", channelNo=" + channelNo + ", startDate="
				+ startDate + ", endDate=" + endDate + ", productId=" + productId + ", queryCardSource="
				+ queryCardSource + "]";
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
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
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
	public Boolean getQueryCardSource() {
		return queryCardSource;
	}
	public void setQueryCardSource(Boolean queryCardSource) {
		this.queryCardSource = queryCardSource;
	}
	public Date getSearchStart() throws ParseException{
		if(StringUtils.isNotBlank(getStartDate())){
			return  DateUtil.toDayStart(getStartDate());
		}
		return null;
	}
	
	public Date getSearchEnd() throws ParseException{
			if(StringUtils.isNotBlank(getEndDate())){
				return  DateUtil.toDayEnd(getEndDate());
			}
			
		return null;
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
	 * #{bare_field_comment} 
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * #{bare_field_comment} 
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
