package jspetrinet.ast;

import jspetrinet.exception.*;

public class ASTNot extends ASTUnaryOperator {

	public ASTNot(ASTree child) {
		super(child);
	}

	@Override
	public Object eval(ASTEnv m) throws ASTException {
		Object res = this.getChild().eval(m);
		if (res instanceof Boolean) {
			return !((Boolean) res);
		} else {
			throw new TypeMismatch();
		}
	}

}
