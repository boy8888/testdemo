package com.hummingbird.maaccount.schedule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.common.util.PropertiesUtil;
import com.hummingbird.common.util.json.JSONArray;
import com.hummingbird.common.util.json.JSONException;
import com.hummingbird.common.util.json.JSONObject;
import com.hummingbird.maaccount.entity.Address;
import com.hummingbird.maaccount.entity.Shop;
import com.hummingbird.maaccount.entity.Supplier;
import com.hummingbird.maaccount.entity.Terminal;
import com.hummingbird.maaccount.service.SupplierShopService;

import hprose.client.HproseHttpClient;
/**
 * 该类用于定时向epay系统获取门店商户信息并保存于本地数据库
 * @author 
 * <html><table border="1" width="100%">
 * <tr><td>description:           </td><td></td></tr>
 * <tr><td>mender: <a href='mailto:443513088@qq.com'>潘裕江</a></td><td>updated: 2016年4月21日</td></tr>
 * </table></html>
 * @version 1.0
 */
//@Service   modify 2016-06-08营销平台不做此数据同步，此数据抽出单独应用做同步
public class CreateSupplierShopScheduler {
    @Autowired
    private SupplierShopService supplierShopService;
    
    private HproseHttpClient    client = new HproseHttpClient();
    
    private static final Log    log    = LogFactory.getLog(CreateSupplierShopScheduler.class);
    
    
    public void createSupplierShop() {
        long startTime=System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("到epay系统同步商户门店信息任务开始。。。。。。开始时间为："+sdf.format(new Date()));
        Integer pageSize = 100;
        Integer first = 0;
        Integer last = 0;
        for (int i = 0; i < 300; i++) {//Epay系统默认最大3万条记录
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
        log.info("到epay系统同步商户门店信息任务完成。。。。。。结束时间为："+sdf.format(new Date()));
        log.info("任务耗时："+(System.currentTimeMillis()-startTime)/1000L+"秒");
    }
    
    /**
     * 默认查询Epay系统所有商户门店，不按相关条件过滤
     * @param first
     * @param last
     * @return
     */
    public Map<String, Object> queryShop(Integer first, Integer last) {
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
        String status = "1";

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
    
    /**
     * 字符串过滤
     */
    private String trim(String str) {
        if (str == null) {
            return str;
        } else {
            return str.trim();
        }
    }
    
    public void test(){
        System.out.println("_______________________________");
    }

    // 提交数据到epay
    private String requestEpay(String condition) {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        String EPAY_SERVICE = propertiesUtil.getProperty("epay.shop.url");
        client.useService(EPAY_SERVICE);

        Map<String, String> map = new HashMap<>();
        map.put("methodName", "findShopByPage");
        map.put("sign", condition);
        map.put("msg", condition);
        map.put("appNo", "10006");

        String result = null;
        try {
            log.info("提交epayService:findShopByPage的数据：" + new JSONObject(map).toString());
            result = (String) client.invoke("getMethodForHprose", new Object[] { new JSONObject(map).toString() });
            log.info("返回epayService:findShopByPage的数据:" + result);
        } catch (IOException e) {
            log.error("==============》请求epay接口出错", e);
        }

        return result;
    }

    // 解析json数据返回客户端需要的数据
    private Map<String, Object> analysisJson(String result) throws JSONException {
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
        log.info("解析epay返回epayService:findShopByPage的数据为：" + resultMap);
        return resultMap;
    }
}
