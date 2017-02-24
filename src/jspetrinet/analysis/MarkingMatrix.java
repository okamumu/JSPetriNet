package jspetrinet.analysis;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jspetrinet.JSPetriNet;
import jspetrinet.ast.*;
import jspetrinet.exception.JSPNException;
import jspetrinet.graph.Arc;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.marking.PetriAnalysis;
import jspetrinet.petri.*;

public class MarkingMatrix {

	protected final Net net;
//	protected final List<MarkGroup> immMarkGroup;
//	protected final List<MarkGroup> genMarkGroup;
	protected final List<MarkGroup> allMarkGroup;

	//	private final MarkingGraph mp;

	private final GroupMarkingGraph markGroups;
	private final Map<Mark,Integer> revMarkIndex;


	//	private final Map<GenVec,MarkGroup> immGroup;
//	private final Map<GenVec,MarkGroup> genGroup;
	
//	private List<GenVec> sortedAllGenVec;
//	private List<Trans> sortedImmTrans;
	
	public MarkingMatrix(GroupMarkingGraph markGroups, boolean oneBased) {
		this.net = markGroups.getNet();
		this.markGroups = markGroups;
//		this.mp = mp;
//		immMarkGroup = markGroups.getImmMarkGroupList();
//		genMarkGroup = markGroups.getGenMarkGroupList();
		allMarkGroup = markGroups.getAllMarkGroupList();
		revMarkIndex = new HashMap<Mark,Integer>();

//		genGroup = mp.getGenGroup();
//		immGroup = mp.getImmGroup();
		
//		Set<GenVec> tmp = new HashSet<GenVec>();
//		tmp.addAll(mp.getImmGroup().keySet());
//		tmp.addAll(mp.getGenGroup().keySet());
//
//		sortedAllGenVec = new ArrayList<GenVec>(tmp);
//		Collections.sort(sortedAllGenVec);
//		sortedImmTrans = new ArrayList<Trans>(mp.getNet().getImmTransSet());
//		sortedImmTrans.sort(new PriorityComparator());

		this.createIndex(oneBased);
	}
	
