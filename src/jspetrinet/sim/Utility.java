package jspetrinet.sim;

import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;

public class Utility {
	public static double convertObjctToDouble(Object obj) throws ASTException{
		double doubleType;
		if(obj instanceof Integer){
			doubleType = (Integer)obj;
		}else if(obj instanceof Double){
			doubleType = (Double)obj;
		}else {
			throw new TypeMismatch();
		}
		return doubleType;
	}
}
