package bdd;

import java.util.HashMap;
import java.util.Map;

public class Bdd<T> {
	
	private Vertex top;
	private final Map<T,Terminal<T>> termSet;
	protected final Map<NonTerminal,NonTerminal> nonTermSet;

	public Bdd() {
		top = null;
		termSet = new HashMap<T,Terminal<T>>();
		nonTermSet = new HashMap<NonTerminal,NonTerminal>();
	}
	
	// getter
	public final Vertex getTop() {
		return top;
	}
	
	public final void setTop(Vertex top) {
		this.top = top;
	}

	public Vertex createNonTerm(int index, Vertex low, Vertex high) {
		if (low == high) {
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

	public final Vertex createTerm(T value) {
		if (termSet.containsKey(value)) {
			return termSet.get(value);
		} else {
			Terminal<T> term = new Terminal<T>(value);
			termSet.put(value, term);
			return term;
		}
	}
}
