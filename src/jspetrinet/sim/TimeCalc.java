package rnd;

import java.util.ArrayList;

import jspetrinet.exception.ASTException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.ImmTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;
import jspetrinet.petri.UnifTrans;
import jspetrinet.sim.Calculate;

public class TimeCalc implements Calculate{

	Sfmt rnd;
	
	public TimeCalc(Net net, int seed) throws ASTException {
		rnd = new Sfmt(seed);
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
	
	public double[] getImmTransWeigth(ArrayList<Trans> tr, Net net) throws ASTException{
		double[] weight = new double[tr.size()];
		for(int i=0;i<tr.size();i++){
			weight[i] = convertObject(((ImmTrans)tr.get(i)).getWeight().eval(net));
		}
		return weight;
	}
	
	@Override
	public int nextMultinomial(double[] weight) throws ASTException {
		double totalWeight = 0;
		for(int i=0; i<weight.length;i++){
			totalWeight += weight[i];
		}
		
		double value = nextUnif()*totalWeight;
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
	public double nextExpTrans(Trans tr, Net net) throws ASTException {
		double lambda = convertObject(((ExpTrans)tr).getRate().eval(net));
		return nextExp()/lambda;
	}
	
	@Override
	public double nextConstTrans(double time) throws ASTException{
		return time;
	}
	
	@Override
	public double nextGenTrans(Trans tr, Net net) throws ASTException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public double nextUnif() {
		return rnd.NextUnif();
	}

	@Override
	public double nextExp() {
		return rnd.NextExp();
	}

	@Override
	public double nextUnifTrans(Trans tr, Net net) throws ASTException {
		double upper = convertObject(((UnifTrans)tr).getUpper().eval(net));
		double lower = convertObject(((UnifTrans)tr).getLower().eval(net));
		return rnd.NextUnif()%(upper - lower + 1) + upper;
	}
}
