package jspetrinet.ast;

public class ASTNaN {

	private AST value;
	
	public ASTNaN(AST value) {
		this.value = value;
	}
	
	public AST getValue() {
		return value;
	}
	
	public ASTNaN setValue(AST value) {
		this.value = value;
		return this;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
