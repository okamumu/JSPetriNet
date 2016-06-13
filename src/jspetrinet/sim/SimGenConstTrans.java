package jspetrinet.sim;

import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.petri.GenTransPolicy;
import jspetrinet.petri.Net;

public class SimGenConstTrans extends SimGenTrans{

	private ASTree constant;
	
	public SimGenConstTrans(String label, ASTree constant, GenTransPolicy policy) {
		super(label, policy);
		this.constant = constant;
	}
	
	public void setConst(ASTree constant){
		this.constant = constant;
	}

	@Override
	public double nextTime(Net net, Random rnd) throws ASTException {
		Object obj = constant.eval(net);
		double constTime;
		if(obj instanceof Integer){
			constTime = (Integer)obj;
		}else if(obj instanceof Double){
			constTime = (Double)obj;
		}else {
			throw new TypeMismatch();
		}
		return constTime;
	}	
}
