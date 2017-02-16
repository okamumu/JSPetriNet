package jspetrinet.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Random rnd;

	private Map<Mark,Mark> createdMarks;
	protected final double[] genTransRemainingTime;
	protected final double[] genTransTimeInit;
//	protected final TransStatus[] genTransStatus;
	
	private final List<GenTrans> genTrans;
	private final List<GenTrans> genTransPRI;

	private final List<Trans> sortedImmTrans;
	
	public MCSimulation2(MarkingGraph markGraph) {
		this.markGraph = markGraph;
		net = markGraph.getNet();

		createdMarks = new HashMap<Mark,Mark>();

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

	private double nextExpTime(Net net, ExpTrans tr, Random rnd) throws JSPNException {
		try {
			double rate = Utility.convertObjctToDouble(tr.getRate().eval(net));
			return rnd.nextExp(rate);
		} catch (TypeMismatch e) {
			System.err.println("Did not get a rate of ExpTrans " + tr.getLabel() + " " + tr.getRate().eval(net));
			throw e;
		}
	}

	private double nextGenTime(Net net, GenTrans tr, Random rnd) throws JSPNException {
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
		Mark dest = PetriAnalysis.doFiring(net, selected);
		if (createdMarks.containsKey(dest)) {
			dest = createdMarks.get(dest);
		} else {
			createdMarks.put(dest, dest);
		}
		new MarkingArc(m, dest, selected);
		return dest;
	}

	public List<EventValue> runSimulation(Mark initMarking, double startTime, double endTime,
			int limitFiring, Random rnd) throws JSPNException {
		List<EventValue> eventValues = new ArrayList<EventValue>();
		for (Trans tr: net.getGenTransSet()) {
			genTransRemainingTime[tr.getIndex()] = 0.0;
		}

		int count = 0;
		double time = startTime;
		Mark m = initMarking;
		if (createdMarks.containsKey(m)) {
			m = createdMarks.get(m);
		} else {
			createdMarks.put(m, m);
		}
		eventValues.add(new EventValue(m, time));

		while (true) {
			
			if (count >= limitFiring) {
				break;
			}
			
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
						genTransRemainingTime[tr.getIndex()] = this.nextGenTime(net, tr, rnd);
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
						genTransRemainingTime[tr.getIndex()] = this.nextGenTime(net, tr, rnd);
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
			if (enabledIMMList.size() > 0) {
				m.setIMM();
				setGenVecToImm(net, genv, m);
				m = visitImmMark(net, enabledIMMList, m);
				count++;
			} else {
				m.setGEN();
				setGenVecToGen(net, genv, m);
				m = visitGenMark(net, m);
			}
			
				Trans selTrans = null;
				double mindt = 0;
				double totalWeight = 0;
				for (Trans tr : net.getExpTransSet()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = this.nextTime(net, (ExpTrans) tr, rnd);
						if(dt < mindt){
							mindt = dt;
							selTrans = tr;
						}
						break;
					default:
					}
				}
			}
			if(selTrans==null){
				//終了したことを記録
				break;
			}
			//一般発火トランジションの残り時間再セット
			updateRemainingTime(selTrans, mindt);
			time += mindt;
			if(time>endTime){
				break;
			}
			//発火処理
			Mark previousMarking = m;
			m = PetriAnalysis.doFiring(net, selTrans);
			if(!markSet.containsValue(m)){//発火先がmarkSetになければ追加
				markSet.put(m, m);
			}else{//発火先がmarkSetにある場合、
				m = markSet.get(m);
			}
			PairMark pairMark = new PairMark(previousMarking, m);
			if(!arcSet.containsValue(pairMark)){
				arcSet.put(pairMark, pairMark);
				new MarkingArc(previousMarking, m, selTrans);

			}
			eventValues.add(new EventValue(m, time));
			firingcount++;
		}
		return eventValues;
	}

	public List<EventValue> runSimulation(Mark initMarking, double startTime, double endTime, AST stopCondition, int limitFiring, Random rnd) throws JSPNException {
		List<EventValue> eventValues = new ArrayList<EventValue>();
		int firingcount = 0;
		double currentTime = startTime;
		Mark currentMarking = initMarking;
		if(!markSet.containsValue(currentMarking)){
			markSet.put(currentMarking, currentMarking);
		}else{
			currentMarking = markSet.get(currentMarking);
		}
		eventValues.add(new EventValue(initMarking, currentTime));
		while (true) {
			net.setCurrentMark(currentMarking);
			if (Utility.convertObjctToBoolean(stopCondition.eval(net))) {
				break;
			}
			if(firingcount>=limitFiring){
				//上限推移数で終了したことを伝える
				break;
			}
			for (Trans tr : net.getGenTransSet()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					if(genTransRemainingTime.get(tr)==0){//残り時間0とは前回がDISABLE,もしくは発火したことを示すので残り時間を初期化
						genTransRemainingTime.put(tr, this.nextTime(net, (GenTrans) tr, rnd));
					}//それ以外は残り時間継続
					break;
				case PREEMPTION:
					//ガードがあり、PolicyがPRSのため残り時間は減らない
					break;
				case DISABLE:
					genTransRemainingTime.put(tr, 0.0);
					break;
				default:
				}
			}
			Trans selTrans = null;
			double totalWeight = 0;
			for (Trans tr : net.getImmTransSet()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					double weight = Utility.convertObjctToDouble(((ImmTrans)tr).getWeight().eval(net));
					if(weight>=(rnd.nextUnif()*(weight+totalWeight))){
						selTrans = tr;
					}
					totalWeight += weight;
					break;
				default:
				}
			}
			double mindt = Double.POSITIVE_INFINITY;
			if(totalWeight==0){
				for (Trans tr : net.getGenTransSet()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = genTransRemainingTime.get(tr);
						if(dt < mindt){
							mindt = dt;
							selTrans = tr;
						}
						break;
					default:
					}
				}
				for (Trans tr : net.getExpTransSet()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = this.nextTime(net, (ExpTrans) tr, rnd);
						if(dt < mindt){
							mindt = dt;
							selTrans = tr;
						}
						break;
					default:
					}
				}
			}
			if(selTrans==null){
				//終了したことを記録
				break;
			}
			//一般発火トランジションの残り時間再セット
			updateRemainingTime(selTrans, mindt);
			currentTime += mindt;
			if(currentTime>endTime){
				break;
			}
			//発火処理
			Mark previousMarking = currentMarking;
			currentMarking = PetriAnalysis.doFiring(net, selTrans);
			if(!markSet.containsValue(currentMarking)){//発火先がmarkSetになければ追加
				markSet.put(currentMarking, currentMarking);
			}else{//発火先がmarkSetにある場合、
				currentMarking = markSet.get(currentMarking);
			}
			PairMark pairMark = new PairMark(previousMarking, currentMarking);
			if(!arcSet.containsValue(pairMark)){
				arcSet.put(pairMark, pairMark);
				new MarkingArc(previousMarking, currentMarking, selTrans);

			}
			eventValues.add(new EventValue(currentMarking, currentTime));
			firingcount++;
		}
		return eventValues;
	}

	public void resultEvent(PrintWriter pw, List<EventValue> eventValues){
		for (EventValue ev : eventValues) {
			pw.print(String.format("%.2f", ev.getEventTime())+" : ");
			pw.println(JSPetriNet.markToString(net, ev.getEventMarking()));
		}
	}
}
