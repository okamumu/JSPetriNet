package jspetrinet.sim;

import jspetrinet.exception.ASTException;
import rnd.Sfmt;

public class RandomGenerator implements Random{

	Sfmt rnd;
	
	public RandomGenerator(int seed) throws ASTException {
		rnd = new Sfmt(seed);
	}
	
	@Override
	public double nextUnif01() {
		return rnd.NextUnif();
	}

	@Override
	public double nextExp() {
		return rnd.NextExp();
	}

	@Override
	public double nextUnif(double low, double high) {
		return nextUnif01()%(high - low + 1) + low;
	}

	@Override
	public double nextExp(double rate) {
		return nextExp()/rate;
	}
}
