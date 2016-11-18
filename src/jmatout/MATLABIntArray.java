package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MATLABIntArray implements MATLABbyteIF {

	private final int[] data;
	private final MATLABDataType type;
	private final Byte4 length;

	public MATLABIntArray(int[] x) {
		int len = x.length;
		int padding = x.length % 2;
		data = Arrays.copyOf(x, len + padding);
		type = MATLABDataType.miINT32;
		length = new Byte4(len * 4);
	}
	
	@Override
	public String toString() {
		String ret = "0x";
		for (int i=0; i<data.length; i++) {
			ret += String.format("%04x", data[i]);
		}
		return ret;
	}

	@Override
	public long getByteNum() {
		return type.getByteNum() + length.getByteNum() + data.length * 4;
	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		type.write(dos);
		length.write(dos);
		for (int i=0; i<data.length; i++) {
			dos.writeInt(data[i]);
		}
	}
}
