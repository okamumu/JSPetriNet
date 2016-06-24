package jspetrinet.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jspetrinet.ast.ASTree;
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
	protected final Map<Mark, Mark> markSet;//結果表示用に通ったマーキングを保存
	protected final Map<PairMark, PairMark> arcSet;
	protected final Map<Trans, Double> remainingTime;//一般発火トランジションの残り時間
	
	public MCSimulation(Net net) throws ASTException {
		this.net = net;
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
	
	public ArrayList<EventValue> runSimulation(Mark initMarking, double startTime, double endTime, int limitFiring, int seed) throws ASTException {
		ArrayList<EventValue> eventValues = new ArrayList<EventValue>();
		rnd = new RandomGenerator(seed);
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
			for (Trans tr : net.getGenTransSet().values()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					if(remainingTime.get(tr)==0){//残り時間0とは前回がDISABLE,もしくは発火したことを示すので残り時間を初期化
						remainingTime.put(tr, ((SimGenTrans)tr).nextTime(net, rnd));
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
						double dt = remainingTime.get(tr);
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
		resultEvent(eventValues);
		resultMarking();
		return eventValues;
	}
	
	public boolean canStop(ASTree stopCondition) throws ASTException{
		if(Utility.convertObjctToDouble(stopCondition.eval(net))==1){
			return true;
		}else{
			return false;
		}
	}
	
	public ArrayList<EventValue> runSimulation(Mark initMarking, double startTime, double endTime, ASTree stopCondition, int limitFiring, int seed) throws ASTException {
		ArrayList<EventValue> eventValues = new ArrayList<EventValue>();
		rnd = new RandomGenerator(seed);
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
			if(canStop(stopCondition)){
				break;
			}
			if(firingcount>=limitFiring){
				//上限推移数で終了したことを伝える
				break;
			}
			for (Trans tr : net.getGenTransSet().values()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					if(remainingTime.get(tr)==0){//残り時間0とは前回がDISABLE,もしくは発火したことを示すので残り時間を初期化
						remainingTime.put(tr, ((SimGenTrans)tr).nextTime(net, rnd));
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
						double dt = remainingTime.get(tr);
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
		resultEvent(eventValues);
		resultMarking();
		return eventValues;
	}

	public void resultEvent(ArrayList<EventValue> eventValues){
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
