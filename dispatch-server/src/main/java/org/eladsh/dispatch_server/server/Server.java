package org.eladsh.dispatch_server.server;

import lombok.Data;
import lombok.val;

@Data
public class Server {
	public final String id;
	public final ServerConfig config;
	
	public String toCsvString() {
		val sb = new StringBuilder();
		val delim = ",";
		sb.append(id);
		sb.append(delim+config.getArchitecture());
		sb.append(delim+config.getGpu());
		sb.append(delim+config.getMemory());
		sb.append(delim+config.getOs());
		return sb.toString();
	}
}
