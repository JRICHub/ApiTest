/**
 * 
 */
package com.fkapi.utils;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

/**
 * @author Administrator
 * 
 */
public class GetExcelConfig {
	public Map<String, String> getConfigData(String type) {
		Map<String, String> map = new HashMap<>();
		int allRowNum = 0, allColNum = 0;
		try {
			allRowNum = ExcelUtils.getAllRowNum(System.getProperty("user.dir")
					+ "\\testcase.xlsx", "config");
			allColNum = ExcelUtils.getAllColNum(System.getProperty("user.dir")
					+ "\\testcase.xlsx", "config", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < allRowNum-1; i++) {
			for (int j = 0; j < allColNum; j++) {
				if(ExcelUtils.getCellDate(i+1, 0).equals("1") && ExcelUtils.getCellDate(i+1, allColNum-1).equals(type)){
					map.put(ExcelUtils.getCellDate(0, j),
							ExcelUtils.getCellDate(i+1, j));
				}
			}
		}
		return map;
	}
	
	@Test
	public void test(){
		getConfigData("");
	}
}
