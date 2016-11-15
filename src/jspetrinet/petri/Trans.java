package jspetrinet.petri;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.AST;
import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.graph.LabeledNode;

abstract public class Trans extends LabeledNode {

	private int index;
	private AST guard;

	private int priority;
	private boolean vanishable;

	public Trans(String label) {
		super(label);
		guard = null;
		index = 0;
		priority = 0;
	}

	public final int getIndex() {
		return index;
	}
	
	public final void setIndex(int index) {
		this.index = index;
	}

	public final int getPriority() {
		return priority;
	}
	
	public final void setPriority(int priority) {
		this.priority = priority;
	}

	public final boolean canVanishing() {
		return vanishable;
	}
	
	public final void setVanishable(boolean vanishable) {
		this.vanishable = vanishable;
	}
	
	public final void setGuard(AST guard) {
		this.guard = guard;
	}

	public final String toGuardString(ASTEnv m) throws ASTException {
		if (guard == null) {
			throw new ASTException("");
		}
		Object result = guard.eval(m);
		if (result instanceof Boolean) {
			throw new ASTException("");
		} else {
			return result.toString();
		}
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
