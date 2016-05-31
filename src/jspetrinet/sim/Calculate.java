package jspetrinet.sim;

import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public interface Calculate {
	abstract double nextUnif();
	abstract double nextExp();
	abstract double nextExpTrans(Trans tr, Net net) throws ASTException;
	abstract double nextGenTrans(Trans tr, Net net) throws ASTException;
	abstract double nextConstTrans(double time) throws ASTException;
	abstract double nextUnifTrans(Trans tr, Net net) throws ASTException;
	abstract int nextMultinomial(double[] weight) throws ASTException;
}
