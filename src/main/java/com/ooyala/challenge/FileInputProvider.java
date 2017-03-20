package com.ooyala.challenge;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;

public class FileInputProvider {
	private final File file;

	public FileInputProvider(String fileName) {
		Validate.notBlank(fileName);
		this.file = new File(fileName);
		validateFileExists(file);
	}

	public MonthlyScenario parseInput() throws IOException {
		List<String> fileContent = FileUtils.readLines(file, Charset.defaultCharset());

		int inventory = parseInventory(fileContent.get(0));
		List<CustomerCampaign> customers = new ArrayList<>();

		for (int i = 1; i < fileContent.size(); i++) {
			String line = fileContent.get(i);
			String[] parts = extractLineParts(line);
			customers.add(new CustomerCampaign(parts[0].trim(), parseImpressions(parts[1]), parsePrice(parts[2])));
		}

		MonthlyScenario scenario = new MonthlyScenario(customers, inventory);
		return scenario;
	}

	private String[] extractLineParts(String line) {
		Validate.notBlank(line, "Input line in file cannot be empty");
		String[] parts = line.split(",");

		Validate.notNull(parts, "A Line in the input file cannot be null");
		Validate.isTrue(parts.length == 3,
				"A line in the input file should contain exactly three comma separated parts");

		Validate.notBlank(parts[0], "Customer cannot be empty");
		Validate.notBlank(parts[1], "Impressions cannot be empty");
		Validate.notBlank(parts[2], "Campaign Price cannot be empty");
		return parts;
	}

	private int parsePrice(String word) {
		Validate.notBlank(word, "Price cannot be null or empty");

		int price = Integer.valueOf(word.trim());
		Validate.isTrue(price >= 0, "Price cannot be negative");

		return price;

	}

	private int parseImpressions(String word) {
		Validate.notBlank(word, "Impressions cannot be null or empty");

		int imps = Integer.valueOf(word.trim());
		Validate.isTrue(imps >= 0, "Impressions shoule be a positive number");

		return imps;
	}

	private int parseInventory(String line) {
		Validate.notBlank(line, "Inventory value cannot be blank");

		int inv = Integer.valueOf(line.trim());
		Validate.isTrue(inv > 0, "Inventory should be a positive number");

		return inv;
	}

	private void validateFileExists(File file) {
		if (!file.exists()) {
			throw new IllegalArgumentException("Input file does not exist");
		}
	}

}
