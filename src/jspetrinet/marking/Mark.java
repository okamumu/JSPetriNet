package jspetrinet.marking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jspetrinet.graph.Arc;

public final class Mark extends jspetrinet.graph.Node {

	private int index;
	private final byte[] vec;
	private MarkGroup markGroup;

//	public Mark(Net net, Map<String,Integer> map) {
//		super(map.toString());
//		vec = new int[net.getNumOfPlace()];
//		try {
//			for (Map.Entry<String, Integer> e : map.entrySet()) {
//				vec[net.getPlace(e.getKey()).getIndex()] = e.getValue();
//			}
//		} catch (ASTException e1) {
//			e1.printStackTrace();
//		}
//		markGroup = null;
//	}
//
//	public Mark(int[] vec) {
//		super(Arrays.toString(vec));
//		this.vec = Arrays.copyOf(vec, vec.length);
//		markGroup = null;
//	}

//	public Mark(String label, int[] vec) {
//		super(label);
//		this.vec = Arrays.copyOf(vec, vec.length);
//		markGroup = null;
//	}

	public Mark(int size) {
		this.vec = new byte [size];
		markGroup = null;
	}

	public Mark(Mark m) {
		this.vec = Arrays.copyOf(m.vec, m.vec.length);
		markGroup = null;
	}

	// getter
//	public final int[] get() {
//		return vec;
//	}
//
	public final int get(int i) {
		return vec[i];
	}
	
	public final void set(int i, int v) {
		vec[i] = (byte) v;
	}

	public final int index() {
		return index;
	}

	public final void setIndex(int i) {
		index = i;
	}
	
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
