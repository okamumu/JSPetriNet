package jspetrinet.sim;

import rnd.Sfmt;

public class RandomGenerator implements Random{

	Sfmt rnd;
	
	public RandomGenerator(int seed) {
		rnd = new Sfmt(seed);
	}
	
	@Override
	public double nextUnif01() {
		return rnd.NextUnif();
	}

	@Override
	public double nextUnif(double low, double high) {
		return (high - low) * rnd.NextUnif() + low;
	}

	@Override
	public double nextExp(double rate) {
		return rnd.NextExp()/rate;
	}

	@Override
	public double nextTNorm(double mu, double sig) {
		double tmp;
		do {
			tmp = sig * rnd.NextNormal() + mu;
		} while (tmp > 0.0);
		return tmp;
	}
}
