package jspetrinet.ast;

import jspetrinet.exception.*;

public class ASTIfThenElse extends ASTTernaryOperator {

	public ASTIfThenElse(ASTree first, ASTree second, ASTree third) {
		super(first, second, third);
	}

	@Override
	public Object eval(ASTEnv m) throws ASTException {
		Object f = this.getFirst().eval(m);
		if (f instanceof Boolean) {
			if ((Boolean) f) {
				return this.getSecond().eval(m);
			} else {
				return this.getThird().eval(m);
			}
		} else {
			throw new TypeMismatch();
		}
	}

}
