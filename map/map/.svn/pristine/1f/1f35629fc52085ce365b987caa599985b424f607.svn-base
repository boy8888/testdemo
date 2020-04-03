/**
 * 
 * OfflinePayDataSync.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.hummingbird.common.constant.CommonStatusConst;
import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.commonbiz.face.DataSyncReceiveBody;
import com.hummingbird.maaccount.entity.TerminalList;
import com.hummingbird.maaccount.entity.ZJProduct;
import com.hummingbird.maaccount.mapper.TerminalListMapper;

/**
 * @author john huang
 * 2015年8月17日 下午8:44:16
 * 本类主要做为
 */
public class OfflineCancelDataSync  extends BaseDayaSync {

//	{
//    "orderId":"UD00201507062222000000123456", 
//    "sum":100000,
//    "status":"正常",
//    "productName":"92#汽油",
//    "productPrice":"466",
//    "productQuantity":20000,
//    "insertTime":"2015-06-30 12:45:23",
//    "storeName":"新祠上行油站",
//    "typeName":"消费",
//    "mobileNum":"13912345678",
//    "accountId":"9500010000091681"
//}
	/**
	 * 构造函数
	 * @param ext 
	 * @param encryptkey 
	 */
	public OfflineCancelDataSync(TransOrderVO2<AntiPayoffline> transorder,
			ResultModel rm, ZJProduct zjProduct, String mobilenum, Map ext) {
		data.put("mobileNum", mobilenum);
		
		Map order = (Map) rm.get("order");
		Map accountmap = (Map) rm.get("account");
		String accountId = ObjectUtils.toString(accountmap.get("accountId"));
		String terminalId = ObjectUtils.toString(order.get("terminalId"));
		data.put("accountId", accountId);
		data.put("orderId", ObjectUtils.toString(order.get("orderId")));
		String insertTime= ObjectUtils.toString(order.get("payTime"));
		data.put("insertTime",insertTime);
		data.put("sum", NumberUtils.toLong(ObjectUtils.toString(order.get("sum"),"0")));
		data.put("productQuantity", NumberUtils.toLong(ObjectUtils.toString(ext.get("productQuantity"),"0")));
		data.put("productName", zjProduct!=null?zjProduct.getZjproductname():"");
		data.put("productPrice", NumberUtils.toLong(ObjectUtils.toString(ext.get("productPrice"),"0")));
		TerminalListMapper terminalListMapper = SpringBeanUtil.getInstance().getBean(TerminalListMapper.class);
		TerminalList terminalList = terminalListMapper.selectByPrimaryKey(terminalId);
		data.put("storeName", terminalList!=null?terminalList.getStoreName():"未知油站");
		data.put("typeName", "撤销");
		data.put("status", "正常");
		
//		attrs.put("status",ObjectUtils.toString(accountmap.get("status")));
		attrs.put("insertTime", insertTime);
		attrs.put("productId", StringUtils.left(accountId, 4));
		
//		addSignature(encryptkey);
	}





	/* (non-Javadoc)
	 * @see com.hummingbird.commonbiz.face.DataSyncReceiveBody#getDataType()
	 */
	@Override
	public String getDataType() {
		return "MAP_CANCEL";
	}

	
	
	
	
	
}
