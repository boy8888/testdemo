package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;
//获取查询交易订单接口参数
public class GetTradeOrderVO extends AppBaseVO implements AppMobileDecidable {
	private TradeOrderVO order;
	

	public TradeOrderVO getOrder() {
		return order;
	}
	public void setOrder(TradeOrderVO order) {
		this.order = order;
	}
	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		return "OpenCashCardVO [order=" + order + ", app=" + app + "]";
	}


	@Override
	public String getAppId() {
		// TODO Auto-generated method stub
		return app.getAppId();
	}
}
