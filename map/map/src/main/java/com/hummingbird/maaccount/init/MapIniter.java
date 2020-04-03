/**
 * 
 * MapIniter.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.init;

import java.util.Map;

import com.hummingbird.common.event.EventListenerContainer;
import com.hummingbird.common.ext.AppIniter;
import com.hummingbird.maaccount.listener.DataSyncEventListener;

/**
 * @author john huang
 * 2015年8月18日 下午2:07:35
 * 本类主要做为
 */
public class MapIniter implements AppIniter {

	org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(this.getClass());
	/* (non-Javadoc)
	 * @see com.hummingbird.common.ext.AppIniter#init(java.util.Map)
	 */
	@Override
	public void init(Map param) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("初始化数据同步监听"));
		}
		EventListenerContainer.getInstance().addMyListener(new DataSyncEventListener());
		
	}

}
