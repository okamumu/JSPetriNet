package jspetrinet.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jspetrinet.exception.JSPNException;
import jspetrinet.exception.JSPNExceptionType;
import jspetrinet.exception.TypeMismatch;

public class ASTMathFunc implements AST {
	
	private final List<AST> arg;
	private final String op;

	private final List<Object> res;

	public ASTMathFunc(List<AST> arg, String op) {
		this.arg = arg;
		this.op = op;
		this.res = new ArrayList<Object>();
	}

	private final Object exp() throws JSPNException {
		if (arg.size() != 1) {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Wrong definition of args of " + op);
		}

		Object arg1 = res.iterator().next();
		if (arg1 instanceof Integer) {
			return Math.exp((Integer) arg1);
		} else if (arg1 instanceof Double) {
			return Math.exp((Double) arg1);
		} else {
			throw new TypeMismatch();
		}
	}

	private final Object log() throws JSPNException {
		if (arg.size() != 1) {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Wrong definition of args of " + op);
		}

		Object arg1 = res.iterator().next();
		if (arg1 instanceof Integer) {
			return Math.log((Integer) arg1);
		} else if (arg1 instanceof Double) {
			return Math.log((Double) arg1);
		} else {
			throw new TypeMismatch();
		}
	}

	private final Object pow() throws JSPNException {
		if (arg.size() != 2) {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Wrong definition of args of " + op);
		}

		Iterator<Object> ite = res.iterator();
		Object arg1 = ite.next();
		Object arg2 = ite.next();
		if (arg1 instanceof Integer && arg2 instanceof Integer) {
			return Math.pow((Integer) arg1, (Integer) arg2);
		} else if (arg1 instanceof Integer && arg2 instanceof Double) {
			return Math.pow((Integer) arg1, (Double) arg2);
		} else if (arg1 instanceof Double && arg2 instanceof Integer) {
			return Math.pow((Double) arg1, (Integer) arg2);
		} else if (arg1 instanceof Double && arg2 instanceof Double) {
			return Math.pow((Double) arg1, (Double) arg2);
		} else {
			throw new TypeMismatch();
		}
	}

	private final Object min() throws JSPNException {
		if (arg.size() <= 1) {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Wrong definition of args of " + op);
		}

		Iterator<Object> ite = res.iterator();
		Object tmp1 = ite.next();
		while (ite.hasNext()) {
			Object tmp2 = ite.next();
			if (tmp1 instanceof Integer && tmp2 instanceof Integer) {
				tmp1 = Math.min((Integer) tmp1, (Integer) tmp2);
			} else if (tmp1 instanceof Integer && tmp2 instanceof Double) {
				tmp1 = Math.min((Integer) tmp1, (Double) tmp2);
			} else if (tmp1 instanceof Double && tmp2 instanceof Integer) {
				tmp1 = Math.min((Double) tmp1, (Integer) tmp2);
			} else if (tmp1 instanceof Double && tmp2 instanceof Double) {
				tmp1 = Math.min((Double) tmp1, (Double) tmp2);
			} else {
				throw new TypeMismatch();
			}			
		}
		return tmp1;
	}

	private final Object max() throws JSPNException {
		if (arg.size() <= 1) {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Wrong definition of args of " + op);
		}

		Iterator<Object> ite = res.iterator();
		Object tmp1 = ite.next();
		while (ite.hasNext()) {
			Object tmp2 = ite.next();
			if (tmp1 instanceof Integer && tmp2 instanceof Integer) {
				tmp1 = Math.max((Integer) tmp1, (Integer) tmp2);
			} else if (tmp1 instanceof Integer && tmp2 instanceof Double) {
				tmp1 = Math.max((Integer) tmp1, (Double) tmp2);
			} else if (tmp1 instanceof Double && tmp2 instanceof Integer) {
				tmp1 = Math.max((Double) tmp1, (Integer) tmp2);
			} else if (tmp1 instanceof Double && tmp2 instanceof Double) {
				tmp1 = Math.max((Double) tmp1, (Double) tmp2);
			} else {
				throw new TypeMismatch();
			}			
		}
		return tmp1;
	}

	private Object makeASTNaN() {
		ASTList x = new ASTList();
		for (Object obj : res) {
			x.add(AST.getAST(obj));
		}
		return new ASTNaN(new ASTMathFunc(x, op));		
	}

	@Override
	public Object eval(ASTEnv m) throws JSPNException {
		boolean hasNaN = false;
		for (AST a : arg) {
			Object tmp = a.eval(m);
			if (tmp instanceof ASTNaN) {
				hasNaN = true;
			}
			res.add(tmp);
		}
		
		if (hasNaN) {
			return this.makeASTNaN();
		}

		switch(op) {
		case "pow":
			return pow();
		case "exp":
			return exp();
		case "log":
			return log();
		case "min":
			return min();
		case "max":
			return max();
		default:
			throw new TypeMismatch();
		}
	}

	@Override
	public String toString() {
		return op + "(" + this.arg.toString() + ")";
	}
}
