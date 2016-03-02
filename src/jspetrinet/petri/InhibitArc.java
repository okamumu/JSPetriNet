package jspetrinet.petri;

import jspetrinet.ast.ASTValue;
import jspetrinet.ast.ASTree;

public final class InhibitArc extends ArcBase {

	public InhibitArc(Place src, Trans dest, ASTree multi) {
		super(src, dest, multi);
		setFiring(new ASTValue(0));
	}

}
