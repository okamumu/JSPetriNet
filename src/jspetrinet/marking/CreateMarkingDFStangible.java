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

class ExitMark {
	private Mark emark;
	private int numOfMark;
	
	ExitMark() {
		this.emark = null;
		this.numOfMark = 0;
	}
	
	Mark get() {
		return emark;
	}
	
	boolean canVanishing() {
		if (numOfMark == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	void addMark(Mark emark) {
		switch (this.numOfMark) {
		case 0:
			this.emark = emark;
			this.numOfMark = 1;
			break;
		case 1:
			if (this.emark != emark) {
				this.emark = null;
				this.numOfMark = 2;
			};
			break;
		default:
		}
	}

	void addMark(ExitMark other) {
		switch (this.numOfMark) {
		case 0:
			switch (other.numOfMark) {
			case 1:
				this.emark = other.emark;
				this.numOfMark = 1;
				break;
			case 2:
				this.emark = null;
				this.numOfMark = 2;
				break;
			default:
			}
			break;
		case 1:
			switch (other.numOfMark) {
			case 1:
				if (this.emark != other.emark) {
					this.emark = null;
					this.numOfMark = 2;
				};
				break;
			case 0:
			case 2:
				this.emark = null;
				this.numOfMark = 2;
				break;
			default:
			}
			break;
		default:
		}
	}
}

public class CreateMarkingDFStangible implements CreateMarking {
	
	private final MarkingGraph markGraph;
	private final List<Trans> expTransSet;

	private Map<Mark,Mark> createdMarks;
	private Map<GenVec,GenVec> createdGenVec;

	private Map<Mark,GenVec> immToGenVec;
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
	
	@Override
	public Mark create(Mark init, Net net) throws JSPNException {
		createdMarks = new HashMap<Mark,Mark>();
		createdGenVec = new HashMap<GenVec,GenVec>();
		immToGenVec = new HashMap<Mark,GenVec>();

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

		// post processing 1		
		for (Map.Entry<Mark,GenVec> v : immToGenVec.entrySet()) {
//			if (exitMarkSet.containsKey(v.getKey())) {
				if (!exitMarkSet.get(v.getKey()).canVanishing()) {
					this.setGenVecToImm(net, v.getValue(), v.getKey());
				}
//			} else {
//				System.err.println("Error in vanishing!");
//			}
		}

		for (MarkMarkTrans a : arcListGEN) {
			Mark src = a.getSrc();
			Mark dest = a.getDest();
			Trans tr = a.getTrans();
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
//			if (exitMarkSet.containsKey(src)) {
				if (!exitMarkSet.get(src).canVanishing()) {
					Mark dest = a.getDest();
					Trans tr = a.getTrans();
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
//			} else {
//				System.err.println("Error in vanishing!");
//			}
		}

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
		
		if (!createdGenVec.containsKey(genv)) {
			createdGenVec.put(genv, genv);
			return genv;
		} else {
			return createdGenVec.get(genv);
		}
		
//		return genv;
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

	private void visitImmMark(Net net, List<Mark> nextMarksFromIMM, int size, Mark m) throws JSPNException {
		if (!exitMarkSet.containsKey(m)) {
			exitMarkSet.put(m, new ExitMark());
			novisitedIMM.push(null);
			for (Mark dest : nextMarksFromIMM) {
				novisitedIMM.push(dest);
			}
			markPath.addLast(m);
//			return m;
		} else {
			System.err.println("loop exist!: " + JSPetriNet.markToString(net, m));
			if (!markPath.contains(m)) {
				novisitedIMM.push(null);
				for (Mark dest: nextMarksFromIMM) {
					novisitedIMM.push(dest);
				}
				markPath.addLast(m);
//				return m;
			} else {
//				return markPath.peekLast();
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

//	private void vanishing(Net net) throws ASTException {
//		while (!novisitedIMM.isEmpty()) {
//			Mark m = novisitedIMM.pop();
//
//			if (m == null) {
//				Mark e = markPath.removeLast();
//				visitedIMM.add(e);		
//				visited.add(e);
//				continue;
//			}
//
//			if (visitedIMM.contains(m)) {
//				Mark e = exitMarkSet.get(m).get();
//				for (Mark v : markPath) {
//					exitMarkSet.get(v).setMark(e);
//				}
//				continue;
//			}
//
//			// check tangible GEN (set visited - visitedIMM)
//			if (visited.contains(m)) {
//				for (Mark v : markPath) {
//					exitMarkSet.get(v).setMark(m);
//				}
//				continue;
//			}
//
//			// new visit
//			net.setCurrentMark(m);
//
//			List<Trans> nextMarksFromIMM = createEnabledIMM(net);
//			int size = nextMarksFromIMM.size();
//			if (size > 0) {
//				immToGenVec.put(m, createGenVec(net));
//				visitImmMark(net, nextMarksFromIMM, size, m);
//			} else {
//				for (Mark v : markPath) {
//					exitMarkSet.get(v).setMark(m);
//				}
//				setGenVecToGen(net, createGenVec(net), m);
//				visitGenMark(net, m);
//			}
//		}
//	}
	
	private void vanishing(Net net) throws JSPNException {
		while (!novisitedIMM.isEmpty()) {
			Mark m = novisitedIMM.pop();

			if (m == null) {
				Mark e = markPath.removeLast();
				Mark r = markPath.peekLast();
				if (r != null) {
					exitMarkSet.get(r).addMark(exitMarkSet.get(e));
				}
				visitedIMM.add(e);		
				visited.add(e);
				continue;
			}

			if (visitedIMM.contains(m)) {
				Mark r = markPath.peekLast();
				exitMarkSet.get(r).addMark(exitMarkSet.get(m));
				continue;
			}

			// check tangible GEN (set visited - visitedIMM)
			if (visited.contains(m)) {
				Mark r = markPath.peekLast();
				exitMarkSet.get(r).addMark(m);
				continue;
			}

			// new visit
			net.setCurrentMark(m);

			List<Mark> nextMarksFromIMM = createNextMarksIMM(net, m);
			int size = nextMarksFromIMM.size();
			if (size > 0) {
				immToGenVec.put(m, createGenVec(net));
				visitImmMark(net, nextMarksFromIMM, size, m);
			} else {
				Mark r = markPath.peekLast();
				exitMarkSet.get(r).addMark(m);
				setGenVecToGen(net, createGenVec(net), m);
				visitGenMark(net, m);
			}
		}
	}

	private void createMarking(Net net) throws JSPNException {
		while (!novisitedGEN.isEmpty()) {
			Mark m = novisitedGEN.pop();
			if (visited.contains(m)) {
				continue;
			}

			// new visit
			net.setCurrentMark(m);

			List<Mark> nextMarksFromIMM = createNextMarksIMM(net, m);
			int size = nextMarksFromIMM.size();
			if (size > 0) {
				immToGenVec.put(m, createGenVec(net));
				visitImmMark(net, nextMarksFromIMM, size, m);
				vanishing(net);
			} else {
				setGenVecToGen(net, createGenVec(net), m);
				visitGenMark(net, m);
			}
		}
	}
}
