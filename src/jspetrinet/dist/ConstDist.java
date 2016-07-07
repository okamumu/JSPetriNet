package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;

public class ConstDist extends Dist {
	
	ASTree constValue;

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
	public Object eval(ASTEnv env) throws ASTException {
		Object result = constValue.eval(env);
		return "ConstDist(" + result + ")";
	}
}
