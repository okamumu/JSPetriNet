package jspetrinet.ast;

import jspetrinet.exception.*;

public class ASTAnd extends ASTBinaryOperator {

	public ASTAnd(ASTree left, ASTree right) {
		super(left, right);
	}

	@Override
	public Object eval(ASTEnv m) throws ASTException {
		Object lhs = this.getLeft().eval(m);
		Object rhs = this.getRight().eval(m);
		if (lhs instanceof Boolean && rhs instanceof Boolean) {
			return (Boolean) lhs && (Boolean) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

}
