package com.ooyala.test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ooyala.challenge.FileInputProvider;
import com.ooyala.challenge.MonthlyScenario;

public class MonthlyScenarioTest {

	@Test(dataProvider = "dp")
	public void testMonthlyScenario(String input, String output) throws IOException, URISyntaxException {

		/*
		 * Using URI class to decode the file path as a workaround in case the
		 * directory contains a space which gets encoded as '%20'
		 */

		String inputFile = new URI(getClass().getResource(input).toString()).getPath();
		String outputFile = new URI(getClass().getResource(output).toString()).getPath();
	
		FileInputProvider fip = new FileInputProvider(inputFile);
		MonthlyScenario scenario = fip.parseInput();
		String actual = scenario.formatResult(scenario.computeOptimalSolution());
		String expected = FileUtils.readFileToString(new File(outputFile), Charset.defaultCharset());

		Assert.assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] dp() {
		// @formatter:off
		return new Object[][] { 
			new Object[] { "input1.txt", "output1.txt" },
			new Object[] { "input2.txt", "output2.txt" }, 
			new Object[] { "input3.txt", "output3.txt" }, 
		};
		// @formatter:on
	}
}
