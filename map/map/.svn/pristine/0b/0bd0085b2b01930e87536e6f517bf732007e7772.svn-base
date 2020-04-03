package com.hummingbird.maaccount.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.json.JSONArray;
import com.hummingbird.common.util.json.JSONException;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.maaccount.entity.Address;
import com.hummingbird.maaccount.entity.Shop;
import com.hummingbird.maaccount.entity.Supplier;

import hprose.client.HproseHttpClient;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:测试门店查询</td>
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
public class QueryShopByEpayTest {

    private static HproseHttpClient client = new HproseHttpClient();


    public static void main(String[] args) {
        Map<String, Object> result = queryShop(0, 100);
        System.out.println("result = " + result.get("rows"));
        //        Iterator iter = ((Map)result.get("rows")).entrySet().iterator();
        //        while (iter.hasNext()) {
        //            Map.Entry entry = (Map.Entry) iter.next();
        //            Object key = entry.getKey();
        //            Object val = entry.getValue();
        //            System.out.println("key = " + key);
        //            System.out.println("val = " + val);
        //        }
        List<Supplier> pageSupplierList = new ArrayList<Supplier>();
        List<Shop> pageShopList = new ArrayList<Shop>();

        for (HashMap<String, Object> map : ((ArrayList<HashMap<String, Object>>) result.get("rows"))) {
            System.out.println("obj = " + map);

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
            if (!pageSupplierList.contains(supplier)) {
                pageSupplierList.add(supplier);
            }
            if (!pageShopList.contains(shop)) {
                pageShopList.add(shop);
            }
        }

        System.out.println("pageSupplierList size = " + pageSupplierList.size());
        System.out.println("pageShopList size = " + pageShopList.size());

        for (Supplier supplier : pageSupplierList) {
            System.out.println(supplier.getCode());
        }
        Gson gson = new Gson();
        for (Shop shop : pageShopList) {
            System.out.println(gson.toJson(shop));
        }

    }

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
            map.put("posStatus", o.getString("posStatus"));
            dataList.add(map);
        }
        //int size = jsonObject.getIntValue("size");// 此句暂时无法使用
        resultMap.put("total", jsonObject.getInt("size"));
        resultMap.put("rows", dataList);
        System.out.println("解析epay返回epayService:findShopByPage的数据为：" + resultMap);
        return resultMap;
    }
}
