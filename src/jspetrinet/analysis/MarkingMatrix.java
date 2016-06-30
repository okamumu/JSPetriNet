package jspetrinet.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	protected final Map<Mark,Integer> revIndex;
	protected final Map<GenVec,String> genMatrixLabel;
	protected final Map<GenVec,String> immMatrixLabel;

	protected final Map<GenVec,MarkGroup> genGroup;
	protected final Map<GenVec,MarkGroup> immGroup;
	
	public static int ExpTransIndex = -1;

	public MarkingMatrix(MarkingGraph mp, boolean oneBased) {
		this.mp = mp;
		revIndex = new HashMap<Mark,Integer>();
		genGroup = mp.getGenGroup();
		immGroup = mp.getImmGroup();
		genMatrixLabel = new HashMap<GenVec,String>();
		immMatrixLabel = new HashMap<GenVec,String>();
		this.createIndex(oneBased);
	}
	
	public MarkingGraph getMarkingProcess() {
		return mp;
	}

	private void createIndex(boolean oneBased) {

		revIndex.clear();

		int start;
		if (oneBased == true) {
			start = 1;
		} else {
			start = 0;
		}

		// IMM
		int x = 0;
		for (GenVec gv : immGroup.keySet()) {
			MarkGroup mg = immGroup.get(gv);
			immMatrixLabel.put(gv, "I"+x);
			int i = start;
			for (Mark m : mg.markset()) {
				revIndex.put(m, i);
				i++;
			}
			x++;
		}

		// Exp/Gen Matrices
		x = 0;
		for (GenVec gv : genGroup.keySet()) {
			MarkGroup mg = genGroup.get(gv);
			genMatrixLabel.put(gv, "G"+x);
			int i = start;
			for (Mark m : mg.markset()) {
				revIndex.put(m, i);
				i++;
			}
			x++;
		}
	}
	
	public Map<GenVec,String> getImmMatrixLabel() {
		return this.immMatrixLabel;
	}

	public Map<GenVec,String> getGenMatrixLabel() {
		return this.genMatrixLabel;
	}

	public Map<Trans,List<List<Object>>> getMatrixG(Net net, MarkGroup mgms, MarkGroup mgds) {
		Set<Mark> ms = mgms.markset();
		Set<Mark> ds = mgds.markset();
		Map<Trans,List<List<Object>>> result = new HashMap<Trans,List<List<Object>>>();
		List<List<Object> > resultE = new ArrayList<List<Object> >();
		result.put(null, resultE);
		for (Mark m: ms) {
			net.setCurrentMark(m);
			for (Arc a: m.getOutArc()) {
				Mark nm = (Mark) a.getDest();
				if (ds.contains(nm)) {
					List<Object> tmp = new ArrayList<Object>();
					MarkingArc marc = (MarkingArc) a;
					if (marc.getTrans() instanceof ExpTrans) {
						tmp.add(revIndex.get(m));
						tmp.add(revIndex.get(nm));
						ExpTrans tr = (ExpTrans) marc.getTrans();
						try {
							tmp.add(tr.getRate().eval(net));
						} catch (ASTException e) {
							System.err.println("Fail to get rate: " + tr.getLabel());
						}
						resultE.add(tmp);
					} else if (marc.getTrans() instanceof GenTrans) {
						tmp.add(revIndex.get(m));
						tmp.add(revIndex.get(nm));
						GenTrans tr = (GenTrans) marc.getTrans();
						try {
							tmp.add(tr.getDist().eval(net));
						} catch (ASTException e) {
							System.err.println("Fail to get dist: " + tr.getLabel());
						}
						if (!result.containsKey(tr)) {
							result.put(tr, new ArrayList<List<Object> >());
						}
						result.get(tr).add(tmp);
					}
				}
			}
		}
		return result;
	}

	public List<List<Object>> getMatrixI(Net net, MarkGroup mgms, MarkGroup mgds) {		
		Set<Mark> ms = mgms.markset();
		Set<Mark> ds = mgds.markset();
		List< List<Object> > result = new ArrayList< List<Object> >();
		for (Mark m: ms) {
			net.setCurrentMark(m);
			for (Arc a: m.getOutArc()) {
				List<Object> tmp = new ArrayList<Object>();
				Mark nm = (Mark) a.getDest();
				if (ds.contains(nm)) {
					MarkingArc marc = (MarkingArc) a;
					tmp.add(revIndex.get(m));
					tmp.add(revIndex.get(nm));
					if (marc.getTrans() instanceof ImmTrans) {
						ImmTrans tr = (ImmTrans) marc.getTrans();
						try {
							tmp.add(tr.getWeight().eval(net));
						} catch (ASTException e) {
							System.err.println("Fail to get weight: " + tr.getLabel());
//							e.printStackTrace();
						}
						result.add(tmp);
					}
				}
			}
		}
		return result;
	}

	public List< List<Object> > sreward(Net net, String reward, MarkGroup ms) throws ASTException {
		return sreward(net, reward, ms.markset());
	}
	
	public List< List<Object> > sreward(Net net, String reward, Set<Mark> ms) throws ASTException {
		net.setReward(reward);
		List< List<Object> > result = new ArrayList< List<Object> >();
		for (Mark m: ms) {
			net.setCurrentMark(m);
			List<Object> tmp = new ArrayList<Object>();
			tmp.add(revIndex.get(m));
			tmp.add(net.getReward());
			result.add(tmp);
		}
		return result;
	}
	
	public List< List<Object> > getMakingSet(MarkGroup mg) {
		return this.getMakingSet(mg.markset());
	}

	public List< List<Object> > getMakingSet(Set<Mark> ms) {
		List< List<Object> > result = new ArrayList< List<Object> >();
		for (Mark m: ms) {
			List<Object> tmp = new ArrayList<Object>();
			tmp.add(revIndex.get(m));
			tmp.add(m);
			result.add(tmp);
		}
//		Collections.sort(n);
//		for (Integer i: n) {
//			List<Object> tmp = new ArrayList<Object>();
//			tmp.add(i);
//			tmp.add(index.get(i));
//			result.add(tmp);
//		}
		return result;
	}
}
