package jspetrinet.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.rel.jmtrandom.Random;
import jspetrinet.JSPetriNet;
import jspetrinet.ast.AST;
import jspetrinet.common.Utility;
import jspetrinet.dist.Dist;
import jspetrinet.exception.JSPNException;
import jspetrinet.exception.JSPNExceptionType;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.marking.CreateGroupMarkingGraph;
import jspetrinet.marking.GenVec;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkGroup;
import jspetrinet.marking.MarkMarkTrans;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.marking.PetriAnalysis;
import jspetrinet.marking.TransStatus;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.GenTransPolicy;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.PriorityComparator;
import jspetrinet.petri.Trans;

public class MCSimulation2 {

	private final Net net;
	protected final double[] genTransRemainingTime;
	protected final double[] genTransTimeInit;
	protected final int[] genTransState;
	
	private final List<GenTrans> genTrans;
	private final List<GenTrans> genTransPRI;

	private int count;
	private double time;
	private Random rnd;
	
	public MCSimulation2(Net net, Random rnd) {
		this.net = net;
		this.rnd = rnd;
		genTransRemainingTime = new double [net.getGenTransSet().size()];
		genTransTimeInit = new double [net.getGenTransSet().size()];
		genTransState = new int [net.getGenTransSet().size()];
		genTrans = new ArrayList<GenTrans>();
		genTransPRI = new ArrayList<GenTrans>();
		for (GenTrans tr : net.getGenTransSet()) {
			if (tr.getPolicy() == GenTransPolicy.PRI) {
				genTransPRI.add(tr);
			} else {
				genTrans.add(tr);
			}
		}
	}
	
	private double nextExpTime(Net net, ExpTrans tr) throws JSPNException {
		try {
			double rate = Utility.convertObjctToDouble(tr.getRate().eval(net));
			return rnd.nextExp(rate);
		} catch (TypeMismatch e) {
			System.err.println("Did not get a rate of ExpTrans " + tr.getLabel() + " " + tr.getRate().eval(net));
			throw e;
		}
	}

	private double nextGenTime(Net net, GenTrans tr) throws JSPNException {
		Object v = tr.getDist().eval(net);
		if (v instanceof Dist) {
			return ((Dist) v).next(net, rnd);
		} else {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Gen " + tr.getLabel() + " was not set as an object of Dist. Please check the 'dist' attribute of " + tr.getLabel());
		}
	}

	private List<ImmTrans> createEnabledIMM(Net net) throws JSPNException {
		List<ImmTrans> enabledIMMList = new ArrayList<ImmTrans>();
		int highestPriority = 0;
		for (ImmTrans tr : net.getImmTransSet()) {
			if (highestPriority > tr.getPriority()) {
				break;
			}
			switch (PetriAnalysis.isEnable(net, tr)) {
			case ENABLE:
				highestPriority = tr.getPriority();
				enabledIMMList.add(tr);
				break;
			default:
			}
		}
		return enabledIMMList;
	}

	public List<EventValue> runSimulation(Mark init, double endTime, int limitFiring, AST stopCondition) throws JSPNException {
		List<EventValue> eventValues = new ArrayList<EventValue>();
		for (GenTrans tr: net.getGenTransSet()) {
			genTransRemainingTime[tr.getIndex()] = 0.0;
		}

		this.count = 0;
		this.time = 0.0;

		Mark m = init;
		while(true) {
			net.setCurrentMark(m);
			
			// check stop conditions
			if (time > endTime) {
				eventValues.add(new EventValue(time, m, true));				
				break;
			} else if (limitFiring > 0 && count > limitFiring) {
				eventValues.add(new EventValue(time, m, true));				
				break;
			} else if (stopCondition != null) {
				Object obj = stopCondition.eval(net);
				if (obj instanceof Boolean) {
					if ((Boolean) obj) {
						eventValues.add(new EventValue(time, m, true));				
						break;
					}
				}
			} else {
				eventValues.add(new EventValue(time, m));				
			}


			// update remainingTime
			for (GenTrans tr : genTrans) { // except for PRI
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case DISABLE:
					genTransRemainingTime[tr.getIndex()] = 0.0;
					this.genTransState[tr.getIndex()] = 0;
					break;
				case ENABLE:
					if (genTransRemainingTime[tr.getIndex()] == 0.0) {
						genTransRemainingTime[tr.getIndex()] = this.nextGenTime(net, tr);
					}
					this.genTransState[tr.getIndex()] = 1;
					break;
				case PREEMPTION:
					this.genTransState[tr.getIndex()] = 2;
					break;
				default:
				}
			}
			for (GenTrans tr : genTransPRI) { // for PRI
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case DISABLE:
					genTransRemainingTime[tr.getIndex()] = 0.0;
					genTransTimeInit[tr.getIndex()] = 0.0;
					this.genTransState[tr.getIndex()] = 0;
					break;
				case ENABLE:
					if (genTransRemainingTime[tr.getIndex()] == 0.0) {
						genTransRemainingTime[tr.getIndex()] = this.nextGenTime(net, tr);
						genTransTimeInit[tr.getIndex()] = genTransRemainingTime[tr.getIndex()];
					}
					this.genTransState[tr.getIndex()] = 2;
					break;
				case PREEMPTION:
					genTransRemainingTime[tr.getIndex()] = genTransTimeInit[tr.getIndex()];
					this.genTransState[tr.getIndex()] = 2;
					break;
				default:
				}
			}

			// for IMM
			List<ImmTrans> enabledIMMList = createEnabledIMM(net);
			Trans selected = null;
			double minFiringTime = 0.0;
			if (enabledIMMList.size() > 0) {
				double totalWeight = 0.0;
				for (ImmTrans tr : enabledIMMList) {
					double weight = Utility.convertObjctToDouble(tr.getWeight().eval(net));
					if (weight >= rnd.nextUnif(0.0, weight + totalWeight)) {
						selected = tr;
					}
					totalWeight += weight;
				}
			} else {
				minFiringTime = Double.MAX_VALUE;
				for (GenTrans tr : net.getGenTransSet()) {
					if (m.getGenVec().get(tr.getIndex()) == 1) { // ENABLE
						if (genTransRemainingTime[tr.getIndex()] < minFiringTime) {
							selected = tr;
							minFiringTime = genTransRemainingTime[tr.getIndex()];
						}
					}
				}
				for (ExpTrans tr : net.getExpTransSet()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double expftime = nextExpTime(net, tr);
						if (expftime < minFiringTime) {
							selected = tr;
							minFiringTime = expftime;
						}
						break;
					default:
					}
				}
			}

			// m is absorbing state
			if (selected == null) {
				break;
			}

			Mark next = PetriAnalysis.doFiring(net, selected);
			for (GenTrans tr : net.getGenTransSet()) {
				if (this.genTransState[tr.getIndex()] == 1) { // ENABLE
					genTransRemainingTime[tr.getIndex()] -= minFiringTime;				
				}
			}
			time += minFiringTime;
			count++;
			m = next;
		}
		return eventValues;
	}
}
