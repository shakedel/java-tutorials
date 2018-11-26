package org.eladsh.sparse_matrix;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class App {
	
	public interface SparseMatrix {
		public abstract void set(int row, int col, float val);
		public abstract float get(int row, int col);
	}
	
	
	public static class SparseMatrixImpl implements SparseMatrix {

		private static Pair<Integer, Integer> to_indices(int row, int col) {
			return new ImmutablePair<Integer, Integer>(row, col);
		}
		
		private final Map<Pair<Integer, Integer>, Float> matrix = new HashMap<Pair<Integer, Integer>, Float>();

		public void set(int row, int col, float val) {
			Pair<Integer, Integer> indices = to_indices(row, col);
			if (val != 0) {
				this.matrix.put(indices, val);
			} else if (this.matrix.get(indices) != null) {
				this.matrix.remove(indices);
			}
			
		}

		public float get(int row, int col) {
			Pair<Integer, Integer> indices = to_indices(row, col);
			Float val = this.matrix.get(indices);
			return null == val ? 0 : val;
		}
	}
}


