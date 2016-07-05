package jspetrinet.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jspetrinet.exception.ASTException;
import jspetrinet.graph.Arc;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.*;

public class MarkingMatrix {
	protected final MarkingGraph mp;

	protected final Map<Mark,Integer> revMarkIndex;
	protected final Map<MarkGroup,String> revMarkGroupIndex;

	protected final Map<GenVec,MarkGroup> genGroup;
	protected final Map<GenVec,MarkGroup> immGroup;
	
	protected List<GenVec> sortedAllGenVec;

	public MarkingMatrix(MarkingGraph mp, boolean oneBased) {
		this.mp = mp;
		revMarkIndex = new HashMap<Mark,Integer>();
		revMarkGroupIndex = new HashMap<MarkGroup,String>();
		genGroup = mp.getGenGroup();
		immGroup = mp.getImmGroup();

		Set<GenVec> tmp = new HashSet<GenVec>();
		tmp.addAll(mp.getImmGroup().keySet());
		tmp.addAll(mp.getGenGroup().keySet());
		sortedAllGenVec = new ArrayList<GenVec>(tmp);
		Collections.sort(sortedAllGenVec);

		this.createIndex(oneBased);

		for (MarkGroup src : immGroup.values()) {
			for (MarkGroup dest : immGroup.values()) {
				this.makeArcI(src, dest);
			}
		}
		for (MarkGroup src : immGroup.values()) {
			for (MarkGroup dest : genGroup.values()) {
				this.makeArcI(src, dest);
			}
		}
		for (MarkGroup src : genGroup.values()) {
			for (MarkGroup dest : immGroup.values()) {
				this.makeArcE(src, dest);
				this.makeArcG(src, dest);
			}
		}
		for (MarkGroup src : genGroup.values()) {
			for (MarkGroup dest : genGroup.values()) {
				if (src != dest) {
					this.makeArcE(src, dest);
				}
				this.makeArcG(src, dest);
			}
		}
	}
	
	public MarkingGraph getMarkingGraph() {
		return mp;
	}

	private void createIndex(boolean oneBased) {
		revMarkIndex.clear();
		final int start = oneBased ? 1 : 0;

		// IMM
		int x = 0;
		for (GenVec gv : sortedAllGenVec) {
			if (immGroup.containsKey(gv)) {
				MarkGroup mg = immGroup.get(gv);
				this.revMarkGroupIndex.put(mg, "I"+x);
				int i = start;
				for (Mark m : mg.getMarkSet()) {
					revMarkIndex.put(m, i);
					i++;
				}
			}
			if (genGroup.containsKey(gv)) {
				MarkGroup mg = genGroup.get(gv);
				this.revMarkGroupIndex.put(mg, "G"+x);
				int i = start;
				for (Mark m : mg.getMarkSet()) {
					revMarkIndex.put(m, i);
					i++;
				}
			}
			x++;
		}
	}
	
	public List<GenVec> getSortedAllGenVec() {
		return this.sortedAllGenVec;
	}
	
	public String getGroupLabel(MarkGroup mg) {
		return revMarkGroupIndex.get(mg);
	}

	public void makeArcI(MarkGroup srcMarkGroup, MarkGroup destMarkGroup) {
		for (Mark src: srcMarkGroup.getMarkSet()) {
			for (Arc arc: src.getOutArc()) {
				Mark dest = (Mark) arc.getDest();
				if (destMarkGroup.getMarkSet().contains(dest)) {
					new MarkingArc(srcMarkGroup, destMarkGroup, null);
					return;
				}
			}
		}
	}

	public void makeArcE(MarkGroup srcMarkGroup, MarkGroup destMarkGroup) {
		for (Mark src: srcMarkGroup.getMarkSet()) {
			for (Arc arc: src.getOutArc()) {
				Mark dest = (Mark) arc.getDest();
				if (destMarkGroup.getMarkSet().contains(dest)) {
					Trans tr = ((MarkingArc) arc).getTrans();
					if (tr instanceof ExpTrans) {
						new MarkingArc(srcMarkGroup, destMarkGroup, null);
						return;
					}
				}
			}
		}
	}