	private void createIndex(boolean oneBased) {
		final int start = oneBased ? 1 : 0;
		for (MarkGroup mg : allMarkGroup) {
			int i = start;
			for (Mark m : mg.getMarkSet()) {
				revMarkIndex.put(m, i);
				i++;
			}
		}
//		for (MarkGroup mg : immMarkGroup) {
//			int i = start;
//			for (Mark m : mg.getMarkSet()) {
//				revMarkIndex.put(m, i);
//				i++;
//			}
//		}
//		for (MarkGroup mg : genMarkGroup) {
//			int i = start;
//			for (Mark m : mg.getMarkSet()) {
//				revMarkIndex.put(m, i);
//				i++;
//			}
//		}
	}

//	protected MarkingGraph getMarkingGraph() {
//		return mp;
//	}
//	
//	protected Map<GenVec,MarkGroup> getImmGroup() {
//		return immGroup;
//	}
//
//	protected Map<GenVec,MarkGroup> getGenGroup() {
//		return genGroup;
//	}

//	protected List<GenVec> getSortedAllGenVec() {
//		return this.sortedAllGenVec;
//	}
//	
//	protected String getGroupLabel(MarkGroup mg) {
//		return revMarkGroupIndex.get(mg);
//	}

//	protected List<List<Object>> getMatrixI(Net net, MarkGroup srcMarkGroup, MarkGroup destMarkGroup) {
//		List< List<Object>> result = new ArrayList< List<Object> >();
//		for (Mark src: srcMarkGroup.getMarkSet()) {
//			net.setCurrentMark(src);
//			for (Arc arc: src.getOutArc()) {
//				List<Object> elem = new ArrayList<Object>();
//				Mark dest = (Mark) arc.getDest();
//				if (destMarkGroup.getMarkSet().contains(dest)) {
//					MarkingArc markingArc = (MarkingArc) arc;
//					elem.add(revMarkIndex.get(src));
//					elem.add(revMarkIndex.get(dest));
//					if (markingArc.getTrans() instanceof ImmTrans) {
//						ImmTrans tr = (ImmTrans) markingArc.getTrans();
//						try {
//							elem.add(tr.getWeight().eval(net));
//						} catch (JSPNException e) {
//							System.err.println("Fail to get weight: " + tr.getLabel());
//						}
//						result.add(elem);
//					}
//				}
//			}
//		}
//		return result;
//	}

//	protected Map<Trans,List<List<Object>>> getMatrixG(Net net, MarkGroup srcMarkGroup, MarkGroup destMarkGroup) {
//		Map<Trans,List<List<Object>>> result = new HashMap<Trans,List<List<Object>>>();
//		List<List<Object>> resultE = new ArrayList<List<Object>>();
//		result.put(null, resultE);
//		for (Mark src: srcMarkGroup.getMarkSet()) {
//			net.setCurrentMark(src);
//			for (Arc arc: src.getOutArc()) {
//				Mark dest = (Mark) arc.getDest();
//				if (destMarkGroup.getMarkSet().contains(dest)) {
//					List<Object> elem = new ArrayList<Object>();
//					MarkingArc markingArc = (MarkingArc) arc;
//					if (markingArc.getTrans() instanceof ExpTrans) {
//						elem.add(revMarkIndex.get(src));
//						elem.add(revMarkIndex.get(dest));
//						ExpTrans tr = (ExpTrans) markingArc.getTrans();
//						try {
//							elem.add(tr.getRate().eval(net));
//						} catch (JSPNException e) {
//							System.err.println("Fail to get rate: " + tr.getLabel());
//						}
//						resultE.add(elem);
//					} else if (markingArc.getTrans() instanceof GenTrans) {
//						elem.add(revMarkIndex.get(src));
//						elem.add(revMarkIndex.get(dest));
//						GenTrans tr = (GenTrans) markingArc.getTrans();
//						elem.add(1);
//						if (!result.containsKey(tr)) {
//							result.put(tr, new ArrayList<List<Object>>());
//						}
//						result.get(tr).add(elem);
//					}
//				}
//			}
//		}
//		return result;
//	}

	protected List<List<Object>> getMatrixI(Net net, MarkGroup srcMarkGroup, MarkGroup destMarkGroup) {
		List<List<Object>> result = new ArrayList<List<Object>>();
		for (Mark src: srcMarkGroup.getMarkSet()) {
			net.setCurrentMark(src);
			for (Arc arc: src.getOutArc()) {
				List<Object> elem = new ArrayList<Object>();
				Mark dest = (Mark) arc.getDest();
				if (markGroups.getMarkGroup(dest) == destMarkGroup) {
					MarkingArc markingArc = (MarkingArc) arc;
					elem.add(revMarkIndex.get(src));
					elem.add(revMarkIndex.get(dest));
					if (markingArc.getTrans() instanceof ImmTrans) {
						ImmTrans tr = (ImmTrans) markingArc.getTrans();
						try {
							elem.add(tr.getWeight().eval(net));
						} catch (JSPNException e) {
							System.err.println("Fail to get weight: " + tr.getLabel());
						}
						result.add(elem);
					}
				}
			}
		}
		return result;
	}

