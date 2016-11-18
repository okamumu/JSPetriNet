package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public class Byte8 implements MATLABbyteIF {

	private long data;
	
	public Byte8(long x) {
		data = x;
	}

	public long get() {
		return data;
	}

	public long getByteNum() {
		return 8;
	}

	public void write(DataOutputStream dos) throws IOException {
		dos.writeLong(data);
	}

	@Override
	public String toString() {
		return String.format("0x%016x", data);
	}
}
