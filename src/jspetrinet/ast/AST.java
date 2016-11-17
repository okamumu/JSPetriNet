package jspetrinet.ast;

import jspetrinet.exception.*;

abstract public class AST {

	abstract public Object eval(ASTEnv env) throws JSPNException;

}
