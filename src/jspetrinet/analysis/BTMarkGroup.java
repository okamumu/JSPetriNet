package jspetrinet.analysis;

import java.util.HashSet;
import java.util.Set;
import jspetrinet.marking.*;

public class BTMarkGroup extends MarkGroup {
	
	private final Set<Mark> enterSet;
	private final Set<Mark> exitSet;
	private Boolean isTransient;
	private Boolean isSlow;
	
	public BTMarkGroup() {
		super("");
		enterSet = new HashSet<Mark>();
		exitSet = new HashSet<Mark>();
		isTransient = false;
		isSlow = false;
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

	public void clear() {
		super.clear();
		enterSet.clear();
		exitSet.clear();
		isTransient = false;
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
