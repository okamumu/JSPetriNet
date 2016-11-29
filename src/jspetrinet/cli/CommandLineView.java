package jspetrinet.cli;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import jspetrinet.JSPetriNet;
import jspetrinet.petri.Net;

public class CommandLineView {

	public static void cmdView(String[] args) {
		Options options = new Options();
		options.addOption(CommandLineOptions.INPETRI, true, "input Petrinet file");
		options.addOption(CommandLineOptions.OUT, true, "output file");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net = CommandLineCommons.loadNet(cmd);

		PrintWriter bw;
		if (cmd.hasOption(CommandLineOptions.OUT)) {
			try {
				bw = new PrintWriter(cmd.getOptionValue(CommandLineOptions.OUT));
			} catch (FileNotFoundException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(CommandLineOptions.OUT));
				return;
			}
		} else {
			bw = new PrintWriter(System.out);
		}
		JSPetriNet.writeDotfile(bw, net);
		bw.close();
	}

}
