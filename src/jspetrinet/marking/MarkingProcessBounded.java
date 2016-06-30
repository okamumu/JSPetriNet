package jspetrinet.marking;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import jspetrinet.exception.*;
import jspetrinet.petri.*;

public final class MarkingProcessBounded extends MarkingProcess {

	private int depth;
	private int maxdepth;
	private final Map<Mark,Integer> numberOfFiring;
	
	public MarkingProcessBounded(int maxdepth) {
		super();
		this.maxdepth = maxdepth;
		numberOfFiring = new HashMap<Mark,Integer>();
	}

	@Override
	public Mark create(Mark init, Net net) throws ASTException {
		this.net = net;
		markSet.clear();
		arcSet.clear();
		arcSet.put(init, init);

		numOfGenTrans = net.getNumOfGenTrans();
		immGroup.clear();
		genGroup.clear();

		this.depth = 0;
		LinkedList<Mark> novisited = new LinkedList<Mark>();
		novisited.offer(init);
		numberOfFiring.put(init, depth+1);
		createMarking(novisited, net);
		return init;
	}

	@Override
	protected void createMarking(LinkedList<Mark> novisited, Net net) throws ASTException {
		while (!novisited.isEmpty()) {
			Mark m = novisited.poll();
			if (numberOfFiring.get(m) > this.maxdepth) {
				continue;
			} else {
				depth = numberOfFiring.get(m);
			}

			if (markSet.containsKey(m)) {
				continue;
			}
			markSet.put(m, m);
			net.setCurrentMark(m);

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
//						if (numberOfFiring.get(dest) > depth+1) {
//							numberOfFiring.put(dest, depth+1);
//						}
					} else {
						novisited.offer(dest);
						numberOfFiring.put(dest, depth+1);
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
//						if (numberOfFiring.get(dest) > depth+1) {
//							numberOfFiring.put(dest, depth+1);
//						}
					} else {
						novisited.offer(dest);
						numberOfFiring.put(dest, depth+1);
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
//						if (numberOfFiring.get(dest) > depth+1) {
//							numberOfFiring.put(dest, depth+1);
//						}
					} else {
						novisited.offer(dest);
						numberOfFiring.put(dest, depth+1);
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
