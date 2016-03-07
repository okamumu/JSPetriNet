import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

import jspetrinet.*;
import jspetrinet.exception.*;
import jspetrinet.marking.*;
import jspetrinet.petri.*;

public class testMain {
	
	public static void prog10(Net global) throws ASTException {
		JSPetriNet.load(global, "cloud.spn");
		try {
			PrintStream out = new PrintStream("petri.dot");
			out.println("digraph { layout=dot; overlap=false; splines=true; node [fontsize=10];");
			global.getPlace("Ph").accept(new jspetrinet.petri.VizPrint(out));
			out.println("}");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		global.setIndex();

		Map<String,Integer> initmark = new HashMap<String,Integer>();
		initmark.put("Ph", 5);
		initmark.put("Pw", 5);
		initmark.put("Pc", 5);
		Mark m1 = JSPetriNet.mark(global, initmark);
//		int[] vec = global.toMarkVec(initmark);
//		Mark m1 = new Mark(global.markToString(vec), vec);

		MarkingProcess mp = JSPetriNet.marking(global, m1);
		
		String param = "Thr.rate = 100.0;"
				+ "lambda_h = 5.0;"
				+ "lambda_w = 8.0;"
				+ "lambda_c = 7.0;"
				+ "Tchm.rate = 10.0;"
				+ "Twhm.rate = 10.0;"
				+ "Twr.rate = 10.0;"
				+ "Thcm.rate = 10.0;"
				+ "Thwm.rate = 10.0;"
				+ "Tcwm.rate = 100000.0;"
				+ "Tcr.rate = 10.0;"
				+ "Twcm.rate = 10.0;";
		JSPetriNet.eval(global, param);
		
//		CooMatrixWithMarkingProcess cmat = new CooMatrixWithMarkingProcess(mp, global, true);
//		mp.createIndex(true);
//		for (int i=mp.groupIndex(mp.getImmGroup());
//				i<=mp.groupIndex(mp.getImmGroup())+mp.getImmGroup().size(); i++) {
//			System.out.println(i + ": " + mp.mark(i));						
		}
//		for (int i=1; i<=mp.count(); i++) {
//			System.out.println(i + " : " + cmat.mark(i));			
//		}
//		try {
//			PrintStream out = new PrintStream("/Users/okamu/Desktop/mat.txt");
//			cmat.write(out);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		try {
//			PrintStream out = new PrintStream("/Users/okamu/Desktop/result.txt");
//
//			int x = 0;
//			for (Mark m : mp.getMarkSet()) {
//				out.println(x + " : " + m.getLabel());
//				m.setLabel(Integer.toString(x));
//				x++;
//			}
//
//			// IMM matrices
//			out.println("Imm group (inner): " + mp.getImmGroup());
//			for (Mark m : mp.getImmGroup().getMarkSet()) {
//				global.setCurrentMark(m);
//				for (Arc arc : m.getOutArc()) {
//					MarkingArc marc = (MarkingArc) arc;
//					Mark dest = (Mark) arc.getDest();
//					ImmTrans itrans = (ImmTrans) marc.getTrans();
//					if (dest.getMarkGroup() == mp.getImmGroup()) {
//						out.println(m.getLabel() + " " + dest.getLabel()
//								+ " " + itrans.getWeight(global)
//								+ "  # " + itrans.getLabel());
//					}
//				}
//			}
//			out.println("Imm group (IMM trans to out): " + mp.getImmGroup());
//			for (Mark m : mp.getImmGroup().getMarkSet()) {
//				global.setCurrentMark(m);
//				for (Arc arc : m.getOutArc()) {
//					MarkingArc marc = (MarkingArc) arc;
//					Mark dest = (Mark) arc.getDest();
//					ImmTrans tr = (ImmTrans) marc.getTrans();
//					if (dest.getMarkGroup() != mp.getImmGroup()) {
//						out.println(m.getLabel() + " " + dest.getLabel()
//								+ " " + tr.getWeight(global)
//								+ "  # " + tr.getLabel()
//								+ " to " + dest.getMarkGroup());
//					}
//				}
//			}
//			out.println("");
//
//			// Exp/Gen Matrices
//			for (GenVec egv : mp.getGenGroup().keySet()) {
//				MarkGroup mg = mp.getGenGroup().get(egv);
//				// inn exp
//				out.println("Exp/Gen group (innter EXP trans) " + egv.toString() + " " + mg);
//				for (Mark m : mg.getMarkSet()) {
//					global.setCurrentMark(m);
//					for (Arc arc : m.getOutArc()) {
//						MarkingArc marc = (MarkingArc) arc;
//						Mark dest = (Mark) arc.getDest();
//						if (marc.getTrans() instanceof ExpTrans) {
//							ExpTrans tr = (ExpTrans) marc.getTrans();
//							if (dest.getMarkGroup() == mg) {
//								out.println(m.getLabel() + " " + dest.getLabel()
//										+ " " + tr.getRate(global)
//										+ "  # " + tr.getLabel());
//							}
//						}
//					}
//				}
//				// out exp
//				out.println("Exp/Gen group (EXP trans to out) " + egv.toString() + " " + mg);
//				for (Mark m : mg.getMarkSet()) {
//					global.setCurrentMark(m);
//					for (Arc arc : m.getOutArc()) {
//						MarkingArc marc = (MarkingArc) arc;
//						Mark dest = (Mark) arc.getDest();
//						if (marc.getTrans() instanceof ExpTrans) {
//							ExpTrans tr = (ExpTrans) marc.getTrans();
//							if (dest.getMarkGroup() != mg) {
//								out.println(m.getLabel() + " " + dest.getLabel()
//										+ " " + tr.getRate(global)
//										+ "  # " + tr.getLabel()
//										+ " to " + dest.getMarkGroup());
//							}
//						}
//					}
//				}
//				out.println("");
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// try {
		// PrintStream out = new PrintStream("/Users/okamu/Desktop/graph.dot");
		// out.println("digraph { layout=dot; overlap=false; splines=true;"); //
		// node [fontsize=5];");
		// m2.accept(new graph.VizPrint(out));
		// out.println("}");
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		//
		
//		BddNet bglobal = (BddNet) global;

//		Bdd<Boolean> bdd = new Bdd<Boolean>();
//		BddSet bs;
//
//		System.out.println("start");
//		bs = BddPetriAnalysis.createGuradSet(global, bdd, (ASTree) global.get("g1"));
//		System.out.println("done");
//		System.out.println(bs.count());
//
//		System.out.println("start");
//		bs = BddPetriAnalysis.createInTransSet(global, bdd, (Trans) global.get("Tchm"));
//		System.out.println("done");
//		System.out.println(bs.count());
//
//		try {
//			PrintStream out = new PrintStream("/Users/okamu/Desktop/bdd.dot");
//			out.println("digraph { ");
//			bs.getTop().accept(new bdd.VizPrint<Boolean>(out));
//			out.println("}");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		BddSet novisited = new BddSet(bdd, false);
//		novisited.add(BddPetriSet.setInitMark(m1, bglobal), bglobal.getTotalBit(), 0, 2);
//
//		try {
//			PrintStream out = new PrintStream("/Users/okamu/Desktop/bdd2.dot");
//			out.println("digraph { ");
//			novisited.getTop().accept(new bdd.VizPrint<Boolean>(out));
//			out.println("}");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		bs.intersection(novisited);
//		try {
//			PrintStream out = new PrintStream("/Users/okamu/Desktop/bdd3.dot");
//			out.println("digraph { ");
//			bs.getTop().accept(new bdd.VizPrint<Boolean>(out));
//			out.println("}");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		BddPetriSet bps = new BddPetriSet();
//		long start2 = System.nanoTime();
//		BddSet bs2 = bps.create(m1, bglobal);
//		System.out.println((System.nanoTime() - start2) / 1000000);
//
//		System.out.println(bs2.countEven());
//	}

//	public static void prog11(Net global) throws ASTException {
//		JSPetriNet.load(global, "/Users/okamu/Desktop/policy1.txt");
//		try {
//			PrintStream out = new PrintStream("/Users/okamu/Desktop/petri.dot");
//			out.println("digraph { layout=dot; overlap=false; splines=true; node [fontsize=10];");
//			global.getPlace("Pup").accept(new petri.VizPrint(out));
//			out.println("}");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		global.setIndex();
//
//		int[] a = new int[global.getNumOfPlace()];
//		a[global.getPlace("Pup").getIndex()] = 1;
//		a[global.getPlace("Pclock").getIndex()] = 1;
//		Mark m1 = new Mark(a);
//
//		MarkingProcess mp = new MarkingProcess();
//		System.out.print("Create marking...");
//		long start = System.nanoTime();
//		Mark m2 = mp.create(m1, global);
//		System.out.println("done");
//		System.out.println("computation time    : " + (System.nanoTime() - start) / 1000000000.0 + " (sec)");
//
//		int total = mp.count();
//		int immtotal = mp.getImmGroup().size();
//		System.out.println("# of total states   : " + total);
//		System.out.println("# of IMM states     : " + immtotal);
//		System.out.println("# of EXP/GEN states : " + (total - immtotal));
//		
////		String param = "Thr.rate = 100.0;"
////				+ "lambda_h = 5.0;"
////				+ "lambda_w = 8.0;"
////				+ "lambda_c = 7.0;"
////				+ "Tchm.rate = 10.0;"
////				+ "Twhm.rate = 10.0;"
////				+ "Twr.rate = 10.0;"
////				+ "Thcm.rate = 10.0;"
////				+ "Thwm.rate = 10.0;"
////				+ "Tcwm.rate = 100000.0;"
////				+ "Tcr.rate = 10.0;"
////				+ "Twcm.rate = 10.0;";
////		JSPetriNet.eval(global, param);
////		
////		CooMatrixWithMarkingProcess cmat = new CooMatrixWithMarkingProcess(mp, global, true);
////		cmat.printGroupIndex();
////		try {
////			PrintStream out = new PrintStream("/Users/okamu/Desktop/mat.txt");
////			cmat.write(out);
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////
////		try {
////			PrintStream out = new PrintStream("/Users/okamu/Desktop/result.txt");
////
////			int x = 0;
////			for (Mark m : mp.getMarkSet()) {
////				out.println(x + " : " + m.getLabel());
////				m.setLabel(Integer.toString(x));
////				x++;
////			}
////
////			// IMM matrices
////			out.println("Imm group (inner): " + mp.getImmGroup());
////			for (Mark m : mp.getImmGroup().getMarkSet()) {
////				global.setCurrentMark(m);
////				for (Arc arc : m.getOutArc()) {
////					MarkingArc marc = (MarkingArc) arc;
////					Mark dest = (Mark) arc.getDest();
////					ImmTrans itrans = (ImmTrans) marc.getTrans();
////					if (dest.getMarkGroup() == mp.getImmGroup()) {
////						out.println(m.getLabel() + " " + dest.getLabel()
////								+ " " + itrans.getWeight(global)
////								+ "  # " + itrans.getLabel());
////					}
////				}
////			}
////			out.println("Imm group (IMM trans to out): " + mp.getImmGroup());
////			for (Mark m : mp.getImmGroup().getMarkSet()) {
////				global.setCurrentMark(m);
////				for (Arc arc : m.getOutArc()) {
////					MarkingArc marc = (MarkingArc) arc;
////					Mark dest = (Mark) arc.getDest();
////					ImmTrans tr = (ImmTrans) marc.getTrans();
////					if (dest.getMarkGroup() != mp.getImmGroup()) {
////						out.println(m.getLabel() + " " + dest.getLabel()
////								+ " " + tr.getWeight(global)
////								+ "  # " + tr.getLabel()
////								+ " to " + dest.getMarkGroup());
////					}
////				}
////			}
////			out.println("");
////
////			// Exp/Gen Matrices
////			for (GenVec egv : mp.getGenGroup().keySet()) {
////				MarkGroup mg = mp.getGenGroup().get(egv);
////				// inn exp
////				out.println("Exp/Gen group (innter EXP trans) " + egv.toString() + " " + mg);
////				for (Mark m : mg.getMarkSet()) {
////					global.setCurrentMark(m);
////					for (Arc arc : m.getOutArc()) {
////						MarkingArc marc = (MarkingArc) arc;
////						Mark dest = (Mark) arc.getDest();
////						if (marc.getTrans() instanceof ExpTrans) {
////							ExpTrans tr = (ExpTrans) marc.getTrans();
////							if (dest.getMarkGroup() == mg) {
////								out.println(m.getLabel() + " " + dest.getLabel()
////										+ " " + tr.getRate(global)
////										+ "  # " + tr.getLabel());
////							}
////						}
////					}
////				}
////				// out exp
////				out.println("Exp/Gen group (EXP trans to out) " + egv.toString() + " " + mg);
////				for (Mark m : mg.getMarkSet()) {
////					global.setCurrentMark(m);
////					for (Arc arc : m.getOutArc()) {
////						MarkingArc marc = (MarkingArc) arc;
////						Mark dest = (Mark) arc.getDest();
////						if (marc.getTrans() instanceof ExpTrans) {
////							ExpTrans tr = (ExpTrans) marc.getTrans();
////							if (dest.getMarkGroup() != mg) {
////								out.println(m.getLabel() + " " + dest.getLabel()
////										+ " " + tr.getRate(global)
////										+ "  # " + tr.getLabel()
////										+ " to " + dest.getMarkGroup());
////							}
////						}
////					}
////				}
////				out.println("");
////			}
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//
//		// try {
//		// PrintStream out = new PrintStream("/Users/okamu/Desktop/graph.dot");
//		// out.println("digraph { layout=dot; overlap=false; splines=true;"); //
//		// node [fontsize=5];");
//		// m2.accept(new graph.VizPrint(out));
//		// out.println("}");
//		// } catch (FileNotFoundException e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//
//		//
//		
////		BddNet bglobal = (BddNet) global;
//
////		Bdd<Boolean> bdd = new Bdd<Boolean>();
////		BddSet bs;
////
////		System.out.println("start");
////		bs = BddPetriAnalysis.createGuradSet(global, bdd, (ASTree) global.get("g1"));
////		System.out.println("done");
////		System.out.println(bs.count());
////
////		System.out.println("start");
////		bs = BddPetriAnalysis.createInTransSet(global, bdd, (Trans) global.get("Tchm"));
////		System.out.println("done");
////		System.out.println(bs.count());
////
////		try {
////			PrintStream out = new PrintStream("/Users/okamu/Desktop/bdd.dot");
////			out.println("digraph { ");
////			bs.getTop().accept(new bdd.VizPrint<Boolean>(out));
////			out.println("}");
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////
////		BddSet novisited = new BddSet(bdd, false);
////		novisited.add(BddPetriSet.setInitMark(m1, bglobal), bglobal.getTotalBit(), 0, 2);
////
////		try {
////			PrintStream out = new PrintStream("/Users/okamu/Desktop/bdd2.dot");
////			out.println("digraph { ");
////			novisited.getTop().accept(new bdd.VizPrint<Boolean>(out));
////			out.println("}");
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////
////		bs.intersection(novisited);
////		try {
////			PrintStream out = new PrintStream("/Users/okamu/Desktop/bdd3.dot");
////			out.println("digraph { ");
////			bs.getTop().accept(new bdd.VizPrint<Boolean>(out));
////			out.println("}");
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//
////		BddPetriSet bps = new BddPetriSet();
////		long start2 = System.nanoTime();
////		BddSet bs2 = bps.create(m1, bglobal);
////		System.out.println((System.nanoTime() - start2) / 1000000);
////
////		System.out.println(bs2.countEven());
//	}
	
	public static void prog11(Net global) throws ASTException {
		JSPetriNet.load(global, "raid6.spn");
		JSPetriNet.writeDotfile(global, "petri1.dot", "Pn");
		JSPetriNet.writeDotfile(global, "petri2.dot", "Pnormal");

		global.setIndex();

		Map<String,Integer> initmark = new HashMap<String,Integer>();
		initmark.put("Pn", 10);
		initmark.put("Pnormal", 1);
		Mark m1 = JSPetriNet.mark(global, initmark);
		MarkingProcess mp = JSPetriNet.marking(global, m1);
	}
		
	public static void main(String[] args) throws ASTException {
		// prog0();
		// prog1();
		// Net net = prog5();
		// prog4(net);
		// prog3(net);
		// prog4(new Net(null, "global"));
		// prog40(new Net(null));
//		prog10(new Net(null, "global"));
		prog11(new Net(null, "global"));
//		prog0();
		// prog20();
	}

}
