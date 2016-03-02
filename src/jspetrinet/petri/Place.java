package jspetrinet.petri;

import jspetrinet.graph.Node;

public class Place extends Node {
	
	static public int DefaultMax = 10;
	
	private int index;
	private int max;
	
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

	public void setMax(int max) {
		this.max = max;
	}
	
}
