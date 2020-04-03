package com.hummingbird.maaccount.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;

public class BatchOpenOnlineDetailVO implements IOrderVO {
   /* 
    "order":
    {
           "channelNo":"channel001",
            "appOrderId":"123456789", 
            orders:[{
	            "ID":"515411244445444444X", 
	            "name":"李四", 
	            "mobileNum":"13912345678", 
	            "channelOrderId":"channel1234567890",
	            "productId":"10",
	            "remark":"某某渠道为用户13912345678开卡"}]
    }*/	
	private String channelNo;
	private String appOrderId;
	private List<BatchOpenOnlineListVO> orders;
	/**
	 * 加密的渠道key
	 */
	private String channelKey;
	public String toString() {
		return "BatchOpenOnlineDetailVO [channelNo=" + channelNo 
				+ ", appOrderId="+ appOrderId 
				+ "orders=" +orders +"]";
	}
	@Override
	public void selfvalidate() throws DataInvalidException {
		ValidateUtil.assertNull(this.channelNo, "渠道编码");
		ValidateUtil.assertNull(this.appOrderId, "app自定义订单号");
		for(BatchOpenOnlineListVO order:orders){
			order.selfvalidate();
		}
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getAppOrderId() {
		return appOrderId;
	}
	public void setAppOrderId(String appOrderId) {
		this.appOrderId = appOrderId;
	}
	public List<BatchOpenOnlineListVO> getOrders() {
		return orders;
	}
	public void setOrders(List<BatchOpenOnlineListVO> orders) {
		this.orders = orders;
	}
	@Override
	public String getPaintText() {
		List<String> pts = new ArrayList<String>();
		for (BatchOpenOnlineListVO batchOpenOnlineListVO:orders) {
			pts.add(batchOpenOnlineListVO.getPaintText());
		}
		pts.add(channelNo);
		pts.add(appOrderId);
		pts.add(channelKey);
		return ValidateUtil.sortbyValues(pts);
	}
	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 加密的渠道key
	 */
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	/**
	 * 加密的渠道key
	 */
	public String getChannelKey() {
		return channelKey;
	}
	
	
}
