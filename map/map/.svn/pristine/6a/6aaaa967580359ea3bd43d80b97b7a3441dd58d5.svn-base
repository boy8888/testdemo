/**
 * 
 * YYDSmsSender.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.entity.AppSmsActionSetting;
import com.hummingbird.maaccount.entity.AppSmsSetting;
import com.hummingbird.maaccount.service.ZjSmsSender;

/**
 * @author john huang
 * 2015年12月8日 下午7:51:30
 * 本类主要做为 抽像有油贷的短信发送
 */
public abstract class AbstractSmsSender implements ZjSmsSender {

	protected AppSmsSetting appSmsSetting;
	protected AppSmsActionSetting action;
	protected String url;
	protected org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	/* (non-Javadoc)
	 * @see com.hummingbird.maaccount.service.ZjSmsSender#init(com.hummingbird.maaccount.entity.AppSmsSetting, com.hummingbird.maaccount.entity.AppSmsActionSetting)
	 */
	@Override
	public void init(AppSmsSetting appSmsSetting, AppSmsActionSetting action) {
		this.appSmsSetting = appSmsSetting;
		this.action = action;
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		url=propertiesUtil.getProperty("zj.newsms.url");
	}

	


}
