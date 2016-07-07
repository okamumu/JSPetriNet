package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;

public class UnifDist extends Dist {
	
	private ASTree lower;
	private ASTree upper;

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
	public Object eval(ASTEnv env) throws ASTException {
		Object l = lower.eval(env);
		Object u = upper.eval(env);
		return "UnifDist(" + l + "," + u + ")";
	}
}
