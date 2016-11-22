package jspetrinet.ast;

abstract public class ASTBinary implements AST {

	private final AST lhs;
	private final AST rhs;
	
	public ASTBinary(AST left, AST right) {
		lhs = left;
		rhs = right;
	}
	
	public AST getLeft() {
		return lhs;
	}
	
	public AST getRight() {
		return rhs;
	}
}
