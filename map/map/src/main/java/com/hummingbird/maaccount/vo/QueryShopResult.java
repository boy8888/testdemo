package com.hummingbird.maaccount.vo;

import java.util.List;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:查询门店VO值对象封装结果集</td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年4月11日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
public class QueryShopResult {

    private Integer           total;

    private List<QueryShopVO> rows;


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<QueryShopVO> getRows() {
        return rows;
    }

    public void setRows(List<QueryShopVO> rows) {
        this.rows = rows;
    }

}
