package jspetrinet.analysis;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import jspetrinet.JSPetriNet;
import jspetrinet.graph.Arc;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingArc;
import jspetrinet.petri.Net;

public class MarkClassAnalysis {
	
	private final Net net;
	private final Set<MarkGroup> allMarkGroup;
	private final Set<MarkGroup> dagMarkGroup;
	private final Map<Mark,MarkGroup> markToGroup;

	public MarkClassAnalysis(Net net) {
		this.net = net;
		allMarkGroup = new HashSet<MarkGroup>();
		markToGroup = new HashMap<Mark,MarkGroup>();
		dagMarkGroup = new HashSet<MarkGroup>();
	}
	
	public void scc(Mark start, Set<Mark> allmark) {
		// SCC: Decomposition of Strongly Connected Components by Kosaraju
		// init
		LinkedList<Mark> sorted = new LinkedList<Mark>();
		LinkedList<Mark> novisited = new LinkedList<Mark>();
		Set<Mark> visited = new HashSet<Mark>();
		Set<Mark> reserved = new HashSet<Mark>();
		
		// DFS 1 pass
		novisited.push(start);
		reserved.add(start);
		while (!novisited.isEmpty()) {
			Mark m = novisited.pop();
			if (visited.contains(m)) {
				sorted.push(m);
				continue;
			}
			visited.add(m);

			novisited.push(m);
			for (Arc a : m.getOutArc()) {
				Mark next = (Mark) a.getDest();
				if (!reserved.contains(next)) {
					novisited.push(next);
					reserved.add(next);
				}
			}
		}

		// DFS 2 pass (reverse)
		novisited.clear();
		visited.clear();
		reserved.clear();
		while (!sorted.isEmpty()) {
			Mark m = sorted.pop();
			if (!visited.contains(m)) {
				MarkGroup mg = new MarkGroup(JSPetriNet.markToString(net, m));
				novisited.push(m);
				reserved.add(m);
				while (!novisited.isEmpty()) {
					Mark m2 = novisited.pop();
					visited.add(m2);
					mg.add(m2);
					markToGroup.put(m2, mg);

					for (Arc a : m2.getInArc()) {
						Mark next = (Mark) a.getSrc();
						if (!reserved.contains(next)) {
							novisited.push(next);
							reserved.add(next);
						}
					}
				}
				if (mg.getMarkSet().size() == 1) {
					Mark mm = mg.getMarkSet().iterator().next();
					for (Arc a : mm.getInArc()) {
						Mark mmnext = (Mark) a.getSrc();
						MarkGroup mmmg = markToGroup.get(mmnext);
						if (dagMarkGroup.contains(mmmg)) {
//								for (Mark cm : mmmg.getMarkSet()) {
//									mg.add(cm);
//									this.markToGroup.put(cm, mg);
//								}
							mg.addAll(mmmg.getMarkSet());
							dagMarkGroup.remove(mmmg);
							allMarkGroup.remove(mmmg);
						}
					}
					dagMarkGroup.add(mg);
				}
				allMarkGroup.add(mg);
			}
		}
	}
	
	private void connectGroup(MarkGroup src, MarkGroup dest) {
		for (Mark msrc : src.getMarkSet()) {
			for (Arc a : msrc.getOutArc()) {
				Mark next = (Mark) a.getDest();
				if (dest.getMarkSet().contains(next)) {
					new MarkingArc(src, dest, null);
					return;
				}
			}
		}
	}
	
	public void connectGroup() {
		for (MarkGroup src : allMarkGroup) {
			for (MarkGroup dest : allMarkGroup) {
				if (src != dest) {
					connectGroup(src, dest);
				}
			}
		}
	}
	
	public void dotMarkGroup(PrintWriter bw) {
		bw.println("digraph { layout=dot; overlap=false; splines=true;");
		for (MarkGroup entry : allMarkGroup) {
			if (dagMarkGroup.contains(entry)) {
				bw.println("\"" + entry + "\" [shape = box, label = \""
						+ entry.getLabel() + " (" + entry.getMarkSet().size() + ")\"];");
			} else {
				bw.println("\"" + entry + "\" [label = \""
						+ entry.getLabel() + " (" + entry.getMarkSet().size() + ")\"];");				
			}
			for (Arc a : entry.getOutArc()) {
				bw.println("\"" + a.getSrc() + "\" -> \"" + a.getDest() + "\";");
			}
		}
		bw.println("}");
	}

	//	private Map<MarkGroup,Boolean> isSlow;
//	private Set<Trans> slowTransSet;
//
//	private boolean hasFastTrans(Mark m) {
//		for (Arc a : m.getOutArc()) {
//			MarkingArc ma = (MarkingArc) a;
//			if (!slowTransSet.contains(ma.getTrans())) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public Set<MarkGroup> searchFastStates(Set<Mark> allState) {
//		Set<Mark> visited = new HashSet<Mark>();
//		Set<Mark> determined = new HashSet<Mark>();
//		Set<Mark> remain = new HashSet<Mark>(allState);
//		Set<MarkGroup> mgg = new HashSet<MarkGroup>();
//
//		MarkGroup slowSet = new MarkGroup("");
//		for (Mark m : allState) {
//			if (hasFastTrans(m) == false) {
//				slowSet.add(m);
//				visited.add(m);
//				remain.remove(m);
//			}
//		}
//		isSlow.put(slowSet, true);
//		mgg.add(slowSet);
//
//		while (!remain.isEmpty()) {
//			Mark start = remain.iterator().next();
//			MarkGroup mg = createFastGroup(start, remain, visited, determined, allState, slowTransSet, slowSet);
//			if (mg.size() != 0) {
//				mgg.add(mg);
//			}
//		}
//		return mgg;
//	}
//
//	private MarkGroup createFastGroup(Mark start, Set<Mark> remain,
//			Set<Mark> visited, Set<Mark> determined,
//			Set<Mark> allState, Set<Trans> slowTransSet, MarkGroup slowSet) {
//		
//		MarkGroup mg = new MarkGroup("");
//		LinkedList<Mark> novisited = new LinkedList<Mark>();
//		novisited.push(start);
//		while (!novisited.isEmpty()) {
//			Mark m = novisited.pop();
//			if (visited.contains(m) || !allState.contains(m)) {
//				continue;
//			}
//			visited.add(m);
//			remain.remove(m);
//
//			for (Arc a : m.getOutArc()) {
//				MarkingArc ma = (MarkingArc) a;
//				if (!slowTransSet.contains(ma.getTrans())) {
//					Mark dest = (Mark) ma.getDest();
//					if (!determined.contains(dest)) {
//						if (allState.contains(dest) && !slowSet.contains(dest)) {
//							mg.add(dest);
//							determined.add(dest);
//							novisited.push(dest);
//						} else {
//							determined.add(dest);
////							mg.setTransient(true);
////							mg.getExitMarkSet().add(m);
//						}
//					}
//				}
//			}
//			for (Arc a : m.getInArc()) {
//				MarkingArc ma = (MarkingArc) a;
//				if (!slowTransSet.contains(ma.getTrans())) {
//					Mark src = (Mark) ma.getSrc();
//					if (!determined.contains(src)) {
//						if (allState.contains(src)) {
//							mg.add(src);
//							determined.add(src);
//							novisited.push(src);
//						} else {
//							// TODO
//						}
//					}
//				}
//			}
//		}
//		return mg;
//	}

}
