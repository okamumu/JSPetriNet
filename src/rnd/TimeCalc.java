package rnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.GenTransPolicy;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;
import jspetrinet.sim.Calculate;

public class TimeCalc implements Calculate{

	public Map<Trans, Double> resumeTime;//一定分布のレジューム用
	
	public TimeCalc(Net net) throws ASTException {
		resumeTime = new HashMap<Trans, Double>();
		resumeGenTrans(net);
	}
	
	public double[] getImmTransWeigth(ArrayList<Trans> tr, Net net) throws ASTException{
		double[] weight = new double[tr.size()];
		for(int i=0;i<tr.size();i++){
			Object w = ((ImmTrans)tr.get(i)).getWeight().eval(net);
			if (w instanceof Integer) {
				weight[i] += (Integer)w;
			}else if (w instanceof Double) {
				weight[i] += (Double)w;
			}else {
				throw new TypeMismatch();
			}
		}
		return weight;
	}
	
	@Override
	public int Multinomial(double[] weight, Sfmt rnd) throws ASTException {
		double totalWeight = 0;
		for(int i=0; i<weight.length;i++){
			totalWeight += weight[i];
		}
		
		double value = rnd.NextUnif()*totalWeight;
		int retIndex = -1;
		for(int i=0;i<weight.length;i++){
			if(weight[i] >= value){
				retIndex = i;
				break;
			}
			value -= weight[i];
		}
		return retIndex;
	}

	@Override
	public double nextExp(Trans tr, Net net, Sfmt rnd) throws ASTException {
		Object lambda = ((ExpTrans)tr).getRate().eval(net);
		if (lambda instanceof Integer) {
			return rnd.NextExp()/(Integer)lambda;
		}else if (lambda instanceof Double) {
			return rnd.NextExp()/(Double)lambda;
		}else {
			throw new TypeMismatch();
		}
	}
	
	@Override
	public double nextCertain(Trans tr, Net net) throws ASTException{
		Object dist = ((GenTrans)tr).getDist().eval(net);
		double certain;
		if (dist instanceof Integer) {
			certain = (Integer)dist;
		}else if (dist instanceof Double) {
			certain = (Double)dist;
		}else {
			throw new TypeMismatch();
		}
		
		GenTransPolicy gTransPolicy = ((GenTrans)tr).getPolicy();
		switch (gTransPolicy) {
		case PRD:
			return certain;
		case PRS:
			return resumeTime.get(tr);
		default:
			throw new ASTException();//?
		}
	}
	
	public void resumeGenTrans(Net net) throws ASTException{
		for (Trans tr : net.getGenTransSet().values()) {
			Object dist = ((GenTrans)tr).getDist().eval(net);
			if(((GenTrans)tr).getPolicy()==GenTransPolicy.PRS){
				double certain;
				if (dist instanceof Integer) {
					certain = (Integer)dist;
				}else if (dist instanceof Double) {
					certain = (Double)dist;
				}else {
					throw new TypeMismatch();
				}
				resumeTime.put(tr, certain);
			}
		}
	}
}
