package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.ZJProduct;

public interface ZJProductMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String zjid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(ZJProduct record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(ZJProduct record);

    /**
     * 根据主键查询记录
     */
    ZJProduct selectByPrimaryKey(String zjid);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ZJProduct record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ZJProduct record);
}