package bdd;

public class PairVertex {
	
	protected final Vertex first;
	protected final Vertex second;
	
	public PairVertex(Vertex first, Vertex second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + first.hashCode();
		result = prime * result + second.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PairVertex other = (PairVertex) obj;
		if (first != other.first || second != other.second)
			return false;
		return true;
	}
}
