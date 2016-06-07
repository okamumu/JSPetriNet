package jspetrinet.sim;

import jspetrinet.ast.ASTree;
import jspetrinet.petri.GenTransPolicy;

public class SimGenConstTrans extends SimGenTrans{

	static public GenTransPolicy DefaultPolicy = GenTransPolicy.PRD;

	private ASTree constant;
	
	public SimGenConstTrans(String label, ASTree constant, GenTransPolicy policy) {
		super(label, policy);
		this.constant = constant;
	}
	
	public final ASTree getConstant(){
		return constant;
	}

	public final void setConstant(ASTree constant){
		this.constant = constant;
	}
	
}
