package jspetrinet.marking;

import java.util.HashSet;
import java.util.Set;

public class MarkGroup {
	
	private final Set<Mark> markSet;
	private final Set<Mark> enterSet;
	private final Set<Mark> exitSet;
	private Boolean isTransient;
	private Boolean isSlow;
	
	public MarkGroup() {
		markSet = new HashSet<Mark>();
		enterSet = new HashSet<Mark>();
		exitSet = new HashSet<Mark>();
		isTransient = false;
		isSlow = false;
	}
	
	@Override
	public String toString() {
		return "#" + markSet.size();
	}

	public final void setTransient(boolean b) {
		isTransient = b;
	}
	
	public final boolean isTransient() {
		return isTransient;
	}

	public final void setSlow(boolean b) {
		isSlow = b;
	}
	
	public final boolean isSlow() {
		return isSlow;
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
	
	public final void clear() {
		markSet.clear();
		exitSet.clear();
		isTransient = false;
	}
	
	public final Set<Mark> markset() {
		return markSet;
	}
	
	public final Set<Mark> getEnterMarkSet() {
		return enterSet;
	}

	public final Set<Mark> getExitMarkSet() {
		return exitSet;
	}

//	public final void addExit(Mark m) {
//		exitSet.add(m);
//	}

}
