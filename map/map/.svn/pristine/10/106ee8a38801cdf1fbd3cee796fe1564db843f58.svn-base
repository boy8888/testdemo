package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.WxRechargeLimit;

public interface WxRechargeLimitMapper {
    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(WxRechargeLimit record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(WxRechargeLimit record);
    WxRechargeLimit selectByType(String type);
}