package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.sim.Random;

abstract public class Dist extends ASTree {
	
	private ASTEnv env;
	
	protected final ASTEnv getEnv() {
		return env;
	}

	@Override
	public final Object eval(ASTEnv env) throws ASTException {
		if (this.env != env) {
			this.env = env;
			updateObj();
		}
		return this;
	}

	public final double next(ASTEnv env, Random rnd) throws ASTException {
		this.eval(env);
		return nextImpl(rnd);
	}

	abstract public void updateObj() throws ASTException;
	abstract public double nextImpl(Random rnd) throws ASTException;

}
