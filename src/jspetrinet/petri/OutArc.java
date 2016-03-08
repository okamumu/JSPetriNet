package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public final class OutArc extends ArcBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4748898575710265489L;

	public OutArc(Trans src, Place dest, ASTree multi) {
		super(src, dest, multi);
		setFiring(multi);
	}

}
