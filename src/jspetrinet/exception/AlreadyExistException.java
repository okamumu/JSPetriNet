package jspetrinet.exception;

public class AlreadyExistException extends JSPNException {

	public AlreadyExistException(String msg) {
		super(JSPNExceptionType.ALREADY_EXIST, msg);
	}

}
