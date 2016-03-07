package jspetrinet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import jspetrinet.exception.ASTException;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingProcess;
import jspetrinet.parser.JSPetriNetParser;
import jspetrinet.parser.ParseException;
import jspetrinet.parser.TokenMgrError;
import jspetrinet.petri.Net;
import jspetrinet.petri.Place;
import jspetrinet.petri.Trans;

public class JSPetriNet {
	
	public static void load(Net global, String fn) {
		try {
			InputStream in = new FileInputStream(fn);
			JSPetriNetParser parser = new JSPetriNetParser(in);
			parser.setNet(global);
			parser.makeNet();
//			for (Place p : global.getPlaceSet().values()) {
////				System.out.println(p.getLabel() + " max:" + p.getMax());
//				System.out.println("place: " + p.getLabel());
//			}
//			for (Trans tr : global.getExpTransSet().values()) {
//				ExpTrans etr = (ExpTrans) tr;
////				System.out.println(etr.getLabel() + " rate:" + etr.getRate(global));			
//				System.out.println("EXP: " + etr.getLabel());
//			}
//			for (Trans tr : global.getImmTransSet().values()) {
//				ImmTrans etr = (ImmTrans) tr;
////				System.out.println(etr.getLabel() + " weight:" + etr.getWeight(global));			
//				System.out.println("IMM: " + etr.getLabel());			
//			}
//			for (Trans tr : global.getGenTransSet().values()) {
//				GenTrans etr = (GenTrans) tr;
//				System.out.println("GEN: " + etr.getLabel() + " policy:" + etr.getPolicy());
//			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TokenMgrError ex) {
			System.out.println("token error: " + ex.getMessage());
		} catch (ParseException ex) {
			System.out.println("parse error: " + ex.getMessage());			
		} catch (ASTException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	public static Net load(String label, String fn) {
		Net global = new Net(null, label);
		try {
			InputStream in = new FileInputStream(fn);
			JSPetriNetParser parser = new JSPetriNetParser(in);
			parser.setNet(global);
			parser.makeNet();
//			for (Place p : global.getPlaceSet().values()) {
////				System.out.println(p.getLabel() + " max:" + p.getMax());
//				System.out.println("place: " + p.getLabel());
//			}
//			for (Trans tr : global.getExpTransSet().values()) {
//				ExpTrans etr = (ExpTrans) tr;
////				System.out.println(etr.getLabel() + " rate:" + etr.getRate(global));			
//				System.out.println("EXP: " + etr.getLabel());
//			}
//			for (Trans tr : global.getImmTransSet().values()) {
//				ImmTrans etr = (ImmTrans) tr;
////				System.out.println(etr.getLabel() + " weight:" + etr.getWeight(global));			
//				System.out.println("IMM: " + etr.getLabel());			
//			}
//			for (Trans tr : global.getGenTransSet().values()) {
//				GenTrans etr = (GenTrans) tr;
//				System.out.println("GEN: " + etr.getLabel() + " policy:" + etr.getPolicy());
//			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TokenMgrError ex) {
			System.out.println("token error: " + ex.getMessage());
		} catch (ParseException ex) {
			System.out.println("parse error: " + ex.getMessage());			
		} catch (ASTException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		global.setIndex();
		return global;
	}

	public static void eval(Net global, String text) {
		try {
			InputStream in = new ByteArrayInputStream(text.getBytes("utf-8"));
			JSPetriNetParser parser = new JSPetriNetParser(in);
			parser.setNet(global);
			parser.makeNet();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TokenMgrError ex) {
			System.out.println("token error: " + ex.getMessage());
		} catch (ParseException ex) {
			System.out.println("parse error: " + ex.getMessage());			
		} catch (ASTException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	public static String toViz(Net global, String startPlace) throws ASTException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bos);
		out.println("digraph { layout=dot; overlap=false; splines=true; node [fontsize=10];");
		global.getPlace(startPlace).accept(new jspetrinet.petri.VizPrint(out));
		out.println("}");
		return bos.toString();		
	}
	
	public static Mark mark(Net net, Map<String,Integer> map) {
		Mark m = new Mark(net.getNumOfPlace());
		try {
			for (Map.Entry<String, Integer> e : map.entrySet()) {
				m.set(net.getPlace(e.getKey()).getIndex(), e.getValue());
			}
		} catch (ASTException e1) {
			e1.printStackTrace();
		}
		return m;
	}

	public static GenVec genvec(Net net, Map<String,Integer> map) {
		GenVec gv = new GenVec(net.getNumOfGenTrans());
		for (Map.Entry<String,Integer> e: map.entrySet()) {			
			gv.set(net.getGenTransSet().get(e.getKey()).getIndex(), e.getValue());
		}
		return gv;
	}

	public static MarkingProcess marking(Net global, Mark m) {
		MarkingProcess mp = new MarkingProcess();
		try {
			System.out.print("Create marking...");
			long start = System.nanoTime();
			mp.create(m, global);
			System.out.println("done");
			System.out.println("computation time    : " + (System.nanoTime() - start) / 1000000000.0 + " (sec)");
//			mp.createIndex(true);
			System.out.println(markingToString(global, mp));
		} catch (ASTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mp;
	}

	/// utils
	
	public static String markToString(Net net, Mark vec) {
		String result = "";
		for (Map.Entry<String,Place> e : net.getPlaceSet().entrySet()) {
			if (vec.get(e.getValue().getIndex()) != 0) {
				if (result.equals("")) {
					result = e.getKey() + ":" + vec.get(e.getValue().getIndex());
				} else {
					result = result + "," + e.getKey() + ":" + vec.get(e.getValue().getIndex());
				}
			}
		}
		return result;
	}

	public static String genvecToString(Net net, GenVec genv) {
		String result = "(";
		for (Trans t: net.getGenTransSet().values()) {
			switch(genv.get(t.getIndex())) {
			case 0:
//				if (!result.equals("(")) {
//					result += " ";
//				}
//				result += t.getLabel() + "->disable";
				break;
			case 1:
				if (!result.equals("(")) {
					result += " ";
				}
				result += t.getLabel() + "->enable";
				break;
			case 2:
				if (!result.equals("(")) {
					result += " ";
				}
				result += t.getLabel() + "->preemption";
				break;
			default:
				break;
			}
		}
		if (result.equals("(")) {
			result += "EXP";
		}
		return result + ")";
	}

	public static String markingToString(Net net, MarkingProcess mp) {
		String linesep = System.getProperty("line.separator").toString();
		String res = "";
		int total = mp.count();
		int immtotal = mp.immcount();
		res += "# of total states   : " + total + linesep;
		res += "# of IMM states     : " + immtotal + linesep;
		res += "# of EXP/GEN states : " + (total - immtotal) + linesep;
		for (Entry<GenVec, MarkGroup> e : mp.getImmGroup().entrySet()) {
			String g = genvecToString(net, e.getKey());
			int im = e.getValue().size();
			int em = mp.getGenGroup().get(e.getKey()).size();
			res += g + linesep;
			res += "  # of IMM states     : " + im + linesep;
			res += "  # of EXP/GEN states : " + em + linesep;
		}
		return res;
	}
	
	public static void writeDotfile(Net net, String file, String placeLabel) {
		try {
			PrintStream out = new PrintStream(file);
			out.println("digraph { layout=dot; overlap=false; splines=true; node [fontsize=10];");
			net.getPlace(placeLabel).accept(new jspetrinet.petri.VizPrint(out));
			out.println("}");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ASTException e) {
			e.printStackTrace();
		}
	}

}
