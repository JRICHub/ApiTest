package com.fkapi.service;

import com.fkapi.auth.createUserInfo;
import com.fkapi.datebase.domain.jxl_phone_call_records;
import com.fkapi.datebase.dao.jxl_phone_call_recordsMapper;
import com.fkapi.datebase.domain.p2p_cust_addr_list;
import com.fkapi.utils.CommonUtils;
import com.fkapi.utils.ExcelUtils;
import com.fkapi.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by Administrator on 2017/7/10.
 */
public class jxl_phone_call_recordsService {
    jxl_phone_call_recordsMapper jxlRecordMapper ;
    jxl_primary_infoService jpiService ;
    p2p_cust_addr_listService pcalService ;
    p2p_mobile_addrService pmaService ;
    p2p_dictionaryService pdService ;

    /**
     *  根据VA_F008规则输入的信息添加通话记录
     * @param userInfoMap 用户基础信息表
     * @param json 要添加的通话记录信息（虚拟信用卡数据生成表中的json）
     * @param session
     */
    public void addCallRecordsForVA_F008(Map<String,String> userInfoMap, JSONObject json, SqlSession session){
        delCallRecords(userInfoMap.get("oldCustId"), session);
        jxlRecordMapper = session.getMapper(jxl_phone_call_recordsMapper.class);
        jpiService = new jxl_primary_infoService();
        JSONArray jsonArray = (JSONArray) new JSONObject(userInfoMap.get("contractor")).get("contractor");
        List<jxl_phone_call_records> list = new ArrayList<>();
        jxl_phone_call_records jxlRecords = new jxl_phone_call_records();

        jxlRecords.setId(String.valueOf(System.currentTimeMillis()));
        jxlRecords.setPrimaryId(jpiService.getPrimaryId(userInfoMap.get("custId"), session));
        jxlRecords.setCellPhone(new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"));
        if(json.getString("isContactor").toUpperCase().equals("Y")){
            jxlRecords.setOtherCellPhone(String.valueOf(new JSONObject(jsonArray.get(0).toString()).get("mobile")));
        }else {
            jxlRecords.setOtherCellPhone("13333333333");
        }
        jxlRecords.setCallPlace("江苏");
        jxlRecords.setStartTime(CommonUtils.subDay(CommonUtils.getCurDate("second"), json.getInt("day")));
        jxlRecords.setUseTime(10L);
        jxlRecords.setInitType(json.getString("type"));
        jxlRecords.setUpdateTime(CommonUtils.subDay(CommonUtils.getCurDate("second"), json.getInt("day")));
        list.add(jxlRecords);
        jxlRecordMapper.insertVA_F008(list);
    }

    /**
     * 根据VA_F007规则输入的信息添加通话记录
     * @param userInfoMap
     * @param json 要添加的通话记录信息（虚拟信用卡数据生成表中的json-VA_F007）
     * @param session
     */
    public void addRecordsForVA_F007(Map<String,String> userInfoMap, JSONObject json, SqlSession session){
        delCallRecords(userInfoMap.get("oldCustId"), session);
        //首先生成基础的通话记录数据，认证前三个月的数据，主叫次数均为一次，通话时间一次增加
        jxlRecordMapper = session.getMapper(jxl_phone_call_recordsMapper.class);
        jpiService = new jxl_primary_infoService();
        pcalService = new p2p_cust_addr_listService();
        List<jxl_phone_call_records> list;
        jxl_phone_call_records jxlRecords = new jxl_phone_call_records();
        for(int k=0;k<3;k++){
            for (int i=0;i<10;i++){
                for(int j=0;j<1;j++){
                    list = new ArrayList<>();
                    jxlRecords.setId(i + UUID.randomUUID().toString().replaceAll("-","").substring(1));
                    jxlRecords.setPrimaryId(jpiService.getPrimaryId(userInfoMap.get("custId"), session));
                    jxlRecords.setCellPhone(new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"));
                    jxlRecords.setOtherCellPhone("1333333333" + i);
                    jxlRecords.setCallPlace("江苏");
                    jxlRecords.setStartTime(CommonUtils.subMonth(CommonUtils.getCurDate("second"), k+1));
                    jxlRecords.setUseTime(10L + i*2);
                    jxlRecords.setInitType("主叫");
                    jxlRecords.setUpdateTime(CommonUtils.subMonth(CommonUtils.getCurDate("second"), k+1));
                    list.add(jxlRecords);
                    jxlRecordMapper.insertVA_F008(list);
                }
            }
        }
        //根据所填信息，新增相应的数据
        JSONArray jsonArray = json.getJSONArray("records");
        List<p2p_cust_addr_list> mobileList = pcalService.getCustAddrListMobile(userInfoMap.get("custId"), session);
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int numTopTen = jsonObject.getInt("NumTopTen");
            if(jsonObject.getInt("NumTopTen") > 0){
                for(int k=0;k<numTopTen;k++){
                    list = new ArrayList<>();
                    jxlRecords.setId(k + UUID.randomUUID().toString().replaceAll("-","").substring(1));
                    jxlRecords.setPrimaryId(jpiService.getPrimaryId(userInfoMap.get("custId"), session));
                    jxlRecords.setCellPhone(new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"));
                    jxlRecords.setOtherCellPhone(mobileList.get(k).getMobile());
                    jxlRecords.setCallPlace("江苏");
                    jxlRecords.setStartTime(CommonUtils.subMonth(CommonUtils.getCurDate("second"), i+1));
                    if(k == 0){
                        switch (jsonObject.getInt("MinRank") - numTopTen){
                            case 0 :
                                jxlRecords.setUseTime(99L);
                            case 1 :
                                jxlRecords.setUseTime(27L);
                                break;
                            case 2 :
                                jxlRecords.setUseTime(25L);
                                break;
                            case 3 :
                                jxlRecords.setUseTime(23L);
                                break;
                            case 4 :
                                jxlRecords.setUseTime(21L);
                                break;
                            case 5 :
                                jxlRecords.setUseTime(19L);
                                break;
                            case 6 :
                                jxlRecords.setUseTime(17L);
                                break;
                            case 7 :
                                jxlRecords.setUseTime(15L);
                                break;
                            case 8 :
                                jxlRecords.setUseTime(13L);
                                break;
                            case 9 :
                                jxlRecords.setUseTime(11L);
                                break;
                            case 10 :
                                jxlRecords.setUseTime(9L);
                                break;
                        }
                    }else {
                        jxlRecords.setUseTime(99L);
                    }
                    jxlRecords.setInitType(json.getString("type"));
                    jxlRecords.setUpdateTime(CommonUtils.subMonth(CommonUtils.getCurDate("second"), i+1));
                    list.add(jxlRecords);
                    jxlRecordMapper.insertVA_F008(list);
                }
            }
        }
    }

    /**
     * 添加牛大咖AF013中的数据
     * @param userInfoMap
     * @param json
     * @param session
     */
    public void addPhoneCallRecordsForAF013(Map<String,String> userInfoMap, JSONObject json, SqlSession session){
        delCallRecords(userInfoMap.get("oldCustId"), session);
        jxlRecordMapper = session.getMapper(jxl_phone_call_recordsMapper.class);
        jpiService = new jxl_primary_infoService();
        pmaService = new p2p_mobile_addrService();
        pcalService = new p2p_cust_addr_listService();
        pdService = new p2p_dictionaryService() ;
        List<jxl_phone_call_records> list;
        jxl_phone_call_records jxlRecords ;
        String homeCityCode = pdService.getDictCode(new JSONObject(userInfoMap.get("nowAddress")).getString("nowCity"), session);
        //先添加主叫号码的记录
        JSONArray callingJsonArray = new JSONArray(json.get("calling").toString());
        JSONArray calledJsonArray = new JSONArray(json.get("called").toString());
        for (int i = 0; i < callingJsonArray.getJSONObject(0).getInt("homeRecordsNo"); i++) {
            list = new ArrayList<>();
            jxlRecords = new jxl_phone_call_records();
            jxlRecords.setId(UUID.randomUUID().toString().replaceAll("-",""));
            jxlRecords.setPrimaryId(jpiService.getPrimaryId(userInfoMap.get("custId"), session));
            jxlRecords.setCellPhone(new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"));
            jxlRecords.setOtherCellPhone(pmaService.getOtherMobileByAddr(homeCityCode, session) + "111" + i);
            jxlRecords.setCallPlace(null);
            jxlRecords.setStartTime(CommonUtils.subMonth(CommonUtils.getCurDate("second"), 1));
            jxlRecords.setUseTime(10L);
            jxlRecords.setInitType("主叫");
            jxlRecords.setUpdateTime(CommonUtils.getCurDate("second"));
            list.add(jxlRecords);
            jxlRecordMapper.insertVA_F008(list);
        }
        for (int i = 0; i < callingJsonArray.getJSONObject(0).getInt("otherRecordsNo"); i++) {
            list = new ArrayList<>();
            jxlRecords = new jxl_phone_call_records();
            jxlRecords.setId(UUID.randomUUID().toString().replaceAll("-",""));
            jxlRecords.setPrimaryId(jpiService.getPrimaryId(userInfoMap.get("custId"), session));
            jxlRecords.setCellPhone(new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"));
            jxlRecords.setOtherCellPhone(pmaService.getMobileByAddr(homeCityCode, session) + "111" + i);
            jxlRecords.setCallPlace(null);
            jxlRecords.setStartTime(CommonUtils.subMonth(CommonUtils.getCurDate("second"), 1));
            jxlRecords.setUseTime(10L);
            jxlRecords.setInitType("主叫");
            jxlRecords.setUpdateTime(CommonUtils.getCurDate("second"));
            list.add(jxlRecords);
            jxlRecordMapper.insertVA_F008(list);
        }
        //添加被叫号码
        for (int i = 0; i < calledJsonArray.getJSONObject(0).getInt("homeRecordsNo"); i++) {
            list = new ArrayList<>();
            jxlRecords = new jxl_phone_call_records();
            jxlRecords.setId(UUID.randomUUID().toString().replaceAll("-",""));
            jxlRecords.setPrimaryId(jpiService.getPrimaryId(userInfoMap.get("custId"), session));
            jxlRecords.setCellPhone(new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"));
            jxlRecords.setOtherCellPhone(pmaService.getOtherMobileByAddr(homeCityCode, session) + "111" + i);
            jxlRecords.setCallPlace(null);
            jxlRecords.setStartTime(CommonUtils.subMonth(CommonUtils.getCurDate("second"), 1));
            jxlRecords.setUseTime(10L);
            jxlRecords.setInitType("被叫");
            jxlRecords.setUpdateTime(CommonUtils.getCurDate("second"));
            list.add(jxlRecords);
            jxlRecordMapper.insertVA_F008(list);
        }
        for (int i = 0; i < calledJsonArray.getJSONObject(0).getInt("otherRecordsNo"); i++) {
            list = new ArrayList<>();
            jxlRecords = new jxl_phone_call_records();
            jxlRecords.setId(UUID.randomUUID().toString().replaceAll("-",""));
            jxlRecords.setPrimaryId(jpiService.getPrimaryId(userInfoMap.get("custId"), session));
            jxlRecords.setCellPhone(new JSONObject(userInfoMap.get("phoneAuth")).getString("mobile"));
            jxlRecords.setOtherCellPhone(pmaService.getMobileByAddr(homeCityCode, session) + "111" + i);
            jxlRecords.setCallPlace(null);
            jxlRecords.setStartTime(CommonUtils.subMonth(CommonUtils.getCurDate("second"), 1));
            jxlRecords.setUseTime(10L);
            jxlRecords.setInitType("被叫");
            jxlRecords.setUpdateTime(CommonUtils.getCurDate("second"));
            list.add(jxlRecords);
            jxlRecordMapper.insertVA_F008(list);
        }
    }

    /**
     * 根据primaryId删除删除jxl_phone_call_records表中的信息
     * @param oldCustId
     * @param session
     */
    public void delCallRecords(String oldCustId, SqlSession session){
        jxlRecordMapper = session.getMapper(jxl_phone_call_recordsMapper.class);
        jpiService = new jxl_primary_infoService();
        try {
            jxlRecordMapper.deleteByPrimaryId(jpiService.getPrimaryId(oldCustId, session));
        }catch (Exception e) {
            Reporter.log("根据primaryId: " + jpiService.getPrimaryId(oldCustId, session) +"删除jxl_phone_call_records表中的信息时发生异常" + e.getMessage());
        }
    }

    @Test
    public void test(){

    }
}
