package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.AST;
import jspetrinet.exception.ASTException;
import jspetrinet.sim.Random;
import jspetrinet.sim.Utility;

public class UnifDist extends Dist {
	
	private AST lower;
	private AST upper;
	private Object lowerObj;
	private Object upperObj;

	public UnifDist(AST lower, AST upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	public final AST getLower() {
		return lower;
	}

	public final AST getUpper() {
		return upper;
	}
	
	public final void setLower(AST lower) {
		this.lower = lower;
	}

	public final void setUpper(AST upper) {
		this.upper = upper;
	}

	@Override
	public void updateObj(ASTEnv env) throws ASTException {
		lowerObj = lower.eval(env);
		upperObj = upper.eval(env);
	}

	@Override
	public String toString() {
		return "UnifDist(" + lowerObj + "," + upperObj + ")";
	}

	@Override
	public double nextImpl(Random rnd) throws ASTException {
		return rnd.nextUnif(Utility.convertObjctToDouble(lowerObj),
				Utility.convertObjctToDouble(upperObj));
	}
}
