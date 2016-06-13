package jspetrinet.sim;

import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
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

	public void setLower(ASTree lower) {
		this.lower = lower;
	}

	public void setUpper(ASTree upper) {
		this.upper = upper;
	}

	@Override
	public double nextTime(Net net, Random rnd) throws ASTException {
		Object obj = lower.eval(net);
		double lower;
		if(obj instanceof Integer){
			lower = (Integer)obj;
		}else if(obj instanceof Double){
			lower = (Double)obj;
		}else {
			throw new TypeMismatch();
		}
		obj = upper.eval(net);
		double upper;
		if(obj instanceof Integer){
			upper = (Integer)obj;
		}else if(obj instanceof Double){
			upper = (Double)obj;
		}else {
			throw new TypeMismatch();
		}
		return rnd.nextUnif()%(upper - lower + 1) + lower;
	}
}
