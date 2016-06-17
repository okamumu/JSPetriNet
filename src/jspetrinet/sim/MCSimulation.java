package jspetrinet.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.PetriAnalysis;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class MCSimulation {
	
	protected Net net;
	protected Random rnd;
	protected final ArrayList<EventValue> eventValues;
	protected final Map<Mark, Mark> markSet;//結果表示用に通ったマーキングを保存
	protected final Map<PairMark, PairMark> arcSet;
	protected final Map<Trans, Double> remainingTime;//一般発火トランジションの残り時間
	
	public MCSimulation(Net net) throws ASTException {
		eventValues = new ArrayList<EventValue>();
		markSet = new HashMap<Mark, Mark>();
		arcSet = new HashMap<PairMark, PairMark>();
		remainingTime = new HashMap<Trans, Double>();
		for (Trans tr : net.getGenTransSet().values()) {
			remainingTime.put(tr, 0.0);
		}
	}
	
	private void updateRemainingTime(Trans selTrans, double elapsedTime) throws ASTException{
		for (Trans tr : net.getGenTransSet().values()) {
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
	
	public ArrayList<EventValue> runSimulation(Mark initMarking, Net net, double t, int seed) throws ASTException {
		this.net = net;
		rnd = new RandomGenerator(seed);
		double currentTime = 0;
		Mark currentMarking = initMarking;
		
		markSet.put(initMarking, initMarking);
		eventValues.add(new EventValue(initMarking, currentTime));
		while (true) {			
			Mark m = currentMarking;
			net.setCurrentMark(m);
			
			for (Trans tr : net.getGenTransSet().values()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					if(remainingTime.get(tr)==0){//残り時間0とは前回がDISABLE,もしくは発火したことを示すので残り時間を初期化
						//remainingTime.put(tr, ((SimGenTrans)tr).nextTime(net, rnd));
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
			for (Trans tr : net.getImmTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					double weight = Utility.convertObjctToDouble(((ImmTrans)tr).getWeight().eval(net));
					if(weight>=(rnd.nextUnif01()*(weight+totalWeight))){
						selTrans = tr;
					}
					totalWeight += weight;
					break;
				default:
				}
			}
			double mindt = Double.POSITIVE_INFINITY;
			if(totalWeight==0){
				for (Trans tr : net.getGenTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = ((SimGenTrans)tr).nextTime(net, rnd);
						if(dt < mindt){
							mindt = dt;
							selTrans = tr;
						}
						break;
					default:
					}
				}
				for (Trans tr : net.getExpTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = ((SimExpTrans)tr).nextTime(net, rnd);
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
				break;
			}
			//一般発火トランジションの残り時間再セット
			updateRemainingTime(selTrans, mindt);
			currentTime += mindt;
			if(currentTime>t){
				break;
			}
			//発火処理			
			currentMarking = PetriAnalysis.doFiring(net, selTrans);
			if(!markSet.containsValue(currentMarking)){//発火先がmarkSetになければ追加
				markSet.put(currentMarking, currentMarking);
			}else{//発火先がmarkSetにある場合、
				currentMarking = markSet.get(currentMarking);
			}
 			PairMark pairMark = new PairMark(m, currentMarking);
			if(!arcSet.containsValue(pairMark)){
				arcSet.put(pairMark, pairMark);
				new MarkingArc(m, currentMarking, selTrans);

			}
			eventValues.add(new EventValue(currentMarking, currentTime));
		}
		resultEvent();
		resultMarking();
		return eventValues;
	}
	
	public ArrayList<EventValue> runSimulation(Mark initMarking, Net net, Mark finalMarking, int seed) throws ASTException {
		this.net = net;
		rnd = new RandomGenerator(seed);
		double currentTime = 0;
		Mark currentMarking = initMarking;
		
		markSet.put(initMarking, initMarking);
		eventValues.add(new EventValue(initMarking, currentTime));
		while (true) {			
			if(currentMarking.equals(finalMarking)){
				break;
			}
			Mark m = currentMarking;
			net.setCurrentMark(m);
			
			for (Trans tr : net.getGenTransSet().values()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					if(remainingTime.get(tr)==0){//残り時間0とは前回がDISABLE,もしくは発火したことを示すので残り時間を初期化
						//remainingTime.put(tr, ((SimGenTrans)tr).nextTime(net, rnd));
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
			for (Trans tr : net.getImmTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					double weight = Utility.convertObjctToDouble(((ImmTrans)tr).getWeight().eval(net));
					if(weight>=(rnd.nextUnif01()*(weight+totalWeight))){
						selTrans = tr;
					}
					totalWeight += weight;
					break;
				default:
				}
			}
			double mindt = Double.POSITIVE_INFINITY;
			if(totalWeight==0){
				for (Trans tr : net.getGenTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = ((SimGenTrans)tr).nextTime(net, rnd);
						if(dt < mindt){
							mindt = dt;
							selTrans = tr;
						}
						break;
					default:
					}
				}
				for (Trans tr : net.getExpTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = ((SimExpTrans)tr).nextTime(net, rnd);
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
				break;
			}
			//一般発火トランジションの残り時間再セット
			updateRemainingTime(selTrans, mindt);
			currentTime += mindt;
			//発火処理			
			currentMarking = PetriAnalysis.doFiring(net, selTrans);
			if(!markSet.containsValue(currentMarking)){//発火先がmarkSetになければ追加
				markSet.put(currentMarking, currentMarking);
			}else{//発火先がmarkSetにある場合、
				currentMarking = markSet.get(currentMarking);
			}
 			PairMark pairMark = new PairMark(m, currentMarking);
			if(!arcSet.containsValue(pairMark)){
				arcSet.put(pairMark, pairMark);
				new MarkingArc(m, currentMarking, selTrans);
			}
			eventValues.add(new EventValue(currentMarking, currentTime));
			if(currentMarking.equals(finalMarking)){
				break;
			}
		}
		resultEvent();
		resultMarking();
		return eventValues;
	}
	
	public void resultEvent(){
		for(int i=0;i<eventValues.size();i++){
			System.out.print(String.format("%.2f", eventValues.get(i).getEventTime())+" : ");
			for(int j=0;j<net.getNumOfPlace();j++){
				System.out.print(eventValues.get(i).getEventMarking().get(j));
			}
			System.out.println("");
		}
	}
	
	public void resultMarking(){
		for(Mark mark : markSet.keySet()){
			for(int i=0;i<net.getNumOfPlace();i++){
				System.out.print(mark.get(i));
			}
			System.out.println("");
		}
	}
}
