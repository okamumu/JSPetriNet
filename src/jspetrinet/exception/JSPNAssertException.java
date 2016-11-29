package jspetrinet.exception;

public class JSPNAssertException extends JSPNException {

	public JSPNAssertException(String msg) {
		super(JSPNExceptionType.ASSERTION, msg);
	}

}
