package jspetrinet.petri;

import jspetrinet.ast.AST;

public final class OutArc extends ArcBase {

	public OutArc(Trans src, Place dest, AST multi) {
		super(src, dest, multi);
		setFiring(multi);
	}

}
