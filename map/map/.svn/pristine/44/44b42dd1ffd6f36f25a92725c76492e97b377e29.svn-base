package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.JifenProduct;

public interface JifenProductMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String productId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(JifenProduct record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(JifenProduct record);

    /**
     * 根据主键查询记录
     */
    JifenProduct selectByPrimaryKey(String productId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(JifenProduct record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(JifenProduct record);
}