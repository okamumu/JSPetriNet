package jspetrinet.marking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jspetrinet.exception.*;
import jspetrinet.petri.*;

public class MarkingGraph {

	protected Net net;
	protected Mark imark;

	private final Map<GenVec,MarkGroup> genGroup;
	private final Map<GenVec,MarkGroup> immGroup;
	
//	private CreateMarking createMarking;
	
	public MarkingGraph(Net net) {
		this.net = net;
		genGroup = new HashMap<GenVec,MarkGroup>();
		immGroup = new HashMap<GenVec,MarkGroup>();
	}
	
	public final Net getNet() {
		return net;
	}
	
	public final void setInitialMark(Mark imark) {
		this.imark = imark;
	}
	

	public final Mark getInitialMark() {
		return imark;
	}

//	public void setCreateMarking(CreateMarking createMarking) {
//		this.createMarking = createMarking;
//	}

	public final int immSize() {
		int total = 0;
		for (MarkGroup mg: immGroup.values()) {
			total += mg.size();
		}
		return total;
	}
	
	public final int genSize() {
		int total = 0;
		for (MarkGroup mg: genGroup.values()) {
			total += mg.size();
		}
		return total;
	}

	public final Map<GenVec,MarkGroup> getImmGroup() {
		return immGroup;
	}

	public final Map<GenVec,MarkGroup> getGenGroup() {
		return genGroup;
	}

//	public Mark create(Mark init, Net net) throws JSPNException {
//		this.net = net;
//		this.imark = init;
////		markSet.clear();
////		numOfGenTrans = net.getGenTransSet().size();
//		immGroup.clear();
//		genGroup.clear();		
//		Mark ret = this.createMarking.create(init, net);
//		CreateGroupMarkingGraph.createMarkGroupGraph(net, immGroup, genGroup);
//		return ret;
//	}
}
