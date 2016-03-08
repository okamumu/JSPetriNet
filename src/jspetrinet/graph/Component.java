package jspetrinet.graph;

import java.io.Serializable;

abstract public interface Component extends Serializable {
	
	abstract void accept(Visitor visitor);

}
