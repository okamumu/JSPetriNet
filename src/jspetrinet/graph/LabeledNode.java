package jspetrinet.graph;

public class LabeledNode extends Node {
	
	private final String label;
	
	public LabeledNode(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

}
