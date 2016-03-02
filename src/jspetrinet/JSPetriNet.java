package jspetrinet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import jspetrinet.exception.ASTException;
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

	public void eval(Net global, String text) {
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

	public String toViz(Net global, String startPlace) throws ASTException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bos);
		out.println("digraph { layout=dot; overlap=false; splines=true; node [fontsize=10];");
		global.getPlace(startPlace).accept(new jspetrinet.petri.VizPrint(out));
		out.println("}");
		return bos.toString();		
	}
}
