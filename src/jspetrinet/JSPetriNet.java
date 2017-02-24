package jspetrinet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jspetrinet.analysis.GroupMarkingGraph;
import jspetrinet.exception.JSPNException;
import jspetrinet.exception.JSPNExceptionType;
import jspetrinet.marking.*;
import jspetrinet.parser.JSPetriNetParser;
import jspetrinet.petri.*;

public class JSPetriNet {

	public static Net load(Net net, InputStream in) throws JSPNException, IOException {
		JSPetriNetParser parser = new JSPetriNetParser(in);
		parser.setNet(net);
		parser.makeNet();
		return net;
	}

	public static Net eval(Net net, String text) throws JSPNException {
		JSPetriNetParser parser = new JSPetriNetParser(text);
		parser.setNet(net);
		parser.makeNet();
		return net;
	}

	public static Mark mark(Net net, Map<String,Integer> map) throws JSPNException {
		Mark m = new Mark(net.getPlaceSet().size());
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			Object obj = null;
			try {
				obj = net.get(entry.getKey());
				Place p = (Place) obj;
				m.set(p.getIndex(), entry.getValue());
			} catch (JSPNException ex) {
				throw new JSPNException(JSPNExceptionType.NOT_FIND, "Did not find " + entry.getKey() + ". ");
			} catch(ClassCastException ex) {
				throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, entry.getKey() + " was not a place object. " + obj.toString());
			}
		}
		return m;
	}
	
//	public static MarkingGraph marking(PrintWriter pw, Net net, Mark init, int depth, boolean tangible) throws JSPNException {
//		MarkingGraph mp = new MarkingGraph(net);
//		CreateMarkingStrategyAnalysis cm;
//		if (tangible) {
//			cm = new CreateMarkingDFStangible(mp, new CreateMarking(mp));
//		} else {
//			cm = new CreateMarkingDFS(mp, depth, new CreateMarking(mp));
//		}
//		pw.print("Create marking...");
//		long start = System.nanoTime();
//		cm.create(init);
//		pw.println("done");
//		pw.println("computation time    : " + (System.nanoTime() - start) / 1000000000.0 + " (sec)");
//		pw.println(markingToString(net, mp));
//		return mp;
//	}

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
		for (GenTrans t: net.getGenTransSet()) {
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
//		for (Trans t: net.getExpTransSet()) {
//			switch(genv.get(t.getIndex())) {
//			case 0:
//				break;
//			case 1:
//				if (!result.equals("(")) {
//					result += " ";
//				}
//				result += t.getLabel() + "->enable";
//				break;
//			default:
//				break;
//			}
//		}
		if (result.equals("(")) {
			result += "EXP";
		}
		return result + ")";
	}

	public static String markingToString(Net net, MarkingGraph mp, GroupMarkingGraph markGroups) {
		String linesep = System.getProperty("line.separator").toString();
		String res = "";
		int immtotal = mp.immSize();
		int gentotal = mp.genSize();
		int total = immtotal + gentotal;
		res += "# of total states   : " + total + linesep;
		res += "# of IMM states     : " + immtotal + linesep;
		res += "# of EXP/GEN states : " + gentotal + linesep;
		for (MarkGroup mg : markGroups.getAllMarkGroupList()) {
//		Set<GenVec> all = new TreeSet<GenVec>();
//		all.addAll(mp.getImmGroup().keySet());
//		all.addAll(mp.getGenGroup().keySet());
//		for (GenVec gv : all) {
//			String g = genvecToString(net, gv);
			res += mg.getID() + " " + mg.getLabel() + " ";
			if (mg.isIMM()) {
				res += mg.size() + linesep;				
			} else {
				res += mg.size() + linesep;				
			}
		}
		return res;
	}
	
	public static void writeDotfile(PrintWriter bw, Net net) {
		VizPrint vp = new VizPrint(net);
		vp.toviz(bw);
	}

}
