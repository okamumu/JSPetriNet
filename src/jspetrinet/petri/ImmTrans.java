package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public final class ImmTrans extends Trans {
	
	private ASTree weight;
	
	public ImmTrans(String label, ASTree weight) {
		super(label);
		this.weight = weight;
	}
	
	// getter	
	public final ASTree getWeight() {
		return weight;
	}
	
	public final void setWeight(ASTree weight) {
		this.weight = weight;
	}

}
