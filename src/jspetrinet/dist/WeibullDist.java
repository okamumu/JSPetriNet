package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jp.rel.jmtrandom.Random;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.sim.Utility;

public class WeibullDist extends Dist {
	
	public static final String dname = "weibull";

	private AST scale;
	private AST shape;
	private Object scaleObj;
	private Object shapeObj;

	public WeibullDist(AST shape, AST scale) {
		this.scale = scale;
		this.shape = shape;
	}
	
	public final AST getScale() {
		return scale;
	}

	public final AST getShape() {
		return shape;
	}
	
	public final void setParam(AST shape, AST scale) {
		this.scale = scale;
		this.shape = shape;
	}

	@Override
	public void updateObj(ASTEnv env) throws JSPNException {
		scaleObj = scale.eval(env);
		shapeObj = shape.eval(env);
	}

	@Override
	public String toString() {
		return dname + "(" + shapeObj + "," + scaleObj + ")";
	}

	@Override
	public double nextImpl(Random rnd) throws JSPNException {
		double scale_value = Utility.convertObjctToDouble(scaleObj);
		double shape_value = Utility.convertObjctToDouble(shapeObj);
		return rnd.nextWeibull(shape_value, scale_value);
	}
}
