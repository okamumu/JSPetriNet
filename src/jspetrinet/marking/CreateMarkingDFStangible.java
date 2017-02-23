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
import jspetrinet.petri.Trans;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.ImmTrans;

public class CreateMarkingDFStangible implements CreateMarkingStrategyAnalysis {
//	private final MarkingGraph markGraph;
//	private final List<Trans> expTransSet;

	private final Net net;
	private final CreateMarking cm;

	private int depth;

	private final Set<Mark> visited;
	private final LinkedList<Mark> novisited;

	private final Set<Mark> visitedIMM;
	private final LinkedList<Mark> novisitedIMM;

	private final LinkedList<Mark> markPath;

	private final Map<Mark,ExitMark> exitMarkSet;
	private final List<MarkMarkTrans> arcListIMM;
	private final List<MarkMarkTrans> arcListGEN;
	
	public CreateMarkingDFStangible(MarkingGraph markGraph, CreateMarking cm) {
		this.net = markGraph.getNet();
		this.cm = cm;
//		this.markGraph = markGraph;
//		this.expTransSet = genTransSet;
		visitedIMM = new HashSet<Mark>();
		visited = new HashSet<Mark>();

		exitMarkSet = new HashMap<Mark,ExitMark>();
		arcListIMM = new ArrayList<MarkMarkTrans>();
		arcListGEN = new ArrayList<MarkMarkTrans>();

		novisited = new LinkedList<Mark>();
		novisitedIMM = new LinkedList<Mark>();
		markPath = new LinkedList<Mark>();
		
	}
	
//	private GenVec createGenVec(Net net) throws JSPNException {
//		GenVec genv = new GenVec(net);
//		for (GenTrans tr : net.getGenTransSet()) {
//			switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
//			case ENABLE:
//				genv.set(tr.getIndex(), 1);
//				break;
//			case PREEMPTION:
//				genv.set(tr.getIndex(), 2);
//				break;
//			default:
//			}
//		}
////		for (Trans tr : expTransSet) {
////			switch (PetriAnalysis.isEnable(net, tr)) {
////			case ENABLE:
////				genv.set(tr.getIndex(), 1);
////				break;
////			default:
////			}
////		}
//		
//		if (!createdGenVec.containsKey(genv)) {
//			createdGenVec.put(genv, genv);
//			return genv;
//		} else {
//			return createdGenVec.get(genv);
//		}
//	}
	
//	private void setGenVecToImm(Net net, Mark m) {
//		GenVec genv = m.getGenVec();
//		if (!markGraph.getImmGroup().containsKey(genv)) {
//			markGraph.getImmGroup().put(genv, new MarkGroup("Imm: " + JSPetriNet.genvecToString(net, genv), genv, true));
//		}
////		markGraph.addMark(m);
//		markGraph.getImmGroup().get(genv).add(m);
//	}
//
//	private void setGenVecToGen(Net net, Mark m) {
//		GenVec genv = m.getGenVec();
//		if (!markGraph.getGenGroup().containsKey(genv)) {
//			markGraph.getGenGroup().put(genv, new MarkGroup("Gen: " + JSPetriNet.genvecToString(net, genv), genv, false));
//		}
////		markGraph.addMark(m);
//		markGraph.getGenGroup().get(genv).add(m);
//	}

	@Override
	public void create(Mark init) throws JSPNException {
		Mark imark = cm.createMark(init, 0);
		cm.getMarkingGraph().setInitialMark(imark);
		novisited.push(imark);
		createMarking();
		postProcessing();
		cm.createMarkGroupGraph();
	}
	
