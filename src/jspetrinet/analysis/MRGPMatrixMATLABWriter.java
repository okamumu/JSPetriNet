package jspetrinet.analysis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jmatio.io.MatFileIncrementalWriter;
import com.jmatio.types.MLDouble;
import com.jmatio.types.MLSparse;

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
	
	public void writeStateVec(MatFileIncrementalWriter mwriter, PrintWriter pw, Mark imark) throws IOException {
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
				mwriter.write(new MLDouble(name, data, 1));
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
				mwriter.write(new MLDouble(name, data, 1));
			}
		}
	}

	public void writeStateRewardVec(MatFileIncrementalWriter mwriter, PrintWriter pw, List<AST> reward) throws JSPNException, IOException {
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
				double[][] data = new double [mg.size()][reward.size()];
				for (List<Object> e: s) {
					int i = (int) e.get(0);
					Mark m = (Mark) e.get(1);
					net.setCurrentMark(m);
					int j = 0;
					for (AST a : reward) {
						try {
							Object obj = a.eval(net);
							if (obj instanceof Double) {
								data[i][j] = (Double) a.eval(net);
							} else if (obj instanceof Integer) {
								data[i][j] = (Integer) a.eval(net);								
							} else {
								throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not convert " + a.eval(net).toString() + " to Double at mark " + JSPetriNet.markToString(net, m));
							}
						} catch (JSPNException ex) {
							throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Error: Could not eval " + a.eval(net).toString() + " at mark " + JSPetriNet.markToString(net, m));
						}
						j++;
					}
				}
				mwriter.write(new MLDouble(name, data));
			}
		}
	}

	public void writeSumVec(MatFileIncrementalWriter mwriter, PrintWriter pw) throws IOException, JSPNException {
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
				mwriter.write(new MLDouble(name, data, 1));
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
					mwriter.write(new MLDouble(name, data, 1));
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
						mwriter.write(new MLDouble(name, data, 1));
					}
				}
			}
		}
	}

	private MLSparse defineMatrix(PrintWriter pw, String matrixName, MarkGroup src, MarkGroup dest, List<List<Object>> s) {
		pw.println("# " + matrixName + " " + this.getGroupLabel(src) + " to " + this.getGroupLabel(dest));
		pw.println("# size " + src.size() + " " + dest.size() + " " + s.size());
		return new MLSparse(matrixName, new int[] {src.size(), dest.size()}, 0, s.size());
	}

	private void putElement(MLSparse m, Object iv, Object jv, Object vv) throws JSPNException {
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
		m.setReal(v, i, j);
	}

	private void writeImm(MatFileIncrementalWriter mwriter, PrintWriter pw, MarkGroup src, MarkGroup dest) throws JSPNException, IOException {
		Net net = this.getMarkingGraph().getNet();
		if (dest.size() == 0) {
			return;
		}
		List< List<Object> > s = this.getMatrixI(net, src, dest);
		if (s.size() == 0) {
			return;
		}
		String matname = this.getGroupLabel(src) + this.getGroupLabel(dest);
		MLSparse mm = this.defineMatrix(pw, matname, src, dest, s);
		for (List<Object> e: s) {
			this.putElement(mm, e.get(0), e.get(1), e.get(2));
		}
		matrixName.put(new GroupPair(src, dest), matname);
		mwriter.write(mm);
	}

	private void writeGen(MatFileIncrementalWriter mwriter, PrintWriter pw, MarkGroup src, MarkGroup dest) throws JSPNException, IOException {
		Net net = this.getMarkingGraph().getNet();
		if (dest.size() == 0) {
			return;
		}
		Map<Trans,List<List<Object>>> elem = this.getMatrixG(net, src, dest);
		List<List<Object>> s = elem.get(null); // get EXP
		if (s.size() != 0 || src == dest) {
			String matname = this.getGroupLabel(src) + this.getGroupLabel(dest) + "E";
			MLSparse mm = this.defineMatrix(pw, matname, src, dest, s);
			for (List<Object> e: s) {
				this.putElement(mm, e.get(0), e.get(1), e.get(2));
			}
			matrixName.put(new GroupPair(src, dest), matname);
			mwriter.write(mm);
		}
		for (Map.Entry<Trans, List<List<Object>>> entry: elem.entrySet()) {
			if (entry.getKey() != null) {
				s = entry.getValue();
				if (s.size() != 0) {
					String matname = this.getGroupLabel(src) + this.getGroupLabel(dest)
						+ "P" + entry.getKey().getIndex();
					MLSparse mm = this.defineMatrix(pw, matname, src, dest, s);
					for (List<Object> e: s) {
						this.putElement(mm, e.get(0), e.get(1), e.get(2));
					}
					matrixName.put(new GroupPair(src, dest, entry.getKey()), matname);
					mwriter.write(mm);
				}
			}
		}
	}

	public void writeMatrix(MatFileIncrementalWriter mwriter, PrintWriter pw) throws JSPNException, IOException {
		Map<GenVec,MarkGroup> immGroup = this.getImmGroup();
		Map<GenVec,MarkGroup> genGroup = this.getGenGroup();
		for (GenVec srcgv : this.getSortedAllGenVec()) {
			for (GenVec destgv : this.getSortedAllGenVec()) {
				if (immGroup.containsKey(srcgv) && immGroup.containsKey(destgv)) {
					writeImm(mwriter, pw, immGroup.get(srcgv), immGroup.get(destgv));
				}
				if (immGroup.containsKey(srcgv) && genGroup.containsKey(destgv)) {
					writeImm(mwriter, pw, immGroup.get(srcgv), genGroup.get(destgv));
				}
			}
			for (GenVec destgv : this.getSortedAllGenVec()) {
				if (genGroup.containsKey(srcgv) && immGroup.containsKey(destgv)) {
					writeGen(mwriter, pw, genGroup.get(srcgv), immGroup.get(destgv));
				}
				if (genGroup.containsKey(srcgv) && genGroup.containsKey(destgv)) {
					writeGen(mwriter, pw, genGroup.get(srcgv), genGroup.get(destgv));
				}
			}
		}
	}
}
