package org.eladsh.dispatch_server.job;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.eladsh.dispatch_server.server.ServerConfig;
import org.eladsh.dispatch_server.server.ServerFeature.Architecture;
import org.eladsh.dispatch_server.server.ServerFeature.GPU;
import org.eladsh.dispatch_server.server.ServerFeature.Memory;
import org.eladsh.dispatch_server.server.ServerFeature.OS;

import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

public class Predicates {
	
	@Value
	@ToString
	private static class ArchitecturePredicate implements Predicate<ServerConfig> {
		@NonNull Predicate<Architecture> p;
		
		@Override
		public boolean test(ServerConfig t) {
			return p.test(t.getArchitecture());
		}
		
	}
	@Value
	@ToString
	private static class MemoryPredicate implements Predicate<ServerConfig> {
		@NonNull Predicate<Memory> p;
		
		@Override
		public boolean test(ServerConfig t) {
			return p.test(t.getMemory());
		}
		
	}
	@Value
	@ToString
	private static class OsPredicate implements Predicate<ServerConfig> {
		@NonNull Predicate<OS> p;
		
		@Override
		public boolean test(ServerConfig t) {
			return p.test(t.getOs());
		}
		
	}
	@Value
	@ToString
	private static class GpuPredicate implements Predicate<ServerConfig> {
		@NonNull Predicate<GPU> p;
		
		@Override
		public boolean test(ServerConfig t) {
			return p.test(t.getGpu());
		}
		
	}

	public static Predicate<ServerConfig> minimumMemory(int memGb) {
		return new MemoryPredicate(new Predicate<Memory>() {
			@Override
			public boolean test(Memory t) {
				return t.memGb >= memGb;
			}
			
			@Override
			public String toString() {
				return "Memory>="+memGb+"GB";
			}
		});
	}
	
	public static Predicate<ServerConfig> osIn(List<OS> os) {
		return new OsPredicate(new Predicate<OS>() {
			@Override
			public boolean test(OS t) {
				return os.contains(t);
			}
			@Override
			public String toString() {
				return "OS in "+Arrays.toString(os.toArray());
			}
		});
	}
	
	public static Predicate<ServerConfig> architectureIs(Architecture arch) {
		return new ArchitecturePredicate(new Predicate<Architecture>() {
			@Override
			public boolean test(Architecture t) {
				return t.equals(arch);
			}
			@Override
			public String toString() {
				return "Architecture="+arch;
			}
		});
	}
	
	public static Predicate<ServerConfig> minimumGpu(GPU gpu) {
		return new GpuPredicate(new Predicate<GPU>() {
			@Override
			public boolean test(GPU t) {
				return GPU.comparator.compare(gpu, t) >= 0;
			}
			@Override
			public String toString() {
				return "GPU>="+gpu;
			}
		});
	}
	
}
