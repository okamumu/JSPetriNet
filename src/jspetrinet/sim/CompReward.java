package jspetrinet.sim;

import java.util.List;

import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.petri.Net;

public class CompReward {

	private static double evalReward(Net net, AST reward) throws JSPNException {
		return Utility.convertObjctToDouble(reward.eval(net));
	}

	public static double[] resultCumulativeReward(Net net, List<EventValue> simResult, AST reward, double startTime, double endTime) throws JSPNException {
		double[] totalReward = new double [2];
		totalReward[0] = 0;
		for (int i=0; i<simResult.size(); i++) {
			net.setCurrentMark(simResult.get(i).getEventMarking());
			double tmp = evalReward(net, reward);
			if (startTime <= simResult.get(i).getEventTime()) {
				if (i == simResult.size()-1) {
					totalReward[0] += (endTime - simResult.get(i).getEventTime()) * tmp;
					totalReward[1] = tmp;
					break;
				} else {
					if (endTime >= simResult.get(i+1).getEventTime()) {
						totalReward[0] += (simResult.get(i+1).getEventTime() - simResult.get(i).getEventTime()) * tmp;
					} else {
						totalReward[0] += (endTime - simResult.get(i).getEventTime()) * tmp;
						totalReward[1] = tmp;
						break;
					}
				}
			} else if (i == simResult.size()-1) {
				totalReward[0] += (endTime - startTime) * tmp;
				totalReward[1] = tmp;
			} else if (startTime <= simResult.get(i+1).getEventTime()) {
				totalReward[0] += (simResult.get(i+1).getEventTime() - startTime) * tmp;
			}
		}
		return totalReward;
	}

}
