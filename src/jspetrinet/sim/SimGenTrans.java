package jspetrinet.sim;

import jspetrinet.exception.ASTException;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.GenTransPolicy;
import jspetrinet.petri.Net;

public abstract class SimGenTrans extends GenTrans implements SimTimedCalc{

	public SimGenTrans(String label, GenTransPolicy policy) {
		super(label, policy);
	}

	public abstract double nextTime(Net net, Random rnd) throws ASTException;
}
