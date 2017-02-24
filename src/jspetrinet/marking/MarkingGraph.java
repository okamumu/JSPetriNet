package jspetrinet.marking;

import java.io.PrintWriter;

import jspetrinet.JSPetriNet;
import jspetrinet.graph.Arc;
import jspetrinet.petri.*;

public class MarkingGraph {

	protected Net net;
	protected Mark imark;

//	private final Map<GenVec,MarkGroup> genGroup;
//	private final Map<GenVec,MarkGroup> immGroup;

	private final MarkGroup genGroup;
	private final MarkGroup immGroup;
	
//	private CreateMarking createMarking;
	
	public MarkingGraph(Net net) {
		this.net = net;
		immGroup = new MarkGroup("I0", "Imm", true);
		genGroup = new MarkGroup("G0", "Gen", false);
	}
	
	public final Net getNet() {
		return net;
	}
	
	public final void setInitialMark(Mark imark) {
		this.imark = imark;
	}
	

	public final Mark getInitialMark() {
		return imark;
	}

	public final int immSize() {
		return immGroup.size();
	}
	
	public final int genSize() {
		return genGroup.size();
	}

//	public final Map<GenVec,MarkGroup> getImmGroup() {
//		return immGroup;
//	}
//
//	public final Map<GenVec,MarkGroup> getGenGroup() {
//		return genGroup;
//	}

	public final MarkGroup getImmGroup() {
		return immGroup;
	}
	
	public final MarkGroup getGenGroup() {
		return genGroup;
	}

	private static String ln = "\n";
	private static String genFormat = "\"%s\" [label=\"%s\n%s\"];" + ln;
	private static String immFormat = "\"%s\" [label=\"%s\n%s\"];" + ln;
	private static String arcFormat = "\"%s\" -> \"%s\" [label=\"%s\"];" + ln;

	public void dotMarking(PrintWriter bw) {
		bw.println("digraph { layout=dot; overlap=false; splines=true;");
		for (Mark m : immGroup.getMarkSet()) {
			bw.printf(immFormat, m,
					JSPetriNet.markToString(net, m),
					JSPetriNet.genvecToString(net, m.getGenVec()));
			for (Arc a : m.getOutArc()) {
				MarkingArc ma = (MarkingArc) a;
				bw.printf(arcFormat, ma.getSrc(), ma.getDest(), ma.getTrans().getLabel());
			}
		}
		for (Mark m : genGroup.getMarkSet()) {
			bw.printf(genFormat, m,
					JSPetriNet.markToString(net, m),
					JSPetriNet.genvecToString(net, m.getGenVec()));
			for (Arc a : m.getOutArc()) {
				MarkingArc ma = (MarkingArc) a;
				bw.printf(arcFormat, ma.getSrc(), ma.getDest(), ma.getTrans().getLabel());
			}
		}
		bw.println("}");
	}
}
