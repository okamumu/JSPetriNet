package jspetrinet.cli;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import jp.rel.jmtrandom.Random;
import jspetrinet.JSPetriNet;
import jspetrinet.analysis.MarkingMatrix;
import jspetrinet.ast.AST;
import jspetrinet.common.Utility;
import jspetrinet.exception.JSPNException;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.Net;
import jspetrinet.sim.CompReward;
import jspetrinet.sim.EventValue;
import jspetrinet.sim.MCSimulation;
import jspetrinet.sim.MCSimCreateMarking;
import jspetrinet.sim.SimReward;

public class CommandLineSimMark {
	
	private static void outRewardText(CommandLine cmd, List<SimReward> simresult) {
		if (cmd.hasOption(CommandLineOptions.OUT)) {
			try {
				PrintWriter pw1;
				pw1 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.OUT) + ".sim")));
				for (SimReward rwd : simresult) {
					rwd.print(pw1);
				}
				pw1.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.OUT));
				e.printStackTrace();
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.OUT));
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			PrintWriter pw1;
			pw1 = new PrintWriter(System.out);
			for (SimReward rwd : simresult) {
				rwd.print(pw1);
			}
			pw1.flush();
		}
	}
	
//	private static void outPathText(CommandLine cmd, Net net, List<EventValue> result) {
//		if (cmd.hasOption(CommandLineOptions.OUT)) {
//			try {
//				PrintWriter pw1;
//				pw1 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.OUT) + ".sim")));
//				pw1.println();
//				for (EventValue ev : result) {
//					pw1.print(ev.getTime());
//					pw1.print(" ");
//					pw1.print(ev.getEvent());
//					pw1.println(" # " + JSPetriNet.markToString(net, ev.getEvent()));
//				}
//				pw1.close();
//			} catch (FileNotFoundException e) {
//				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.OUT));
//				e.printStackTrace();
//				System.exit(1);
//			} catch (IOException e) {
//				System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.OUT));
//				e.printStackTrace();
//				System.exit(1);
//			}
//		} else {
//			PrintWriter pw1;
//			pw1 = new PrintWriter(System.out);
//			pw1.println();
//			for (EventValue ev : result) {
//				pw1.print(ev.getTime());
//				pw1.print(" ");
//				pw1.print(ev.getEvent());
//				pw1.println(" # " + JSPetriNet.markToString(net, ev.getEvent()));
//			}
//			pw1.flush();
//		}
//	}

	public static void cmdSimulation(String[] args) {
		Options options = new Options();
		options.addOption(CommandLineOptions.INPETRI, true, "input PetriNet file");
		options.addOption(CommandLineOptions.INITMARK, true, "initial marking");
		options.addOption(CommandLineOptions.FIRINGLIMIT, true, "limit");
		options.addOption(CommandLineOptions.SEED, true, "seed");
		options.addOption(CommandLineOptions.SIMTIME, true, "time");
		options.addOption(CommandLineOptions.SIMRUN, true, "run");
		options.addOption(CommandLineOptions.REWARD, true, "reward");
		options.addOption(CommandLineOptions.OUT, true, "matrix (output)");
		options.addOption(CommandLineOptions.TEXT, false, "TEXT mat file");
		options.addOption(CommandLineOptions.MATLAB, false, "MATLAB mat file");
		options.addOption(CommandLineOptions.GROUPGRAPH, true, "marking group graph (output)");
		options.addOption(CommandLineOptions.MARKGRAPH, true, "marking graph (output)");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		CommandLineCommons.checkOptionMark(cmd);

		Net net = CommandLineCommons.loadNet(cmd);
		Mark imark = CommandLineCommons.getInitialMark(cmd, net);
		int limits = CommandLineCommons.getLimit(cmd, 1000);
		int run = CommandLineCommons.getRun(cmd, 1);
		long seed = CommandLineCommons.getSeed(cmd);

		double endTime;
		if (cmd.hasOption(CommandLineOptions.SIMTIME)) {
			try {
				endTime = CommandLineCommons.getTime(cmd, net);
			} catch (JSPNException e) {
				System.err.println("Cannot get value of option (-time).");
				return;
			}
		} else {
			System.err.println("Time (-time) should be set for the simulation.");
			return;
		}

		MarkingGraph mp = new MarkingGraph(imark);
		MCSimCreateMarking mc = new MCSimCreateMarking(net, new Random(seed));

		PrintWriter pw0 = new PrintWriter(System.out);
		pw0.print("Start simulation...");
		long start = System.nanoTime();
		try {
			if (cmd.hasOption(CommandLineOptions.REWARD)) {
				String rewardLabel = cmd.getOptionValue(CommandLineOptions.REWARD);
				List<AST> rwdList = CommandLineCommons.parseReward(net, rewardLabel);
				List<SimReward> simresult = new ArrayList<SimReward>();
				for (AST rwd : rwdList) {
					simresult.add(new SimReward(net, rwd));
				}
				for (int k=0; k<run; k++) {
					List<EventValue> result = mc.runSimulation(imark, endTime, limits, null);
					for (SimReward rwd : simresult) {
						rwd.add(result, endTime);
					}
				}
				outRewardText(cmd, simresult);
			} else {
				if (cmd.hasOption(CommandLineOptions.OUT)) {
//					try {
//						PrintWriter pw1;
//						pw1 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(CommandLineOptions.OUT) + ".sim")));
						for (int k=0; k<run; k++) {
							List<EventValue> result = mc.runSimulation(imark, endTime, limits, null);
//							pw1.println();
//							for (EventValue ev : result) {
//								pw1.print(ev.getTime());
//								pw1.print(" ");
//								pw1.print(ev.getEvent());
//								pw1.println(" # " + JSPetriNet.markToString(net, ev.getEvent()));
//							}
						}
//						pw1.close();
//					} catch (FileNotFoundException e) {
//						System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.OUT));
//						e.printStackTrace();
//						System.exit(1);
//					} catch (IOException e) {
//						System.err.println("Error: Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.OUT));
//						e.printStackTrace();
//						System.exit(1);
//					}
				} else {
//					PrintWriter pw1;
//					pw1 = new PrintWriter(System.out);
					for (int k=0; k<run; k++) {
						List<EventValue> result = mc.runSimulation(imark, endTime, limits, null);
//						pw1.println();
//						for (EventValue ev : result) {
//							pw1.print(ev.getTime());
//							pw1.print(" ");
//							pw1.print(ev.getEvent());
//							pw1.println(" # " + JSPetriNet.markToString(net, ev.getEvent()));
//						}
					}
//					pw1.flush();
				}
			}
		} catch (JSPNException e) {
			System.err.println("Simulation Failed: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		mc.makeMarking(mp);
		pw0.println("done");
		pw0.println("computation time    : " + (System.nanoTime() - start) / 1000000000.0 + " (sec)");
		pw0.println(JSPetriNet.markingToString(net, mp));
		pw0.flush();

		MarkingMatrix mmat;
		if (cmd.hasOption(CommandLineOptions.MATLAB)) {
			mmat = CommandLineMark.outputBin(cmd, net, mp);
		} else {
			mmat = CommandLineMark.outputText(cmd, net, mp); // default -text
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
	}

}
