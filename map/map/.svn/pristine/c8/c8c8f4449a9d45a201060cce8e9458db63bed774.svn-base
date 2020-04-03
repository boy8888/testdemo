/**
 * 
 * DataSyncEventListener.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.listener;

import com.hummingbird.common.event.AbstractBusinessEventListener;
import com.hummingbird.common.event.BusinessEvent;
import com.hummingbird.commonbiz.util.DataSyncNotifyUtil;
import com.hummingbird.maaccount.event.OfflinePayCancelEvent;
import com.hummingbird.maaccount.event.OfflinePayEvent;
import com.hummingbird.maaccount.event.OfflinePayUndoEvent;
import com.hummingbird.maaccount.event.OpenCardEvent;

/**
 * @author john huang
 * 2015年8月18日 上午9:33:07
 * 本类主要做为
 */
public class DataSyncEventListener extends
AbstractBusinessEventListener  {

	/* (non-Javadoc)
	 * @see com.hummingbird.common.event.BusinessEventListener#handleEvent(com.hummingbird.common.event.BusinessEvent)
	 */
	@Override
	public void handleEvent(BusinessEvent event) {
		if (event instanceof OfflinePayUndoEvent) {
			OfflinePayUndoEvent undoe = (OfflinePayUndoEvent) event;
			DataSyncNotifyUtil.notify(undoe);
		}
		else if (event instanceof OfflinePayCancelEvent) {
			OfflinePayCancelEvent cancele = (OfflinePayCancelEvent) event;
			DataSyncNotifyUtil.notify(cancele);
		}
		else if (event instanceof OfflinePayEvent) {
			OfflinePayEvent paye = (OfflinePayEvent) event;
			DataSyncNotifyUtil.notify(paye);
		}
		else if (event instanceof OpenCardEvent) {
			OpenCardEvent oce = (OpenCardEvent) event;
			DataSyncNotifyUtil.notify(oce);
		}
		
	}

}
