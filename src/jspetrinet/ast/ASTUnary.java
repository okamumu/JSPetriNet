package jspetrinet.ast;

import jspetrinet.exception.JSPNException;
import jspetrinet.exception.TypeMismatch;

public final class ASTUnary implements AST {

	private final AST child;
	private final String op;
	private Object res;
	
	public ASTUnary(AST child, String op) {
		this.child = child;
		this.op = op;
	}
	
//	public ASTree getChild() {
//		return child;
//	}

	private final Object plus() throws JSPNException {
		if (res instanceof Integer) {
			return (Integer) res;
		} else if (res instanceof Double) {
			return (Double) res;
		} else {
			throw new TypeMismatch();
		}
	}
	
	private final Object minus() throws JSPNException {
		if (res instanceof Integer) {
			return -((Integer) res);
		} else if (res instanceof Double) {
			return -((Double) res);
		} else {
			throw new TypeMismatch();
		}
	}

	private Object not() throws JSPNException {
		if (res instanceof Boolean) {
			return !((Boolean) res);
		} else {
			throw new TypeMismatch();
		}
	}

	@Override
	public Object eval(ASTEnv m) throws JSPNException {
		res = this.child.eval(m);
		
		switch(op) {
		case "+":
			return plus();
		case "-":
			return minus();
		case "!":
			return not();
		default:
			throw new TypeMismatch();
		}
	}
	
	@Override
	public String toString() {
		return "(" + op + this.child.toString() + ")";
	}
}

