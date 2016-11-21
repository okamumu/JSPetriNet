package jspetrinet.petri;

import jspetrinet.ast.ASTEnv;

import java.util.Map;

import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.exception.JSPNExceptionType;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.graph.LabeledNode;

abstract public class Trans extends LabeledNode {

	private int index;
	private AST guard;
	private Map<Place,AST> update;

	private int priority;
	private boolean vanishable;

	public Trans(String label) {
		super(label);
		guard = null;
		index = 0;
		priority = 0;
		update = null;
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

	public final String toGuardString(ASTEnv m) throws JSPNException {
		if (guard == null) {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Cannot covert Guard to String in " + this.getLabel());
		}
		Object result = guard.eval(m);
		if (result instanceof Boolean) {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "The guard of " + this.getLabel() + " is set as a boolean TRUE/FALSE");
		} else {
			return result.toString();
		}
	}

	public final Boolean guardEval(ASTEnv m) throws JSPNException {
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

	public final void setUpdate(Map<Place,AST> update) {
		this.update = update;
	}
	
	public final Map<Place,AST> getUpdate() {
		return update;
	}

	public final String toUpdateString(ASTEnv m) throws JSPNException {
		if (update == null) {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Cannot covert Update to String in " + this.getLabel());
		} else {
			String ret = "";
			boolean firstloop = true;
			for (Map.Entry<Place,AST> entry : update.entrySet()) {
				if (firstloop) {
					ret += "#" + entry.getKey().getLabel() + "<-" + entry.getValue().eval(m);
					firstloop = false;
				} else {
					ret += "," + entry.getKey().getLabel() + "<-" + entry.getValue().eval(m);
				}
			}
			return ret;
		}
	}

}
