package jspetrinet.petri;

import jspetrinet.ast.*;

import jspetrinet.exception.JSPNException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.graph.LabeledNode;

abstract public class Trans extends LabeledNode {

	private int index;
	private AST guard;
	private AST update;

	private int priority;
	private boolean vanishable;

	public Trans(String label) {
		super(label);
		guard = new ASTValue(true);
		index = 0;
		priority = 0;
		update = null;
	}

	// getter

	public final int getIndex() {
		return index;
	}
	
	public final int getPriority() {
		return priority;
	}
	
	public final boolean canVanishing() {
		return vanishable;
	}
	
	public final AST getGuard() {
		return guard;
	}

	public final AST getUpdate() {
		return update;
	}

	// setter

	public final void setIndex(int index) {
		this.index = index;
	}

	public final void setPriority(int priority) {
		this.priority = priority;
	}

	public final void setVanishable(boolean vanishable) {
		this.vanishable = vanishable;
	}
	
	public final void setGuard(AST guard) {
		this.guard = guard;
	}

	public final void setUpdate(AST update) {
		this.update = update;
	}
	
	public final boolean guardEval(ASTEnv m) throws JSPNException {
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

	public final void updateEval(ASTEnv m) throws JSPNException {
		if (update == null) {
			return;
		}
		update.eval(m);
	}
}
