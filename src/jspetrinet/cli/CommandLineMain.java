package jspetrinet.cli;
public class CommandLineMain {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.print("Require mode: " + CommandLineOptions.VIEW + ", " + CommandLineOptions.MARKING + ", " + CommandLineOptions.SIMULATION);
			System.err.println();
			return;
		}

		String mode = args[0];
		String[] newargs = new String [args.length-1];
		for (int i=1; i<args.length; i++) {
			newargs[i-1] = args[i];
		}

		if (mode.equals(CommandLineOptions.VIEW)) {
			CommandLineView.cmdView(newargs);
		} else if (mode.equals(CommandLineOptions.MARKING)) {
			CommandLineMark.cmdAnalysis(newargs);
		} else if (mode.equals(CommandLineOptions.SIMULATION)) {
			CommandLineSim.cmdSimulation(newargs);
		} else {
			System.err.print("Require mode: " + CommandLineOptions.VIEW + ", " + CommandLineOptions.MARKING + ", " + CommandLineOptions.SIMULATION);
			System.err.println();
			return;
		}
	}

}
