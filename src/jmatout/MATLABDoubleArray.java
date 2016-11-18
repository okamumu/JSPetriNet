package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public class MATLABDoubleArray implements MATLABbyteIF {

	private final MATLABDataType type;
	private final Byte4 length;
	private final double[] data;
	
	public MATLABDoubleArray(double[] x) {
		type = MATLABDataType.miDOUBLE;
		data = x;
		length = new Byte4(data.length * 8);
	}
	
	@Override
	public long getByteNum() {
		return type.getByteNum() + length.getByteNum() + data.length * 8;
	}
	
	public void write(DataOutputStream dos) throws IOException {
		type.write(dos);
		length.write(dos);
		for (int i=0; i<data.length; i++) {
			dos.writeDouble(data[i]);
		}
	}
}
