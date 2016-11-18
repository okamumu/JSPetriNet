package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public class MATLABArrayFlags extends Byte8 {

	private static final long complexBit = 0x00000700000000L;
	private static final long globalBit = 0x00000400000000L;
	private static final long logicalBit = 0x0000200000000L;
	
	private final MATLABDataType datatype;
	private final Byte4 length;

	public MATLABArrayFlags(MATLABArrayType type, boolean complex, boolean global, boolean logical) {
		super(type.getID().get() | (complex?complexBit:0L) | (global?globalBit:0L) | (logical?logicalBit:0L));
		datatype = MATLABDataType.miUINT32;
		length = new Byte4(8);
	}

	public MATLABArrayFlags(long x, MATLABArrayType type, boolean complex, boolean global, boolean logical) {
		super(x | type.getID().get() | (complex?complexBit:0L) | (global?globalBit:0L) | (logical?logicalBit:0L));
		datatype = MATLABDataType.miUINT32;
		length = new Byte4(8);
	}
	
	@Override
	public long getByteNum() {
		return datatype.getByteNum() + length.getByteNum() + super.getByteNum();
	}

	@Override
	public void write(DataOutputStream dos) throws IOException {
		datatype.write(dos);
		length.write(dos);
		super.write(dos);
	}
}
