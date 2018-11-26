package org.eladsh.dispatch_server.dispatch;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.eladsh.dispatch_server.job.Job;
import org.eladsh.dispatch_server.server.Server;

import lombok.val;

public class DispatchServer implements Closeable {

	private class ExecutingServer {
		private final Server server;
		private final LinkedBlockingQueue<QueueJob> queue;
		private final WorkerThread thread;
		
		public ExecutingServer(Server server, LinkedBlockingQueue<QueueJob> queue) {
			this.queue = queue;
			this.server = server;
			this.thread = new WorkerThread(server, queue, pendingJobs);
		}
		
	}
	
	private final List<ExecutingServer> xservers;
	private volatile AtomicInteger pendingJobs = new AtomicInteger(0);
	
	
	public DispatchServer(List<Server> servers) {
		this.xservers = servers.stream().map(s -> new ExecutingServer(s, new LinkedBlockingQueue<QueueJob>()))
				.collect(Collectors.toList());
		this.xservers.forEach(xs -> {xs.thread.start();});
	}

	public void dispatchJob(Job job) {
		val qJob = new QueueJob(job);
		
		val validServers = this.xservers.stream().filter(xs -> job.getRequirements().test(xs.server.config)).collect(Collectors.toList());
		if (validServers.isEmpty()) {
			System.out.println("No valid server was found for job: "+job);
			return;
		} else {
			synchronized (pendingJobs) {
				val numJobs = pendingJobs.incrementAndGet();
				System.out.println(numJobs+" existing jobs");
			}
			validServers.stream().map(xs -> xs.queue).forEach(q -> q.add(qJob));
		}
		
	}

	@Override
	public void close() throws IOException {

		synchronized (pendingJobs) {
			while (pendingJobs.get() > 0) {
				try {
					System.out.println("waiting for jobs to finish");
					pendingJobs.wait();
					System.out.println(pendingJobs.get()+" jobs left");
				} catch (InterruptedException e) {
					throw new RuntimeException("Should NEVER happen!", e);
				}
			}
		}
		
		this.xservers.forEach(xs -> {xs.thread.interrupt();});
	}

}
