package jspetrinet.ast;

import jspetrinet.exception.JSPNException;
import jspetrinet.exception.TypeMismatch;

public final class ASTIfThenElse implements AST {

	private final AST first;
	private final AST second;
	private final AST third;
	
	public ASTIfThenElse(AST first, AST second, AST third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	@Override
	public Object eval(ASTEnv m) throws JSPNException {
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
	public String toString() {
		return "ifelse(" + first.toString() +"," + second.toString() + "," + third.toString() + ")";
	}
}
