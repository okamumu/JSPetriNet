package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.sim.Random;
import jspetrinet.sim.Utility;

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

	public final void setConstValue(AST constValue) {
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
