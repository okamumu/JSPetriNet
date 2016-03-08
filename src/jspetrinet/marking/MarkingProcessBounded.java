package jspetrinet.marking;

import java.util.HashMap;
import java.util.Map;

import jspetrinet.exception.*;
import jspetrinet.petri.*;

public final class MarkingProcessBounded extends MarkingProcess {

	private final int firingBound;
	
	public MarkingProcessBounded(int firingBound) {
		super();
		this.firingBound = firingBound;
	}

	@Override
	protected void create(Stack<Mark> novisited, Net net) throws ASTException {
		while (!novisited.empty()) {
			Mark m = novisited.pop();
			System.out.println(m.getFiring());
			if (this.firingBound != 0 && m.getFiring() > this.firingBound) {
				continue;
			}
			net.setCurrentMark(m);
			if (markSet.containsKey(m)) {
				if (this.firingBound != 0 && markSet.get(m).getFiring() > m.getFiring()) {
					System.out.println("!!");
					markSet.get(m).setFiring(m.getFiring());
					novisited.push(markSet.get(m));
					markSet.remove(m);
				}
				continue;
			}
			markSet.put(m, m);

			// make genvec
			GenVec genv = new GenVec(numOfGenTrans);
			for (Trans tr : net.getGenTransSet().values()) {
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
			for (Trans tr : net.getImmTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					hasImmTrans = true;
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						if (dest.getFiring() < arcSet.get(dest).getFiring()) {
							arcSet.get(dest).setFiring(dest.getFiring());
						}
						dest = arcSet.get(dest);
					} else {
						novisited.push(dest);
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
			
			for (Trans tr : net.getGenTransSet().values()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						if (dest.getFiring() < arcSet.get(dest).getFiring()) {
							arcSet.get(dest).setFiring(dest.getFiring());
						}
						dest = arcSet.get(dest);
					} else {
						novisited.push(dest);
						arcSet.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			
			for (Trans tr : net.getExpTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						if (dest.getFiring() < arcSet.get(dest).getFiring()) {
							arcSet.get(dest).setFiring(dest.getFiring());
						}
						dest = arcSet.get(dest);
					} else {
						novisited.push(dest);
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
