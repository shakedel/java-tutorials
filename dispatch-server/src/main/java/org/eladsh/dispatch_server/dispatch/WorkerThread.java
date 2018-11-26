package org.eladsh.dispatch_server.dispatch;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.eladsh.dispatch_server.job.Job;
import org.eladsh.dispatch_server.server.Server;

import lombok.val;

public class WorkerThread extends Thread {
	
	public static final long baseMillis = new Date().getTime();
	
	private final Server server;
	private final LinkedBlockingQueue<QueueJob> q;
	private final AtomicInteger jobCounter;
	
	public WorkerThread(Server server, LinkedBlockingQueue<QueueJob> q, AtomicInteger jobCounter) {
		super(server.getId());
		this.server = server;
		this.q = q;
		this.jobCounter = jobCounter;
	}
	
	@Override
	public void run() {
		QueueJob qJob;
		while (true) {
			try {
				System.out.println("server "+server.id+" is waiting on queue");
				qJob = q.take();
				System.out.println("server "+server.id+" got job "+qJob.getJob());
			} catch(InterruptedException e) {
				if (q.isEmpty()) {
					/* empty queue and interrupt -> exit */
					System.out.println("Thread interrupted with empty queue");
					break;
				} else {
					throw new RuntimeException("Thread interrupted with queue size:"+q.size());
				}
			}
			if (!qJob.lock.tryLock()) {
				/* job is already locked drop and continue */
				continue;
			} else {
				try {
					this.processJob(qJob.getJob());
					
					synchronized (this.jobCounter) {
						val numJobs = this.jobCounter.decrementAndGet();
						System.out.println(numJobs+" left after one job finished");
						this.jobCounter.notify();
					}
				} catch (InterruptedException e) {
					throw new RuntimeException("Job Interrupted!", e);
				}
			}
		}
		
	}

	private void processJob(Job job) throws InterruptedException {
		long millis = job.duration * 100;
		val start = new Date();
		Thread.sleep(millis);
//		synchronized(job) {
//			job.wait(millis);
//		}
		val end = new Date();
		System.out.println(job+"; "+server+"; "+(start.getTime()-baseMillis)/100+"-"+(end.getTime()-baseMillis)/100);
	}
	
}
