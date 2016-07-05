package jspetrinet.marking;

import java.util.HashSet;
import java.util.Set;

public class MarkGroup extends jspetrinet.graph.Node {

	private final String label;
	private final Set<Mark> markSet;
	
	public MarkGroup(String label) {
		this.label = label;
		markSet = new HashSet<Mark>();
	}
	
	@Override
	public String toString() {
		return label;
	}

	public final void add(Mark m) {
		markSet.add(m);
	}

	public final void remove(Mark m) {
		markSet.remove(m);
	}

	public final boolean contains(Mark m) {
		return markSet.contains(m);
	}
	
	public final int size() {
		return markSet.size();
	}
	
	public void clear() {
		markSet.clear();
	}
	
	public final Set<Mark> getMarkSet() {
		return markSet;
	}
	
}
