package jspetrinet.marking;

import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;

public interface CreateMarking {
	
	public Mark create(Mark init, Net net) throws ASTException;

}