	private void visitImmMark(List<ImmTrans> enabledIMMList, Mark m) throws JSPNException {
		if (!exitMarkSet.containsKey(m)) {
			exitMarkSet.put(m, new ExitMark());
			novisitedIMM.push(null);
			for (ImmTrans tr : enabledIMMList) {
				Mark dest = cm.doFiring(tr, depth);
				novisitedIMM.push(dest);
				arcListIMM.add(new MarkMarkTrans(m, dest, tr));
			}
			markPath.addLast(m);
		} else {
			System.err.println("loop exist!: " + JSPetriNet.markToString(net, m));
			if (!markPath.contains(m)) {
				novisitedIMM.push(null);
				for (ImmTrans tr : enabledIMMList) {
					Mark dest = cm.doFiring(tr, depth);
					novisitedIMM.push(dest);
					arcListIMM.add(new MarkMarkTrans(m, dest, tr));
				}
				markPath.addLast(m);
			}
		}
	}
	
//	private List<Mark> createNextMarksIMM(Net net, Mark m) throws JSPNException {
//		List<Mark> nextMarksFromIMM = new ArrayList<Mark>();
//		int highestPriority = 0;
//		for (ImmTrans tr : net.getImmTransSet()) {
//			if (highestPriority > tr.getPriority()) {
//				break;
//			}
//			switch (PetriAnalysis.isEnable(net, tr)) {
//			case ENABLE:
//				highestPriority = tr.getPriority();
//				Mark dest = PetriAnalysis.doFiring(net, tr);
//				if (createdMarks.containsKey(dest)) {
//					dest = createdMarks.get(dest);
//				} else {
//					createdMarks.put(dest, dest);
//				}
//				nextMarksFromIMM.add(dest);
//				arcListIMM.add(new MarkMarkTrans(m, dest, tr));
//				break;
//			default:
//			}
//		}
//		return nextMarksFromIMM;
//	}
	
//	private void visitImmMark(Net net, List<Mark> nextMarksFromIMM, int size, Mark m) throws JSPNException {
//		if (!exitMarkSet.containsKey(m)) {
//			exitMarkSet.put(m, new ExitMark());
//			novisitedIMM.push(null);
//			for (Mark dest : nextMarksFromIMM) {
//				novisitedIMM.push(dest);
//			}
//			markPath.addLast(m);
//		} else {
//			System.err.println("loop exist!: " + JSPetriNet.markToString(net, m));
//			if (!markPath.contains(m)) {
//				novisitedIMM.push(null);
//				for (Mark dest: nextMarksFromIMM) {
//					novisitedIMM.push(dest);
//				}
//				markPath.addLast(m);
//			}
//		}
//	}
	
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
				visitImmMark(enabledIMMList, m);
				vanishing();
			} else {
				m.setGEN();
				visitGenMark(m);
			}

//			List<Mark> nextMarksFromIMM = this.createEnabledIMM();
//			createNextMarksIMM(net, m);
//			int size = nextMarksFromIMM.size();
//			if (size > 0) {
//				m.setIMM();
//				visitImmMark(net, nextMarksFromIMM, size, m);
//				vanishing(net);
//			} else {
//				m.setGEN();
//				visitGenMark(net, m);
//			}
		}
	}

	private void vanishing() throws JSPNException {
		while (!novisitedIMM.isEmpty()) {
			Mark m = novisitedIMM.pop();

			if (m == null) {
				Mark e = markPath.removeLast();
				Mark r = markPath.peekLast();
				if (r != null) {
					exitMarkSet.get(r).addMark(r, exitMarkSet.get(e));
				}
				visitedIMM.add(e);		
				visited.add(e);
				continue;
			}

			depth = m.getDepth();

			if (visitedIMM.contains(m)) {
				Mark r = markPath.peekLast();
				exitMarkSet.get(r).addMark(r, exitMarkSet.get(m));
				continue;
			}

			// check tangible GEN (set visited - visitedIMM)
			if (visited.contains(m)) {
				Mark r = markPath.peekLast();
				exitMarkSet.get(r).addMark(r, m);
				continue;
			}

			// new visit
			net.setCurrentMark(m);
			GenVec genv = cm.createGenVec();
			m.setGroup(genv);

			List<ImmTrans> enabledIMMList = cm.createEnabledIMM();			
			if (enabledIMMList.size() > 0) {
				m.setIMM();
				visitImmMark(enabledIMMList, m);
			} else {
				m.setGEN();
				Mark r = markPath.peekLast();
				exitMarkSet.get(r).addMark(r, m);
				visitGenMark(m);
			}
//			List<Mark> nextMarksFromIMM = createNextMarksIMM(net, m);
//			int size = nextMarksFromIMM.size();
//			if (size > 0) {
//				m.setIMM();
//				visitImmMark(net, nextMarksFromIMM, size, m);
//			} else {
//				m.setGEN();
//				Mark r = markPath.peekLast();
//				exitMarkSet.get(r).addMark(r, m);
//				visitGenMark(net, m);
//			}
		}
	}

	private void visitGenMark(Mark m) throws JSPNException {
		for (GenTrans tr : net.getGenTransSet()) {
			switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
			case ENABLE:
				Mark dest = cm.doFiring(tr, depth);
				novisited.push(dest); // if dest is newly created?
				arcListGEN.add(new MarkMarkTrans(m, dest, tr));
				break;
			default:
			}
		}
		for (ExpTrans tr : net.getExpTransSet()) {
			switch (PetriAnalysis.isEnable(net, tr)) {
			case ENABLE:
				Mark dest = cm.doFiring(tr, depth);
				novisited.push(dest); // if dest is newly created?
				arcListGEN.add(new MarkMarkTrans(m, dest, tr));
				break;
			default:
			}
		}
		visited.add(m);
	}

