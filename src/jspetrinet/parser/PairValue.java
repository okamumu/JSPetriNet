package jspetrinet.parser;

import jspetrinet.ast.ASTree;

public final class PairValue {
	private final String label;
	private final ASTree value;
	
	public PairValue(String label, ASTree value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public ASTree getValue() {
		return value;
	}

}
