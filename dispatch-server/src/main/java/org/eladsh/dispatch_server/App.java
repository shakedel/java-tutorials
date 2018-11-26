package org.eladsh.dispatch_server;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eladsh.dispatch_server.dispatch.DispatchServer;
import org.eladsh.dispatch_server.server.Generators;

import lombok.val;

public class App {
    public static void main( String[] args ) throws IOException {
    	val gen = new Generators(Generators.SEED);
		
		val servers = IntStream.range(0, Generators.DEFAULT_NUM_SERVERS).mapToObj(__ -> gen.genServer()).collect(Collectors.toList());
		val jobs = IntStream.range(0, Generators.DEFAULT_NUM_JOBS).mapToObj(id -> gen.genJob(id)).collect(Collectors.toList());
		
    	try(DispatchServer dc = new DispatchServer(servers)) {
    		jobs.forEach(job -> {dc.dispatchJob(job);});
    	}
    	
    }
}
