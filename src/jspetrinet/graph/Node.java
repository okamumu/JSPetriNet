package jspetrinet.graph;

import java.util.ArrayList;
import java.util.List;

public class Node implements Component {

	private final List<Arc> inarc;
	private final List<Arc> outarc;

	// constructor
	public Node() {
		inarc = new ArrayList<Arc>();
		outarc = new ArrayList<Arc>();
	}
	
	public final List<Arc> getInArc() {
		return inarc;
	}

	public final List<Arc> getOutArc() {
		return outarc;
	}
	
	// methods
	
	public final void addInArc(Arc arc) {
		inarc.add(arc);
	}

	public final void addOutArc(Arc arc) {
		outarc.add(arc);
	}

	public final void removeInArc(Arc arc) {
		inarc.remove(arc);
	}

	public final void removeOutArc(Arc arc) {
		outarc.remove(arc);
	}

	//	@Override
//	public final String toString() {
//		return getLabel();
//	}

	@Override
	public final void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
