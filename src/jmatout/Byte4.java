package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Byte4 implements MATLABbyteIF {

	private final int data;
	
	public Byte4(long x) {
		data = (int) (x & 0x00000000ffffffffL);
	}

	public long getByteNum() {
		return 4;
	}

	public void write(DataOutputStream dos) throws IOException {
		dos.writeInt(data);
	}

	@Override
	public String toString() {
		return String.format("0x%08x", data);
	}
}
