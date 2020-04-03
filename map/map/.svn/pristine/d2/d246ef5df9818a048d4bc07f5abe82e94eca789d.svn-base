package com.hummingbird.maaccount.util;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:凯撒算法</td>
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
public class Caesar {

    private String table;

    private int    seedA = 1103515245;

    private int    seedB = 98765;


    public Caesar(String table, int seed) {
        this.table = chaos(table, seed, table.length());
    }

    public Caesar(String table) {
        this(table, 11);
    }

    public Caesar() {
        this(11);
    }

    public Caesar(int seed) {
        this("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", seed);
    }

    public char dict(int i, boolean reverse) {
        int s = this.table.length();
        int index = reverse ? s - i : i;
        return this.table.charAt(index);
    }

    public int dict(char c, boolean reverse) {
        int s = this.table.length();
        int index = this.table.indexOf(c);
        return reverse ? s - index : index;
    }

    public int seed(int seed) {
        long temp = seed;
        return (int) (temp * this.seedA + this.seedB & 0x7FFFFFFF);
    }

    public String chaos(String data, int seed, int cnt) {
        StringBuffer buf = new StringBuffer(data);

        int r = data.length();
        for (int i = 0; i < cnt; i++) {
            seed = seed(seed);
            int a = seed % r;
            seed = seed(seed);
            int b = seed % r;
            char tmp = buf.charAt(a);
            buf.setCharAt(a, buf.charAt(b));
            buf.setCharAt(b, tmp);
        }
        return buf.toString();
    }

    public String crypto(boolean reverse, int key, String text) {
        String ret = null;
        StringBuilder buf = new StringBuilder();
        int s = this.table.length();
        int e = text.length();

        for (int i = 0; i < e; i++) {
            int m = dict(text.charAt(i), reverse);
            if (m < 0) break;
            m = m + key + i;
            buf.append(dict(m % s, reverse));
        }
        if (buf.length() == e) ret = buf.toString();
        return ret;
    }

    public String encode(int key, String text) {
        return crypto(false, key, text);
    }

    public String decode(int key, String text) {
        return crypto(true, key, text);
    }
}