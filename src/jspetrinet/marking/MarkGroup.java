package jspetrinet.marking;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MarkGroup extends jspetrinet.graph.Node {

	private final String label;
	private final Set<Mark> markSet;
	
//	private final Map<MarkGroup,Set<Mark>> enterSet;
//	private final Map<MarkGroup,Set<Mark>> exitSet;

	public MarkGroup(String label) {
		this.label = label;
		markSet = new HashSet<Mark>();
//		enterSet = new HashMap<MarkGroup,Set<Mark>>();
//		exitSet = new HashMap<MarkGroup,Set<Mark>>();
	}
	
//	@Override
//	public String toString() {
//		return label;
//	}
	
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
	
//	public final Map<MarkGroup,Set<Mark>> getEnterMarkSet() {
//		return enterSet;
//	}
//
//	public final Map<MarkGroup,Set<Mark>> getExitMarkSet() {
//		return exitSet;
//	}
}
