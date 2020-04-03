<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=path%>/js/jquery-1.11.1.js"></script>
	<style type="text/css">
	.menu li{
		display: inline-block;
	}
	.f-fl{
		float: left;
	}
	.f-fl li{
		margin-left: 10px;
	}
	</style>
  </head>
  <body>
  	<div>
	<ul>
	<li><label>服务器</label>
	<select name="host" id="host">
	<option value="http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath() %>" selected>当前服务器地址</option>
	</select><input type='txt' id='realhost' >
	<li><label>接口名</label>
	<textarea id='api' cols="60"></textarea>

	</li>
	<li><label>参数</label><textarea id="param" cols="100" rows="15"></textarea></li>
	<li><label>cookie</label><textarea id="cookie" cols="100" rows="2"></textarea></li>
	<li><input type="button" value="提交" id="bt"></li>
	<li><label>响应</label><div id="resp"></div></li>
	</ul>
	</div>
<table>
<tr><td colspan="6">投资帐户</td></tr>
<tr>
<td><input type="button" onclick='setbinding("/investmentAccount/transfer_to_cashAccount","{\"app\":{\"appId\":\"zjhtwallet\"}, \"order\":{\"mobileNum\":\"13912345678\",\"sum\":500000,\"productName\":\"投资国际原油\",\"remark\":\"从投资账户转账5000元到现金账户\", \"appOrderId\":\"AO201412122344888444\",\"accountCode\":\"104778\",\"paymentCodeMD5\":\"w344dioeorreeoocWRT\",\"peerAccountId\":\"peerAccountId\",\"peerAccountUnit\":\"建设银行\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}} ")' value="投资帐户转出至现金帐户" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/savings_by_user","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"b94490aecabf04eba1e9ab58dc76c115\"},\"tsig\":{\"orderMD5\":\"550ebb66030bd4ef8a4b7a43a4b2c5a1\",\"signature\":\"eae3e57a497203bdd676934d8cb0b156\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"},\"order\":{\"mobileNum\":\"18922260815\",\"sum\":3412,\"remark\":\"建设银行柜台充值34.12，时间2014年12月12日\",\"appOrderId\":\"AO20141212234483412\",\"externalOrderId\":\"1234567890123453412\",\"externalOrderTime\":\"2014-12-12 12:00:00\",\"peerAccountId\":\"6226881301233412\",\"peerAccountUnit\":\"建设银行\"}}")' value="线下充值接口" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/query_offlineRecharge","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"mobileNum\":\"18922260815\",\"startDate\":\"2014-01-01\",\"endDate\":\"2015-11-02\",\"pageSize\":10,\"pageIndex\":1}}")' value="线下充值查询接口" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/offlineRecharge_success","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"b94490aecabf04eba1e9ab58dc76c115\"},\"tsig\":{\"orderMD5\":\"550ebb66030bd4ef8a4b7a43a4b2c5a1\",\"signature\":\"OA\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"},\"order\":{\"orderId\":\"AO201412122344888444\",\"remark\":\"建设银行柜台充值34.12，时间2014年12月12日\",\"appOrderId\":\"AO20141212234483412\"}}")' value="线下充值成功接口" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/offlineRecharge_to_cancel","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"b94490aecabf04eba1e9ab58dc76c115\"},\"tsig\":{\"orderMD5\":\"550ebb66030bd4ef8a4b7a43a4b2c5a1\",\"signature\":\"jj\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"},\"order\":{\"orderId\":\"AO201412122344888444\",\"remark\":\"建设银行柜台充值34.12，时间2014年12月12日\",\"appOrderId\":\"AO20141212234483412\"}}")' value="线下充值撤销接口" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/query_withdrawalApplication","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"mobileNum\":\"18922260815\",\"startDate\":\"2014-01-01\",\"endDate\":\"2015-11-02\",\"pageSize\":10,\"pageIndex\":1}}")' value="投资帐户查询提现申请" ></td> 
</tr>
<tr>
<td><input type="button"  onclick='setbinding("/investmentAccount/withdraw_to_freeze","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"peerAccountId\":\"1234123412341234\",\"peerAccountUnit\":\"招商银行\",\"mobileNum\":\"18922260815\",\"sum\":50000,\"remark\":\"提现冻结5000元，会计：李娜\",\"appOrderId\":\"AO201412122344888444\",\"accountCode\":\"123456\",\"paymentCodeMD5\":\"123werq123wer\"},\"tsig\":{\"orderMD5\":\"50e9d6a05f8f2ce09dc47ab85bb9604c\",\"signature\":\"d038ebd3a523401d5fa0bc633401347d\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="投资帐户提现申请" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/withdraw_success","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"orderId\":\"udO201412122344888444\",\"remark\":\"提现解冻5000元成功，会计：李娜\",\"appOrderId\":\"AO201412122344888444\",\"externalOrderId\":\"bh12345678901234567890\", \"externalOrderTime\":\"2014-12-12 12:00:00\", \"peerAccountId\":\"234123412341234\", \"peerAccountUnit\":\"建设银行\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="投资帐户提现成功" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/withdraw_to_unfreeze","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"orderId\":\"udO201412122344888444\",\"remark\":\"提现冻结5000元，会计：李娜\",\"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="投资帐户撤销申请" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/payback_capital","{\"app\":{\"appId\":\"zjhtwallet\"}, \"order\":{\"mobileNum\":\"13912345678\",\"sum\":500000,\"productName\":\"有油贷5000元产品返本\",\"remark\":\"有油贷5000元产品返本\", \"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}} ")' value="投资帐户返本" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/payback_interest","{\"app\":{\"appId\":\"zjhtwallet\"}, \"order\":{\"mobileNum\":\"13912345678\",\"sum\":500000,\"productName\":\"有油贷5000元产品返息\",\"remark\":\"有油贷5000元产品返息\", \"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}} ")' value="投资帐户返息" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/pay_yyd","{\"app\":{\"appId\":\"zjhtwallet\"}, \"order\":{\"mobileNum\":\"18922260815\", \"orderId\":\"ud1234567890\",\"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}} ")' value="投资帐户支付有油贷" ></td> 
</tr>
<tr>
<td><input type="button"  onclick='setbinding("/investmentAccount/transfer_to_bankCard","{\"app\":{\"appId\":\"zjhtwallet\"}, \"order\":{\"mobileNum\":\"13912345678\",\"userToken\":\"qwerqerewtuuuu123wer\",\"sum\":500000,\"productName\":\"支付宝\",\"remark\":\"从投资账户转账5000元到银行卡（卡号6211 2345 5678 1234）\", \"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}} ")' value="投资帐户转出（到银行卡）" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/transfer_to_bankCard_undo","{\"app\":{\"appId\":\"zjhtwallet\"}, \"order\":{\"mobileNum\":\"13912345678\",\"userToken\":\"qwerqerewtuuuu123wer\",\"sum\":500000, \"orderId\":\"UD20141212123023WEAER2233\",\"productName\":\"\",\"remark\":\"从投资账户转账5000元到银行卡（卡号6211 2345 5678 1234）冲正\", \"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}} ")' value="投资账户转账到银行卡冲正" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/freeze_yyd","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"18922260815\",\"sum\":500000,\"productName\":\"有油贷5000元产品\",\"remark\":\"在有油贷网站够爱5000元有油贷产品\",\"appOrderId\":\"a_yyd_freeze\",\"accountCode\":\"123456\",\"paymentCodeMD5\":\"123werq123wer\"},\"tsig\":{\"orderMD5\":\"d9c1d00f31bef034967d20a36352fe20\",\"signature\":\"7d955c0a6bd536c7b0083ecafd1893f0\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="投资账户/冻结投标金额" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/unfreeze_yyd","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"18922260815\",\"orderId\":\"UD002015011921534600000531771430\",\"remark\":\"在有油贷网站解冻5000元有油贷产品\",\"appOrderId\":\"a_yyd_unfreeze\"},\"tsig\":{\"orderMD5\":\"565121e4667067193be5cfb85badf050\",\"signature\":\"ab41276b8c6a74f201250b67ce8098a1\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="投资账户/解冻投标金额" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/queryRemainingOrderListByUser","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"mobileNum\":\"18922260815\",\"startTime\":\"2014-01-01\",\"endTime\":\"2015-11-02\",\"type\":\"TOB\",\"flowDirection\":\"OUT\",\"peerAccountType\":\"TA#\" ,\"pageSize\":\"10\",\"pageIndex\":\"1\"}}")' value="投资账户查询余额订单记录" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/queryObjectOrderListByUser","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"mobileNum\":\"18922260815\",\"startTime\":\"2014-01-01\",\"endTime\":\"2015-11-02\" ,\"pageSize\":\"10\",\"pageIndex\":\"1\"}} ")' value="投资账户查询标的交易记录" ></td> 
</tr>
<tr>
<td><input type="button"  onclick='setbinding("/investmentAccount/query_recharge","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"mobileNum\":\"18922260815\",\"startDate\":\"2014-01-01\",\"endDate\":\"2015-11-02\",\"pageSize\":10,\"pageIndex\":1}}")' value="线上/线下充值查询接口" ></td>
<td><input type="button"  onclick='setbinding("/investmentAccount/recommended_income","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"18922260815\",\"sum\":500,\"remark\":\"投资收益5元\",\"appOrderId\":\"\"},\"tsig\":{\"orderMD5\":\"cea0afd5a37b2c1fe9bb6f97819625b5\",\"signature\":\"63622c310a3dc97e5aacab1a20078a66\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="推荐收益接口" ></td>
<td><input type="button"  onclick='setbinding("/bankCard/transfer_to_investmentAccount","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"18922260815\",\"sum\":500000,\"remark\":\"从银行卡转账5000元到投资账户\",\"appOrderId\":\"bankcard 2 investment\",\"peerAccountId\":\"1234123412341234\",\"peerAccountUnit\":\"招商银行\"},\"tsig\":{\"orderMD5\":\"cea0afd5a37b2c1fe9bb6f97819625b5\",\"signature\":\"63622c310a3dc97e5aacab1a20078a66\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="银行卡转账到投资账户" ></td> 
<td><input type="button"  onclick='setbinding("/investmentAccount/auto_freeze_yyd","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"18922260815\",\"sum\":500000,\"productName\":\"有油贷5000元产品\",\"remark\":\"在有油贷网站够爱5000元有油贷产品\",\"appOrderId\":\"a_yyd_freeze\"},\"tsig\":{\"orderMD5\":\"d9c1d00f31bef034967d20a36352fe20\",\"signature\":\"7d955c0a6bd536c7b0083ecafd1893f0\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="投资账户/自动投标" ></td> 
</tr>
<tr><td colspan="6">现金帐户和其它</td></tr>
<tr>
<td><input type="button"  onclick='setbinding("/cashAccount/pay_yyd","{\"app\":{\"appId\":\"zjhtwallet\"}, \"order\":{\"mobileNum\":\"13912345678\",\"userToken\":\"qwerqerewtuuuu123wer\",\"sum\":300000, \"productName\":\"有油贷3000元产品\",\"remark\":\"在有油贷网站够爱3000元有油贷产品\", \"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}} ")' value="现金支付有油贷产品" ></td> 
<td><input type="button"  onclick='setbinding("/cashAccount/transfer_to_ia","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"18922260815\",\"sum\":500000,\"remark\":\"从现金账户转账5000元到投资账户\",\"appOrderId\":\"AO201412122344888444\",\"accountCode\":\"231435\",\"paymentCodeMD5\":\"w344dioeorreeoocWRT\"},\"tsig\":{\"orderMD5\":\"9a6d43e811e857b7bdc1a35228a318f8\",\"signature\":\"5a1aea0c1fcf251355a5b60bf77784b1\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="现金帐户转账到投资账户" ></td> 
<td><input type="button"  onclick='setbinding("/cashAccount/queryOrderListByUser","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"mobileNum\":\"13912345678\",\"startDate\":\"2015-01-01\",\"endDate\":\"2015-01-02\" ,\"pageSize\":\"10\",\"pageIndex\":\"1\"}}")' value="查询现金帐户交易订单记录" ></td> 
<td><input type="button"  onclick='setbinding("/huitongCard/transfer_to_cashAccount","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"13912345678\",\"sum\":500000,\"remark\":\"从汇通卡转账5000元到现金账户（汇通卡号6211234556781234）\",\"appOrderId\":\"AO201412122344888444\",\"peerAccountId\":\"1234123412341234\", \"peerAccountUnit\":\"汇通宝\"},\"tsig\":{\"orderMD5\":\"1e8e8db3a734a59fed183173e1924f98\",\"signature\":\"478e9a37ac29ba2fd15f8003182fc377\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="汇通卡转至现金帐户" ></td> 
<td><input type="button"  onclick='setbinding("/bankCard/pay_yyd","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"13912345678\",\"sum\":500000,\"productName\":\"有油贷5000元产品\",\"remark\":\"在有油贷网站够爱5000元有油贷产品\",\"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"72b338f3ecbab1690306b08297393263\",\"signature\":\"AHoWdNRaf1k4ZrOlJX1LcX8act1FWF/VDi5dahgZB2kIUJFSne+9CC0g+8fNA/ftdoUa4G9gxzLGnU9slQxUE/TXBEBtc2Y5iqqSGi1T9mxK3Hqs1E3QOpEGvkOnApfq6+yqvz9v0EIC66ROSinfxYs1HfU8R43UAEKq5s8y78RqIbjFXhakzIMXU0KYXQSyGDj2lNKEC/AwdqZlKLyMc26Oan4Lb9ZiwLvhNzCtJkk13d+nQ2uUOB5lKS3qnzwtjAcYezZzQDd0SlUhzIn+ccpD6l+YGM/NUGAP6gqMjLl+uZhOmwYHEvR/UTmOhFAKeQPX53DGB6FluKiteUZtEA==\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="银行卡购买有油贷" ></td> 
<td><input type="button"  onclick='setbinding("/bankCard/transfer_to_cashAccount","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"18922260815\",\"sum\":500000,\"remark\":\"从银行卡转账5000元到现金账户\",\"appOrderId\":\"AO201412122344888444\",\"peerAccountId\":\"1234123412341234\",\"peerAccountUnit\":\"招商银行\"},\"tsig\":{\"orderMD5\":\"7d56cbc2a721d06bc26b918a86d30be9\",\"signature\":\"5646e7fe0f9a40d1bfccef3c97535df9\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="银行卡转账到现金账户" ></td> 
</tr>
<tr>
<td><input type="button"  onclick='setbinding("/bankCard/transfer_to_investmentAccount","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"18922260815\",\"sum\":500000,\"remark\":\"从银行卡转账5000元到投资账户\",\"appOrderId\":\"bankcard 2 investment\",\"peerAccountId\":\"1234123412341234\",\"peerAccountUnit\":\"招商银行\"},\"tsig\":{\"orderMD5\":\"cea0afd5a37b2c1fe9bb6f97819625b5\",\"signature\":\"63622c310a3dc97e5aacab1a20078a66\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="银行卡转账到投资账户" ></td> 
<td><input type="button"  onclick='setbinding("/my/queryUserAccountList","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"mobileNum\":\"13912345678\"}}")' value="查询帐户明细" ></td> 
<td><input type="button"  onclick='setbinding("/order/querySpendOrder","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"pageSize\":10,\"pageIndex\":1,\"mobileNum\":\"13912345678\",\"type\":[\"XF#\",\"CX#\",\"CZH\"],\"sellerIds\":[\"0201000270\",\"0201000271\"],\"startDate\":\"2015-01-01\",\"endDate\":\"2015-01-02\"    }}")' value="查询消费订单" ></td> 
</tr>
<tr><td colspan="6">鉴权部分</td></tr>
<tr>
<td><input type="button"   onclick='setbinding("/userAuth/get_smscode","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"mobileNum\":\"18922260815\"}")' value="发送验证码" ></td> 
<td><input type="button"   onclick='setbinding("/userAuth/login","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"login\":{\"mobileNum\":\"18922260815\",\"smsCode\":\"4567\"}}")' value="登录" ></td>
<td><input type="button"   onclick='setbinding("/userAuth/logina","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"login\":{\"mobileNum\":\"18922260815\",\"password\":\"1234\"}}")' value="登录密码登录" ></td>
<td><input type="button"   onclick='setbinding("/userAuth/register","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"login\":{\"mobileNum\":\"18922260815\",\"smsCode\":\"4567\",\"password\":\"1234\",\"paymentCodeMD5\":\"1234\"}}")' value="新用户注册" ></td>
<td><input type="button"   onclick='setbinding("/userAuth/resetPassword","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"login\":{\"mobileNum\":\"18922260815\",\"oldpassword\":\"4567\",\"newpassword\":\"1234\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"}}")' value="重置登录密码" ></td>
<td><input type="button"   onclick='setbinding("/userAuth/get_accountcode","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"b94490aecabf04eba1e9ab58dc76c115\"},\"mobileNum\":\"18922260815\"}")' value="获取账户验证码" ></td>
</tr>
<tr>
<td><input type="button"   onclick='setbinding("/userAuth/set_paymentcode","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"b94490aecabf04eba1e9ab58dc76c115\"},\"mobileNum\":\"18922260815\",\"accountCode\":\"123456\",\"paymentCodeMD5\":\"qwer1234567\"}")' value="设置支付密码" ></td>
<td><input type="button"   onclick='setbinding("/userAuth/genkey","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"order\":{\"mobileNum\":\"13912345678\",\"objectSum\":500000,\"productName\":\"有油贷5000元产品\",\"remark\":\"在有油贷网站够爱5000元有油贷产品\",\"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="生成key" ></td>
<td><input type="button"   onclick='setbinding4string("/userAuth/genParam","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"order\":{\"mobileNum\":\"13912345678\",\"objectSum\":500000,\"productName\":\"有油贷5000元产品\",\"remark\":\"在有油贷网站够爱5000元有油贷产品\",\"appOrderId\":\"AO201412122344888444\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="生成签名" ></td>
<td><input type="button"   onclick='setbinding("/sms/send","{\"user\":\"test\",\"password\":\"smst\",\"mobileNum\":\"18922260815\",\"content\":\"测试\"}")' value="短信发送" ></td>
<td><input type="button"   onclick='setbinding("/userAuth/verify_token_original","{\"appCode\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"MD5\",\"userToken\":{\"appCode\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"MD5\",\"token\":\"qpoiwreq99eekkeuurqwerq23ewrm\"}}")' value="验证用户token接口" ></td>
<td><input type="button"   onclick='setbinding("/userAuth/verify_token","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"MD5\"},\"userToken\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"MD5\",\"token\":\"qpoiwreq99eekkeuurqwerq23ewrm\"}}")' value="验证用户token接口(新)" ></td>
<td><input type="button"   onclick='setbinding("/userAuth/update_userInfo","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"update\":{\"mobileNum\":\"13912345678\",\"name\":\"zhangsan\",\"ID\":\"5600101981111123456\",\"nickname\":\"dog\",\"headImage\":\"图像base64编码字节流\",\"email\":\"zhangsan@123.com\"}}")' value="更新用户基本信息" ></td>
</tr>
<tr>
<td><input type="button"  onclick='setbinding("/userAuth/bind_bankCard","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"bind\":{\"mobileNum\":\"18922260815\",\"area\":\"深圳\",\"bankName\":\"建设银行\",\"branchName\":\"支行名称\",\"cardNo\":\"5600101981111123456\",\"name\":\"张三\",\"ID\":\"5600101981111123456\"}}")' value="绑定银行卡" ></td> 
<td><input type="button"  onclick='setbinding("/userAuth/unbind_bankCard","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"unbind\":{\"mobileNum\":\"18922260815\",\"cardNo\":\"5600101981111123456\"}}")' value="解除绑定银行卡" ></td> 
<td><input type="button"  onclick='setbinding("/userAuth/resetPasswordWithSmscode","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"login\":{\"mobileNum\":\"18922260815\",\"smsCode\":\"4567\",\"newpassword\":\"1234\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"}}")' value="通过验证码重置密码" ></td> 
<td><input type="button"  onclick='setbinding("/userAuth/getPaymentCodeStatus","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"query\":{\"mobileNum\":\"18922260815\"}}")' value="查看支付密码状态" ></td> 
<td><input type="button"  onclick='setbinding("/userAuth/batchAddUser","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"body\":{\"users\":[{\"mobileNum\":\"18922260815\",\"ID\":\"4567\",\"name\":\"张三\",\"attrs\":[\"UBER\"]}]}}")' value="批量添加用户" ></td> 
<td><input type="button"  onclick='setbinding("/userAuth/hasAttr","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"body\":{\"mobileNum\":\"18922260815\",\"attr\":\"UBER\"}}")' value="判断用户属性" ></td> 
</tr>
<tr>
<td><input type="button"  onclick='setbinding("/userAuth/loginWithPaymentCodeDES","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"b94490aecabf04eba1e9ab58dc76c115\"},\"body\":{\"mobileNum\":\"18922260815\",\"smsCode\":\"4567\",\"paymentCodeDES\":\"IT0nt8GfsYU=\"}}")' value="使用支付密码登录" ></td> 
<td><input type="button"  onclick='setbinding("/userAuth/resetPaymentCodeBySmsCodeAndPassword","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"73ef10be2e08a0ad541d28fbe99a0df4\"},\"reset\":{\"mobileNum\":\"18922260815\",\"smsCode\":\"4567\",\"newPaymentCodeDES\":\"IT0nt8GfsYU=\",\"password\":\"g/vM8awZhICUBjt7aAR7OA==\"}}")' value="验证码和登录密码修改支付密码" ></td> 
</tr>
<tr><td colspan="6">分期卡部分</td></tr>
<tr> 
<td><input type="button"  onclick='setbinding("/oilcard/open_online","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"ID\":\"515411244445444444X\",\"name\":\"李四\",\"mobileNum\":\"18922260815\",\"smsCode\":\"4567\",\"channelOrderId\":\"channel1234567890\",\"channelNo\":\"channel001\",\"appOrderId\":\"hjjtest2\",\"productId\":\"2010\",\"remark\":\"某某渠道为用户13912345678开卡\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="线上开卡" ></td>
<td><input type="button"  onclick='setbinding("/oilcard/transfer_to_cashAccount","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"18922260815\",\"sum\":500000,\"remark\":\"从分期卡账户转账5000元到现金账户\",\"appOrderId\":\"AO201412122344888444\",\"accountCode\":\"231435\",\"paymentCodeMD5\":\"w344dioeorreeoocWRT\",\"accountId\":\"2010010000100548\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="分期卡转账到现金账户" ></td>
<td><input type="button"  onclick='setbinding("/offline/pay","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"consumerId\":\"18922260815\",\"paymentCodeDES\":\"123werq123wer\",\"sum\":5432,\"sellerId\":\"hjjtest\",\"storeId\":\"ST12345\",\"terminalId\":\"ZJ000001\",\"operatorId\":\"zhangsan\",\"batchNo\":\"0004\",\"terminalOrderId\":\"1248163297531\",\"productName\":\"0号柴油\",\"productPrice\":500,\"productQuantity\":\"1.55\",\"appOrderId\":\"1234567890\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="线下支付" ></td>
<td><input type="button"  onclick='setbinding("/offline/pay_cancel","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"orderId\":\"1248163297531\",\"mobileNum\":\"13912345678\",\"sellerId\":\"hjjtest\",\"storeId\":\"ST12345\",\"terminalId\":\"ZJ000001\",\"operatorId\":\"zhangsan\",\"batchNo\":\"0004\",\"terminalOrderId\":\"12468163297532\",\"remark\":\"\",\"appOrderId\":\"12345678\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="线下支付撤销" ></td>
<td><input type="button"  onclick='setbinding("/offline/pay_undo","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"sellerId\":\"hjjtest\",\"storeId\":\"ST12345\",\"terminalId\":\"ZJ000001\",\"operatorId\":\"zhangsan\",\"batchNo\":\"0004\",\"terminalOrderId\":\"1248163297531\",\"remark\":\"\",\"appOrderId\":\"12345678\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="线下支付冲正" ></td>
<td><input type="button"  onclick='setbinding("/offline/get_account_info","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"consumerId\":\"18922260815\",\"sellerId\":\"hjjtest\",\"storeId\":\"ST12345\",\"terminalId\":\"POS12345\",\"operatorId\":\"zhangsan\",\"terminalOrderId\":\"1234567890\",\"batchNo\":\"123123123122132\",\"remark\":\"\"}}")' value="线下支付余额查询" ></td>
</tr>
<tr>
<td><input type="button"  onclick='setbinding("/oilcard/get","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"appOrderId\":\"123456789\"}")' value="查询开分期卡结果" ></td>
<td><input type="button"  onclick='setbinding("/offline/download_product","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"download\":{\"sellerId\":\"SH01\",\"storeId\":\"ST01\",\"terminalId\":\"POS01\",\"batchNo\":\"1234567\",\"terminalOrderId\":\"1234567890\"}}")' value="产品下载" ></td>
<td><input type="button"  onclick='setbinding("/offline/open_card","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"ID\":\"515411244445444444X\",\"name\":\"李四\",\"mobileNum\":\"18922260815\",\"appOrderId\":\"31415926\",\"sellerId\":\"hjjtest3\",\"storeId\":\"ST01\",\"terminalId\":\"POS01\",\"batchNo\":\"1234567\",\"terminalOrderId\":\"1234567890\",\"productId\":\"2010\",\"remark\":\"某某渠道为用户13912345678开卡\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="线下开卡(未开通)" ></td>
<td><input type="button"  value="线下开卡交割接口"  onclick='setbinding("/oilcard/open_offline_delivery","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"orderId\":\"123456789\", \"accountType\":\"DAC\"} ,\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")'></td>
<td><input type="button"  value="线下开卡交割失败记录同步"  onclick='setbinding("/oilcard/open_offline_deliveryfail_turbo","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"recoderList\":[{\"ID\":\"515411244445444444X\", \"name\":\"\", \"mobileNum\":\"13912345678\", \"sellerId\":\"SH01\",\"storeId\":\"\",\"terminalId\":\"POS01\",\"operatorId\":\"zhangsan\",\"batchNo\":\"1234567\",\"terminalOrderId\":\"1234567890\",\"productId\":\"10\",\"paySum\":100000,\"remark\":\"某某渠道为用户13912345678开卡\",\"payTime\":\"2015-01-01 12:12:00\"},{\"ID\":\"515411244445444444X\", \"name\":\"\", \"mobileNum\":\"13912345678\", \"sellerId\":\"SH01\",\"storeId\":\"\",\"terminalId\":\"POS01\",\"operatorId\":\"zhangsan\",\"batchNo\":\"1234567\",\"terminalOrderId\":\"1234567890\",\"productId\":\"10\",\"paySum\":100000,\"remark\":\"某某渠道为用户13912345678开卡\",\"payTime\":\"2015-01-01 12:12:00\"},{\"ID\":\"515411244445444444X\", \"name\":\"\", \"mobileNum\":\"13912345678\", \"sellerId\":\"SH01\",\"storeId\":\"\",\"terminalId\":\"POS01\",\"operatorId\":\"zhangsan\",\"batchNo\":\"1234567\",\"terminalOrderId\":\"1234567890\",\"productId\":\"10\",\"paySum\":100000,\"remark\":\"某某渠道为用户13912345678开卡\",\"payTime\":\"2015-01-01 12:12:00\"}],\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")'></td>
<td><input type="button"  onclick='setbinding("/oilcard/open_offline_delivery_turbo","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"ID\":\"515411244445444444X\",\"name\":\"李四\",\"mobileNum\":\"18922260815\",\"appOrderId\":\"31415926\",\"sellerId\":\"hjjtest3\",\"storeId\":\"ST01\",\"terminalId\":\"POS01\",\"batchNo\":\"1234567\",\"terminalOrderId\":\"1234567890\",\"productId\":\"2010\",\"remark\":\"某某渠道为用户13912345678开卡\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="线下开卡" ></td>
<td><input type="button"  onclick='setbinding("/oilcard/batch_open_online","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"}, \"order\":{\"channelNo\":\"channel001\",\"appOrderId\":\"123456789\", \"orders\":[{\"ID\":\"515411244445444444X\", \"name\":\"李四\", \"mobileNum\":\"13912345678\", \"channelOrderId\":\"channel1234567890\",\"productId\":\"10\",\"remark\":\"某某渠道为用户13912345678开卡\"}]},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="分期卡线上批量开卡" ></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>

