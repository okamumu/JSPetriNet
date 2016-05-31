package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public final class UnifTrans extends Trans {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4237973155665080274L;

	static public GenTransPolicy DefaultPolicy = GenTransPolicy.PRD;
	
	private ASTree lower;
	private ASTree upper;
	private GenTransPolicy policy;
	
	public UnifTrans(String label, ASTree lower, ASTree upper, GenTransPolicy policy) {
		super(label);
		this.lower = lower;
		this.upper = upper;
		this.policy = policy;
	}
	
	// getter
	public final ASTree getLower() {
		return lower;
	}
	
	public final ASTree getUpper() {
		return upper;
	}
	
	public final void setLower(ASTree lower) {
		this.lower = lower;
	}
	
	public final void setUpper(ASTree upper) {
		this.upper = upper;
	}
	
	public final GenTransPolicy getPolicy() {
		return policy;
	}

	public final void setPolicy(GenTransPolicy policy) {
		this.policy = policy;
	}
}
