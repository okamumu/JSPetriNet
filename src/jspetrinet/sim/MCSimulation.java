package jspetrinet.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.PetriAnalysis;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class MCSimulation {
	
	protected Net net;
	protected Random rnd;
	protected final ArrayList<Mark> eventMarking;
	protected final ArrayList<Double> eventTime;
	protected final Map<Mark, Mark> marking;//結果表示用に通ったマーキングを保存
	protected final Map<Trans, Double> remainingTime;//一般発火トランジションの残り時間
	
	public MCSimulation(Net net) throws ASTException {
		eventMarking = new ArrayList<Mark>();
		eventTime = new ArrayList<Double>();
		marking = new HashMap<Mark, Mark>();
		remainingTime = new HashMap<Trans, Double>();
		for (Trans tr : net.getGenTransSet().values()) {
			remainingTime.put(tr, 0.0);
		}
	}
	
	public double convertObject(Object obj) throws ASTException{
		double doubleType;
		if(obj instanceof Integer){
			doubleType = (Integer)obj;
		}else if(obj instanceof Double){
			doubleType = (Double)obj;
		}else {
			throw new TypeMismatch();
		}
		return doubleType;
	}
	
	public boolean nextMultinomial(double w1, double w2){
		double value = rnd.nextUnif() * (w1 + w2);
		if(w1>=value){
			return true;
		}else{
			return false;
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
	
	public void mcSimulation(Mark visited, Net net, double t, int seed) throws ASTException {
		this.net = net;
		rnd = new Adapter(net, seed);
		double total = 0;
		Mark dest = visited;
		
		marking.put(visited, visited);
		eventMarking.add(visited);
		eventTime.add(total);
		while (true) {			
			Mark m = dest;
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
			boolean hasImmTrans = false;
			double totalWeight = 0;
			double weight = 0;
			for (Trans tr : net.getImmTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					weight = convertObject(((ImmTrans)tr).getWeight().eval(net));
					if(nextMultinomial(weight, totalWeight)){
						selTrans = tr;
					}
					totalWeight += weight;
					hasImmTrans = true;
					break;
				default:
				}
			}
			if(!hasImmTrans){
				double mindt = Double.POSITIVE_INFINITY;
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
				//一般発火トランジションの残り時間再セット
				updateRemainingTime(selTrans, mindt);
				total += mindt;
			}
			if(total>t){
				break;
			}
			//発火処理			
			dest = PetriAnalysis.doFiring(net, selTrans);
			new MarkingArc(m, dest, selTrans);
			eventTime.add(total);
			if(!marking.containsValue(dest)){//発火先がmarkingになければ追加
				marking.put(dest, dest);
				eventMarking.add(dest);
			}else{//発火先がmarkingにある場合、
				eventMarking.add(dest);
			}
		}
		resultEvent();
		resultMarking();
	}
	
	public void resultEvent(){
		for(int i=0;i<eventMarking.size();i++){
			System.out.print(String.format("%.2f", eventTime.get(i))+" : ");
			for(int j=0;j<net.getNumOfPlace();j++){
				System.out.print(eventMarking.get(i).get(j));
			}
			System.out.println("");
		}
	}
	
	public void resultMarking(){
		for(Mark mark : marking.keySet()){
			for(int i=0;i<net.getNumOfPlace();i++){
				System.out.print(mark.get(i));
			}
			System.out.println("");
		}
	}
	
	//getter
	public final Mark geteventMarking(int index){
		return eventMarking.get(index);
	}
	
	public final double geteventTime(int index){
		return eventTime.get(index);
	}
}
