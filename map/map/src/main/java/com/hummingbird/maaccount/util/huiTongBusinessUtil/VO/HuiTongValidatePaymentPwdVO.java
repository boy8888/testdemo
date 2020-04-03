package com.hummingbird.maaccount.util.huiTongBusinessUtil.VO;

import com.hummingbird.maaccount.util.huiTongBusinessUtil.util.HuiTongBusinessConfig;


/**
 * 此类作为汇通接口验证密码的VO对象
 * @author 
 *
 */
public class HuiTongValidatePaymentPwdVO extends HuiTongBusinessBaseVO{
	
	private String pin_data;// 密码密文

	private String memo;// 备注	
	
	public HuiTongValidatePaymentPwdVO() {
		
	}

	public HuiTongValidatePaymentPwdVO(Long txn_id, String channel,
			String chan_user, String systrace, String pin_data) {
		super(txn_id, channel, chan_user, systrace);
		this.pin_data = pin_data;
	}

	public String getPin_data() {
		return pin_data;
	}

	public void setPin_data(String pin_data) {
		this.pin_data = pin_data;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public boolean isSUCCESS() {
		return super.isSUCCESS()&&this.getTxn_id().longValue()==HuiTongBusinessConfig.HUI_TONG_VALIDATE_PWD_TXN_ID;
	}
    
}
