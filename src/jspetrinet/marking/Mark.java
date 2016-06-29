package jspetrinet.marking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jspetrinet.graph.Arc;

public final class Mark extends jspetrinet.graph.Node {

	/**
	 * 
	 */
	private static final long serialVersionUID = -812297444841411911L;
//	private int index;
	private int minfiring;
	private final byte[] vec;
	private MarkGroup markGroup;

	public Mark(int size) {
		this.vec = new byte [size];
		this.minfiring = 0;
		markGroup = null;
	}

	public Mark(Mark m) {
		this.vec = Arrays.copyOf(m.vec, m.vec.length);
		this.minfiring = m.minfiring;
		markGroup = null;
	}

	// getter
	public final void setFiring(int k) {
		this.minfiring = k;
	}

	public final int getFiring() {
		return this.minfiring;
	}

	public final int get(int i) {
		return vec[i];
	}
	
	public final void set(int i, int v) {
		vec[i] = (byte) v;
	}

//	public final int index() {
//		return index;
//	}
//
//	public final void setIndex(int i) {
//		index = i;
//	}
	
	public final List<Mark> next() {
		List<Mark> next = new ArrayList<Mark>();
		for (Arc a: getOutArc()) {
			next.add((Mark) a.getDest());
		}
		return next;
	}
	
	public final MarkGroup getMarkGroup() {
		return markGroup;
	}
	
	public final void setMarkGroup(MarkGroup mg) {
//		if (markGroup != null) {
//			markGroup.remove(this);
//		}
		mg.add(this);
		markGroup = mg;
	}
	
	@Override
	public final int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + Arrays.hashCode(vec);
//		return result;
//		final int prime = 31;
//		int result = 1;
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
}
