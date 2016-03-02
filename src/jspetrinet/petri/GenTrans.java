package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public final class GenTrans extends Trans {

	static public GenTransPolicy DefaultPolicy = GenTransPolicy.PRD;
	
	private ASTree dist;
	private GenTransPolicy policy;
	
	public GenTrans(String label, ASTree dist, GenTransPolicy policy) {
		super(label);
		this.dist = dist;
		this.policy = policy;
	}
	
	// getter
	public final ASTree getDist() {
		return dist;
	}
	
	public final void setDist(ASTree dist) {
		this.dist = dist;
	}
	
	public final GenTransPolicy getPolicy() {
		return policy;
	}

	public final void setPolicy(GenTransPolicy policy) {
		this.policy = policy;
	}
}
