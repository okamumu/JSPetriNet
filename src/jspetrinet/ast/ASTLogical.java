package jspetrinet.ast;

import jspetrinet.exception.*;

public final class ASTLogical extends ASTBinary {
	
	private final String op;
	private Object lhs;
	private Object rhs;

	public ASTLogical(AST left, AST right, String op) {
		super(left, right);
		this.op = op;
	}

	public final Object and() throws JSPNException {
		if (lhs instanceof Boolean && rhs instanceof Boolean) {
			return (Boolean) lhs && (Boolean) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	public final Object or() throws JSPNException {
		if (lhs instanceof Boolean && rhs instanceof Boolean) {
			return (Boolean) lhs || (Boolean) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	@Override
	public final Object eval(ASTEnv m) throws JSPNException {
		lhs = this.getLeft().eval(m);
		rhs = this.getRight().eval(m);
		
		if (lhs instanceof String || rhs instanceof String) {
			return "(" + lhs.toString() + op + rhs.toString() + ")";
		}

		switch(op) {
		case "&&":
			return and();
		case "||":
			return or();
		default:
			throw new TypeMismatch();
		}
	}

}
