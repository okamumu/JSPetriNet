package jspetrinet;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import jspetrinet.exception.ASTException;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkingProcess;
import jspetrinet.marking.MarkingProcessBounded;
import jspetrinet.parser.JSPetriNetParser;
import jspetrinet.parser.ParseException;
import jspetrinet.parser.TokenMgrError;
import jspetrinet.petri.Net;
import jspetrinet.petri.Place;
import jspetrinet.petri.Trans;
import jspetrinet.petri.VizPrint;

public class JSPetriNet {
	
	public static Net load(String label, Net parent, InputStream in) {
		Net net = new Net(parent, label);
		try {
			JSPetriNetParser parser = new JSPetriNetParser(in);
			parser.setNet(net);
			parser.makeNet();
		} catch (TokenMgrError ex) {
			System.out.println("token error: " + ex.getMessage());
		} catch (ParseException ex) {
			System.out.println("parse error: " + ex.getMessage());			
		} catch (ASTException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return net;
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

	public static MarkingProcess marking(Net global, Mark m, int depth) {
		MarkingProcess mp;
		if (depth == 0) {
			mp = new MarkingProcess();
		} else {
			mp = new MarkingProcessBounded(depth);
		}
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
	
	public static void writeDotfile(Net net, PrintWriter bw) {
		VizPrint vp = new VizPrint(net);
		vp.toviz(bw);
	}

	public static void writeMarkingProcess(Serializable mp) {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("test.obj"));
			oos.writeObject(mp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
