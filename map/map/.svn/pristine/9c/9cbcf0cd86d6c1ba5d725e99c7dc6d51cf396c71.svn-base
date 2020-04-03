package com.hummingbird.maaccount.util;

import java.util.Random;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:随机数工具类</td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年4月6日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
public class NumRandom {

    private static final Random RANDOM        = new Random();

    private static final Caesar NUMBER_CAESAR = new Caesar("0123456789", RANDOM.nextInt(1000));


    public static String getRandom(int n) {//逐位生成随机数
        Random random = new Random();
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append((char) ('0' + random.nextInt(10)));
        }
        return sb.toString();
    }

    public static String getRandomRecursion(int n) {//递归调用示例(所产生的随机数一定要包含91)
        String random = getRandom(n);
        if (n > 2 && !random.contains("91")) {
            return getRandomRecursion(n);
        } else {
            return random;
        }
    }

    /**
     * 产生指定长度的随机数
     **/
    public static String randomNumber(int id, int len) {
        int key = RANDOM.nextInt(100);
        String num = String.format("%0" + len + "d", new Object[] { Integer.valueOf(id) });
        return NUMBER_CAESAR.encode(key, num);
    }

    /**
     * 获取系统编码
     **/
    public static String getSystemCode() {
        String code = String.valueOf(System.currentTimeMillis()).substring(7, 13) + String.valueOf(System.nanoTime()).substring(2, 13) + randomNumber(0, 4);//微秒时间后6位+纳秒时间后11位+4位随机数=21位数字
        return code;
    }

}
