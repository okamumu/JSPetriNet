package jspetrinet.analysis;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmatout.MATLABDoubleMatrix;
import jmatout.SparseMatrixCSC;
import jspetrinet.JSPetriNet;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.exception.JSPNExceptionType;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class MRGPMatrixMATLABWriter extends MarkingMatrix {

	private final Map<GroupPair,String> matrixName;
	
	public MRGPMatrixMATLABWriter(MarkingGraph mp) {
		super(mp, false);
		matrixName = new HashMap<GroupPair,String>();
	}

	public void writeMarkSet(PrintWriter pw) {
		Net net = this.getMarkingGraph().getNet();
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
	
	public void writeStateVec(DataOutputStream dos, PrintWriter pw, Mark imark) throws IOException {
		Net net = this.getMarkingGraph().getNet();
		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec gv : this.getSortedAllGenVec()) {
			if (immGroup.containsKey(gv)) {
				MarkGroup mg = immGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				String name = this.getGroupLabel(mg) + "init";
				pw.println("# " + name + " " + glabel);
				pw.println("# size " + mg.size() + " 1");
				List<List<Object>> s = this.getMakingSet(mg);
				double[] data = new double [mg.size()];
				for (List<Object> e: s) {
					int i = (int) e.get(0);
					Mark m = (Mark) e.get(1);
					double value;
					if (m == imark) {
						value = 1;
					} else {
						value = 0;
					}
					data[i] = value;
				}
				MATLABDoubleMatrix matlab = new MATLABDoubleMatrix(name, new int[] {1, mg.size()}, data);
				matlab.write(dos);
			}
			if (genGroup.containsKey(gv)) {
				MarkGroup mg = genGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				String name = this.getGroupLabel(mg) + "init";
				pw.println("# " + name + " " + glabel);
				pw.println("# size " + mg.size() + " 1");
				List<List<Object>> s = this.getMakingSet(mg);
				double[] data = new double [mg.size()];
				for (List<Object> e: s) {
					int i = (int) e.get(0);
					Mark m = (Mark) e.get(1);
					double value;
					if (m == imark) {
						value = 1;
					} else {
						value = 0;
					}
					data[i] = value;
				}
				MATLABDoubleMatrix matlab = new MATLABDoubleMatrix(name, new int[] {1, mg.size()}, data);
				matlab.write(dos);
			}
		}
	}

	public void writeStateRewardVec(DataOutputStream dos, PrintWriter pw, List<AST> reward) throws JSPNException, IOException {
		Net net = this.getMarkingGraph().getNet();
		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec gv : this.getSortedAllGenVec()) {
			if (genGroup.containsKey(gv)) {
				MarkGroup mg = genGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				String name = this.getGroupLabel(mg) + "rwd";
				pw.println("# " + name + " " + glabel);
				List<List<Object>> s = this.getMakingSet(mg);
				pw.println("# size " + mg.size() + " " + reward.size());
				int nrow = mg.size();
				double[] data = new double [nrow * reward.size()];
				for (List<Object> e: s) {
					int i = (int) e.get(0);
					Mark m = (Mark) e.get(1);
					net.setCurrentMark(m);
					int j = 0;
					for (AST a : reward) {
						try {
							Object obj = a.eval(net);
							if (obj instanceof Double) {
								data[i + j * nrow] = (Double) a.eval(net);
							} else if (obj instanceof Integer) {
								data[i + j * nrow] = (Integer) a.eval(net);								
							} else {
								throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not convert " + a.eval(net).toString() + " to Double at mark " + JSPetriNet.markToString(net, m));
							}
						} catch (JSPNException ex) {
							throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not eval " + a.eval(net).toString() + " at mark " + JSPetriNet.markToString(net, m));
						}
						j++;
					}
				}
				MATLABDoubleMatrix matlab = new MATLABDoubleMatrix(name, new int[] {nrow, reward.size()}, data);
				matlab.write(dos);
			}
		}
	}

	public void writeSumVec(DataOutputStream dos, PrintWriter pw) throws IOException, JSPNException {
		Net net = this.getMarkingGraph().getNet();
		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec gv : this.getSortedAllGenVec()) {
			if (immGroup.containsKey(gv)) {
				MarkGroup mg = immGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				String name = this.getGroupLabel(mg) + "sum";
				pw.println("# " + name + " " + glabel);
				List<List<Object>> s = this.getSumVecI(net, mg);
				pw.println("# size " + mg.size() + " 1");
				double[] data = new double [mg.size()];
				for (List<Object> e: s) {
					int i = (int) e.get(0);
					Mark m = (Mark) e.get(1);
					AST d = (AST) e.get(2);
					net.setCurrentMark(m);
					try {
						Object obj = d.eval(net);
						if (obj instanceof Double) {
							data[i] = (Double) d.eval(net);
						} else if (obj instanceof Integer) {
							data[i] = (Integer) d.eval(net);								
						} else {
							throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not convert " + d.eval(net).toString() + " to Double at IMM mark " + JSPetriNet.markToString(net, m));
						}
					} catch (JSPNException ex) {
						throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not eval " + d.eval(net).toString() + " at IMM mark " + JSPetriNet.markToString(net, m));
					}
				}
				MATLABDoubleMatrix matlab = new MATLABDoubleMatrix(name, new int[] {1, mg.size()}, data);
				matlab.write(dos);
			}
			if (genGroup.containsKey(gv)) {
				MarkGroup mg = genGroup.get(gv);
				String glabel = JSPetriNet.genvecToString(net, gv);
				Map<Trans,List<List<Object>>> d0 = this.getSumVecG(net, mg);
				{
					List<List<Object>> s = d0.get(null);
					String name = this.getGroupLabel(mg) + "Esum";
					pw.println("# " + name + " " + glabel);
					pw.println("# size " + mg.size() + " 1");
					double[] data = new double [mg.size()];
					for (List<Object> e: s) {
						int i = (int) e.get(0);
						Mark m = (Mark) e.get(1);
						AST d = (AST) e.get(2);
						net.setCurrentMark(m);
						try {
							Object obj = d.eval(net);
							if (obj instanceof Double) {
								data[i] = (Double) d.eval(net);
							} else if (obj instanceof Integer) {
								data[i] = (Integer) d.eval(net);								
							} else {
								throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not convert " + d.eval(net).toString() + " to Double at EXP mark " + JSPetriNet.markToString(net, m));
							}
						} catch (JSPNException ex) {
							throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not eval " + d.eval(net).toString() + " at EXP mark " + JSPetriNet.markToString(net, m));
						}
					}					
					MATLABDoubleMatrix matlab = new MATLABDoubleMatrix(name, new int[] {1, mg.size()}, data);
					matlab.write(dos);
				}				
				for (Map.Entry<Trans, List<List<Object>>> entry : d0.entrySet()) {
					if (entry.getKey() != null) {
						List<List<Object>> s = entry.getValue();
						String name = this.getGroupLabel(mg) + "P" + entry.getKey().getIndex() + "sum";
						pw.println("# " + name + " " + glabel);
						pw.println("# size " + mg.size() + " 1");
						double[] data = new double [mg.size()];
						for (List<Object> e: s) {
							int i = (int) e.get(0);
							Mark m = (Mark) e.get(1);
							AST d = (AST) e.get(2);
							net.setCurrentMark(m);
							try {
								Object obj = d.eval(net);
								if (obj instanceof Double) {
									data[i] = (Double) d.eval(net);
								} else if (obj instanceof Integer) {
									data[i] = (Integer) d.eval(net);								
								} else {
									throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not convert " + d.eval(net).toString() + " to Double at GEN mark " + JSPetriNet.markToString(net, m));
								}
							} catch (JSPNException ex) {
								throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not eval " + d.eval(net).toString() + " at GEN mark " + JSPetriNet.markToString(net, m));
							}
						}
						MATLABDoubleMatrix matlab = new MATLABDoubleMatrix(name, new int[] {1, mg.size()}, data);
						matlab.write(dos);
					}
				}
			}
		}
	}

	private SparseMatrixCSC defineMatrix(PrintWriter pw, String matrixName, MarkGroup src, MarkGroup dest, List<List<Object>> s) {
		s.sort(new CSCComparator());
		pw.println("# " + matrixName + " " + this.getGroupLabel(src) + " to " + this.getGroupLabel(dest));
		pw.println("# size " + src.size() + " " + dest.size() + " " + s.size());
		return new SparseMatrixCSC(matrixName, src.size(), dest.size(), s.size());
	}

	private void putElement(SparseMatrixCSC m, Object iv, Object jv, Object vv) throws JSPNException {
		int i = (Integer) iv;
		int j = (Integer) jv;
		double v;
		if (vv instanceof Double) {
			v = (Double) vv;
		} else if (vv instanceof Integer) {
			v = (Integer) vv;
		} else {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not convert " + vv.toString() + " to Double in (" + i + "," + j + ") of " + m.getName());
		}
		m.set(i, j, v);
	}

	private void writeImm(DataOutputStream dos, PrintWriter pw, MarkGroup src, MarkGroup dest) throws JSPNException, IOException {
		Net net = this.getMarkingGraph().getNet();
		if (dest.size() == 0) {
			return;
		}
		List< List<Object> > s = this.getMatrixI(net, src, dest);
		if (s.size() == 0) {
			return;
		}
		String matname = this.getGroupLabel(src) + this.getGroupLabel(dest);
		SparseMatrixCSC mm = this.defineMatrix(pw, matname, src, dest, s);
		for (List<Object> e: s) {
			this.putElement(mm, e.get(0), e.get(1), e.get(2));
		}
		matrixName.put(new GroupPair(src, dest), matname);
		mm.write(dos);
	}

	private void writeGen(DataOutputStream dos, PrintWriter pw, MarkGroup src, MarkGroup dest) throws JSPNException, IOException {
		Net net = this.getMarkingGraph().getNet();
		if (dest.size() == 0) {
			return;
		}
		Map<Trans,List<List<Object>>> elem = this.getMatrixG(net, src, dest);
		List<List<Object>> s = elem.get(null); // get EXP
		if (s.size() != 0 || src == dest) {
			String matname = this.getGroupLabel(src) + this.getGroupLabel(dest) + "E";
			SparseMatrixCSC mm = this.defineMatrix(pw, matname, src, dest, s);
			for (List<Object> e: s) {
				this.putElement(mm, e.get(0), e.get(1), e.get(2));
			}
			matrixName.put(new GroupPair(src, dest), matname);
			mm.write(dos);
		}
		for (Map.Entry<Trans, List<List<Object>>> entry: elem.entrySet()) {
			if (entry.getKey() != null) {
				s = entry.getValue();
				if (s.size() != 0) {
					String matname = this.getGroupLabel(src) + this.getGroupLabel(dest)
						+ "P" + entry.getKey().getIndex();
					SparseMatrixCSC mm = this.defineMatrix(pw, matname, src, dest, s);
					for (List<Object> e: s) {
						this.putElement(mm, e.get(0), e.get(1), e.get(2));
					}
					matrixName.put(new GroupPair(src, dest, entry.getKey()), matname);
					mm.write(dos);
				}
			}
		}
	}

	public void writeMatrix(DataOutputStream dos, PrintWriter pw) throws JSPNException, IOException {
		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec srcgv : this.getSortedAllGenVec()) {
			for (GenVec destgv : this.getSortedAllGenVec()) {
				if (immGroup.containsKey(srcgv) && immGroup.containsKey(destgv)) {
					writeImm(dos, pw, immGroup.get(srcgv), immGroup.get(destgv));
				}
				if (immGroup.containsKey(srcgv) && genGroup.containsKey(destgv)) {
					writeImm(dos, pw, immGroup.get(srcgv), genGroup.get(destgv));
				}
			}
			for (GenVec destgv : this.getSortedAllGenVec()) {
				if (genGroup.containsKey(srcgv) && immGroup.containsKey(destgv)) {
					writeGen(dos, pw, genGroup.get(srcgv), immGroup.get(destgv));
				}
				if (genGroup.containsKey(srcgv) && genGroup.containsKey(destgv)) {
					writeGen(dos, pw, genGroup.get(srcgv), genGroup.get(destgv));
				}
			}
		}
	}
}
