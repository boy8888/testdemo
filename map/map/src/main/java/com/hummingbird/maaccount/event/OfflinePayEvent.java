/**
 * 
 * OpenCardEvent.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.event;

import java.util.Date;

import com.hummingbird.common.constant.CommonStatusConst;
import com.hummingbird.common.util.DESUtil;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.event.DataSyncEvent;
import com.hummingbird.commonbiz.face.DataSyncReceiveBody;
import com.hummingbird.maaccount.entity.DiscountCardProduct;
import com.hummingbird.maaccount.entity.OilcardAccountProduct;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.entity.VolumecardAccountProduct;
import com.hummingbird.maaccount.entity.ZJProduct;
import com.hummingbird.maaccount.vo.BatchOpenOnlineResultDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineResultVO;
import com.hummingbird.maaccount.vo.OfflineOpencardOrderVO;
import com.hummingbird.maaccount.vo.OfflinePayDataSync;
import com.hummingbird.maaccount.vo.OfflinePayOrderConsumerVO;
import com.hummingbird.maaccount.vo.OilcardOrderVO;
import com.hummingbird.maaccount.vo.OilcardResultVO;
import com.hummingbird.maaccount.vo.OpenCardDataSync;
import com.hummingbird.maaccount.vo.TransOrderVO2;
import com.hummingbird.maaccount.vo.TransOrderWithConsumerVO;

/**
 * @author john huang
 * 2015年8月14日 上午9:14:44
 * 本类主要做为 线下支付事件
 */
public class OfflinePayEvent implements DataSyncEvent{

	OfflinePayDataSync dataSyncbody;
	

	/**
	 * 构造函数
	 */
	public OfflinePayEvent(
			TransOrderWithConsumerVO<OfflinePayOrderConsumerVO> transorder,
			ZJProduct zjProduct, ResultModel rm, String mobile) {
		dataSyncbody = new OfflinePayDataSync(transorder,rm,zjProduct,mobile);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.event.DataSyncEvent#getDataSyncBody()
	 */
	@Override
	public DataSyncReceiveBody getDataSyncBody() {
		return dataSyncbody;
	}

}
