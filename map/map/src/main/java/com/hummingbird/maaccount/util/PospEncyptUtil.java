/**
 * 
 * PospEncyptUtil.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.maaccount.util;

import com.hummingbird.common.exception.ToolsException;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.StrUtil;
import com.hummingbird.common.util.TripleDESUtil;

/**
 * @author john huang
 * 2015年2月28日 下午2:54:52
 * 本类主要做为
 */
public class PospEncyptUtil {
	
	static String prefix = "06";
	

	/**
	 * 解密
	 * @param source
	 * @param salt
	 * @return
	 * @throws ToolsException 
	 */
	public static String decypt(String source,String salt) throws ToolsException{
		String encyptkey = new PropertiesUtil().getProperty("posp.encyptkey");
		if(StrUtil.isBlank(encyptkey)){
			encyptkey = "1111111111111111";
//			encyptkey = "3131313131313131";
		}
//		System.out.println(String.format("加密内容：%s，手机号%s",source,salt);
		String mingwen = Des2cUtil.decryptDes(source, encyptkey);
		
		String rawsalt = ("0000000000000000"+salt);
		//做了移位处理
		String fillsalt = rawsalt.substring(rawsalt.length()-16-1,rawsalt.length()-1);
		//异或
		String fillpasswd = xor(mingwen,fillsalt);
		//去掉前面的06和后面的FF，因为pos机不能输入字母
		String passwd = fillpasswd.replaceFirst(prefix, "").replaceAll("F*", "");
		return passwd;
	}
	
	/**
	 * 加密
	 * @param str
	 * @param salt
	 * @throws ToolsException 
	 */
	public static String encypt(String str,String salt) throws ToolsException{
//		以下所有数据格式均为hex
//		秘钥明文：
//		11111111111111112222222222222222
//		密码：
//		123456
//		参与运算：
//		06123456FFFFFFFF
//
//		手机号：
//		13811112222
//		前补“0”
//		0000013811112222
//		参与运算:
//		0000001381111222
//
//		异或：
//		06123456FFFFFFFF
//		0000001381111222
//		结果：
//		061234457EEEEDDD
//
//		加密：
//		11111111111111112222222222222222
//		061234457EEEEDDD
//		结果：
//		66F2A80279ACD921
		
		String encyptkey = new PropertiesUtil().getProperty("posp.encyptkey");
		if(StrUtil.isBlank(encyptkey)){
			encyptkey = "1111111111111111";
//			encyptkey = "3131313131313131";
		}
//		System.out.println(String.format("加密内容：%s，手机号%s",str,salt));
		String fillstr = ("06"+str+"FFFFFFFFFFFFFFFF").substring(0,16);
//		System.out.println(String.format("处理后的内容%s",fillstr));
		String rawsalt = ("0000000000000000"+salt);
		//做了移位处理
		String fillsalt = rawsalt.substring(rawsalt.length()-16-1,rawsalt.length()-1);
//		System.out.println(String.format("处理后的手机内容%s",fillsalt));
		String yihuo = xor(fillstr,fillsalt);
//		System.out.println(String.format("异或内容%s",yihuo));
		String encrystr = Des2cUtil.encryptDes(yihuo, encyptkey);
//		System.out.println(String.format("des加密结果%s",encrystr));
		return encrystr;
	}
	
	static String xor(String str1,String str2){
		String str="";  
//		System.out.println("异或"+str1+"和"+str2);
        int a=(str1.length());  
        int j=0;  
        int Xor=0;         
        for(int i=0;i<a;i++){
        	Integer m = Integer.valueOf(str1.substring(i,i+1), 16);
        	Integer n =Integer.valueOf(str2.substring(i,i+1), 16);
        	Xor =m^n;
//        	System.out.println("异或的数字为"+m+"和"+n+"结果为"+Xor+",转16进制后是"+Integer.toHexString( Xor));
            str = str +Integer.toHexString( Xor);       //(char)获取ASCII码值对应的值                
        }
        return str.toUpperCase();
	}
	
	public static void main(String[] args) throws ToolsException {
		String enc = PospEncyptUtil.encypt("123456", "18922260815");
		System.out.println(String.format("明文=%s,手机号=%s,加密后=%s","123456", "13719406133",enc));
		String dec=PospEncyptUtil.decypt("8A79BD529BA4ABB9", "18122449597");
		System.out.println(String.format("密文=%s,手机号=%s,解密后=%s","202B1B0F0025328A", "13811112222",dec));
//		PospEncyptUtil.encypt("096743", "13640698445");
//		dec=PospEncyptUtil.decypt("6C0DBA8BB91D3890", "18620120406");
//		System.out.println(String.format("密文=%s,手机号=%s,解密后=%s","8E55355FABAEAC38", "13423946770",dec));
		
//		System.out.println(xor("06123456FFFFFFFF","0000001381111222"));
	}
	
}
