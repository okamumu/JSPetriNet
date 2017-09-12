package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jp.rel.jmtrandom.Random;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.sim.Utility;

public class ExpDist extends Dist {
	
	public static final String dname = "expdist";

	private AST rate;
	private Object rateObj;

	public ExpDist(AST rate) {
		this.rate = rate;
	}
	
	public final AST getRate() {
		return rate;
	}

	public final void setParam(AST rate) {
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
		double rate_value = Utility.convertObjctToDouble(rateObj);
		return rnd.nextExp(rate_value);
	}
}
