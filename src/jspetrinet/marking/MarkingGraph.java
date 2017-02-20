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

//	protected final List<Mark> markSet;

//	protected int numOfGenTrans;
	private final Map<GenVec,MarkGroup> genGroup;
	private final Map<GenVec,MarkGroup> immGroup;
	
	private CreateMarking createMarking;
	
//	public MarkingGraph() {
//		markSet = new HashMap<Mark,Mark>();
//		genGroup = new HashMap<GenVec,MarkGroup>();
//		immGroup = new HashMap<GenVec,MarkGroup>();
//		numOfGenTrans = 0;
//	}
	
	public MarkingGraph() {
//		markSet = new ArrayList<Mark>();
		genGroup = new HashMap<GenVec,MarkGroup>();
		immGroup = new HashMap<GenVec,MarkGroup>();
//		this.net = net;
//		numOfGenTrans = net.getGenTransSet().size();
	}

	public void setCreateMarking(CreateMarking createMarking) {
		this.createMarking = createMarking;
	}
	
//	public final int size() {
//		return markSet.size();
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

//	public final Net getNet() {
//		return net;
//	}

	public final Mark getInitialMark() {
		return imark;
	}

	public final Map<GenVec,MarkGroup> getImmGroup() {
		return immGroup;
	}

	public final Map<GenVec,MarkGroup> getGenGroup() {
		return genGroup;
	}

//	public final boolean containtsMark(Mark m) {
//		return markSet.containsKey(m);
//	}
	
//	public final void addMark(Mark m) {
////		if (markSet.containsKey(m)) {
////			System.out.println(m.toString() + " already exits");
////		}
////		markSet.put(m, m);
//		markSet.add(m);
//	}

	public Mark create(Mark init, Net net) throws JSPNException {
		this.net = net;
		this.imark = init;
//		markSet.clear();
//		numOfGenTrans = net.getGenTransSet().size();
		immGroup.clear();
		genGroup.clear();		
		Mark ret = this.createMarking.create(init, net);
		CreateGroupMarkingGraph.createMarkGroupGraph(net, immGroup, genGroup);
		return ret;
	}
}
