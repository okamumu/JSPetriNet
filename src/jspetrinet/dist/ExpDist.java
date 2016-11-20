package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.sim.Random;
import jspetrinet.sim.Utility;

public class ExpDist extends Dist {
	
	public static final String dname = "dist.exp";

	private AST rate;
	private Object rateObj;

	public ExpDist(AST rate) {
		this.rate = rate;
	}
	
	public final AST getRate() {
		return rate;
	}

	public final void setRate(AST rate) {
		this.rate = rate;
	}

	@Override
	public void updateObj(ASTEnv env) throws JSPNException {
		rateObj = rate.eval(env);
	}

	@Override
	public String toString() {
		return dname + "(" + rateObj + ")";
	}

	@Override
	public double nextImpl(Random rnd) throws JSPNException {
		return rnd.nextExp(Utility.convertObjctToDouble(rateObj));
	}
}
