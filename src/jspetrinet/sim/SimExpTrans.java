package jspetrinet.sim;

import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.Net;

public class SimExpTrans extends ExpTrans implements SimTimedCalc{

	public SimExpTrans(String label, ASTree rate) {
		super(label, rate);
	}

	@Override
	public double nextTime(Net net, Random rnd) throws ASTException {
		Object obj = getRate().eval(net);
		double lamda;
		if(obj instanceof Integer){
			lamda = (Integer)obj;
		}else if(obj instanceof Double){
			lamda = (Double)obj;
		}else {
			throw new TypeMismatch();
		}
		return rnd.nextExp()/lamda;
	}
}
