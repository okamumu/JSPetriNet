package jspetrinet.exception;

public class NotFindObjectException extends JSPNException {

	public NotFindObjectException(String msg) {
		super(JSPNExceptionType.NOT_FIND, msg);
	}

}
