package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class MATLABHeader implements MATLABbyteIF {

	private final String text;
	private final Date date;
	private final short version;
	private final String endian;
	
	public MATLABHeader() {
		text = "MATLAB 5.0 MAT-file, Platform: " + System.getProperty("os.name")
			+ ", CREATED on: ";
		date = new Date();
		version = 0x0100;
		endian = "MI";
	}
	
	public void write(DataOutputStream dos) throws IOException {
		byte[] desc = Arrays.copyOf((text + date.toString()).getBytes(), 116);
		dos.write(desc);
		dos.writeInt(0); // subsys data offset
		dos.writeInt(0); // subsys data offset
		dos.writeShort(version);
		dos.write(endian.getBytes());
	}

	@Override
	public long getByteNum() {
		return 128;
	}
}
