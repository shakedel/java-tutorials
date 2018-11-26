package org.eladsh.dispatch_server.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import lombok.val;

public class WeightedRand {
	public static <R> R weightedRand(Random rand, int[] weights, R[] items) {
		if (weights.length != items.length) {
			throw new IllegalArgumentException("weights and items length must be the same but are "+weights.length+" and "+items.length);
		}
		val len = weights.length;
		val l = new ArrayList<R>(Arrays.stream(weights).sum());
		for (int i=0; i<len; i++) {
			val curItem = items[i];
			for (int j=0; j<weights[i]; j++) {
				l.add(curItem);
			}
		}
		return l.get(rand.nextInt(l.size()));
	}
	
	public static <R> R weightedRand(int[] weights, R[] items) {
		val rand = new Random();
		return weightedRand(rand, weights, items);
	}
}
