package com.fkapi.vcc;

import com.fkapi.auth.createUserInfo;
import com.fkapi.auth.createVccCustomer;
import com.fkapi.service.*;
import com.fkapi.utils.Assertion;
import com.fkapi.utils.ExcelUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Reporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */
public class CreateVccConsumeTestData {
    vcc_orderService voService;
    vcc_user_card_mapService vucmService;
    vcc_loan_infoService vliService;
    p2p_nw_blacklistService pnbService;
    p2p_blacklist_storeService pbsService;
    p2p_black_deviceService pbdService;
    p2p_loan_claimService plcService;
    vcc_repay_planService vrpService ;

    p2p_repay_planService prpService ;

    String excelPath = System.getProperty("user.dir") + "\\testcase.xlsx";

    Map<String, String> userInfoMap;

    public Map<String, String> getUserInfoMap() {
        return userInfoMap;
    }

    public void setUserInfoMap(Map<String, String> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }

    public void create(String dataName, String userInfoNo, SqlSession session, SqlSession vccSession, SqlSession riskSession) {
        JSONObject json = null;
        //创建基础用户信息，并将基础用户信息保存到userInfoMap中
        createUserInfo cui = new createUserInfo();
        setUserInfoMap(cui.create(ExcelUtils.getRowNoByValue(excelPath, "userInfo",
                userInfoNo), session, vccSession));

        //添加虚拟信用卡用户表信息
        createVccCustomer cvc = new createVccCustomer();
        cvc.create(userInfoMap, true, vccSession);

        vucmService = new vcc_user_card_mapService();
        vucmService.addVccUserCardMap(userInfoMap, vccSession);

        if (!dataName.trim().isEmpty()) {
            Map<String, String> map = getVccConsumeData(ExcelUtils.getRowNoByValue(excelPath, "虚拟信用卡消费数据生成", dataName));
            if (!map.get("VPT_F001_F002").trim().isEmpty()) {
                json = new JSONObject(map.get("VPT_F001_F002"));
                JSONObject vccJson ;
                vliService = new vcc_loan_infoService();
                voService = new vcc_orderService();
                vrpService = new vcc_repay_planService();
                if (!map.get("VCC订单").trim().isEmpty()){
                    vccJson = new JSONObject(map.get("VCC订单"));
                    if(vccJson.getString("repayStatus").equals("OVERDUE_NO_REPAY")){
                        vliService.addVccLoanInfo(userInfoMap, json, "Y", vccSession);
                    }else {
                        vliService.addVccLoanInfo(userInfoMap, json, "N", vccSession);
                    }
                    voService.addVccOrder(userInfoMap, json, vccSession);
                    vrpService.addVccRepalyPlan(userInfoMap, vccJson, false, vccSession);
                }else {
                    vliService.addVccLoanInfo(userInfoMap, json, "N", vccSession);
                    voService.addVccOrder(userInfoMap, json, vccSession);
                    vccJson = new JSONObject();
                    vccJson.put("repayStatus","WAIT_REPAY");
                    vccJson.put("billOutFlag","N");
                    vccJson.put("delFlag","N");
                    vrpService.addVccRepalyPlan(userInfoMap, vccJson, true, vccSession);
                }
            }

            if (!map.get("现金贷订单").trim().isEmpty()) {
                json = new JSONObject(map.get("现金贷订单"));
                plcService = new p2p_loan_claimService();
                plcService.addProject(userInfoMap, json, false, session);
            }

            if (!map.get("VPT_U005现金贷明细").trim().isEmpty()){

                plcService = new p2p_loan_claimService();
                prpService = new p2p_repay_planService();
                JSONObject repayPlanJson = new JSONObject(map.get("VPT_U005现金贷明细"));
                if (!map.get("现金贷订单").trim().isEmpty()){
                    json = new JSONObject(map.get("现金贷订单"));
                    plcService.addProject(userInfoMap, json, false, session);
                }else {
                    json = new JSONObject();
                    json.put("projectName","牛大款");
                    json.put("loanSubSrc","NKK");
                    json.put("loanTerm",repayPlanJson.getInt("allTerm"));
                    json.put("status","WAIT_REPAY");
                    json.put("deviceCode","999999999");
                    json.put("time",0);
                    plcService.addProject(userInfoMap, json, false, session);
                }
                //添加还款明细表，每笔明细均为100元
                prpService.addRepayPlan(userInfoMap, plcService.getProjectNo(), repayPlanJson, session);
            }

            // 根据输入的信息判断是否将用户添加进黑名单
            if (map.get("内部黑名单") != null && map.get("内部黑名单").equals("Y")) {
                pnbService = new p2p_nw_blacklistService();
                pnbService.delNwBlackList(userInfoMap.get("oldCustId"), session);
                pnbService.addNwBlackList(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), session);
            } else if (map.get("内部黑名单") != null && map.get("内部黑名单").equals("N")) {
                pnbService = new p2p_nw_blacklistService();
                pnbService.delNwBlackList(userInfoMap.get("oldCustId"), session);
            }

            // 将用户输入的身份信息加入到黑名单中
            if (!map.get("外部黑名单").trim().isEmpty()) {
                pbsService = new p2p_blacklist_storeService();
                JSONObject inputJson = null;
                try {
                    inputJson = new JSONObject(map.get("外部黑名单"));
                    json = new JSONObject(userInfoMap.get("certAuth"));
                } catch (Exception e) {
                    Reporter.log("参数转json时出错，参数为：" + map.get("外部黑名单")
                            + userInfoMap.get("certAuth"));
                    Assertion.verifyTure(false);
                }
                // 删除使用的基础用户的黑名单信息
                pbsService.delBlackListStore(json, session);
                // 删除case中用户输入的信息
                pbsService.delBlackListStore(inputJson, session);
                // 将用户输入的身份信息添加到外部黑名单中
                pbsService.addBlackListStore(inputJson, session);
            } else {
                // 如果为空则表示不添加用户进入黑名单
                pbsService = new p2p_blacklist_storeService();
                json = new JSONObject(userInfoMap.get("certAuth"));
                pbsService.delBlackListStore(json, session);
            }

            // 根据输入的信息判断是否将设备加入到黑名单
            if (!map.get("设备黑名单").trim().isEmpty()) {
                pbdService = new p2p_black_deviceService();
                json = new JSONObject();
                //虚拟信用卡中的设备编号为当前用户的mobileSign
                json.put("deviceCode", new JSONObject(userInfoMap.get("phoneAuth")).getString("mobileSign"));
                json.put("loanSubSrc", "VCC");
                pbdService.delBlackDevice(json, session);
                if (map.get("设备黑名单").trim().toUpperCase().equals("Y")) {
                    pbdService.addBlackDevice(json, session);
                }
            }
        }
    }

    /**
     * 获取虚拟信用卡消费数据生成表中的信息
     *
     * @param dataNo
     * @return
     */
    public Map<String, String> getVccConsumeData(Integer dataNo) {
        Map<String, String> map;
        int allColNum = 0;
        try {
            allColNum = ExcelUtils.getAllColNum(System.getProperty("user.dir")
                    + "\\testcase.xlsx", "虚拟信用卡消费数据生成", 0);
        } catch (IOException e) {
            Reporter.log("获取Excel的信息失败");
        }
        map = new HashMap<>();
        for (int j = 0; j < allColNum; j++) {
            map.put(ExcelUtils.getCellDate(0, j),
                    ExcelUtils.getCellDate(dataNo, j));
        }
        return map;
    }

}