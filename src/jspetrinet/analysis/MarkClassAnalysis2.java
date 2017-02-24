package jspetrinet.analysis;

import java.io.PrintWriter;
import java.util.Collection;
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
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.Net;

public class MarkClassAnalysis2 {
	
	private final Net net;
	private final Set<MarkGroup> allMarkGroup;
	private final Set<MarkGroup> dagMarkGroup;
	private final Map<Mark,MarkGroup> markToGroup;
	
	public MarkClassAnalysis2(Net net, MarkingGraph mp, Collection<Mark> allmark) {
		this.net = net;
		allMarkGroup = new HashSet<MarkGroup>();
		markToGroup = new HashMap<Mark,MarkGroup>();
		dagMarkGroup = new HashSet<MarkGroup>();
//		dagtran = new HashSet<Mark>();
		scc(allmark);
		connectGroup2(allmark);
	}

	private void scc(Collection<Mark> allmark) {
		// SCC: Decomposition of Strongly Connected Components by Kosaraju
		// init
		LinkedList<Mark> sorted = new LinkedList<Mark>();
		LinkedList<Mark> comp = new LinkedList<Mark>();
		LinkedList<Mark> novisited = new LinkedList<Mark>();
		Set<Mark> visited = new HashSet<Mark>();
		
		// DFS 1 pass
		for (Mark s : allmark) {
			if (visited.contains(s)) {
				continue;
			}
			novisited.push(s);
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
					if (allmark.contains(next)) {
						novisited.push(next);
					}
				}
			}
		}

		// DFS 2 pass (reverse)
		novisited.clear();
		visited.clear();
		for (Mark s : sorted) {
			if (visited.contains(s)) {
				continue;
			}
			MarkGroup mg = new MarkGroup("", JSPetriNet.markToString(net, s), s.isIMM());
			novisited.push(s);
			while (!novisited.isEmpty()) {
				Mark m = novisited.pop();
				if (visited.contains(m)) {
					continue;
				}
				visited.add(m);
				mg.add(m);
				markToGroup.put(m, mg);

				for (Arc a : m.getInArc()) {
					Mark next = (Mark) a.getSrc();
					if (allmark.contains(next)) {
						novisited.push(next);
					}
				}
			}
			if (mg.getMarkSet().size() != 1) {
				allMarkGroup.add(mg);
			} else {
//				dagtran.add(mg.getMarkSet().iterator().next());
				allMarkGroup.add(mg);
				dagMarkGroup.add(mg);
			}
		}
	}

//	private void scc2(Collection<Mark> allmark) {
//		// SCC: Decomposition of Strongly Connected Components by Kosaraju
//		// init
//		LinkedList<Mark> sorted = new LinkedList<Mark>();
//		LinkedList<Mark> comp = new LinkedList<Mark>();
//		LinkedList<Mark> novisited = new LinkedList<Mark>();
//		Set<Mark> visited = new HashSet<Mark>();
//		
//		// DFS 1 pass
//		for (Mark s : allmark) {
//			if (visited.contains(s)) {
//				continue;
//			}
//			novisited.push(s);
//			while (!novisited.isEmpty()) {
//				Mark m = novisited.pop();
//				if (m == null) {
//					sorted.push(comp.pop());
//					continue;
//				}
//				if (!allmark.contains(m)) {
//					sorted.push(m);
//					continue;
//				}
//				if (visited.contains(m)) {
//					continue;
//				}
//				visited.add(m);
//
//				novisited.push(null);
//				comp.push(m);
//				for (Arc a : m.getOutArc()) {
//					Mark next = (Mark) a.getDest();
//					novisited.push(next);
//				}
//			}
//		}
//
//		// DFS 2 pass (reverse)
//		novisited.clear();
//		visited.clear();
//		for (Mark s : sorted) {
//			if (visited.contains(s)) {
//				continue;
//			}
//			MarkGroup mg = new MarkGroup(JSPetriNet.markToString(net, s));
//			novisited.push(s);
//			while (!novisited.isEmpty()) {
//				Mark m = novisited.pop();
//				if (visited.contains(m)) {
//					continue;
//				}
//				visited.add(m);
//				mg.add(m);
//				markToGroup.put(m, mg);
//
//				for (Arc a : m.getInArc()) {
//					Mark next = (Mark) a.getSrc();
//					if (allmark.contains(next)) {
//						novisited.push(next);
//					}
//				}
//			}
//			if (mg.getMarkSet().size() != 1) {
//				allMarkGroup.add(mg);
//				System.out.println("SCC " + mg.getLabel());
//				for (Mark x : mg.getMarkSet()) {
//					System.out.println(" " + JSPetriNet.markToString(net, x));
//				}
//			} else {
////				dagtran.add(mg.getMarkSet().iterator().next());
//				allMarkGroup.add(mg);
//				dagMarkGroup.add(mg);
//			}
//		}
//	}

	private void connectGroup2(Collection<Mark> allmark) {
		Set<Mark> visited = new HashSet<Mark>();
		LinkedList<Mark> novisited = new LinkedList<Mark>();
		Set<GroupPair> connected = new HashSet<GroupPair>();
		for (MarkGroup mg : allMarkGroup) {
			for (Mark s : mg.getMarkSet()) {
				if (visited.contains(s)) {
					continue;
				}
				novisited.push(s);
				while (!novisited.isEmpty()) {
					Mark m = novisited.pop();
					if (visited.contains(m)) {
						continue;
					}
					visited.add(m);
					
					MarkGroup src = markToGroup.get(m);
					for (Arc a : m.getOutArc()) {
						Mark next = (Mark) a.getDest();
						if (!allmark.contains(next)) {
							continue;
						}
						MarkGroup dest = markToGroup.get(next);
						if (src != dest) {
							GroupPair gp = new GroupPair(src, dest);
							if (!connected.contains(gp)) {
								new MarkingArc(src, dest, null);
								connected.add(gp);
							}
						}
						novisited.push(next);
					}
				}
			}
		}
	}

//	private void connectGroup3(Collection<Mark> allmark) {
//		Set<Mark> visited = new HashSet<Mark>();
//		LinkedList<Mark> novisited = new LinkedList<Mark>();
//		Set<GroupPair> connected = new HashSet<GroupPair>();
//		for (MarkGroup mg : allMarkGroup) {
//			for (Mark s : mg.getMarkSet()) {
//				if (visited.contains(s)) {
//					continue;
//				}
//				novisited.push(s);
//				while (!novisited.isEmpty()) {
//					Mark m = novisited.pop();
//					if (visited.contains(m)) {
//						continue;
//					}
//					if (!allmark.contains(m)) {
//						continue;
//					}
//					visited.add(m);
//					
//					MarkGroup src = markToGroup.get(m);
//					for (Arc a : m.getOutArc()) {
//						Mark next = (Mark) a.getDest();
//						MarkGroup dest = markToGroup.get(next);
//						if (dest == null) {
//							
//						}
//						if (src != dest) {
//							GroupPair gp = new GroupPair(src, dest);
//							if (!connected.contains(gp)) {
//								new MarkingArc(src, dest, null);
//								connected.add(gp);
//							}
//						}
//						novisited.push(next);
//					}
//				}
//			}
//		}
//	}

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
