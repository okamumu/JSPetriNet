package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public enum MATLABDataType implements MATLABbyteIF {
	miINT8(1),
	miUINT8(2),
	miINT16(3),
	miUINT16(4),
	miINT32(5),
	miUINT32(6),
	miSINGLE(7),
	miDOUBLE(9),
	miINT64(12),
	miUINT64(13),
	miMATRIX(14),
	miCOMPRESSED(15),
	miUTF16(16),
	miUTF32(17),
	miUTF64(18),
	;
	
	private final Byte4 id;
	
	private MATLABDataType(int id) {
		this.id = new Byte4(id);
	}
	
	public Byte4 getID() {
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

