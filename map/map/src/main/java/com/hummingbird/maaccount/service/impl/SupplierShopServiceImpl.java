package com.hummingbird.maaccount.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.entity.Address;
import com.hummingbird.maaccount.entity.Shop;
import com.hummingbird.maaccount.entity.Supplier;
import com.hummingbird.maaccount.entity.Terminal;
import com.hummingbird.maaccount.mapper.ShopMapper;
import com.hummingbird.maaccount.mapper.SupplierMapper;
import com.hummingbird.maaccount.mapper.TerminalMapper;
import com.hummingbird.maaccount.service.AddressService;
import com.hummingbird.maaccount.service.SupplierShopService;
import com.hummingbird.maaccount.vo.QueryShopVO;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:商户门店终端号接口实现类</td>
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
/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:</td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年4月19日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
@Service
public class SupplierShopServiceImpl implements SupplierShopService {

    @Autowired
    private SupplierMapper supplierDao;

    @Autowired
    private ShopMapper     shopDao;

    @Autowired
    private TerminalMapper terminalDao;

    @Autowired
    private AddressService addressService;


    /**
     * 创建商户
     */
    @Override
    public boolean createSupplier(Supplier supplier) {
        //addressService.createAddress(supplier.getAddress());//根据Epay系统同步数据结果判断，商户未同步具体地址信息，故此处新增地址注释掉
        int result = 1;
        Supplier DBsupplier = supplierDao.selectByCode(supplier.getCode());
        //判断是否已经删除，如果已经删除则将deleted字段改成0(表示未删除)并更新该记录
        if (DBsupplier != null) {
            if (DBsupplier.getDeleted() != null && DBsupplier.getDeleted() == 1) {//对于已删除的如果再次增加，则更新其deleted状态=未删除
                supplier.setDeleted(0);
            }
            supplier.setUpdatedDate(new Date());
            result = supplierDao.updateByPrimaryKeySelective(supplier);
        } else if (DBsupplier == null) {
            //数据库中无相应记录直接插入
            supplier.setCreatedDate(new Date());
            result = supplierDao.insert(supplier);
        }
        //return result > 0;
        return true;
    }

    /**
     * 创建门店
     */
    @Override
    public boolean createShop(Shop shop) {
        addressService.createAddress(shop.getAddress());//新增地址
        int result = 1;
        Shop DBshop = shopDao.selectByCode(shop.getCode());
        //判断是否已经删除，如果已经删除则将deleted字段改成0(表示未删除)并更新该记录
        if (DBshop != null) {
            if (DBshop.getDeleted() != null && DBshop.getDeleted() == 1) {//对于已删除的如果再次增加，则更新其deleted状态=未删除
                shop.setDeleted(0);
            }
            shop.setUpdatedDate(new Date());
            result = shopDao.updateByPrimaryKeySelective(shop);
        } else if (DBshop == null) {
            //数据库中无相应记录直接插入
            shop.setCreatedDate(new Date());
            result = shopDao.insert(shop);
        }
        //return result > 0;
        return true;
    }

    /**
     * 创建终端号
     */
    @Override
    public boolean createTerminal(Terminal terminal) {
        int result = 1;
        Terminal DBterminal = terminalDao.selectByCode(terminal.getCode());
        //判断是否已经删除，如果已经删除则将deleted字段改成0(表示未删除)并更新该记录
        if (DBterminal != null) {
            if (DBterminal.getDeleted() != null && DBterminal.getDeleted() == 1) {//对于已删除的如果再次增加，则更新其deleted状态=未删除
                terminal.setDeleted(0);
            }
            result = terminalDao.updateByPrimaryKeySelective(terminal);
        } else if (DBterminal == null) {
            //数据库中无相应记录直接插入
            result = terminalDao.insert(terminal);
        }
        //return result > 0;
        return true;
    }

    /**
     * 修改商户(包含逻辑删除)
     */
    @Override
    public boolean updateSupplier(Supplier supplier) {
        supplier.setUpdatedDate(new Date());
        int result = supplierDao.updateByPrimaryKeySelective(supplier);
        return result > 0;
    }

    /**
     * 修改门店(包含逻辑删除)
     */
    @Override
    public boolean updateShop(Shop shop) {
        Shop existShop = shopDao.selectShopAddress(shop.getCode());
        if (existShop != null) {
            Address address = shop.getAddress();
            Address existAddress = existShop.getAddress();
            if (existAddress == null) {
                existAddress = new Address();
            }
            if (address == null) {
                address = new Address();
            }
            existAddress.setProvince(address.getProvince());
            existAddress.setCity(address.getCity());
            existAddress.setArea(address.getArea());
            existAddress.setSpecifics(address.getSpecifics());
            existAddress.setLatitude(address.getLatitude());
            existAddress.setLongitude(address.getLongitude());
            existAddress.setContact(address.getContact());
            existAddress.setMobile(address.getMobile());
            existAddress.setPhone(address.getPhone());
            existAddress.setMailAddress(address.getMailAddress());
            existAddress.setPhone(address.getPhone());
            addressService.updateAddress(existAddress);//修改地址
            address.setCode(existAddress.getCode());//此处非常重要
            shop.setAddress(address);
        }
        shop.setUpdatedDate(new Date());
        int reuslt = shopDao.updateByPrimaryKeySelective(shop);
        return reuslt > 0;
    }

    /**
     * 修改终端号(包含逻辑删除)
     */
    @Override
    public boolean updateTerminal(Terminal terminal) {
        int result = terminalDao.updateByPrimaryKeySelective(terminal);
        return result > 0;
    }

