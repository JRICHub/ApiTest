/**
 * 
 */
package com.fkapi.utils;

import java.util.Random;

import com.fkapi.reTry.RetryListener;
import com.fkapi.reTry.TestngListener;
import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * @author Administrator
 *
 */
@Listeners({ AssertionListener.class, RetryListener.class, TestngListener.class})

public class test {
	int i = 1;
	@Test
	public void f(){
		Assertion.verifyTure(true);
	}
	@Test
	public void f1(){
		Assertion.verifyTure(false);

		System.out.print("11111111111111");
	}
}
