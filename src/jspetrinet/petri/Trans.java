package jspetrinet.petri;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.*;
import jspetrinet.graph.LabeledNode;

abstract public class Trans extends LabeledNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8924675835076592109L;
	private int index;
	private ASTree guard;

	public Trans(String label) {
		super(label);
		guard = null;
		index = 0;
	}

	public final int getIndex() {
		return index;
	}
	
	public final void setIndex(int index) {
		this.index = index;
	}
	
	public final void setGuard(ASTree guard) {
		this.guard = guard;
	}

	public final Boolean guardEval(ASTEnv m) throws ASTException {
		if (guard == null) {
			return true;
		}
		Object result = guard.eval(m);
		if (result instanceof Boolean) {
			return (Boolean) result;
		} else {
			throw new TypeMismatch();
		}
	}

}
