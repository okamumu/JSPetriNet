package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;

public class ExpDist extends Dist {
	
	private ASTree rate;

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
	public Object eval(ASTEnv env) throws ASTException {
		Object obj = rate.eval(env);
		return "ExpDist(" + obj + ")";
	}
}
