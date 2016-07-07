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

	public ASTree getObject(ASTEnv env) throws ASTException {
		Object v = env.get(label);
		if (v instanceof ASTVariable) {
			return ((ASTVariable) v).getObject(env);
		} else if (v instanceof ASTree) {
			return (ASTree) v;
		} else {
			throw new ASTException("Did not find the containt of variable " + label);
		}
	}
}
