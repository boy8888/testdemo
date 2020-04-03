package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.RealNameAuth;

public interface RealNameAuthMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer userid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(RealNameAuth record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(RealNameAuth record);

    /**
     * 根据主键查询记录
     */
    RealNameAuth selectByPrimaryKey(Integer userid);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(RealNameAuth record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(RealNameAuth record);
}