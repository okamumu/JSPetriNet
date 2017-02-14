package jspetrinet.marking;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jspetrinet.JSPetriNet;
import jspetrinet.exception.*;
import jspetrinet.graph.Arc;
import jspetrinet.petri.*;

public class MarkingGraph {

	private static String ln = "\n";
	private static String genFormat = "\"%s\" [label=\"GEN(%d)\n %s\"];" + ln;
	private static String immFormat = "\"%s\" [label=\"IMM(%d)\n %s\"];" + ln;
	private static String arcFormat = "\"%s\" -> \"%s\" [label=\"%s\"];" + ln;

	protected Net net;
	protected Mark imark;

	protected final Map<Mark,Mark> markSet;

	protected int numOfGenTrans;
	protected final Map<GenVec,MarkGroup> genGroup;
	protected final Map<GenVec,MarkGroup> immGroup;
	
	private CreateMarking createMarking;
	
	public MarkingGraph() {
		markSet = new HashMap<Mark,Mark>();
		genGroup = new HashMap<GenVec,MarkGroup>();
		immGroup = new HashMap<GenVec,MarkGroup>();
		numOfGenTrans = 0;
	}
	
	public void setCreateMarking(CreateMarking createMarking) {
		this.createMarking = createMarking;
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

	public final Mark getInitialMark() {
		return imark;
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

	public Mark create(Mark init, Net net) throws JSPNException {
		this.net = net;
		this.imark = init;
		markSet.clear();
		numOfGenTrans = net.getNumOfGenTrans();
		immGroup.clear();
		genGroup.clear();		
		Mark ret = this.createMarking.create(init, net);
		this.createMarkGroupGraph();
		return ret;
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

	// mark group
	public final void createMarkGroupGraph() {
		CreateGroupMarkingGraph.createMarkGroupGraph(net, immGroup, genGroup);
	}

	public void dotMarkGroup(PrintWriter bw) {
		bw.println("digraph { layout=dot; overlap=false; splines=true;");
		for (Map.Entry<GenVec, MarkGroup> entry : this.genGroup.entrySet()) {
			bw.printf(genFormat, entry.getValue(),
					entry.getValue().getMarkSet().size(),
					JSPetriNet.genvecToString(net, entry.getKey()));
			for (Arc a : entry.getValue().getOutArc()) {
				MarkingArc ma = (MarkingArc) a;
				if (ma.getTrans() instanceof ExpTrans) {
					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), "EXP(" + ma.getTrans().getLabel() + ")");
				} else {
					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), ma.getTrans().getLabel());
				}
			}
		}
		for (Map.Entry<GenVec, MarkGroup> entry : this.immGroup.entrySet()) {
			bw.printf(immFormat, entry.getValue(),
					entry.getValue().getMarkSet().size(),
					JSPetriNet.genvecToString(net, entry.getKey()));
			for (Arc a : entry.getValue().getOutArc()) {
				MarkingArc ma = (MarkingArc) a;
				bw.printf(arcFormat, ma.getSrc(), ma.getDest(),  "IMM(" + ma.getTrans().getLabel() + ")");
			}
		}
		bw.println("}");
	}
}
