package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MATLABString implements MATLABbyteIF {

	private final byte[] data;
	private final MATLABDataType type;
	private final Byte4 length;

	MATLABString(String x) {
		int len = x.length();
		int padding = (8 - x.length() % 8) % 8;
		data = Arrays.copyOf(x.getBytes(), len + padding);
		type = MATLABDataType.miINT8;
		length = new Byte4(len);
	};
	
	@Override
	public String toString() {
		String ret = "0x";
		for (int i=0; i<data.length; i++) {
			ret += String.format("%02x", data[i]);
		}
		return ret;
	}

	@Override
	public long getByteNum() {
		return type.getByteNum() + length.getByteNum() + data.length;
	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		type.write(dos);
		length.write(dos);
		dos.write(data);
	}
}
