package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public class InArc extends ArcBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8276358301363409993L;

	public InArc(Place src, Trans dest, ASTree multi) {
		super(src, dest, multi);
		setFiring(multi);
	}

}