//	private void visitGenMark(Mark m) throws JSPNException {
//		for (GenTrans tr : net.getGenTransSet()) {
//			switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
//			case ENABLE:
//				Mark dest = PetriAnalysis.doFiring(net, tr);
//				if (createdMarks.containsKey(dest)) {
//					dest = createdMarks.get(dest);
//				} else {
//					novisitedGEN.push(dest);
//					createdMarks.put(dest, dest);
//				}
//				arcListGEN.add(new MarkMarkTrans(m, dest, tr));
//				break;
//			default:
//			}
//		}
//		for (ExpTrans tr : net.getExpTransSet()) {
//			switch (PetriAnalysis.isEnable(net, tr)) {
//			case ENABLE:
//				Mark dest = PetriAnalysis.doFiring(net, tr);
//				if (createdMarks.containsKey(dest)) {
//					dest = createdMarks.get(dest);
//				} else {
//					novisitedGEN.push(dest);
//					createdMarks.put(dest, dest);
//				}
//				arcListGEN.add(new MarkMarkTrans(m, dest, tr));
//				break;
//			default:
//			}
//		}
//		visited.add(m);
//	}
	
	/*
	 * Post processing
	 */
	private void postProcessing() {
		// post processing 1
		for (Mark m : visited) {
			if (m.isIMM()) {
				if (!exitMarkSet.get(m).canVanishing()) {
					cm.setGenVecToImm(m);
				}
			} else {
				cm.setGenVecToGen(m);
			}
		}
		for (MarkMarkTrans a : arcListGEN) {
			Mark src = a.getSrc();
			Mark dest = a.getDest();
			Trans tr = a.getTrans();
//			setGenVecToGen(net, src);
			if (exitMarkSet.containsKey(dest)) {
				if (!exitMarkSet.get(dest).canVanishing()) {
					new MarkingArc(src, dest, tr);
				} else {
					Mark sdest = exitMarkSet.get(dest).get();
					new MarkingArc(src, sdest, tr);
				}
			} else {
				new MarkingArc(src, dest, tr);
			}
		}

		for (MarkMarkTrans a : arcListIMM) {
			Mark src = a.getSrc();
			if (!exitMarkSet.get(src).canVanishing()) {
				Mark dest = a.getDest();
				Trans tr = a.getTrans();
//				setGenVecToImm(net, src);
				if (exitMarkSet.containsKey(dest)) {
					if (!exitMarkSet.get(dest).canVanishing()) {
						new MarkingArc(src, dest, tr);
					} else {
						Mark sdest = exitMarkSet.get(dest).get();
						new MarkingArc(src, sdest, tr);
					}
				} else {
					new MarkingArc(src, dest, tr);
				}
			}
		}
	}
}
