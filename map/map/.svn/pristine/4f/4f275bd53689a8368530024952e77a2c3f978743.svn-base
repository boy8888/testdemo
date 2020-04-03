package com.hummingbird.maaccount.util.synuserinfo.config;

import com.hummingbird.maaccount.util.synuserinfo.util.SynUserCenterPropertiesUtil;

public class SynUserCenterConfig {
	
	/**用户ID*/
	public final static String CLIENT_ID;
	
	/**用户密钥*/
	public final static String CLIENT_SECRET;
	
	/**默认的字符编码*/
	public final static String CHARSET;
	
	/**请求协议(http 或 https)*/
	public final static String PROTOCOL;
	
	/**默认的读取远程数据超时时间单位毫秒*/
	public final static Integer SO_TIMEOUT;
	
	/**获取授权码的路径*/
	public final static String GET_CODE_PATH;
	
	/**获取访问令牌的路径*/
	public final static String GET_ACCESS_TOKEN_PATH;
	
	/**更新密码的访问路径*/
	public final static String UPDATE_PASSWORD_PATH;
	
	/**获取授权码的默认response_type类型(由接口方提供，不允许更改)*/
	public final static String CODE_DEFAULT_RESPONSE_TYPE="code";
	
	/**获取访问令牌的默认GRANT_TYPE类型(由接口方提供，不允许更改)*/
	public final static String ACCESS_TOKEN_DEFAULT_GRANT_TYPE="authorization_code";
	
	public final static String PROPERTIES_FILE_PATH="synUserCenterData.properties";
	
	static {
		String filePath=SynUserCenterConfig.class.getClassLoader().getResource(PROPERTIES_FILE_PATH).getPath();
		SynUserCenterPropertiesUtil synUserCenterPropertiesUtil=new SynUserCenterPropertiesUtil(filePath);
		CLIENT_ID=synUserCenterPropertiesUtil.getString("CLIENT_ID");
		CLIENT_SECRET=synUserCenterPropertiesUtil.getString("CLIENT_SECRET");
		CHARSET=synUserCenterPropertiesUtil.getString("CHARSET");
		PROTOCOL=synUserCenterPropertiesUtil.getString("PROTOCOL");
		SO_TIMEOUT=synUserCenterPropertiesUtil.getInteger("SO_TIMEOUT",15000);
		GET_CODE_PATH=synUserCenterPropertiesUtil.getString("GET_CODE_PATH");
		GET_ACCESS_TOKEN_PATH=synUserCenterPropertiesUtil.getString("GET_ACCESS_TOKEN_PATH");
		UPDATE_PASSWORD_PATH=synUserCenterPropertiesUtil.getString("UPDATE_PASSWORD_PATH");
	}

}
