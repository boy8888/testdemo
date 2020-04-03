/**
 * 
 * ZjSmsSenderFactory.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service.impl;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.maaccount.entity.AppSmsActionSetting;
import com.hummingbird.maaccount.entity.AppSmsSetting;
import com.hummingbird.maaccount.service.ZjSmsSender;

/**
 * @author john huang
 * 2015年12月8日 下午7:38:16
 * 本类主要做为 中经短信发送器工厂类
 */
public class ZjSmsSenderFactory {

	/**
	 * 获取中经短信发送器
	 * @param appSmsSetting
	 * @param action
	 * @return
	 * @throws ValidateException 
	 */
	public static ZjSmsSender getZjSmsSender(AppSmsSetting appSmsSetting, AppSmsActionSetting action) throws ValidateException {
		String senderImpl = appSmsSetting.getSenderImpl();
		if(StringUtils.isBlank(senderImpl)){
			throw ValidateException.ERROR_PARAM_NULL.clone(null, "无法获取短信发送实现");
		}
		ZjSmsSender smssender;
		try {
			smssender = (ZjSmsSender) Class.forName(senderImpl).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw ValidateException.ERROR_SYSTEM_INTERNAL.clone(null, "无法获取短信发送实现");
		}
		smssender.init(appSmsSetting,action);
		
		return smssender;
	}

}
