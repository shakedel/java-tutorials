package org.eladsh.tf_idf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import lombok.Value;
import lombok.val;

public class JointEnthropy {

	@Value
	private static class X {
		int a;
		int b;
	}

	
	@Value
	private static class Sample {
		X x;
		int y;
	}
	
	private static List<X> xStates = genXStates();
	private static List<Sample> sampleStates = genSampleStates();
	
	private static Double je(int[] a, int[] b, int[] labels) {
		if (a.length != b.length || a.length != labels.length) {
			throw new IllegalArgumentException();
		}
		
		Multiset<X> xs = HashMultiset.create();
		Multiset<Sample> samples = HashMultiset.create();
		for (int i=0; i<a.length; i++) {
			val x = new X(a[i], b[i]);
			val sample = new Sample(x, labels[i]);
			
			xs.add(x);
			samples.add(sample);
		}
		
		val xProb = toProb(xs, xStates);
		val sampleProb = toProb(samples, sampleStates);
		double sum = 0;
		
		for (Sample sample: sampleStates) {
			val sp = sampleProb.get(sample);
			if (sp == 0.0) {
				continue;
			}
			sum += sp*(Math.log(xProb.get(sample.x)/sp)/Math.log(2));
		}
		return sum;
	}

	private static <R> Map<R, Double> toProb(Multiset<R> ms, List<R> possibleRs) {
		val res = new HashMap<R, Double>();
		int count = Tfidf.sumCount(ms);
		for (R r: possibleRs) {
			res.put(r, ((double) ms.count(r))/count);
		}
		return res;
	}
	
	private static List<Sample> genSampleStates() {
		val res = new ArrayList<Sample>(4);
		for (int a=0; a<2; a++) {
			for (int b=0; b<2; b++) {
				for (int l=0; l<2; l++) {
					res.add(new Sample(new X(a, b), l));
				}
			}
		}
		return res;
	}

	private static List<X> genXStates() {
		val res = new ArrayList<X>(4);
		for (int a=0; a<2; a++) {
			for (int b=0; b<2; b++) {
				res.add(new X(a, b));
			}
		}
		return res;
	}

	public static void main(String[] args) {
		int[] all1 = new int[16];
		Arrays.fill(all1, 1);
		
		int[] all0 = new int[16];
		Arrays.fill(all1, 0);

		int[] labels = {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0};
		
		System.out.println(je(all0, all0, labels));
		System.out.println(je(all0, labels, labels));
		System.out.println(je(all1, new int[]{0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0}, labels));
		System.out.println(je(all0, new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, labels));
		System.out.println(je(new int[]{0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0}, new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, labels));
		System.out.println(je(new int[]{1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0}, new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, labels));


		
		
		
	}
}
