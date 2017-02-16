package jspetrinet.cli;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

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
import jspetrinet.exception.JSPNException;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.Net;

public class CommandLineMark {

	private static void checkOptionMark(CommandLine cmd) {
		if (cmd.hasOption(CommandLineOptions.MATLAB) && cmd.hasOption(CommandLineOptions.TEXT)) {
			System.err.println("Output mode should be chosen either '-text' or '-bin'.");
			System.exit(1);
		}
		if (cmd.hasOption(CommandLineOptions.MATLAB)) {
			if (!cmd.hasOption(CommandLineOptions.OUT)) {
				System.err.println("The option '-bin' requires the option '-o' to write binary filise.");				
				System.exit(1);
			}
		}
	}
	
	private static MarkingMatrix outputBin(CommandLine cmd, MarkingGraph mp) {
		Net net = mp.getNet();
		Mark imark = mp.getInitialMark();
		MRGPMatrixMATLABWriter matlab = new MRGPMatrixMATLABWriter(mp);
		if (cmd.hasOption(CommandLineOptions.OUT)) {
			try {
				PrintWriter pw = new PrintWriter(System.out);
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cmd.getOptionValue(CommandLineOptions.OUT) + ".mat")));
				MATLABHeader header = new MATLABHeader();
				header.write(dos);
				matlab.writeMatrix(dos, pw);
				matlab.writeStateVec(dos, pw, imark);
				matlab.writeSumVec(dos, pw);
				if (cmd.hasOption(CommandLineOptions.REWARD)) {
					String rewardLabel = cmd.getOptionValue(CommandLineOptions.REWARD);
					matlab.writeStateRewardVec(dos, pw, CommandLineCommons.parseReward(net, rewardLabel));
				}
				dos.close();
				pw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (JSPNException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			System.err.println("The option bin requires the option '-o' to write binary filise.");
			System.exit(1);
		}
		return matlab;
	}

	private static MarkingMatrix outputText(CommandLine cmd, MarkingGraph mp) {
		Net net = mp.getNet();
		Mark imark = mp.getInitialMark();
		MRGPMatrixASCIIWriter mrgp = new MRGPMatrixASCIIWriter(mp, true);
		if (cmd.hasOption(CommandLineOptions.OUT)) {
			try {
				PrintWriter pw1, pw2, pw6, pw7;
				pw1 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.OUT) + ".states")));
				pw2 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.OUT) + ".matrix")));
				pw6 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.OUT) + ".init")));
				pw7 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.OUT) + ".sum")));
				mrgp.writeMarkSet(pw1);
				mrgp.writeMatrix(pw2);
				mrgp.writeStateVec(pw6, imark);
				mrgp.writeSumVec(pw7);
				pw1.close();
				pw2.close();
				pw6.close();
				pw7.close();
				if (cmd.hasOption(CommandLineOptions.REWARD)) {
					String rewardLabel = cmd.getOptionValue(CommandLineOptions.REWARD);
					PrintWriter pw5 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.OUT) + ".reward")));
					mrgp.writeStateRewardVec(pw5, CommandLineCommons.parseReward(net, rewardLabel));
					pw5.close();
				}
			} catch (FileNotFoundException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.OUT));
				return mrgp;
			} catch (IOException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.OUT));
				return mrgp;
			} catch (JSPNException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			PrintWriter pw1, pw2, pw6, pw7;
			pw1 = new PrintWriter(System.out);
			pw2 = new PrintWriter(System.out);
			pw6 = new PrintWriter(System.out);
			pw7 = new PrintWriter(System.out);
			try {
				mrgp.writeMarkSet(pw1);
				mrgp.writeMatrix(pw2);
				mrgp.writeStateVec(pw6, imark);
				mrgp.writeSumVec(pw7);
			} catch (JSPNException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
			pw1.flush();
			pw2.flush();
			pw6.flush();
			pw7.flush();
			if (cmd.hasOption(CommandLineOptions.REWARD)) {
				String rewardLabel = cmd.getOptionValue(CommandLineOptions.REWARD);
				PrintWriter pw5 = new PrintWriter(System.out);
				try {
					mrgp.writeStateRewardVec(pw5, CommandLineCommons.parseReward(net, rewardLabel));
				} catch (JSPNException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return mrgp;
				}
				pw5.flush();
			}
		}
		return mrgp;
	}

	public static void cmdAnalysis(String[] args) {
		Options options = new Options();
		options.addOption(CommandLineOptions.INPETRI, true, "input PetriNet file");
		options.addOption(CommandLineOptions.INITMARK, true, "initial marking");
		options.addOption(CommandLineOptions.FIRINGLIMIT, true, "test mode (input depth for DFS)");
		options.addOption(CommandLineOptions.OUT, true, "matrix (output)");
		options.addOption(CommandLineOptions.VANISHING, false, "vanish IMM");
		options.addOption(CommandLineOptions.GROUPGRAPH, true, "marking group graph (output)");
		options.addOption(CommandLineOptions.MARKGRAPH, true, "marking graph (output)");
		options.addOption(CommandLineOptions.REWARD, true, "reward");
//		options.addOption(CommandLineOptions.EXP, true, "exp trans");
		options.addOption(CommandLineOptions.TEXT, false, "TEXT mat file");
		options.addOption(CommandLineOptions.MATLAB, false, "MATLAB mat file");
		options.addOption(CommandLineOptions.SCC, true, "scc");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		checkOptionMark(cmd);

		Net net = CommandLineCommons.loadNet(cmd);
		Mark imark = CommandLineCommons.getInitialMark(cmd, net);
		int depth = CommandLineCommons.getLimit(cmd, 0);
		boolean vanishing = CommandLineCommons.getVanish(cmd, false);

//		List<Trans> expTrans = null;
//		if (cmd.hasOption(CommandLineOptions.EXP)) {
//			try {
//				expTrans = CommandLineCommons.parseExpTrans(net, cmd.getOptionValue(CommandLineOptions.EXP));
//			} catch (JSPNException e) {
//				System.err.println(e.getMessage());
//				System.exit(1);
//			}
//		} else {
//			expTrans = new ArrayList<Trans>();
//		}
		
		PrintWriter pw0;
		pw0 = new PrintWriter(System.out);
		MarkingGraph mp;
		try {
			mp = JSPetriNet.marking(pw0, net, imark, depth, vanishing);
		} catch (JSPNException e1) {
			System.err.println(e1.getMessage());
			return;
		}
		pw0.flush();

		MarkingMatrix mmat;
		if (cmd.hasOption(CommandLineOptions.MATLAB)) {
			mmat = outputBin(cmd, mp);
		} else {
			mmat = outputText(cmd, mp); // default -text
		}

		if (cmd.hasOption(CommandLineOptions.GROUPGRAPH)) {
			try {
				PrintWriter pw;
				pw = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.GROUPGRAPH))));
				mmat.dotMarkGroup(pw);
				pw.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.GROUPGRAPH));
				return;
			} catch (IOException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.GROUPGRAPH));
				return;
			}
		}

		if (cmd.hasOption(CommandLineOptions.MARKGRAPH)) {
			try {
				PrintWriter pw;
				pw = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.MARKGRAPH))));
				mmat.dotMarking(pw);
				pw.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.MARKGRAPH));
				return;
			} catch (IOException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.MARKGRAPH));
				return;
			}
		}

		if (cmd.hasOption(CommandLineOptions.SCC)) {
			try {
				PrintWriter pw;
				pw = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.SCC))));
				Collection<Mark> am = mp.getImmGroup().get(mp.getImmGroup().keySet().iterator().next()).getMarkSet();
				MarkClassAnalysis mca = new MarkClassAnalysis(mp, am);
				mca.dotMarkGroup(pw);
				pw.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.SCC));
				return;
			} catch (IOException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.SCC));
				return;
			}
		}

	}

}
