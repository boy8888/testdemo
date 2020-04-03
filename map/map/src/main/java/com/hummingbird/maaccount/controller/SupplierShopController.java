package com.hummingbird.maaccount.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.ext.AccessRequered;
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
 *         <td>description:商户门店终端号业务控制器</td>
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
@Controller
@RequestMapping("/supplierShop")
public class SupplierShopController extends BaseController {

    private static final Log    log    = LogFactory.getLog(SupplierShopController.class);

    private Gson                gson   = new Gson();

    private HproseHttpClient    client = new HproseHttpClient();

    @Autowired
    private SupplierShopService supplierShopService;


    @RequestMapping("/queryShopListByEpay")
    @AccessRequered(methodName = "查询Epay系统之商户门店")
    @ResponseBody
    public Map<String, Object> queryShopListByEpay(@RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("supplierFullName") String supplierFullName,
            @RequestParam("supplierShortName") String supplierShortName, @RequestParam("supplierCode") String supplierCode, @RequestParam("shopFullName") String shopFullName,
            @RequestParam("shopShortName") String shopShortName, @RequestParam("status") String status, @RequestParam("province") String province, @RequestParam("city") String city,
            @RequestParam("area") String area) {
        int first = 0, last = 0;
        if (page == 1) {
            first = 0;
        } else {
            first = (page - 1) * rows + 1;
        }
        last = page * rows;

        String shopCode = "";// 门店代码
        String address = "";// 地址
        String terminalCode = "";// 终端号

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
            log.error("请求epayService:findShopByPage后解析json数据失败", e);
        }
        return result;
    }

    /*@RequestMapping("/createSupplierShop")     modify 2016-06-08营销平台不做此数据同步，此数据抽出单独应用做同步
    @AccessRequered(methodName = "创建商户门店")
    @ResponseBody
    public String createSupplierShop() {
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

        return "success";
    }*/

    @RequestMapping(value = "/getShopList", produces = "text/html;charset=UTF-8")
    @AccessRequered(methodName = "获取门店列表")
    @ResponseBody
    public String getShopList(@RequestBody QueryShopVO queryShopVO) {
        log.info("app_chezhu请求值：" + gson.toJson(queryShopVO));
        List<QueryShopVO> list = supplierShopService.getShopList(queryShopVO.getBusinessType(), queryShopVO.getGoodsType(), queryShopVO.getSupplierCode(), queryShopVO.getSupplierName(),
                queryShopVO.getSupplierShortName(), queryShopVO.getCode(), queryShopVO.getName(), queryShopVO.getShortName(), queryShopVO.getTerminalCodes(), queryShopVO.getProvince(),
                queryShopVO.getCity(), queryShopVO.getArea(), queryShopVO.getSpecifics(), queryShopVO.getStatus(), queryShopVO.getFirst(), queryShopVO.getLast());
        if (list == null || list.isEmpty()) {
            list = new ArrayList<QueryShopVO>();
        }
        QueryShopResult result = new QueryShopResult();
        result.setRows(list);
        result.setTotal(list.size());

        String json = gson.toJson(result);
        log.info("app_chezhu返回值：" + json);
        return json;
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

    // 由于时间关系，原有设计页面选择门店方式的需求变更为一次性全部同步；备注：下面这种是真正意义上的选择模式，而且对deleted状态有严格控制；只需要将下方supplier、shop对象的静态参数值变更为动态参数值(通过页面传递)即可    
    //    @RequestMapping("/createSupplierShop")
    //    @AccessRequered(methodName = "创建商户门店")
    //    @ResponseBody
    //    public String createSupplierShop() {
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
    //        entityCompare(pageSupplierList, existSupplierList, addSupplierList, updateSupplierList, removeSupplierList);
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
    //        entityCompare(pageShopList, existShopList, addShopList, updateShopList, removeShopList);
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
    //        entityCompare(pageTerminalList, existTerminalList, addTerminalList, updateTerminalList, removeTerminalList);
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
    //        return null;
    //    }
}
