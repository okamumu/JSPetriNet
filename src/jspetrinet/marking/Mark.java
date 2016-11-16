package jspetrinet.marking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jspetrinet.graph.Arc;

public final class Mark extends jspetrinet.graph.Node implements Comparable<Mark> {

	private final int[] vec;

	public Mark(int size) {
		this.vec = new int [size];
	}

	public Mark(Mark m) {
		this.vec = Arrays.copyOf(m.vec, m.vec.length);
	}

	public final int get(int i) {
		return vec[i];
	}
	
	public final void set(int i, int v) {
		vec[i] = v;
	}

	public final List<Mark> next() {
		List<Mark> next = new ArrayList<Mark>();
		for (Arc a: getOutArc()) {
			next.add((Mark) a.getDest());
		}
		return next;
	}

	@Override
	public String toString() {
		return Arrays.toString(vec);
	}

	@Override
	public final int hashCode() {
		return Arrays.hashCode(vec);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mark other = (Mark) obj;
		if (!Arrays.equals(vec, other.vec))
			return false;
		return true;
	}

	@Override
	public int compareTo(Mark other) {
		if (vec.length < other.vec.length) {
			return -1;
		}
		if (vec.length > other.vec.length) {
			return 1;
		}
		for (int i=vec.length-1; i>=0; i--) {
			if (vec[i] < other.vec[i]) {
				return -1;
			}
			if (vec[i] > other.vec[i]) {
				return 1;
			}
		}
		return 0;
	}
}
