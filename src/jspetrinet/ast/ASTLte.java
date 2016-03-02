package jspetrinet.ast;

import jspetrinet.exception.*;

public class ASTLte extends ASTBinaryOperator {

	public ASTLte(ASTree left, ASTree right) {
		super(left, right);
	}

	@Override
	public Object eval(ASTEnv m) throws ASTException {
		Object lhs = this.getLeft().eval(m);
		Object rhs = this.getRight().eval(m);
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

}
