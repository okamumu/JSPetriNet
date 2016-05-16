package jspetrinet.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingArc;
import jspetrinet.marking.PetriAnalysis;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;
import rnd.Sfmt;

public class MCSimulation {
	
	protected Net net;
	protected final ArrayList<String> eventMarking;
	protected final ArrayList<Double> eventTime;
	protected final Map<String, Mark> marking;//結果表示用に通ったマーキングを保存
	
	public MCSimulation() {
		eventMarking = new ArrayList<String>();
		eventTime = new ArrayList<Double>();
		marking = new HashMap<String, Mark>();
	}
	
	protected double exponentDelayTime(Trans tr, double x) throws ASTException {
		Object lambda = ((ExpTrans)tr).getRate().eval(net);
		if (lambda instanceof Integer) {
			return ((Integer)lambda * Math.exp(-(Integer)lambda * x));
		}else if (lambda instanceof Double) {
			return ((Double)lambda * Math.exp(-(Double)lambda * x));
		}else {
			throw new TypeMismatch();
		}
	}
	
	protected double generalDelayTime(Trans tr, double x) throws ASTException {
		Object lambda = ((ExpTrans)tr).getRate().eval(net);
		if (lambda instanceof Integer) {
			return ((Integer)lambda * Math.exp(-(Integer)lambda * x));
		}else if (lambda instanceof Double) {
			return ((Double)lambda * Math.exp(-(Double)lambda * x));
		}else {
			throw new TypeMismatch();
		}
	}
	
	//渡された即時トランジションのリストからweightを用いてIndexを得る
	protected int getRandomIndex(ArrayList<Trans> etr, int seed) throws ASTException{
		double totalWeight = 0;
		for(Trans tr : etr){
			Object weight = ((ImmTrans)tr).getWeight().eval(net);
			if (weight instanceof Integer) {
				totalWeight += (Integer)((ImmTrans)tr).getWeight().eval(net);
			}else if (weight instanceof Double) {
				totalWeight += (Double)((ImmTrans)tr).getWeight().eval(net);
			}else {
				throw new TypeMismatch();
			}
		}
		
		Sfmt rnd = new Sfmt(seed);
		double value = rnd.NextUnif()*totalWeight;
		int retIndex = -1;
		for(int i=0;i<etr.size();i++){
			Object weight = ((ImmTrans)etr.get(i)).getWeight().eval(net);
			if (weight instanceof Integer) {
				if((Integer)((ImmTrans)etr.get(i)).getWeight().eval(net) >= value){
					retIndex = i;
					break;
				}
				value -= (Integer)((ImmTrans)etr.get(i)).getWeight().eval(net);
			}else if (weight instanceof Double) {
				if((Double)((ImmTrans)etr.get(i)).getWeight().eval(net) >= value){
					retIndex = i;
					break;
				}
				value -= (Double)((ImmTrans)etr.get(i)).getWeight().eval(net);
			}else {
				throw new TypeMismatch();
			}
		}
		return retIndex;
	}
	
	public void mcSimulation(Mark visited, Net net, double t, int seed) throws ASTException {
		this.net = net;
		int count=1;
		double total = 0;
		Mark dest = visited;
		ArrayList<Trans> eTrans = new ArrayList<Trans>();//発火可能なトランジションのリスト
		//int[] init_key = {(int) System.currentTimeMillis(), (int) Runtime.getRuntime().freeMemory()};
		Sfmt rnd = new Sfmt(seed);
		
		marking.put("M"+count, visited);
		eventMarking.add("M"+count);
		eventTime.add(total);
		count++;
		while (total<t) {
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
			
			if(hasImmTrans){
				//重み付きランダムで選択されたトランジションをリストの先頭へ
				int index = getRandomIndex(eTrans, rnd.NextInt(seed));
				eTrans.set(0, eTrans.get(index));
			}else{
				//発火可能なトランジションを格納
				for (Trans tr : net.getGenTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						eTrans.add(tr);
						break;
					default:
					}
				}
				for (Trans tr : net.getExpTransSet().values()) {
					switch (PetriAnalysis.isEnable(net, tr)) {
					case ENABLE:
						eTrans.add(tr);
						break;
					default:
					}
				}
				
				//最少の発火遅延時間とそれを持つトランジションを決める
				//一般発火トランジションをリストに含む場合に修正する
				double mindt = exponentDelayTime(eTrans.get(0), rnd.NextUnif());
				if(eTrans.size()!=1){
					for(int i=1;i<eTrans.size();i++){
						double dt = exponentDelayTime(eTrans.get(i), rnd.NextUnif());
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
				total += mindt;
			}

			//発火処理
			dest = PetriAnalysis.doFiring(net, eTrans.get(0));
			new MarkingArc(m, dest, eTrans.get(0));
			eventTime.add(total);
			if(!marking.containsValue(dest)){//発火先がmarkingになければ追加
				marking.put("M"+count, dest);
				eventMarking.add("M"+count);
				count++;
			}else{//発火先がmarkingにある場合、destと一致するvalueを持つkeyを探して表示
				for(Iterator<String> i = marking.keySet().iterator(); i.hasNext();){
					String k = i.next();
					Mark v = marking.get(k);
					if(dest.equals(v)){
						eventMarking.add(k);
					}
				}
			}
		}
		for(int i=0;i<eventMarking.size();i++){
			System.out.println(String.format("%.2f", eventTime.get(i))+" : "+eventMarking.get(i));
		}
		for(Map.Entry<String, Mark> hoge : marking.entrySet()){
			System.out.print(hoge.getKey() + ":");
			for(int i=0;i<net.getNumOfPlace();i++){
				System.out.print(hoge.getValue().get(i));
			}
			System.out.println("");
		}
	}
	
	//getter
	public final String geteventMarking(int index){
		return eventMarking.get(index);
	}
	
	public final double geteventTime(int index){
		return eventTime.get(index);
	}
	
	public final Mark getMarking(String label){
		return marking.get(label);
	}
}
