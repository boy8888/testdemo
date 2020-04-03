package com.hummingbird.maaccount.util.huiTongBusinessUtil.VO;

public class HuiTongBusinessBaseVO {

	private Long txn_id;// 交易类型

	private String channel;// 渠道号

	private String chan_user;// 用户名

	private String systrace;// 流水号

	private String rc;// 返回码

	private String rc_detail;//返回解释

	private String txn_time;// 交易时间

	private String txn_date;//交易日期

	private String sign;// 签名
	
	public HuiTongBusinessBaseVO(Long txn_id, String channel,String chan_user, String systrace) {
		this.txn_id = txn_id;
		this.channel = channel;
		this.chan_user = chan_user;
		this.systrace = systrace;
	}
	
	public HuiTongBusinessBaseVO() {
		
	}

	public Long getTxn_id() {
		return txn_id;
	}

	public void setTxn_id(Long txn_id) {
		this.txn_id = txn_id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChan_user() {
		return chan_user;
	}

	public void setChan_user(String chan_user) {
		this.chan_user = chan_user;
	}

	public String getSystrace() {
		return systrace;
	}

	public void setSystrace(String systrace) {
		this.systrace = systrace;
	}

	public String getRc() {
		return rc;
	}

	public void setRc(String rc) {
		this.rc = rc;
	}

	public String getRc_detail() {
		return rc_detail;
	}

	public void setRc_detail(String rc_detail) {
		this.rc_detail = rc_detail;
	}

	public String getTxn_time() {
		return txn_time;
	}

	public void setTxn_time(String txn_time) {
		this.txn_time = txn_time;
	}

	public String getTxn_date() {
		return txn_date;
	}

	public void setTxn_date(String txn_date) {
		this.txn_date = txn_date;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public boolean isSUCCESS(){
		return "00".equals(this.rc);
	}

}
