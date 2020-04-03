package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.VolumecardProduct;

public interface VolumecardProductMapper {
    int deleteByPrimaryKey(String productId);

    int insert(VolumecardProduct record);

    int insertSelective(VolumecardProduct record);

    VolumecardProduct selectByPrimaryKey(String productId);

    int updateByPrimaryKeySelective(VolumecardProduct record);

    int updateByPrimaryKey(VolumecardProduct record);
}