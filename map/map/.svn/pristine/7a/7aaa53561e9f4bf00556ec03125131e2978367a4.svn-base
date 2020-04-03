package com.hummingbird.maaccount.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.json.JSONArray;
import com.hummingbird.common.util.json.JSONException;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.maaccount.entity.Address;
import com.hummingbird.maaccount.entity.Shop;
import com.hummingbird.maaccount.entity.Supplier;
import com.hummingbird.maaccount.entity.Terminal;
import com.hummingbird.maaccount.service.SupplierShopService;
import com.hummingbird.maaccount.vo.QueryShopResult;
import com.hummingbird.maaccount.vo.QueryShopVO;

import hprose.client.HproseHttpClient;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:商户门店测试类</td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年4月7日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
public class SupplierShopServiceImplTest {

    private SupplierShopService     supplierShopService;

    private static HproseHttpClient client = new HproseHttpClient();


    @Before
    public void init() {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        supplierShopService = ac.getBean(SupplierShopService.class);
    }

    /**
     * 创建门店
     */
    @Test
    public void createSupplierShop() {
        Integer pageSize = 100;
        Integer first = 0;
        Integer last = 0;
        for (int i = 0; i < 300; i++) {//Epay系统默认最大3万条记录
            System.out.println("for循环开始 i=" + i);
            List<Supplier> pageSupplierList = new ArrayList<Supplier>();
            //
            List<Shop> pageShopList = new ArrayList<Shop>();
            //
            first = i * pageSize;
            last = (i + 1) * pageSize;
            Map<String, Object> result = queryShop(first, last);//默认查询Epay系统所有商户门店，不按相关条件过滤
            for (HashMap<String, Object> map : ((ArrayList<HashMap<String, Object>>) result.get("rows"))) {
                Supplier supplier = new Supplier();
                supplier.setCode((String) map.get("supplierCode"));
                supplier.setName((String) map.get("supplierName"));
                supplier.setShortName((String) map.get("supplierNameShort"));

                Address address = new Address();
                address.setProvince((String) map.get("province"));
                address.setCity((String) map.get("city"));
                address.setArea((String) map.get("area"));
                address.setSpecifics((String) map.get("address"));
                address.setContact((String) map.get("contact"));
                address.setMobile((String) map.get("mobile"));
                address.setPhone((String) map.get("phone"));
                address.setLatitude((String) map.get("latitude"));
                address.setLongitude((String) map.get("longitude"));

                Shop shop = new Shop();
                shop.setCode((String) map.get("code"));
                shop.setName((String) map.get("name"));
                shop.setShortName((String) map.get("nameShort"));
                shop.setStatus((String) map.get("status"));
                shop.setTerminalCodes((String) map.get("terminalCode"));//以逗号分隔
                shop.setAddress(address);
                shop.setSupplier(supplier);
                if (!pageSupplierList.contains(supplier)) {
                    pageSupplierList.add(supplier);
                }
                if (!pageShopList.contains(shop)) {
                    pageShopList.add(shop);
                }
            }
            List<Supplier> existSupplierList = supplierShopService.getAllSupplierList();
            List<Shop> existShopList = supplierShopService.getAllShopList();
            List<Terminal> pageTerminalList = new ArrayList<Terminal>();
            for (Shop tempShop : pageShopList) {//更新终端号
                String terminalCodes = tempShop.getTerminalCodes();
                for (String terminalCode : terminalCodes.split(",")) {
                    Terminal terminal = new Terminal();
                    terminal.setShop(tempShop);
                    terminal.setCode(terminalCode);
                    pageTerminalList.add(terminal);
                }
            }
            List<Terminal> existTerminalList = supplierShopService.getAllTerminalList();

            for (Supplier supplier : pageSupplierList) {
                if (existSupplierList.contains(supplier)) {
                    supplierShopService.updateSupplier(supplier);
                } else {
                    supplierShopService.createSupplier(supplier);
                }
            }
            for (Shop shop : pageShopList) {
                if (existShopList.contains(shop)) {
                    supplierShopService.updateShop(shop);
                } else {
                    supplierShopService.createShop(shop);
                }
            }
            for (Terminal terminal : pageTerminalList) {
                if (existTerminalList.contains(terminal)) {
                    supplierShopService.updateTerminal(terminal);
                } else {
                    supplierShopService.createTerminal(terminal);
                }
            }
        }
    }

