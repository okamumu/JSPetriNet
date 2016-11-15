package jspetrinet.petri;

import jspetrinet.ast.AST;

public class InArc extends ArcBase {

	public InArc(Place src, Trans dest, AST multi) {
		super(src, dest, multi);
		setFiring(multi);
	}

}
