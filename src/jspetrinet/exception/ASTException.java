package jspetrinet.exception;

public class ASTException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1180049151304269796L;
	private String msg;
	
	public ASTException(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}
}
