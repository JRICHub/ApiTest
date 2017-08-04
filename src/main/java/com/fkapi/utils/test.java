/**
 * 
 */
package com.fkapi.utils;

import java.util.Random;

import com.fkapi.reTry.RetryListener;
import com.fkapi.reTry.TestngListener;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * @author Administrator
 *
 */
@Listeners({ AssertionListener.class })

public class test {

	@Test
	public void f(){
		Assertion.verifyTure(true);
	}

	@Test
	public void f1(){
		System.out.print("11111111111111");
		Assert.assertTrue(false);
	}
}
