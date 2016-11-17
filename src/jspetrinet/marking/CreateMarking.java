package jspetrinet.marking;

import jspetrinet.exception.JSPNException;
import jspetrinet.petri.Net;

public interface CreateMarking {
	
	public Mark create(Mark init, Net net) throws JSPNException;

}
