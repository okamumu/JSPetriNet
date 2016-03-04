package jspetrinet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingProcess;
import jspetrinet.parser.JSPetriNetParser;
import jspetrinet.parser.ParseException;
import jspetrinet.parser.TokenMgrError;
import jspetrinet.petri.Net;

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
			mp.createIndex(true);
			System.out.println(mp.toString());
		} catch (ASTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mp;
	}

}
