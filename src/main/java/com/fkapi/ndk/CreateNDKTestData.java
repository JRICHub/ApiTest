package com.fkapi.ndk;

import com.fkapi.auth.createUserInfo;
import com.fkapi.rule.rule;
import com.fkapi.service.p2p_loan_claimService;
import com.fkapi.utils.CommonUtils;
import com.fkapi.utils.ExcelUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Reporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/18.
 */
public class CreateNDKTestData {
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
            Map<String, String> map = getNDKTestData(ExcelUtils.getRowNoByValue(CommonUtils.ndkExcelPath, CommonUtils.ndkDataSheetName, dataName));
            //添加风控审批订单
            rule.createLastOrder(userInfoMap, map.get("风控审批订单"), true, session);
            //添加用户的通讯录联系人信息
            rule.createCustList(this.getUserInfoMap(), map.get("通讯录联系人"), session);
            //根据是否全部为手机号中的信息判断，添加相应的数据
            rule.createMobileNo(userInfoMap, map.get("是否全部为手机号"), session);
            //根据住宅所在地中的信息添加相应的数据
            rule.createHomeCity(userInfoMap, map.get("是否有住宅所在地"), session);
            //添加AF004中对应的测试数据(此规则与虚拟信用卡中的VA_F007相同)
            rule.createVcc_VA_F007(userInfoMap, map.get("AF004"), session);
            //添加AF005/AF006中所需的测试数据
            rule.createVcc_VA_F009(userInfoMap, map.get("AF005/AF006"), session, vccSession, false);
            //添加AF007中所需的测试数据
            rule.createVcc_VA_F011(userInfoMap, map.get("AF007"), session, vccSession, false);
            //添加AF008中所需的测试数据
            rule.createVcc_VA_F012(userInfoMap, map.get("AF008"), session);
            //添加AF013中所需的测试数据
            rule.createNDKAF013(userInfoMap, map.get("AF013"), session);
            //添加AF014中所需的测试数据
            rule.createVcc_VA_F008(userInfoMap, map.get("AF014"), session);
            //添加AF015/AF016中所需的测试数据
            rule.createNDKAF014(userInfoMap, map.get("AF015/AF016"), session);
        }
    }

    public Map<String, String> getNDKTestData(Integer dataNo) {
        Map<String, String> map;
        int allColNum = 0;
        try {
            allColNum = ExcelUtils.getAllColNum(CommonUtils.ndkExcelPath, CommonUtils.ndkDataSheetName, 0);
        } catch (IOException e) {
            Reporter.log("获取Excel的信息失败");
        }
        map = new HashMap<>();
        for (int j = 0; j < allColNum; j++) {
            map.put(ExcelUtils.getCellDate(CommonUtils.ndkExcelPath, CommonUtils.ndkDataSheetName, 0, j),
                    ExcelUtils.getCellDate(CommonUtils.ndkExcelPath, CommonUtils.ndkDataSheetName, dataNo, j));
        }
        return map;
    }
}
