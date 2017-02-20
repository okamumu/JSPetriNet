package jspetrinet.marking;

import java.util.ArrayList;
import java.util.List;

public class MarkGroup extends jspetrinet.graph.Node {

	private final String label;
	private final List<Mark> markSet;
	
	public MarkGroup(String label) {
		this.label = label;
		markSet = new ArrayList<Mark>();
	}
	
	public String getLabel() {
		return label;
	}

	public final void add(Mark m) {
		markSet.add(m);
	}

//	public final void addAll(Collection<Mark> m) {
//		markSet.addAll(m);
//	}

//	public final void remove(Mark m) {
//		markSet.remove(m);
//	}

//	public final boolean contains(Mark m) {
//		return markSet.contains(m);
//	}
	
	public final int size() {
		return markSet.size();
	}
	
	public final List<Mark> getMarkSet() {
		return markSet;
	}
}
