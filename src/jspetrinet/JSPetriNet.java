package jspetrinet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jspetrinet.exception.ASTException;
import jspetrinet.marking.*;
import jspetrinet.parser.JSPetriNetParser;
import jspetrinet.parser.ParseException;
import jspetrinet.petri.*;

public class JSPetriNet {

	public static Net load(Net net, InputStream in) throws ParseException, ASTException {
		JSPetriNetParser parser = new JSPetriNetParser(in);
		parser.setNet(net);
		parser.makeNet();
		return net;
	}

	public static Net eval(Net net, String text) throws ParseException, ASTException, UnsupportedEncodingException {
		InputStream in = new ByteArrayInputStream(text.getBytes("utf-8"));
		JSPetriNetParser parser = new JSPetriNetParser(in);
		parser.setNet(net);
		parser.makeNet();
		return net;
	}

	public static Mark mark(Net net, Map<String,Integer> map) throws ASTException {
		Mark m = new Mark(net.getNumOfPlace());
		for (Map.Entry<String, Integer> e : map.entrySet()) {
			Object obj = net.get(e.getKey());
			if (obj instanceof Place) {
				Place p = (Place) obj;
				m.set(p.getIndex(), e.getValue());
			} else {
				throw new ASTException(e.getKey() + " is not a place.");
			}
		}
		return m;
	}
	
	public static MarkingGraph marking(PrintWriter pw, Net net, Mark m, int depth, boolean tangible, List<Trans> expTransSet) throws ASTException {
		MarkingGraph mp = new MarkingGraph();
		if (depth == 0) {
			if (tangible) {
				CreateMarkingDFStangible cmdt = new CreateMarkingDFStangible(mp);
				cmdt.setGenTransSet(expTransSet);
				mp.setCreateMarking(cmdt);
			} else {
				CreateMarkingDFS cmdt = new CreateMarkingDFS(mp);
				cmdt.setGenTransSet(expTransSet);
				mp.setCreateMarking(cmdt);
			}
		} else {
			mp.setCreateMarking(new CreateMarkingBFS(mp, depth));
		}
		pw.print("Create marking...");
		long start = System.nanoTime();
		mp.create(m, net);
		pw.println("done");
		pw.println("computation time    : " + (System.nanoTime() - start) / 1000000000.0 + " (sec)");
		pw.println(markingToString(net, mp));
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
			switch(genv.get(net.getExpTransSet().size() + t.getIndex())) {
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
		for (Trans t: net.getExpTransSet()) {
			switch(genv.get(t.getIndex())) {
			case 0:
				break;
			case 1:
				if (!result.equals("(")) {
					result += " ";
				}
				result += t.getLabel() + "->enable";
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
	
	public static void writeDotfile(PrintWriter bw, Net net) {
		VizPrint vp = new VizPrint(net);
		vp.toviz(bw);
	}

}
