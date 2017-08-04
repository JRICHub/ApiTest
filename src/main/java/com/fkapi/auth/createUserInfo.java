package com.fkapi.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.fkapi.service.ex_StudentCreditInfoService;
import com.fkapi.service.jxl_primary_infoService;
import com.fkapi.service.p2p_account_bankcardService;
import com.fkapi.service.p2p_account_bankcard_logService;
import com.fkapi.service.p2p_account_checkService;
import com.fkapi.service.p2p_base_accountService;
import com.fkapi.service.p2p_base_customerService;
import com.fkapi.service.p2p_cert_authService;
import com.fkapi.service.p2p_cust_accountService;
import com.fkapi.service.p2p_cust_credit_infoService;
import com.fkapi.service.p2p_cust_top_contactorService;
import com.fkapi.service.p2p_customerService;
import com.fkapi.service.p2p_customer_contactorService;
import com.fkapi.service.p2p_customer_educationService;
import com.fkapi.service.p2p_customer_education_logService;
import com.fkapi.service.p2p_juxinli_mobile_authService;
import com.fkapi.service.p2p_juxinli_mobile_logService;
import com.fkapi.service.p2p_linkface_authService;
import com.fkapi.service.p2p_linkface_msgService;
import com.fkapi.service.p2p_student_custService;
import com.fkapi.service.p2p_student_infoService;
import com.fkapi.service.p2p_third_accountService;
import com.fkapi.service.py_educationService;
import com.fkapi.utils.ExcelUtils;

public class createUserInfo {

