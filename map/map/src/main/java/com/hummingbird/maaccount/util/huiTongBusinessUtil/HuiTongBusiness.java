package com.hummingbird.maaccount.util.huiTongBusinessUtil;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hummingbird.maaccount.util.huiTongBusinessUtil.VO.HuiTongBusinessBaseVO;
import com.hummingbird.maaccount.util.huiTongBusinessUtil.VO.HuiTongValidatePaymentPwdVO;
import com.hummingbird.maaccount.util.huiTongBusinessUtil.socketClient.SocketBean;
import com.hummingbird.maaccount.util.huiTongBusinessUtil.util.HuiTongBusinessConfig;
import com.hummingbird.maaccount.util.huiTongBusinessUtil.util.HuiTongBusinessParamGenerate;
import com.hummingbird.maaccount.util.huiTongBusinessUtil.util.HuiTongBusinessUtil;

public class HuiTongBusiness {
    
     private static final Log    log    = LogFactory.getLog(HuiTongBusiness.class);
	 //验证密码
     public static HuiTongBusinessBaseVO validatePWD(String userName,String paymentPWD) throws Exception{
    	 try {
    		 //组织参数
    		 String json=HuiTongBusinessParamGenerate.createValiPWDParam(userName, paymentPWD);
    		 //读取返回
        	 String result=sendToSocket(json);
			 HuiTongValidatePaymentPwdVO returnVo=HuiTongBusinessUtil.JsonToObject(result, HuiTongValidatePaymentPwdVO.class);
			 return returnVo;
		 } catch (Exception e) {
			 throw new Exception(e);
		 }
    	 
     }
     
     //发送验证请求到服务端并读取返回值
     private static String sendToSocket(String json) throws Exception{
    	 SocketBean socketBean=SocketBean.create(HuiTongBusinessConfig.HUI_TONG_ADDRESS, HuiTongBusinessConfig.HUI_TONG_PORT,HuiTongBusinessConfig.HUI_TONG_INPUT_CHARSET,HuiTongBusinessConfig.HUI_TONG_OPEN_SSL);
    	 try{
    		 log.info("汇通接口请求发送的数据为："+json);
    	     socketBean.write(json);
             String result=new String(socketBean.read(HuiTongBusinessConfig.HUI_TONG_READ_BUFF_SIZE),HuiTongBusinessConfig.HUI_TONG_INPUT_CHARSET).trim();
    	     System.out.println("汇通接口返回的数据为："+result);
    	     return result;
    	 }finally{
    		 socketBean.closeSocket();
    	 }
     }
}
