package jspetrinet.marking;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jspetrinet.JSPetriNet;
import jspetrinet.exception.*;
import jspetrinet.graph.Arc;
import jspetrinet.petri.*;

public class MarkingGraph {

	protected Net net;

	protected final Map<Mark,Mark> markSet;

	protected int numOfGenTrans;
	protected final Map<GenVec,MarkGroup> genGroup;
	protected final Map<GenVec,MarkGroup> immGroup;
	
	private CreateMarking creteMarking;
	
	public MarkingGraph() {
		markSet = new HashMap<Mark,Mark>();
		genGroup = new HashMap<GenVec,MarkGroup>();
		immGroup = new HashMap<GenVec,MarkGroup>();
		numOfGenTrans = 0;
	}
	
	public void setCreateMarking(CreateMarking createMarking) {
		this.creteMarking = createMarking;
	}
	
	public final int size() {
		return markSet.size();
	}
	
//	public final List<Mark> getMarkList() {
//		return new ArrayList<Mark>(markSet.keySet());
//	}
	
	public final int immSize() {
		int total = 0;
		for (MarkGroup mg: immGroup.values()) {
			total += mg.size();
		}
		return total;
	}
	
	public final Net getNet() {
		return net;
	}

	public final Map<GenVec,MarkGroup> getImmGroup() {
		return immGroup;
	}

	public final Map<GenVec,MarkGroup> getGenGroup() {
		return genGroup;
	}

//	public final MarkGroup getExpGroup() {
//		return genGroup.get(new GenVec(numOfGenTrans));
//	}
	
	public final boolean containtsMark(Mark m) {
		return markSet.containsKey(m);
	}
	
	public final void addMark(Mark m) {
		markSet.put(m, m);
	}

	public Mark create(Mark init, Net net) throws ASTException {
		this.net = net;
		markSet.clear();
		numOfGenTrans = net.getNumOfGenTrans();
		immGroup.clear();
		genGroup.clear();		
		return this.creteMarking.create(init, net);
	}

	public void dotMarking(PrintWriter bw) {
		bw.println("digraph { layout=dot; overlap=false; splines=true;");
		for (Mark m : markSet.keySet()) {
			bw.println("\"" + m + "\" [label = \""
					+ JSPetriNet.markToString(net, m) + "\"];");
			for (Arc a : m.getOutArc()) {
				bw.println("\"" + a.getSrc() + "\" -> \"" + a.getDest() + "\";");
			}
		}
		bw.println("}");
	}

	public void dotMarkGroup(PrintWriter bw) {
		bw.println("digraph { layout=dot; overlap=false; splines=true;");
		for (Map.Entry<GenVec, MarkGroup> entry : this.genGroup.entrySet()) {
			bw.println("\"" + entry.getValue() + "\" [label = \"GEN "
					+ JSPetriNet.genvecToString(net, entry.getKey()) + "\"];");
			for (Arc a : entry.getValue().getOutArc()) {
				bw.println("\"" + a.getSrc() + "\" -> \"" + a.getDest() + "\";");
			}
		}
		for (Map.Entry<GenVec, MarkGroup> entry : this.immGroup.entrySet()) {
			bw.println("\"" + entry.getValue() + "\" [label = \"IMM "
					+ JSPetriNet.genvecToString(net, entry.getKey()) + "\"];");
			for (Arc a : entry.getValue().getOutArc()) {
				bw.println("\"" + a.getSrc() + "\" -> \"" + a.getDest() + "\";");
			}
		}
		bw.println("}");
	}
}
