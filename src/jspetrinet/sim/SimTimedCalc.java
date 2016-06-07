package jspetrinet.sim;

import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public interface SimTimedCalc {
	double nextTime(Trans tr, Net net);
}
