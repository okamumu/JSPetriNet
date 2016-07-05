package jspetrinet.sim;

import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.Net;

public class SimExpTrans extends ExpTrans implements SimTimedCalc{

	public SimExpTrans(String label, ASTree rate) {
		super(label, rate);
	}

	@Override
	public double nextTime(Net net, Random rnd) throws ASTException {
		double lambda = Utility.convertObjctToDouble(getRate().eval(net));
		return rnd.nextExp(lambda);
	}
}
