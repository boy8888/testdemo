package com.hummingbird.maaccount.vo;

public class QueryUserCardListBodyVO {
	/*{
	    "app":
	        {"appId":"zjhtwallet","timeStamp":"TIMESTAMP", "nonce":"NONCE","signature":"SIGNATURE"},
	    "body":{
	        "pageSize":10,"pageIndex":1,
	        "mobileNum":"13912345678",
	        "startDate":"2015-07-01",
	        "endDate":"2015-07-10",
	        "types":["VCA","DCA","OCA"],
	        "status":["END","OK#","NOP","FRZ"],
	        "channelNo":"渠道",
	        "queryCardSource":true
	    }
	}*/
	/**
	 * 构造函数
	 */
	public QueryUserCardListBodyVO() {
		// TODO Auto-generated constructor stub
	}
	
	protected AppVO app;
	
	protected QueryUserCardListDetailVO body;

	public QueryUserCardListDetailVO getBody() {
		return body;
	}

	public void setBody(QueryUserCardListDetailVO body) {
		this.body = body;
	}

	/**
	 * @return the app
	 */
	public AppVO getApp() {
		return app;
	}

	/**
	 * @param app the app to set
	 */
	public void setApp(AppVO app) {
		this.app = app;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderQueryVO [app=" + app + ", body=" + body + "]";
	}
}
