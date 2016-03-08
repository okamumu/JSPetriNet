package jspetrinet.petri;

import jspetrinet.ast.ASTValue;
import jspetrinet.ast.ASTree;

public final class InhibitArc extends ArcBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8082156190163345196L;

	public InhibitArc(Place src, Trans dest, ASTree multi) {
		super(src, dest, multi);
		setFiring(new ASTValue(0));
	}

}
