package jspetrinet.marking;

import jspetrinet.petri.Trans;

final class MarkMarkTrans {
	private final Mark src;
	private final Mark dest;
	private final Trans tr;
	
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

