package jspetrinet.ast;

import java.io.Serializable;

import jspetrinet.exception.*;

abstract public class ASTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4220156790250996863L;

	abstract public Object eval(ASTEnv env) throws ASTException;

}
