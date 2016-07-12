package jspetrinet.analysis;

import java.io.PrintWriter;
import java.util.Collections;
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
	private final Set<Mark> dagtran;

	public MarkClassAnalysis(Net net) {
		this.net = net;
		allMarkGroup = new HashSet<MarkGroup>();
		markToGroup = new HashMap<Mark,MarkGroup>();
		dagMarkGroup = new HashSet<MarkGroup>();
		dagtran = new HashSet<Mark>();
	}
	
	public void scc(Mark start, Set<Mark> allmark) {
		// SCC: Decomposition of Strongly Connected Components by Kosaraju
		// init
		LinkedList<Mark> sorted = new LinkedList<Mark>();
		LinkedList<Mark> comp = new LinkedList<Mark>();
		LinkedList<Mark> novisited = new LinkedList<Mark>();
		Set<Mark> visited = new HashSet<Mark>();
		
		// DFS 1 pass
		novisited.push(start);
		while (!novisited.isEmpty()) {
			Mark m = novisited.pop();
			if (m == null) {
				sorted.push(comp.pop());
				continue;
			}
			if (visited.contains(m)) {
				continue;
			}
			visited.add(m);

			novisited.push(null);
			comp.push(m);
			for (Arc a : m.getOutArc()) {
				Mark next = (Mark) a.getDest();
				novisited.push(next);
			}
		}

		// DFS 2 pass (reverse)
		novisited.clear();
		visited.clear();
		for (Mark m : sorted) {
			if (!visited.contains(m)) {
				MarkGroup mg = new MarkGroup(JSPetriNet.markToString(net, m));
				novisited.push(m);
				while (!novisited.isEmpty()) {
					Mark m2 = novisited.pop();
					if (visited.contains(m2)) {
						continue;
					}
					visited.add(m2);
					mg.add(m2);
					markToGroup.put(m2, mg);

					for (Arc a : m2.getInArc()) {
						Mark next = (Mark) a.getSrc();
						novisited.push(next);
					}
				}
				if (mg.getMarkSet().size() != 1) {
					allMarkGroup.add(mg);
				} else {
					dagtran.add(mg.getMarkSet().iterator().next());
					allMarkGroup.add(mg);
					dagMarkGroup.add(mg);
				}
			}
		}

//		// DFS 3 pass
//		novisited.clear();
//		visited.clear();
//		Collections.reverse(sorted);
//		for (Mark m : sorted) {
//			if (!visited.contains(m) && dagtran.contains(m)) {
//				MarkGroup mg = new MarkGroup(JSPetriNet.markToString(net, m));
//				novisited.push(m);
//				while (!novisited.isEmpty()) {
//					Mark m2 = novisited.pop();
//					if (visited.contains(m2) || !dagtran.contains(m2)) {
//						continue;
//					}
//					visited.add(m2);
//					mg.add(m2);
//					markToGroup.put(m2, mg);
//
//					for (Arc a : m2.getOutArc()) {
//						Mark next = (Mark) a.getDest();
//	//					if (!reserved.contains(next)) {
//							novisited.push(next);
////							reserved.add(next);
////						}
//					}
//				}
//				allMarkGroup.add(mg);
//				dagMarkGroup.add(mg);
//			}
//		}
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
}
