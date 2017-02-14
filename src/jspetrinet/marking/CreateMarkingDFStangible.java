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

public class CreateMarkingDFStangible implements CreateMarking {
	private final MarkingGraph markGraph;
	private final List<Trans> expTransSet;

	private Map<Mark,Mark> createdMarks;
	private Map<GenVec,GenVec> createdGenVec;

	private Set<Mark> visitedIMM;
	private Set<Mark> visited;

	private Map<Mark,ExitMark> exitMarkSet;
	private LinkedList<MarkMarkTrans> arcListIMM;
	private LinkedList<MarkMarkTrans> arcListGEN;

	private List<Trans> sortedImmTrans;
	
	private LinkedList<Mark> novisitedIMM;
	private LinkedList<Mark> novisitedGEN;
	private LinkedList<Mark> markPath;
	
	public CreateMarkingDFStangible(MarkingGraph markGraph, List<Trans> genTransSet) {
		this.markGraph = markGraph;
		this.expTransSet = genTransSet;
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
		
		if (!createdGenVec.containsKey(genv)) {
			createdGenVec.put(genv, genv);
			return genv;
		} else {
			return createdGenVec.get(genv);
		}
	}
	
	private void setGenVecToImm(Net net, Mark m) {
		GenVec genv = m.getGenVec();
		if (!markGraph.getImmGroup().containsKey(genv)) {
			markGraph.getImmGroup().put(genv, new MarkGroup("Imm: " + JSPetriNet.genvecToString(net, genv)));
		}
		markGraph.addMark(m);
		markGraph.getImmGroup().get(genv).add(m);
	}

	private void setGenVecToGen(Net net, Mark m) {
		GenVec genv = m.getGenVec();
		if (!markGraph.getGenGroup().containsKey(genv)) {
			markGraph.getGenGroup().put(genv, new MarkGroup("Gen: " + JSPetriNet.genvecToString(net, genv)));
		}
		markGraph.addMark(m);
		markGraph.getGenGroup().get(genv).add(m);
	}

	@Override
	public Mark create(Mark init, Net net) throws JSPNException {
		createdMarks = new HashMap<Mark,Mark>();
		createdGenVec = new HashMap<GenVec,GenVec>();

		visitedIMM = new HashSet<Mark>();
		visited = new HashSet<Mark>();

		exitMarkSet = new HashMap<Mark,ExitMark>();
		arcListIMM = new LinkedList<MarkMarkTrans>();
		arcListGEN = new LinkedList<MarkMarkTrans>();

		sortedImmTrans = new ArrayList<Trans>(net.getImmTransSet());
		sortedImmTrans.sort(new PriorityComparator());
		
		novisitedGEN = new LinkedList<Mark>();
		novisitedIMM = new LinkedList<Mark>();
		markPath = new LinkedList<Mark>();
		
		createdMarks.put(init, init);
		novisitedGEN.push(init);
		createMarking(net);
		postProcessing(net);
		return init;
	}
	
	private void createMarking(Net net) throws JSPNException {
		while (!novisitedGEN.isEmpty()) {
			Mark m = novisitedGEN.pop();
			if (visited.contains(m)) {
				continue;
			}

			// new visit
			net.setCurrentMark(m);
			m.setGroup(createGenVec(net));

			List<Mark> nextMarksFromIMM = createNextMarksIMM(net, m);
			int size = nextMarksFromIMM.size();
			if (size > 0) {
				m.setIMM();
				visitImmMark(net, nextMarksFromIMM, size, m);
				vanishing(net);
			} else {
				m.setGEN();
				visitGenMark(net, m);
			}
		}
	}

	private List<Mark> createNextMarksIMM(Net net, Mark m) throws JSPNException {
		List<Mark> nextMarksFromIMM = new ArrayList<Mark>();
		int highestPriority = 0;
		for (Trans t : sortedImmTrans) {
			ImmTrans tr = (ImmTrans) t;
			if (highestPriority > tr.getPriority()) {
				break;
			}
			switch (PetriAnalysis.isEnable(net, tr)) {
			case ENABLE:
				highestPriority = tr.getPriority();
				Mark dest = PetriAnalysis.doFiring(net, tr);
				if (createdMarks.containsKey(dest)) {
					dest = createdMarks.get(dest);
				} else {
					createdMarks.put(dest, dest);
				}
				nextMarksFromIMM.add(dest);
				arcListIMM.add(new MarkMarkTrans(m, dest, tr));
				break;
			default:
			}
		}
		return nextMarksFromIMM;
	}
	
	private void visitImmMark(Net net, List<Mark> nextMarksFromIMM, int size, Mark m) throws JSPNException {
		if (!exitMarkSet.containsKey(m)) {
			exitMarkSet.put(m, new ExitMark());
			novisitedIMM.push(null);
			for (Mark dest : nextMarksFromIMM) {
				novisitedIMM.push(dest);
			}
			markPath.addLast(m);
		} else {
			System.err.println("loop exist!: " + JSPetriNet.markToString(net, m));
			if (!markPath.contains(m)) {
				novisitedIMM.push(null);
				for (Mark dest: nextMarksFromIMM) {
					novisitedIMM.push(dest);
				}
				markPath.addLast(m);
			}
		}
	}
	
	private void vanishing(Net net) throws JSPNException {
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
			m.setGroup(createGenVec(net));

			List<Mark> nextMarksFromIMM = createNextMarksIMM(net, m);
			int size = nextMarksFromIMM.size();
			if (size > 0) {
				m.setIMM();
				visitImmMark(net, nextMarksFromIMM, size, m);
			} else {
				m.setGEN();
				Mark r = markPath.peekLast();
				exitMarkSet.get(r).addMark(r, m);
				visitGenMark(net, m);
			}
		}
	}

	private void visitGenMark(Net net, Mark m) throws JSPNException {
		for (Trans tr : net.getGenTransSet()) {
			switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
			case ENABLE:
				Mark dest = PetriAnalysis.doFiring(net, tr);
				if (createdMarks.containsKey(dest)) {
					dest = createdMarks.get(dest);
				} else {
					novisitedGEN.push(dest);
					createdMarks.put(dest, dest);
				}
				arcListGEN.add(new MarkMarkTrans(m, dest, tr));
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
					novisitedGEN.push(dest);
					createdMarks.put(dest, dest);
				}
				arcListGEN.add(new MarkMarkTrans(m, dest, tr));
				break;
			default:
			}
		}
		visited.add(m);
	}
	
	/*
	 * Post processing
	 */
	private void postProcessing(Net net) {
		// post processing 1		
		for (MarkMarkTrans a : arcListGEN) {
			Mark src = a.getSrc();
			Mark dest = a.getDest();
			Trans tr = a.getTrans();
			setGenVecToGen(net, src);
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
				setGenVecToImm(net, src);
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
