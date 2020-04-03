package com.hummingbird.maaccount.mapper;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.ProductOiltype;

public interface ProductOiltypeMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer customId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ProductOiltype record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ProductOiltype record);

    /**
     * 根据主键查询记录
     */
    ProductOiltype selectByPrimaryKey(Integer customId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ProductOiltype record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ProductOiltype record);

	/**
	 * 判断此产品能否对指定的中经产品进行支付
	 * @param productId
	 * @param productName
	 */
	int countProductOiltype(@Param("productId") String productId,@Param("zjProductId")  String zjProductId);
}