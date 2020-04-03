package com.hummingbird.maaccount.mapper;

import com.hummingbird.maaccount.entity.SmsCode;

public interface UserSmsCodeMapper {
    int deleteByPrimaryKey(Integer idtUserSmscode);

    int insert(SmsCode record);

    int insertSelective(SmsCode record);

    SmsCode selectByPrimaryKey(Integer idtUserSmscode);

    int updateByPrimaryKeySelective(SmsCode record);

    int updateByPrimaryKey(SmsCode record);
    
    /**
	 * 获取短信消息码
	 * @param appId
	 * @param mobileNum
	 * @return
	 */
	SmsCode getAuthCode(SmsCode record);
	
	/**
	 * 删除短消息验证码
	 * @param record
	 * @return
	 */
	int deleteAuthCode(SmsCode record);
}