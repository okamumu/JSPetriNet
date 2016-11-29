package jspetrinet.ast;

import jspetrinet.exception.JSPNException;

public class ASTNull implements AST {

	public ASTNull() {}
	
	@Override
	public Object eval(ASTEnv env) throws JSPNException {
		return new ASTNaN(this);
	}
	
	@Override
	public String toString() {
		return "";
	}
}
