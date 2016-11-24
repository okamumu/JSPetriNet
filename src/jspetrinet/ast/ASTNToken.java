package jspetrinet.ast;

import jspetrinet.petri.Place;

public class ASTNToken implements AST {

	private final Place place;

	public ASTNToken(Place p) {
		this.place = p;
	}
	
	public final Place getPlace() {
		return place;
	}
	
	@Override
	public Object eval(ASTEnv env) {
		if (env.getCurrentMark() == null) {
			return "#" + place.getLabel();
		} else {
			return env.getCurrentMark().get(place.getIndex());
		}
	}
}
