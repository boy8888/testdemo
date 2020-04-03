/**
 * 
 * ConsumerFactory.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.LuhnUtils;
import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.common.util.ValidateUtil;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.exception.MaAccountException;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.face.Consumer;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.vo.BaseConsumer;

/**
 * @author john huang
 * 2015年2月13日 下午5:14:36
 * 本类主要做为
 */
public abstract class ConsumerFactory {

	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(ConsumerFactory.class);
	
	/**
	 * 获得消费者
	 * @param consumerId
	 * @return
	 * @throws DataInvalidException
	 * @throws MaAccountException 
	 */
	public static Consumer getConsumer(String consumerId) throws DataInvalidException, MaAccountException{
		ValidateUtil.assertNull(consumerId, "consumerId");
		//统一采用16位数字编码，出了”1X“开头的账户编码例外，"1X"开头账户编码12位，为手机号码
//		编码规则说明：
//		AABB CCCD DDDD DDDD
//		AA：代表账户类型。
//
//		AA	账户类型
//		1X	手机号码，总长度11位数字
//		95	现金账户，总长度16位数字
//		97	投资账户，总长度16位数字
//		22	分期卡账户，总长度16位数字
//		23	折扣卡账户，总长度16位数字
//		BB：代表某账户下的产品形态，比如01表示1000元套餐，02标识2000元套餐呢。
//
//		AABB：组合形成了一个具体的产品编码，比如2201就表示分期电子卡1000元套餐。
//
//		CCC：表示发行地点，一般采用区域的电话号码前缀，比如深圳发行就是755，888或者020代表总部发行。
//
//		D DDDD DDDD：表示自动生成的账户数字，某个产品编号下的某个地方发行的可以容纳近亿个账号。
		BaseConsumer bc;
		ProductMapper prodao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
		if(consumerId.length()==11&&consumerId.startsWith("1")){
			//手机号码
			bc= new BaseConsumer(consumerId,Consumer.CONSUMER_TYPE_MOBILE);
		}
		else if(consumerId.length()==16){
			//判断卡片的正确性
			boolean luhnTest = LuhnUtils.luhnTest(consumerId);
			if(!luhnTest){
				if (log.isDebugEnabled()) {
					log.debug(String.format("消费号码[%s]卡号校验不通过.",consumerId));
				}
				//throw new MaAccountException(MaAccountException. ERR_CONSUMER_EXCEPTION,"消费号码["+consumerId+"]不能识别");
			}
			String accountprefix = consumerId.substring(0,4);
			Product product = prodao.selectByPrimaryKey(accountprefix);
			if(product==null){
				if (log.isDebugEnabled()) {
					log.debug(String.format("消费号码[%s]的前4位并不能找到相应的产品",consumerId));
				}
				throw new MaAccountException(MaAccountException. ERR_CONSUMER_EXCEPTION,"消费号码["+consumerId+"]不能识别");
			}
			String accountType = product.getAccountType();
			switch (accountType) {
			case Consumer.CONSUMER_TYPE_CASHACCOUNT:
				bc=new BaseConsumer(consumerId,Consumer.CONSUMER_TYPE_CASHACCOUNT);
				break;
			case Consumer.CONSUMER_TYPE_INVESTMENTACCOUNT:
				bc=new BaseConsumer(consumerId,Consumer.CONSUMER_TYPE_INVESTMENTACCOUNT);
				break;
			case Consumer.CONSUMER_TYPE_OILCARD:
				bc=new BaseConsumer(consumerId,Consumer.CONSUMER_TYPE_OILCARD);
				break;
			case Consumer.CONSUMER_TYPE_DISCOUNTCARD:
				bc=new BaseConsumer(consumerId,Consumer.CONSUMER_TYPE_DISCOUNTCARD);
				break;
			case Consumer.CONSUMER_TYPE_VOLUMECARD:
				bc=new BaseConsumer(consumerId,Consumer.CONSUMER_TYPE_VOLUMECARD);
				break;
			default:
				if (log.isDebugEnabled()) {
					log.debug(String.format("消费号码[%s]的对应的帐户类型[%s]不能识别",consumerId,accountType));
				}
				throw new MaAccountException(MaAccountException. ERR_CONSUMER_EXCEPTION,"消费号码["+consumerId+"]不能识别");
			}
		}
		else{
			if (log.isDebugEnabled()) {
				log.debug(String.format("消费号码[%s]长度不符合要求",consumerId));
			}
			throw new MaAccountException(MaAccountException. ERR_CONSUMER_EXCEPTION,"消费号码["+consumerId+"]不能识别");
			
		}
		
		
		return bc;
	}
	
	
}
