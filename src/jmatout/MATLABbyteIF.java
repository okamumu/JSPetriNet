package jmatout;
import java.io.DataOutputStream;
import java.io.IOException;

public interface MATLABbyteIF {

	long getByteNum();
	void write(DataOutputStream dos) throws IOException;

}
