package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.AST;
import jspetrinet.exception.ASTException;
import jspetrinet.sim.Random;
import jspetrinet.sim.Utility;

public class ConstDist extends Dist {
	
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
		return "ConstDist(" + constValueObj + ")";
	}

	@Override
	public void updateObj(ASTEnv env) throws ASTException {
		constValueObj = constValue.eval(env);
	}

	@Override
	public double nextImpl(Random rnd) throws ASTException {
		return Utility.convertObjctToDouble(constValueObj);
	}
}
