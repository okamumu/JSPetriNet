package jspetrinet.graph;

public class LabeledNode extends Node {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3711795635339517104L;
	private final String label;
	
	public LabeledNode(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

}
