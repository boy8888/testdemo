/**
 * 
 * IOrderVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.common.exception.DataInvalidException;

/**
 * @author john huang
 * 2015年2月5日 下午8:44:45
 * 本类主要做为 资金转移对象的接口
 */
public interface IOrderVO {

	
	/**
	 * 获取明文
	 * @return
	 */
	public String getPaintText();

	/**
	 * 手机号
	 * @return
	 */
	public String getMobileNum();
	
	public void selfvalidate()  throws DataInvalidException;
	
}
