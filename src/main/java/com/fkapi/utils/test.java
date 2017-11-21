/**
 * 
 */
package com.fkapi.utils;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import sun.plugin2.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
@Listeners({ AssertionListener.class })

public class test {

	@Test
	public void f(){
		String a = "31011111";
		System.out.println(System.currentTimeMillis());
	}
}