    /**
     * 获取门店列表
     */
    @Test
    public void getShopList() {
        String businessType = "";
        String goodsType = "";
        String supplierCode = "";
        String supplierName = "百胜";
        String supplierShortName = "";
        String shopCode = "";
        String shopName = "";
        String shopShortName = "";
        String terminalCode = "";
        String province = "";
        String city = "";
        String area = "";
        String specifics = "";
        String status = "";
        Integer first = 0;
        Integer last = 20;

        List<QueryShopVO> list = supplierShopService.getShopList(businessType, goodsType, supplierCode, supplierName, supplierShortName, shopCode, shopName, shopShortName, terminalCode, province,
                city, area, specifics, status, first, last);
        if (list == null || list.isEmpty()) {
            list = new ArrayList<QueryShopVO>();
        }
        QueryShopResult result = new QueryShopResult();
        result.setRows(list);
        result.setTotal(list.size());
        Gson gson = new Gson();
        System.out.println(gson.toJson(result));
    }

    /**
     * 默认查询Epay系统所有商户门店，不按相关条件过滤
     * @param first
     * @param last
     * @return
     */
    public static Map<String, Object> queryShop(Integer first, Integer last) {
        String terminalCode = null;// 终端号
        String supplierCode = "";
        String supplierFullName = "";
        String supplierShortName = null;
        String shopCode = "";// 门店代码
        String shopFullName = null;
        String shopShortName = null;
        String province = null;
        String city = "";
        String area = "";
        String address = null;// 地址
        String status = "1";//在用门店

        Map<String, Object> map = new HashMap<>();
        map.put("first", first);// 起始行
        map.put("last", last);// 结束行
        map.put("supplierCode", supplierCode);// 商户代码
        map.put("supplierName", trim(supplierFullName));// 商户名称
        map.put("supplierNameShort", trim(supplierShortName));// 商户简称
        map.put("code", shopCode);// 门店代码
        map.put("name", trim(shopFullName));// 门店名称
        map.put("nameShort", trim(shopShortName));// 门店简称
        map.put("provinceName", trim(province));// 省份
        map.put("cityName", trim(city));// 市
        map.put("areaName", trim(area));// 区
        map.put("address", trim(address));// 地址
        map.put("terminalCode", terminalCode);// 终端号
        map.put("status", status);// 状态1：正常 0：删除
        Map<String, Object> result = null;
        try {
            result = analysisJson(requestEpay(new JSONObject(map).toString()));
        } catch (JSONException e) {
            System.out.println("请求epayService:findShopByPage后解析json数据失败");
        }
        return result;
    }

    public static String trim(String str) {
        if (str == null) {
            return str;
        } else {
            return str.trim();
        }
    }

    // 提交数据到epay
    private static String requestEpay(String condition) {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        String EPAY_SERVICE = propertiesUtil.getProperty("epay.shop.url");
        client.useService(EPAY_SERVICE);
        //client.useService("http://172.168.101.43:8888/epayHproseServer");//生产环境

        Map<String, String> map = new HashMap<>();
        map.put("methodName", "findShopByPage");
        map.put("sign", condition);
        map.put("msg", condition);
        map.put("appNo", "10006");

        String result = null;
        try {
            System.out.println("提交epayService:findShopByPage的数据：" + new JSONObject(map).toString());
            result = (String) client.invoke("getMethodForHprose", new Object[] { new JSONObject(map).toString() });
            System.out.println("返回epayService:findShopByPage的数据:" + result);
        } catch (IOException e) {
            System.out.println("==============》请求epay接口出错");
        }

        return result;
    }

