/**
 * 
 */
package com.fkapi.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.fkapi.datebase.dao.ex_StudentCreditInfoMapper;
import com.fkapi.datebase.domain.ex_StudentCreditInfo;
import com.fkapi.utils.CommonUtils;
import com.fkapi.utils.Log;
import com.fkapi.utils.MybatisUtils;

/**
 * @author Administrator
 *
 */
public class ex_StudentCreditInfoService {
	Log log = new Log(ex_StudentCreditInfoService.class);
	ex_StudentCreditInfoMapper esMapper = null;
	p2p_customerService pcService = new p2p_customerService();
	
	/**
	 * 添加学籍认证信息（未毕业）
	 * @param custId
	 * @param certNo
	 * @param schoolRollAuth：学籍认证信息
	 */
	public void addExStudentInfo(String oldCustId, String custId,String certNo,JSONObject schoolRollAuth, SqlSession session){
		delExStudentInfo(oldCustId, session);
		esMapper = session.getMapper(ex_StudentCreditInfoMapper.class);
		List<ex_StudentCreditInfo> list = new ArrayList<>();
		ex_StudentCreditInfo ex = new ex_StudentCreditInfo();
		ex.setPlatfromuserno(custId);
		ex.setDocumentno(certNo == null?"":certNo);
		ex.setCollege(schoolRollAuth.getString("collegeName") == null?"":schoolRollAuth.getString("collegeName"));
		ex.setColglevel(schoolRollAuth.getString("clogLevel") == null?"":schoolRollAuth.getString("clogLevel"));
		ex.setEntrancetime(schoolRollAuth.getString("entranceTime") == null?null:CommonUtils.StringToDate(schoolRollAuth.getString("entranceTime"), "day"));
		if(schoolRollAuth.getString("clogLevel") != null && schoolRollAuth.getString("clogLevel").equals("本科")){
			ex.setEducational("4  年");
		}else if(schoolRollAuth.getString("clogLevel") != null && schoolRollAuth.getString("clogLevel").equals("专科")){
			ex.setEducational("3  年");
		}else {
			ex.setEducational("");
		}
		ex.setStatus("注册学籍");
		ex.setCreatetime(CommonUtils.getCurDate("second"));
		list.add(ex);
		Reporter.log("添加ex_StudentCreditInfo表的参数为： " + schoolRollAuth.toString());
		log.info("添加ex_StudentCreditInfo表的参数为： " + schoolRollAuth.toString());
		try {
			esMapper.insert(list);
			Reporter.log("添加学籍认证信息为："+ schoolRollAuth.toString() + "的ex_StudentCreditInfo表信息成功");
			log.info("添加学籍认证信息为："+ schoolRollAuth.toString() + "的ex_StudentCreditInfo表信息成功");
		} catch (Exception e) {
			Reporter.log("添加学籍认证信息为："+ schoolRollAuth.toString() + "的ex_StudentCreditInfo表信息时发生异常，添加失败！" + e.getMessage());
			log.info("添加学籍认证信息为："+ schoolRollAuth.toString() + "的ex_StudentCreditInfo表信息时发生异常，添加失败！" + e.getMessage());
		}
	}
	
	/**
	 * 根据custId删除学籍认证信息
	 * @param oldCustId
	 */
	public void delExStudentInfo(String oldCustId, SqlSession session){
		session = MybatisUtils.getFactory().openSession(true);
		esMapper = session.getMapper(ex_StudentCreditInfoMapper.class);
		try {
			esMapper.deleteByCustId(oldCustId);
			//Reporter.log("删除custId为:"+ pcService.getCustID(loginName) +"的ex_StudentCreditInfo表信息成功");
		} catch (Exception e) {
			Reporter.log("删除custId为:"+ oldCustId +"的ex_StudentCreditInfo表信息时发生异常，删除失败" + e.getMessage());
		}
	}
	
	@Test
	public void test(){
		//addExStudentInfo();
	}
	
}
