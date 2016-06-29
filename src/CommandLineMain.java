import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import jspetrinet.*;
import jspetrinet.exception.*;
import jspetrinet.marking.*;
import jspetrinet.petri.*;

class Opts {
	// modes
	public static String VIEW="view";
	public static String MARKING="marking";
	
	// options
	public static String INPETRI="spn";
	public static String OUTPETRI="dot";
	public static String OUTMAT="out";
	public static String INITMARK="imark";
	public static String DEPTH="depth";
}

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
	
	public static void cmdView(String[] args) {
		Options options = new Options();
		options.addOption(Opts.INPETRI, true, "input Petrinet file");
		options.addOption(Opts.OUTPETRI, true, "output file");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net;
		if (cmd.hasOption(Opts.INPETRI)) {
			InputStream in;
			try {
				in = new FileInputStream(cmd.getOptionValue(Opts.INPETRI));
			} catch (FileNotFoundException e) {
				System.err.println("Did not find file: " + cmd.getOptionValue(Opts.INPETRI));
				return;
			}
			net = JSPetriNet.load("", null, in);
		} else {
			net = JSPetriNet.load("", null, System.in);
		}
		net.setIndex();

		PrintWriter bw;
		if (cmd.hasOption(Opts.OUTPETRI)) {
			try {
				bw = new PrintWriter(cmd.getOptionValue(Opts.OUTPETRI));
			} catch (FileNotFoundException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTPETRI));
				return;
			}
		} else {
			bw = new PrintWriter(System.out);
		}
		JSPetriNet.writeDotfile(net, bw);
		bw.close();
	}
		
	public static void cmdMarking(String[] args) {
		Options options = new Options();
		options.addOption(Opts.INPETRI, true, "input Petrinet file");
		options.addOption(Opts.INITMARK, true, "initial marking");
		options.addOption(Opts.DEPTH, true, "depth for DFS");
		options.addOption(Opts.OUTMAT, true, "matrix (output)");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net;
		if (cmd.hasOption(Opts.INPETRI)) {
			InputStream in;
			try {
				in = new FileInputStream(cmd.getOptionValue(Opts.INPETRI));
			} catch (FileNotFoundException e) {
				System.err.println("Did not find file: " + cmd.getOptionValue(Opts.INPETRI));
				return;
			}
			net = JSPetriNet.load("", null, in);
		} else {
			net = JSPetriNet.load("", null, System.in);
		}
		net.setIndex();

		int depth;
		if (cmd.hasOption(Opts.DEPTH)) {
			depth = Integer.parseInt(cmd.getOptionValue(Opts.DEPTH));
		} else {
			depth = 0;
		}

		Mark imark;
		if (cmd.hasOption(Opts.INITMARK)) {
			imark = JSPetriNet.mark(net, parseMark(cmd.getOptionValue(Opts.INITMARK)));
			System.out.println("Initial marking: " + JSPetriNet.markToString(net, imark));
		} else {
			System.err.println("Marking process requires an initial marking (-" + Opts.INITMARK + ").");
			return;
		}
		
		MarkingProcess mp = JSPetriNet.marking(net, imark, depth);
		MarkingMatrix mat = new MarkingMatrix(mp, true);

		PrintWriter pw1, pw2;
		if (cmd.hasOption(Opts.OUTMAT)) {
			try {
				pw1 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUTMAT) + ".states")));
				pw2 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUTMAT) + ".matrix")));
			} catch (FileNotFoundException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTMAT));
				return;
			} catch (IOException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTMAT));
				return;
			}			
		} else {
			pw1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
			pw2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		}

		writeMarkSet(pw1, mat);
		writeMatrix(pw2, mat);
		pw1.close();
		pw2.close();
	}

	public static void writeMarkSet(PrintWriter pw, MarkingMatrix mat) {
		MarkingProcess mp = mat.getMarkingProcess();
		Net net = mp.getNet();
		pw.println("IMM");
		for (GenVec gv: mp.getImmGroup().keySet()) {
			pw.println(mat.getImmMatrixLabel().get(gv) + " " + JSPetriNet.genvecToString(net, gv));
			List< List<Object> > s = mat.getMakingSet(mp.getImmGroup().get(gv));
			for (List<Object> e: s) {
				pw.print(e.get(0) + " : ");
				Mark m = (Mark) e.get(1);
				pw.println(JSPetriNet.markToString(net, m));
			}
		}
		pw.println("GEN");
		for (GenVec gv: mp.getGenGroup().keySet()) {
			pw.println(mat.getGenMatrixLabel().get(gv) + " " + JSPetriNet.genvecToString(net, gv));
			List< List<Object> > s = mat.getMakingSet(mp.getGenGroup().get(gv));
			for (List<Object> e: s) {
				pw.print(e.get(0) + " : ");
				Mark m = (Mark) e.get(1);
				pw.println(JSPetriNet.markToString(net, m));
			}
		}
	}

	public static void writeMatrix(PrintWriter pw, MarkingMatrix mat) {
		MarkingProcess mp = mat.getMarkingProcess();
		Net net = mp.getNet();
		for (GenVec gv: mp.getImmGroup().keySet()) {
			MarkGroup src = mp.getImmGroup().get(gv);
			for (GenVec gv2: mp.getImmGroup().keySet()) {
				MarkGroup dest = mp.getImmGroup().get(gv2);
				if (dest.size() != 0) {
					List< List<Object> > s = mat.getMatrixI(net, src, dest);
					if (s.size() != 0) {
						String matname = mat.getImmMatrixLabel().get(gv) + mat.getImmMatrixLabel().get(gv2);
						pw.println("# " + matname + " " + JSPetriNet.genvecToString(net, gv) + " >>> " + JSPetriNet.genvecToString(net, gv2));
						pw.println(matname + " <- Matrix(0," + src.size() + "," + dest.size() + ")");
						for (List<Object> e: s) {
							pw.println(matname + "[" + e.get(0) + "," + e.get(1) + "] <- " + e.get(2));
						}
					}
				}
			}
		}
		for (GenVec gv: mp.getImmGroup().keySet()) {
			MarkGroup src = mp.getImmGroup().get(gv);
			for (GenVec gv2: mp.getGenGroup().keySet()) {
				MarkGroup dest = mp.getGenGroup().get(gv2);
				if (dest.size() != 0) {
					List< List<Object> > s = mat.getMatrixI(net, src, dest);
					if (s.size() != 0) {
						String matname = mat.getImmMatrixLabel().get(gv) + mat.getGenMatrixLabel().get(gv2);
						pw.println("# " + matname + " " + JSPetriNet.genvecToString(net, gv) + " >>> " + JSPetriNet.genvecToString(net, gv2));
						pw.println(matname + " <- Matrix(0," + src.size() + "," + dest.size() + ")");
						for (List<Object> e: s) {
							pw.println(matname + "[" + e.get(0) + "," + e.get(1) + "] <- " + e.get(2));
						}
					}
				}
			}
		}
		for (GenVec gv: mp.getGenGroup().keySet()) {
			MarkGroup src = mp.getGenGroup().get(gv);
			for (GenVec gv2: mp.getImmGroup().keySet()) {
				MarkGroup dest = mp.getImmGroup().get(gv2);
				if (dest.size() != 0) {
					Map<Integer,List<List<Object>>> elem = mat.getMatrixG(net, src, dest);
					List<List<Object>> s = elem.get(MarkingMatrix.ExpTransIndex);
					if (s.size() != 0) {
						String matname = mat.getGenMatrixLabel().get(gv) + mat.getImmMatrixLabel().get(gv2) + "E";
						pw.println("# " + matname + " " + JSPetriNet.genvecToString(net, gv) + " >>> " + JSPetriNet.genvecToString(net, gv2));
						pw.println(matname + " <- Matrix(0," + src.size() + "," + dest.size() + ")");
						for (List<Object> e: s) {
							pw.println(matname + "[" + e.get(0) + "," + e.get(1) + "] <- " + e.get(2));
						}
					}
					
					for (Map.Entry<Integer, List<List<Object>>> entry: elem.entrySet()) {
						if (entry.getKey() != MarkingMatrix.ExpTransIndex) {
							s = entry.getValue();
							if (s.size() != 0) {
								String matname = mat.getGenMatrixLabel().get(gv) + mat.getImmMatrixLabel().get(gv2) + "P" + entry.getKey();
								pw.println("# " + matname + " " + JSPetriNet.genvecToString(net, gv) + " >>> " + JSPetriNet.genvecToString(net, gv2));
								pw.println(matname + " <- Matrix(0," + src.size() + "," + dest.size() + ")");
								for (List<Object> e: s) {
									pw.println(matname + "[" + e.get(0) + "," + e.get(1) + "] <- " + e.get(2));
								}
							}							
						}
					}
				}
			}
		}
		for (GenVec gv: mp.getGenGroup().keySet()) {
			MarkGroup src = mp.getGenGroup().get(gv);
			for (GenVec gv2: mp.getGenGroup().keySet()) {
				MarkGroup dest = mp.getGenGroup().get(gv2);
				if (dest.size() != 0) {
					Map<Integer,List<List<Object>>> elem = mat.getMatrixG(net, src, dest);
					List<List<Object>> s = elem.get(MarkingMatrix.ExpTransIndex);
					if (s.size() != 0 || src == dest) {
						String matname = mat.getGenMatrixLabel().get(gv) + mat.getGenMatrixLabel().get(gv2) + "E";
						pw.println("# " + matname + " " + JSPetriNet.genvecToString(net, gv) + " >>> " + JSPetriNet.genvecToString(net, gv2));
						pw.println(matname + " <- Matrix(0," + src.size() + "," + dest.size() + ")");
						for (List<Object> e: s) {
							pw.println(matname + "[" + e.get(0) + "," + e.get(1) + "] <- " + e.get(2));
						}
					}
					
					for (Map.Entry<Integer, List<List<Object>>> entry: elem.entrySet()) {
						if (entry.getKey() != MarkingMatrix.ExpTransIndex) {
							s = entry.getValue();
							if (s.size() != 0) {
								String matname = mat.getGenMatrixLabel().get(gv) + mat.getGenMatrixLabel().get(gv2) + "P" + entry.getKey();
								pw.println("# " + matname + " " + JSPetriNet.genvecToString(net, gv) + " >>> " + JSPetriNet.genvecToString(net, gv2));
								pw.println(matname + " <- Matrix(0," + src.size() + "," + dest.size() + ")");
								for (List<Object> e: s) {
									pw.println(matname + "[" + e.get(0) + "," + e.get(1) + "] <- " + e.get(2));
								}
							}							
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws ASTException {
		if (args.length < 1) {
			System.err.print("Require mode: " + Opts.VIEW + ", " + Opts.MARKING);
			System.err.println();
			return;
		}

		String mode = args[0];
		String[] newargs = new String [args.length-1];
		for (int i=1; i<args.length; i++) {
			newargs[i-1] = args[i];
		}

		if (mode.equals(Opts.VIEW)) {
			cmdView(newargs);
		} else if (mode.equals(Opts.MARKING)) {
			cmdMarking(newargs);
		}
	}

}
