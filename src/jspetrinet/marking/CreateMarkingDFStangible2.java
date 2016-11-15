package jspetrinet.marking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jspetrinet.JSPetriNet;
import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.PriorityComparator;
import jspetrinet.petri.Trans;
import jspetrinet.petri.ImmTrans;

public class CreateMarkingDFStangible2 implements CreateMarking {
	
	private final MarkingGraph markGraph;
	private final List<Trans> expTransSet;

	private Map<Mark,Mark> createdMarks;
	private Map<GenVec,GenVec> createdGenVec;
	private Map<Mark,GenVec> immToGenVec;
	private Set<Mark> tangibleMarks;
	private Set<Mark> visitedIMM;
	private Set<Mark> visitedGEN;

	private Map<Mark,Set<Mark>> exitMarkSet;
	private LinkedList<MarkMarkTrans> arcListIMM;
	private LinkedList<MarkMarkTrans> arcListGEN;

	private List<Trans> sortedImmTrans;
	
	private LinkedList<Mark> novisitedIMM;
	private LinkedList<Mark> novisitedGEN;
	private LinkedList<Mark> markPath;
	
	public CreateMarkingDFStangible2(MarkingGraph markGraph, List<Trans> genTransSet) {
		this.markGraph = markGraph;
		this.expTransSet = genTransSet;
	}
	
	@Override
	public Mark create(Mark init, Net net) throws ASTException {
		createdMarks = new HashMap<Mark,Mark>();
		createdGenVec = new HashMap<GenVec,GenVec>();
		immToGenVec = new HashMap<Mark,GenVec>();

		tangibleMarks = new HashSet<Mark>();
		visitedIMM = new HashSet<Mark>();
		visitedGEN = new HashSet<Mark>();

		exitMarkSet = new HashMap<Mark,Set<Mark>>();
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
			if (exitMarkSet.containsKey(v.getKey())) {
				if (exitMarkSet.get(v.getKey()).size() != 1) {
					if (!markGraph.getImmGroup().containsKey(v.getValue())) {
						markGraph.getImmGroup().put(v.getValue(), new MarkGroup("Imm: " + JSPetriNet.genvecToString(net, v.getValue())));
					}
					markGraph.addMark(v.getKey());
					markGraph.getImmGroup().get(v.getValue()).add(v.getKey());					
				}
			} else {
				System.err.println("Error in vanishing!");
			}
		}

		for (MarkMarkTrans a : arcListGEN) {
			Mark src = a.getSrc();
			Mark dest = a.getDest();
			Trans tr = a.getTrans();
			if (exitMarkSet.containsKey(dest)) {
				if (exitMarkSet.get(dest).size() != 1) {
					new MarkingArc(src, dest, tr);
				} else {
					Mark sdest = exitMarkSet.get(dest).iterator().next();
					new MarkingArc(src, sdest, tr);
				}
			} else {
				new MarkingArc(src, dest, tr);
			}
		}

		for (MarkMarkTrans a : arcListIMM) {
			Mark src = a.getSrc();
			if (exitMarkSet.containsKey(src)) {
				if (exitMarkSet.get(src).size() != 1) {
					Mark dest = a.getDest();
					Trans tr = a.getTrans();
					if (exitMarkSet.containsKey(dest)) {
						if (exitMarkSet.get(dest).size() != 1) {
							new MarkingArc(src, dest, tr);
						} else {
							Mark sdest = exitMarkSet.get(dest).iterator().next();
							new MarkingArc(src, sdest, tr);
						}
					} else {
						new MarkingArc(src, dest, tr);
					}
				}
			} else {
				System.err.println("Error in vanishing!");
			}
		}

		return init;
	}
	
	private GenVec createGenVec(Net net) throws ASTException {
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
	
	private List<Trans> createEnabledIMM(Net net) throws ASTException {
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
	
	private void visitImmMark(Net net, List<Trans> enabledIMMList, Mark m) throws ASTException {
		if (!exitMarkSet.containsKey(m)) {
			exitMarkSet.put(m, new HashSet<Mark>());
		}
		novisitedIMM.push(null);
		for (Trans tr : enabledIMMList) {
			Mark dest = PetriAnalysis.doFiring(net, tr);
			if (createdMarks.containsKey(dest)) {
				dest = createdMarks.get(dest);
			} else {
				createdMarks.put(dest, dest);
			}
			novisitedIMM.push(dest);
			arcListIMM.add(new MarkMarkTrans(m, dest, tr));
		}
		markPath.addLast(m);
	}
	
	private void setGenVecToGen(Net net, GenVec genv, Mark m) {
		if (!markGraph.getGenGroup().containsKey(genv)) {
			markGraph.getGenGroup().put(genv, new MarkGroup("Gen: " + JSPetriNet.genvecToString(net, genv)));
		}
		markGraph.addMark(m);
		markGraph.getGenGroup().get(genv).add(m);
	}

	private void visitGenMark(Net net, Mark m) throws ASTException {
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
		visitedGEN.add(m);
		tangibleMarks.add(m);
	}

	private void vanishing(Net net) throws ASTException {
		while (!novisitedIMM.isEmpty()) {
			Mark m = novisitedIMM.pop();

			if (m == null) {
				visitedIMM.add(markPath.removeLast());				
				continue;
			}

			if (visitedIMM.contains(m)) {
				for (Mark v : markPath) {
					exitMarkSet.get(v).addAll(exitMarkSet.get(m));
				}
				continue;
			}

			if (tangibleMarks.contains(m)) {
				for (Mark v : markPath) {
					exitMarkSet.get(v).add(m);
				}
				continue;
			}

			// new visit
			net.setCurrentMark(m);
			GenVec genv = createGenVec(net);

			List<Trans> enabledIMMList = createEnabledIMM(net);			
			if (enabledIMMList.size() == 0) {
				tangibleMarks.add(m);
				for (Mark v : markPath) {
					exitMarkSet.get(v).add(m);
				}
				setGenVecToGen(net, genv, m);
				visitGenMark(net, m);
			} else {
				immToGenVec.put(m, genv);
				visitImmMark(net, enabledIMMList, m);
			}
		}
	}
	
	private void createMarking(Net net) throws ASTException {
		while (!novisitedGEN.isEmpty()) {
			Mark m = novisitedGEN.pop();
			if (visitedGEN.contains(m) || visitedIMM.contains(m)) {
				continue;
			}

			// new visit
			net.setCurrentMark(m);
			GenVec genv = createGenVec(net);

			List<Trans> enabledIMMList = createEnabledIMM(net);
			if (enabledIMMList.size() > 0) {
				immToGenVec.put(m, genv);
				visitImmMark(net, enabledIMMList, m);
				vanishing(net);
			} else {
				setGenVecToGen(net, genv, m);
				visitGenMark(net, m);
			}
		}
	}
}
