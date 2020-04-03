/**
 * 
 * OrderQueryPagingVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.maaccount.face.Pagingnation;

/**
 * @author huangjiej_2
 * 2015年1月8日 下午10:15:48
 * 本类主要做为 消费订单查询条件
 */
public class SpendOrderQueryPagingVO extends OrderQueryPagingVO{

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	/**
	 * 构造函数
	 */
	public SpendOrderQueryPagingVO() {
	}
	
	protected int pageSize ;
	
	protected int pageIndex;
	
	
	protected List<String> type;
	
	/**
	 * 商户
	 */
	protected List<String> sellerIds;
	
	
	/**
	 * 门店
	 */
	protected List<String> storeIds;
	/**
	 * 终端
	 */
	protected List<String> terminalId;

	/**
	 * 只查询成功的消费记录
	 */
	protected boolean justSuccess;
	
	
	
	
	
	public List<String> getType() {
		return type;
	}
	
	public void setType(List<String> type) {
		this.type = type;
	}

	/**
	 * @return the sellerIds
	 */
	public List<String> getSellerIds() {
		return sellerIds;
	}

	/**
	 * @param sellerIds the sellerIds to set
	 */
	public void setSellerIds(List<String> sellerIds) {
		this.sellerIds = sellerIds;
	}


	/**
	 * @return the storeIds
	 */
	public List<String> getStoreIds() {
		return storeIds;
	}

	/**
	 * @param storeIds the storeIds to set
	 */
	public void setStoreIds(List<String> storeIds) {
		this.storeIds = storeIds;
	}
	
	public Date getSearchStart() throws ParseException{
		if(StringUtils.isNotBlank(getStartDate())){
			return  DateUtils.parseDate(getStartDate(),new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd"});
		}
		if(StringUtils.isNotBlank(getStartTime())){
			return  DateUtils.parseDate(getStartTime(),new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd"});
		}
		return null;
	}
	
	public Date getSearchEnd() throws ParseException{
			if(StringUtils.isNotBlank(getEndDate())){
				return  DateUtils.parseDate(getEndDate(),new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd"});
			}
			if(StringUtils.isNotBlank(getEndTime())){
				return  DateUtils.parseDate(getEndTime(),new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd"});
			}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.vo.PagingnationVO#toPagingnation()
	 */
	@Override
	public Pagingnation toPagingnation() {
		//如果没有值,则不进行分页
		if(getPageIndex()<=0){
			return null;
		}
		if(getPageSize()<=0){
			return null;
		}
		if(pageSize>500){
			pageSize=500;
		}
		return  new Pagingnation(pageIndex, pageSize);
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 是否只查询成功的记录
	 * @param b
	 */
	public void setJustSuccess(boolean justSuccess) {
		this.justSuccess = justSuccess;
		
	}

	/**
	 * @return the justSuccess
	 */
	public boolean isJustSuccess() {
		return justSuccess;
	}
//	/**
//	 * @return the justSuccess
//	 */
//	public boolean getJustSuccess() {
//		return justSuccess;
//	}

	/**
	 * 终端 
	 */
	public List<String> getTerminalId() {
		return terminalId;
	}

	/**
	 * 终端 
	 */
	public void setTerminalId(List<String> terminalId) {
		this.terminalId = terminalId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SpendOrderQueryPagingVO [log=" + log + ", pageSize=" + pageSize + ", pageIndex=" + pageIndex + ", type="
				+ type + ", sellerIds=" + sellerIds + ", storeIds=" + storeIds
				+ ", terminalId=" + terminalId + ", justSuccess=" + justSuccess + "]";
	}

	
	

}
