package jspetrinet.ast;

import java.util.ArrayList;

import jspetrinet.exception.JSPNException;

public class ASTList extends ArrayList<AST> implements AST {

	private static final long serialVersionUID = 8936972023576027928L;

	public ASTList() {}
	
	@Override
	public Object eval(ASTEnv env) throws JSPNException {
		// TODO: for ASTNaN
		Object obj = null;
		for (AST a : this) {
			obj = a.eval(env);
		}
		return obj;
	}

	@Override
	public String toString() {
		String ret = "";
		for (AST a : this) {
			ret += a.toString() + ";";
		}
		return ret;
	}
}
