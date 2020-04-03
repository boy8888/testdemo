package com.hummingbird.maaccount.mapper;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.DiscountCardAccount;
import com.hummingbird.maaccount.entity.VolumecardAccount;
import com.hummingbird.maaccount.face.Account;
import com.hummingbird.maaccount.vo.CardSelecter;
import com.hummingbird.maaccount.vo.QueryUserCardListResultVO;

public interface VolumecardAccountMapper extends DefaultAccountDao{
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String accountId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(VolumecardAccount record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(VolumecardAccount record);

    /**
     * 根据主键查询记录
     */
    VolumecardAccount selectByPrimaryKey(String accountId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(VolumecardAccount record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(VolumecardAccount record);
    /**
	 * 获取帐户信息
	 * @param mobile
	 * @return
	 */
	Account getAccountByAccountId(String accountId );
	/**
	 * 创建帐户
	 * @param ca
	 */
	int createAccount(VolumecardAccount ca);
	List<VolumecardAccount> getAccountByUserId4list(@Param("userId")Integer userId,@Param("terminalId")String terminalId,@Param("productQuantity")Long productQuantity);
	List<VolumecardAccount> getAllAccount();
	
	/**
	 * 获取帐户信息
	 * @param mobile
	 * @return
	 */
	List<VolumecardAccount> getAccountsByMobile(String mobile );
	/**
	 * 获取帐户列表
	 * @param mobile
	 * @return
	 */
	List<QueryUserCardListResultVO> getUserAccounts(@Param("mobileNum")String mobile,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("status")List<String> status,@Param("channelNo")String  channelNo);

	/**
	 * 查询合适的容量卡以进行支付
	 * @param cs
	 * @return
	 */
	List<VolumecardAccount> getAccountByCardSelecter(CardSelecter cs);

}