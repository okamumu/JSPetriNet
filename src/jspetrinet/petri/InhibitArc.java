package jspetrinet.petri;

import jspetrinet.ast.ASTValue;
import jspetrinet.ast.AST;

public final class InhibitArc extends ArcBase {

	public InhibitArc(Place src, Trans dest, AST multi) {
		super(src, dest, multi);
		setFiring(new ASTValue(0));
	}

}
