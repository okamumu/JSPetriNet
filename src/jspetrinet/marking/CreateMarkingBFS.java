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

public class CreateMarkingBFS implements CreateMarking {

	private final MarkingGraph markGraph;
	private Map<Mark,Mark> createdMarks;
	private Map<Mark,Integer> markDepth;
	private int depth;
	private final int maxdepth;

	private List<Trans> sortedImmTrans;
	
	public CreateMarkingBFS(MarkingGraph markGraph, int maxdepth) {
		this.markGraph = markGraph;
		this.maxdepth = maxdepth;
	}

	@Override
	public Mark create(Mark init, Net net) throws ASTException {
		this.depth = 0;
		createdMarks = new HashMap<Mark,Mark>();
		markDepth = new HashMap<Mark,Integer>();

		sortedImmTrans = new ArrayList<Trans>(net.getImmTransSet());
		sortedImmTrans.sort(new PriorityComparator());

		LinkedList<Mark> novisited = new LinkedList<Mark>();
		createdMarks.put(init, init);
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
						novisited.offer(dest);
						markDepth.put(dest, depth+1);
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
						novisited.offer(dest);
						markDepth.put(dest, depth+1);
						createdMarks.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			
			System.out.println("-------");
			for (Trans tr : net.getExpTransSet()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					System.out.println(tr.getLabel());
					System.out.println("dest " + JSPetriNet.markToString(net, dest));
					if (createdMarks.containsKey(dest)) {
						dest = createdMarks.get(dest);
					} else {
						novisited.offer(dest);
						markDepth.put(dest, depth+1);
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
