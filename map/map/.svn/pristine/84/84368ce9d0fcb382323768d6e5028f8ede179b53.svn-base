/**
 * 
 * ZjSmsSender.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.JsonUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.entity.AppSmsActionSetting;
import com.hummingbird.maaccount.entity.AppSmsSetting;
import com.zjhtc.htm.message.serializable.MessageRequest;
import com.zjhtc.htm.message.serializable.factory.MessageFactory;
import com.zjhtc.htm.message.serializable.factory.SimpleSmsMessageFactory;

/**
 * @author john huang
 * 2015年12月8日 下午7:36:59
 * 本类主要做为
 */
public interface ZjSmsSender {

	/**
	 * 初始化发送器
	 * @param appSmsSetting
	 * @param action
	 */
	void init(AppSmsSetting appSmsSetting, AppSmsActionSetting action);

	ResultModel send(String mobileNum, String content) throws DataInvalidException;

}
