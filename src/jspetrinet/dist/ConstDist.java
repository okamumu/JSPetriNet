package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.common.Utility;
import jp.rel.jmtrandom.Random;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;

public class ConstDist extends Dist {
	
	public static final String dname = "det";

	AST constValue;
	Object constValueObj;

	public ConstDist(AST constValue) {
		this.constValue = constValue;
	}

	public final AST getConstValue() {
		return constValue;
	}

	public final void setParam(AST constValue) {
		this.constValue = constValue;
	}

	@Override
	public String toString() {
		return dname + "(" + constValueObj + ")";
	}

	@Override
	public void updateObj(ASTEnv env) throws JSPNException {
		constValueObj = constValue.eval(env);
	}

	@Override
	public double nextImpl(Random rnd) throws JSPNException {
		return Utility.convertObjctToDouble(constValueObj);
	}
}
