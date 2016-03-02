package jspetrinet.ast;

abstract public class ASTUnaryOperator extends ASTree {

	private final ASTree child;
	
	public ASTUnaryOperator(ASTree child) {
		this.child = child;
	}
	
	public ASTree getChild() {
		return child;
	}
}
