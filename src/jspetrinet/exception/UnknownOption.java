package jspetrinet.exception;

public class UnknownOption extends JSPNException {

	public UnknownOption(String msg) {
		super(JSPNExceptionType.UNKNOWN_OPTION, msg);
	}

}
