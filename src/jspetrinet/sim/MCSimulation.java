package jspetrinet.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.PetriAnalysis;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;
import rnd.TimeCalc;

public class MCSimulation {
	
	protected Net net;
	protected final ArrayList<Mark> eventMarking;
	protected final ArrayList<Double> eventTime;
	protected final Map<Mark, Mark> marking;//結果表示用に通ったマーキングを保存
	protected Map<Trans, Double> remaingTime;//一般発火トランジションの残り時間
	
	public MCSimulation(Net net) throws ASTException {
		eventMarking = new ArrayList<Mark>();
		eventTime = new ArrayList<Double>();
		marking = new HashMap<Mark, Mark>();
		remaingTime = new HashMap<Trans, Double>();
		remaingTimeGenTrans(net);
	}

	private void remaingTimeGenTrans(Net net) throws ASTException{
		for (Trans tr : net.getGenTransSet().values()) {
			double t = new TimeCalc(net, 0).convertObject(((GenTrans)tr).getDist().eval(net));
			remaingTime.put(tr, t);
		}
	}
	
	private void resetRemaingTime(Trans selTrans,double elapsedTime) throws ASTException{
		for (Trans tr : net.getGenTransSet().values()) {
			switch (PetriAnalysis.isEnable(net, tr)) {
			case ENABLE:
				if(tr.equals(selTrans)){//発火したトランジションの残り時間を初期化
					double t = new TimeCalc(net, 0).convertObject(((GenTrans)tr).getDist().eval(net));
					remaingTime.put(tr, t);
				}else{//発火可能だったものの発火しなかったトランジションは残り時間を更新
					remaingTime.put(tr, remaingTime.get(tr)-elapsedTime);
				}
				break;
			default:
			}
		}
	}
	
	public void mcSimulation(Mark visited, Net net, double t, int seed) throws ASTException {
		this.net = net;
		TimeCalc tCalc = new TimeCalc(net, seed);
		double total = 0;
		Mark dest = visited;
		ArrayList<Trans> eTrans = new ArrayList<Trans>();//発火可能な即時トランジションのリスト
		//int[] init_key = {(int) System.currentTimeMillis(), (int) Runtime.getRuntime().freeMemory()};
		
		marking.put(visited, visited);
		eventMarking.add(visited);
		eventTime.add(total);
		while (true) {
			eTrans.clear();//発火可能トランジションを削除
			
			Mark m = dest;
			net.setCurrentMark(m);
			
			boolean hasImmTrans = false;
			for (Trans tr : net.getImmTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					hasImmTrans = true;
					eTrans.add(tr);
					break;
				default:
				}
			}
			Trans selTrans = null;
			if(hasImmTrans){
				//重み付きランダムで選択されたトランジションをリストの先頭へ
				int index = tCalc.nextMultinomial(tCalc.getImmTransWeigth(eTrans, net));
				selTrans = eTrans.get(index);
			}else{
				double mindt = Double.POSITIVE_INFINITY;
				for (Trans tr : net.getExpTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = tCalc.nextExpTrans(tr, net);
						if(dt < mindt){
							mindt = dt;
							selTrans = tr;
						}
						break;
					default:
					}
				}
				for (Trans tr : net.getGenTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						double dt = tCalc.nextConstTrans(remaingTime.get(tr));
						if(dt < mindt){
							mindt = dt;
							selTrans = tr;
						}
						break;
					default:
					}
				}
				//一般発火トランジションの残り時間再セット
				resetRemaingTime(selTrans, mindt);
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
