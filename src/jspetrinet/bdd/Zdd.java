package bdd;

public class Zdd<T> extends Bdd<T> {
	
	private final Vertex zeroTerm;

	public Zdd(T zero) {
		super();
		zeroTerm = createTerm(zero);
	}

	public Vertex createNonTerm(int index, Vertex low, Vertex high) {
		if (high == zeroTerm) {
			return low;
		}
		
		NonTerminal nterm = new NonTerminal(index, low, high);
		if (nonTermSet.containsKey(nterm)) {
			return nonTermSet.get(nterm);
		} else {
			nonTermSet.put(nterm, nterm);
			return nterm;
		}
	}

}
