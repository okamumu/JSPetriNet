package jspetrinet.exception;

public class JSPNException extends Exception {

	private static final long serialVersionUID = -4987117861807445888L;
	private final JSPNExceptionType type;

	public JSPNException(JSPNExceptionType type, String msg) {
		super(msg);
		this.type = type;
	}
	
	public JSPNExceptionType getType() {
		return type;
	}
}
