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
import com.hummingbird.maaccount.vo.BatchOpenOnlineDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineListVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineResultDetailVO;
import com.hummingbird.maaccount.vo.BatchOpenOnlineResultVO;
import com.hummingbird.maaccount.vo.OfflineOpencardOrderVO;
import com.hummingbird.maaccount.vo.OilcardOrderVO;
import com.hummingbird.maaccount.vo.OilcardResultVO;
import com.hummingbird.maaccount.vo.OpenCardDataSync;
import com.hummingbird.maaccount.vo.TransOrderVO2;

/**
 * @author john huang
 * 2015年8月14日 上午9:14:44
 * 本类主要做为
 */
public class OpenCardEvent implements DataSyncEvent{

	OpenCardDataSync dataSyncbody;
	

	/**
	 * 构造函数
	 * @param commproduct 
	 * @param appkey 
	 * @param channelNo 
	 */
	public OpenCardEvent(BatchOpenOnlineResultVO result,
			VolumecardAccountProduct product, Product commproduct, String appkey, String channelNo) {
		String mobile = DESUtil.encryptDESwithCBC(result.getMobile(), appkey);
		BatchOpenOnlineResultDetailVO card = result.getCard();
		String accountId = DESUtil.encryptDESwithCBC(card.getAccountId(), appkey);
		
		dataSyncbody = new OpenCardDataSync(mobile,accountId,DateUtil.formatCommonDate(new Date()),card.getStartTime(),card.getEndTime(),commproduct.getProductName(),commproduct.getProductAmount(),card.getStatus(),result.getOrderId(),result.getChannelOrderId(),commproduct.getProductId(),channelNo);
	}

	/**
	 * 构造函数
	 * @param string 
	 */
	public OpenCardEvent(String mobileNum, String channelOrderId, String channelNo,
			String message, String appKey) {
		String mobile = DESUtil.encryptDESwithCBC(mobileNum, appKey);
		dataSyncbody = new OpenCardDataSync(mobile,null,DateUtil.formatCommonDate(new Date()),null,null,null,null,CommonStatusConst.STATUS_FAIL,null,channelOrderId,null,channelNo);
	}

	/**
	 * 构造函数
	 */
	public OpenCardEvent(BatchOpenOnlineResultVO result,
			DiscountCardProduct product, Product commproduct, String appkey, String channelNo) {
		String mobile = DESUtil.encryptDESwithCBC(result.getMobile(), appkey);
		BatchOpenOnlineResultDetailVO card = result.getCard();
		String accountId = DESUtil.encryptDESwithCBC(card.getAccountId(), appkey);
		
		dataSyncbody = new OpenCardDataSync(mobile,accountId,DateUtil.formatCommonDate(new Date()),card.getStartTime(),card.getEndTime(),commproduct.getProductName(),commproduct.getProductAmount(),card.getStatus(),result.getOrderId(),result.getChannelOrderId(),commproduct.getProductId(),channelNo);
	}

	/**
	 * 构造函数
	 */
	public OpenCardEvent(BatchOpenOnlineResultVO result,
			OilcardAccountProduct product, Product commproduct, String appkey, String channelNo) {
		String mobile = DESUtil.encryptDESwithCBC(result.getMobile(), appkey);
		BatchOpenOnlineResultDetailVO card = result.getCard();
		String accountId = DESUtil.encryptDESwithCBC(card.getAccountId(), appkey);
		
		dataSyncbody = new OpenCardDataSync(mobile,accountId,DateUtil.formatCommonDate(new Date()),card.getStartTime(),card.getEndTime(),commproduct.getProductName(),commproduct.getProductAmount(),card.getStatus(),result.getOrderId(),result.getChannelOrderId(),commproduct.getProductId(),channelNo);
	}

	/**
	 * 构造函数
	 * @param transorder 
	 */
	public OpenCardEvent(TransOrderVO2<OilcardOrderVO> transorder, ResultModel rm, OilcardAccountProduct product,
			Product commproduct, String appKey, String channelNo) {
		String mobile = DESUtil.encryptDESwithCBC(transorder.getOrder().getMobileNum(), appKey);
		OilcardResultVO card = (OilcardResultVO) rm.get("card" );
		
		String accountId = DESUtil.encryptDESwithCBC(card.getAccountId(), appKey);
		dataSyncbody = new OpenCardDataSync(mobile,accountId,DateUtil.formatCommonDate(new Date()),card.getStartTime(),card.getEndTime(),commproduct.getProductName(),commproduct.getProductAmount(),card.getStatus(),(String) rm.get("orderId"),transorder.getOrder().getChannelOrderId(),commproduct.getProductId(),channelNo);
	}

	/**
	 * 构造函数
	 */
	public OpenCardEvent(TransOrderVO2<OfflineOpencardOrderVO> transorder,
			ResultModel rm, OilcardAccountProduct product,
			Product commproduct, String appKey) {
		String mobile = DESUtil.encryptDESwithCBC(transorder.getOrder().getMobileNum(), appKey);
		OilcardResultVO card = (OilcardResultVO) rm.get("card" );
		
		String accountId = DESUtil.encryptDESwithCBC(card.getAccountId(), appKey);
		dataSyncbody = new OpenCardDataSync(mobile,accountId,DateUtil.formatCommonDate(new Date()),card.getStartTime(),card.getEndTime(),commproduct.getProductName(),commproduct.getProductAmount(),card.getStatus(),(String) rm.get("orderId"),null,commproduct.getProductId(),null);
	}

	/**
	 * 构造函数
	 */
	public OpenCardEvent(BatchOpenOnlineListVO order,
			BatchOpenOnlineDetailVO ordervo, String message, String channelkey) {
		dataSyncbody = new OpenCardDataSync(order.getMobileNum(),null,DateUtil.formatCommonDate(new Date()),null,null,null,null,CommonStatusConst.STATUS_FAIL,null,order.getChannelOrderId(),order.getProductId(),ordervo.getChannelNo());
	}


	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.event.DataSyncEvent#getDataSyncBody()
	 */
	@Override
	public DataSyncReceiveBody getDataSyncBody() {
		return dataSyncbody;
	}

}
