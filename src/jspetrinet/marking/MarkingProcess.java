package jspetrinet.marking;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class MarkingProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4650765905602529452L;

	protected Net net;

	protected final Map<Mark,Mark> markSet;
	protected final Map<Mark,Mark> arcSet;
	protected final Map<Integer,Mark> index;

	protected int numOfGenTrans;
	protected final Map<GenVec,MarkGroup> genGroup;
	protected final Map<GenVec,MarkGroup> immGroup;
	
	public MarkingProcess() {
		markSet = new HashMap<Mark,Mark>();
		genGroup = new HashMap<GenVec,MarkGroup>();
		immGroup = new HashMap<GenVec,MarkGroup>();
		arcSet = new HashMap<Mark,Mark>();
		index = new HashMap<Integer,Mark>();
		numOfGenTrans = 0;
	}
	
//	public void createIndex(boolean oneBased) {
//		int i;
//		if (oneBased == true) {
//			i = 1;
//		} else {
//			i = 0;
//		}
//
//		// IMM
//		for (MarkGroup mg : immGroup.values()) {
//			for (Mark m : mg.markset()) {
//				index.put(i, m);
//				m.setIndex(i);
//				i++;
//			}
//		}
//
//		// Exp/Gen Matrices
//		for (MarkGroup mg : genGroup.values()) {
//			for (Mark m : mg.markset()) {
//				index.put(i, m);
//				m.setIndex(i);
//				i++;
//			}
//		}
//	}
//
//	public List< List<Object> > matrix(Net net, Set<Mark> ms) throws ASTException {
//		List< List<Object> > result = new ArrayList< List<Object> >();
//		for (Mark m: ms) {
//			net.setCurrentMark(m);
//			for (Arc a: m.getOutArc()) {
//				List<Object> tmp = new ArrayList<Object>();
//				Mark nm = (Mark) a.getDest();
//				MarkingArc marc = (MarkingArc) a;
//				tmp.add(m.index());
//				tmp.add(nm.index());
//				if (marc.getTrans() instanceof ImmTrans) {
//					ImmTrans tr = (ImmTrans) marc.getTrans();
//					tmp.add(tr.getWeight().eval(net));
//				} else if (marc.getTrans() instanceof ExpTrans) {
//					ExpTrans tr = (ExpTrans) marc.getTrans();
//					tmp.add(tr.getRate().eval(net));
//				} else if (marc.getTrans() instanceof GenTrans) {
//					GenTrans tr = (GenTrans) marc.getTrans();
//					tmp.add(tr.getDist().eval(net));
//				}
//				result.add(tmp);
//			}
//		}
//		return result;
//	}
//
//	public List< List<Object> > matrix(Net net, Set<Mark> ms, Set<Mark> ds) throws ASTException {
//		List< List<Object> > result = new ArrayList< List<Object> >();
//		for (Mark m: ms) {
//			net.setCurrentMark(m);
//			for (Arc a: m.getOutArc()) {
//				List<Object> tmp = new ArrayList<Object>();
//				Mark nm = (Mark) a.getDest();
//				if (ds.contains(nm)) {
//					MarkingArc marc = (MarkingArc) a;
//					tmp.add(m.index());
//					tmp.add(nm.index());
//					if (marc.getTrans() instanceof ImmTrans) {
//						ImmTrans tr = (ImmTrans) marc.getTrans();
//						tmp.add(tr.getWeight().eval(net));
//					} else if (marc.getTrans() instanceof ExpTrans) {
//						ExpTrans tr = (ExpTrans) marc.getTrans();
//						tmp.add(tr.getRate().eval(net));
//					} else if (marc.getTrans() instanceof GenTrans) {
//						GenTrans tr = (GenTrans) marc.getTrans();
//						tmp.add(tr.getDist().eval(net));
//					}
//					result.add(tmp);
//				}
//			}
//		}
//		return result;
//	}
//
//	public List< List<Object> > sreward(Net net, String reward, Set<Mark> ms) throws ASTException {
//		net.setReward(reward);
//		List< List<Object> > result = new ArrayList< List<Object> >();
//		for (Mark m: ms) {
//			net.setCurrentMark(m);
//			List<Object> tmp = new ArrayList<Object>();
//			tmp.add(m.index());
//			tmp.add(net.getReward());
//			result.add(tmp);
//		}
//		return result;
//	}

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

//	public Mark mark(int index) {
//		return this.index.get(index);
//	}
//
//	public Mark mark(Mark m) {
//		return markSet.get(m);
//	}
//
//	public final Set<Mark> markset() {
//		return markSet.keySet();
//	}

	public final Map<GenVec,MarkGroup> getImmGroup() {
		return immGroup;
	}

	public final Map<GenVec,MarkGroup> getGenGroup() {
		return genGroup;
	}

	public final MarkGroup getExpGroup() {
		return genGroup.get(new GenVec(numOfGenTrans));
	}

//	public final Map<GenVec,MarkGroup> imm() {
//		return immGroup;
//	}
//
//	public final MarkGroup imm(GenVec gv) {
//		return immGroup.get(gv);
//	}
//
//	public final Map<GenVec,MarkGroup> gen() {
//		return genGroup;
//	}
//
//	public final MarkGroup gen(GenVec gv) {
//		return genGroup.get(gv);
//	}

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
