package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public class InArc extends ArcBase {

	public InArc(Place src, Trans dest, ASTree multi) {
		super(src, dest, multi);
		setFiring(multi);
	}

}
