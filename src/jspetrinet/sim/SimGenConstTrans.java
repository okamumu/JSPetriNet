package jspetrinet.sim;

import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.petri.GenTransPolicy;
import jspetrinet.petri.Net;

public class SimGenConstTrans extends SimGenTrans{

	private ASTree constant;
	
	public SimGenConstTrans(String label, ASTree constant, GenTransPolicy policy) {
		super(label, policy);
		this.constant = constant;
	}
	
	public final void setConst(ASTree constant){
		this.constant = constant;
	}

	@Override
	public double nextTime(Net net, Random rnd) throws ASTException {
		return Utility.convertObjctToDouble(this.constant.eval(net));
	}	
}
