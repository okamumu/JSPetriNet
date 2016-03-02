package jspetrinet.marking;

import java.util.Arrays;

public final class GenVec {

	private final String label;
	private final int[] vec;

//	public GenVec(String label, int length) {
//		this.label = label;
//		this.vec = new int [length];
//	}

	public GenVec(String label, int[] v) {
		this.label = label;
		this.vec = v;
	}

	// getter
	public final int[] get() {
		return vec;
	}

	public final int get(int i) {
		return vec[i];
	}
	
	public final void set(int i, int value) {
		vec[i] = value;
	}
	
	@Override
	public String toString() {
		return label;
//		return Arrays.toString(vec);
	}
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(vec);
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
		GenVec other = (GenVec) obj;
		if (!Arrays.equals(vec, other.vec))
			return false;
		return true;
	}
}
