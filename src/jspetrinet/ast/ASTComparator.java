package jspetrinet.ast;

import jspetrinet.exception.*;

public final class ASTComparator extends ASTBinary {
	
	private Object lhs;
	private Object rhs;

	public ASTComparator(AST left, AST right, String op) {
		super(left, right, op);
	}

	private final Object eq() throws JSPNException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return ((Integer) lhs).intValue() == ((Integer) rhs).intValue();
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return ((Integer) lhs).doubleValue() == ((Double) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return ((Double) lhs).doubleValue() == ((Integer) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return ((Double) lhs).doubleValue() == ((Double) rhs).doubleValue();
		} else if (lhs instanceof Boolean && rhs instanceof Boolean) {
			return ((Boolean) lhs).equals((Boolean) rhs);				
		} else {
			return false;
		}
	}

	private final Object neq() throws JSPNException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return ((Integer) lhs).intValue() != ((Integer) rhs).intValue();
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return ((Integer) lhs).doubleValue() != ((Double) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return ((Double) lhs).doubleValue() != ((Integer) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return ((Double) lhs).doubleValue() != ((Double) rhs).doubleValue();
		} else if (lhs instanceof Boolean && rhs instanceof Boolean) {
			return !((Boolean) lhs).equals((Boolean) rhs);				
		} else {
			return true;
		}
	}

	private final Object lt() throws JSPNException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return ((Integer) lhs).intValue() < ((Integer) rhs).intValue();
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return ((Integer) lhs).doubleValue() < ((Double) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return ((Double) lhs).doubleValue() < ((Integer) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return ((Double) lhs).doubleValue() < ((Double) rhs).doubleValue();
		} else {
			return new TypeMismatch();
		}
	}

	private final Object lte() throws JSPNException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return ((Integer) lhs).intValue() <= ((Integer) rhs).intValue();
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return ((Integer) lhs).doubleValue() <= ((Double) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return ((Double) lhs).doubleValue() <= ((Integer) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return ((Double) lhs).doubleValue() <= ((Double) rhs).doubleValue();
		} else {
			return new TypeMismatch();
		}
	}

	private final Object gt() throws JSPNException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return ((Integer) lhs).intValue() > ((Integer) rhs).intValue();
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return ((Integer) lhs).doubleValue() > ((Double) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return ((Double) lhs).doubleValue() > ((Integer) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return ((Double) lhs).doubleValue() > ((Double) rhs).doubleValue();
		} else {
			return new TypeMismatch();
		}
	}

	private final Object gte() throws JSPNException {
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return ((Integer) lhs).intValue() >= ((Integer) rhs).intValue();
		} else if (lhs instanceof Integer && rhs instanceof Double) {
			return ((Integer) lhs).doubleValue() >= ((Double) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Integer) {
			return ((Double) lhs).doubleValue() >= ((Integer) rhs).doubleValue();
		} else if (lhs instanceof Double && rhs instanceof Double) {
			return ((Double) lhs).doubleValue() >= ((Double) rhs).doubleValue();
		} else {
			return new TypeMismatch();
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
		case "==":
			return eq();
		case "!=":
			return neq();
		case "<":
			return lt();
		case "<=":
			return lte();
		case ">":
			return gt();
		case ">=":
			return gte();
		default:
			throw new TypeMismatch();
		}
	}

}
