package com.ooyala.test;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ooyala.challenge.util.GCDUtil;

import junit.framework.Assert;

public class GcdTest {

	@Test(dataProvider = "dp")
	public void testGcd(List<Integer> values, int expected) {

		int actual = GCDUtil.computeGcd(values);
		Assert.assertEquals(expected, actual);
	}

	@DataProvider
	public Object[][] dp() {
		// @formatter:off
		return new Object[][] { 
			{ Arrays.asList(35, 49), 7 }, 
			{ Arrays.asList(23, 37), 1 },
			{ Arrays.asList(32356000, 2000000, 3500000), 4000 },
			
		};
		// @formatter:on
	}
}
