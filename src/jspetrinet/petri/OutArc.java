package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public final class OutArc extends ArcBase {

	public OutArc(Trans src, Place dest, ASTree multi) {
		super(src, dest, multi);
		setFiring(multi);
	}

}
