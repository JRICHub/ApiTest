/**
 * 
 */
package com.fkapi.utils;

import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @author Administrator
 * 
 */
public class Post {
	public static void postYcCredit(String projectNo, String prodName) {
		Reporter.log("请求风控审批接口的projectNo为：" + projectNo + "prodName为  ："
				+ prodName);
		Reporter.log("请求地址为：http://" + getPostIp()
				+ CommonUtils.getConfigValue("riskControl"));
		HttpClientUtil.sendHttpPost(
				"http://" + getPostIp()
						+ CommonUtils.getConfigValue("riskControl"),
				"{\"projectNo\":\"" + projectNo + "\",\"prodName\":\""
						+ prodName + "\"}");
	}

	public static void postYcLimit(String custId, String custName, String idNo) {
		Reporter.log("请求额度计算接口的custId为： " + custId + "custName为 ：" + custName
				+ "idNo为： " + idNo);
		Reporter.log("请求地址为：http://" + getPostIp()
				+ CommonUtils.getConfigValue("getlimit"));
		HttpClientUtil.sendHttpPost(
				"http://" + getPostIp()
						+ CommonUtils.getConfigValue("getlimit"),
				"{\"CUST_ID\":\"" + custId + "\",\"realName\":\"" + custName
						+ "\",\"idCardNo\":\"" + idNo + "\"}");
	}

	public static void postVccApply(String custId, String mobileSign){
		Reporter.log("请求虚拟信用卡准入接口的custId为： " + custId + "mobileSign为 ：" + mobileSign );
		Reporter.log("请求地址为：http://" + getPostIp()
				+ CommonUtils.getConfigValue("vccApply"));
		HttpClientUtil.sendHttpPost(
				"http://" + getPostIp() + CommonUtils.getConfigValue("vccApply"),
				"{\"cust_id\": \"" + custId + "\",\"mobile_sign\":\""
						+ mobileSign + "\"}");
	}

	public static void postVccApplyPre(String custId){
		Reporter.log("请求虚拟信用卡准入前置接口的custId为： " + custId );
		Reporter.log("请求地址为：http://" + getPostIp()
				+ CommonUtils.getConfigValue("vccPre"));
		HttpClientUtil.sendHttpPost(
				"http://" + getPostIp() + CommonUtils.getConfigValue("vccPre"),
				"{\"cust_id\": \"" + custId + "\"}");
	}

	public static void postVccConsume(String custId, String transId){
		Reporter.log("请求虚拟信用卡准入接口的custId为： " + custId + "transId ：" + transId );
		Reporter.log("请求地址为：http://" + getPostIp()
				+ CommonUtils.getConfigValue("vccConsume"));
		HttpClientUtil.sendHttpPost(
				"http://" + getPostIp() + CommonUtils.getConfigValue("vccConsume"),
				"{\"cust_id\": \""+ custId + "\",\"trans_id\":\"" + transId + "\"}");
	}

	public static String postVccLimit(String custId){
		String response = null ;
		Reporter.log("请求虚拟信用卡额度接口的custId为： " + custId );
		Reporter.log("请求地址为：http://" + getPostIp()
				+ CommonUtils.getConfigValue("vccLimit"));
		response = HttpClientUtil.sendHttpPost(
				"http://" + getPostIp() + CommonUtils.getConfigValue("vccLimit"),
				"{\"cust_id\": \"" + custId + "\"}");
		return response ;
	}

	/**
	 * 通过数据库的路径获取请求地址的Ip
	 * 
	 * @return
	 */
	public static String getPostIp() {
		String excelPath = System.getProperty("user.dir")
				+ "\\testcase.xlsx";
		String sheetName = "config";
		GetExcelConfig getExcelConfig = new GetExcelConfig();
		Map<String, String> map = getExcelConfig.getConfigData(excelPath, sheetName, "common");
		String ip = map.get("ip");
		return ip;
	}

	@Test
	public void test() {
		postVccLimit("715009726");
	}
}
