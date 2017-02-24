package jspetrinet.marking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jspetrinet.JSPetriNet;
import jspetrinet.exception.JSPNException;
import jspetrinet.graph.Arc;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class CreateMarking {
	
	private final Net net;
	private final MarkingGraph markGraph;
	private final Map<Mark,Mark> createdMarks;
	private final Map<GenVec,GenVec> createdGenVec;
	
	public CreateMarking(MarkingGraph markGraph) {
		this.markGraph = markGraph;
		this.net = markGraph.getNet();
		createdMarks = new HashMap<Mark,Mark>();
		createdGenVec = new HashMap<GenVec,GenVec>();
	}
	
	public MarkingGraph getMarkingGraph() {
		return markGraph;
	}
	
	public void setGenVecToImm(Mark m) {
//		GenVec genv = m.getGenVec();
//		if (!markGraph.getImmGroup().containsKey(genv)) {
//			markGraph.getImmGroup().put(genv, new MarkGroup("Imm: " + JSPetriNet.genvecToString(net, genv), genv, true));
//		}
//		markGraph.getImmGroup().get(genv).add(m);					
		markGraph.getImmGroup().add(m);					
	}

	public void setGenVecToGen(Mark m) {
//		GenVec genv = m.getGenVec();
//		if (!markGraph.getGenGroup().containsKey(genv)) {
//			markGraph.getGenGroup().put(genv, new MarkGroup("Gen: " + JSPetriNet.genvecToString(net, genv), genv, false));
//		}
//		markGraph.getGenGroup().get(genv).add(m);
		markGraph.getGenGroup().add(m);
	}

	public Mark createMark(Mark m, int depth) throws JSPNException {
		if (createdMarks.containsKey(m)) {
			m = createdMarks.get(m);
			if (m.getDepth() > depth+1) {
				m.setDepth(depth+1);
			}
		} else {
			createdMarks.put(m, m);
			m.setDepth(depth+1);
		}
		return m;
	}

	public Mark doFiring(Trans tr, int depth) throws JSPNException {
		Mark dest = PetriAnalysis.doFiring(net, tr);
		if (createdMarks.containsKey(dest)) {
			dest = createdMarks.get(dest);
			if (dest.getDepth() > depth+1) {
				dest.setDepth(depth+1);
			}
		} else {
			createdMarks.put(dest, dest);
			dest.setDepth(depth+1);
		}
		return dest;
	}

	public GenVec createGenVec() throws JSPNException {
		GenVec genv = new GenVec(net);
		for (GenTrans tr : net.getGenTransSet()) {
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
//		for (Trans tr : expTransSet) {
//			switch (PetriAnalysis.isEnable(net, tr)) {
//			case ENABLE:
//				genv.set(tr.getIndex(), 1);
//				break;
//			default:
//			}
//		}
		
		if (!createdGenVec.containsKey(genv)) {
			createdGenVec.put(genv, genv);
			return genv;
		} else {
			return createdGenVec.get(genv);
		}
	}
	
	public List<ImmTrans> createEnabledIMM() throws JSPNException {
		List<ImmTrans> enabledIMMList = new ArrayList<ImmTrans>();
		int highestPriority = 0;
		for (ImmTrans tr : net.getImmTransSet()) {
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

	// Group Net
//	public void createMarkGroupGraph() {
//		Map<GenVec,MarkGroup> immGroup = markGraph.getImmGroup();
//		Map<GenVec,MarkGroup> genGroup = markGraph.getGenGroup();
//		Set<GvGvTrans> gvItoI = new HashSet<GvGvTrans>();
//		Set<GvGvTrans> gvGtoI = new HashSet<GvGvTrans>();
//		Set<GvGvTrans> gvItoG = new HashSet<GvGvTrans>();
//		Set<GvGvTrans> gvGtoG = new HashSet<GvGvTrans>();
//		for (MarkGroup srcG : immGroup.values()) {
//			for (Mark src : srcG.getMarkSet()) {
////				System.out.println("srcI " + JSPetriNet.markToString(net, src));
//				for (Arc a : src.getOutArc()) {
//					MarkingArc ma = (MarkingArc) a;
//					Mark dest = (Mark) ma.getDest();
////					System.out.println("destI " + JSPetriNet.markToString(net, dest));
//					if (dest.isIMM()) {
//						GvGvTrans gvtrans = new GvGvTrans(src.getGenVec(), dest.getGenVec(), ma.getTrans());
//						if (!gvItoI.contains(gvtrans)) {
//							new MarkingArc(srcG, immGroup.get(dest.getGenVec()), ma.getTrans());
//							gvItoI.add(gvtrans);						
//						}
//					} else if (!dest.isIMM()) {
//						GvGvTrans gvtrans = new GvGvTrans(src.getGenVec(), dest.getGenVec(), ma.getTrans());
//						if (!gvItoG.contains(gvtrans)) {
//							new MarkingArc(srcG, genGroup.get(dest.getGenVec()), ma.getTrans());
//							gvItoG.add(gvtrans);
//						}
//					}
//				}
//			}
//		}
//		for (MarkGroup srcG : genGroup.values()) {
//			for (Mark src : srcG.getMarkSet()) {
////				System.out.println("srcG " + JSPetriNet.markToString(net, src));
//				for (Arc a : src.getOutArc()) {
//					MarkingArc ma = (MarkingArc) a;
//					Mark dest = (Mark) ma.getDest();
////					System.out.println("destG " + JSPetriNet.markToString(net, dest));
//					if (!dest.isIMM() && !src.getGenVec().equals(dest.getGenVec())) {
//						GvGvTrans gvtrans = new GvGvTrans(src.getGenVec(), dest.getGenVec(), ma.getTrans());
//						if (!gvGtoG.contains(gvtrans)) {
//							new MarkingArc(srcG, genGroup.get(dest.getGenVec()), ma.getTrans());
//							gvGtoG.add(gvtrans);
//						}
//					} else if (dest.isIMM()) {
//						GvGvTrans gvtrans = new GvGvTrans(src.getGenVec(), dest.getGenVec(), ma.getTrans());
//						if (!gvGtoI.contains(gvtrans)) {
//							new MarkingArc(srcG, immGroup.get(dest.getGenVec()), ma.getTrans());
//							gvGtoI.add(gvtrans);
//						}
//					}
//				}
//			}
//		}
//	}
}
