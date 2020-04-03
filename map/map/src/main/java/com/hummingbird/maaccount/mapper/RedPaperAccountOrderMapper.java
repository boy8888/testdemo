package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.RedPaperAccountOrder;

public interface RedPaperAccountOrderMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String orderId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(RedPaperAccountOrder record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(RedPaperAccountOrder record);

    /**
     * 根据主键查询记录
     */
    RedPaperAccountOrder selectByPrimaryKey(String orderId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(RedPaperAccountOrder record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(RedPaperAccountOrder record);
    
    /**
	 * 根据第三方的应用查询分期卡帐户情况
	 * @param appId
	 * @param appOrderId
	 * @return
	 */
    RedPaperAccountOrder selectByChannelOrderId(@Param("appId") String appId,@Param("channelOrderId")  String channelOrderId);
    
    List<String> selectByAccountIdAndAppId(@Param("accountIds")  List<String> accountIds,@Param("appId") String appId);

}