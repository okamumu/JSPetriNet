package jspetrinet.sim;

import java.util.List;

import jspetrinet.ast.AST;
import jspetrinet.common.Utility;
import jspetrinet.exception.JSPNException;
import jspetrinet.petri.Net;

public class CompReward {

	private static double evalReward(Net net, AST reward) throws JSPNException {
		return Utility.convertObjctToDouble(reward.eval(net));
	}

	public static double[] resultCumulativeReward(Net net, List<EventValue> simResult, AST reward, double endTime) throws JSPNException {
		double totalReward = 0.0;
		double lastReward = Double.NaN;
		double time = 0.0;
		double rwd = 0.0;
		for (EventValue ev : simResult) {
			if (ev.getTime() <= endTime) {
				totalReward += rwd * (ev.getTime() - time);
				time = ev.getTime();
			} else {
				totalReward += rwd * (endTime - time);
				time = endTime;
				lastReward = rwd;
				break;
			}
			if (ev.isStop()) {
				break;
			}
			net.setCurrentMark(ev.getEvent());
			rwd = evalReward(net, reward);
		}
		return new double[] {lastReward, totalReward, time};
	}
}
