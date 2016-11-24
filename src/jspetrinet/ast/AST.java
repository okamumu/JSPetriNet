package jspetrinet.ast;

import jspetrinet.exception.*;

public interface AST {

	public static AST getAST(Object obj) {
		if (obj == null) {
			return new ASTNull();
		} else if (obj instanceof Integer) {
			return new ASTValue((Integer) obj);
		} else if (obj instanceof Double) {
			return new ASTValue((Double) obj);
		} else if (obj instanceof Boolean) {
			return new ASTValue((Boolean) obj);
		} else if (obj instanceof ASTNaN) {
			return ((ASTNaN) obj).getValue();
		} else {
			return new ASTNull();
		}
	}

	Object eval(ASTEnv env) throws JSPNException;

}
