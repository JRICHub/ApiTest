package com.fkapi.vcc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fkapi.auth.createVccCustomer;
import com.fkapi.utils.Assertion;
import com.fkapi.utils.CommonUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Reporter;
import com.fkapi.auth.createUserInfo;
import com.fkapi.service.p2p_cust_addr_listService;
import com.fkapi.service.*;
import com.fkapi.utils.ExcelUtils;

/**
 * @author Administrator
 */
public class CreateVccApplyTestData {

    private String excelPath = System.getProperty("user.dir") + "\\testcase.xlsx";

    private Map<String, String> userInfoMap ;

    public Map<String, String> getUserInfoMap() {
        return userInfoMap;
    }

    public void setUserInfoMap(Map<String, String> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }

    JSONObject json = null;

    public void create(String dataName, String userInfoNo, SqlSession session, SqlSession vccSession) {
        p2p_cust_addr_listService pcalService;
        p2p_dictionaryService pdService;
        p2p_mobile_addrService pmaService;
        p2p_student_custService pscService;
        p2p_customer_contactorService pccService;
        p2p_base_customerService pbcService;
        p2p_customer_educationService pceService;
        jxl_phone_call_recordsService jxlRecordsService;
        p2p_loan_claimService plcService;
        p2p_cust_location_logService pcllService;
        p2p_cert_authService pcaService ;
        p2p_nw_blacklistService pnbService ;
        p2p_blacklist_storeService pbsService ;
        p2p_black_deviceService pbdService ;
        vcc_user_card_mapService vucmService ;

        createUserInfo cui = new createUserInfo();

        //创建基础用户信息并获取用户基础信息，并转成map形式存储（userinfo表）
        setUserInfoMap(cui.create(ExcelUtils.getRowNoByValue(excelPath, "userInfo",
                userInfoNo), session, vccSession));

        //添加虚拟信用卡用户表信息
        createVccCustomer cvc = new createVccCustomer();
        cvc.create(userInfoMap, true, vccSession);

        if (!dataName.trim().isEmpty()) {
            Map<String, String> map = getVccApplyData(ExcelUtils.getRowNoByValue(excelPath, "虚拟信用卡准入数据生成", dataName));

            if (!map.get("上笔订单").trim().isEmpty()) {
                json = new JSONObject(map.get("上笔订单"));
                plcService = new p2p_loan_claimService();
                plcService.addProject(userInfoMap, json, false, session);
            }
            //根据通讯录联系人中的信息判断，添加相应的数据
            if (!map.get("通讯录联系人").trim().isEmpty()) {
                json = new JSONObject(map.get("通讯录联系人"));
                pcalService = new p2p_cust_addr_listService();
                pcalService.addCustAddrList(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"), json, session);
            }

            //根据是否全部为手机号中的信息判断，添加相应的数据
            if (!map.get("是否全部为手机号").trim().isEmpty()) {
                pcalService = new p2p_cust_addr_listService();
                if (map.get("是否全部为手机号").equals("标准手机号")) {
                    //为标准手机号时不做任何操作
                }
                if (map.get("是否全部为手机号").equals("非标准手机号")) {
                    //更新其中一条手机号将其改为非标准手机号
                    pcalService.updateCustAddrList(userInfoMap.get("custId"), session, "非标准手机号");
                }
                if (map.get("是否全部为手机号").equals("N")) {
                    //若不是手机号，则更新手机号为非手机号
                    pcalService.updateCustAddrList(userInfoMap.get("custId"), session, "非手机号");
                }
            }

            //根据住宅所在地中的信息判断，添加相应的数据
            if (!map.get("是否有住宅所在地").trim().isEmpty()) {
                pdService = new p2p_dictionaryService();
                pmaService = new p2p_mobile_addrService();
                pcalService = new p2p_cust_addr_listService();
                json = new JSONObject(userInfoMap.get("nowAddress"));
                if (map.get("是否有住宅所在地").toUpperCase().equals("Y")) {
                    String cityCode = pdService.getDictCode(json.getString("nowCity"), session);
                    String mobileCode = pmaService.getMobileByAddr(cityCode, session);
                    pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), mobileCode + "0000", session);
                }
                if (map.get("是否有住宅所在地").toUpperCase().equals("N")) {
                    String cityCode = pdService.getDictCode(json.getString("nowCity"), session);
                    String mobileCode = pmaService.getOtherMobileByAddr(cityCode, session);
                    pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), mobileCode + "0000", session);
                }
            }

            //根据是否有户籍所在地中的信息判断，添加相应的数据
            if (!map.get("是否有户籍所在地").trim().isEmpty()) {
                pmaService = new p2p_mobile_addrService();
                pcalService = new p2p_cust_addr_listService();
                //截取身份证前4位，即为户籍所在地编码
                String cityCode = new JSONObject(userInfoMap.get("certAuth")).getString("idNo").substring(0, 4);
                if (map.get("是否有户籍所在地").toUpperCase().equals("Y")) {
                    String mobileCode = pmaService.getMobileByAddr(cityCode, session);
                    pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), mobileCode + "0000", session);
                }
                if (map.get("是否有户籍所在地").toUpperCase().equals("N")) {
                    String mobileCode = pmaService.getOtherMobileByAddr(cityCode, session);
                    pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), mobileCode + "0000", session);
                }
            }

            //根据是否有父母所在地中的信息判断，添加相应的数据
            if (!map.get("是否有父母所在地").trim().isEmpty()) {
                pccService = new p2p_customer_contactorService();
                pmaService = new p2p_mobile_addrService();
                pcalService = new p2p_cust_addr_listService();
                String cityCode = pccService.getCustomerContactor(userInfoMap.get("custId"), session);
                if (map.get("是否有父母所在地").toUpperCase().equals("Y")) {
                    String mobileCode = pmaService.getMobileByAddr(cityCode, session);
                    pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), mobileCode + "0000", session);
                }
                if (map.get("是否有父母所在地").toUpperCase().equals("N")) {
                    String mobileCode = pmaService.getOtherMobileByAddr(cityCode, session);
                    pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), mobileCode + "0000", session);
                }
            }

            //根据是否有学校号码段中的信息判断，添加相应的数据
            if (!map.get("是否有学校号码段").trim().isEmpty()) {
                String schoolCityCode;
                pmaService = new p2p_mobile_addrService();
                pcalService = new p2p_cust_addr_listService();
                pscService = new p2p_student_custService();
                pceService = new p2p_customer_educationService();
                if (userInfoNo.length() > 0 && userInfoNo.contains("student")) {
                    schoolCityCode = pscService.getSchoolCityCode(userInfoMap.get("custId"), session);
                } else {
                    schoolCityCode = pceService.getSchoolCityCode(userInfoMap.get("custId"), session);
                }
                if (map.get("是否有学校号码段").toUpperCase().equals("Y")) {
                    String mobileCode = pmaService.getMobileByAddr(schoolCityCode, session);
                    pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), mobileCode + "0000", session);
                }
                if (map.get("是否有学校号码段").toUpperCase().equals("N")) {
                    String mobileCode = pmaService.getOtherMobileByAddr(schoolCityCode, session);
                    pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), mobileCode + "0000", session);
                }
            }

            //根据VA_F006中的信息判断，添加相应的数据
            if (!map.get("VA_F006").trim().isEmpty()) {
                String mobileCode = null;
                String schoolCityCode;
                JSONObject mobileJson = new JSONObject();
                json = new JSONObject(map.get("VA_F006"));
                pccService = new p2p_customer_contactorService();
                pbcService = new p2p_base_customerService();
                pmaService = new p2p_mobile_addrService();
                pscService = new p2p_student_custService();
                pceService = new p2p_customer_educationService();
                //获取父母所在地城市编码
                String parentCityCode = pccService.getCustomerContactor(userInfoMap.get("custId"), session);
                //根据所输入的参数判断是否毕业，获取相应的学校所在地城市编码
                if (userInfoNo.length() > 0 && userInfoNo.contains("student")) {
                    schoolCityCode = pscService.getSchoolCityCode(userInfoMap.get("custId"), session);
                } else {
                    schoolCityCode = pceService.getSchoolCityCode(userInfoMap.get("custId"), session);
                }
                if (json.get("parentAddr").equals("Y") && json.get("schoolAddr").equals("Y")) {
                    mobileCode = pmaService.getMobileByAddr(parentCityCode, session);
                }
                if (json.get("parentAddr").equals("Y") && json.get("schoolAddr").equals("N")) {
                    mobileCode = pmaService.getMobileByAddr(parentCityCode, session);
                }
                if (json.get("parentAddr").equals("N") && json.get("schoolAddr").equals("Y")) {
                    mobileCode = pmaService.getMobileByAddr(schoolCityCode, session);
                }
                if (json.get("parentAddr").equals("N") && json.get("schoolAddr").equals("N")) {
                    mobileCode = pmaService.getOtherMobileByAddr(schoolCityCode, session);
                }
                mobileJson.put("phoneAuthStatus", "AS");
                mobileJson.put("mobile", mobileCode + "0000");
                pbcService.update(userInfoMap.get("custId"), mobileJson, "phoneAuth", session);
            }

            if (!map.get("VA_F007").trim().isEmpty()) {
                jxlRecordsService = new jxl_phone_call_recordsService();
                json = new JSONObject(map.get("VA_F007"));
                jxlRecordsService.addRecordsForVA_F007(userInfoMap, json, session);
            }

            if (!map.get("VA_F008").trim().isEmpty()) {
                jxlRecordsService = new jxl_phone_call_recordsService();
                json = new JSONObject(map.get("VA_F008"));
                jxlRecordsService.addCallRecordsForVA_F008(userInfoMap, json, session);
            }

            if (!map.get("VA_F009/VA_F010").trim().isEmpty()){
                vucmService = new vcc_user_card_mapService();
                json = new JSONObject(map.get("VA_F009/VA_F010"));
                if(json.getString("isApply").toUpperCase().equals("Y")){
                    vucmService.addVccUserCardMap(userInfoMap, json, session, vccSession);
                } else {
                    vucmService.delVccUserCardMap(new JSONObject(userInfoMap.get("phoneAuth")).getString("mobileSign"), vccSession);
                }
            }

            if (!map.get("VA_F011").trim().isEmpty()){
                vucmService = new vcc_user_card_mapService();
                json = new JSONObject(map.get("VA_F011"));
                JSONArray jsonArray = json.getJSONArray("addrList");
                JSONObject addrListJson = jsonArray.getJSONObject(0);
                pcalService = new p2p_cust_addr_listService();
                Map<String, String> otherUserInfoMap = cui.create(ExcelUtils.getRowNoByValue(excelPath, "userInfo",
                        json.getString("userinfoNo")), session, vccSession);
                cvc.create(otherUserInfoMap, false, vccSession);
                vucmService.addVccUserCardMap(otherUserInfoMap, vccSession);
                pcalService.addCustAddrList(userInfoMap.get("oldCustId"), otherUserInfoMap.get("custId"), new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"), addrListJson, session);
            }

            if (!map.get("VA_F012").trim().isEmpty()) {
                pcllService = new p2p_cust_location_logService();
                json = new JSONObject(map.get("VA_F012"));
                pcllService.addCustLocationLog(userInfoMap, json, session, "VA_F012");
            }

            if (!map.get("VA_F013_用户名字").trim().isEmpty()) {
                pbcService = new p2p_base_customerService();
                json = new JSONObject();
                json.put("custName", map.get("VA_F013_用户名字"));
                json.put("idNo", new JSONObject(userInfoMap.get("certAuth")).getString("idNo"));
                json.put("certAuthStatus", "AS");
                pbcService.update(userInfoMap.get("custId"), json, "certAuth", session);
            }

            if (!map.get("VA_F014").trim().isEmpty()) {
                pcllService = new p2p_cust_location_logService();
                json = new JSONObject(map.get("VA_F014"));
                pcllService.addCustLocationLog(userInfoMap, json, session, "VA_F014");
            }

            if (!map.get("VA_F015").trim().isEmpty()) {
                JSONObject jsonObject = new JSONObject(map.get("VA_F015"));
                int num = jsonObject.getInt("allNum");
                if (num > 0) {
                    for (int i = 0; i < num; i++) {
                        pcalService = new p2p_cust_addr_listService();
                        JSONArray jsonArray = jsonObject.getJSONArray("userinfo");
                        JSONObject userJson = jsonArray.getJSONObject(i);
                        //创建用户信息
                        Map<String, String> classmateUserInfoMap = cui.create(ExcelUtils.getRowNoByValue(excelPath, "userInfo",
                                userJson.getString("userinfoNo")), session, vccSession);
                        if (userJson.getString("inAddrListNum").equals("1")) {
                            pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), classmateUserInfoMap.get("custId"), new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"), session);
                        }
                        if (userJson.getString("inAddrListNum").equals("2")) {
                            pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), userInfoMap.get("custId"), new JSONObject(classmateUserInfoMap.get("phoneAuth")).getString("mobile"), session);
                            pcalService.addCustAddrListForAddress(userInfoMap.get("oldCustId"), classmateUserInfoMap.get("custId"), new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"), session);
                        }
                    }
                }
            }

            // 根据用户输入的年龄，更新对应的身份证信息
            if (!map.get("年龄").trim().isEmpty()) {
                pbcService = new p2p_base_customerService();
                pcaService = new p2p_cert_authService();
                json = new JSONObject();
                json.put("certNo", CommonUtils.creteIdCardByAge(
                        userInfoMap.get("custId"), map.get("年龄"), session));
                pbcService.update(userInfoMap.get("custId"), json, "age",
                        session);
                pcaService.update(userInfoMap.get("custId"), CommonUtils
                        .creteIdCardByAge(userInfoMap.get("custId"),
                                map.get("年龄"), session), session);
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
                try {
                    json = new JSONObject(map.get("上笔订单"));
                } catch (Exception e) {
                    Reporter.log("参数转json时出错，参数为：" + map.get("上笔订单"));
                    Assertion.verifyTure(false);
                }
                pbdService.delBlackDevice(json, session);
                if (map.get("设备黑名单").equals("Y")) {
                    pbdService.addBlackDevice(json, session);
                }
            }
        }
    }


    /**
     * 获取虚拟信用卡数据生成表的信息
     *
     * @param dataNo
     * @return 返回Excel的二维数据给dataprovider
     */
    public Map<String, String> getVccApplyData(Integer dataNo) {
        Map<String, String> map;
        int allColNum = 0;
        try {
            allColNum = ExcelUtils.getAllColNum(System.getProperty("user.dir")
                    + "\\testcase.xlsx", "虚拟信用卡准入数据生成", 0);
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
