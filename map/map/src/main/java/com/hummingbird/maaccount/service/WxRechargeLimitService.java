package com.hummingbird.maaccount.service;

import com.hummingbird.maaccount.entity.WxRechargeLimit;

public interface WxRechargeLimitService {
	public Integer selectByType(String type);
}
