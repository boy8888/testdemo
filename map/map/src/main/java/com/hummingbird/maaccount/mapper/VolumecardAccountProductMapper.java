package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.VolumecardAccountProduct;

public interface VolumecardAccountProductMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String productId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(VolumecardAccountProduct record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(VolumecardAccountProduct record);

    /**
     * 根据主键查询记录
     */
    VolumecardAccountProduct selectByPrimaryKey(String productId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(VolumecardAccountProduct record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(VolumecardAccountProduct record);
}