package bdd;

import java.util.HashMap;
import java.util.Map;

abstract public class BinOperator<T> {
	
	protected final Map<PairVertex,Vertex> hash;

	public BinOperator() {
		hash = new HashMap<PairVertex,Vertex>();
	}

	abstract protected Vertex apply(Vertex f, Vertex g, Map<PairVertex,Vertex> hash, Bdd<T> bdd);
	
	public final Vertex apply(Vertex f, Vertex g, Bdd<T> bdd) {
		return apply(f, g, hash, bdd);
	}
	
	public final void clearHash() {
		hash.clear();
	}
	
	public final Map<PairVertex,Vertex> getHash() {
		return hash;
	}

}
