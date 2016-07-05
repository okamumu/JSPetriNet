package jspetrinet.marking;

import jspetrinet.graph.Node;
import jspetrinet.petri.Trans;

public class MarkingArc extends jspetrinet.graph.Arc {
	
	private final Trans trans;

	public MarkingArc(Node src, Node dest, Trans tr) {
		super(src, dest);
		this.trans = tr;
	}
	
	public final Trans getTrans() {
		return trans;
	}

}
