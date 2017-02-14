package jspetrinet.marking;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jspetrinet.JSPetriNet;
import jspetrinet.graph.Arc;
import jspetrinet.petri.Net;

public class CreateGroupMarkingGraph {
	
	public static final void createMarkGroupGraph(Net net, Map<GenVec,MarkGroup> immGroup, Map<GenVec,MarkGroup> genGroup) {
		Set<GvGvTrans> gvItoI = new HashSet<GvGvTrans>();
		Set<GvGvTrans> gvGtoI = new HashSet<GvGvTrans>();
		Set<GvGvTrans> gvItoG = new HashSet<GvGvTrans>();
		Set<GvGvTrans> gvGtoG = new HashSet<GvGvTrans>();
		for (MarkGroup srcG : immGroup.values()) {
			for (Mark src : srcG.getMarkSet()) {
				for (Arc a : src.getOutArc()) {
					MarkingArc ma = (MarkingArc) a;
					Mark dest = (Mark) ma.getDest();
					if (dest.isIMM()) {
						GvGvTrans gvtrans = new GvGvTrans(src.getGenVec(), dest.getGenVec(), ma.getTrans());
						if (!gvItoI.contains(gvtrans)) {
							new MarkingArc(srcG, immGroup.get(dest.getGenVec()), ma.getTrans());
							gvItoI.add(gvtrans);						
						}
					} else if (!dest.isIMM()) {
						GvGvTrans gvtrans = new GvGvTrans(src.getGenVec(), dest.getGenVec(), ma.getTrans());
						if (!gvItoG.contains(gvtrans)) {
							new MarkingArc(srcG, genGroup.get(dest.getGenVec()), ma.getTrans());
							gvItoG.add(gvtrans);
						}
					}
				}
			}
		}
		for (MarkGroup srcG : genGroup.values()) {
			for (Mark src : srcG.getMarkSet()) {
				for (Arc a : src.getOutArc()) {
					MarkingArc ma = (MarkingArc) a;
					Mark dest = (Mark) ma.getDest();
					if (!dest.isIMM() && !src.getGenVec().equals(dest.getGenVec())) {
						GvGvTrans gvtrans = new GvGvTrans(src.getGenVec(), dest.getGenVec(), ma.getTrans());
						if (!gvGtoG.contains(gvtrans)) {
							new MarkingArc(srcG, genGroup.get(dest.getGenVec()), ma.getTrans());
							gvGtoG.add(gvtrans);
						}
					} else if (dest.isIMM()) {
						GvGvTrans gvtrans = new GvGvTrans(src.getGenVec(), dest.getGenVec(), ma.getTrans());
						if (!gvGtoI.contains(gvtrans)) {
							new MarkingArc(srcG, immGroup.get(dest.getGenVec()), ma.getTrans());
							gvGtoI.add(gvtrans);
						}
					}
				}
			}
		}
	}
}
