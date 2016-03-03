package jspetrinet.marking;

import java.util.LinkedList;

public class Stack<T> extends LinkedList<T> {
	private static final long serialVersionUID = 1L;

	public Stack() {
		super();
	}

	@Override
	public final void push(T item) {
		add(item);
	}

	@Override
	public final T pop() {
		return removeLast();
	}
	
	public final boolean empty() {
		return isEmpty();
	}
}
