package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public class GenTrans extends Trans {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4237973155665080274L;

	static public GenTransPolicy DefaultPolicy = GenTransPolicy.PRD;
	
	private ASTree dist;
	private GenTransPolicy policy;
	
	public GenTrans(String label, ASTree dist, GenTransPolicy policy) {
		super(label);
		this.dist = dist;
		this.policy = policy;
	}
	
	public GenTrans(String label, GenTransPolicy policy) {
		super(label);
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
