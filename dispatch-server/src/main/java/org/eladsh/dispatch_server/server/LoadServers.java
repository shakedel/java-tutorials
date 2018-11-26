package org.eladsh.dispatch_server.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.eladsh.dispatch_server.server.ServerFeature.Architecture;
import org.eladsh.dispatch_server.server.ServerFeature.GPU;
import org.eladsh.dispatch_server.server.ServerFeature.Memory;
import org.eladsh.dispatch_server.server.ServerFeature.OS;

import com.opencsv.CSVReaderBuilder;

import lombok.val;


public class LoadServers {
	public static final String DEFAULT_SERVER_CONFIG_PATH = "/servers.csv";
	
	public static Map<String, ServerConfig> fromResource() throws IOException {
		return fromResource(DEFAULT_SERVER_CONFIG_PATH);
	}
	
	public static Map<String, ServerConfig> fromResource(String resourcePath) throws IOException {
		val servers = new TreeMap<String, ServerConfig>();
		try (InputStream is = LoadServers.class.getResourceAsStream(resourcePath)) {
			val reader = new CSVReaderBuilder(new InputStreamReader(is)).withSkipLines(1).build();
			reader.forEach(row -> {
				val id = row[0];
				val arch = Architecture.valueOf(row[1]);
				val gpu = GPU.valueOf(row[2]);
				val mem = Memory.valueOf(row[3]);
				val os = OS.valueOf(row[4]);
				servers.put(id, new ServerConfig(arch, mem, os, gpu));
			});
		}
		return servers;
	}
	
	
	public static void main(String[] args) throws IOException {
		val servers = fromResource();
		System.out.println(Arrays.toString(servers.entrySet().toArray()));
	}
}
 