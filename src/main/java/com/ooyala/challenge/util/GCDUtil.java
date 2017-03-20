package com.ooyala.challenge.util;

import java.util.List;

import org.apache.commons.lang3.Validate;

public class GCDUtil {

	public static int computeGcd(List<Integer> values) {
		Validate.notEmpty(values, "Parameter cannot be empty or null");

		int res = values.get(0);
		for (int i = 1; i < values.size(); i++) {
			res = gcd(res, values.get(i));
		}
		return res;
	}

	private static int gcd(int p, int q) {
		if (q == 0) {
			return p;
		} else {
			return gcd(q, p % q);
		}
	}
}
