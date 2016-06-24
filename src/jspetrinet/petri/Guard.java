package jspetrinet.petri;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import jspetrinet.ast.ASTree;
import jspetrinet.exception.ASTException;
import jspetrinet.parser.JSPetriNetParser;
import jspetrinet.parser.ParseException;
import jspetrinet.parser.TokenMgrError;

public class Guard implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8127649463467917612L;

	static public ASTree createGuard(Net global, String formula) throws ASTException {
		JSPetriNetParser parser = new JSPetriNetParser(new ByteArrayInputStream(formula.getBytes()));
		parser.setNet(global);
		try {
			return parser.getASTree();
		} catch (TokenMgrError ex) {
			System.out.println("token error: " + ex.getMessage());
			return null;
		} catch (ParseException ex) {
			System.out.println("parse error: " + ex.getMessage());			
			return null;
		}		
	}

}
