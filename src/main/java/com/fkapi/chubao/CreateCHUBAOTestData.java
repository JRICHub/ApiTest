package com.fkapi.chubao;

import com.fkapi.auth.createUserInfo;
import com.fkapi.rule.rule;
import com.fkapi.utils.CommonUtils;
import com.fkapi.utils.ExcelUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Reporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18.
 */
public class CreateCHUBAOTestData {
    private Map<String, String> userInfoMap ;

    public Map<String, String> getUserInfoMap() {
        return userInfoMap;
    }

    public void setUserInfoMap(Map<String, String> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }

    public void create(String dataName, String userInfoNo, SqlSession session){
        SqlSession vccSession = null;
        rule rule = new rule();
        createUserInfo cui = new createUserInfo();
        //创建用户数据信息并保存
        setUserInfoMap(cui.create(ExcelUtils.getRowNoByValue(CommonUtils.excelPath, CommonUtils.userInfoSheetName,
                userInfoNo), true, session));

        if (!dataName.trim().isEmpty()) {
            Map<String, String> map = getCHUBAOTestData(ExcelUtils.getRowNoByValue(CommonUtils.chubaoExcelPath, CommonUtils.chubaoDataSheetName, dataName));
            //添加风控审批订单
            rule.createLastOrder(userInfoMap, map.get("风控审批订单"), true, session);

            rule.createLastOrder(userInfoMap, map.get("上笔订单"), false, session);

            rule.createCertCity(userInfoMap, map.get("F003"), session);

            rule.createVcc_VA_F009(userInfoMap, map.get("F004/F005"), session, vccSession, false);

            rule.updateIdCardNo(userInfoMap, map.get("F007"), session);

            rule.createAge(userInfoMap, map.get("年龄"), session);

            rule.createOutBlackList(userInfoMap, map.get("外部黑名单"), session);

            rule.createInBlackList(userInfoMap, map.get("内部黑名单"), session);

            rule.createDeviceBlackList(userInfoMap, map.get("设备黑名单"), map.get("风控审批订单"), session);

            rule.createCustStatus(userInfoMap, map.get("账户状态"), session);
        }
    }

    /**
     * 获取触宝测试数据生成表中的数据
     * @param dataNo
     * @return
     */
    public Map<String, String> getCHUBAOTestData(Integer dataNo) {
        Map<String, String> map;
        int allColNum = 0;
        try {
            allColNum = ExcelUtils.getAllColNum(CommonUtils.chubaoExcelPath, CommonUtils.chubaoDataSheetName, 0);
        } catch (IOException e) {
            Reporter.log("获取Excel的信息失败");
        }
        map = new HashMap<>();
        for (int j = 0; j < allColNum; j++) {
            map.put(ExcelUtils.getCellDate(CommonUtils.chubaoExcelPath, CommonUtils.chubaoDataSheetName, 0, j),
                    ExcelUtils.getCellDate(CommonUtils.chubaoExcelPath, CommonUtils.chubaoDataSheetName, dataNo, j));
        }
        return map;
    }
}
