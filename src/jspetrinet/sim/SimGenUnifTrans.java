package jspetrinet.sim;

import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.petri.GenTransPolicy;
import jspetrinet.petri.Net;

public class SimGenUnifTrans extends SimGenTrans{
	
	private ASTree lower;
	private ASTree upper;
	
	public SimGenUnifTrans(String label, ASTree lower, ASTree upper, GenTransPolicy policy) {
		super(label, policy);
		this.lower = lower;
		this.upper = upper;
	}

	public final void setLower(ASTree lower) {
		this.lower = lower;
	}

	public final void setUpper(ASTree upper) {
		this.upper = upper;
	}

	@Override
	public double nextTime(Net net, Random rnd) throws ASTException {
		double lower = Utility.convertObjctToDouble(this.lower.eval(net));
		double upper = Utility.convertObjctToDouble(this.upper.eval(net));
		return rnd.nextUnif(lower, upper);
	}
}
