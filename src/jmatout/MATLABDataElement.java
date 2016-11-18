package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public class MATLABDataElement implements MATLABbyteIF {

	private final MATLABDataType dataType;
	private final Byte4 numOfByte;
	
	public MATLABDataElement(MATLABDataType dataType, Byte4 numOfByte) {
		this.dataType = dataType;
		this.numOfByte = numOfByte;
	}

	public void write(DataOutputStream dos) throws IOException {
		dataType.getID().write(dos);
		numOfByte.write(dos);
	}

	@Override
	public long getByteNum() {
		return dataType.getByteNum() + numOfByte.getByteNum();
	}
}
