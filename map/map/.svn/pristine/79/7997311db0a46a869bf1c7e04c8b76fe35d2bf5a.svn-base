package com.hummingbird.maaccount.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hummingbird.maaccount.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	/**
	 * 根据手机号进行查询
	 * @param mobileNum
	 * @return
	 */
	User selectByMobile(String mobileNum);
	//添加用户注册记录应用id 记录写到表 t_user_register_appid 
	int insertAppId(@Param("userId")Integer userId,@Param("appId")String appId );

	/**
	 * 查询符合条件的数据,准备调整des支付密码
	 * @return
	 */
	List<User> select4Deschange();
	
	/**
     * 根据用户名进行查询
     * @param mobileNum
     * @return
     */
    User selectByUserName(String user_name);
    
    /**
     * 根据邮箱进行查询
     * @param mobileNum
     * @return
     */
    User selectByEmail(String email);
}