package jspetrinet.sim;

import jspetrinet.petri.GenTrans;
import jspetrinet.petri.GenTransPolicy;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class SimGenTrans extends GenTrans implements SimTimedCalc{

	public SimGenTrans(String label, GenTransPolicy policy) {
		super(label, policy);
	}

	@Override
	public double nextTime(Trans tr, Net net) {
		
		if(tr instanceof SimGenConstTrans){
			 
		}else if(tr instanceof SimGenUnifTrans){
			
		}
		return 0;
	}
}
