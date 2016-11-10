package jspetrinet.ast;

public class ASTValue extends AST {

	private final Object value;

	public ASTValue(int value) {
		this.value = new Integer(value);
	}

	public ASTValue(boolean value) {
		this.value = new Boolean(value);
	}

	public ASTValue(double value) {
		this.value = new Double(value);
	}

	public ASTValue(String value) {
		this.value = value;
	}

	@Override
	public Object eval(ASTEnv m) {
		return value;
	}

}
