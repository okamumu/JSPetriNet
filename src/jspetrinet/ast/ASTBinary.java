package jspetrinet.ast;

abstract public class ASTBinary implements AST {

	private final AST lhs;
	private final AST rhs;
	protected final String op;
	
	public ASTBinary(AST left, AST right, String op) {
		lhs = left;
		rhs = right;
		this.op = op;
	}
	
	public AST getLeft() {
		return lhs;
	}
	
	public AST getRight() {
		return rhs;
	}

	@Override
	public String toString() {
		return "(" + this.getLeft().toString() + op + this.getRight().toString() + ")";
	}
}
