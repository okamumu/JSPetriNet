package jspetrinet.marking;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MarkGroup extends jspetrinet.graph.Node {

	private final String label;
	private final Set<Mark> markSet;
	
	public MarkGroup(String label) {
		this.label = label;
		markSet = new HashSet<Mark>();
	}
	
	public String getLabel() {
		return label;
	}

	public final void add(Mark m) {
		markSet.add(m);
	}

	public final void addAll(Collection<Mark> m) {
		markSet.addAll(m);
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
	
	public final Set<Mark> getMarkSet() {
		return markSet;
	}
}
