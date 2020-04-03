package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;


public class BankCardVO extends AppBaseVO implements AppMobileDecidable{
	
	private BindVO bind;
	
	public BindVO getBind() {
		return bind;
	}

	public void setBind(BindVO bind) {
		this.bind = bind;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BankCardVO [bind=" + bind + ", app=" + app + "]";
	}

	@Override
	public String getAppId() {
		// TODO Auto-generated method stub
		return app.getAppId();
	}

	@Override
	public String getMobileNum() {
		// TODO Auto-generated method stub
		return bind.getMobileNum();
	}
	

}
