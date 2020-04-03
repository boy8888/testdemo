package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.AttrInfo;

public interface AttrInfoMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String attrId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(AttrInfo record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(AttrInfo record);

    /**
     * 根据主键查询记录
     */
    AttrInfo selectByPrimaryKey(String attrId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(AttrInfo record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AttrInfo record);
}