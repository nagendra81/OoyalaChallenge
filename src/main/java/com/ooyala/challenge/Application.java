package com.ooyala.challenge;

import java.io.IOException;
import java.util.Map;

public class Application {

	public static void main(String[] args) {
		try {
			FileInputProvider fip = new FileInputProvider(args[0]);

			MonthlyScenario scenario = fip.parseInput();
			Map<CustomerCampaign, Integer> solution = scenario.computeOptimalSolution();

			String result = scenario.formatResult(solution);
			System.out.println(result);

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}
}
