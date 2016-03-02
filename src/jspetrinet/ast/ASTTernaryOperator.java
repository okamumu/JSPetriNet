package jspetrinet.ast;

abstract public class ASTTernaryOperator extends ASTree {

	private final ASTree first;
	private final ASTree second;
	private final ASTree third;
	
	public ASTTernaryOperator(ASTree first, ASTree second, ASTree third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public ASTree getFirst() {
		return first;
	}
	
	public ASTree getSecond() {
		return second;
	}

	public ASTree getThird() {
		return third;
	}
}
