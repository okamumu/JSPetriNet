package jspetrinet.graph;

abstract public interface Component {
	
	abstract void accept(Visitor visitor);

}
