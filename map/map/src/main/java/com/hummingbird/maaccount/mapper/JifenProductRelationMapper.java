package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.JifenProductRelation;

public interface JifenProductRelationMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(JifenProductRelation record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(JifenProductRelation record);

    /**
     * 根据主键查询记录
     */
    JifenProductRelation selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(JifenProductRelation record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(JifenProductRelation record);
}