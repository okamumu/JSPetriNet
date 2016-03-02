package bdd;

public final class NonTerminal extends Vertex {
	
	private final int index;
	private final Vertex low;
	private final Vertex high;

	public NonTerminal(int index, Vertex low, Vertex high) {
		this.index = index;
		this.low = low;
		this.high = high;
	}
	
	@Override
	public final int getIndex() {
		return index;
	}
	
	public final Vertex getLow() {
		return low;
	}
	
	public final Vertex getHigh() {
		return high;
	}

	@Override
	public final void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public final boolean isTerminal() {
		return false;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = index;
		result = prime * result + low.hashCode();
		result = prime * result + high.hashCode();
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
		NonTerminal other = (NonTerminal) obj;
		if (index != other.index || low != other.low || high != other.high)
			return false;
		return true;
	}
}
