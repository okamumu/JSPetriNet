package jspetrinet.marking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jspetrinet.JSPetriNet;
import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class CreateMarkingBFSwithPriority implements CreateMarking {

	private final MarkingGraph markGraph;
	private Map<Mark,Mark> arcSet;
	private Map<Mark,Integer> markDepth;
	private int depth;
	private final int maxdepth;

	private List<Trans> sortedImmTrans;
	
	public CreateMarkingBFSwithPriority(MarkingGraph markGraph, int maxdepth) {
		this.markGraph = markGraph;
		this.maxdepth = maxdepth;
	}

	@Override
	public Mark create(Mark init, Net net) throws ASTException {
		this.depth = 0;
		arcSet = new HashMap<Mark,Mark>();
		markDepth = new HashMap<Mark,Integer>();

		sortedImmTrans = new ArrayList<Trans>(net.getImmTransSet());
		sortedImmTrans.sort(new PriorityComparator());

		LinkedList<Mark> novisited = new LinkedList<Mark>();
		arcSet.put(init, init);
		novisited.offer(init);
		markDepth.put(init, depth+1);
		createMarking(novisited, net);
		return init;
	}

	private void createMarking(LinkedList<Mark> novisited, Net net) throws ASTException {
		while (!novisited.isEmpty()) {
			Mark m = novisited.poll();
			if (markDepth.get(m) > this.maxdepth) {
				continue;
			} else {
				depth = markDepth.get(m);
			}

			if (markGraph.containtsMark(m)) {
				continue;
			}
			markGraph.addMark(m);
			net.setCurrentMark(m);

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
	//						if (numberOfFiring.get(dest) > depth+1) {
	//							numberOfFiring.put(dest, depth+1);
	//						}
						} else {
							novisited.offer(dest);
							markDepth.put(dest, depth+1);
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
//						if (numberOfFiring.get(dest) > depth+1) {
//							numberOfFiring.put(dest, depth+1);
//						}
					} else {
						novisited.offer(dest);
						markDepth.put(dest, depth+1);
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
//						if (numberOfFiring.get(dest) > depth+1) {
//							numberOfFiring.put(dest, depth+1);
//						}
					} else {
						novisited.offer(dest);
						markDepth.put(dest, depth+1);
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
