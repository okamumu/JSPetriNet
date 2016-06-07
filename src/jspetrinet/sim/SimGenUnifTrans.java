package jspetrinet.sim;

import jspetrinet.ast.ASTree;
import jspetrinet.petri.GenTransPolicy;

public class SimGenUnifTrans extends SimGenTrans{

	static public GenTransPolicy DefaultPolicy = GenTransPolicy.PRD;
	
	private ASTree lower;
	private ASTree upper;
	
	public SimGenUnifTrans(String label, ASTree lower, ASTree upper, GenTransPolicy policy) {
		super(label, policy);
		this.lower = lower;
		this.upper = upper;
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
}
