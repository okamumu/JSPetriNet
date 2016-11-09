package jspetrinet.marking;

import java.util.Arrays;

import jspetrinet.petri.Net;

public final class GenVec implements Comparable<GenVec> {

	private final byte[] vec;

	public GenVec(Net net) {
		int size = net.getGenTransSet().size() + net.getExpTransSet().size();
		this.vec = new byte [size];
	}

	public final int get(int i) {
		return vec[i];
	}
	
	public final void set(int i, int value) {
		vec[i] = (byte) value;
	}
	
	public final void set(int i, byte value) {
		vec[i] = value;
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
		GenVec other = (GenVec) obj;
		if (!Arrays.equals(vec, other.vec))
			return false;
		return true;
	}

	@Override
	public int compareTo(GenVec other) {
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
