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
import java.util.Set;
import java.util.TreeSet;

import jspetrinet.exception.ASTException;
import jspetrinet.marking.*;
import jspetrinet.parser.JSPetriNetParser;
import jspetrinet.parser.ParseException;
import jspetrinet.parser.TokenMgrError;
import jspetrinet.petri.*;

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
			System.out.println("Error: " + e.getMsg());
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
			System.out.println("Error: " + e.getMsg());
			e.printStackTrace();
		}
	}

	public static Mark mark(Net net, Map<String,Integer> map) {
		Mark m = new Mark(net, net.getNumOfPlace());
		try {
			for (Map.Entry<String, Integer> e : map.entrySet()) {
				Object obj = net.get(e.getKey());
				if (obj instanceof Place) {
					Place p = (Place) obj;
					m.set(p.getIndex(), e.getValue());
				} else {
					throw new ASTException(e.getKey() + " is not a place.");
				}
			}
		} catch (ASTException e1) {
			e1.printStackTrace();
		}
		return m;
	}

	public static MarkingGraph marking(Net global, Mark m, int depth) {
		MarkingGraph mp = new MarkingGraph();
		if (depth == 0) {
			mp.setCreateMarking(new CreateMarkingDFS(mp));
		} else {
			mp.setCreateMarking(new CreateMarkingBFS(mp, depth));
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
		for (Place p : net.getPlaceSet()) {
			if (vec.get(p.getIndex()) != 0) {
				if (result.equals("")) {
					result = p.getLabel() + ":" + vec.get(p.getIndex());
				} else {
					result = result + "," + p.getLabel() + ":" + vec.get(p.getIndex());
				}
			}
		}
		return result;
	}

	public static String genvecToString(Net net, GenVec genv) {
		String result = "(";
		for (Trans t: net.getGenTransSet()) {
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

	public static String markingToString(Net net, MarkingGraph mp) {
		String linesep = System.getProperty("line.separator").toString();
		String res = "";
		int total = mp.size();
		int immtotal = mp.immSize();
		res += "# of total states   : " + total + linesep;
		res += "# of IMM states     : " + immtotal + linesep;
		res += "# of EXP/GEN states : " + (total - immtotal) + linesep;
		Set<GenVec> all = new TreeSet<GenVec>();
		all.addAll(mp.getImmGroup().keySet());
		all.addAll(mp.getGenGroup().keySet());
		for (GenVec gv : all) {
			String g = genvecToString(net, gv);
			res += g + linesep;
			if (mp.getImmGroup().containsKey(gv)) {
				int im = mp.getImmGroup().get(gv).size();
				res += "  # of IMM states     : " + im + linesep;
			}
			if (mp.getGenGroup().containsKey(gv)) {
				int em = mp.getGenGroup().get(gv).size();
				res += "  # of EXP/GEN states : " + em + linesep;
			}
		}
		return res;
	}
	
	public static void writeDotfile(Net net, PrintWriter bw) {
		VizPrint vp = new VizPrint(net);
		vp.toviz(bw);
	}

}
