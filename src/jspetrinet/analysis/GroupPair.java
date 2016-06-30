package jspetrinet.analysis;

import jspetrinet.marking.MarkGroup;
import jspetrinet.petri.Trans;

class GroupPair {

	MarkGroup g1;
	MarkGroup g2;
	Trans tr;
	
	public GroupPair(MarkGroup g1, MarkGroup g2) {
		this.g1 = g1;
		this.g2 = g2;
		this.tr = null;
	}

	public GroupPair(MarkGroup g1, MarkGroup g2, Trans tr) {
		this.g1 = g1;
		this.g2 = g2;
		this.tr = tr;
	}
	
	public MarkGroup getSrcMarkGroup() {
		return g1;
	}
	
	public MarkGroup getDestMarkGroup() {
		return g2;
	}
	
	public Trans getTrans() {
		return tr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((g1 == null) ? 0 : g1.hashCode());
		result = prime * result + ((g2 == null) ? 0 : g2.hashCode());
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
		GroupPair other = (GroupPair) obj;
		if (g1 == null) {
			if (other.g1 != null)
				return false;
		} else if (!g1.equals(other.g1))
			return false;
		if (g2 == null) {
			if (other.g2 != null)
				return false;
		} else if (!g2.equals(other.g2))
			return false;
		if (tr == null) {
			if (other.tr != null)
				return false;
		} else if (!tr.equals(other.tr))
			return false;
		return true;
	}
}