	public void makeArcG(MarkGroup srcMarkGroup, MarkGroup destMarkGroup) {
		Set<Trans> mm = new HashSet<Trans>();
		for (Mark src: srcMarkGroup.getMarkSet()) {
			for (Arc arc: src.getOutArc()) {
				Mark dest = (Mark) arc.getDest();
				if (destMarkGroup.getMarkSet().contains(dest)) {
					Trans tr = ((MarkingArc) arc).getTrans();
					if (!mm.contains(tr)) {
						if (tr instanceof GenTrans) {
							new MarkingArc(srcMarkGroup, destMarkGroup, tr);
							mm.add(tr);
						}
					}
				}
			}
		}
	}

	public List<List<Object>> getMatrixI(Net net, MarkGroup srcMarkGroup, MarkGroup destMarkGroup) {
		List< List<Object>> result = new ArrayList< List<Object> >();
		for (Mark src: srcMarkGroup.getMarkSet()) {
			net.setCurrentMark(src);
			for (Arc arc: src.getOutArc()) {
				List<Object> elem = new ArrayList<Object>();
				Mark dest = (Mark) arc.getDest();
				if (destMarkGroup.getMarkSet().contains(dest)) {
					MarkingArc markingArc = (MarkingArc) arc;
					elem.add(revMarkIndex.get(src));
					elem.add(revMarkIndex.get(dest));
					if (markingArc.getTrans() instanceof ImmTrans) {
						ImmTrans tr = (ImmTrans) markingArc.getTrans();
						try {
							elem.add(tr.getWeight().eval(net));
						} catch (ASTException e) {
							System.err.println("Fail to get weight: " + tr.getLabel());
						}
						result.add(elem);
					}
				}
			}
		}
		return result;
	}

	public Map<Trans,List<List<Object>>> getMatrixG(Net net, MarkGroup srcMarkGroup, MarkGroup destMarkGroup) {
		Map<Trans,List<List<Object>>> result = new HashMap<Trans,List<List<Object>>>();
		List<List<Object>> resultE = new ArrayList<List<Object>>();
		result.put(null, resultE);
		for (Mark src: srcMarkGroup.getMarkSet()) {
			net.setCurrentMark(src);
			for (Arc arc: src.getOutArc()) {
				Mark dest = (Mark) arc.getDest();
				if (destMarkGroup.getMarkSet().contains(dest)) {
					List<Object> elem = new ArrayList<Object>();
					MarkingArc markingArc = (MarkingArc) arc;
					if (markingArc.getTrans() instanceof ExpTrans) {
						elem.add(revMarkIndex.get(src));
						elem.add(revMarkIndex.get(dest));
						ExpTrans tr = (ExpTrans) markingArc.getTrans();
						try {
							elem.add(tr.getRate().eval(net));
						} catch (ASTException e) {
							System.err.println("Fail to get rate: " + tr.getLabel());
						}
						resultE.add(elem);
					} else if (markingArc.getTrans() instanceof GenTrans) {
						elem.add(revMarkIndex.get(src));
						elem.add(revMarkIndex.get(dest));
						GenTrans tr = (GenTrans) markingArc.getTrans();
						try {
							elem.add(tr.getDist().eval(net));
						} catch (ASTException e) {
							System.err.println("Fail to get dist: " + tr.getLabel());
						}
						if (!result.containsKey(tr)) {
							result.put(tr, new ArrayList<List<Object>>());
						}
						result.get(tr).add(elem);
					}
				}
			}
		}
		return result;
	}

//	public List<List<Object>> sreward(Net net, String reward, MarkGroup ms) throws ASTException {
//		return sreward(net, reward, ms.getMarkSet());
//	}
//	
//	public List< List<Object>> sreward(Net net, String reward, Set<Mark> ms) throws ASTException {
//		net.setReward(reward);
//		List< List<Object> > result = new ArrayList< List<Object> >();
//		for (Mark m: ms) {
//			net.setCurrentMark(m);
//			List<Object> tmp = new ArrayList<Object>();
//			tmp.add(revMarkIndex.get(m));
//			tmp.add(net.getReward());
//			result.add(tmp);
//		}
//		return result;
//	}
//	
	public List<List<Object>> getMakingSet(MarkGroup markGroup) {
		List<List<Object>> result = new ArrayList<List<Object>>();
		for (Mark m: markGroup.getMarkSet()) {
			List<Object> elem = new ArrayList<Object>();
			elem.add(revMarkIndex.get(m));
			elem.add(m);
			result.add(elem);
		}
		return result;
	}
}
