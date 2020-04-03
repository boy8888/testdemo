package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.JifenAccountOrder;

public interface JifenAccountOrderMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String orderId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(JifenAccountOrder record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(JifenAccountOrder record);

    /**
     * 根据主键查询记录
     */
    JifenAccountOrder selectByPrimaryKey(String orderId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(JifenAccountOrder record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(JifenAccountOrder record);
}