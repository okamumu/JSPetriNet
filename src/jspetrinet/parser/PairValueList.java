package jspetrinet.parser;

import java.util.ArrayList;
import java.util.List;

public final class PairValueList {

	private final List<PairValue> list;
	
	public PairValueList() {
		list = new ArrayList<PairValue>();
	}
	
	public final List<PairValue> getList() {
		return list;
	}
	
	public final void add(PairValue elem) {
		list.add(elem);
	}

}
