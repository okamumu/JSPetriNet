package jspetrinet.dist;

import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.sim.Random;
import jspetrinet.sim.Utility;

public class ExpDist extends Dist {
	
	private ASTree rate;
	private Object rateObj;

	public ExpDist(ASTree rate) {
		this.rate = rate;
	}
	
	public final ASTree getRate() {
		return rate;
	}

	public final void setRate(ASTree rate) {
		this.rate = rate;
	}

	@Override
	public void updateObj() throws ASTException {
		rateObj = rate.eval(this.getEnv());
	}

	@Override
	public String toString() {
		return "ExpDist(" + rateObj + ")";
	}

	@Override
	public double nextImpl(Random rnd) throws ASTException {
		return rnd.nextExp(Utility.convertObjctToDouble(rateObj));
	}
}
