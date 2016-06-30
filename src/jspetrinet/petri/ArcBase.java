package jspetrinet.petri;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.graph.Arc;

abstract public class ArcBase extends Arc {

	private ASTree multi;
	private ASTree firingFunc;
	
	public ArcBase(Place src, Trans dest, ASTree multi) {
		super(src, dest);
		this.multi = multi;
	}

	public ArcBase(Trans src, Place dest, ASTree multi) {
		super(src, dest);
		this.multi = multi;
	}
	
	// getter
	public final int getMulti(ASTEnv env) throws ASTException {
		Object result = multi.eval(env);
		if (result instanceof Integer) {
			return (Integer) result;
		} else if (result instanceof Double) {
			return ((Double) result).intValue();
		} else {
			throw new TypeMismatch();
		}
	}
	
	public final void setMulti(ASTree multi) {
		this.multi = multi;
	}
	
	public final ASTree getFiring() {
		return firingFunc;
	}

	public final void setFiring(ASTree firingFunc) {
		this.firingFunc = firingFunc;
	}

	public final int firing(ASTEnv env) throws ASTException {
		Object result = firingFunc.eval(env);
		if (result instanceof Integer) {
			return (Integer) result;
		} else if (result instanceof Double) {
			return ((Double) result).intValue();
		} else {
			throw new TypeMismatch();
		}
	}

}
