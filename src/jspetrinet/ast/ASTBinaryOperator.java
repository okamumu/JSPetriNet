package jspetrinet.ast;

abstract public class ASTBinaryOperator extends ASTree {

	private final ASTree lhs;
	private final ASTree rhs;
	
	public ASTBinaryOperator(ASTree left, ASTree right) {
		lhs = left;
		rhs = right;
	}
	
	public ASTree getLeft() {
		return lhs;
	}
	
	public ASTree getRight() {
		return rhs;
	}
}
