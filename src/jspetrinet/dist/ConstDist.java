package jspetrinet.dist;

import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.sim.Random;
import jspetrinet.sim.Utility;

public class ConstDist extends Dist {
	
	ASTree constValue;
	Object constValueObj;

	public ConstDist(ASTree constValue) {
		this.constValue = constValue;
	}

	public final ASTree getConstValue() {
		return constValue;
	}

	public final void setConstValue(ASTree constValue) {
		this.constValue = constValue;
	}

	@Override
	public String toString() {
		return "ConstDist(" + constValueObj + ")";
	}

	@Override
	public void updateObj() throws ASTException {
		constValueObj = constValue.eval(this.getEnv());
	}

	@Override
	public double nextImpl(Random rnd) throws ASTException {
		return Utility.convertObjctToDouble(constValueObj);
	}
}
