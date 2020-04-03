package com.hummingbird.maaccount.util.synuserinfo.util;

import java.net.Socket;
import java.util.Map;

import com.hummingbird.maaccount.util.synuserinfo.config.SynUserCenterConfig;

import jodd.http.HttpRequest;
import jodd.http.net.SocketHttpConnection;

public class HttpHelper {
	
	/**
	 * 提交数据到远程服务端(表单形式)附带鉴权用户信息
	 * @param url   目标地址
	 * @param userName  鉴权用户名
	 * @param password  鉴权密码
	 * @param form      表单信息
	 * @param soTimeout 超时时间(毫秒)
	 * @param charset   请求的编码
	 * @return 返回json字符串
	 */
	public static String formPostTakeUserInfo(String url,Map<String,Object> form){
		try {
			HttpRequest request=HttpRequest.post(url);
			request.protocol(SynUserCenterConfig.PROTOCOL);
			//设置读取超时
			request.open();
			SocketHttpConnection socketHttpConnection=(SocketHttpConnection)request.httpConnection();
			Socket socket=socketHttpConnection.getSocket();
			socket.setSoTimeout(SynUserCenterConfig.SO_TIMEOUT);
			//设置账户密码
			request.basicAuthentication(SynUserCenterConfig.CLIENT_ID, SynUserCenterConfig.CLIENT_SECRET);
			//设置编码
			request.charset(SynUserCenterConfig.CHARSET);
			//设置表单信息
			request.form(form);
			//发送请求
			return request.send().bodyText();
		} catch (Exception e) {
			throw new RuntimeException("joddHttpHelper请求失败",e);
		}
	}
	
	/**
	 * 提交数据到远程服务端(表单形式)
	 * @param url   目标地址
	 * @param userName  鉴权用户名
	 * @param password  鉴权密码
	 * @param form      表单信息
	 * @param soTimeout 超时时间(毫秒)
	 * @param charset   请求的编码
	 * @return 返回json字符串
	 */
	public static String formPost(String url,Map<String,Object> form){
		try {
			HttpRequest request=HttpRequest.post(url);
			request.protocol(SynUserCenterConfig.PROTOCOL);
			//设置读取超时
			request.open();
			SocketHttpConnection socketHttpConnection=(SocketHttpConnection)request.httpConnection();
			Socket socket=socketHttpConnection.getSocket();
			socket.setSoTimeout(SynUserCenterConfig.SO_TIMEOUT);
			//设置编码
			request.charset(SynUserCenterConfig.CHARSET);
			//设置表单信息
			request.form(form);
			//发送请求
			return request.send().bodyText();
		} catch (Exception e) {
			throw new RuntimeException("joddHttpHelper请求失败",e);
		}
	}

}
