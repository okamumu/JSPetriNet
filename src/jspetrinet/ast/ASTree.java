package jspetrinet.ast;

import jspetrinet.exception.*;

abstract public class ASTree {

	abstract public Object eval(ASTEnv env) throws ASTException;

}