    /**
     * 创建商户门店终端号；备注：未做事务处理，例如：未做统一回滚处理
     */
    @Deprecated
    @Override
    public boolean createSupplierShop(Shop shop) {
        boolean result = createSupplier(shop.getSupplier());
        if (result) {//result暂时未设置相关作用
            result = createShop(shop);
        }
        Terminal terminal = new Terminal();
        String terminalCodes = shop.getTerminalCodes();
        String[] terminalCodeList = terminalCodes.split(",");
        for (String terminalCode : terminalCodeList) {
            terminal.setShop(shop);
            terminal.setCode(terminalCode);
            if (result) {
                result = createTerminal(terminal);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * 查询所有商户
     */
    @Override
    public List<Supplier> getAllSupplierList() {
        return supplierDao.getAllSupplierList();
    }

    /**
     * 查询所有门店
     */
    @Override
    public List<Shop> getAllShopList() {
        return shopDao.getAllShopList();
    }

    /**
     * 查询所有终端号
     */
    @Override
    public List<Terminal> getAllTerminalList() {
        return terminalDao.getAllTerminalList();
    }

    /**
     * 获取门店列表
     */
    @Override
    public List<QueryShopVO> getShopList(String businessType, String goodsType, String supplierCode, String supplierName, String supplierShortName, String shopCode, String shopName,
            String shopShortName, String terminalCode, String province, String city, String area, String specifics, String status, Integer first, Integer last) {
        QueryShopVO vo = new QueryShopVO();
        vo.setBusinessType(businessType);
        vo.setGoodsType(goodsType);
        vo.setSupplierCode(supplierCode);
        vo.setSupplierName(supplierName);
        vo.setSupplierShortName(supplierShortName);
        vo.setCode(shopCode);
        vo.setName(shopName);
        vo.setShortName(shopShortName);
        vo.setStatus(status);
        vo.setTerminalCodes(terminalCode);
        vo.setProvince(province);
        vo.setCity(city);
        vo.setArea(area);
        vo.setSpecifics(specifics);
        if (first == null) {
            first = 0;
        }
        if (last == null) {
            last = 100;
        }
        vo.setFirst(first);
        vo.setLast(last - first);
        List<Shop> shops = shopDao.findByList(vo);
        List<QueryShopVO> result = null;
        if (shops != null && !shops.isEmpty()) {
            result = new ArrayList<QueryShopVO>();
            for (Shop shop : shops) {
                List<String> terminals = terminalDao.findTerminalCodeListByShop(shop.getCode());
                String terminalListString = createTerminaListString(terminals);
                result.add(createQueryShopVO(shop, terminalListString));
            }
        }
        return result;
    }

    /**
     * 生成终端字符串列表以“,”隔开
     * @param terminals terminalCode集合
     * @return
     */
    private String createTerminaListString(List<String> terminals) {
        if (terminals == null || terminals.size() == 0) { return null; }
        StringBuilder sb = new StringBuilder();
        for (String terminal : terminals) {
            sb.append(terminal).append(",");
        }
        if (sb.toString().contains(",")) {
            return sb.toString().substring(0, sb.toString().length() - 1);
        } else {
            return sb.toString();
        }
    }

    /**
     * 组装QueryShopVO
     * @param shop 门店对象
     * @param terminals 终端字符串列表以“,”隔开
     * @return
     */
    private QueryShopVO createQueryShopVO(Shop shop, String terminalCodes) {
        if (shop == null) { return null; }
        QueryShopVO temp = new QueryShopVO();
        temp.setCode(trim(shop.getCode()));
        temp.setName(trim(shop.getName()));
        temp.setShortName(trim(shop.getShortName()));
        temp.setStatus(trim(shop.getStatus()));
        temp.setTerminalCodes(trim(terminalCodes));
        if (shop.getAddress() != null) {
            temp.setProvince(trim(shop.getAddress().getProvince()));
            temp.setCity(trim(shop.getAddress().getCity()));
            temp.setArea(trim(shop.getAddress().getArea()));
            temp.setSpecifics(trim(shop.getAddress().getSpecifics()));
            temp.setContact(trim(shop.getAddress().getContact()));
            temp.setMobile(trim(shop.getAddress().getMobile()));
            temp.setPhone(trim(shop.getAddress().getPhone()));
            temp.setLatitude(trim(shop.getAddress().getLatitude()));
            temp.setLongitude(trim(shop.getAddress().getLongitude()));
        } else {
            temp.setProvince("NULL");
            temp.setCity("NULL");
            temp.setArea("NULL");
            temp.setSpecifics("NULL");
            temp.setContact("NULL");
            temp.setMobile("NULL");
            temp.setPhone("NULL");
            temp.setLatitude("NULL");
            temp.setLongitude("NULL");
        }
        if (shop.getSupplier() != null) {
            temp.setSupplierCode(trim(shop.getSupplier().getCode()));
            temp.setSupplierName(trim(shop.getSupplier().getName()));
            temp.setSupplierShortName(trim(shop.getSupplier().getShortName()));
            temp.setBusinessType(trim(shop.getSupplier().getBusinessType()));
            temp.setGoodsType(trim(shop.getSupplier().getGoodsType()));
        } else {
            temp.setSupplierCode("NULL");
            temp.setSupplierName("NULL");
            temp.setSupplierShortName("NULL");
            temp.setBusinessType("NULL");
            temp.setGoodsType("NULL");
        }
        return temp;
    }

    private String trim(String str) {
        if (str == null) {
            str = "NULL";
        }
        return str;
    }

}
