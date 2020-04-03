package com.hummingbird.maaccount.mapper;

import java.util.List;

import com.hummingbird.maaccount.entity.Shop;
import com.hummingbird.maaccount.vo.QueryShopVO;

public interface ShopMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Long id);
    
    Shop selectShopAddress(String code);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    List<Shop> findByList2(String businessType, String supplierCode, String supplierName, String supplierShortName, String shopCode, String shopName, String shopShortName, String terminalCode,
            String province, String city, String area, String specifics, String status, Integer first, Integer last);

    List<Shop> findByList(QueryShopVO qeuryShopVO);

    int shopExistFlag(String code);

    /**
     * 根据code查询并且不理会是否已经逻辑删除
     * @param code
     * @return
     */
    Shop selectByCode(String code);

    /**
     * 查询所有门店
     */
    List<Shop> getAllShopList();

}