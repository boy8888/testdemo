package com.hummingbird.maaccount.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.util.json.JSONException;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.service.impl.UserService;

/**
* @ClassName: NewAuthController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zouyi
* @date 2019年4月18日 下午4:31:53
* @version V1.0  
*/
@Controller
@RequestMapping("/user")
public class NewAuthController extends BaseController{
	
	@Autowired(required = true)
	private UserService userSrv;

	@RequestMapping("/query")
	public @ResponseBody Object queryUser(String mobileNum) throws JSONException{
		JSONObject object = new JSONObject();
		if(!StringUtils.isNotBlank(mobileNum)){
			object.put("result", "0");
			object.put("info","mobile is null");
			return object.toString();
		}
		//验证手机号格式开头数字必须是1 并且是11位
		boolean phoneV = phoneValite(mobileNum);
		if(!phoneV){
			object.put("result", "0");
			object.put("info", "phone bad format");
			return object.toString();
		}
		
		User user = userSrv.getUserByMobile(mobileNum);
		if(user!=null){
			object.put("result", "1");
			object.put("info", user.getInsertTime().getTime());
			return object.toString();
		}
		object.put("result", "0");
		object.put("info", "users are not registered");
		return object.toString();
	}
	
	public static boolean phoneValite(String phone){
		String  regPhone  ="^1[\\d]{10}";
		 // 编译正则表达式
	    Pattern pattern = Pattern.compile(regPhone);
	    Matcher matcher = pattern.matcher(phone);
	    return matcher.matches();
	}
	
}
