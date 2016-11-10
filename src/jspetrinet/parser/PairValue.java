package jspetrinet.parser;

import jspetrinet.ast.AST;

public final class PairValue {
	private final String label;
	private final AST value;
	
	public PairValue(String label, AST value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public AST getValue() {
		return value;
	}

}
