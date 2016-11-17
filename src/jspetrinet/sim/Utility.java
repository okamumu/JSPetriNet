package jspetrinet.sim;

import jspetrinet.exception.JSPNException;
import jspetrinet.exception.TypeMismatch;

public class Utility {

	public static boolean convertObjctToBoolean(Object obj) throws JSPNException{
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		} else {
			throw new TypeMismatch();
		}
	}

	public static double convertObjctToDouble(Object obj) throws JSPNException{
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
