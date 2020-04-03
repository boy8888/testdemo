package com.hummingbird.maaccount.mapper;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.UserAttr;

public interface UserAttrMapper {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer userAttrId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(UserAttr record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(UserAttr record);

    /**
     * 根据主键查询记录
     */
    UserAttr selectByPrimaryKey(Integer userAttrId);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(UserAttr record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(UserAttr record);

	/**
	 * 查询用户属性
	 * @param mobileNum
	 * @param attr
	 * @return
	 */
	UserAttr selectUserAttr(@Param("mobileNum") String mobileNum,@Param("attr") String attr);

	/**
	 * 删除属性
	 * @param userId
	 * @param attr
	 */
	int deleteUserAttr(@Param("userId") Integer userId,@Param("attr") String attr);
}