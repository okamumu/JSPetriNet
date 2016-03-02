package jspetrinet.petri;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.*;
import jspetrinet.graph.Node;

abstract public class Trans extends Node {

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
	
	public final ASTree getGuard() {
		return guard;
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
