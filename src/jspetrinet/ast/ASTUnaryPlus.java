package jspetrinet.ast;

import jspetrinet.exception.*;

public class ASTUnaryPlus extends ASTUnaryOperator {

	public ASTUnaryPlus(ASTree child) {
		super(child);
	}

	@Override
	public Object eval(ASTEnv m) throws ASTException {
		Object res = this.getChild().eval(m);
		if (res instanceof Integer) {
			return (Integer) res;
		} else if (res instanceof Double) {
			return (Double) res;
		} else {
			throw new TypeMismatch();
		}
	}

}
