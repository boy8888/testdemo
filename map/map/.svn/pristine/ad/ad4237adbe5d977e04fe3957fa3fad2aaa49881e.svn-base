package com.hummingbird.maaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.entity.WxRechargeLimit;
import com.hummingbird.maaccount.mapper.WxRechargeLimitMapper;
import com.hummingbird.maaccount.service.WxRechargeLimitService;
@Service
public class WxRechargeLimitServiceImpl implements WxRechargeLimitService{
@Autowired
WxRechargeLimitMapper WxDao;
	@Override
	public Integer selectByType(String type) {
		WxRechargeLimit rechargeLimit= WxDao.selectByType(type);
		return rechargeLimit.getMoneyLimit();
	}

}
