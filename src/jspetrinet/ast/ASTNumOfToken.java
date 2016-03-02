package jspetrinet.ast;

import jspetrinet.petri.Place;

public class ASTNumOfToken extends ASTree {

	private final Place place;

	public ASTNumOfToken(Place p) {
		this.place = p;
	}
	
	public final Place getPlace() {
		return place;
	}
	
	@Override
	public Object eval(ASTEnv env) {
		return env.getCurrentMark().get(place.getIndex());
	}
}
