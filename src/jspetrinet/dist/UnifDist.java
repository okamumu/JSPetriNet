package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.common.Utility;
import jp.rel.jmtrandom.Random;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;

public class UnifDist extends Dist {
	
	public static final String dname = "unif";

	private AST min;
	private AST max;
	private Object minObj;
	private Object maxObj;

	public UnifDist(AST min, AST max) {
		this.min = min;
		this.max = max;
	}
	
	public final AST getMin() {
		return min;
	}

	public final AST getMax() {
		return max;
	}
	
	public final void setParam(AST min, AST max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public void updateObj(ASTEnv env) throws JSPNException {
		minObj = min.eval(env);
		maxObj = max.eval(env);
	}

	@Override
	public String toString() {
		return dname + "(" + minObj + "," + maxObj + ")";
	}

	@Override
	public double nextImpl(Random rnd) throws JSPNException {
		double min_value = Utility.convertObjctToDouble(minObj);
		double max_value = Utility.convertObjctToDouble(maxObj);
		return rnd.nextUnif(min_value, max_value);
	}
}
