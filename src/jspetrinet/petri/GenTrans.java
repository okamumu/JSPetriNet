package jspetrinet.petri;

import jspetrinet.ast.AST;

public class GenTrans extends Trans {

	static public GenTransPolicy DefaultPolicy = GenTransPolicy.PRD;
	
	private AST dist;
	private GenTransPolicy policy;
	
	public GenTrans(String label, AST dist, GenTransPolicy policy) {
		super(label);
		this.dist = dist;
		this.policy = policy;
	}
	
	public GenTrans(String label, GenTransPolicy policy) {
		super(label);
		this.policy = policy;
	}

	// getter
	public final AST getDist() {
		return dist;
	}
	
	public final GenTransPolicy getPolicy() {
		return policy;
	}

	// setter
	public final void setDist(AST dist) {
		this.dist = dist;
	}
	
	public final void setPolicy(GenTransPolicy policy) {
		this.policy = policy;
	}
}
