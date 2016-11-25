package jspetrinet.graph;

public class Arc implements Component {
	
	private final Node src;
	private final Node dest;

	public Arc(Node src, Node dest) {
		this.src = src;
		this.dest = dest;
		src.addOutArc(this);
		dest.addInArc(this);
	}

	// getter
	public final Node getSrc() {
		return src;
	}
	
	public final Node getDest() {
		return dest;
	}
	
//	public final Arc delete() {
//		src.removeOutArc(this);
//		dest.removeInArc(this);
//		return this;
//	}
//	
	@Override
	public final void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
