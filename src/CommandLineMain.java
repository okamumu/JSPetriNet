import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jspetrinet.*;
import jspetrinet.exception.*;
import jspetrinet.marking.*;
import jspetrinet.petri.*;

public class CommandLineMain {
	
	static Map<String,Integer> parseMark(String str) {
		Map<String,Integer> result = new HashMap<String,Integer>();
		String[] ary = str.split(",", 0);
		Pattern p = Pattern.compile("(\\w+):([0-9]+)");
		for (String s : ary) {
			Matcher m = p.matcher(s);
			if (m.matches()) {
				String key = m.group(1);
				int value = Integer.parseInt(m.group(2));
				result.put(key, value);
			}
		}
		return result;
	}
	
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

		Mark m1 = JSPetriNet.mark(global, parseMark("Ph:5,Pw:5,Pc:5"));
		System.out.println("Initial marking: " + JSPetriNet.markToString(global, m1));
//		Map<String,Integer> initmark = new HashMap<String,Integer>();
//		initmark.put("Ph", 5);
//		initmark.put("Pw", 5);
//		initmark.put("Pc", 5);
//		Mark m1 = JSPetriNet.mark(global, initmark);
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
	}
	
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
		if (args.length < 1) {
			System.err.println("Require mode: graphviz, marking.");
			System.exit(1);
		}
		System.out.println(args[0]);
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
