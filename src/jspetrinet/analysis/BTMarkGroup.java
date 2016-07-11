package jspetrinet.analysis;

import java.util.HashSet;
import java.util.Set;
import jspetrinet.marking.*;

public class BTMarkGroup extends MarkGroup {
	
	private Boolean isTransient;
	private Boolean isSlow;
	
	public BTMarkGroup() {
		super("");
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

//	public final void addExit(Mark m) {
//		exitSet.add(m);
//	}

}
