package org.eladsh.pm_tutorial.sparse_matrix;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eladsh.sparse_matrix.App;
import org.eladsh.sparse_matrix.App.SparseMatrix;
import org.junit.Test;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class AppTest {

	private static SparseMatrix loadInitMatrix(String resourcePath) throws RuntimeException, IOException {
		SparseMatrix matrix = new App.SparseMatrixImpl();
		URL url = AppTest.class.getResource(resourcePath);
		
        try (InputStream is = url.openStream()) {
        	CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is)).withSkipLines(1).build();
        	
        	String[] line;
            while ((line = reader.readNext()) != null) {
            	int row = Integer.parseInt(line[0]);
            	int col = Integer.parseInt(line[1]);
            	float val = Float.parseFloat(line[2]);
            	matrix.set(row, col, val);
            }
        } 
        return matrix;
	}
	
	@Test
	public void sanityCheck() {
		assertTrue(true);
	}
	
	@Test
	public void testSparseMatrix() throws IOException {
		SparseMatrix matrix = loadInitMatrix("/matrix_init.csv");
		for (int row=1; row<=10; row++) {
			for (int col=1; col<=10; col++) {
				float actualVal = matrix.get(row, col);
				float expectedVal = (float) row*col;
				assertEquals(actualVal, expectedVal, 0);
			}
		}
				
	}

}
