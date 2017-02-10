package jspetrinet.sim;

public interface Random {
	abstract double nextUnif01();
	abstract double nextUnif(double low, double high);
	abstract double nextExp(double rate);
	abstract double nextTNorm(double mu, double sig);
}
