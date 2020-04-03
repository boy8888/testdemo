package com.hummingbird.maaccount.mapper;

import java.util.List;

import com.hummingbird.maaccount.entity.Product;

public interface ProductMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String productId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Product record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Product record);

    /**
     * 根据主键查询记录
     */
    Product selectByPrimaryKey(String productId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Product record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Product record);
    
    /**
     * 查询可用的产品
     * @return
     */
    List<Product> selectUseableProducts();
}