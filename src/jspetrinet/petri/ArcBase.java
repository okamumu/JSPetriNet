package jspetrinet.petri;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.AST;
import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.graph.Arc;

abstract public class ArcBase extends Arc {

	private AST multi;
	private AST firingFunc;
	
	public ArcBase(Place src, Trans dest, AST multi) {
		super(src, dest);
		this.multi = multi;
	}

	public ArcBase(Trans src, Place dest, AST multi) {
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
	
	public final void setMulti(AST multi) {
		this.multi = multi;
	}
	
	public final AST getFiring() {
		return firingFunc;
	}

	public final void setFiring(AST firingFunc) {
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
