package jspetrinet.marking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jspetrinet.JSPetriNet;
import jspetrinet.exception.JSPNException;
import jspetrinet.petri.Net;
import jspetrinet.petri.PriorityComparator;
import jspetrinet.petri.Trans;
import jspetrinet.petri.ImmTrans;

public class CreateMarkingDFS2 implements CreateMarking {
	
	private final MarkingGraph markGraph;
	private final List<Trans> expTransSet;

	private Map<Mark,Mark> createdMarks;
	private Set<Mark> visitedGEN;

	private List<Trans> sortedImmTrans;
	
	private LinkedList<Mark> novisitedIMM;
	private LinkedList<Mark> novisitedGEN;
	
	public CreateMarkingDFS2(MarkingGraph markGraph, List<Trans> genTransSet) {
		this.markGraph = markGraph;
		this.expTransSet = genTransSet;
	}
	
	@Override
	public Mark create(Mark init, Net net) throws JSPNException {
		createdMarks = new HashMap<Mark,Mark>();

		visitedGEN = new HashSet<Mark>();

		sortedImmTrans = new ArrayList<Trans>(net.getImmTransSet());
		sortedImmTrans.sort(new PriorityComparator());
		
		novisitedGEN = new LinkedList<Mark>();
		novisitedIMM = new LinkedList<Mark>();

		createdMarks.put(init, init);
		novisitedGEN.push(init);
		createMarking(net);

		return init;
	}
	
	private GenVec createGenVec(Net net) throws JSPNException {
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
		return genv;
	}
	
	private List<Trans> createEnabledIMM(Net net) throws JSPNException {
		List<Trans> enabledIMMList = new ArrayList<Trans>();
		int highestPriority = 0;
		for (Trans t : sortedImmTrans) {
			ImmTrans tr = (ImmTrans) t;
			if (highestPriority > tr.getPriority()) {
				break;
			}
			switch (PetriAnalysis.isEnable(net, tr)) {
			case ENABLE:
				highestPriority = tr.getPriority();
				enabledIMMList.add(tr);
				break;
			default:
			}
		}
		return enabledIMMList;
	}
	
	private void setGenVecToImm(Net net, GenVec genv, Mark m) {
		if (!markGraph.getImmGroup().containsKey(genv)) {
			markGraph.getImmGroup().put(genv, new MarkGroup("Imm: " + JSPetriNet.genvecToString(net, genv)));
		}
		markGraph.addMark(m);
		markGraph.getImmGroup().get(genv).add(m);					
	}

	private void setGenVecToGen(Net net, GenVec genv, Mark m) {
		if (!markGraph.getGenGroup().containsKey(genv)) {
			markGraph.getGenGroup().put(genv, new MarkGroup("Gen: " + JSPetriNet.genvecToString(net, genv)));
		}
		markGraph.addMark(m);
		markGraph.getGenGroup().get(genv).add(m);
	}

	private void visitImmMark(Net net, List<Trans> enabledIMMList, Mark m) throws JSPNException {
		net.assertNet();
		for (Trans tr : enabledIMMList) {
			Mark dest = PetriAnalysis.doFiring(net, tr);
			if (createdMarks.containsKey(dest)) {
				dest = createdMarks.get(dest);
			} else {
				createdMarks.put(dest, dest);
			}
			novisitedIMM.push(dest);
			new MarkingArc(m, dest, tr);
		}
		visitedGEN.add(m);
	}
	
	private void visitGenMark(Net net, Mark m) throws JSPNException {
		net.assertNet();
		for (Trans tr : net.getGenTransSet()) {
			switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
			case ENABLE:
				Mark dest = PetriAnalysis.doFiring(net, tr);
				if (createdMarks.containsKey(dest)) {
					dest = createdMarks.get(dest);
				} else {
					createdMarks.put(dest, dest);
				}
				novisitedGEN.push(dest);
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
					createdMarks.put(dest, dest);
				}
				novisitedGEN.push(dest);
				new MarkingArc(m, dest, tr);
				break;
			default:
			}
		}
		visitedGEN.add(m);
	}

	private void vanishing(Net net) throws JSPNException {
		while (!novisitedIMM.isEmpty()) {
			Mark m = novisitedIMM.pop();
			if (visitedGEN.contains(m)) {
				continue;
			}
			
			// new visit
			net.setCurrentMark(m);
			GenVec genv = createGenVec(net);

			List<Trans> enabledIMMList = createEnabledIMM(net);			
			if (enabledIMMList.size() > 0) {
				setGenVecToImm(net, genv, m);
				visitImmMark(net, enabledIMMList, m);
			} else {
				setGenVecToGen(net, genv, m);
				visitGenMark(net, m);
			}
		}
	}
	
	private void createMarking(Net net) throws JSPNException {
		while (!novisitedGEN.isEmpty()) {
			Mark m = novisitedGEN.pop();
			if (visitedGEN.contains(m)) {
				continue;
			}

			// new visit
			net.setCurrentMark(m);
			GenVec genv = createGenVec(net);

			List<Trans> enabledIMMList = createEnabledIMM(net);
			if (enabledIMMList.size() > 0) {
				setGenVecToImm(net, genv, m);
				visitImmMark(net, enabledIMMList, m);
				vanishing(net);
			} else {
				setGenVecToGen(net, genv, m);
				visitGenMark(net, m);
			}
		}
	}
}