<tr><td colspan="6">容量卡部分</td></tr>
<tr>
<td><input type="button"  onclick='setbinding("/volumecard/open_online","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"ID\":\"wIk3R0FIE8b2ABQPQTmIVk6glA12PwgF\",\"name\":\"bPiriIdT4oA=\",\"mobileNum\":\"54tpDBXnIuY9X3SfEF3/mA==\",\"smsCode\":\"4567\",\"channelOrderId\":\"channel1234567890\",\"channelNo\":\"channel001\",\"appOrderId\":\"hjjtest2\",\"productId\":\"4001\",\"remark\":\"某某渠道为用户13912345678开卡\"},\"tsig\":{\"orderMD5\":\"231b803fddb898a8a19e09eebb6e10f2\",\"signature\":\"662b9d64e55ec4ed80f4e1dd3c658aa7\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="线上开卡" ></td>
<td><input type="button"  onclick='setbinding("/volumecard/get","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"appOrderId\":\"123456789\"}")' value="查询开分期卡结果" ></td>
<td><input type="button"  onclick='setbinding("/offline/get_account_info","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"consumerId\":\"18922260815\",\"sellerId\":\"hjjtest\",\"storeId\":\"ST12345\",\"terminalId\":\"POS12345\",\"operatorId\":\"zhangsan\",\"terminalOrderId\":\"1234567890\",\"batchNo\":\"123123123122132\",\"remark\":\"\"}}")' value="线下余额查询" ></td>
<td><input type="button"  onclick='setbinding("/volumecard/batch_open_online","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"}, \"order\":{\"channelNo\":\"channel001\",\"appOrderId\":\"123456789\", \"orders\":[{\"ID\":\"515411244445444444X\", \"name\":\"李四\", \"mobileNum\":\"13912345678\", \"channelOrderId\":\"channel1234567890\",\"productId\":\"10\",\"remark\":\"某某渠道为用户13912345678开卡\"}]},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="容量卡线上批量开卡" ></td>
<td></td>
</tr>
<tr><td colspan="6">折扣卡部分</td></tr>
<tr>
<td><input type="button"  onclick='setbinding("/discountCard/open_online","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"ID\":\"515411244445444444X\",\"name\":\"李四\",\"mobileNum\":\"18922260815\",\"smsCode\":\"4567\",\"channelOrderId\":\"channel1234567890\",\"channelNo\":\"channel0879\",\"appOrderId\":\"hjjtest2\",\"productId\":\"3001\",\"remark\":\"某某渠道为用户13912345678开卡\"},\"tsig\":{\"orderMD5\":\"orderMD5\",\"signature\":\"signature\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="线上开卡" ></td>
<td><input type="button"  onclick='setbinding("/discountCard/get","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"appOrderId\":\"123456789\"}")' value="查询开分期卡结果" ></td>
<td><input type="button"  onclick='setbinding("/discountCard/batch_open_online","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"}, \"order\":{\"channelNo\":\"channel001\",\"appOrderId\":\"123456789\", \"orders\":[{\"ID\":\"515411244445444444X\", \"name\":\"李四\", \"mobileNum\":\"13912345678\", \"channelOrderId\":\"channel1234567890\",\"productId\":\"10\",\"remark\":\"某某渠道为用户13912345678开卡\"}]},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="折扣卡线上批量开卡" ></td>
<td><input type="button"  onclick='setbinding("/discountCard/openYouMeCardOnline","{\"app\":{\"appId\":\"YouMe\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"body\":{\"ID\":\"515411244445444444X\",\"name\":\"李四\",\"mobileNum\":\"13912345678\",\"appOrderId\":\"123456789\",\"productId\":\"3010\",\"amount\":500000,\"productPrice\":\"400000\",\"storeId\":[\"31010499\"],\"zjProducts\":[\"20120202\"],\"startTime\":\"2015-10-31\",\"endTime\":\"2015-12-31\",\"customStartTime\":\"20:00:00\",\"customEndTime\":\"24:00:00\",\"remark\":\"某某渠道为用户13912345678开卡\"},\"tsig\":{\"orderMD5\":\"df420b19711cdce5f965b9f784ff2630\",\"signature\":\"7df9da8ebdd23ffd8b3b033a35f7361c\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="油我发起线上开卡" ></td>
<td><input type="button"  onclick='setbinding("/paySetting/addAccountPayAllowProduct","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"body\":{\"accountId\":\"3010010000000233\",\"groupId\":\"3010010000000233\",\"zjproducts\":[\"99990123\",\"99990124\"]}}")' value="添加帐户消费允许油品" ></td>
<td></td>
</tr>
<tr>
</tr>
<tr><td colspan="6">红包</td></tr>
<tr>
<td><input type="button"  onclick='setbinding("/redPaper/give","{ \"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},  \"order\":{\"mobileNum\":\"18922260815\",\"sum\":100000,\"redPaperProductId\":\"6001\", \"appOrderId\":\"123456789\",\"remark\":\"某某为用户赠送有油贷1000元红包\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="赠送红包" ></td>
<td><input type="button"  onclick='setbinding("/redPaper/query","{ \"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"}, \"query\":{\"pageSize\":10,\"pageIndex\":1,\"mobileNum\":\"18922260815\", \"redPaperProductId\":\"6001|6002\",\"expireIn\":\"ALL\"}}")' value="查询红包" ></td>
<td><input type="button"  onclick='setbinding("/redPaper/spend","{ \"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"}, \"order\":{\"mobileNum\":\"18922260815\",\"redPaperId\":\"1234123412341234|1234123412341235\",\"remark\":\"使用红包消费\", \"appOrderId\":\"AO201412122344888444\",\"associatedOrderId\":\"AO201412122344888444\",\"accountCode\":\"231435\",\"paymentCodeMD5\":\"123werq123wer\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="消费红包" ></td>
<td><input type="button"  onclick='setbinding("/redPaper/undo","{ \"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":[{\"orderId\":\"ud12345678901234567890\"},{\"orderId\":\"ud12345678901234567890\"}],\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="撤销红包支付" ></td>

