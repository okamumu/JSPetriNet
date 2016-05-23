package jspetrinet.sim;

import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;
import rnd.Sfmt;

public interface Calculate {
	abstract double nextExp(Trans tr, Net net, Sfmt rnd) throws ASTException;
	abstract double nextCertain(Trans tr, Net net) throws ASTException;
	abstract int Multinomial(double[] weight, Sfmt rnd) throws ASTException;
}
