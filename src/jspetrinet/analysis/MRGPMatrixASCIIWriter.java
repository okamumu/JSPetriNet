package jspetrinet.analysis;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jspetrinet.JSPetriNet;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class MRGPMatrixASCIIWriter extends MarkingMatrix {

	private final Map<GroupPair,String> matrixName;	
	private static String colsep = "\t";
	
	public MRGPMatrixASCIIWriter(Net net, MarkingGraph mp, boolean oneBased) {
		super(net, mp, oneBased);
		matrixName = new HashMap<GroupPair,String>();
	}

	public void writeMarkSet(PrintWriter pw) {
		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec gv : this.getSortedAllGenVec()) {
			if (immGroup.containsKey(gv)) {
				MarkGroup mg = immGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				pw.println(this.getGroupLabel(mg) + " " + glabel);
				List<List<Object>> s = this.getMakingSet(mg);
				for (List<Object> e: s) {
					Mark m = (Mark) e.get(1);
					pw.println(e.get(0) + " : " + JSPetriNet.markToString(net, m));
				}
			}
			if (genGroup.containsKey(gv)) {
				MarkGroup mg = genGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				pw.println(this.getGroupLabel(mg) + " " + glabel);
				List<List<Object>> s = this.getMakingSet(mg);
				for (List<Object> e: s) {
					Mark m = (Mark) e.get(1);
					pw.println(e.get(0) + " : " + JSPetriNet.markToString(net, m));
				}
			}
		}
	}
	
	public void writeStateVec(PrintWriter pw, Mark imark) {
		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec gv : this.getSortedAllGenVec()) {
			if (immGroup.containsKey(gv)) {
				MarkGroup mg = immGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				pw.println("# " + this.getGroupLabel(mg) + "init" + " " + glabel);
				pw.println("# size " + mg.size() + " 1");
				List<List<Object>> s = this.getMakingSet(mg);
				for (List<Object> e: s) {
					Mark m = (Mark) e.get(1);
					int value;
					if (m == imark) {
						value = 1;
					} else {
						value = 0;
					}
					pw.println(this.getGroupLabel(mg) + "init" + colsep + e.get(0) + colsep + value);
				}
			}
			if (genGroup.containsKey(gv)) {
				MarkGroup mg = genGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				pw.println("# " + this.getGroupLabel(mg) + "init" + " " + glabel);
				pw.println("# size " + mg.size() + " 1");
				List<List<Object>> s = this.getMakingSet(mg);
				for (List<Object> e: s) {
					Mark m = (Mark) e.get(1);
					int value;
					if (m == imark) {
						value = 1;
					} else {
						value = 0;
					}
					pw.println(this.getGroupLabel(mg) + "init" + colsep + e.get(0) + colsep + value);
				}
			}
		}
	}

	public void writeStateRewardVec(PrintWriter pw, List<AST> reward) throws JSPNException {
//		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec gv : this.getSortedAllGenVec()) {
			if (genGroup.containsKey(gv)) {
				MarkGroup mg = genGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				pw.println("# " + this.getGroupLabel(mg) + "rwd" + " " + glabel);
				List<List<Object>> s = this.getMakingSet(mg);
				pw.println("# size " + mg.size() + " " + reward.size());
				for (List<Object> e: s) {
					Mark m = (Mark) e.get(1);
					net.setCurrentMark(m);
					pw.print(this.getGroupLabel(mg) + "rwd" + colsep + e.get(0));
					for (AST a : reward) {
						pw.print(colsep + a.eval(net));
					}
					pw.println();
				}
			}
		}
	}

	public void writeSumVec(PrintWriter pw) throws JSPNException {
		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec gv : this.getSortedAllGenVec()) {
			if (immGroup.containsKey(gv)) {
				MarkGroup mg = immGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				pw.println("# " + this.getGroupLabel(mg) + "sum" + " " + glabel);
				List<List<Object>> s = this.getSumVecI(net, mg);
				pw.println("# size " + mg.size() + " 1");
				for (List<Object> e: s) {
					Mark m = (Mark) e.get(1);
					AST d = (AST) e.get(2);
					net.setCurrentMark(m);
					pw.print(this.getGroupLabel(mg) + "sum" + colsep + e.get(0));
					pw.print(colsep + d.eval(net));
					pw.println();
				}
			}
			if (genGroup.containsKey(gv)) {
				MarkGroup mg = genGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				Map<Trans,List<List<Object>>> d0 = this.getSumVecG(net, mg);
				{
					List<List<Object>> s = d0.get(null);
					pw.println("# " + this.getGroupLabel(mg) + "Esum" + " " + glabel);
					pw.println("# size " + mg.size() + " 1");
					for (List<Object> e: s) {
						Mark m = (Mark) e.get(1);
						AST d = (AST) e.get(2);
						net.setCurrentMark(m);
						pw.print(this.getGroupLabel(mg) + "Esum" + colsep + e.get(0));
						pw.print(colsep + d.eval(net));
						pw.println();
					}					
				}				
				for (Map.Entry<Trans, List<List<Object>>> entry : d0.entrySet()) {
					if (entry.getKey() != null) {
						List<List<Object>> s = entry.getValue();
						pw.println("# " + this.getGroupLabel(mg) + "P" + entry.getKey().getIndex() + "sum" + " " + glabel);
						pw.println("# size " + mg.size() + " 1");
						for (List<Object> e: s) {
							Mark m = (Mark) e.get(1);
							AST d = (AST) e.get(2);
							net.setCurrentMark(m);
							pw.print(this.getGroupLabel(mg) + "sum" + colsep + e.get(0));
							pw.print(colsep + d.eval(net));
							pw.println();
						}
					}
				}
			}
		}
	}

	private void defineMatrix(PrintWriter pw, String matrixName, MarkGroup src, MarkGroup dest, List<List<Object>> s) {
		pw.println("# " + matrixName + " " + this.getGroupLabel(src) + " to " + this.getGroupLabel(dest));
		pw.println("# size" + colsep + src.size() + colsep + dest.size() + colsep + s.size());
	}

	private void defineMatrix(PrintWriter pw, String matrixName, MarkGroup src, MarkGroup dest, String dist, List<List<Object>> s) {
		pw.println("# " + matrixName + " " + this.getGroupLabel(src) + " to " + this.getGroupLabel(dest) + " " + dist);
		pw.println("# size" + colsep + src.size() + colsep + dest.size() + colsep + s.size());
	}

	private void putElement(PrintWriter pw, String matrixName, Object i, Object j, Object v) {
		pw.println(matrixName + colsep + i + colsep + j + colsep + v);
	}

	private void writeImm(PrintWriter pw, MarkGroup src, MarkGroup dest) {
		if (dest.size() == 0) {
			return;
		}
		List< List<Object> > s = this.getMatrixI(net, src, dest);
		if (s.size() == 0) {
			return;
		}
		String matname = this.getGroupLabel(src) + this.getGroupLabel(dest);
		this.defineMatrix(pw, matname, src, dest, s);
		for (List<Object> e: s) {
			this.putElement(pw, matname, e.get(0), e.get(1), e.get(2));
		}
		matrixName.put(new GroupPair(src, dest), matname);
	}

	private void writeGen(PrintWriter pw, MarkGroup src, MarkGroup dest) throws JSPNException {
		if (dest.size() == 0) {
			return;
		}
		Map<Trans,List<List<Object>>> elem = this.getMatrixG(net, src, dest);
		List<List<Object>> s = elem.get(null); // get EXP
		if (s.size() != 0 || src == dest) {
			String matname = this.getGroupLabel(src) + this.getGroupLabel(dest) + "E";
			this.defineMatrix(pw, matname, src, dest, s);
			for (List<Object> e: s) {
				this.putElement(pw, matname, e.get(0), e.get(1), e.get(2));
			}
			matrixName.put(new GroupPair(src, dest), matname);
		}
		for (Map.Entry<Trans, List<List<Object>>> entry: elem.entrySet()) {
			if (entry.getKey() != null) {
				s = entry.getValue();
				if (s.size() != 0) {
					String matname = this.getGroupLabel(src) + this.getGroupLabel(dest)
						+ "P" + entry.getKey().getIndex();
					String dist = entry.getKey().getLabel() + " " + ((GenTrans) entry.getKey()).getDist().eval(net);
					this.defineMatrix(pw, matname, src, dest, dist, s);
					for (List<Object> e: s) {
						this.putElement(pw, matname, e.get(0), e.get(1), e.get(2));
					}
					matrixName.put(new GroupPair(src, dest, entry.getKey()), matname);
				}
			}
		}
	}

	public void writeMatrix(PrintWriter pw) throws JSPNException {
		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec srcgv : this.getSortedAllGenVec()) {
			for (GenVec destgv : this.getSortedAllGenVec()) {
				if (immGroup.containsKey(srcgv) && immGroup.containsKey(destgv)) {
					writeImm(pw, immGroup.get(srcgv), immGroup.get(destgv));
				}
				if (immGroup.containsKey(srcgv) && genGroup.containsKey(destgv)) {
					writeImm(pw, immGroup.get(srcgv), genGroup.get(destgv));
				}
			}
			for (GenVec destgv : this.getSortedAllGenVec()) {
				if (genGroup.containsKey(srcgv) && immGroup.containsKey(destgv)) {
					writeGen(pw, genGroup.get(srcgv), immGroup.get(destgv));
				}
				if (genGroup.containsKey(srcgv) && genGroup.containsKey(destgv)) {
					writeGen(pw, genGroup.get(srcgv), genGroup.get(destgv));
				}
			}
		}
	}
}
