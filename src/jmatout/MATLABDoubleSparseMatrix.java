package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public class MATLABDoubleSparseMatrix implements MATLABbyteIF {

	private final MATLABDataElement tag;
	private final MATLABArrayFlags arrayFlags;
	private final MATLABIntArray dimensionArray;
	private final MATLABString arrayName;
	private final MATLABIntArray ir;
	private final MATLABIntArray jc;
	private final MATLABDoubleArray pr;
	
	public MATLABDoubleSparseMatrix(String name, int[] dims, long nnz, int[] ir, int[] jc, double[] pr) {
		arrayFlags = new MATLABArrayFlags(0x00000000ffffffffL & nnz,
				MATLABArrayType.mxSPARSE_CLASS, false, false, false);
		dimensionArray = new MATLABIntArray(dims);
		arrayName = new MATLABString(name);
		this.ir = new MATLABIntArray(ir);
		this.jc = new MATLABIntArray(jc);
		this.pr = new MATLABDoubleArray(pr);
		tag = new MATLABDataElement(MATLABDataType.miMATRIX,
				new Byte4(arrayFlags.getByteNum()
						+ dimensionArray.getByteNum()
						+ arrayName.getByteNum()
						+ this.ir.getByteNum()
						+ this.jc.getByteNum()
						+ this.pr.getByteNum()));
	}
	
	public long getByteNum() {
		return tag.getByteNum()
				+ arrayFlags.getByteNum()
				+ dimensionArray.getByteNum()
				+ arrayName.getByteNum()
				+ ir.getByteNum()
				+ jc.getByteNum()
				+ pr.getByteNum();
	}
	
	public void write(DataOutputStream dos) throws IOException {
		tag.write(dos);
		arrayFlags.write(dos);
		dimensionArray.write(dos);
		arrayName.write(dos);
		ir.write(dos);
		jc.write(dos);
		pr.write(dos);
	}
}
