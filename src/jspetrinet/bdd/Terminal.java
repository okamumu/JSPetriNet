package bdd;

public final class Terminal<T> extends Vertex {
	
	private final T value;

	public Terminal(T value) {
		this.value = value;
	}
	
	public final T getValue() {
		return value;
	}

	@Override
	public final int getIndex() {
		return -1;
	}
	
	@Override
	public final void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public final boolean isTerminal() {
		return true;
	}

}
