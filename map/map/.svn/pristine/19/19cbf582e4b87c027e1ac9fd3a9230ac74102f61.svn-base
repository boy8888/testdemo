package com.hummingbird.maaccount.mapper;

import com.hummingbird.commonbiz.vo.AppMobile;
import com.hummingbird.maaccount.entity.UserAccountCode;

public interface UserAccountCodeMapper {
    int deleteByPrimaryKey(Integer idtUserAccountcode);

    int insert(UserAccountCode record);

    UserAccountCode selectByPrimaryKey(Integer idtUserAccountcode);

	/**
	 * 根据app和手机号码查询帐户验证码
	 * @param request
	 * @return
	 */
	UserAccountCode selectUserAccountCode(AppMobile request);
}