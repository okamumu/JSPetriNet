package jspetrinet.ast;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jspetrinet.exception.JSPNException;
import jspetrinet.exception.NotFindObjectException;
import jspetrinet.marking.Mark;

public class ASTEnv {
	
	private Mark currentMark;
	private final Map<String,Object> hash;

	public ASTEnv() {
		currentMark = null;
		hash = new HashMap<String,Object>();
	}

	@Override
	public String toString() {
		String linesep = System.getProperty("line.separator").toString();
		String res = "Defined labels:" + linesep;
		for (String s: hash.keySet()) {
			res += s + "; ";
		}
		return res;
	}
	
	// getter
	public Mark getCurrentMark() {
		return currentMark;
	}

	public void setCurrentMark(Mark m) {
		currentMark = m;
	}
	
	public Object get(String label) throws JSPNException {
		if (!hash.containsKey(label)) {
			throw new NotFindObjectException(label);
		}
		return hash.get(label);
	}

	public void put(String label, Object value) {
		hash.put(label, value);
	}
	
	public boolean contains(String label) {
		return hash.containsKey(label);
	}
	
	public Set<Map.Entry<String,Object>> entrySet() {
		return hash.entrySet();
	}

}
