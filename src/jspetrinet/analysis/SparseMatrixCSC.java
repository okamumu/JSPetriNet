package jspetrinet.analysis;

import java.io.DataOutputStream;
import java.io.IOException;

import jmatout.MATLABDoubleSparseMatrix;

public class SparseMatrixCSC {
	
	private final int nrow;
	private final int ncol;
	private final int nnz;

	private final String name;
	private final int[] colptr;
	private final int[] rowind;
	private final double[] value;
	
	private int z;
	private int prevj;

	public SparseMatrixCSC(String name, int nrow, int ncol, int nnz) {
		this.name = name;
		this.nrow = nrow;
		this.ncol = ncol;
		this.nnz = nnz;
		colptr = new int [ncol+1];
		rowind = new int [nnz];
		value = new double [nnz];
		z = 0;
		prevj = -1;
	}
	
	public String getName() {
		return name;
	}
	
	public void set(int i, int j, double v) {
		if (j != prevj) {
			colptr[j] = z;
			prevj = j;
		}
		rowind[z] = i;
		value[z] = v;
		z++;
		if (z == nnz) {
			colptr[j+1] = z;
		}
	}
	
	public void write(DataOutputStream dos) throws IOException {
		MATLABDoubleSparseMatrix matlab = new MATLABDoubleSparseMatrix(name, new int[] {nrow, ncol}, nnz, rowind, colptr, value);
		matlab.write(dos);
	}
}
