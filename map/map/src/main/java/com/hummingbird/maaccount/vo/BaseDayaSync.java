/**
 * 
 * BaseDayaSync.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.hummingbird.common.util.Md5Util;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.commonbiz.face.DataSyncReceiveBody;

/**
 * @author john huang
 * 2015年8月20日 下午5:56:17
 * 本类主要做为
 */
public abstract class BaseDayaSync  implements DataSyncReceiveBody{

	protected Map data = new HashMap();
	protected Map attrs = new HashMap();
	/**
	 * @return the data
	 */
	public Map getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Map data) {
		this.data = data;
	}
	/**
	 * @return the attrs
	 */
	public Map getAttrs() {
		return attrs;
	}
	/**
	 * @param attrs the attrs to set
	 */
	public void setAttrs(Map attrs) {
		this.attrs = attrs;
	}
	
	/**
	 * 添加签名
	 * @param encryptkey 
	 */
	protected void addSignature(String encryptkey) {
		Collection values = data.values();
		
		List<String> list = new ArrayList<String>();
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			String str = (String) ObjectUtils.toString(iterator.next());
			list.add(str);
		}
		list.add(getDataType());
		list.add(encryptkey);
		String sortbyValues = ValidateUtil.sortbyValues(list);
		data.put("signature",Md5Util.Encrypt(sortbyValues));
	}
	
}
