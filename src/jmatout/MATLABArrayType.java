package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public enum MATLABArrayType implements MATLABbyteIF {
	mxCELL_CLASS(1),
	mxSTRUCT_CLASS(2),
	mxOBJECT_CLASS(3),
	mxCHAR_CLASS(4),
	mxSPARSE_CLASS(5),
	mxDOUBLE_CLASS(6),
	mxSINGLE_CLASS(7),
	mxINT8_CLASS(8),
	mxUINT8_CLASS(9),
	mxINT16_CLASS(10),
	mxUINT16_CLASS(11),
	mxINT32_CLASS(12),
	mxUINT32_CLASS(13),
	mxINT64_CLASS(14),
	mxUINT64_CLASS(15),
	;

	private final Byte8 id;
	
	private MATLABArrayType(int id) {
		long x = id;
		x <<= 32;
		this.id = new Byte8(x);
	}
	
	public Byte8 getID() {
		return id;
	}

	@Override
	public long getByteNum() {
		return id.getByteNum();
	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		id.write(dos);
	}	
}
