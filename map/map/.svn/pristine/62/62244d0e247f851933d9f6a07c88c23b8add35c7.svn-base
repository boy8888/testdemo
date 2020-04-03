package com.hummingbird.maaccount.util.huiTongBusinessUtil.util;

import java.util.Map;

import com.hummingbird.maaccount.util.huiTongBusinessUtil.VO.HuiTongValidatePaymentPwdVO;

public class HuiTongBusinessParamGenerate {

	
	//组织请求汇通验证密码接口的json数据
    public static String createValiPWDParam(String userId,String paymentPWD){
	   	 //组织发送的数据
	   	 String systrace=HuiTongBusinessUtil.createRandNum(6);
	   	 
	   	 HuiTongValidatePaymentPwdVO validatePaymentPwdByHuiTongVo=new HuiTongValidatePaymentPwdVO(HuiTongBusinessConfig.HUI_TONG_VALIDATE_PWD_TXN_ID, HuiTongBusinessConfig.HUI_TONG_CHANNEL, userId, systrace, paymentPWD);
	   	 
	   	 return createParam(validatePaymentPwdByHuiTongVo);
    }
    
    private static String createParam(Object o){
    	
	   	 //将VO转换成MAP
	   	 Map<String,Object> param=HuiTongBusinessUtil.ObjectToMap(o);
	   	 //TODO 转MD5
	   	 String sign=HuiTongBusinessUtil.MD5(HuiTongBusinessUtil.getSignPlaintext(param)).toUpperCase();
	   	 
	   	 param.put("sign", sign);
	   	 
   	     return HuiTongBusinessUtil.ObjectToJson(param);
    }

}
