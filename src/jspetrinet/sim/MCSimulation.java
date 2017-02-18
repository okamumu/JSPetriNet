package jspetrinet.sim;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.rel.jmtrandom.Random;
import jspetrinet.JSPetriNet;
import jspetrinet.ast.AST;
import jspetrinet.common.Utility;
import jspetrinet.dist.Dist;
import jspetrinet.exception.JSPNException;
import jspetrinet.exception.JSPNExceptionType;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkMarkTrans;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.PetriAnalysis;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class MCSimulation {

	protected Net net;
	protected Random rnd;
	protected final Map<Mark, Mark> markSet;//結果表示用に通ったマーキングを保存
	protected final Map<MarkMarkTrans, MarkMarkTrans> arcSet;
	protected final Map<Trans, Double> remainingTime;//一般発火トランジションの残り時間

	public MCSimulation(Net net) {
		this.net = net;
		markSet = new HashMap<Mark, Mark>();
		arcSet = new HashMap<MarkMarkTrans, MarkMarkTrans>();
		remainingTime = new HashMap<Trans, Double>();
		for (Trans tr : net.getGenTransSet()) {
			remainingTime.put(tr, 0.0);
		}
	}

	private double nextTime(Net net, ExpTrans tr, Random rnd) throws JSPNException {
		try {
			double rate = Utility.convertObjctToDouble(tr.getRate().eval(net));
			return rnd.nextExp(rate);
		} catch (TypeMismatch e) {
			System.err.println("Did not get a rate of ExpTrans " + tr.getLabel() + " " + tr.getRate().eval(net));
			throw e;
		}
	}

	private double nextTime(Net net, GenTrans tr, Random rnd) throws JSPNException {
		Object v = tr.getDist().eval(net);
		if (v instanceof Dist) {
			return ((Dist) v).next(net, rnd);
		} else {
			throw new JSPNException(JSPNExceptionType.TYPE_MISMATCH, "Gen " + tr.getLabel() + " was not set as an object of Dist. Please check the 'dist' attribute of " + tr.getLabel());
		}
	}

	private void updateRemainingTime(Trans selTrans, double elapsedTime) throws JSPNException{
		for (Trans tr : net.getGenTransSet()) {
			switch (PetriAnalysis.isEnable(net, tr)) {
			case ENABLE:
				if(tr.equals(selTrans)){//発火したことがわかるよう残り時間を0に
					remainingTime.put(tr, 0.0);
				}else{//発火可能だったものの発火しなかったトランジションは残り時間を更新
					remainingTime.put(tr, remainingTime.get(tr)-elapsedTime);
				}
				break;
			default:
			}
		}
	}

	public List<EventValue> runSimulation(Mark initMarking, double startTime, double endTime, int limitFiring, Random rnd) throws JSPNException {
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
			if(firingcount>=limitFiring){
				//上限推移数で終了したことを伝える
				break;
			}
			for (Trans tr : net.getGenTransSet()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					if(remainingTime.get(tr)==0){//残り時間0とは前回がDISABLE,もしくは発火したことを示すので残り時間を初期化
						remainingTime.put(tr, this.nextTime(net, (GenTrans) tr, rnd));
					}//それ以外は残り時間継続
					break;
				case PREEMPTION:
					//ガードがあり、PolicyがPRSのため残り時間は減らない
					break;
				case DISABLE:
					remainingTime.put(tr, 0.0);
					break;
				default:
				}
			}
			Trans selTrans = null;
			double mindt = 0;
			double totalWeight = 0;
			for (ImmTrans tr : net.getImmTransSet()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					double weight = Utility.convertObjctToDouble(tr.getWeight().eval(net));
					if(weight>=(rnd.nextUnif()*(weight+totalWeight))){
						selTrans = tr;
					}
					totalWeight += weight;
					break;
				default:
				}
			}
			if(totalWeight==0){
				mindt = Double.POSITIVE_INFINITY;
				for (GenTrans tr : net.getGenTransSet()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = remainingTime.get(tr);
						if(dt < mindt){
							mindt = dt;
							selTrans = tr;
						}
						break;
					default:
					}
				}
				for (ExpTrans tr : net.getExpTransSet()) {
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
 			MarkMarkTrans pairMark = new MarkMarkTrans(previousMarking, currentMarking, null);
			if(!arcSet.containsValue(pairMark)){
				arcSet.put(pairMark, pairMark);
				new MarkingArc(previousMarking, currentMarking, selTrans);

			}
			eventValues.add(new EventValue(currentMarking, currentTime));
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
					if(remainingTime.get(tr)==0){//残り時間0とは前回がDISABLE,もしくは発火したことを示すので残り時間を初期化
						remainingTime.put(tr, this.nextTime(net, (GenTrans) tr, rnd));
					}//それ以外は残り時間継続
					break;
				case PREEMPTION:
					//ガードがあり、PolicyがPRSのため残り時間は減らない
					break;
				case DISABLE:
					remainingTime.put(tr, 0.0);
					break;
				default:
				}
			}
			Trans selTrans = null;
			double totalWeight = 0;
			for (ImmTrans tr : net.getImmTransSet()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					double weight = Utility.convertObjctToDouble(tr.getWeight().eval(net));
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
						double dt = remainingTime.get(tr);
						if(dt < mindt){
							mindt = dt;
							selTrans = tr;
						}
						break;
					default:
					}
				}
				for (ExpTrans tr : net.getExpTransSet()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = this.nextTime(net, tr, rnd);
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
 			MarkMarkTrans pairMark = new MarkMarkTrans(previousMarking, currentMarking, null);
			if(!arcSet.containsValue(pairMark)){
				arcSet.put(pairMark, pairMark);
				new MarkingArc(previousMarking, currentMarking, selTrans);

			}
			eventValues.add(new EventValue(currentMarking, currentTime));
			firingcount++;
		}
		return eventValues;
	}

	private double evalReward(Net net, AST reward) throws JSPNException {
		return Utility.convertObjctToDouble(reward.eval(net));
	}

	public double[] resultCumulativeReward(Net net, List<EventValue> simResult, AST reward, double startTime, double endTime) throws JSPNException {
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

	public void resultEvent(PrintWriter pw, List<EventValue> eventValues){
		for (EventValue ev : eventValues) {
			pw.print(String.format("%.2f", ev.getEventTime())+" : ");
			pw.println(JSPetriNet.markToString(net, ev.getEventMarking()));
		}
	}
}
