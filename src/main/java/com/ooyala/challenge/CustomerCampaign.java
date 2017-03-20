package com.ooyala.challenge;

public class CustomerCampaign implements Comparable<CustomerCampaign> {

	private final String name;
	private final int impressions;
	private final int price;

	CustomerCampaign(String name, int impressions, int price) {
		this.name = name;
		this.impressions = impressions;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getImpressions() {
		return impressions;
	}

	public int getPrice() {
		return price;
	}

	/**
	 * Comparator to compare ratios which uses the fact that a ratio a/b is >
	 * c/d iff (a * d) > (b * c)
	 */
	@Override
	public int compareTo(CustomerCampaign other) {
		long prod1 = (long) this.impressions * other.price;
		long prod2 = (long) other.impressions * this.price;

		return Long.compare(prod1, prod2);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CustomerCampaign)) {
			return false;
		}
		CustomerCampaign other = (CustomerCampaign) obj;
		return this.name.equals(other.name);
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

}
