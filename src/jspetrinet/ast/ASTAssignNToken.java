package jspetrinet.ast;

import jspetrinet.exception.JSPNException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.marking.Mark;
import jspetrinet.petri.Place;

public class ASTAssignNToken implements AST {
	
	private final Place place;
	private final AST right;

	public ASTAssignNToken(Place place, AST right) {
		this.place = place;
		this.right = right;
	}

	@Override
	public Object eval(ASTEnv env) throws JSPNException {
		Mark m = env.getCurrentMark();
		Object obj = right.eval(env);
		if (obj instanceof Integer) {
			int tmp = (Integer) obj;
			m.set(place.getIndex(), tmp);
			return tmp;
		} else if (obj instanceof String) {
			return "#" + place.getLabel() + "=" + obj.toString();
		} else {
			throw new TypeMismatch();
		}
	}
	
	@Override
	public String toString() {
		return "#" + place.getLabel() + "=" + right.toString();
	}
}
