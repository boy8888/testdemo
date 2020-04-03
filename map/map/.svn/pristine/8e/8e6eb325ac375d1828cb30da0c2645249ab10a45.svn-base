package com.hummingbird.maaccount.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.maaccount.entity.User;
import com.hummingbird.maaccount.service.impl.SmsSendService;
import com.hummingbird.maaccount.service.impl.UserService;

/**
 * @author
 *         <html>
 *         <table border="1" width="100%">
 *         <tr>
 *         <td>description:营销账户后台(portal)发送短信</td>
 *         <td></td>
 *         </tr>
 *         <tr>
 *         <td>mender: <a href='mailto:zyyceo@gmail.com'>Ecol</a></td>
 *         <td>updated: 2016年4月28日</td>
 *         </tr>
 *         </table>
 *         </html>
 * @version 1.0
 */
@Controller
@RequestMapping("/sendMessage")
public class SendMessageController extends BaseController {

    private static final Log    log   = LogFactory.getLog(SendMessageController.class);

    private Gson                gson  = new Gson();

    @Autowired
    protected SmsSendService    smsSender;

    @Autowired
    private UserService         userService;

    private static final String APPID = "yxzh_portal";                                 //营销账户后台


    @RequestMapping("/batchSendSms")
    @ResponseBody
    private void batchSendSms(HttpServletRequest request) {
        String json = "";
        try {
            json = RequestUtil.getRequestPostData(request);
        } catch (Exception e) {
            log.error(String.format("获取订单参数出错"), e);
        }
        log.info("后台批量发送用户短信开始...." + json);
        List<?> list = gson.fromJson(json, List.class);
        String content;
        for (Object obj : list) {
            if (obj instanceof com.google.gson.internal.LinkedTreeMap) {
                com.google.gson.internal.LinkedTreeMap map = (com.google.gson.internal.LinkedTreeMap) obj;
                User user = userService.getUserByMobile((String) map.get("mobile"));
                if (!user.getPassword().equals(user.getPasswordFirst())) {
                    log.error("第一次密码已被修改，不能发送短信通知用户！mobile=" + user.getMobilenum());
                    break;
                } else {
                    if ("SHOP".equals(map.get("userType"))) {
                        String username = (String) map.get("mobile");
                        if ("MOBILE".equals(user.getLoginType())) {
                            username = user.getMobilenum();
                        } else if ("EMAIL".equals(user.getLoginType())) {
                            username = user.getEmail();
                        } else if ("USERNAME".equals(user.getLoginType())) {
                            username = user.getUserName();
                        }
                        content = "您的“油我发起”抢单账户已开通，登陆账号：" + username + "，原始密码：" + user.getPasswordPlaintext() + "，请通过中经油马APP进行抢单和进入“我的”更改密码，详询4006630666。";
                    } else {
                        content = "您签约的洗车店抢单账户已经开通，登陆账号和密码已经发送给洗车店负责人，请您通知签约洗车店，可以通过中经油马APP进行抢单和进入“我的”更改密码，详询4006630666。";
                    }
                    try {
                        ResultModel resultModel = smsSender.send((String) map.get("mobile"), content, APPID, SmsSendService.ACTION_NAME_REGISTER);
                        boolean sendresult = resultModel != null && resultModel.isSuccessed();
                        if (log.isDebugEnabled()) {
                            if (sendresult) {
                                log.debug("发送短信成功");
                            } else {
                                log.debug("发送短信失败," + resultModel != null ? resultModel.getErrmsg() : "短信访问出错");
                            }
                        }
                    } catch (Exception e) {
                        log.error("发送短信异常,手机号" + map.get("mobile"));
                    }
                }
            }
        }
    }

}