	protected Map<Trans,List<List<Object>>> getMatrixG(Net net, MarkGroup srcMarkGroup, MarkGroup destMarkGroup) {
		Map<Trans,List<List<Object>>> result = new HashMap<Trans,List<List<Object>>>();
		List<List<Object>> resultE = new ArrayList<List<Object>>();
		result.put(null, resultE);
		for (Mark src: srcMarkGroup.getMarkSet()) {
			net.setCurrentMark(src);
			for (Arc arc: src.getOutArc()) {
				Mark dest = (Mark) arc.getDest();
				if (markGroups.getMarkGroup(dest) == destMarkGroup) {
					List<Object> elem = new ArrayList<Object>();
					MarkingArc markingArc = (MarkingArc) arc;
					if (markingArc.getTrans() instanceof ExpTrans) {
						elem.add(revMarkIndex.get(src));
						elem.add(revMarkIndex.get(dest));
						ExpTrans tr = (ExpTrans) markingArc.getTrans();
						try {
							elem.add(tr.getRate().eval(net));
						} catch (JSPNException e) {
							System.err.println("Fail to get rate: " + tr.getLabel());
						}
						resultE.add(elem);
					} else if (markingArc.getTrans() instanceof GenTrans) {
						elem.add(revMarkIndex.get(src));
						elem.add(revMarkIndex.get(dest));
						GenTrans tr = (GenTrans) markingArc.getTrans();
						elem.add(1);
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

//	protected List<List<Object>> getSumVecI(Net net, MarkGroup srcMarkGroup) {
//		List<List<Object>> result = new ArrayList<List<Object>>();
//		for (Mark src: srcMarkGroup.getMarkSet()) {
//			AST d = null;
//			for (Arc arc: src.getOutArc()) {
//				MarkingArc markingArc = (MarkingArc) arc;
//				if (markingArc.getTrans() instanceof ImmTrans) {
//					ImmTrans tr = (ImmTrans) markingArc.getTrans();
//					if (d == null) {
//						d = tr.getWeight();
//					} else {
//						d = new ASTArithmetic(d, tr.getWeight(), "+");
//					}
//				}
//			}
//			List<Object> elem = new ArrayList<Object>();
//			elem.add(revMarkIndex.get(src));
//			elem.add(src);
//			if (d == null) {
//				elem.add(new ASTValue(0));
//			} else {
//				elem.add(d);
//			}
//			result.add(elem);
//		}
//		return result;
//	}

	protected List<List<Object>> getSumVecI(Net net, MarkGroup srcMarkGroup) throws JSPNException {
		List<List<Object>> result = new ArrayList<List<Object>>();
		for (Mark m : srcMarkGroup.getMarkSet()) {
			net.setCurrentMark(m);
			AST d = null;
			int highestPriority = 0;
			for (ImmTrans tr : net.getImmTransSet()) {
				if (highestPriority > tr.getPriority()) {
					break;
				}
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					highestPriority = tr.getPriority();
					if (d == null) {
						d = tr.getWeight();
					} else {
						d = new ASTArithmetic(d, tr.getWeight(), "+");
					}
					break;
				default:
				}
			}
			List<Object> elem = new ArrayList<Object>();
			elem.add(revMarkIndex.get(m));
			elem.add(m);
			if (d == null) {
				elem.add(new ASTValue(0));
			} else {
				elem.add(d);
			}
			result.add(elem);
		}
		return result;
	}

	protected Map<Trans,List<List<Object>>> getSumVecG(Net net, MarkGroup mg) throws JSPNException {
		Map<Trans,List<List<Object>>> result = new HashMap<Trans,List<List<Object>>>();
		List<List<Object>> resultE = new ArrayList<List<Object>>();
		result.put(null, resultE);
		for (Mark m: mg.getMarkSet()) {
			net.setCurrentMark(m);
			Map<Trans,AST> tmp = new HashMap<Trans,AST>();
			tmp.put(null, null);
			for (ExpTrans tr : net.getExpTransSet()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					if (tmp.get(null) == null) {
						tmp.put(null, tr.getRate());
					} else {
						tmp.put(null, new ASTArithmetic(tmp.get(null), tr.getRate(), "+"));
					}
					break;
				default:
				}				
			}
			for (GenTrans tr : net.getGenTransSet()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					if (!tmp.containsKey(tr)) {
						tmp.put(tr, new ASTValue(1));
					} else {
						tmp.put(tr, new ASTArithmetic(tmp.get(tr), new ASTValue(1), "+"));
					}
					break;
				default:
				}				
			}
			for (Map.Entry<Trans, AST> entry : tmp.entrySet()) {
				List<Object> elem = new ArrayList<Object>();
				elem.add(revMarkIndex.get(m));
				elem.add(m);
				if (entry.getValue() == null) {
					elem.add(new ASTValue(0));
				} else {
					elem.add(entry.getValue());
				}
				List<List<Object>> listelem;
				if (!result.containsKey(entry.getKey())) {
					listelem = new ArrayList<List<Object>>();
					result.put(entry.getKey(), listelem);
				} else {
					listelem = result.get(entry.getKey());
				}
				listelem.add(elem);
			}
		}
		return result;
	}

	protected List<List<Object>> getMakingSet(MarkGroup markGroup) {
		List<List<Object>> result = new ArrayList<List<Object>>();
		for (Mark m: markGroup.getMarkSet()) {
			List<Object> elem = new ArrayList<Object>();
			elem.add(revMarkIndex.get(m));
			elem.add(m);
			result.add(elem);
		}
		return result;
	}
	
//	// graph
//
//	private static String ln = "\n";
//	private static String genFormat = "\"%s\" [label=\"%s\n %s%s\"];" + ln;
//	private static String immFormat = "\"%s\" [label=\"%s\n %s%s\"];" + ln;
//	private static String genFormatG = "\"%s\" [label=\"%s(%d)\n %s\"];" + ln;
//	private static String immFormatG = "\"%s\" [label=\"%s(%d)\n %s\"];" + ln;
//	private static String arcFormat = "\"%s\" -> \"%s\" [label=\"%s\"];" + ln;
//
//	public void dotMarking(PrintWriter bw) {
//		bw.println("digraph { layout=dot; overlap=false; splines=true;");
//		for (MarkGroup mg : immGroup.values()) {
//			for (Mark m : mg.getMarkSet()) {
//				bw.printf(immFormat, m,
//						JSPetriNet.markToString(net, m),
//						revMarkGroupIndex.get(mg),
//						JSPetriNet.genvecToString(net, m.getGenVec()));
//				for (Arc a : m.getOutArc()) {
//					MarkingArc ma = (MarkingArc) a;
//					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), ma.getTrans().getLabel());
//				}
//			}
//		}
//		for (MarkGroup mg : genGroup.values()) {
//			for (Mark m : mg.getMarkSet()) {
//				bw.printf(genFormat, m,
//						JSPetriNet.markToString(net, m),
//						revMarkGroupIndex.get(mg),
//						JSPetriNet.genvecToString(net, m.getGenVec()));
//				for (Arc a : m.getOutArc()) {
//					MarkingArc ma = (MarkingArc) a;
//					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), ma.getTrans().getLabel());
//				}
//			}
//		}
//		bw.println("}");
//	}
//
//	public void dotMarkGroup(PrintWriter bw) {
////		CreateGroupMarkingGraph.createMarkGroupGraph(mp.getNet(), immGroup, genGroup);
//		bw.println("digraph { layout=dot; overlap=false; splines=true;");
//		for (Map.Entry<GenVec, MarkGroup> entry : this.genGroup.entrySet()) {
//			bw.printf(genFormatG, entry.getValue(),
//					revMarkGroupIndex.get(entry.getValue()),
//					entry.getValue().getMarkSet().size(),
//					JSPetriNet.genvecToString(net, entry.getKey()));
//			for (Arc a : entry.getValue().getOutArc()) {
//				MarkingArc ma = (MarkingArc) a;
//				if (ma.getTrans() instanceof ExpTrans) {
//					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), "EXP(" + ma.getTrans().getLabel() + ")");
//				} else {
//					bw.printf(arcFormat, ma.getSrc(), ma.getDest(), ma.getTrans().getLabel());
//				}
//			}
//		}
//		for (Map.Entry<GenVec, MarkGroup> entry : this.immGroup.entrySet()) {
//			bw.printf(immFormatG, entry.getValue(),
//					revMarkGroupIndex.get(entry.getValue()),
//					entry.getValue().getMarkSet().size(),
//					JSPetriNet.genvecToString(net, entry.getKey()));
//			for (Arc a : entry.getValue().getOutArc()) {
//				MarkingArc ma = (MarkingArc) a;
//				bw.printf(arcFormat, ma.getSrc(), ma.getDest(),  "IMM(" + ma.getTrans().getLabel() + ")");
//			}
//		}
//		bw.println("}");
//	}
}
