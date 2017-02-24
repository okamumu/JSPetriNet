package jspetrinet.marking;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jspetrinet.exception.JSPNException;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;

public class CreateMarkingDFS implements CreateMarkingStrategyAnalysis {

//	private final List<Trans> expTransSet;
	
	private final CreateMarking cm;
	private final Net net;

	private int depth;
	private final int maxdepth;

	private final Set<Mark> visited;
	private final LinkedList<Mark> novisitedIMM;
	private final LinkedList<Mark> novisited;
	
	public CreateMarkingDFS(MarkingGraph markGraph, int maxdepth, CreateMarking cm) {
//		this.expTransSet = genTransSet;
		this.net = markGraph.getNet();
		this.maxdepth = maxdepth;
		this.cm = cm;
		visited = new HashSet<Mark>();
		novisited = new LinkedList<Mark>();
		novisitedIMM = new LinkedList<Mark>();
	}
	
	@Override
	public void create(Mark init) throws JSPNException {
		Mark imark = cm.createMark(init, 0);
		cm.getMarkingGraph().setInitialMark(imark);
		novisited.push(imark);
		createMarking();
	}

	private void visitImmMark(List<ImmTrans> enabledIMMList, Mark m) throws JSPNException {
		for (ImmTrans tr : enabledIMMList) {
			Mark dest = cm.doFiring(tr, depth);
			novisitedIMM.push(dest);
			new MarkingArc(m, dest, tr);
		}
		visited.add(m);
	}
	
	private void visitGenMark(Mark m) throws JSPNException {
		for (GenTrans tr : net.getGenTransSet()) {
			switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
			case ENABLE:
				Mark dest = cm.doFiring(tr, depth);
				novisited.push(dest);
				new MarkingArc(m, dest, tr);
				break;
			default:
			}
		}
		for (ExpTrans tr : net.getExpTransSet()) {
			switch (PetriAnalysis.isEnable(net, tr)) {
			case ENABLE:
				Mark dest = cm.doFiring(tr, depth);
				novisited.push(dest);
				new MarkingArc(m, dest, tr);
				break;
			default:
			}
		}
		visited.add(m);
	}

	private void vanishing() throws JSPNException {
		while (!novisitedIMM.isEmpty()) {
			Mark m = novisitedIMM.pop();
			depth = m.getDepth();

			if (visited.contains(m)) {
				continue;
			}
			
			// new visit
			net.setCurrentMark(m);
			GenVec genv = cm.createGenVec();
			m.setGroup(genv);
			
			List<ImmTrans> enabledIMMList = cm.createEnabledIMM();			
			if (enabledIMMList.size() > 0) {
				m.setIMM();
				cm.setGenVecToImm(m);
				if (maxdepth == 0 || depth < maxdepth) {
					visitImmMark(enabledIMMList, m);
				}
			} else {
				m.setGEN();
				cm.setGenVecToGen(m);
				if (maxdepth == 0 || depth < maxdepth) {
					visitGenMark(m);
				}
			}
		}
	}
	
	private void createMarking() throws JSPNException {
		while (!novisited.isEmpty()) {
			Mark m = novisited.pop();
			depth = m.getDepth();

			if (visited.contains(m)) {
				continue;
			}

			// new visit
			net.setCurrentMark(m);
			GenVec genv = cm.createGenVec();
			m.setGroup(genv);

			List<ImmTrans> enabledIMMList = cm.createEnabledIMM();
			if (enabledIMMList.size() > 0) {
				m.setIMM();
				cm.setGenVecToImm(m);
				if (maxdepth == 0 || depth < maxdepth) {
					visitImmMark(enabledIMMList, m);
					vanishing();
				}
			} else {
				m.setGEN();
				cm.setGenVecToGen(m);
				if (maxdepth == 0 || depth < maxdepth) {
					visitGenMark(m);
				}
			}
		}
	}
}
