/**
 * 
 * OpenCardDataSync.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.HashMap;
import java.util.Map;

import com.hummingbird.common.constant.CommonStatusConst;
import com.hummingbird.commonbiz.face.DataSyncReceiveBody;

/**
 * @author john huang
 * 2015年8月14日 上午9:16:31
 * 本类主要做为 开卡结果处理
 */
public class OpenCardDataSync extends BaseDayaSync {

//	{
//        "mobileNum":"13912345678",
//       "orderId":"UD00201507062222000000123456", 
//       "accountId":"4001000000000001", 
//       "status":"正常",
//       "insertTime":"2015-07-06 22:22:22",
//       "startTime":"2015-07-06 22:22:22",
//       "endTime":"2016-07-06 22:22:22",
//       "productAmount":50000,
//       "productName":"300元加油卡",
//       "channelNo":"123456"
//
//}  
	
	
	/**
	 * 构造函数
	 * @param channelNo 渠道编号
	 * @param channelOrderId 渠道订单号
	 * @param encryptkey 
	 */
	public OpenCardDataSync(String mobile, String accountId,
			String insertTime, String startTime, String endTime,
			String productName, Long productAmount,String status, String orderId,
			String channelOrderId,String productId,String channelNo) {
		data.put("mobileNum", mobile);
		data.put("accountId", accountId);
		data.put("insertTime", insertTime);
		data.put("startTime", startTime);
		data.put("endTime", endTime);
		data.put("productName", productName);
		data.put("productAmount", productAmount);
		data.put("orderId", orderId);
		data.put("channelNo", channelOrderId);
		switch (status) {
		case CommonStatusConst.STATUS_OK:
			data.put("status", "正常");
			break;
		case CommonStatusConst.STATUS_FAIL:
			data.put("status", "失败");
			break;
		case CommonStatusConst.STATUS_FROZEN:
			data.put("status", "冻结");
			break;
		case "NOP":
			data.put("status", "未开通");
			break;
		default:
			data.put("status", status);
			break;
		}
		
		attrs.put("insertTime", insertTime);
		attrs.put("productId", productId);
		attrs.put("status", status);
		attrs.put("channelNo", channelNo);//渠道编号
//		addSignature(encryptkey);
	}

	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.face.DataSyncReceiveBody#getDataType()
	 */
	@Override
	public String getDataType() {
		return "MAP_OPENCARD";
	}


}
