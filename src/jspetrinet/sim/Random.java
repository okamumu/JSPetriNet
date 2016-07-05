package jspetrinet.sim;

public interface Random {
	abstract double nextUnif01();
	abstract double nextUnif(double low, double high);
	abstract double nextExp();
	abstract double nextExp(double rate);
}
