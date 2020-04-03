package com.hummingbird.maaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.entity.Address;
import com.hummingbird.maaccount.mapper.AddressMapper;
import com.hummingbird.maaccount.service.AddressService;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:地址接口实现类</td>
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
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressDao;


    /**
     * 创建地址
     */
    @Override
    public boolean createAddress(Address address) {
        int result = 1;
        Address DBaddress = addressDao.selectByCode(address.getCode());
        if (DBaddress == null) {
            result = addressDao.insert(address);
        } else {
            result = addressDao.updateByPrimaryKeySelective(address);
        }
        return result > 0;
    }

    /**
     * 修改地址
     */
    @Override
    public boolean updateAddress(Address address) {
        int result = 1;
        Address DBaddress = addressDao.selectByCode(address.getCode());
        if (DBaddress == null) {
            result = addressDao.insert(address);
        } else {
            result = addressDao.updateByPrimaryKeySelective(address);
        }
        return result > 0;
    }

    /**
     * 查询地址
     */
    @Override
    public Address queryAddress(String code) {
        return addressDao.selectByCode(code);
    }

}
