package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.OpenCardDeliveryFailEntity;
import com.hummingbird.maaccount.vo.OpenCardFailDeliveryDetailVO;

public interface OpenCardDeliveryFailEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OpenCardDeliveryFailEntity record);

    int insertSelective(OpenCardDeliveryFailEntity record);

    OpenCardDeliveryFailEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpenCardDeliveryFailEntity record);

    int updateByPrimaryKey(OpenCardDeliveryFailEntity record);
    
    /**
     * 记录失败的交割数据
     * @param insertlist
     * @return
     */
    void recordOpenCardFail(@Param("list") List<OpenCardFailDeliveryDetailVO> list);
}