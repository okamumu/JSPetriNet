package jspetrinet.petri;

import jspetrinet.ast.AST;

public final class ImmTrans extends Trans {
	
	private AST weight;
	
	public ImmTrans(String label, AST weight) {
		super(label);
		this.weight = weight;
	}
	
	// getter	
	public final AST getWeight() {
		return weight;
	}
	
	public final void setWeight(AST weight) {
		this.weight = weight;
	}

}
