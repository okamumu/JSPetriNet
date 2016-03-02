package bdd;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public final class CountEven implements Visitor {

	private final Map<Vertex,BigInteger> vertexSet;
	private final Map<Vertex,Integer> vertexIndex;

	public CountEven() {
		vertexSet = new HashMap<Vertex,BigInteger>();
		vertexIndex = new HashMap<Vertex,Integer>();
	}
	
	public BigInteger count(Vertex top) {
		vertexSet.clear();
		vertexIndex.clear();
		top.accept(this);
		return vertexSet.get(top);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void visit(Vertex v) {
		if (vertexSet.containsKey(v)) {
			return;
		}
		
		if (v instanceof Terminal<?>) {
			Terminal<Boolean> term = (Terminal<Boolean>) v;
			if (term.getValue() == true) {
				vertexSet.put(term, BigInteger.ONE);
			} else {
				vertexSet.put(term, BigInteger.ZERO);
			}
			vertexIndex.put(term, -2);
		} else {
			NonTerminal term = (NonTerminal) v;
			term.getLow().accept(this);
			term.getHigh().accept(this);
			int x = (term.getIndex() - vertexIndex.get(term.getLow()))/2;
			int y = (term.getIndex() - vertexIndex.get(term.getHigh()))/2;
			vertexSet.put(term,
					vertexSet.get(term.getLow()).shiftLeft(x-1).add(vertexSet.get(term.getHigh()).shiftLeft(y-1)));
			vertexIndex.put(term, term.getIndex());
		}
	}
	
	
}
