package com.hummingbird.maaccount.mapper;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.SMSMessage;
import com.hummingbird.maaccount.vo.QuerySMSMessageVO;

public interface SMSMessageMapper {
	
   public int validateSMS(QuerySMSMessageVO querySMSMessageVO);
   
   public int validateSMSInterval(QuerySMSMessageVO querySMSMessageVO);
   
   public int deleteByMobileAndAppId(@Param("mobileNum")String mobileNum,@Param("appId")String appId);
   
   public int insert(SMSMessage sMSMessage);
}
