package jspetrinet.analysis;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jspetrinet.JSPetriNet;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingMatrix;
import jspetrinet.marking.MarkingProcess;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class MRGPAnalysis {

	private PrintWriter pw;
	private MarkingMatrix mat;
	private MarkingProcess mp;
	private Net net;
	
	private Map<GenVec,MarkGroup> immGroup;
	MarkGroup expGroup;
	private Map<GenVec,MarkGroup> genGroup;
	
	private String expMatNameI;
	private String expMatNameG;
	
	private final Map<MarkGroup,String> groupLabel;
	private final Map<GroupPair,String> matrixName;
	
	public MRGPAnalysis(MarkingMatrix mat) {
		this.mat = mat;
		mp = mat.getMarkingProcess();
		net = mp.getNet();
		groupLabel = new HashMap<MarkGroup,String>();
		matrixName = new HashMap<GroupPair,String>();
		immGroup = mp.getImmGroup();
		genGroup = mp.getGenGroup();
		expGroup = mp.getExpGroup();
	}

	public void writeMarkSet(PrintWriter pw) {
		pw.println("IMM");
		for (GenVec gv: immGroup.keySet()) {
			MarkGroup mg = immGroup.get(gv);
			String glabel = JSPetriNet.genvecToString(net, gv);
			groupLabel.put(mg, glabel);
			pw.println(mat.getImmMatrixLabel().get(gv) + " " + glabel);
			List< List<Object> > s = mat.getMakingSet(mg);
			for (List<Object> e: s) {
				Mark m = (Mark) e.get(1);
				pw.println(e.get(0) + " : " + JSPetriNet.markToString(net, m));
			}
		}
		pw.println("GEN");
		for (GenVec gv: genGroup.keySet()) {
			MarkGroup mg = genGroup.get(gv);
			String glabel = JSPetriNet.genvecToString(net, gv);
			groupLabel.put(mg, glabel);
			pw.println(mat.getGenMatrixLabel().get(gv) + " " + glabel);
			List< List<Object> > s = mat.getMakingSet(mg);
			for (List<Object> e: s) {
				Mark m = (Mark) e.get(1);
				pw.println(e.get(0) + " : " + JSPetriNet.markToString(net, m));
			}
		}
	}
	
	private void defineMatrix(String matrixName, MarkGroup src, MarkGroup dest) {
		String label1 = groupLabel.get(src);
		String label2 = groupLabel.get(dest);
		pw.println("# " + matrixName + " " + label1 + " to " + label2);
		pw.println(matrixName + " <- Matrix(0," + src.size() + "," + dest.size() + ")");
	}

	private void putElement(String matrixName, Object i, Object j, Object v) {
		pw.println(matrixName + "[" + i + "," + j + "] <- " + v);
	}

	private void writeImmToImm(Set<GenVec> srcgv, Set<GenVec> destgv) {
		for (GenVec gv: srcgv) {
			MarkGroup src = immGroup.get(gv);
			for (GenVec gv2: destgv) {
				MarkGroup dest = immGroup.get(gv2);
				if (dest.size() == 0) {
					continue;
				}
				List< List<Object> > s = mat.getMatrixI(net, src, dest);
				if (s.size() == 0) {
					continue;
				}
				String matname = mat.getImmMatrixLabel().get(gv) + mat.getImmMatrixLabel().get(gv2);
				this.defineMatrix(matname, src, dest);
				for (List<Object> e: s) {
					this.putElement(matname, e.get(0), e.get(1), e.get(2));
				}
				matrixName.put(new GroupPair(src, dest), matname);
			}
		}
	}
	
	private void writeImmToGen(Set<GenVec> srcgv, Set<GenVec> destgv) {
		for (GenVec gv: srcgv) {
			MarkGroup src = immGroup.get(gv);
			for (GenVec gv2: destgv) {
				MarkGroup dest = genGroup.get(gv2);
				if (dest.size() == 0) {
					continue;
				}
				List< List<Object> > s = mat.getMatrixI(net, src, dest);
				if (s.size() == 0) {
					continue;
				}
				String matname = mat.getImmMatrixLabel().get(gv) + mat.getGenMatrixLabel().get(gv2);
				this.defineMatrix(matname, src, dest);
				for (List<Object> e: s) {
					this.putElement(matname, e.get(0), e.get(1), e.get(2));
				}
				matrixName.put(new GroupPair(src, dest), matname);
			}
		}
	}

	private void writeGenToImm(Set<GenVec> srcgv, Set<GenVec> destgv) {
		for (GenVec gv: srcgv) {
			MarkGroup src = genGroup.get(gv);
			for (GenVec gv2: destgv) {
				MarkGroup dest = immGroup.get(gv2);
				if (dest.size() == 0) {
					continue;
				}
				Map<Trans,List<List<Object>>> elem = mat.getMatrixG(net, src, dest);
				List<List<Object>> s = elem.get(null);
				if (s.size() != 0) {
					String matname = mat.getGenMatrixLabel().get(gv) + mat.getImmMatrixLabel().get(gv2) + "E";
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
							String matname = mat.getGenMatrixLabel().get(gv) + mat.getImmMatrixLabel().get(gv2) + "P" + entry.getKey().getIndex();
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

	private void writeGenToGen(Set<GenVec> srcgv, Set<GenVec> destgv) {
		for (GenVec gv: srcgv) {
			MarkGroup src = genGroup.get(gv);
			for (GenVec gv2: destgv) {
				MarkGroup dest = genGroup.get(gv2);
				if (dest.size() == 0) {
					continue;
				}
				Map<Trans,List<List<Object>>> elem = mat.getMatrixG(net, src, dest);
				List<List<Object>> s = elem.get(null);
				if (s.size() != 0 || src == dest) {
					String matname = mat.getGenMatrixLabel().get(gv) + mat.getGenMatrixLabel().get(gv2) + "E";
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
							String matname = mat.getGenMatrixLabel().get(gv) + mat.getGenMatrixLabel().get(gv2) + "P" + entry.getKey().getIndex();
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
		writeImmToImm(immGroup.keySet(), immGroup.keySet());
		writeImmToGen(immGroup.keySet(), genGroup.keySet());
		writeGenToImm(genGroup.keySet(), immGroup.keySet());
		writeGenToGen(genGroup.keySet(), genGroup.keySet());
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
