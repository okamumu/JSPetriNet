package jspetrinet.marking;

import java.util.ArrayList;
import java.util.List;

public class MarkGroup extends jspetrinet.graph.Node {

	private final String id;
	private final String label;
//	private final GenVec genv;
	private final List<Mark> markSet;
	private final boolean imm;
	
	public MarkGroup(String id, String label, boolean imm) {
		this.id = id;
		this.label = label;
//		this.genv = genv;
		this.imm = imm;
		markSet = new ArrayList<Mark>();
	}

//	public MarkGroup(String label, GenVec genv, boolean imm) {
//		this.label = label;
////		this.genv = genv;
////		this.imm = imm;
//		markSet = new ArrayList<Mark>();
//	}
	
	public String getID() {
		return id;
	}

	public String getLabel() {
		return label;
	}
	
//	public final GenVec getGenVec() {
//		return genv;
//	}

	public final void add(Mark m) {
		markSet.add(m);
	}
	
	public final boolean isIMM() {
		return imm;
	}

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
