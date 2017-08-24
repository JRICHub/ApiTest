/**
 * 
 */
package com.fkapi.vcc;

import java.util.Map;

import com.fkapi.service.p2p_cust_addr_listService;
import com.fkapi.service.vcc_customerService;
import com.fkapi.service.vcc_user_card_mapService;
import com.fkapi.utils.*;
import javafx.geometry.Pos;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.*;
import com.fkapi.service.p2p_loan_claim_auditService;

/**
 * @author Administrator
 * 
 */
@Listeners({ AssertionListener.class })
public class ExcuteVccApplyCase {

	vcc_user_card_mapService vucmService ;
	vcc_customerService vcService ;

	@Test(dataProvider = "getData")
	public void excute(String caseName, String isRun, String userInfoNo, String dataName,
			String remark, String expect) {
		Map<String, String> userInfoMap ;
		p2p_loan_claim_auditService plcaService ;
		CreateVccApplyTestData cvtd = new CreateVccApplyTestData();
		SqlSession session = MybatisUtils.getFactory().openSession(true);
		SqlSession vccSession = VCCMybatisUtils.getFactory().openSession(true);
		Reporter.log("               ");
		Reporter.log("************** 当前执行的caseNo为： "	+ caseName + " **************");
		Reporter.log("               ");
		if (!userInfoNo.isEmpty()) {
			// 创建用户信息
			cvtd.create(dataName, userInfoNo, session, vccSession);

			// 获取userinfo表的基础信息
			userInfoMap = cvtd.getUserInfoMap();

			if (remark != null && expect != null) {
				// 清除风控审批数据
				plcaService = new p2p_loan_claim_auditService();
				plcaService.delAuditRe(userInfoMap.get("oldCustId"), session);

				// remark中以P开头的为准入前置规则
				if(remark.startsWith("P")){
					//请求准入前置规则接口
					Post.postVccApplyPre(userInfoMap.get("custId"));
				}else {
					//请求风控审批接口
					Post.postVccApply(
							userInfoMap.get("custId"), new JSONObject(userInfoMap.get("phoneAuth")).getString("mobileSign"));
				}
				//获取最终的审批结果
				String result = plcaService.getAuditRe(
						userInfoMap.get("custId"), remark, session);

				// 向Excel中写入实际结果
				try {
					ExcelUtils.setCellData(result, ExcelUtils.getRowNoByValue(
							CommonUtils.excelPath, CommonUtils.applyCaseSheetName, caseName), ExcelUtils
							.getColNoByValue(CommonUtils.excelPath, CommonUtils.applyCaseSheetName, "实际结果"),
							CommonUtils.excelPath, CommonUtils.applyCaseSheetName);
					Reporter.log("期望结果为：" + expect);
					Reporter.log("实际结果为：" + result);
				} catch (Exception e1) {
					Reporter.log("插入实际结果时发生异常，插入失败");
				}

				String finalResult = result.equals(expect) ? "Passed"
						: "Failed";
				// //向Excel中写入是否通过
				try {
					ExcelUtils.setCellData(finalResult, ExcelUtils
							.getRowNoByValue(CommonUtils.excelPath, CommonUtils.applyCaseSheetName, caseName),
							ExcelUtils.getColNoByValue(CommonUtils.excelPath, CommonUtils.applyCaseSheetName,
									"是否通过"), CommonUtils.excelPath, CommonUtils.applyCaseSheetName);
					Reporter.log("用例执行结果为：" + finalResult);
				} catch (Exception e) {
					Reporter.log("插入执行结果时发生异常，插入失败");
				}

				// 断言，判断实际结果与预期结果是否一致
				Assertion.verifyEquals(result, expect);
				Reporter.log("               ");
				Reporter.log("************** caseNo： " + caseName + " 执行完毕 **************");
				Reporter.log("               ");
				Reporter.log("               ");
				session.close();
			}
		} else {
			Reporter.log("请选择要创建的用户信息");
			Assertion.verifyTure(false);
		}
	}

	@BeforeMethod
	public void clearTestData(){
		SqlSession vccSession = VCCMybatisUtils.getFactory().openSession(true);
		vucmService = new vcc_user_card_mapService();
		vcService = new vcc_customerService();

		vucmService.delVccUserCardMap("62170010000000002%", vccSession);
		vcService.delVccCustomerByMobileSign("999999999", vccSession);

	}

	@DataProvider
	public Object[][] getData() throws Exception {
		return ExcelUtils.excelToDateMap(CommonUtils.excelPath, CommonUtils.applyCaseSheetName);
	}
}
