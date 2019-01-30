package jspetrinet.analysis;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import jspetrinet.graph.Arc;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingArc;
import jspetrinet.petri.Trans;

public class BTAnalysis {
	
	private Map<MarkGroup,Boolean> isSlow;
	private Set<Trans> slowTransSet;

	private boolean hasFastTrans(Mark m) {
		for (Arc a : m.getOutArc()) {
			MarkingArc ma = (MarkingArc) a;
			if (!slowTransSet.contains(ma.getTrans())) {
				return true;
			}
		}
		return false;
	}

	public Set<MarkGroup> searchFastStates(Set<Mark> allState) {
		Set<Mark> visited = new HashSet<Mark>();
		Set<Mark> determined = new HashSet<Mark>();
		Set<Mark> remain = new HashSet<Mark>(allState);
		Set<MarkGroup> mgg = new HashSet<MarkGroup>();

		MarkGroup slowSet = new MarkGroup("", null, false);
		for (Mark m : allState) {
			if (hasFastTrans(m) == false) {
				slowSet.add(m);
				visited.add(m);
				remain.remove(m);
			}
		}
		isSlow.put(slowSet, true);
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

	private MarkGroup createFastGroup(Mark start, Set<Mark> remain,
			Set<Mark> visited, Set<Mark> determined,
			Set<Mark> allState, Set<Trans> slowTransSet, MarkGroup slowSet) {
		
		MarkGroup mg = new MarkGroup("", null, false);
		LinkedList<Mark> novisited = new LinkedList<Mark>();
		novisited.push(start);
		while (!novisited.isEmpty()) {
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
//						if (allState.contains(dest) && !slowSet.contains(dest)) {
//							mg.add(dest);
//							determined.add(dest);
//							novisited.push(dest);
//						} else {
//							determined.add(dest);
////							mg.setTransient(true);
////							mg.getExitMarkSet().add(m);
//						}
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
