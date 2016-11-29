package jspetrinet.ast;

public class ASTValue implements AST {
	
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

	public ASTValue(Integer value) {
		this.value = value;
	}

	public ASTValue(Boolean value) {
		this.value = value;
	}

	public ASTValue(Double value) {
		this.value = value;
	}

	public ASTValue(String value) {
		this.value = value;
	}

	@Override
	public Object eval(ASTEnv m) {
		return value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
