package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.OilcardAccountProduct;

public interface OilcardAccountProductMapper {
	
    int deleteByPrimaryKey(String productid);

    int insert(OilcardAccountProduct record);

    int insertSelective(OilcardAccountProduct record);

    OilcardAccountProduct selectByPrimaryKey(String productid);

    int updateByPrimaryKeySelective(OilcardAccountProduct record);

    int updateByPrimaryKey(OilcardAccountProduct record);
}