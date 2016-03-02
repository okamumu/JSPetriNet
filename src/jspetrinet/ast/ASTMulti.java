package jspetrinet.ast;

import jspetrinet.exception.*;

public class ASTMulti extends ASTBinaryOperator {

	public ASTMulti(ASTree left, ASTree right) {
		super(left, right);
	}

	@Override
	public Object eval(ASTEnv m) throws ASTException {
		Object lhs = this.getLeft().eval(m);
		Object rhs = this.getRight().eval(m);
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

}
