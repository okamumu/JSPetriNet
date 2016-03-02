package bdd;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public final class ChangeOperator<T> {
	
	private final Map<SelectVertex,Vertex> hash;
	
	public ChangeOperator() {
		hash = new HashMap<SelectVertex,Vertex>();
	}
	
	public final Vertex apply(BitSet m, Vertex f, Bdd<T> bdd) {
		BitSet m0 = (BitSet) m.clone();
		return apply(m0, f, hash, bdd);
	}
	
	@SuppressWarnings("unchecked")
	public final Vertex apply(BitSet m, Vertex f, Map<SelectVertex,Vertex> hash, Bdd<T> bdd) {

		SelectVertex key = new SelectVertex(m,f);
		if (hash.containsKey(key)) {
			return hash.get(key);
		}
		
		Vertex result;
		if (f instanceof Terminal<?>) {
			Terminal<T> fterm = (Terminal<T>) f;
			result = bdd.createTerm(fterm.getValue());
		} else {
			NonTerminal fterm = (NonTerminal) f;
			Vertex low = apply(m, fterm.getLow(), hash, bdd);
			Vertex high = apply(m, fterm.getHigh(), hash, bdd);
			if (m.get(fterm.getIndex()) == true) {
				result = bdd.createNonTerm(fterm.getIndex()-1, low, high);
			} else {
				result = bdd.createNonTerm(fterm.getIndex(), low, high);
			}
		}
		hash.put(key, result);
		return result;
	}
}
