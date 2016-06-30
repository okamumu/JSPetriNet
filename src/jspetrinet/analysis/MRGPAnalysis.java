package jspetrinet.analysis;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jspetrinet.JSPetriNet;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class MRGPAnalysis {

	private PrintWriter pw;
	private MarkingMatrix mat;
	private MarkingGraph mp;
	private Net net;
	
	private Map<GenVec,MarkGroup> immGroup;
	MarkGroup expGroup;
	private Map<GenVec,MarkGroup> genGroup;
	
//	private List<MarkGroup> immGroup;
//	MarkGroup expGroup;
//	private List<MarkGroup> genGroup;

	private String expMatNameI;
	private String expMatNameG;
	
	private final Map<GroupPair,String> matrixName;
	
	public MRGPAnalysis(MarkingMatrix mat) {
		this.mat = mat;
		mp = mat.getMarkingProcess();
		net = mp.getNet();
		matrixName = new HashMap<GroupPair,String>();
		immGroup = mp.getImmGroup();
		genGroup = mp.getGenGroup();
		expGroup = mp.getExpGroup();
	}

	public void writeMarkSet(PrintWriter pw) {
		for (GenVec gv : mat.getSortedAllGenVec()) {
			if (immGroup.containsKey(gv)) {
				MarkGroup mg = immGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				pw.println(mat.getGroupLabel(mg) + " " + glabel);
				List<List<Object>> s = mat.getMakingSet(mg);
				for (List<Object> e: s) {
					Mark m = (Mark) e.get(1);
					pw.println(e.get(0) + " : " + JSPetriNet.markToString(net, m));
				}
			}
			if (genGroup.containsKey(gv)) {
				MarkGroup mg = genGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				pw.println(mat.getGroupLabel(mg) + " " + glabel);
				List<List<Object>> s = mat.getMakingSet(mg);
				for (List<Object> e: s) {
					Mark m = (Mark) e.get(1);
					pw.println(e.get(0) + " : " + JSPetriNet.markToString(net, m));
				}
			}
		}
	}
	
	private void defineMatrix(String matrixName, MarkGroup src, MarkGroup dest) {
		pw.println("# " + matrixName + " " + mat.getGroupLabel(src) + " to " + mat.getGroupLabel(dest));
		pw.println(matrixName + " <- Matrix(0," + src.size() + "," + dest.size() + ")");
	}

	private void putElement(String matrixName, Object i, Object j, Object v) {
		pw.println(matrixName + "[" + i + "," + j + "] <- " + v);
	}

	private void writeImm(MarkGroup src, MarkGroup dest) {
		if (dest.size() == 0) {
			return;
		}
		List< List<Object> > s = mat.getMatrixI(net, src, dest);
		if (s.size() == 0) {
			return;
		}
		String matname = mat.getGroupLabel(src) + mat.getGroupLabel(dest);
		this.defineMatrix(matname, src, dest);
		for (List<Object> e: s) {
			this.putElement(matname, e.get(0), e.get(1), e.get(2));
		}
		matrixName.put(new GroupPair(src, dest), matname);
	}

	private void writeGen(MarkGroup src, MarkGroup dest) {
		if (dest.size() == 0) {
			return;
		}
		Map<Trans,List<List<Object>>> elem = mat.getMatrixG(net, src, dest);
		List<List<Object>> s = elem.get(null); // get EXP
		if (s.size() != 0 || src == dest) {
			String matname = mat.getGroupLabel(src) + mat.getGroupLabel(dest) + "E";
			this.defineMatrix(matname, src, dest);
			for (List<Object> e: s) {
				this.putElement(matname, e.get(0), e.get(1), e.get(2));
			}
			matrixName.put(new GroupPair(src, dest), matname);
		}
		for (Map.Entry<Trans, List<List<Object>>> entry: elem.entrySet()) {
			if (entry.getKey() != null) {
				s = entry.getValue();
				if (s.size() != 0) {
					String matname = mat.getGroupLabel(src) + mat.getGroupLabel(dest)
						+ "P" + entry.getKey().getIndex();
					this.defineMatrix(matname, src, dest);
					for (List<Object> e: s) {
						this.putElement(matname, e.get(0), e.get(1), e.get(2));
					}
					matrixName.put(new GroupPair(src, dest, entry.getKey()), matname);
				}
			}
		}
	}

	private void writeImm(Collection<MarkGroup> srcMarkGroupSet, Collection<MarkGroup> destMarkGroupSet) {
		for (MarkGroup src: srcMarkGroupSet) {
			for (MarkGroup dest: destMarkGroupSet) {
				if (dest.size() == 0) {
					continue;
				}
				List< List<Object> > s = mat.getMatrixI(net, src, dest);
				if (s.size() == 0) {
					continue;
				}
				String matname = mat.getGroupLabel(src) + mat.getGroupLabel(dest);
				this.defineMatrix(matname, src, dest);
				for (List<Object> e: s) {
					this.putElement(matname, e.get(0), e.get(1), e.get(2));
				}
				matrixName.put(new GroupPair(src, dest), matname);
			}
		}
	}
	
	private void writeGen(Collection<MarkGroup> srcMarkGroupSet, Collection<MarkGroup> destMarkGroupSet) {
		for (MarkGroup src: srcMarkGroupSet) {
			for (MarkGroup dest: destMarkGroupSet) {
				if (dest.size() == 0) {
					continue;
				}
				Map<Trans,List<List<Object>>> elem = mat.getMatrixG(net, src, dest);
				List<List<Object>> s = elem.get(null); // get EXP
				if (s.size() != 0 || src == dest) {
					String matname = mat.getGroupLabel(src) + mat.getGroupLabel(dest) + "E";
					this.defineMatrix(matname, src, dest);
					for (List<Object> e: s) {
						this.putElement(matname, e.get(0), e.get(1), e.get(2));
					}
					matrixName.put(new GroupPair(src, dest), matname);
				}
				for (Map.Entry<Trans, List<List<Object>>> entry: elem.entrySet()) {
					if (entry.getKey() != null) {
						s = entry.getValue();
						if (s.size() != 0) {
							String matname = mat.getGroupLabel(src) + mat.getGroupLabel(dest)
								+ "P" + entry.getKey().getIndex();
							this.defineMatrix(matname, src, dest);
							for (List<Object> e: s) {
								this.putElement(matname, e.get(0), e.get(1), e.get(2));
							}
							matrixName.put(new GroupPair(src, dest, entry.getKey()), matname);
						}
					}
				}
			}
		}
	}

	public void writeMatrix(PrintWriter pw) {
		this.pw = pw;
		for (GenVec srcgv : mat.getSortedAllGenVec()) {
			for (GenVec destgv : mat.getSortedAllGenVec()) {
				if (immGroup.containsKey(srcgv) && immGroup.containsKey(destgv)) {
					writeImm(immGroup.get(srcgv), immGroup.get(destgv));
				}
				if (immGroup.containsKey(srcgv) && genGroup.containsKey(destgv)) {
					writeImm(immGroup.get(srcgv), genGroup.get(destgv));
				}
			}
			for (GenVec destgv : mat.getSortedAllGenVec()) {
				if (genGroup.containsKey(srcgv) && immGroup.containsKey(destgv)) {
					writeGen(genGroup.get(srcgv), immGroup.get(destgv));
				}
				if (genGroup.containsKey(srcgv) && genGroup.containsKey(destgv)) {
					writeGen(genGroup.get(srcgv), genGroup.get(destgv));
				}
			}
		}
		this.pw = null;
	}
	
	public void writeVanishing(PrintWriter pw) {
		for (Map.Entry<GroupPair, String> entry : matrixName.entrySet()) {
			if (entry.getKey().getSrcMarkGroup() == entry.getKey().getDestMarkGroup()
					&& immGroup.containsValue(entry.getKey().getSrcMarkGroup())) {
				pw.println(entry.getValue() + "r <- solve(diag(1,dim(" + entry.getValue() + ")) - " + entry.getValue() + ")");
			}
		}
	}
}
