package jspetrinet.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.PetriAnalysis;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;
import rnd.Sfmt;
import rnd.TimeCalc;

public class MCSimulation {
	
	protected Net net;
	protected final ArrayList<Mark> eventMarking;
	protected final ArrayList<Double> eventTime;
	protected final Map<Mark, Mark> marking;//結果表示用に通ったマーキングを保存
	
	public MCSimulation() {
		eventMarking = new ArrayList<Mark>();
		eventTime = new ArrayList<Double>();
		marking = new HashMap<Mark, Mark>();
	}
	
	public void mcSimulation(Mark visited, Net net, double t, int seed) throws ASTException {
		this.net = net;
		TimeCalc tCalc = new TimeCalc(net);
		double total = 0;
		Mark dest = visited;
		ArrayList<Trans> eTrans = new ArrayList<Trans>();//発火可能なトランジションのリスト
		ArrayList<Trans> gTrans = new ArrayList<Trans>();//発火可能な一般トランジションのリスト
		//int[] init_key = {(int) System.currentTimeMillis(), (int) Runtime.getRuntime().freeMemory()};
		Sfmt rnd = new Sfmt(seed);
		
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
			int selectGen = -1;
			if(hasImmTrans){
				//重み付きランダムで選択されたトランジションをリストの先頭へ
				int index = tCalc.Multinomial(tCalc.getImmTransWeigth(eTrans, net), rnd);
				//int index = getRandomIndex(eTrans, rnd.NextInt(seed));
				eTrans.set(0, eTrans.get(index));
			}else{
				boolean hasExpTrans = false;
				//発火可能なトランジションを格納
				for (Trans tr : net.getExpTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						eTrans.add(tr);
						hasExpTrans = true;
						break;
					default:
					}
				}
				for (Trans tr : net.getGenTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						gTrans.add(tr);
						break;
					default:
					}
				}
				
				//最少の発火遅延時間とそれを持つトランジションを決める
				//一般発火トランジションをリストに含む場合に修正する
				double mindt;
				if(hasExpTrans){
					mindt = tCalc.nextExp(eTrans.get(0), net, rnd);
				}else{
					mindt = tCalc.nextCertain(gTrans.get(0), net);
				}
				if(eTrans.size()!=1){
					for(int i=1;i<eTrans.size();i++){
						double dt = tCalc.nextExp(eTrans.get(i), net, rnd);
						if(dt < mindt){
							mindt = dt;
							eTrans.remove(i-1);
							i--;
						}else{
							eTrans.remove(i);
							i--;
						}
					}
				}
				for(int i=0;i<gTrans.size();i++){
					double dt = tCalc.nextCertain(gTrans.get(i), net);
					if(dt < mindt){
						mindt = dt;
						selectGen = i;
					}
				}
				//一定分布のレジューム再セット
				for(int i=0;i<gTrans.size();i++){
					if(tCalc.resumeTime.containsKey(gTrans.get(i))&&selectGen!=i){
						tCalc.resumeTime.put(gTrans.get(i), tCalc.resumeTime.get(gTrans.get(i))-mindt);
					}
				}
				total += mindt;
			}
			if(total>t){
				break;
			}
			//発火処理
			if(selectGen==-1){
				dest = PetriAnalysis.doFiring(net, eTrans.get(0));
			}else{
				dest = PetriAnalysis.doFiring(net, gTrans.get(selectGen));
			}
			new MarkingArc(m, dest, eTrans.get(0));
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
