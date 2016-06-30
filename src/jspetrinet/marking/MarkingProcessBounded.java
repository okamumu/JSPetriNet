package jspetrinet.marking;

import java.util.LinkedList;

import jspetrinet.exception.*;
import jspetrinet.petri.*;

public final class MarkingProcessBounded extends MarkingProcess {

	/**
	 * 
	 */
	private final int firingBound;
	
	public MarkingProcessBounded(int firingBound) {
		super();
		this.firingBound = firingBound;
	}

	@Override
	protected void createMarking(LinkedList<Mark> novisited, Net net) throws ASTException {
		while (!novisited.isEmpty()) {
			Mark m = novisited.poll();
			if (m.getFiring() > this.firingBound) {
				continue;
			}
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
			if (!immGroup.containsKey(genv)) {
				immGroup.put(genv, new MarkGroup());
			}
			if (!genGroup.containsKey(genv)) {
				genGroup.put(genv, new MarkGroup());
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
						novisited.offer(dest);
						arcSet.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			if (hasImmTrans == true) {
				m.setMarkGroup(immGroup.get(genv));
				continue;
			} else {
				m.setMarkGroup(genGroup.get(genv));
			}
			
			for (Trans tr : net.getGenTransSet()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						dest = arcSet.get(dest);
					} else {
						novisited.offer(dest);
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
						novisited.offer(dest);
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
