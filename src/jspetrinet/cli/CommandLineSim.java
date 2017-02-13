package jspetrinet.cli;
import java.io.PrintWriter;
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
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.marking.Mark;
import jspetrinet.petri.Net;

import jspetrinet.sim.EventValue;
import jspetrinet.sim.MCSimulation;
import jspetrinet.sim.Utility;

public class CommandLineSim {

	public static void cmdSimulation(String[] args) {
		Options options = new Options();
		options.addOption(CommandLineOptions.INPETRI, true, "input PetriNet file");
		options.addOption(CommandLineOptions.INITMARK, true, "initial marking");
		options.addOption(CommandLineOptions.FIRINGLIMIT, true, "limit");
		options.addOption(CommandLineOptions.SEED, true, "seed");
		options.addOption(CommandLineOptions.SIMTIME, true, "time");
		options.addOption(CommandLineOptions.SIMRUN, true, "run");
		options.addOption(CommandLineOptions.REWARD, true, "reward");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net = CommandLineCommons.loadNet(cmd);

		MCSimulation mc = new MCSimulation(net);
		Mark imark = CommandLineCommons.getInitialMark(cmd, net);
		int limits = CommandLineCommons.getLimit(cmd, 1000);

		int run = 1;
		if (cmd.hasOption(CommandLineOptions.SIMRUN)) {
			run = Integer.parseInt(cmd.getOptionValue(CommandLineOptions.SIMRUN));
		}

		double endTime;
		if (cmd.hasOption(CommandLineOptions.SIMTIME)) {
			String label = "simtime" + System.currentTimeMillis();
			try {
				JSPetriNet.eval(net, label + " = " + cmd.getOptionValue(CommandLineOptions.SIMTIME) + ";");
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
		if (cmd.hasOption(CommandLineOptions.SEED)) {
			seed = Integer.valueOf(cmd.getOptionValue(CommandLineOptions.SEED));
		} else {
			seed = (int) System.currentTimeMillis();
		}

		Random rnd = new Random(seed);

		if (cmd.hasOption(CommandLineOptions.REWARD)) {
			String rewardLabel = cmd.getOptionValue(CommandLineOptions.REWARD);
			try {
				List<AST> rwdList = CommandLineCommons.parseReward(net, rewardLabel);
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
}
