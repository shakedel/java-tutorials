package org.eladsh.dispatch_server.server;

import lombok.NonNull;
import lombok.Value;

@Value
public class ServerConfig {
	@NonNull ServerFeature.Architecture architecture;
	@NonNull ServerFeature.Memory memory;
	@NonNull ServerFeature.OS os;
	@NonNull ServerFeature.GPU gpu;
}
