package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.AST;
import jspetrinet.exception.ASTException;
import jspetrinet.sim.Random;

abstract public class Dist extends AST {
	
	@Override
	public final Object eval(ASTEnv env) throws ASTException {
		updateObj(env);
		return this;
	}

	public final double next(ASTEnv env, Random rnd) throws ASTException {
		this.eval(env);
		return nextImpl(rnd);
	}

	abstract public void updateObj(ASTEnv env) throws ASTException;
	abstract public double nextImpl(Random rnd) throws ASTException;

}
