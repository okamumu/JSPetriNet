package jspetrinet.marking;

import jspetrinet.petri.Trans;

public final class MarkMarkTrans {
	private final Mark src;
	private final Mark dest;
	private final Trans tr;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
		result = prime * result + ((tr == null) ? 0 : tr.hashCode());
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
		MarkMarkTrans other = (MarkMarkTrans) obj;
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.equals(other.dest))
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		if (tr == null) {
			if (other.tr != null)
				return false;
		} else if (!tr.equals(other.tr))
			return false;
		return true;
	}

	public MarkMarkTrans(Mark src, Mark dest, Trans tr) {
		this.src = src;
		this.dest = dest;
		this.tr = tr;
	}
	
	public final Mark getSrc() {
		return src;
	}

	public final Mark getDest() {
		return dest;
	}

	public final Trans getTrans() {
		return tr;
	}
}

