package jspetrinet.marking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jspetrinet.JSPetriNet;
import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

class PriorityComparator implements Comparator<Trans> {
	@Override
	public int compare(Trans o1, Trans o2) {
		int p1 = o1.getPriority();
		int p2 = o2.getPriority();
		
		if (p1 == p2) {
			return 0;
		} else if (p1 < p2) {
			return 1;
		} else {
			return -1;
		}
	}
}

public class CreateMarkingDFSwithPriority implements CreateMarking {
	
	private final MarkingGraph markGraph;
	private Map<Mark,Mark> arcSet;
	
	private List<Trans> sortedImmTrans;
	
	public CreateMarkingDFSwithPriority(MarkingGraph markGraph) {
		this.markGraph = markGraph;
	}
	
	@Override
	public Mark create(Mark init, Net net) throws ASTException {
		arcSet = new HashMap<Mark,Mark>();

		sortedImmTrans = new ArrayList<Trans>(net.getImmTransSet());
		sortedImmTrans.sort(new PriorityComparator());

		LinkedList<Mark> novisited = new LinkedList<Mark>();
		arcSet.put(init, init);
		novisited.push(init);
		createMarking(novisited, net);
		return init;
	}

	private void createMarking(LinkedList<Mark> novisited, Net net) throws ASTException {
		while (!novisited.isEmpty()) {
			Mark m = novisited.pop();
			net.setCurrentMark(m);
			if (markGraph.containtsMark(m)) {
				continue;
			}
			markGraph.addMark(m);

			// make genvec
			GenVec genv = new GenVec(net.getGenTransSet().size());
			for (Trans tr : net.getGenTransSet()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					genv.set(tr.getIndex(), 1);
					break;
				case PREEMPTION:
					genv.set(tr.getIndex(), 2);
					break;
				default:
				}
			}

			boolean hasImmTrans = false;
			int highestPriority = 0;
			for (Trans tr : sortedImmTrans) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					if (highestPriority <= tr.getPriority()) {
						highestPriority = tr.getPriority();
						hasImmTrans = true;
						Mark dest = PetriAnalysis.doFiring(net, tr);
						if (arcSet.containsKey(dest)) {
							dest = arcSet.get(dest);
						} else {
							novisited.push(dest);
							arcSet.put(dest, dest);
						}
						new MarkingArc(m, dest, tr);
					}
					break;
				default:
				}
			}
			if (hasImmTrans == true) {
				if (!markGraph.getImmGroup().containsKey(genv)) {
					markGraph.getImmGroup().put(genv, new MarkGroup("Imm: " + JSPetriNet.genvecToString(net, genv)));
				}
				markGraph.getImmGroup().get(genv).add(m);
//				m.setMarkGroup(markGraph.getImmGroup().get(genv));
				continue;
			} else {
				if (!markGraph.getGenGroup().containsKey(genv)) {
					markGraph.getGenGroup().put(genv, new MarkGroup("Gen: " + JSPetriNet.genvecToString(net, genv)));
				}
				markGraph.getGenGroup().get(genv).add(m);
//				m.setMarkGroup(markGraph.getGenGroup().get(genv));
			}
			
			for (Trans tr : net.getGenTransSet()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						dest = arcSet.get(dest);
					} else {
						novisited.push(dest);
						arcSet.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			
			for (Trans tr : net.getExpTransSet()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						dest = arcSet.get(dest);
					} else {
						novisited.push(dest);
						arcSet.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
		}
	}
}
