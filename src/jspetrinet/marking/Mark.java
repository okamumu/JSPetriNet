package jspetrinet.marking;

import java.util.Arrays;

public final class Mark extends jspetrinet.graph.Node implements Comparable<Mark> {

	private final int[] vec;
	private GenVec genvec;
	private boolean imm;
	private int depth;

	public Mark(int size) {
		this.vec = new int [size];
		this.genvec = null;
		this.imm = false;
	}

	public Mark(Mark m) {
		this.vec = Arrays.copyOf(m.vec, m.vec.length);
		this.genvec = null;
	}

	// imm

	public final void setIMM() {
		this.imm = true;
	}

	public final void setGEN() {
		this.imm = false;
	}
	
	public final boolean isIMM() {
		return this.imm;
	}

	// depth
	public final void setDepth(int depth) {
		this.depth = depth;
	}
	
	public final int getDepth() {
		return depth;
	}
	
	// get vec value

	public final int get(int i) {
		return vec[i];
	}
	
	public final void set(int i, int v) {
		vec[i] = v;
	}
	
	// genvec

	public final GenVec getGenVec() {
		return this.genvec;
	}

	public final void setGroup(GenVec genvec) {
		this.genvec = genvec;
	}

//	public final List<Mark> next() {
//		List<Mark> next = new ArrayList<Mark>();
//		for (Arc a: getOutArc()) {
//			next.add((Mark) a.getDest());
//		}
//		return next;
//	}

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
