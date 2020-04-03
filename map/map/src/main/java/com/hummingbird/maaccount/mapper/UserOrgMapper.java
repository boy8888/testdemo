package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.UserOrg;

public interface UserOrgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserOrg record);

    int insertSelective(UserOrg record);

    UserOrg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserOrg record);

    int updateByPrimaryKey(UserOrg record);
    
    int insertUserOrgList(List<UserOrg> userOrgList);
    
    List<UserOrg> selectByUserAndOrgType(@Param("userId") Integer userId,@Param("orgType")String orgType);
    
    int deleteByPrimaryKeys(List<Long> ids);
}