	Map<String, String> map ;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, String> create(Integer userInfoNo, SqlSession session, SqlSession vccSession) {
		p2p_customerService pcService;
		p2p_base_customerService pbcService ;
		p2p_cert_authService pcaService ;
		p2p_student_custService pscService ;
		p2p_student_infoService psiService ;
		ex_StudentCreditInfoService esService ;
		p2p_customer_educationService pceService ;
		p2p_customer_education_logService pcelService ;
		py_educationService peService ;
		p2p_linkface_authService plaService ;
		p2p_linkface_msgService plmService ;
		p2p_customer_contactorService pccService ;
		p2p_cust_top_contactorService pctcService ;
		p2p_juxinli_mobile_authService pjmaService ;
		p2p_juxinli_mobile_logService pjmlService ;
		p2p_cust_accountService pcacService ;
		p2p_account_bankcardService pabService ;
		p2p_base_accountService pbaService ;
		p2p_account_bankcard_logService pablService ;
		p2p_third_accountService ptaService ;
		p2p_account_checkService pacService ;
		p2p_cust_credit_infoService pcciService ;
		jxl_primary_infoService jpiService ;
		JSONObject json ;

		setMap(getUserInfoMap(userInfoNo));
		pcService = new p2p_customerService();
		pbcService = new p2p_base_customerService();

		map.put("oldCustId",String.valueOf(pcService.getCustID(map.get("loginName"), session)));
		// 添加p2p_customer表
		pcService.addCustomer(map.get("loginName"), map.get("platForm"), session);
		map.put("custId", pcService.getCustID(map.get("loginName"), session).toString());

		// 添加用户基础信息
		pbcService.addBaseCustomer(map.get("oldCustId"), map.get("custId"), session);

		// 添加身份认证
		if (!map.get("certAuth").trim().isEmpty()) {
			pbcService = new p2p_base_customerService();
			pcacService = new p2p_cust_accountService();
			pabService = new p2p_account_bankcardService();
			pbaService = new p2p_base_accountService();
			pablService = new p2p_account_bankcard_logService();
			ptaService = new p2p_third_accountService();
			pacService = new p2p_account_checkService();
			pcciService = new p2p_cust_credit_infoService();
			json = new JSONObject(map.get("certAuth").trim());
			if (json.getString("certAuthStatus") != null
					&& json.getString("certAuthStatus").equals("AS")) {
				pcaService = new p2p_cert_authService();
				pcaService.addCertAuth(map.get("oldCustId"), map.get("custId"), json, session);
				pcacService.addCustAccout(map.get("oldCustId"), map.get("custId"), json, session);
				pabService.addAccountBankCard(map.get("custId"), json,
						session);
				pbaService.addBaseAccount(map.get("custId"), json, session);
				pablService.addAccountBankcarLog(map.get("custId"), json,
						session);
				ptaService.addThirdAccount(map.get("oldCustId"), map.get("custId"), json, session);
				pacService
						.addAccountCheckService(map.get("custId"), json, session);
				pcciService.addCustCreditInfo(map.get("oldCustId"), map.get("custId"), session);
				pbcService.update(map.get("custId"), json, "certAuth",
						session);
			}
		}
		// 手机号码认证
		if (!map.get("phoneAuth").trim().isEmpty()) {
			json = new JSONObject(map.get("phoneAuth").trim());
			pbcService = new p2p_base_customerService();
			if (json.getString("phoneAuthStatus") != null
					&& json.getString("phoneAuthStatus").equals("AS")) {
				pcService = new p2p_customerService();
				pbcService.update(map.get("custId"), json, "phoneAuth",
						session);
				pcService.update(map.get("loginName"), json, "phoneAuth",
						session);
			}
		}
		// 添加学籍认证（学生）
		if (!map.get("schoolRollAuth").trim().isEmpty()) {
			pbcService = new p2p_base_customerService();
			json = new JSONObject(map.get("schoolRollAuth").trim());
			if (json.get("schoolRollAuthStatus") != null
					&& json.getString("schoolRollAuthStatus").equals("AS")) {
				pscService = new p2p_student_custService();
				pscService.addStudentCust(map.get("oldCustId"), map.get("custId"), json, session);
				psiService = new p2p_student_infoService();
				psiService.addStudentInfo(map.get("oldCustId"), map.get("custId"), json, session);
				esService = new ex_StudentCreditInfoService();
				esService.addExStudentInfo(map.get("oldCustId"), map.get("custId"),
						new JSONObject(map.get("certAuth")).getString("idNo"),
						json, session);

				pbcService.update(map.get("custId"), json, "schoolRollAuth",
						session);
			} else if (json.getString("schoolRollAuthStatus").equals("AF")) {
				pbcService.update(map.get("custId"), json, "schoolRollAuth",
						session);
			}
		}

		// 添加学历认证（成人）
		if (!map.get("educationAuth").trim().isEmpty()) {
			pbcService = new p2p_base_customerService();
			json = new JSONObject(map.get("educationAuth").trim());
			if (json.get("educationAuthStatus") != null
					&& json.get("educationAuthStatus").equals("AS")) {
				pceService = new p2p_customer_educationService();
				pceService.addCustomerEducation(map.get("oldCustId"), map.get("custId"), json,
						session);
				pcelService = new p2p_customer_education_logService();
				pcelService.addCustomerEducationLog(map.get("oldCustId"), map.get("custId"), json,
						session);
				peService = new py_educationService();
				peService.addPyEducation(map.get("oldCustId"), map.get("custId"), new JSONObject(
						map.get("certAuth")).getString("custName"),
						new JSONObject(map.get("certAuth")).getString("idNo"),
						json, session);

				pbcService.update(map.get("custId"), json, "educationAuth",
						session);
			} else if (json.get("educationAuthStatus") != null
					&& json.get("educationAuthStatus").equals("AF")) {
				pcelService = new p2p_customer_education_logService();
				pcelService.addCustomerEducationLog(map.get("oldCustId"), map.get("custId"), json,
						session);
				pbcService.update(map.get("custId"), json, "educationAuth",
						session);
			}
		}

		// 添加头像认证
		if (!map.get("photoAuth").trim().isEmpty()) {
			pbcService = new p2p_base_customerService();
			json = new JSONObject(map.get("photoAuth").trim());
			if (json.getString("photoAuthStatus") != null
					&& json.getString("photoAuthStatus").equals("AS")) {
				plaService = new p2p_linkface_authService();
				plaService.addLinkFaceAuth(map.get("oldCustId"), map.get("custId"), json, session);
				plmService = new p2p_linkface_msgService();
				plmService.addLinkFaceMsg(map.get("oldCustId"), map.get("custId"), new JSONObject(
						map.get("certAuth")).getString("custName"),
						new JSONObject(map.get("certAuth")).getString("idNo"),
						session);

				pbcService.update(map.get("custId"), json, "photoAuth",
						session);
			} else if (json.getString("photoAuthStatus") != null
					&& json.getString("photoAuthStatus").equals("AF")) {
				plaService = new p2p_linkface_authService();
				plaService.addLinkFaceAuth(map.get("oldCustId"), map.get("custId"), json, session);
				pbcService.update(map.get("custId"), json, "photoAuth",
						session);
			}
		}

		// 添加常用联系人认证
		if (!map.get("contractor").isEmpty()) {
			pccService = new p2p_customer_contactorService();
			pccService.addCustomerContactor(map.get("oldCustId"), map.get("custId"),
					map.get("contractor"), session);
			pctcService = new p2p_cust_top_contactorService();
			pctcService.addCustTopContactor(map.get("oldCustId"), map.get("custId"),
					map.get("contractor"), session);
		}

		// 添加通讯认证
		if (!map.get("jxlMobileAuth").trim().isEmpty()) {
			json = new JSONObject(map.get("jxlMobileAuth").trim());
			if (json.getString("jxlMobileAuthStatus").equals("AS")) {
				pjmaService = new p2p_juxinli_mobile_authService();
				pjmaService.addJuxinliMobileAuth(map.get("custId"), json,
						session);
				pjmlService = new p2p_juxinli_mobile_logService();
				pjmlService.addJuxinliMobileLog(map.get("oldCustId"), map.get("custId"),
						new JSONObject(map.get("phoneAuth"))
								.getString("mobile"), json, session);
				jpiService = new jxl_primary_infoService();
				jpiService.addPrimaryInfo(map.get("oldCustId"), map.get("custId"), json, session);
			}
		}

		// 认证用户住宅地址
		if (!map.get("nowAddress").trim().isEmpty()) {
			json = new JSONObject(map.get("nowAddress"));
			pbcService = new p2p_base_customerService();
			if (json.getString("addrAuthStatus").equals("AS")) {
				pbcService.update(map.get("custId"), json, "addrAuth",
						session);
			}
		}
		return map;
	}

	/**
	 * 根据userInfoNo获取userinfo表中的信息,并返回一个map类型
	 * 
	 * @param userInfoNo
	 *            ：userinfo表中的userInfoNo
	 * @return
	 */
	public Map<String, String> getUserInfoMap(Integer userInfoNo) {
		Map<String, String> map ;
		int allColNum = 0;
		try {
			allColNum = ExcelUtils.getAllColNum(System.getProperty("user.dir")
					+ "\\testcase.xlsx", "userInfo", 0);
		} catch (IOException e) {
			Reporter.log("获取Excel的信息失败");
		}
		map = new HashMap<>();
		for (int j = 0; j < allColNum; j++) {
			map.put(ExcelUtils.getCellDate(0, j),
					ExcelUtils.getCellDate(userInfoNo, j));
		}
		return map;
	}

	@Test
	public void test() {
	}
}
