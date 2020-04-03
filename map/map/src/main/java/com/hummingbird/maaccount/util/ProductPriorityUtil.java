/**
 * 
 * ProductPriorityUtil.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.maaccount.entity.Product;
import com.hummingbird.maaccount.face.Consumer;
import com.hummingbird.maaccount.mapper.ProductMapper;
import com.hummingbird.maaccount.vo.ProductPriority;

/**
 * @author john huang
 * 2015年8月18日 下午11:35:30
 * 本类主要做为 产品优先级处理工具类
 */
public class ProductPriorityUtil {

	
	
	/**
	 * 分解优先级内容
	 * @param priorityStr
	 */
	public static  List<ProductPriority> getProductPriorities(String priorityStr){
		List<ProductPriority> pps = new ArrayList<ProductPriority>();
		String[] prioritiesArr = StringUtils.split(priorityStr,",");
		for (int i = 0; i < prioritiesArr.length; i++) {
			String pristr = prioritiesArr[i];
			if(Consumer.CONSUMER_TYPE_CASHACCOUNT.equals(pristr)){
				ProductPriority pp = new ProductPriority(Consumer.CONSUMER_TYPE_CASHACCOUNT);
				//原来的所有产品都过滤一次,去掉这个产品
				for (Iterator iterator = pps.iterator(); iterator.hasNext();) {
					ProductPriority productPriority = (ProductPriority) iterator
							.next();
					productPriority.removeProductId(pp);
				}
				pps.add(pp);
			}
			else if(Consumer.CONSUMER_TYPE_OILCARD.equals(pristr)){
				ProductPriority pp = new ProductPriority(Consumer.CONSUMER_TYPE_OILCARD);
				//原来的所有产品都过滤一次,去掉这个产品
				for (Iterator iterator = pps.iterator(); iterator.hasNext();) {
					ProductPriority productPriority = (ProductPriority) iterator
							.next();
					productPriority.removeProductId(pp);
				}
				pps.add(pp);
			}
			else if(Consumer.CONSUMER_TYPE_DISCOUNTCARD.equals(pristr)){
				ProductPriority pp = new ProductPriority(Consumer.CONSUMER_TYPE_DISCOUNTCARD);
				//原来的所有产品都过滤一次,去掉这个产品
				for (Iterator iterator = pps.iterator(); iterator.hasNext();) {
					ProductPriority productPriority = (ProductPriority) iterator
							.next();
					productPriority.removeProductId(pp);
				}
				pps.add(pp);
			}
			else if(Consumer.CONSUMER_TYPE_VOLUMECARD.equals(pristr)){
				ProductPriority pp = new ProductPriority(Consumer.CONSUMER_TYPE_VOLUMECARD);
				//原来的所有产品都过滤一次,去掉这个产品
				for (Iterator iterator = pps.iterator(); iterator.hasNext();) {
					ProductPriority productPriority = (ProductPriority) iterator
							.next();
					productPriority.removeProductId(pp);
				}
				pps.add(pp);
			}
			else{
				//可能是产品id,到数据库中进行查询
				ProductMapper prodao = SpringBeanUtil.getInstance().getBean(ProductMapper.class);
				Product product = prodao.selectByPrimaryKey(pristr);
				if(product==null){
					continue;
				}
				else{
					ProductPriority pp = new ProductPriority(product.getAccountType(),pristr);
					//原来的所有产品都过滤一次,去掉这个产品
					for (Iterator iterator = pps.iterator(); iterator.hasNext();) {
						ProductPriority productPriority = (ProductPriority) iterator
								.next();
						productPriority.removeProductId(pp);
					}
					pps.add(pp);
					
				}
			}
			
			
		}
		return pps;
	}
	
	
}
