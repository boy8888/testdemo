package com.hummingbird.maaccount.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hummingbird.maaccount.entity.Supplier;
import com.hummingbird.maaccount.service.UserOrgService;
import com.hummingbird.maaccount.vo.QueryUserOrgVO;

public class UserOrgServiceImplTest {

    private UserOrgService userOrgService;


    @Before
    public void init() {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        userOrgService = ac.getBean(UserOrgService.class);
    }

    @Test
    public void testBindUserOrg() {
        List<String> orgCodes = new ArrayList<String>();
        orgCodes.add("44015501");
        orgCodes.add("44015502");
        orgCodes.add("44015503");
        userOrgService.bindUserOrg(18499, "SHOP", orgCodes);

    }

    @Test
    public void testQueryUserOrgByUserId() {
        QueryUserOrgVO queryUserOrgVO = userOrgService.queryUserOrgByUserId(18499);
        System.out.println(queryUserOrgVO.getOrgType() + "," + queryUserOrgVO.getOrgCodes());
    }

    @Test
    public void testQuery() {
        List<String> orgCodes = new ArrayList<String>();
        orgCodes.add("1");
        orgCodes.add("1");
        orgCodes.add("2");
        orgCodes.add("5");
        orgCodes.add("6");
        orgCodes.add("5");
        orgCodes.add("7");
        if (orgCodes == null || orgCodes.size() == 0) {
            System.out.println("null......");
        }
        List<String> results = new ArrayList<String>(new HashSet<String>(orgCodes));
        for (String str : results) {
            System.out.println("str = " + str);
        }
    }

    @Test
    public void testQuery2() {
        List<Supplier> suppliers = new ArrayList<Supplier>();
        Supplier s1 = new Supplier();
        s1.setCode("0122669916");
        s1.setName("百胜餐饮（广东）有限公司");
        s1.setShortName("肯德基");
        Supplier s2 = new Supplier();
        s2.setCode("0122669916");
        s2.setName("百胜餐饮（广东）有限公司22");
        s2.setShortName("肯德基22");
        Supplier s3 = new Supplier();
        s3.setCode("0122669917");
        s3.setName("百胜餐饮（广东）有限公司33");
        s3.setShortName("肯德基33");
        Supplier s4 = new Supplier();
        s4.setCode("0122669918");
        s4.setName("百胜餐饮（广东）有限公司44");
        s4.setShortName("肯德基44");
        suppliers.add(s1);
        suppliers.add(s2);
        suppliers.add(s3);
        suppliers.add(s4);
        if (suppliers == null || suppliers.size() == 0) {
            System.out.println("null......");
        }
        List<Supplier> results = new ArrayList<Supplier>(new HashSet<Supplier>(suppliers));
        for (Supplier supplier : results) {
            System.out.println("supplier = " + supplier.getCode() + "," + supplier.getName());
        }
    }

}
