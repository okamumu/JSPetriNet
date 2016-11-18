package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public class MATLABDoubleMatrix implements MATLABbyteIF {

	private final MATLABDataElement tag;
	private final MATLABArrayFlags arrayFlags;
	private final MATLABIntArray dimensionArray;
	private final MATLABString arrayName;
	private final MATLABDoubleArray pr;
	
	public MATLABDoubleMatrix(String name, int[] dims, double[] pr) {
		arrayFlags = new MATLABArrayFlags(MATLABArrayType.mxDOUBLE_CLASS, false, false, false);
		dimensionArray = new MATLABIntArray(dims);
		arrayName = new MATLABString(name);
		this.pr = new MATLABDoubleArray(pr);
		tag = new MATLABDataElement(MATLABDataType.miMATRIX,
				new Byte4(arrayFlags.getByteNum()
						+ dimensionArray.getByteNum()
						+ arrayName.getByteNum()
						+ this.pr.getByteNum()));
	}
	
	public long getByteNum() {
		return tag.getByteNum()
				+ arrayFlags.getByteNum()
				+ dimensionArray.getByteNum()
				+ arrayName.getByteNum()
				+ pr.getByteNum();
	}
	
	public void write(DataOutputStream dos) throws IOException {
		tag.write(dos);
		arrayFlags.write(dos);
		dimensionArray.write(dos);
		arrayName.write(dos);
		pr.write(dos);
	}
}
