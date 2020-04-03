/**
 * 
 * OpenCardEvent.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.event;

import java.util.Map;

import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.event.DataSyncEvent;
import com.hummingbird.commonbiz.face.DataSyncReceiveBody;
import com.hummingbird.maaccount.entity.ZJProduct;
import com.hummingbird.maaccount.vo.AntiPayoffline;
import com.hummingbird.maaccount.vo.OfflineCancelDataSync;
import com.hummingbird.maaccount.vo.OfflineUndoDataSync;
import com.hummingbird.maaccount.vo.TransOrderVO2;

/**
 * @author john huang
 * 2015年8月14日 上午9:14:44
 * 本类主要做为 线下支付事件
 */
public class OfflinePayCancelEvent implements DataSyncEvent{

	OfflineCancelDataSync dataSyncbody;
	


	/**
	 * 构造函数
	 * @param ext 
	 */
	public OfflinePayCancelEvent(TransOrderVO2<AntiPayoffline> transorder,
			ZJProduct zjProduct, ResultModel rm, String mobilenum, Map ext) {
		dataSyncbody = new OfflineCancelDataSync(transorder,rm,zjProduct,mobilenum,ext);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.event.DataSyncEvent#getDataSyncBody()
	 */
	@Override
	public DataSyncReceiveBody getDataSyncBody() {
		return dataSyncbody;
	}

}
