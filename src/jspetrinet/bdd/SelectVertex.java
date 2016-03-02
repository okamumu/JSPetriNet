package bdd;

import java.util.BitSet;

public final class SelectVertex {

	private final BitSet m;
	private final Vertex v;
	
	public SelectVertex(BitSet m, Vertex v) {
		this.m = m;
		this.v = v;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + v.hashCode();
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		SelectVertex other = (SelectVertex) obj;
		if (v != other.v)
			return false;
		for (int i=0; i<=v.getIndex(); i++) {
			if (m.get(i) != other.m.get(i)) return false;
		}
		return true;
	}
}
