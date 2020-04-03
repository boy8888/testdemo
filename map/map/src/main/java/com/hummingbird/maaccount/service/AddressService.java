package com.hummingbird.maaccount.service;

import com.hummingbird.maaccount.entity.Address;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:地址接口</td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年3月31日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
public interface AddressService {

    /**
     * 创建地址
     */
    public boolean createAddress(Address address);

    /**
     * 修改地址
     */
    public boolean updateAddress(Address address);

    /**
     * 查询地址
     */
    public Address queryAddress(String code);

}
