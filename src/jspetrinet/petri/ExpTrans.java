package jspetrinet.petri;

import jspetrinet.ast.AST;

public class ExpTrans extends Trans {
	
	private AST rate;
	
	public ExpTrans(String label, AST rate) {
		super(label);
		this.rate = rate;
	}
	
	// getter
	public final AST getRate() {
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
	
	public final void setRate(AST rate) {
		this.rate = rate;
	}
}
