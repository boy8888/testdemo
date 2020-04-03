/**
 * 
 * IOrderVO.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.vo;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Consumer;

/**
 * @author john huang
 * 2015年2月5日 下午8:44:45
 * 本类主要做为 业务处理接口调用传参中的order接口，它不是获取mobileNum，而是consumerId
 */
public interface IOrderConsumerVO {

	
	/**
	 * 获取明文
	 * @return
	 */
	public String getPaintText();

	/**
	 * 手机号
	 * @return
	 */
	public String getConsumerId();
	
	/**
	 * 获得消费号码对象
	 * @return
	 * @throws MaAccountException 
	 * @throws DataInvalidException 
	 */
	public Consumer getConsumer() throws DataInvalidException, MaAccountException;
	
	public void selfvalidate()  throws DataInvalidException;
	
}
