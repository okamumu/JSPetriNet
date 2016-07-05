package jspetrinet.marking;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class MarkingProcess {

	protected Net net;

	protected final Map<Mark,Mark> markSet;
	protected final Map<Mark,Mark> arcSet;

	protected int numOfGenTrans;
	protected final Map<GenVec,MarkGroup> genGroup;
	protected final Map<GenVec,MarkGroup> immGroup;
	
	public MarkingProcess() {
		markSet = new HashMap<Mark,Mark>();
		genGroup = new HashMap<GenVec,MarkGroup>();
		immGroup = new HashMap<GenVec,MarkGroup>();
		arcSet = new HashMap<Mark,Mark>();
		numOfGenTrans = 0;
	}
	
	public final int count() {
		return markSet.size();
	}
	
	public final int immcount() {
		int total = 0;
		for (MarkGroup mg: immGroup.values()) {
			total += mg.size();
		}
		return total;
	}
	
	public final Net getNet() {
		return net;
	}

	public final Map<GenVec,MarkGroup> getImmGroup() {
		return immGroup;
	}

	public final Map<GenVec,MarkGroup> getGenGroup() {
		return genGroup;
	}

	public final MarkGroup getExpGroup() {
		return genGroup.get(new GenVec(numOfGenTrans));
	}

	public Mark create(Mark init, Net net) throws ASTException {
		this.net = net;
		markSet.clear();
		arcSet.clear();
		arcSet.put(init, init);//初期マーキングを到達済みとして記憶

		numOfGenTrans = net.getNumOfGenTrans();
		immGroup.clear();
		GenVec genv = new GenVec(numOfGenTrans);
		immGroup.put(genv, new MarkGroup());
		genGroup.clear();
		genGroup.put(genv, new MarkGroup());

		LinkedList<Mark> novisited = new LinkedList<Mark>();
		novisited.push(init);
		create(novisited, net);
		return init;//初期マーキングをそのまま返しているが意味はあるのか。引数はMarkだが、代入はしなくていいのか
	}
	
	protected void create(LinkedList<Mark> novisited, Net net) throws ASTException {
		while (!novisited.isEmpty()) {
			Mark m = novisited.pop();
			net.setCurrentMark(m);
			if (markSet.containsKey(m)) {
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
					if (arcSet.containsKey(dest)) {//発火先がすでに到達済み
						dest = arcSet.get(dest);
					} else {
						novisited.push(dest);//到達していないマーキングを挿入(リストの先頭に)
						arcSet.put(dest, dest);//到達済みマーキングとして記録
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
		}
	}
}
