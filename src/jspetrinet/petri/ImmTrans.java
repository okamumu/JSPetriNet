package jspetrinet.petri;

import jspetrinet.ast.ASTree;

public final class ImmTrans extends Trans {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2310124733004675596L;
	private ASTree weight;
	
	public ImmTrans(String label, ASTree weight) {
		super(label);
		this.weight = weight;
	}
	
	// getter	
	public final ASTree getWeight() {
		return weight;
//		Object result = weight.eval(env);
//		if (result instanceof Double) {
//			return (Double) result;
//		} else if (result instanceof Integer) {
//				return ((Integer) result).doubleValue();
//		} else {
//			throw new TypeMismatch();
//		}
	}
	
	public final void setWeight(ASTree weight) {
		this.weight = weight;
	}

}
