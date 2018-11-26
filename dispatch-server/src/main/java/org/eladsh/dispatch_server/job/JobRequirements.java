package org.eladsh.dispatch_server.job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.eladsh.dispatch_server.server.ServerConfig;

public class JobRequirements implements Predicate<ServerConfig> {

	private final List<Predicate<ServerConfig>> preds;
	private final Predicate<ServerConfig> globalPred;

	public JobRequirements(List<Predicate<ServerConfig>> preds) {
		this.preds = new ArrayList<>(preds);
		Predicate<ServerConfig> p = (s) -> true;
		for (Predicate<ServerConfig> pred: preds) {
			p = (Predicate<ServerConfig>) p.and(pred);
		}
		this.globalPred = p;
	}

	@Override
	public boolean test(ServerConfig t) {
		return globalPred.test(t);
	}

	@Override
	public String toString() {
		return "JobRequirements [preds=" + Arrays.toString(preds.toArray()) + "]";
	}

}
