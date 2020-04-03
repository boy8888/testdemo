package com.hummingbird.maaccount.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.entity.UserOrg;
import com.hummingbird.maaccount.mapper.UserMapper;
import com.hummingbird.maaccount.mapper.UserOrgMapper;
import com.hummingbird.maaccount.service.UserOrgService;
import com.hummingbird.maaccount.vo.QueryUserOrgVO;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:用户组织机构接口实现类</td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年4月1日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
@Service
public class UserOrgServiceImpl implements UserOrgService {

    @Autowired
    UserMapper    userDao;

    @Autowired
    UserOrgMapper userOrgDao;


    /**
     * 绑定用户和组织
     * 一个用户只能绑定一个组织机构类型下面的多个组织机构对象；不能绑定多个组织机构类型下面的多个组织机构对象
     * @param userId 用户主键ID
     * @param orgType 组织类型；商户、门店、企业客户等
     * @param orgCodes 组织编码；赌赢组织类型的所在对象编码
     * @return
     */
    @Override
    public boolean bindUserOrg(Serializable userId, String orgType, List<String> orgCodes) {
        User user = userDao.selectByPrimaryKey((Integer) userId);
        if (user == null) { return false; }
        orgCodes = distinct(orgCodes);
        //获取数据库中所有用户对应的数据
        List<UserOrg> userOrgs = userOrgDao.selectByUserAndOrgType(user.getUserId(), orgType);
        //获取数据库中存在而传入的orgCodes不存在的数据（用于删除）。
        List<Long> neetToDeleteIds = getRedundanceId(userOrgs, orgCodes);
        //获取数据库中不存在而传入的orgCodes中有的数据(用于插入)
        List<String> neetToInsertCode = getNeetToInsertOrgCode(userOrgs, orgCodes);
        //创建批量插入数据中没有的数据对象
        List<UserOrg> userOrgList = createUserOrgList(user, neetToInsertCode, orgType);
        //批量插入数据中
        if (userOrgList != null && userOrgList.size() > 0) {
            userOrgDao.insertUserOrgList(userOrgList);
        }
        //批量删除
        if (neetToDeleteIds != null && neetToDeleteIds.size() > 0) {
            userOrgDao.deleteByPrimaryKeys(neetToDeleteIds);
        }
        return true;
    }

    /**
     * 根据用户主键ID查询用户可访问的组织机构代码
     * @param userId 用户主键ID
     * @return
     */
    @Override
    public QueryUserOrgVO queryUserOrgByUserId(Serializable userId) {
        User user = userDao.selectByPrimaryKey((Integer) userId);
        if (user == null) { return null; }
        List<UserOrg> userOrgs = userOrgDao.selectByUserAndOrgType(user.getUserId(), user.getOrgType());
        List<String> orgCodes = createOrgCodes(userOrgs);
        return createQueryUserOrgVO(user, orgCodes);
    }

    /**
     * 创建orgCode字符串的列表集合
     * @param userOrgs
     * @return
     */
    private List<String> createOrgCodes(List<UserOrg> userOrgs) {
        if (userOrgs == null || userOrgs.size() == 0) { return null; }
        List<String> orgCodes = new ArrayList<String>();
        for (UserOrg userOrg : userOrgs) {
            orgCodes.add(userOrg.getOrgCode());
        }
        return orgCodes;
    }

    /**
     * 创建QueryUserOrgVO对象
     * @param user
     * @param orgCodes
     * @return
     */
    private QueryUserOrgVO createQueryUserOrgVO(User user, List<String> orgCodes) {
        if (user == null) { return null; }
        QueryUserOrgVO queryUserOrgVO = new QueryUserOrgVO();
        queryUserOrgVO.setUser(user);
        queryUserOrgVO.setOrgType(user.getOrgType());
        queryUserOrgVO.setOrgCodes(orgCodes);
        return queryUserOrgVO;
    }

    /**
     * 创建需要批量插入的对象
     * @param user 指定的用户
     * @param neetToInsertOrgCodes 需要插入的orgCode列表
     * @param orgType 商户类型
     * @return
     */
    private List<UserOrg> createUserOrgList(User user, List<String> neetToInsertOrgCodes, String orgType) {
        List<UserOrg> userOrgList = new ArrayList<UserOrg>();
        if (neetToInsertOrgCodes != null && neetToInsertOrgCodes.size() > 0) {
            for (String orgCode : neetToInsertOrgCodes) {
                UserOrg userOrg = new UserOrg(null, user, orgType, orgCode);
                userOrgList.add(userOrg);
            }
        }
        return userOrgList;
    }

    /**
     * 获取数据库多余数据的ID列表
     * @param userOrgs 数据库中已经存在的数据
     * @param orgCodes 传入的需要绑定的数据
     * @return
     */
    private List<Long> getRedundanceId(List<UserOrg> userOrgs, List<String> orgCodes) {
        //如果客户端传入的数据为空则会返回数据库中所有的数据的ID
        if (orgCodes == null || orgCodes.size() == 0) {
            orgCodes = Collections.emptyList();
        }
        List<Long> ids = new ArrayList<Long>();
        if (userOrgs != null && userOrgs.size() > 0) {
            for (UserOrg userOrg : userOrgs) {
                if (!orgCodes.contains(userOrg.getOrgCode())) {
                    ids.add(userOrg.getId());
                }
            }
        }
        return ids;
    }

    /**
     * 获取需要插入数据库的orgCode列表
     * @param userOrgs 数据库中已经存在的数据
     * @param orgCodes 客户端传入的orgCode
     * @return
     */
    private List<String> getNeetToInsertOrgCode(List<UserOrg> userOrgs, List<String> orgCodes) {
        //获取数据库存在的所有orgCode的集合,如果数据库中没有任何数据则直接返回
        List<String> databaseOrgcodes = createOrgCodes(userOrgs);
        if (databaseOrgcodes == null || databaseOrgcodes.size() == 0) { return orgCodes; }
        List<String> result = new ArrayList<String>();
        if (orgCodes != null && orgCodes.size() > 0) {
            for (String orgCode : orgCodes) {
                if (!databaseOrgcodes.contains(orgCode)) {
                    result.add(orgCode);
                }
            }
        }
        return result;
    }

    //去重
    private List<String> distinct(List<String> orgCodes) {
        if (orgCodes == null || orgCodes.size() == 0) { return orgCodes; }
        return new ArrayList<String>(new HashSet<String>(orgCodes));
    }

}
