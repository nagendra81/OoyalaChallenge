package com.ooyala.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ooyala.challenge.util.GCDUtil;

public class MonthlyScenario {
	private final List<CustomerCampaign> customers;
	private final int inventory;

	public MonthlyScenario(List<CustomerCampaign> customers, int inventory) {
		this.customers = customers;
		this.inventory = inventory;
	}

	/**
	 * Solve the campaign problem using Dynamic Programming technique similar to
	 * the Knapsack problem. Runs in pseudo-polynomial time. Hopes to improve
	 * running time by dividing the impressions by its GCD before computing the
	 * recurrence
	 * 
	 */
	public Map<CustomerCampaign, Integer> computeOptimalSolution() {
		List<Integer> customerImpressions = extractImpressionsFromCustomers();
		customerImpressions.add(inventory);

		int gcd = GCDUtil.computeGcd(customerImpressions);
		int gcdInventory = inventory / gcd;
		List<CustomerCampaign> gcdCustomers = getGcdReducedCustomers(gcd);

		int[] solutionMemo = solveDPReccurence(gcdCustomers, gcdInventory);
		Map<CustomerCampaign, Integer> solution = backtraceSolution(solutionMemo, gcdCustomers, gcdInventory);

		return solution;

	}

	private int[] solveDPReccurence(List<CustomerCampaign> gcdCustomers, int gcdInventory) {

		Collections.sort(gcdCustomers);
		int N = gcdCustomers.size();

		int[] dpMemo = new int[gcdInventory + 1];
		int[] solution = new int[gcdInventory + 1];

		Arrays.fill(solution, -1);

		dpMemo[0] = 0;

		for (int i = 1; i <= gcdInventory; i++) {
			int max = Integer.MIN_VALUE;
			for (int j = 0; j < N; j++) {
				int impressions = gcdCustomers.get(j).getImpressions();
				int price = gcdCustomers.get(j).getPrice();
				if (i - impressions >= 0 && (price + dpMemo[i - impressions]) > max) {
					max = price + dpMemo[i - impressions];
					solution[i] = j;
				}
			}
			if (max != Integer.MIN_VALUE)
				dpMemo[i] = max;
		}

		return solution;

	}

	private Map<CustomerCampaign, Integer> backtraceSolution(int[] solutionMemo, List<CustomerCampaign> gcdCustomers,
			int gcdInventory) {

		Map<CustomerCampaign, Integer> solution = new HashMap<>();

		int inv = gcdInventory;
		while (inv > 0 && solutionMemo[inv] >= 0) {
			int k = solutionMemo[inv];
			increment(solution, gcdCustomers.get(k));
			inv = inv - gcdCustomers.get(k).getImpressions();
		}

		return solution;
	}

	private List<CustomerCampaign> getGcdReducedCustomers(int gcd) {
		List<CustomerCampaign> gcdCustomers = new ArrayList<>();

		for (CustomerCampaign customer : customers) {
			gcdCustomers.add(
					new CustomerCampaign(customer.getName(), customer.getImpressions() / gcd, customer.getPrice()));
		}

		return gcdCustomers;
	}

	public String formatResult(Map<CustomerCampaign, Integer> result) {
		StringBuilder sb = new StringBuilder();
		int totalQuantity = 0;
		int totalRevenue = 0;

		for (CustomerCampaign cu : customers) {
			Integer val = result.get(cu);
			if (val == null) {
				val = 0;
			}

			int impressions = cu.getImpressions() * val;
			int price = cu.getPrice() * val;
			sb.append(cu.getName());
			sb.append(",");
			sb.append(val);
			sb.append(",");
			sb.append(impressions);
			sb.append(",");
			sb.append(price);
			sb.append(System.lineSeparator());

			totalQuantity += impressions;
			totalRevenue += price;

		}

		sb.append(totalQuantity);
		sb.append(",");
		sb.append(totalRevenue);

		return sb.toString();
	}

	private void increment(Map<CustomerCampaign, Integer> result, CustomerCampaign customer) {
		Integer val = result.get(customer);
		if (val == null)
			val = 0;
		result.put(customer, val + 1);
	}

	private List<Integer> extractImpressionsFromCustomers() {
		List<Integer> prices = new ArrayList<>();

		for (CustomerCampaign customer : customers) {
			prices.add(customer.getImpressions());
		}

		return prices;

	}

}
