package jspetrinet.ast;

import jspetrinet.exception.*;

public class ASTMod extends ASTBinaryOperator {

	public ASTMod(ASTree left, ASTree right) {
		super(left, right);
	}

	@Override
	public Object eval(ASTEnv m) throws ASTException {
		Object lhs = this.getLeft().eval(m);
		Object rhs = this.getRight().eval(m);
		if (lhs instanceof Integer && rhs instanceof Integer) {
			return (Integer) lhs / (Integer) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

}
