package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.sim.Random;
import jspetrinet.sim.Utility;

public class UnifDist extends Dist {
	
	private ASTree lower;
	private ASTree upper;
	private Object lowerObj;
	private Object upperObj;

	public UnifDist(ASTree lower, ASTree upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	public final ASTree getLower() {
		return lower;
	}

	public final ASTree getUpper() {
		return upper;
	}
	
	public final void setLower(ASTree lower) {
		this.lower = lower;
	}

	public final void setUpper(ASTree upper) {
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
