package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.PrioritySetting;

public interface PrioritySettingMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(PrioritySetting record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(PrioritySetting record);

    /**
     * 根据主键查询记录
     */
    PrioritySetting selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(PrioritySetting record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(PrioritySetting record);
    String selectByConsumerType(String key);
}