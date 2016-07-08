package jspetrinet.ast;

import jspetrinet.exception.*;

public class ASTVariable extends ASTree {

	private final String label;

	public ASTVariable(String label) {
		this.label = label;
	}
	
	public final String getLabel() {
		return label;
	}

	@Override
	public Object eval(ASTEnv env) throws ASTException {
		Object v;
		try {
			v = env.get(label);
		} catch (NotFindObjectException e) {
			return label;
		}
		if (v instanceof ASTree) {
			return ((ASTree) v).eval(env);
		} else {
			return v;
		}
	}
}
