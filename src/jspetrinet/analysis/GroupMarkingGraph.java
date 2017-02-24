package jspetrinet.analysis;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jspetrinet.JSPetriNet;
import jspetrinet.graph.Arc;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;

public class GroupMarkingGraph {
	
	private final Net net;
	private final MarkingGraph markGraph;
	private final List<MarkGroup> allGroup;
	private final Map<Mark,MarkGroup> revMarkGroup;

	public GroupMarkingGraph(MarkingGraph markGraph) {
		this.net = markGraph.getNet();
		this.markGraph = markGraph;
		allGroup = new ArrayList<MarkGroup>();
		revMarkGroup = new HashMap<Mark,MarkGroup>();
	}
	
	public final Net getNet() {
		return net;
	}
	
	public final MarkingGraph getMarkingGraph() {
		return markGraph;
	}
	
	public List<MarkGroup> getAllMarkGroupList() {
		return allGroup;
	}

	public MarkGroup getMarkGroup(Mark m) {
		return revMarkGroup.get(m);
	}

	public void makeGroup() {
		this.setIndex();
		createMarkGroupGraph();
	}

	private void setIndex() {
		Set<GenVec> allgen = new TreeSet<GenVec>();
		Map<GenVec,MarkGroup> immGroup = new HashMap<GenVec,MarkGroup>();
		Map<GenVec,MarkGroup> genGroup = new HashMap<GenVec,MarkGroup>();

		int ix = 0;
		int gx = 0;
		for (Mark m : markGraph.getImmGroup().getMarkSet()) {
			GenVec genv = m.getGenVec();
			MarkGroup mg;
			if (!immGroup.containsKey(genv)) {
				mg = new MarkGroup("I"+ix, "Imm: " + JSPetriNet.genvecToString(net, genv), true);
				immGroup.put(genv, mg);
				allgen.add(genv);
				ix++;
			} else {
				mg = immGroup.get(genv);
			}
			mg.add(m);
			revMarkGroup.put(m, mg);
		}
		for (Mark m : markGraph.getGenGroup().getMarkSet()) {
			GenVec genv = m.getGenVec();
			MarkGroup mg;
			if (!genGroup.containsKey(genv)) {
				mg = new MarkGroup("G"+gx, "Gen: " + JSPetriNet.genvecToString(net, genv), false);
				genGroup.put(genv, mg);
				allgen.add(genv);
				gx++;
			} else {
				mg = genGroup.get(genv);
			}
			mg.add(m);
			revMarkGroup.put(m, mg);
		}
		
		for (GenVec genv : allgen) {
			if (immGroup.containsKey(genv)) {
				allGroup.add(immGroup.get(genv));
			}
			if (genGroup.containsKey(genv)) {
				allGroup.add(immGroup.get(genv));
			}
		}
	}

	private void createMarkGroupGraph() {
		Set<GvGvTrans> setlinks = new HashSet<GvGvTrans>();
		for (Mark src : markGraph.getImmGroup().getMarkSet()) {
			MarkGroup srcG = revMarkGroup.get(src);
			for (Arc a : src.getOutArc()) {
				MarkingArc ma = (MarkingArc) a;
				Mark dest = (Mark) ma.getDest();
				MarkGroup destG = revMarkGroup.get(dest);
				GvGvTrans gvtrans = new GvGvTrans(srcG, destG, ma.getTrans());
				if (srcG != destG && !setlinks.contains(gvtrans)) {
					new MarkingArc(srcG, destG, ma.getTrans());
					setlinks.add(gvtrans);
				}
			}
		}
		for (Mark src : markGraph.getGenGroup().getMarkSet()) {
			MarkGroup srcG = revMarkGroup.get(src);
			for (Arc a : src.getOutArc()) {
				MarkingArc ma = (MarkingArc) a;
				Mark dest = (Mark) ma.getDest();
				MarkGroup destG = revMarkGroup.get(dest);
				GvGvTrans gvtrans = new GvGvTrans(srcG, destG, ma.getTrans());
				if (srcG != destG && !setlinks.contains(gvtrans)) {
					new MarkingArc(srcG, destG, ma.getTrans());
					setlinks.add(gvtrans);
				}
			}			
		}
	}

	private static String ln = "\n";
	private static String genFormatG = "\"%s\" [label=\"%s(%d)\n %s\"];" + ln;
	private static String immFormatG = "\"%s\" [label=\"%s(%d)\n %s\"];" + ln;
	private static String arcFormat = "\"%s\" -> \"%s\" [label=\"%s\"];" + ln;

	public void dotMarkGroup(PrintWriter bw) {
		bw.println("digraph { layout=dot; overlap=false; splines=true;");
		for (MarkGroup mg : allGroup) {
			if (mg.isIMM()) {
				bw.printf(immFormatG, mg,
						mg.getID(),
						mg.getMarkSet().size(), mg.getLabel());
			} else {
				bw.printf(immFormatG, mg,
						mg.getID(),
						mg.getMarkSet().size(), mg.getLabel());
			}
			for (Arc a : mg.getOutArc()) {
				MarkingArc ma = (MarkingArc) a;
				if (ma.getTrans() instanceof ImmTrans) {
					bw.printf(arcFormat, ma.getSrc(), ma.getDest(),  "IMM(" + ma.getTrans().getLabel() + ")");					
				} else if (ma.getTrans() instanceof ExpTrans) {
					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), "EXP(" + ma.getTrans().getLabel() + ")");
				} else {
					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), ma.getTrans().getLabel());
				}
			}
		}
		bw.println("}");
	}
////		CreateGroupMarkingGraph.createMarkGroupGraph(mp.getNet(), immGroup, genGroup);
//		for (Map.Entry<GenVec, MarkGroup> entry : this.genGroup.entrySet()) {
//			bw.printf(genFormatG, entry.getValue(),
//					entry.getValue().getID(),
//					entry.getValue().getMarkSet().size(),
//					JSPetriNet.genvecToString(net, entry.getKey()));
//			for (Arc a : entry.getValue().getOutArc()) {
//				MarkingArc ma = (MarkingArc) a;
//				if (ma.getTrans() instanceof ExpTrans) {
//					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), "EXP(" + ma.getTrans().getLabel() + ")");
//				} else {
//					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), ma.getTrans().getLabel());
//				}
//			}
//		}
//		for (Map.Entry<GenVec, MarkGroup> entry : this.immGroup.entrySet()) {
//			bw.printf(immFormatG, entry.getValue(),
//					entry.getValue().getID(),
//					entry.getValue().getMarkSet().size(),
//					JSPetriNet.genvecToString(net, entry.getKey()));
//			for (Arc a : entry.getValue().getOutArc()) {
//				MarkingArc ma = (MarkingArc) a;
//				bw.printf(arcFormat, ma.getSrc(), ma.getDest(),  "IMM(" + ma.getTrans().getLabel() + ")");
//			}

}

