package com.hummingbird.maaccount.service;

import java.util.List;

import com.hummingbird.maaccount.entity.Shop;
import com.hummingbird.maaccount.entity.Supplier;
import com.hummingbird.maaccount.entity.Terminal;
import com.hummingbird.maaccount.vo.QueryShopVO;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:商户门店终端号接口</td>
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
public interface SupplierShopService {

    /**
     * 创建商户
     */
    public boolean createSupplier(Supplier supplier);

    /**
     * 创建门店
     */
    public boolean createShop(Shop shop);

    /**
     * 创建终端号
     */
    public boolean createTerminal(Terminal terminal);

    /**
     * 修改商户(包含逻辑删除)
     */
    public boolean updateSupplier(Supplier supplier);

    /**
     * 修改门店(包含逻辑删除)
     */
    public boolean updateShop(Shop shop);

    /**
     * 修改终端号(包含逻辑删除)
     */
    public boolean updateTerminal(Terminal terminal);

    /**
     * 创建商户门店终端
     */
    @Deprecated
    public boolean createSupplierShop(Shop shop);

    /**
     * 查询所有商户
     */
    public List<Supplier> getAllSupplierList();

    /**
     * 查询所有门店
     */
    public List<Shop> getAllShopList();

    /**
     * 查询所有终端号
     */
    public List<Terminal> getAllTerminalList();

    /**
     * 获取门店列表
     */
    public List<QueryShopVO> getShopList(String businessType, String goodsType, String supplierCode, String supplierName, String supplierShortName, String shopCode, String shopName,
            String shopShortName, String terminalCode, String province, String city, String area, String specifics, String status, Integer first, Integer last);

}
