package jspetrinet.marking;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import jspetrinet.exception.*;
import jspetrinet.petri.*;

public class MarkingProcess {

	protected Net net;

	protected final Map<Mark,Mark> markSet;
	protected final Map<Mark,Mark> arcSet;

	protected int numOfGenTrans;
	protected final Map<GenVec,MarkGroup> genGroup;
	protected final Map<GenVec,MarkGroup> immGroup;
	
	public MarkingProcess() {
		markSet = new HashMap<Mark,Mark>();
		genGroup = new HashMap<GenVec,MarkGroup>();
		immGroup = new HashMap<GenVec,MarkGroup>();
		arcSet = new HashMap<Mark,Mark>();
		numOfGenTrans = 0;
	}
	
	public final int size() {
		return markSet.size();
	}
	
	public final int immSize() {
		int total = 0;
		for (MarkGroup mg: immGroup.values()) {
			total += mg.size();
		}
		return total;
	}
	
	public final Net getNet() {
		return net;
	}

	public final Map<GenVec,MarkGroup> getImmGroup() {
		return immGroup;
	}

	public final Map<GenVec,MarkGroup> getGenGroup() {
		return genGroup;
	}

	public final MarkGroup getExpGroup() {
		return genGroup.get(new GenVec(numOfGenTrans));
	}

	public Mark create(Mark init, Net net) throws ASTException {
		this.net = net;
		markSet.clear();
		arcSet.clear();
		arcSet.put(init, init);

		numOfGenTrans = net.getNumOfGenTrans();
		GenVec genv = new GenVec(numOfGenTrans);
		immGroup.clear();
		immGroup.put(genv, new MarkGroup()); // EXP IMM group
		genGroup.clear();
		genGroup.put(genv, new MarkGroup()); // EXP GEN group

		LinkedList<Mark> novisited = new LinkedList<Mark>();
		novisited.push(init);
		createMarking(novisited, net);
		return init;
	}
	
	protected void createMarking(LinkedList<Mark> noVisited, Net net) throws ASTException {
		while (!noVisited.isEmpty()) {
			Mark m = noVisited.pop();
			net.setCurrentMark(m);
			if (markSet.containsKey(m)) {
				continue;
			}
			markSet.put(m, m);

			// make genvec
			GenVec genv = new GenVec(numOfGenTrans);
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
						noVisited.push(dest);
						arcSet.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			if (hasImmTrans == true) {
				if (!immGroup.containsKey(genv)) {
					immGroup.put(genv, new MarkGroup());
				}
				m.setMarkGroup(immGroup.get(genv));
				continue;
			} else {
				if (!genGroup.containsKey(genv)) {
					genGroup.put(genv, new MarkGroup());
				}
				m.setMarkGroup(genGroup.get(genv));
			}
			
			for (Trans tr : net.getGenTransSet()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						dest = arcSet.get(dest);
					} else {
						noVisited.push(dest);
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
						noVisited.push(dest);
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
