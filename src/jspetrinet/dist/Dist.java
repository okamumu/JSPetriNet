package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jp.rel.jmtrandom.Random;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;

abstract public class Dist implements AST {
	
	@Override
	public final Object eval(ASTEnv env) throws JSPNException {
		updateObj(env);
		return this;
	}

	public final double next(ASTEnv env, Random rnd) throws JSPNException {
		this.eval(env);
		return nextImpl(rnd);
	}

	abstract public void updateObj(ASTEnv env) throws JSPNException;
	abstract public double nextImpl(Random rnd) throws JSPNException;

}
