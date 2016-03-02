package jspetrinet.petri;

import java.io.ByteArrayInputStream;

import jspetrinet.ast.*;
import jspetrinet.exception.ASTException;
import jspetrinet.parser.*;

public class Guard {
	
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
