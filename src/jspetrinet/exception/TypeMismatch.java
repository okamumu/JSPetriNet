package jspetrinet.exception;

public class TypeMismatch extends JSPNException {

	public TypeMismatch() {
		super(JSPNExceptionType.TYPE_MISMATCH, "Type mismatch");
	}

}
