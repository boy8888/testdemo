package com.hummingbird.maaccount.util.sign;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hummingbird.maaccount.util.EncryptUtil;

public class SignUtil {

    private static final Logger log = LoggerFactory.getLogger(SignUtil.class);


    /**
     * 计算签名；
     * 首先将通知过来的所有参数（注意是所有参数，而不是下面文档中列举的参数），除去sign本身，以及值是空的参数，按参数名字母升序排序，然后按参数1值1参数2值2…参数n值n（这里的参数和值必须是客户端通知过来的原始值，不能是经过处理的，
     * 如不能将&quot;转成”后再拼接）的方式进行连接，得到一个字符串；然后在连接后得到的字符串前面加上通知验证密钥，然后计算md5值，转成大写;
     * 最后拿这个计算出来的MD5串同客户端通知里面的sign参数值做比较，如果一致，表明该通知是安全的(是淘宝发的且没有被篡改过)；否则，该通知可能是被篡改过的，存在不安全性
     * isFilterFirstKey 是否过滤第一个key加入签名方式
     * isFilterLastKey 是否过滤最后一个key加入签名方式
     */
    public static String computeSign(String key, Map<String, Object> paraMap, boolean isFilterFirstKey, boolean isFilterLastKey) {
        Map<String, Object> filterMap = MapUtil.filterMapByKeyValue(paraMap, "sign", "");//过滤空值和sign键
        Map<String, Object> sortMap = MapUtil.sortMapByKey(filterMap);//升序
        StringBuffer sb = new StringBuffer();
        if (!isFilterFirstKey) {
            sb.append(key);
        }
        Iterator iter = sortMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object mapKey = entry.getKey();
            Object mapValue = entry.getValue();
            sb.append(mapKey);
            sb.append(mapValue);
        }
        if (!isFilterLastKey) {
            sb.append(key);
        }
        log.info("组装参数= " + sb.toString());
        String sign = EncryptUtil.getMD5Str(sb.toString(), "UTF-8");
        sign = sign.toUpperCase();//计算签名
        return sign;
    }

}
