package jspetrinet.marking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jspetrinet.JSPetriNet;
import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.PriorityComparator;
import jspetrinet.petri.Trans;

public class CreateMarkingDFS implements CreateMarking {
	
	private final MarkingGraph markGraph;
	private Map<Mark,Mark> createdMarks;
	
	private List<Trans> sortedImmTrans;
	
	private List<Trans> expTransSet;
	
	public CreateMarkingDFS(MarkingGraph markGraph) {
		this.markGraph = markGraph;
	}
	
	public void setGenTransSet(List<Trans> genTransSet) {
		this.expTransSet = genTransSet;
	}
	
	@Override
	public Mark create(Mark init, Net net) throws ASTException {
		createdMarks = new HashMap<Mark,Mark>();

		sortedImmTrans = new ArrayList<Trans>(net.getImmTransSet());
		sortedImmTrans.sort(new PriorityComparator());

		LinkedList<Mark> novisited = new LinkedList<Mark>();
		createdMarks.put(init, init);
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
			GenVec genv = new GenVec(net);
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
			for (Trans tr : expTransSet) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					genv.set(tr.getIndex(), 1);
					break;
				default:
				}
			}

			boolean hasImmTrans = false;
			int highestPriority = 0;
			for (Trans tr : sortedImmTrans) {
				if (highestPriority > tr.getPriority()) {
					break;
				}
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					highestPriority = tr.getPriority();
					hasImmTrans = true;
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (createdMarks.containsKey(dest)) {
						dest = createdMarks.get(dest);
					} else {
						novisited.push(dest);
						createdMarks.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			if (hasImmTrans == true) {
				if (!markGraph.getImmGroup().containsKey(genv)) {
					markGraph.getImmGroup().put(genv, new MarkGroup("Imm: " + JSPetriNet.genvecToString(net, genv)));
				}
				markGraph.getImmGroup().get(genv).add(m);
				continue;
			} else {
				if (!markGraph.getGenGroup().containsKey(genv)) {
					markGraph.getGenGroup().put(genv, new MarkGroup("Gen: " + JSPetriNet.genvecToString(net, genv)));
				}
				markGraph.getGenGroup().get(genv).add(m);
			}
			
			for (Trans tr : net.getGenTransSet()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (createdMarks.containsKey(dest)) {
						dest = createdMarks.get(dest);
					} else {
						novisited.push(dest);
						createdMarks.put(dest, dest);
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
					if (createdMarks.containsKey(dest)) {
						dest = createdMarks.get(dest);
					} else {
						novisited.push(dest);
						createdMarks.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
		}
	}
}
