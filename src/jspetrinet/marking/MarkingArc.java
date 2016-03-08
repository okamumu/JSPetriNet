package jspetrinet.marking;

import jspetrinet.petri.Trans;

public class MarkingArc extends jspetrinet.graph.Arc {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4486038915018256613L;
	private final Trans trans;

	public MarkingArc(Mark src, Mark dest, Trans tr) {
		super(src, dest);
		this.trans = tr;
	}
	
	public final Trans getTrans() {
		return trans;
	}

}
