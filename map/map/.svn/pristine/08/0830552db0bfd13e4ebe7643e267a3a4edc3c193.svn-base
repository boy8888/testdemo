/**
 * 
 */
package com.hummingbird.maaccount.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.maaccount.vo.IOrderVO;
import com.hummingbird.maaccount.vo.OfflineOpencardOrderVO;

/**
 * @author huangjiej_2
 * 开卡交割失败数据
 */
public class OpenCardFailDeliveryVO extends BaseTransOrderVO {

	//交易id|卡号|金额|流水号|参考号|商户号|终端号|批次号|交易日期|交易时间|身份证号|手机号|推荐人id|产品编号
	
	public List<OpenCardFailDeliveryDetailVO> getRecoderList() {
		return recoderList;
	}


	public void setRecoderList(List<OpenCardFailDeliveryDetailVO> recoderList) {
		this.recoderList = recoderList;
	}


	protected List<OpenCardFailDeliveryDetailVO> recoderList=new ArrayList<OpenCardFailDeliveryDetailVO>();

	@JsonIgnore
	public String getPaintText() {
		List<String> list = new ArrayList<String>();
		for (Iterator iterator = recoderList.iterator(); iterator.hasNext();) {
			OfflineOpencardOrderVO offlineOpencardOrderVO = (OfflineOpencardOrderVO) iterator
					.next();
			list.add(offlineOpencardOrderVO.getPaintText());
		}
		return ValidateUtil.sortbyValues(list);
	}


	public void selfvalidate() throws DataInvalidException {
		
	}
	
	
	
}
