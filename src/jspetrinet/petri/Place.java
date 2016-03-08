package jspetrinet.petri;

import jspetrinet.graph.LabeledNode;

public class Place extends LabeledNode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3638774403733132484L;

	static public final int DefaultMax = 10;
	
	private int index;
	private final int max;
	
	public Place(String label, int max) {
		super(label);
		index = 0;
		this.max = max;
	}
	
	// getter
	public final int getIndex() {
		return index;
	}
	
	public final void setIndex(int index) {
		this.index = index;
	}
	
	public final int getMax() {
		return max;
	}

}
