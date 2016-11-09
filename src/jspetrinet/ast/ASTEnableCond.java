package jspetrinet.ast;

import jspetrinet.exception.ASTException;
import jspetrinet.marking.PetriAnalysis;
import jspetrinet.marking.TransStatus;
import jspetrinet.petri.Trans;

public class ASTEnableCond extends ASTree {

	private final Trans trans;

	public ASTEnableCond(Trans t) {
		this.trans = t;
	}
	
	public final Trans getTrans() {
		return trans;
	}
	
	@Override
	public Object eval(ASTEnv env) {
		try {
			return PetriAnalysis.isEnable(env, trans) == TransStatus.ENABLE;
		} catch (ASTException e) {
			e.printStackTrace();
			return false;
		}
	}
}
