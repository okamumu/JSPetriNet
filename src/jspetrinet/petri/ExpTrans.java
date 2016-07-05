package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public class ExpTrans extends Trans {
	
	private ASTree rate;
	
	public ExpTrans(String label, ASTree rate) {
		super(label);
		this.rate = rate;
	}
	
	// getter
	public final ASTree getRate() {
		return rate;
//		Object result = rate.eval(env);
//		if (result instanceof Double) {
//			return (Double) result;
//		} else if (result instanceof Integer) {
//			return ((Integer) result).doubleValue();
//		} else {
//			throw new TypeMismatch();
//		}
	}
	
	public final void setRate(ASTree rate) {
		this.rate = rate;
	}
}
