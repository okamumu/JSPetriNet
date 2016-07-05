package jspetrinet.sim;

import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;

public interface SimTimedCalc {
	double nextTime(Net net, Random rnd) throws ASTException;
}
