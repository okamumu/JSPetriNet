package jspetrinet.ast;

import jspetrinet.exception.JSPNException;
import jspetrinet.exception.TypeMismatch;

public final class ASTTernary extends AST {

	private final AST first;
	private final AST second;
	private final AST third;
	
	private final String op;

	public ASTTernary(AST first, AST second, AST third, String op) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.op = op;
	}
	
//	public ASTree getFirst() {
//		return first;
//	}
//	
//	public ASTree getSecond() {
//		return second;
//	}
//
//	public ASTree getThird() {
//		return third;
//	}

	private final Object ite(ASTEnv m) throws JSPNException {
		Object f = this.first.eval(m);
		if (f instanceof Boolean) {
			if ((Boolean) f) {
				return this.second.eval(m);
			} else {
				return this.third.eval(m);
			}
		} else {
			throw new TypeMismatch();
		}
	}

	@Override
	public Object eval(ASTEnv m) throws JSPNException {
		switch (op) {
		case "ite":
			return ite(m);
		default:
			throw new TypeMismatch();
		}
	}
}
