package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.sim.Random;
import jspetrinet.sim.Utility;

public class TNormDist extends Dist {
	
	public static final String dname = "tnorm";

	private AST mu;
	private AST sig;
	private Object muObj;
	private Object sigObj;

	public TNormDist(AST lower, AST upper) {
		this.mu = lower;
		this.sig = upper;
	}
	
	public final AST getMu() {
		return mu;
	}

	public final AST getSig() {
		return sig;
	}
	
	public final void setMu(AST mu) {
		this.mu = mu;
	}

	public final void setSig(AST sig) {
		this.sig = sig;
	}

	@Override
	public void updateObj(ASTEnv env) throws JSPNException {
		muObj = mu.eval(env);
		sigObj = sig.eval(env);
	}

	@Override
	public String toString() {
		return dname + "(" + muObj + "," + sigObj + ")";
	}

	@Override
	public double nextImpl(Random rnd) throws JSPNException {
		return rnd.nextUnif(Utility.convertObjctToDouble(muObj),
				Utility.convertObjctToDouble(sigObj));
	}
}
