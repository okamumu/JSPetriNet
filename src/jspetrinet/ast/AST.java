package jspetrinet.ast;

import jspetrinet.exception.*;

public interface AST {

	Object eval(ASTEnv env) throws JSPNException;

}
