import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import jmatout.MATLABHeader;
import jspetrinet.JSPetriNet;
import jspetrinet.analysis.MRGPMatrixASCIIWriter;
import jspetrinet.analysis.MRGPMatrixMATLABWriter;
import jspetrinet.analysis.MarkClassAnalysis;
import jspetrinet.analysis.MarkingMatrix;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.parser.TokenMgrError;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;
import jspetrinet.sim.EventValue;
import jspetrinet.sim.MCSimulation;
import jspetrinet.sim.Random;
import jspetrinet.sim.RandomGenerator;
import jspetrinet.sim.Utility;

class Opts {
	// modes
	public static String VIEW="view";
	public static String MARKING="mark";
	public static String SIMULATION="sim";

	// options
	public static String INPETRI="i";
	public static String OUT="o";
	public static String INITMARK="imark";
	public static String REWARD="reward";
	public static String VANISHING="tangible";
	public static String FIRINGLIMIT="limit";
	public static String EXP="exp";
	public static String MATLAB="matlab";

	public static String GROUPGRAPH="ggraph";
	public static String MARKGRAPH="mgraph";
	public static String SCC="scc";

	public static String SEED="seed";
	public static String SIMTIME="time";
	public static String SIMRUN="run";
}

public class CommandLineMain {

	static private Map<String,Integer> parseMark(String str) {
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

	static private List<AST> parseReward(Net net, String str) throws JSPNException {
		List<AST> result = new ArrayList<AST>();
		String[] ary = str.split(",", 0);
		for (String s : ary) {
			Object obj = net.get(s);
			if (obj instanceof AST) {
				result.add((AST) obj);
			} else {
				throw new TypeMismatch();
			}
		}
		return result;
	}

	static private List<Trans> parseExpTrans(Net net, String str) throws JSPNException {
		List<Trans> result = new ArrayList<Trans>();
		String[] ary = str.split(",", 0);
		for (String s : ary) {
			Object obj = net.get(s);
			if (obj instanceof ExpTrans) {
				result.add((Trans) obj);
			} else {
				throw new TypeMismatch();
			}
		}
		return result;
	}

	private static Net loadNet(CommandLine cmd) {
		Net net = new Net(null, "");
		InputStream in = null;
		if (cmd.hasOption(Opts.INPETRI)) {
			try {
				in = new FileInputStream(cmd.getOptionValue(Opts.INPETRI));
			} catch (FileNotFoundException e) {
				System.err.println("Error: Did not find file: " + cmd.getOptionValue(Opts.INPETRI));
				System.exit(1);
			}
		} else {
			in = System.in;
		}
		try {
			net = JSPetriNet.load(net, in);
		} catch (TokenMgrError ex) {
			System.out.println("token error: " + ex.getMessage());
			System.exit(1);
		} catch (jspetrinet.parser.ParseException ex) {
			System.out.println("parse error: " + ex.getMessage());
			System.exit(1);
		} catch (JSPNException e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		}
		net.setIndex();
		return net;
	}

	private static Mark getInitialMark(CommandLine cmd, Net net) {
		Mark imark = null;
		if (cmd.hasOption(Opts.INITMARK)) {
			try {
				imark = JSPetriNet.mark(net, parseMark(cmd.getOptionValue(Opts.INITMARK)));
			} catch (JSPNException e) {
				System.err.println("Error: " + e.getMessage() + " Please check the imark option.");
				System.exit(1);
			}
			System.out.println("Initial marking: " + JSPetriNet.markToString(net, imark));
		} else {
			System.err.println("Marking process requires an initial marking (-" + Opts.INITMARK + ").");
			System.exit(1);
		}
		return imark;
	}

	private static int getLimit(CommandLine cmd, int defaultValue) {
		if (cmd.hasOption(Opts.FIRINGLIMIT)) {
			return Integer.parseInt(cmd.getOptionValue(Opts.FIRINGLIMIT));
		} else {
			return defaultValue;
		}
	}

	private static boolean getVanish(CommandLine cmd, boolean defaultValue) {
		if (cmd.hasOption(Opts.VANISHING)) {
			return true;
		} else {
			return defaultValue;
		}
	}

	public static void cmdView(String[] args) {
		Options options = new Options();
		options.addOption(Opts.INPETRI, true, "input Petrinet file");
		options.addOption(Opts.OUT, true, "output file");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net = loadNet(cmd);

		PrintWriter bw;
		if (cmd.hasOption(Opts.OUT)) {
			try {
				bw = new PrintWriter(cmd.getOptionValue(Opts.OUT));
			} catch (FileNotFoundException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUT));
				return;
			}
		} else {
			bw = new PrintWriter(System.out);
		}
		JSPetriNet.writeDotfile(bw, net);
		bw.close();
	}

	public static void cmdAnalysis(String[] args) {
		Options options = new Options();
		options.addOption(Opts.INPETRI, true, "input PetriNet file");
		options.addOption(Opts.INITMARK, true, "initial marking");
		options.addOption(Opts.FIRINGLIMIT, true, "test mode (input depth for DFS)");
		options.addOption(Opts.OUT, true, "matrix (output)");
		options.addOption(Opts.VANISHING, false, "vanish IMM");
		options.addOption(Opts.GROUPGRAPH, true, "marking group graph (output)");
		options.addOption(Opts.MARKGRAPH, true, "marking graph (output)");
		options.addOption(Opts.REWARD, true, "reward");
		options.addOption(Opts.EXP, true, "exp trans");
		options.addOption(Opts.MATLAB, false, "MATLAB mat file");
		options.addOption(Opts.SCC, true, "scc");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net = loadNet(cmd);
		Mark imark = getInitialMark(cmd, net);
		int depth = getLimit(cmd, 0);
		boolean vanishing = getVanish(cmd, false);

		List<Trans> expTrans = null;
		if (cmd.hasOption(Opts.EXP)) {
			try {
				expTrans = parseExpTrans(net, cmd.getOptionValue(Opts.EXP));
			} catch (JSPNException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		} else {
			expTrans = new ArrayList<Trans>();
		}

		PrintWriter pw0;
		pw0 = new PrintWriter(System.out);
		MarkingGraph mp;
		try {
			mp = JSPetriNet.marking(pw0, net, imark, depth, vanishing, expTrans);
		} catch (JSPNException e1) {
			System.err.println(e1.getMessage());
			return;
		}
		pw0.flush();

		MRGPMatrixASCIIWriter mrgp = new MRGPMatrixASCIIWriter(mp, true);
		MRGPMatrixMATLABWriter matlab = new MRGPMatrixMATLABWriter(mp);

		if (cmd.hasOption(Opts.OUT)) {
			if (cmd.hasOption(Opts.MATLAB)) {
				try {
					PrintWriter pw = new PrintWriter(System.out);
					DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cmd.getOptionValue(Opts.OUT) + ".mat")));
					MATLABHeader header = new MATLABHeader();
					header.write(dos);
					matlab.writeMatrix(dos, pw);
					matlab.writeStateVec(dos, pw, imark);
					matlab.writeSumVec(dos, pw);
					if (cmd.hasOption(Opts.REWARD)) {
						String rewardLabel = cmd.getOptionValue(Opts.REWARD);
						matlab.writeStateRewardVec(dos, pw, parseReward(net, rewardLabel));
					}
					dos.close();
					pw.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (JSPNException e) {
					System.err.println(e.getMessage());
					System.exit(1);
				}
			} else {
				try {
					PrintWriter pw1, pw2, pw6, pw7;
					pw1 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUT) + ".states")));
					pw2 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUT) + ".matrix")));
					pw6 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUT) + ".init")));
					pw7 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUT) + ".sum")));
					mrgp.writeMarkSet(pw1);
					mrgp.writeMatrix(pw2);
					mrgp.writeStateVec(pw6, imark);
					mrgp.writeSumVec(pw7);
					pw1.close();
					pw2.close();
					pw6.close();
					pw7.close();
					if (cmd.hasOption(Opts.REWARD)) {
						String rewardLabel = cmd.getOptionValue(Opts.REWARD);
						PrintWriter pw5 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUT) + ".reward")));
						mrgp.writeStateRewardVec(pw5, parseReward(net, rewardLabel));
						pw5.close();
					}
				} catch (FileNotFoundException e) {
					System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(Opts.OUT));
					return;
				} catch (IOException e) {
					System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(Opts.OUT));
					return;
				} catch (JSPNException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			PrintWriter pw1, pw2, pw6, pw7;
			pw1 = new PrintWriter(System.out);
			pw2 = new PrintWriter(System.out);
			pw6 = new PrintWriter(System.out);
			pw7 = new PrintWriter(System.out);
			mrgp.writeMarkSet(pw1);
			mrgp.writeMatrix(pw2);
			mrgp.writeStateVec(pw6, imark);
			try {
				mrgp.writeSumVec(pw7);
			} catch (JSPNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			pw1.flush();
			pw2.flush();
			pw6.flush();
			pw7.flush();
			if (cmd.hasOption(Opts.REWARD)) {
				String rewardLabel = cmd.getOptionValue(Opts.REWARD);
				PrintWriter pw5 = new PrintWriter(System.out);
				try {
					mrgp.writeStateRewardVec(pw5, parseReward(net, rewardLabel));
				} catch (JSPNException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				pw5.flush();
			}
		}

		if (cmd.hasOption(Opts.GROUPGRAPH)) {
			try {
				PrintWriter pw;
				pw = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.GROUPGRAPH))));
				mp.dotMarkGroup(pw);
				pw.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(Opts.GROUPGRAPH));
				return;
			} catch (IOException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(Opts.GROUPGRAPH));
				return;
			}
		}

		if (cmd.hasOption(Opts.MARKGRAPH)) {
			try {
				PrintWriter pw;
				pw = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.MARKGRAPH))));
				mp.dotMarking(pw);
				pw.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(Opts.MARKGRAPH));
				return;
			} catch (IOException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(Opts.MARKGRAPH));
				return;
			}
		}

		if (cmd.hasOption(Opts.SCC)) {
			try {
				PrintWriter pw;
				pw = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.SCC))));
				Collection<Mark> am = mp.getImmGroup().get(mp.getImmGroup().keySet().iterator().next()).getMarkSet();
				MarkClassAnalysis mca = new MarkClassAnalysis(mp, am);
				mca.dotMarkGroup(pw);
				pw.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(Opts.SCC));
				return;
			} catch (IOException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(Opts.SCC));
				return;
			}
		}

	}

	public static void cmdSimulation(String[] args) {
		Options options = new Options();
		options.addOption(Opts.INPETRI, true, "input PetriNet file");
		options.addOption(Opts.INITMARK, true, "initial marking");
		options.addOption(Opts.FIRINGLIMIT, true, "limit");
		options.addOption(Opts.SEED, true, "seed");
		options.addOption(Opts.SIMTIME, true, "time");
		options.addOption(Opts.SIMRUN, true, "run");
		options.addOption(Opts.REWARD, true, "reward");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net = loadNet(cmd);

		MCSimulation mc = new MCSimulation(net);
		Mark imark = getInitialMark(cmd, net);
		int limits = getLimit(cmd, 1000);

		int run = 1;
		if (cmd.hasOption(Opts.SIMRUN)) {
			run = Integer.parseInt(cmd.getOptionValue(Opts.SIMRUN));
		}

		double endTime;
		if (cmd.hasOption(Opts.SIMTIME)) {
			String label = "simtime" + System.currentTimeMillis();
			try {
				JSPetriNet.eval(net, label + " = " + cmd.getOptionValue(Opts.SIMTIME) + ";");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (jspetrinet.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (JSPNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			try {
				endTime = Utility.convertObjctToDouble(((AST) net.get(label)).eval(net));
			} catch (JSPNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
//			endTime = Double.valueOf(cmd.getOptionValue(Opts.SIMTIME));
		} else {
			System.err.println("Time (-time) should be set for the simulation.");
			return;
		}

		int seed;
		if (cmd.hasOption(Opts.SEED)) {
			seed = Integer.valueOf(cmd.getOptionValue(Opts.SEED));
		} else {
			seed = (int) System.currentTimeMillis();
		}

		Random rnd = new RandomGenerator(seed);

		if (cmd.hasOption(Opts.REWARD)) {
			String rewardLabel = cmd.getOptionValue(Opts.REWARD);
			try {
				List<AST> rwdList = parseReward(net, rewardLabel);
				Map<AST,Double> totalRwd = new HashMap<AST,Double>();
				Map<AST,Double> total2Rwd = new HashMap<AST,Double>();
				for (AST rwd : rwdList) {
					totalRwd.put(rwd, 0.0);
					total2Rwd.put(rwd, 0.0);
				}
				for (int k=0; k<run; k++) {
					List<EventValue> result = mc.runSimulation(imark, 0.0, endTime, limits, rnd);
					for (AST rwd : rwdList) {
						double tmp = mc.resultReward(net, result, rwd, 0.0, endTime);
						totalRwd.put(rwd, totalRwd.get(rwd) + tmp);
						total2Rwd.put(rwd, total2Rwd.get(rwd) + tmp*tmp);
					}
				}
				for (AST rwd : rwdList) {
					double mean = totalRwd.get(rwd) / run;
					double s2 = (total2Rwd.get(rwd) - totalRwd.get(rwd) * totalRwd.get(rwd) / run) / (run - 1);
					double lcl = mean - 1.96 * Math.sqrt(s2 / run);
					double ucl = mean + 1.96 * Math.sqrt(s2 / run);

					System.out.println(rwd.toString());
					System.out.println(mean + " [" + lcl + "," + ucl + "]");
				}
			} catch (JSPNException e) {
				e.printStackTrace();
			}
		} else {
			try {
				PrintWriter pw = new PrintWriter(System.out);
				for (int k=0; k<run; k++) {
					List<EventValue> result = mc.runSimulation(imark, 0.0, endTime, limits, rnd);
					mc.resultEvent(pw, result);
					pw.println("----------");
				}
				pw.close();
			} catch (JSPNException e) {
				System.err.println("Failed: " + e.getMessage());
				e.printStackTrace();
				return;
			}
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.print("Require mode: " + Opts.VIEW + ", " + Opts.MARKING + ", " + Opts.SIMULATION);
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
			cmdAnalysis(newargs);
		} else if (mode.equals(Opts.SIMULATION)) {
			cmdSimulation(newargs);
		} else {
			System.err.print("Require mode: " + Opts.VIEW + ", " + Opts.MARKING + ", " + Opts.SIMULATION);
			System.err.println();
			return;
		}
	}

}
