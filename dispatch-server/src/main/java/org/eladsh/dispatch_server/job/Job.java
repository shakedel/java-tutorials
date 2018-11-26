package org.eladsh.dispatch_server.job;

import lombok.Data;

@Data
public class Job {
	public final int id;
	public final int duration;
	public final JobRequirements requirements;
}
