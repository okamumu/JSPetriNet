package jspetrinet.ast;

import jspetrinet.exception.*;

public final class ASTArithmetic extends ASTBinary {
	
	private Object lhs;
	private Object rhs;

	public ASTArithmetic(AST left, AST right, String op) {
		super(left, right, op);
	}

	private final Object plus() throws JSPNException {
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

	private final Object minus() throws JSPNException {
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

	private final Object multi() throws JSPNException {
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

	private final Object divide() throws JSPNException {
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

	private final Object idivide() throws JSPNException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return (Integer) lhs / (Integer) rhs;
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return (Integer) lhs / ((Double) rhs).intValue();
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return ((Double) lhs).intValue() / (Integer) rhs;
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return ((Double) lhs).intValue() / ((Double) rhs).intValue();
		} else {
			throw new TypeMismatch();
		}
	}

	private final Object mod() throws JSPNException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return (Integer) lhs / (Integer) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	@Override
	public final Object eval(ASTEnv m) throws JSPNException {
		lhs = this.getLeft().eval(m);
		rhs = this.getRight().eval(m);
		
		if (lhs instanceof ASTNaN || rhs instanceof ASTNaN) {
			return new ASTNaN(new ASTLogical(AST.getAST(lhs), AST.getAST(rhs), op));
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
		case "div":
			return idivide();
		case "mod":
			return mod();
		default:
			throw new TypeMismatch();
		}
	}
	
}
