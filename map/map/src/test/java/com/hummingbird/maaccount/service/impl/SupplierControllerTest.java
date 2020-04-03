package com.hummingbird.maaccount.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class SupplierControllerTest {

    private CloseableHttpClient http;

    private String              QueryShopListByEpayURL = "http://localhost:8080/if/supplierShop/queryShopListByEpay";


    @Before
    public void init() {
        http = new DefaultHttpClient();
    }

    @Test
    public void testQueryShopListByEpay() throws Exception {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        //模拟表单提交查询需要的必要数据
        String page = "1";
        String rows = "10";
        String supplierCode = "0301000084";
        formparams.add(new BasicNameValuePair("page", page));
        formparams.add(new BasicNameValuePair("rows", rows));
        formparams.add(new BasicNameValuePair("supplierFullName", ""));
        formparams.add(new BasicNameValuePair("supplierShortName", ""));
        formparams.add(new BasicNameValuePair("supplierCode", supplierCode));
        formparams.add(new BasicNameValuePair("shopFullName", ""));
        formparams.add(new BasicNameValuePair("shopShortName", ""));
        formparams.add(new BasicNameValuePair("status", ""));
        formparams.add(new BasicNameValuePair("province", ""));
        formparams.add(new BasicNameValuePair("city", ""));
        formparams.add(new BasicNameValuePair("area", ""));

        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, Charset.defaultCharset());
        HttpPost post = new HttpPost(QueryShopListByEpayURL);
        post.setEntity(uefEntity);
        HttpResponse res = http.execute(post);
        String json = EntityUtils.toString(res.getEntity(), "UTF-8");
        System.out.println(json);
    }
}