</tr>
<tr><td colspan="6">积分</td></tr>
<tr>
<td><input type="button"  onclick='setbinding("/jifen/add","{ \"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},  \"order\":{\"mobileNum\":\"18922260815\",\"sum\":100000,\"jifenProductId\":\"7001\", \"appOrderId\":\"123456789\",\"remark\":\"某某为用户赠送有油贷1000元红包\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="赠送积分" ></td>
<td><input type="button"  onclick='setbinding("/jifen/query","{ \"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"}, \"query\":{\"pageSize\":10,\"pageIndex\":1,\"mobileNum\":\"18922260815\", \"jifenProductId\":\"7001|7002\"}}")' value="查询积分" ></td>
<td><input type="button"  onclick='setbinding("/jifen/spend","{ \"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"}, \"order\":{\"mobileNum\":\"18922260815\",\"jifenProductId\":\"552015042120472400000283515921\", \"sum\":300,\"remark\":\"使用红包消费\", \"appOrderId\":\"AO201412122344888444\",\"associatedOrderId\":\"AO201412122344888444\",\"accountCode\":\"231435\",\"paymentCodeMD5\":\"123werq123wer\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="兑换积分" ></td>

</tr>
<tr><td colspan="6">微信部分</td></tr>
<tr>
<td><input type="button"  onclick='setbinding("/cashAccount/open","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"open\":{\"mobileNum\":\"18922260815\",\"smsCode\":\"2234\",\"paymentCode\":\"223344\"}} ")' value="开通现金账户" ></td> 
<td><input type="button"  onclick='setbinding("/cashAccount/getAccountInfo","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"get\":{\"mobileNum\":\"18922260815\"}}")' value="现金账户信息查询" ></td> 
<td><input type="button"  onclick='setbinding("/cashAccount/getBalance","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"get\":{\"mobileNum\":\"18922260815\"}}")' value="查询余额" ></td> 
<td><input type="button"  onclick='setbinding("/cashAccount/recharge","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"mobileNum\":\"13912345678\", \"sum\":500000,\"remark\":\"充值5000元到现金账户\",\"appOrderId\":\"AO201412122344888444\",\"externalOrderId\":\"AO201412122344888444\",\"externalOrderTime\":\"20150505123000\",\"accountCode\":\"\",\"peerAccountId\":\"124\",\"peerAccountUnit\":\"微信支付\",\"peerAccountType\":\"WXP\"},\"tsig\":{\"orderMD5\":\"ORDERMD5\",\"signature\":\"SIGNATURE\", \"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\"}}")' value="充值（到现金账户）接口" ></td> 
<td><input type="button"  onclick='setbinding("/userAuth/uploadPicture","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"upload\":{\"mobileNum\":\"13912345678\",\"name\":\"张三\",\"idType\":\"ID#\",\"idCode\":\"441233343143432141413\",\"picture\":\"BASE64后的图片\",\"picture2\":\"BASE64后的图片\"}}")' value="上传证件照片" ></td> 
<td><input type="button"  onclick='setbinding("/cashAccount/getOrder","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"order\":{\"externalOrderId\":\"22224444445555556666\"}}")' value="查询交易订单接口" ></td> 
</tr>
<tr>
<td><input type="button"  onclick='setbinding("/cashAccount/dayCheck","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"check\":{\"startDate\":\"20150505\",\"endDate\":\"20150506\"}}")' value="交易对账接口" ></td> 
<td><input type="button"  onclick='setbinding("/cashAccount/queryOrderList_xfcz","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"mobileNum\":\"13912345678\",\"pageSize\":10,\"pageIndex\":1}}")' value="交易记录查询接口" ></td> 
<td><input type="button"  onclick='setbinding("/userAuth/verify_paymentCodeDES","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"verifly\":{\"mobileNum\":\"13714770857\",\"paymentCodeDES\":\"F476AF399771994D\"}}")' value="验证用户支付密码DES" ></td> 
<td><input type="button"  onclick='setbinding("/userAuth/resetPaymentCodeBySmsCode","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\",\"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"}, \"reset\":{\"mobileNum\":\"13912345678\",\"smsCode\":\"123456\", \"newPaymentCodeDES\":\"DES(12345678)\"}}")' value="重置用户支付密码DES" ></td> 
<td><input type="button"  onclick='setbinding("/my/queryUserCardList","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"body\":{\"pageSize\":10,\"pageIndex\":1,\"mobileNum\":\"13912345678\",\"startDate\":\"2015-07-01\",\"endDate\":\"2015-07-10\",\"types\":[\"VCA\",\"DCA\",\"OCA\"],\"status\":[\"END\",\"OK#\",\"NOP\",\"FRZ\"],\"channelNo\":\"渠道\",\"queryCardSource\":true}}")' value="查询我的卡接口" ></td> 
<td><input type="button"  onclick='setbinding("/my/queryUserCard","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"body\":{\"accountId\":\"13912345678\"}}")' value="查询我的卡详情" ></td> 
</tr>
<tr><td colspan="6">订单</td></tr>
<tr>
<td><input type="button"  onclick='setbinding("/order/queryOrder","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"pageSize\":10,\"pageIndex\":1,\"mobileNum\":\"13912345678\",\"type\":[\"XF#\",\"CX#\",\"CZH\",\"CZ\"],\"sellerIds\":[\"0201000270\",\"0201000271\"],\"terminalId\":[\"4401008\"],\"startDate\":\"2015-01-01\",\"endDate\":\"2015-12-02\"}}")' value="查询流水" ></td>
<td><input type="button"  onclick='setbinding("/order/queryOrderYesterday","{\"app\":{\"appId\":\"zjhtwallet\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},\"query\":{\"pageSize\":10,\"pageIndex\":1,\"mobileNum\":\"13912345678\",\"type\":[\"XF#\",\"CX#\",\"CZH\",\"CZ\"],\"sellerIds\":[\"0201000270\",\"0201000271\"],\"terminalId\":[\"4401008\"]}}")' value="查询昨日流水" ></td>

