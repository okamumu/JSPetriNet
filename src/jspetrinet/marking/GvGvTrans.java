package jspetrinet.marking;

import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Trans;

final class GvGvTrans {
	private final GenVec src;
	private final GenVec dest;
	private final Trans tr;
	
	public GvGvTrans(GenVec src, GenVec dest, Trans tr) {
		this.src = src;
		this.dest = dest;
		this.tr = tr;
	}
	
	public final GenVec getSrc() {
		return src;
	}
	
	public final GenVec getDest() {
		return dest;
	}
	
	public final Trans getTrans() {
		return tr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dest.hashCode();
		result = prime * result + src.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		GvGvTrans other = (GvGvTrans) obj;
		if (!src.equals(other.src)) {
			return false;
		}
		if (!dest.equals(other.dest)) {
			return false;
		}
		if (tr instanceof ImmTrans && other.tr instanceof ImmTrans) {
			return true;
		}
		if (tr instanceof ExpTrans && other.tr instanceof ExpTrans) {
			return true;
		}
		if (tr != other.tr) {
			return false;
		}
		return true;
	}
}
