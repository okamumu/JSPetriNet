package jspetrinet.marking;

import jspetrinet.petri.Trans;

public class MarkingArc extends jspetrinet.graph.Arc {
	
	private final Trans trans;

	public MarkingArc(Mark src, Mark dest, Trans tr) {
		super(src, dest);
		this.trans = tr;
	}
	
	public final Trans getTrans() {
		return trans;
	}

}
