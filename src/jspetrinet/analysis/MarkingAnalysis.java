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
//		this.setEnterExit();
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
	
//	private void setEnterExit() {
//		for (MarkGroup mg : immGroup.values()) {
//			for (Mark m : mg.getMarkSet()) {
//				for (Arc a: m.getOutArc()) {
//					Mark next = (Mark) a.getDest();
//					MarkGroup nextMg = next.getMarkGroup();
//					if (mg != nextMg) {
//						this.exitSet.get(mg).get(nextMg).add(next);
//						this.enterSet.get(nextMg).get(mg).add(m);
//					}
//				}
//				
//			}
//		}
//		for (MarkGroup mg : genGroup.values()) {
//			for (Mark m : mg.getMarkSet()) {
//				for (Arc a: m.getOutArc()) {
//					Mark next = (Mark) a.getDest();
//					MarkGroup nextMg = next.getMarkGroup();
//					if (mg != nextMg) {
//						this.exitSet.get(mg).get(nextMg).add(next);
//						this.enterSet.get(nextMg).get(mg).add(m);
//					}
//				}
//				
//			}
//		}
//	}
//	
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
}
