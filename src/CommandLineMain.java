import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
	public static String INPETRI="i";
	public static String OUTPETRI="o";
	public static String INITMARK="imark";
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

		Mark imark;
		if (cmd.hasOption(Opts.INITMARK)) {
			imark = JSPetriNet.mark(net, parseMark(cmd.getOptionValue(Opts.INITMARK)));
			System.out.println("Initial marking: " + JSPetriNet.markToString(net, imark));
		} else {
			System.err.println("Marking process requires an initial marking (-" + Opts.INITMARK + ").");
			return;
		}
		
		MarkingProcess mp = JSPetriNet.marking(net, imark);
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
