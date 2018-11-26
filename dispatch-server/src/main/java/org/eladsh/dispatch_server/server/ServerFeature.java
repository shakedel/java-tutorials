package org.eladsh.dispatch_server.server;

import java.util.Comparator;

public interface ServerFeature {

	public enum Architecture implements ServerFeature {
		X86, AIX, AMD
	}
	
	public enum GPU implements Comparable<GPU> {
		NONE(0), M60(1), P100(2), K80(3), K2(4);
		
		private final float rank;
		
		private GPU(float rank) {
			this.rank = rank;
		}
		
		public static Comparator<GPU> comparator = new Comparator<ServerFeature.GPU>() {
			@Override
			public int compare(GPU o1, GPU o2) {
				return Float.compare(o1.rank, o2.rank);
			}
			
		};
		
	}

	public enum Memory {
		GB8(8), GB16(16), GB32(32), GB64(64);
		
		public final int memGb;
		private Memory(int memGb) {
			this.memGb = memGb;
		}
	}

	public enum OS {
		WINDOWS, LINUX, IOS
	}

}
