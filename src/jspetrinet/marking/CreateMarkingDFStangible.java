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

final class MarkMarkTrans {
	private final Mark src;
	private final Mark dest;
	private final Trans tr;
	
	public MarkMarkTrans(Mark src, Mark dest, Trans tr) {
		this.src = src;
		this.dest = dest;
		this.tr = tr;
	}
	
	public final Mark getSrc() {
		return src;
	}

	public final Mark getDest() {
		return dest;
	}

	public final Trans getTrans() {
		return tr;
	}
}

public class CreateMarkingDFStangible implements CreateMarking {
	
	private final MarkingGraph markGraph;
	private Map<Mark,Mark> createdMarks;
	
	private Set<Mark> vanishingMarks;
	private Set<Mark> tangibleMarks;
	
	private List<Trans> sortedImmTrans;
	
	private Map<Mark,Mark> exitSet;
	private LinkedList<Mark> vanishedIMMList;
	
	private LinkedList<MarkMarkTrans> arcList;

	public CreateMarkingDFStangible(MarkingGraph markGraph) {
		this.markGraph = markGraph;
	}
	
	@Override
	public Mark create(Mark init, Net net) throws ASTException {
		createdMarks = new HashMap<Mark,Mark>();

		vanishingMarks = new HashSet<Mark>();
		tangibleMarks = new HashSet<Mark>();

		exitSet = new HashMap<Mark,Mark>();
		vanishedIMMList = new LinkedList<Mark>();
		
		arcList = new LinkedList<MarkMarkTrans>();

		sortedImmTrans = new ArrayList<Trans>(net.getImmTransSet());
		sortedImmTrans.sort(new PriorityComparator());

		LinkedList<Mark> novisited = new LinkedList<Mark>();
		createdMarks.put(init, init);
		novisited.push(init);
		createMarking(novisited, net);

		for (MarkMarkTrans a : arcList) {
			Mark src = a.getSrc();
			Mark dest = a.getDest();
			Trans tr = a.getTrans();
			if (!exitSet.containsKey(dest)) {
				new MarkingArc(src, dest, tr);
			} else {
				new MarkingArc(src, exitSet.get(dest), tr);
			}
		}

		return init;
	}

	private void createMarking(LinkedList<Mark> novisited, Net net) throws ASTException {
		while (!novisited.isEmpty()) {
			Mark m = novisited.pop();
			net.setCurrentMark(m);

//			System.out.print("visit " + JSPetriNet.markToString(net, m));

			if (markGraph.containtsMark(m)) {
				if (tangibleMarks.contains(m)) {
					while (!vanishedIMMList.isEmpty()) {
						Mark v = vanishedIMMList.pop();
						exitSet.put(v, m);
					}
				} else if (exitSet.containsKey(m)) {
					while (!vanishedIMMList.isEmpty()) {
						Mark v = vanishedIMMList.pop();
						exitSet.put(v, exitSet.get(m));
					}
				}
				continue;
			}

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

			// checkEnabled IMM
			List<Trans> enabledIMMList = new ArrayList<Trans>();
			int highestPriority = 0;
			for (Trans tr : sortedImmTrans) {
				if (highestPriority > tr.getPriority()) {
					break;
				}
				if (PetriAnalysis.isEnable(net, tr) == TransStatus.ENABLE) {
					highestPriority = tr.getPriority();
					enabledIMMList.add(tr);
				}
			}
			
			// vanishing
			if (enabledIMMList.size() != 1) {
				tangibleMarks.add(m);
//				System.out.print(" enabled IMM: ");
//				for (Trans tr : enabledIMMList) {
//					System.out.print(tr.getLabel() + " ");
//				}
//				System.out.println("");
			} else {
//				System.out.print(" enabled IMM: ");
//				for (Trans tr : enabledIMMList) {
//					System.out.print(tr.getLabel() + " ");
//				}
//				System.out.println(" vanishing");				
			}

			if (enabledIMMList.size() == 1) {
				for (Trans tr : enabledIMMList) {
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (createdMarks.containsKey(dest)) {
						dest = createdMarks.get(dest);
						novisited.push(dest);
					} else {
						novisited.push(dest);
						createdMarks.put(dest, dest);
					}
				}
			} else {
				for (Trans tr : enabledIMMList) {
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (createdMarks.containsKey(dest)) {
						dest = createdMarks.get(dest);
					} else {
						novisited.push(dest);
						createdMarks.put(dest, dest);
					}
					arcList.add(new MarkMarkTrans(m, dest, tr));
				}
			}
			
			if (enabledIMMList.size() >= 1) {
				if (!markGraph.getImmGroup().containsKey(genv)) {
					markGraph.getImmGroup().put(genv, new MarkGroup("Imm: " + JSPetriNet.genvecToString(net, genv)));
				}
				if (enabledIMMList.size() >= 2) {
					markGraph.addMark(m);
					markGraph.getImmGroup().get(genv).add(m);
				}
			} else {
				if (!markGraph.getGenGroup().containsKey(genv)) {
					markGraph.getGenGroup().put(genv, new MarkGroup("Gen: " + JSPetriNet.genvecToString(net, genv)));
				}
				markGraph.addMark(m);
				markGraph.getGenGroup().get(genv).add(m);
			
				for (Trans tr : net.getGenTransSet()) {
					switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
					case ENABLE:
						Mark dest = PetriAnalysis.doFiring(net, tr);
						if (createdMarks.containsKey(dest)) {
							dest = createdMarks.get(dest);
						} else {
							novisited.push(dest);
							createdMarks.put(dest, dest);
						}
						arcList.add(new MarkMarkTrans(m, dest, tr));
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
							novisited.push(dest);
							createdMarks.put(dest, dest);
						}
						arcList.add(new MarkMarkTrans(m, dest, tr));
						break;
					default:
					}
				}
			}

			if (enabledIMMList.size() == 1) {
				vanishedIMMList.push(m);
			} else {
				while (!vanishedIMMList.isEmpty()) {
					Mark v = vanishedIMMList.pop();
					exitSet.put(v, m);
//					System.out.println("  set exit " + JSPetriNet.markToString(net,v) + " " + JSPetriNet.markToString(net, m));
				}
			}
		}
	}
}
