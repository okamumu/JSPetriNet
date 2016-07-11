package jspetrinet.analysis;

import java.util.*;

import jspetrinet.JSPetriNet;
import jspetrinet.graph.Arc;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.*;

public final class MarkingAnalysis {

	private MarkingGraph mp;
	private Net net;
	
	private Map<GenVec,MarkGroup> immGroup;
	MarkGroup expGroup;
	private Map<GenVec,MarkGroup> genGroup;
	
	private final Map<MarkGroup, Map<MarkGroup,Set<Mark>>> enterSet;
	private final Map<MarkGroup, Map<MarkGroup,Set<Mark>>> exitSet;
	
	public MarkingAnalysis(MarkingGraph mp) {
		this.mp = mp;
		this.net = mp.getNet();
		immGroup = mp.getImmGroup();
		genGroup = mp.getGenGroup();
		expGroup = mp.getExpGroup();
		
		enterSet = new HashMap<MarkGroup, Map<MarkGroup,Set<Mark>>>();
		exitSet = new HashMap<MarkGroup, Map<MarkGroup,Set<Mark>>>();
		this.createEnterExit();
		this.setEnterExit();
	}

	private void createEnterExit() {
		for (MarkGroup mg : immGroup.values()) {
			enterSet.put(mg, new HashMap<MarkGroup,Set<Mark>>());
			exitSet.put(mg, new HashMap<MarkGroup,Set<Mark>>());
		}
		for (MarkGroup mg : genGroup.values()) {
			enterSet.put(mg, new HashMap<MarkGroup,Set<Mark>>());
			exitSet.put(mg, new HashMap<MarkGroup,Set<Mark>>());
		}

		for (MarkGroup mg : immGroup.values()) {
			for (Arc a : mg.getOutArc()) {
				MarkGroup dest = (MarkGroup) a.getDest();
				this.enterSet.get(dest).put(mg, new HashSet<Mark>());
				this.exitSet.get(mg).put(dest, new HashSet<Mark>());
			}
		}
		for (MarkGroup mg : genGroup.values()) {
			for (Arc a : mg.getOutArc()) {
				MarkGroup dest = (MarkGroup) a.getDest();
				this.enterSet.get(dest).put(mg, new HashSet<Mark>());
				this.exitSet.get(mg).put(dest, new HashSet<Mark>());
			}
		}
	}
	
	private void setEnterExit() {
		for (MarkGroup mg : immGroup.values()) {
			for (Mark m : mg.getMarkSet()) {
				for (Arc a: m.getOutArc()) {
					Mark next = (Mark) a.getDest();
					MarkGroup nextMg = next.getMarkGroup();
					if (mg != nextMg) {
						this.exitSet.get(mg).get(nextMg).add(next);
						this.enterSet.get(nextMg).get(mg).add(m);
					}
				}
				
			}
		}
		for (MarkGroup mg : genGroup.values()) {
			for (Mark m : mg.getMarkSet()) {
				for (Arc a: m.getOutArc()) {
					Mark next = (Mark) a.getDest();
					MarkGroup nextMg = next.getMarkGroup();
					if (mg != nextMg) {
						this.exitSet.get(mg).get(nextMg).add(next);
						this.enterSet.get(nextMg).get(mg).add(m);
					}
				}
				
			}
		}
	}
	
	public void doVanishing(MarkGroup mg) {
		HashMap<Mark,Map<Mark,Object>> cash = new HashMap<Mark,Map<Mark,Object>>();
		LinkedList<Mark> tovisit = new LinkedList<Mark>();
		for (Map.Entry<MarkGroup,Set<Mark>> entry : this.enterSet.get(mg).entrySet()) {
			for (Mark m : entry.getValue()) {
				tovisit.push(m);
				while (!tovisit.isEmpty()) {
					Mark c = tovisit.pop();
					for (Arc a : c.getOutArc()) {
						MarkingArc ma = (MarkingArc) a;
						Mark next = (Mark) a.getDest();
						if (cash.containsKey(next)) {
							Map<Mark,Object> r = cash.get(next);
							for (Map.Entry<Mark, Object> entry2 : r.entrySet()) {
								
							}
						}
					}
				}
			}
		}
		
	}

	public static Set<MarkGroup> searchFastStates(Set<Mark> allState, Set<Trans> slowTransSet) {
		Set<Mark> visited = new HashSet<Mark>();
		Set<Mark> determined = new HashSet<Mark>();
		Set<Mark> remain = new HashSet<Mark>(allState);
		Set<MarkGroup> mgg = new HashSet<MarkGroup>();		

		MarkGroup slowSet = new MarkGroup("");
		for (Mark m : allState) {
			if (hasFastTrans(m, slowTransSet) == false) {
				slowSet.add(m);
				visited.add(m);
				remain.remove(m);
			}
		}		
//		slowSet.setSlow(true);
		mgg.add(slowSet);

		while (!remain.isEmpty()) {
			Mark start = remain.iterator().next();
			MarkGroup mg = createFastGroup(start, remain, visited, determined, allState, slowTransSet, slowSet);
			if (mg.size() != 0) {
				mgg.add(mg);
			}
		}
		return mgg;
	}

	private static boolean hasFastTrans(Mark m, Set<Trans> slowTransSet) {
		for (Arc a : m.getOutArc()) {
			MarkingArc ma = (MarkingArc) a;
			if (!slowTransSet.contains(ma.getTrans())) {
				return true;
			}
		}
		return false;
	}

	private static MarkGroup createFastGroup(Mark start, Set<Mark> remain,
			Set<Mark> visited, Set<Mark> determined,
			Set<Mark> allState, Set<Trans> slowTransSet, MarkGroup slowSet) {
		
		MarkGroup mg = new MarkGroup("");
		Stack<Mark> novisited = new Stack<Mark>();
		novisited.push(start);
		while (!novisited.empty()) {
			Mark m = novisited.pop();
			if (visited.contains(m) || !allState.contains(m)) {
				continue;
			}
			visited.add(m);
			remain.remove(m);

			for (Arc a : m.getOutArc()) {
				MarkingArc ma = (MarkingArc) a;
				if (!slowTransSet.contains(ma.getTrans())) {
					Mark dest = (Mark) ma.getDest();
					if (!determined.contains(dest)) {
						if (allState.contains(dest) && !slowSet.contains(dest)) {
							mg.add(dest);
							determined.add(dest);
							novisited.push(dest);
						} else {
							determined.add(dest);
//							mg.setTransient(true);
//							mg.getExitMarkSet().add(m);
						}
					}
				}
			}
			for (Arc a : m.getInArc()) {
				MarkingArc ma = (MarkingArc) a;
				if (!slowTransSet.contains(ma.getTrans())) {
					Mark src = (Mark) ma.getSrc();
					if (!determined.contains(src)) {
						if (allState.contains(src)) {
							mg.add(src);
							determined.add(src);
							novisited.push(src);
						} else {
							// TODO
						}
					}
				}
			}
		}
		return mg;
	}

}
