/**
 * 
 * UpdateUserInfoVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.commonbiz.vo.AppMobileDecidable;

/**
 * @author john huang
 * 2015年4月8日 下午11:09:11
 * 本类主要做为更新用户信息的参数对象
 */
public class UpdateUserInfoVO extends AppBaseVO implements AppMobileDecidable{

	public String getMobileNum(){
		if(update!=null){
			return update.getMobileNum();
		}
		return null;
	}
	
	protected UpdateUserInfoDetailVO update;

	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.vo.AppMobileDecidable#getAppId()
	 */
	@Override
	public String getAppId() {
		return app.getAppId();
	}

	/**
	 * @return the update
	 */
	public UpdateUserInfoDetailVO getUpdate() {
		return update;
	}

	/**
	 * @param update the update to set
	 */
	public void setUpdate(UpdateUserInfoDetailVO update) {
		this.update = update;
	}
	
	
}
