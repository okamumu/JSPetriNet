package jspetrinet.ast;

import jspetrinet.exception.*;

public final class ASTArithmetic extends ASTBinary {
	
	private final String op;
	private Object lhs;
	private Object rhs;

	public ASTArithmetic(AST left, AST right, String op) {
		super(left, right);
		this.op = op;
	}

	private final Object plus() throws ASTException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return (Integer) lhs + (Integer) rhs;
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return (Integer) lhs + (Double) rhs;
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return (Double) lhs + (Integer) rhs;
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return (Double) lhs + (Double) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	private final Object minus() throws ASTException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return (Integer) lhs - (Integer) rhs;
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return (Integer) lhs - (Double) rhs;
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return (Double) lhs - (Integer) rhs;
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return (Double) lhs - (Double) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	private final Object multi() throws ASTException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return (Integer) lhs * (Integer) rhs;
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return (Integer) lhs * (Double) rhs;
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return (Double) lhs * (Integer) rhs;
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return (Double) lhs * (Double) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	private final Object divide() throws ASTException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return ((Integer) lhs).doubleValue() / (Integer) rhs;
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return (Integer) lhs / (Double) rhs;
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return (Double) lhs / (Integer) rhs;
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return (Double) lhs / (Double) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	private final Object mod() throws ASTException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return (Integer) lhs / (Integer) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	@Override
	public final Object eval(ASTEnv m) throws ASTException {
		lhs = this.getLeft().eval(m);
		rhs = this.getRight().eval(m);
		
		if (lhs instanceof String || rhs instanceof String) {
			return "(" + lhs.toString() + op + rhs.toString() + ")";
		}

		switch(op) {
		case "+":
			return plus();
		case "-":
			return minus();
		case "*":
			return multi();
		case "/":
			return divide();
		case "%":
			return mod();
		default:
			throw new TypeMismatch();
		}
	}

}
