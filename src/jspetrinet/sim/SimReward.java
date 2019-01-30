package jspetrinet.sim;

import java.io.PrintWriter;
import java.util.List;

import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.petri.Net;

class SimValue {
	private int count;
	private double value;
	private double value2;
	
	public SimValue() {
		count = 0;
		value = 0.0;
		value2 = 0.0;
	}
	
	public void add(double x) {
		value += x;
		value2 += x * x;
		count++;
	}
	
	public double[] get() {
		double mean = value / count;
		double s2 = (value2 - value * value / count) / (count - 1);
		double lcl = mean - 1.96 * Math.sqrt(s2 / count);
		double ucl = mean + 1.96 * Math.sqrt(s2 / count);
		return new double[] {mean, s2, lcl, ucl};
	}
}

public class SimReward {

	private final Net net;
	private final AST reward;
	private SimValue icount;
	private SimValue ccount;
	private SimValue tcount;
	
	public SimReward(Net net, AST reward) {
		this.net = net;
		this.reward = reward;
		this.icount = new SimValue();
		this.ccount = new SimValue();
		this.tcount = new SimValue();
	}
	
	public void add(List<EventValue> event, double endTime) throws JSPNException {
		double[] tmp = CompReward.resultCumulativeReward(net, event, reward, endTime);
		icount.add(tmp[0]);
		ccount.add(tmp[1]);
		tcount.add(tmp[1]/tmp[2]);
	}
	
	public double[] getI() {
		return icount.get();
	}

	public double[] getC() {
		return ccount.get();
	}

	public double[] getT() {
		return tcount.get();
	}
	
	public void print(PrintWriter pw) {
		double[] resi = this.getI();
		double[] resc = this.getC();
		double[] rest = this.getT();
		pw.println(reward.toString());
		pw.println(resi[0] + " [" + resi[2] + "," + resi[3] + "]");
		pw.println(resc[0] + " [" + resc[2] + "," + resc[3] + "]");
		pw.println(rest[0] + " [" + rest[2] + "," + rest[3] + "]");
	}
}
