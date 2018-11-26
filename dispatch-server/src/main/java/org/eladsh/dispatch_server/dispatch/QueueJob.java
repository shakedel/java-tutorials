package org.eladsh.dispatch_server.dispatch;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eladsh.dispatch_server.job.Job;

import lombok.Getter;

class QueueJob {
	@Getter
	private final Job job;
	public final Lock lock = new ReentrantLock();
	
	public QueueJob(Job job) {
		this.job = job;
	}
}