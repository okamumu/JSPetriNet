package bdd;

import java.util.Map;

public final class AndOperator extends BinOperator<Boolean> {

	@SuppressWarnings("unchecked")
	@Override
	protected Vertex apply(Vertex f, Vertex g, Map<PairVertex, Vertex> hash,
			Bdd<Boolean> bdd) {

		PairVertex key = new PairVertex(f,g);
		if (hash.containsKey(key)) {
			return hash.get(key);
		}
		
		Vertex result;
		if (f instanceof Terminal<?> && g instanceof Terminal<?>) {
			Terminal<Boolean> fterm = (Terminal<Boolean>) f;
			Terminal<Boolean> gterm = (Terminal<Boolean>) g;
			result = bdd.createTerm(fterm.getValue() && gterm.getValue());
		} else if (f instanceof Terminal<?> && g instanceof NonTerminal) {
			Terminal<Boolean> fterm = (Terminal<Boolean>) f;
			NonTerminal gterm = (NonTerminal) g;
			if (fterm.getValue() == false) {
				result = bdd.createTerm(false);
			} else {
				Vertex low = apply(fterm, gterm.getLow(), hash, bdd);
				Vertex high = apply(fterm, gterm.getHigh(), hash, bdd);
				result = bdd.createNonTerm(gterm.getIndex(), low, high);
			}
		} else if (f instanceof NonTerminal && g instanceof Terminal<?>) {
			NonTerminal fterm = (NonTerminal) f;
			Terminal<Boolean> gterm = (Terminal<Boolean>) g;
			if (gterm.getValue() == false) {
				result = bdd.createTerm(false);
			} else {
				Vertex low = apply(fterm.getLow(), gterm, hash, bdd);
				Vertex high = apply(fterm.getHigh(), gterm, hash, bdd);
				result = bdd.createNonTerm(fterm.getIndex(), low, high);				
			}
		} else { //if (f instanceof NonTerminal && g instanceof NonTerminal) {
			NonTerminal fterm = (NonTerminal) f;
			NonTerminal gterm = (NonTerminal) g;
			if (fterm.getIndex() == gterm.getIndex()) {
				Vertex low = apply(fterm.getLow(), gterm.getLow(), hash, bdd);
				Vertex high = apply(fterm.getHigh(), gterm.getHigh(), hash, bdd);
				result = bdd.createNonTerm(fterm.getIndex(), low, high);
			} else if (fterm.getIndex() > gterm.getIndex()) {
				Vertex low = apply(fterm.getLow(), gterm, hash, bdd);
				Vertex high = apply(fterm.getHigh(), gterm, hash, bdd);
				result = bdd.createNonTerm(fterm.getIndex(), low, high);
			} else { //if (fterm.getIndex() < gterm.getIndex()) {
				Vertex low = apply(fterm, gterm.getLow(), hash, bdd);
				Vertex high = apply(fterm, gterm.getHigh(), hash, bdd);
				result = bdd.createNonTerm(gterm.getIndex(), low, high);
			}
		}
		hash.put(key, result);
		hash.put(new PairVertex(g,f), result);
		return result;
	}

}