</tr>
<tr><td colspan="6">其它测试</td></tr>
<tr>
<td><input type="button"  onclick='setbinding("/console/testAccountId","{\"productId\":\"2077\" }")' value="测试获取帐户" ></td>
<td><td><input  value="油站通知接口"  onclick='setbinding("/terminal/addStore","    {        \"app\":            {\"appId\":\"YouMe\",\"timeStamp\":\"TIMESTAMP\", \"nonce\":\"NONCE\",\"signature\":\"SIGNATURE\"},          \"body\":            {                \"sellerId\":\"35746533\",                 \"sellerName\":\"中森美\",                 \"storeId\":\"31010022\",                 \"storeName\":\"上海中油奉贤石油有限公司奉贤第一加油站\",  \"storeShortName\":\"奉贤第一加油站\",                \"addTerminalIds\":[\"44010608\",\"44010609\"],\"delTerminalIds\":[\"44010607\"],\"district\":\"外滩\",\"city\":\"上海\",\"status\":\"OK#\",\"province\":\"上海\",                \"sellerShortName\":\"中油奉贤\",                \"sellerType\":\"油品供应\",                \"longitude\":\"121.652916\",                \"latitude\":\"30.922837\"            }    }  ")'  type="button" ></td></td>

</tr>
</table>


  </body>
    <script>
    var type='payload'
    
		$("#bt").click(function (){
			$("#resp").html("");
			var hostval = $("#realhost").val()
			if(hostval==''){
				hostval=$("#host").val()
			}
			var url =hostval+$("#api").val();
			var data = $("#param").val();
			if(type=='payload')
			{
				var vcookie = $('#cookie').val();
				if(vcookie!=''){
					try{
						vcookie = eval('('+vcookie+')');
						for(item in vcookie){
							$.cookie(item, vcookie[item]);
						}
					}
					catch(e){console.print(e)}
					
				}
				
				$.ajax({type:'POST',contentType:'application/json',url:url,data:data,
				success:function(resp){ $("#resp").html(resp);},dataType:"html"}
				);
			}
			else if(type=='cookie'){
				data = eval('('+data+')');
				for(item in data){
					$.cookie(item, data[item]);
				}
				$.ajax({type:'POST',contentType:'application/json',url:url,
					success:function(resp){ $("#resp").html(resp);},dataType:"html"}
					);
			}
			else if(type=='formdata'){
				data = eval('('+data+')');
				$.ajax({type:'POST',url:url,data:data,
					success:function(resp){ $("#resp").html(resp);},dataType:"html"}
				);
			}
			else if(type=='string'){
				//以string 的形式提交，参数名为param
				$.ajax({type:'POST',url:url,data:{param:data},
					success:function(resp){ $("#resp").html(resp);},dataType:"html"}
					);
			}
			
		});
		
    function setbinding4string(api,json){
    	setbinding(api,json,null,'string');
    }
    
		function setbinding(api,json,cookiejson,submittype){
			$("#api").val(api)
			$("#param").val(json)
			$("#cookie").val(cookiejson)
			if(!submittype){
				submittype= 'payload';
			}
			type=submittype;
		}
		
		jQuery.cookie = function(name, value, options) {
			if (typeof value != 'undefined') {
			   options = options || {};
			   if (value === null) {
			    value = '';
			    options = $.extend({}, options);
			    options.expires = -1;
			   }
			   var expires = '';
			   if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			    var date;
			    if (typeof options.expires == 'number') {
			     date = new Date();
			     date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			    } else {
			     date = options.expires;
			    }
			    expires = '; expires=' + date.toUTCString();
			   }
			   var path = options.path ? '; path=' + (options.path) : '';
			   var domain = options.domain ? '; domain=' + (options.domain) : '';
			   var secure = options.secure ? '; secure' : '';
			   document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
			} else {
			   var cookieValue = null;
			   if (document.cookie && document.cookie != '') {
			    var cookies = document.cookie.split(';');
			    for (var i = 0; i < cookies.length; i++) {
			     var cookie = jQuery.trim(cookies[i]);
			     if (cookie.substring(0, name.length + 1) == (name + '=')) {
			      cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
			      break;
			     }
			    }
			   }
			   return cookieValue;
			}
			};
			
			function setbinding_cookie(api,json){
				$("#api").val(api)
				$("#param").val(json)
				type = 'cookie';
				
			}
		
		
	</script>
  
</html>

