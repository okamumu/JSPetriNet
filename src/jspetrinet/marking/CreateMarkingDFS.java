package jspetrinet.marking;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import jspetrinet.JSPetriNet;
import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class CreateMarkingDFS implements CreateMarking {
	
	private final MarkingGraph markGraph;
	private Map<Mark,Mark> arcSet;
	
	public CreateMarkingDFS(MarkingGraph markGraph) {
		this.markGraph = markGraph;
	}
	
	@Override
	public Mark create(Mark init, Net net) throws ASTException {
		arcSet = new HashMap<Mark,Mark>();
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
			for (Trans tr : net.getImmTransSet()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					hasImmTrans = true;
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
