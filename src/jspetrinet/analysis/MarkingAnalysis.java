package jspetrinet.analysis;

import java.util.*;

import jspetrinet.graph.Arc;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingArc;
import jspetrinet.petri.*;

public final class MarkingAnalysis {

	public static void setEnterMark(Stack<Mark> tovisite, Set<Mark> visited,
			Set<BTMarkGroup> genGroup, Set<BTMarkGroup> immGroup) {
		while (!tovisite.empty()) {
			Mark m = tovisite.pop();
			if (visited.contains(m)) {
				continue;
			}
			visited.add(m);

			for (Arc a: m.getOutArc()) {
				Mark next = (Mark) a.getDest();
				if (genGroup.contains(m.getMarkGroup())
						&& immGroup.contains(next.getMarkGroup())) {
//					next.getMarkGroup().getEnterMarkSet().add(next);
				}
				tovisite.push(next);
			}
		}
	}

	public static Set<MarkGroup> searchFastStates(Set<Mark> allState, Set<Trans> slowTransSet) {
		Set<Mark> visited = new HashSet<Mark>();
		Set<Mark> determined = new HashSet<Mark>();
		Set<Mark> remain = new HashSet<Mark>(allState);
		Set<MarkGroup> mgg = new HashSet<MarkGroup>();		

		MarkGroup slowSet = new MarkGroup();
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
		
		MarkGroup mg = new MarkGroup();
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
