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
import jspetrinet.dist.Dist;
import jspetrinet.exception.JSPNException;
import jspetrinet.exception.JSPNExceptionType;
import jspetrinet.exception.TypeMismatch;
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
	private final MarkingGraph markGraph;

	private final Map<Mark,Mark> createdMarks;
	private final Set<MarkMarkTrans> createdArcs;
	protected final double[] genTransRemainingTime;
	protected final double[] genTransTimeInit;
	
	private final List<GenTrans> genTrans;
	private final List<GenTrans> genTransPRI;

	private final List<Trans> sortedImmTrans;

	private int count;
	private double time;
	private Random rnd;
	
	public MCSimulation2(MarkingGraph markGraph, Random rnd) {
		this.markGraph = markGraph;
		this.net = markGraph.getNet();
		this.rnd = rnd;

		createdMarks = new HashMap<Mark,Mark>();
		createdArcs = new HashSet<MarkMarkTrans>();

		genTransRemainingTime = new double [net.getGenTransSet().size()];
		genTransTimeInit = new double [net.getGenTransSet().size()];
		genTrans = new ArrayList<GenTrans>();
		genTransPRI = new ArrayList<GenTrans>();
		for (Trans tr : net.getGenTransSet()) {
			GenTrans gtr = (GenTrans) tr;
			if (gtr.getPolicy() == GenTransPolicy.PRI) {
				genTransPRI.add(gtr);
			} else {
				genTrans.add(gtr);
			}
		}

		sortedImmTrans = new ArrayList<Trans>(net.getImmTransSet());
		sortedImmTrans.sort(new PriorityComparator());
	}
	
	public void makeMarking() {
		for (Mark m : createdMarks.keySet()) {
			if (m.isIMM()) {
				setGenVecToImm(net, m.getGenVec(), m);				
			} else {
				setGenVecToGen(net, m.getGenVec(), m);
			}			
		}
		for (MarkMarkTrans mmt : createdArcs) {
			new MarkingArc(mmt.getSrc(), mmt.getDest(), mmt.getTrans());
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

	private void updateGenTransRemainingTime(GenVec genv, double elapsedTime) throws JSPNException{
		for (Trans tr : net.getGenTransSet()) {
			if (genv.get(tr.getIndex()) == 1) { // ENABLE
				genTransRemainingTime[tr.getIndex()] -= elapsedTime;				
			}
		}
	}
	
	private GenVec createGenVec(Net net) throws JSPNException {
		GenVec genv = new GenVec(net);
		for (Trans tr : net.getGenTransSet()) {
			switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
			case ENABLE:
				genv.set(tr.getIndex(), 1);
				break;
			case PREEMPTION:
				genv.set(tr.getIndex(), 2);
				break;
			default:
			}
		}
		return genv;
	}

	private List<ImmTrans> createEnabledIMM(Net net) throws JSPNException {
		List<ImmTrans> enabledIMMList = new ArrayList<ImmTrans>();
		int highestPriority = 0;
		for (Trans t : sortedImmTrans) {
			ImmTrans tr = (ImmTrans) t;
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

	private void setGenVecToImm(Net net, GenVec genv, Mark m) {
		if (!markGraph.getImmGroup().containsKey(genv)) {
			markGraph.getImmGroup().put(genv, new MarkGroup("Imm: " + JSPetriNet.genvecToString(net, genv)));
		}
		markGraph.addMark(m);
		markGraph.getImmGroup().get(genv).add(m);					
	}

	private void setGenVecToGen(Net net, GenVec genv, Mark m) {
		if (!markGraph.getGenGroup().containsKey(genv)) {
			markGraph.getGenGroup().put(genv, new MarkGroup("Gen: " + JSPetriNet.genvecToString(net, genv)));
		}
		markGraph.addMark(m);
		markGraph.getGenGroup().get(genv).add(m);
	}

	private Mark visitImmMark(Net net, List<ImmTrans> enabledIMMList, Mark m) throws JSPNException {
		ImmTrans selected = null;
		double totalWeight = 0.0;
		for (ImmTrans tr : enabledIMMList) {
			double weight = Utility.convertObjctToDouble(tr.getWeight().eval(net));
			if (weight >= rnd.nextUnif(0.0, weight + totalWeight)) {
				selected = tr;
			}
			totalWeight += weight;
		}
		if (selected != null) {
			Mark dest = PetriAnalysis.doFiring(net, selected);
			if (createdMarks.containsKey(dest)) {
				dest = createdMarks.get(dest);
			} else {
				createdMarks.put(dest, dest);
			}
			createdArcs.add(new MarkMarkTrans(m, dest, selected));
//			new MarkingArc(m, dest, selected);
			count++;
			return dest;
		} else {
			return null;
		}
	}

	private Mark visitGenMark(Net net, GenVec genv, Mark m) throws JSPNException {
		Trans selected = null;
		double minFiringTime = Double.MAX_VALUE;
		for (Trans tr : net.getGenTransSet()) {
			if (genv.get(tr.getIndex()) == 1) { // ENABLE
				if (genTransRemainingTime[tr.getIndex()] < minFiringTime) {
					selected = tr;
					minFiringTime = genTransRemainingTime[tr.getIndex()];
				}
			}
		}
		for (Trans tr : net.getExpTransSet()) {
			switch (PetriAnalysis.isEnable(net, tr)) {
			case ENABLE:
				double expftime = nextExpTime(net, (ExpTrans) tr);
				if (expftime < minFiringTime) {
					selected = tr;
					minFiringTime = expftime;
				}
				break;
			default:
			}
		}
		if (selected != null) {
			Mark dest = PetriAnalysis.doFiring(net, selected);
			if (createdMarks.containsKey(dest)) {
				dest = createdMarks.get(dest);
			} else {
				createdMarks.put(dest, dest);
			}
			createdArcs.add(new MarkMarkTrans(m, dest, selected));
//			new MarkingArc(m, dest, selected);
			updateGenTransRemainingTime(genv, minFiringTime);
			time += minFiringTime;
			count++;
			return dest;
		} else {
			return null;
		}
	}

	public List<EventValue> runSimulation(Mark init, double endTime, int limitFiring, AST stopCondition) throws JSPNException {
		List<EventValue> eventValues = new ArrayList<EventValue>();
		for (Trans tr: net.getGenTransSet()) {
			genTransRemainingTime[tr.getIndex()] = 0.0;
		}

		this.count = 0;
		this.time = 0.0;

		Mark m = init;
		if (createdMarks.containsKey(m)) {
			m = createdMarks.get(m);
		} else {
			createdMarks.put(m, m);
		}
		eventValues.add(new EventValue(m, time));

		while(true) {
			net.setCurrentMark(m);
			GenVec genv = createGenVec(net);
			m.setGroup(genv);
			
			// update remainingTime
			for (GenTrans tr : genTrans) { // except for PRI
				switch (genv.get(tr.getIndex())) {
				case 0: // DISABLE
					genTransRemainingTime[tr.getIndex()] = 0.0;
					break;
				case 1: // ENABLE
					if (genTransRemainingTime[tr.getIndex()] == 0.0) {
						genTransRemainingTime[tr.getIndex()] = this.nextGenTime(net, tr);
					}
					break;
				case 2: // PREEMPTION
					break;
				default:
				}
			}
			for (GenTrans tr : genTransPRI) { // for PRI
				switch (genv.get(tr.getIndex())) {
				case 0: // DISABLE
					genTransRemainingTime[tr.getIndex()] = 0.0;
					genTransTimeInit[tr.getIndex()] = 0.0;
					break;
				case 1: // ENABLE
					if (genTransRemainingTime[tr.getIndex()] == 0.0) {
						genTransRemainingTime[tr.getIndex()] = this.nextGenTime(net, tr);
						genTransTimeInit[tr.getIndex()] = genTransRemainingTime[tr.getIndex()];
					}
					break;
				case 2: // PREEMPTION
					genTransRemainingTime[tr.getIndex()] = genTransTimeInit[tr.getIndex()];
					break;
				default:
				}
			}

			// for IMM
			List<ImmTrans> enabledIMMList = createEnabledIMM(net);
			Mark next;
			if (enabledIMMList.size() > 0) {
				m.setIMM();
//				setGenVecToImm(net, genv, m);
				next = visitImmMark(net, enabledIMMList, m);
			} else {
				m.setGEN();
//				setGenVecToGen(net, genv, m);
				next = visitGenMark(net, genv, m);
			}

			// m is absorbing state
			if (next == null) {
				break;
			}

			// check stop conditions
			if (stopCondition != null) {
				Object obj = stopCondition.eval(net);
				if (obj instanceof Boolean) {
					if ((Boolean) obj) {
						break;
					}
				}
			}

			if (time > endTime) {
				break;
			}
			
			if (count > limitFiring) {
				break;
			}

			eventValues.add(new EventValue(next, time));
			m = next;
		}
		return eventValues;
	}
}
