package jspetrinet.sim;

import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import rnd.Sfmt;

public class Adapter implements Random{

	Sfmt rnd;
	
	public Adapter(Net net, int seed) throws ASTException {
		rnd = new Sfmt(seed);
	}
	
	@Override
	public double nextUnif() {
		return rnd.NextUnif();
	}

	@Override
	public double nextExp() {
		return rnd.NextExp();
	}
}