    // 解析json数据返回客户端需要的数据
    private static Map<String, Object> analysisJson(String result) throws JSONException {
        if (result == null || "".equals(result)) { return null; }
        JSONObject jsonObject = new JSONObject(result);
        String respCode = jsonObject.getString("respCode");
        // respCode不为0时返空
        if (!"0".equals(respCode)) { return null; }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, String>> dataList = new ArrayList<>();

        JSONArray dataArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject o = dataArray.getJSONObject(i);
            Map<String, String> map = new HashMap<>();
            map.put("code", o.getString("code"));
            map.put("name", o.getString("name"));
            map.put("nameShort", o.getString("nameShort"));
            map.put("status", o.getString("status"));
            map.put("province", o.getString("provinceName"));
            map.put("city", o.getString("cityName"));
            map.put("area", o.getString("areaName"));
            map.put("address", o.getString("address"));
            map.put("contact", o.getString("contact"));
            map.put("mobile", o.getString("mobile"));
            map.put("longitude", o.getString("longitude"));
            map.put("latitude", o.getString("latitude"));

            map.put("supplierCode", o.getString("supplierCode"));
            map.put("supplierName", o.getString("supplierName"));
            map.put("supplierNameShort", o.getString("supplierNameShort"));
            map.put("terminalCode", o.getString("terminalCode"));
            dataList.add(map);
        }
        //int size = jsonObject.getIntValue("size");// 此句暂时无法使用
        resultMap.put("total", jsonObject.getInt("size"));
        resultMap.put("rows", dataList);
        System.out.println("解析epay返回epayService:findShopByPage的数据为：" + resultMap);
        return resultMap;
    }

    //    @SuppressWarnings({ "rawtypes", "unchecked" })
    //    private static void entityCompare(List pageEntityList, List existEntityList, List addList, List updateList, List removeList) {
    //        if (pageEntityList == null || pageEntityList.isEmpty()) {
    //            if (existEntityList != null && !existEntityList.isEmpty()) {
    //                for (Object existEntity : existEntityList) {
    //                    removeList.add(existEntity);
    //                }
    //            }
    //        } else if (existEntityList == null || existEntityList.isEmpty()) {
    //            if (pageEntityList != null && !pageEntityList.isEmpty()) {
    //                for (Object pageEntity : pageEntityList) {
    //                    addList.add(pageEntity);
    //                }
    //            }
    //        } else {
    //            for (Object existEntity : existEntityList) {//服务端数据库已存在实体对象
    //                for (Object pageEntity : pageEntityList) {//客户端页面传入的实体对象
    //                    if (!existEntityList.contains(pageEntity) && !addList.contains(pageEntity)) {//需要重写对应实体对象的equals()方法，根据实际唯一属性相等进行重写；如：id或code
    //                        addList.add(pageEntity);
    //                    } else if (existEntityList.contains(pageEntity) && !updateList.contains(pageEntity)) {
    //                        updateList.add(pageEntity);
    //                    }
    //
    //                    if (!pageEntityList.contains(existEntity) && !removeList.contains(existEntity)) {//需要重写对应实体对象的equals()方法，根据实际唯一属性相等进行重写；如：id或code
    //                        removeList.add(existEntity);
    //                    } else if (pageEntityList.contains(existEntity) && !updateList.contains(existEntity)) {
    //                        updateList.add(pageEntity);
    //                    }
    //                }
    //            }
    //        }
    //    }

    //    /**
    //     * 创建门店
    //     */
    //    @Test
    //    public void createSupplierShop() {
    //        List<Supplier> addSupplierList = new ArrayList<Supplier>();
    //        List<Supplier> updateSupplierList = new ArrayList<Supplier>();
    //        List<Supplier> removeSupplierList = new ArrayList<Supplier>();
    //        List<Supplier> pageSupplierList = new ArrayList<Supplier>();
    //        Supplier supplier = new Supplier();
    //        supplier.setCode("0122669917");
    //        supplier.setName("百胜餐饮（广东）有限公司");
    //        supplier.setShortName("肯德基");
    //        supplier.setStatus("1");
    //        supplier.setCreatedDate(new Date());
    //        supplier.setCreatedIp("127.0.0.1");
    //        supplier.setCreater("admin");
    //        supplier.setBusinessType("餐饮类");
    //        supplier.setGoodsType("非油品");
    //        pageSupplierList.add(supplier);
    //        List<Supplier> existSupplierList = supplierShopService.getAllSupplierList();
    //        System.out.println("existSupplierList = " + existSupplierList.size());
    //        entityCompare(pageSupplierList, existSupplierList, addSupplierList, updateSupplierList, removeSupplierList);
    //        System.out.println("addSupplierList = " + addSupplierList.size());
    //        System.out.println("updateSupplierList = " + updateSupplierList.size());
    //        System.out.println("removeSupplierList = " + removeSupplierList.size());
    //        List<Shop> addShopList = new ArrayList<Shop>();
    //        List<Shop> updateShopList = new ArrayList<Shop>();
    //        List<Shop> removeShopList = new ArrayList<Shop>();
    //        List<Shop> pageShopList = new ArrayList<Shop>();
    //        Address address = new Address();
    //        address.setProvince("广东");
    //        address.setCity("广州");
    //        address.setArea("天河");
    //        address.setSpecifics("龙口中路233号");
    //        address.setMobile("15989248048");
    //        Shop shop = new Shop();
    //        shop.setAddress(address);//增加地址
    //        shop.setSupplier(supplier);//增加商户
    //        shop.setCode("44015502");
    //        shop.setCreatedDate(new Date());
    //        shop.setCreatedIp("127.0.0.1");
    //        shop.setCreater("admin");
    //        shop.setName("百胜餐饮（广东）有限公司天河店");
    //        shop.setShortName("百胜餐饮天河店");
    //        shop.setStatus("1");
    //        shop.setTerminalCodes("11225503,11225504");
    //        pageShopList.add(shop);
    //        List<Shop> existShopList = supplierShopService.getAllShopList();
    //        System.out.println("existShopList = " + existShopList.size());
    //        entityCompare(pageShopList, existShopList, addShopList, updateShopList, removeShopList);
    //        System.out.println("addShopList = " + addShopList.size());
    //        System.out.println("updateShopList = " + updateShopList.size());
    //        System.out.println("removeShopList = " + removeShopList.size());
    //        for (Supplier tempSupplier : addSupplierList) {
    //            supplierShopService.createSupplier(tempSupplier);
    //        }
    //        for (Supplier tempSupplier : updateSupplierList) {
    //            supplierShopService.updateSupplier(tempSupplier);
    //        }
    //        for (Supplier tempSupplier : removeSupplierList) {
    //            tempSupplier.setDeleted(1);//逻辑删除
    //            supplierShopService.updateSupplier(tempSupplier);
    //        }
    //        for (Shop tempShop : addShopList) {
    //            supplierShopService.createShop(tempShop);
    //        }
    //        for (Shop tempShop : updateShopList) {
    //            supplierShopService.updateShop(tempShop);
    //        }
    //        for (Shop tempShop : removeShopList) {
    //            tempShop.setDeleted(1);//逻辑删除
    //            supplierShopService.updateShop(tempShop);
    //        }
    //        List<Terminal> addTerminalList = new ArrayList<Terminal>();
    //        List<Terminal> updateTerminalList = new ArrayList<Terminal>();
    //        List<Terminal> removeTerminalList = new ArrayList<Terminal>();
    //        List<Terminal> pageTerminalList = new ArrayList<Terminal>();
    //        for (Shop tempShop : pageShopList) {//更新终端号
    //            String terminalCodes = tempShop.getTerminalCodes();
    //            for (String terminalCode : terminalCodes.split(",")) {
    //                Terminal terminal = new Terminal();
    //                terminal.setShop(tempShop);
    //                terminal.setCode(terminalCode);
    //                pageTerminalList.add(terminal);
    //            }
    //        }
    //        List<Terminal> existTerminalList = supplierShopService.getAllTerminalList();
    //        System.out.println("existTerminalList = " + existTerminalList.size());
    //        entityCompare(pageTerminalList, existTerminalList, addTerminalList, updateTerminalList, removeTerminalList);
    //        System.out.println("addTerminalList = " + addTerminalList.size());
    //        System.out.println("updateTerminalList = " + updateTerminalList.size());
    //        System.out.println("removeTerminalList = " + removeTerminalList.size());
    //        for (Terminal tempTerminal : addTerminalList) {
    //            supplierShopService.createTerminal(tempTerminal);
    //        }
    //        for (Terminal tempTerminal : updateTerminalList) {
    //            supplierShopService.updateTerminal(tempTerminal);
    //        }
    //        for (Terminal tempTerminal : removeTerminalList) {
    //            tempTerminal.setDeleted(1);//逻辑删除
    //            supplierShopService.updateTerminal(tempTerminal);
    //        }
    //    }

}
