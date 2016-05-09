package jspetrinet.marking;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import jspetrinet.exception.ASTException;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;
import rnd.Sfmt;

public class MarkingProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4650765905602529452L;

	protected Net net;

	protected final Map<Mark,Mark> markSet;
	protected final Map<Mark,Mark> arcSet;
	protected final Map<Integer,Mark> index;

	protected int numOfGenTrans;
	protected final Map<GenVec,MarkGroup> genGroup;
	protected final Map<GenVec,MarkGroup> immGroup;
	
	public MarkingProcess() {
		markSet = new HashMap<Mark,Mark>();
		genGroup = new HashMap<GenVec,MarkGroup>();
		immGroup = new HashMap<GenVec,MarkGroup>();
		arcSet = new HashMap<Mark,Mark>();
		index = new HashMap<Integer,Mark>();
		numOfGenTrans = 0;
	}
	
//	public void createIndex(boolean oneBased) {
//		int i;
//		if (oneBased == true) {
//			i = 1;
//		} else {
//			i = 0;
//		}
//
//		// IMM
//		for (MarkGroup mg : immGroup.values()) {
//			for (Mark m : mg.markset()) {
//				index.put(i, m);
//				m.setIndex(i);
//				i++;
//			}
//		}
//
//		// Exp/Gen Matrices
//		for (MarkGroup mg : genGroup.values()) {
//			for (Mark m : mg.markset()) {
//				index.put(i, m);
//				m.setIndex(i);
//				i++;
//			}
//		}
//	}
//
//	public List< List<Object> > matrix(Net net, Set<Mark> ms) throws ASTException {
//		List< List<Object> > result = new ArrayList< List<Object> >();
//		for (Mark m: ms) {
//			net.setCurrentMark(m);
//			for (Arc a: m.getOutArc()) {
//				List<Object> tmp = new ArrayList<Object>();
//				Mark nm = (Mark) a.getDest();
//				MarkingArc marc = (MarkingArc) a;
//				tmp.add(m.index());
//				tmp.add(nm.index());
//				if (marc.getTrans() instanceof ImmTrans) {
//					ImmTrans tr = (ImmTrans) marc.getTrans();
//					tmp.add(tr.getWeight().eval(net));
//				} else if (marc.getTrans() instanceof ExpTrans) {
//					ExpTrans tr = (ExpTrans) marc.getTrans();
//					tmp.add(tr.getRate().eval(net));
//				} else if (marc.getTrans() instanceof GenTrans) {
//					GenTrans tr = (GenTrans) marc.getTrans();
//					tmp.add(tr.getDist().eval(net));
//				}
//				result.add(tmp);
//			}
//		}
//		return result;
//	}
//
//	public List< List<Object> > matrix(Net net, Set<Mark> ms, Set<Mark> ds) throws ASTException {
//		List< List<Object> > result = new ArrayList< List<Object> >();
//		for (Mark m: ms) {
//			net.setCurrentMark(m);
//			for (Arc a: m.getOutArc()) {
//				List<Object> tmp = new ArrayList<Object>();
//				Mark nm = (Mark) a.getDest();
//				if (ds.contains(nm)) {
//					MarkingArc marc = (MarkingArc) a;
//					tmp.add(m.index());
//					tmp.add(nm.index());
//					if (marc.getTrans() instanceof ImmTrans) {
//						ImmTrans tr = (ImmTrans) marc.getTrans();
//						tmp.add(tr.getWeight().eval(net));
//					} else if (marc.getTrans() instanceof ExpTrans) {
//						ExpTrans tr = (ExpTrans) marc.getTrans();
//						tmp.add(tr.getRate().eval(net));
//					} else if (marc.getTrans() instanceof GenTrans) {
//						GenTrans tr = (GenTrans) marc.getTrans();
//						tmp.add(tr.getDist().eval(net));
//					}
//					result.add(tmp);
//				}
//			}
//		}
//		return result;
//	}
//
//	public List< List<Object> > sreward(Net net, String reward, Set<Mark> ms) throws ASTException {
//		net.setReward(reward);
//		List< List<Object> > result = new ArrayList< List<Object> >();
//		for (Mark m: ms) {
//			net.setCurrentMark(m);
//			List<Object> tmp = new ArrayList<Object>();
//			tmp.add(m.index());
//			tmp.add(net.getReward());
//			result.add(tmp);
//		}
//		return result;
//	}

	public final int count() {
		return markSet.size();
	}
	
	public final int immcount() {
		int total = 0;
		for (MarkGroup mg: immGroup.values()) {
			total += mg.size();
		}
		return total;
	}

//	public Mark mark(int index) {
//		return this.index.get(index);
//	}
//
//	public Mark mark(Mark m) {
//		return markSet.get(m);
//	}
//
//	public final Set<Mark> markset() {
//		return markSet.keySet();
//	}

	public final Map<GenVec,MarkGroup> getImmGroup() {
		return immGroup;
	}

	public final Map<GenVec,MarkGroup> getGenGroup() {
		return genGroup;
	}

	public final MarkGroup getExpGroup() {
		return genGroup.get(new GenVec(numOfGenTrans));
	}

//	public final Map<GenVec,MarkGroup> imm() {
//		return immGroup;
//	}
//
//	public final MarkGroup imm(GenVec gv) {
//		return immGroup.get(gv);
//	}
//
//	public final Map<GenVec,MarkGroup> gen() {
//		return genGroup;
//	}
//
//	public final MarkGroup gen(GenVec gv) {
//		return genGroup.get(gv);
//	}

	public Mark create(Mark init, Net net) throws ASTException {
		this.net = net;
		markSet.clear();
		arcSet.clear();
		arcSet.put(init, init);//初期マーキングを到達済みとして記憶

		numOfGenTrans = net.getNumOfGenTrans();
		immGroup.clear();
		GenVec genv = new GenVec(numOfGenTrans);
		immGroup.put(genv, new MarkGroup());
		genGroup.clear();
		genGroup.put(genv, new MarkGroup());

		LinkedList<Mark> novisited = new LinkedList<Mark>();
		novisited.push(init);
		//create(novisited, net);
		m_simulation(init, net, 10);
		return init;//初期マーキングをそのまま返しているが意味はあるのか。引数はMarkだが、代入はしなくていいのか
	}
	
	protected void create(LinkedList<Mark> novisited, Net net) throws ASTException {
		int count=1;
		String[] pname = new String[net.getNumOfPlace()];
		int n = 0;
		for(String str : net.getPlaceSet().keySet()){
			pname[n] = str;
			n++;
		}
		double total = 0;
		int[][] marking = new int[99][net.getNumOfPlace()];
		String[] marking_name = new String[99];
		while (!novisited.isEmpty()) {
			if(count!=1){
				int[] init_key = {(int) System.currentTimeMillis(), (int) Runtime.getRuntime().freeMemory()};
				Sfmt rnd = new Sfmt(init_key);
				total += rnd.NextExp();
			}
			System.out.print(String.format("%.2f", total));
			marking_name[count-1] = "M" + count;
			System.out.println(" "+marking_name[count-1]);
			for(int i=0;i<net.getNumOfPlace();i++){
				marking[count-1][i] = novisited.get(0).get(i);
			}
			count++;

			Mark m = novisited.pop();
			net.setCurrentMark(m);
			if (markSet.containsKey(m)) {
				continue;
			}
			markSet.put(m, m);

			// make genvec
			GenVec genv = new GenVec(numOfGenTrans);
			for (Trans tr : net.getGenTransSet().values()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					genv.set(tr.getIndex(), 1);
					break;
				case PREEMPTION:
					genv.set(tr.getIndex(), 2);
					break;
				default:
				}
			}
			if (!immGroup.containsKey(genv)) {
				immGroup.put(genv, new MarkGroup());
			}
			if (!genGroup.containsKey(genv)) {
				genGroup.put(genv, new MarkGroup());
			}

			boolean hasImmTrans = false;
			for (Trans tr : net.getImmTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					hasImmTrans = true;
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						dest = arcSet.get(dest);
					} else {
						novisited.push(dest);
						arcSet.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			if (hasImmTrans == true) {
				m.setMarkGroup(immGroup.get(genv));
				continue;
			} else {
				m.setMarkGroup(genGroup.get(genv));
			}
			
			for (Trans tr : net.getGenTransSet().values()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						dest = arcSet.get(dest);
					} else {
						novisited.push(dest);
						arcSet.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			
			for (Trans tr : net.getExpTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {//発火先がすでに到達済み
						dest = arcSet.get(dest);
					} else {
						novisited.push(dest);//到達していないマーキングを挿入(リストの先頭に)
						arcSet.put(dest, dest);//到達済みマーキングとして記録
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
		}
		for(int i=0;i<count-1;i++){
			System.out.print(marking_name[i]+":");
			for(int j=0;j<net.getNumOfPlace();j++){
				if(j==0){
					System.out.print("(");
				}
				System.out.print(marking[i][j]);
				if(j!=net.getNumOfPlace()-1){
					System.out.print(",");
				}else{
					System.out.println(")");
				}
				//System.out.println(pname[i]+":"+novisited.get(0).get(i));
			}
		}
	}
	
	protected void m_simulation(Mark visited, Net net, double t) throws ASTException {
		Map<String, Mark> marking = new HashMap<String, Mark>();
		int count=1;
		double total = 0;
		Mark nextMark = visited;
		
		System.out.print(String.format("%.2f", total));
		marking.put("M"+count, visited);
		System.out.println(" : M"+count);
		count++;
		//while (!visited.isEmpty()) {
		while (total<t) {
			Mark m = nextMark;
			net.setCurrentMark(m);
			if (markSet.containsKey(m)) {
				//continue;
			}
			markSet.put(m, m);
			/*for(int i=0;i<net.getNumOfPlace();i++){
				System.out.print("-"+m.get(i));
			}
			System.out.println("");*/

			// make genvec
			GenVec genv = new GenVec(numOfGenTrans);
			for (Trans tr : net.getGenTransSet().values()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					genv.set(tr.getIndex(), 1);
					break;
				case PREEMPTION:
					genv.set(tr.getIndex(), 2);
					break;
				default:
				}
			}
			if (!immGroup.containsKey(genv)) {
				immGroup.put(genv, new MarkGroup());
			}
			if (!genGroup.containsKey(genv)) {
				genGroup.put(genv, new MarkGroup());
			}

			boolean hasImmTrans = false;
			for (Trans tr : net.getImmTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					hasImmTrans = true;
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						dest = arcSet.get(dest);
					} else {
						//visited.push(dest);
						arcSet.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			if (hasImmTrans == true) {
				m.setMarkGroup(immGroup.get(genv));
				continue;
			} else {
				m.setMarkGroup(genGroup.get(genv));
			}
			
			for (Trans tr : net.getGenTransSet().values()) {
				switch (PetriAnalysis.isEnableGenTrans(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {
						dest = arcSet.get(dest);
					} else {
						//visited.push(dest);
						arcSet.put(dest, dest);
					}
					new MarkingArc(m, dest, tr);
					break;
				default:
				}
			}
			
			loop1: for (Trans tr : net.getExpTransSet().values()) {
				boolean isEnd = false;
				int[] init_key = {(int) System.currentTimeMillis(), (int) Runtime.getRuntime().freeMemory()};
				Sfmt rnd = new Sfmt(init_key);
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					if(rnd.NextUnif()>0.5||isEnd){
						Mark dest = PetriAnalysis.doFiring(net, tr);
						/*for(int i=0;i<net.getNumOfPlace();i++){
							System.out.print(dest.get(i));
						}
						System.out.println("");*/
						nextMark = dest;
						new MarkingArc(m, dest, tr);
					
						total += rnd.NextExp();
						System.out.print(String.format("%.2f", total));
						if(!marking.containsValue(dest)){//発火先がmarkingになければ追加
							marking.put("M"+count, dest);
							System.out.println(" : M"+count);
							count++;
						}else{//発火先がmarkingにある場合、destと一致するvalueを持つkeyを探して表示
							for(Iterator<String> i = marking.keySet().iterator(); i.hasNext();){
								String k = i.next();
								Mark v = marking.get(k);
								if(dest.equals(v)){
									System.out.println(" : "+k);
								}
							}
						}
						break loop1;
					}else{
						isEnd =true;
						break;
					}
				default:
				}
			}
			
			/*for (Trans tr : net.getExpTransSet().values()) {
				switch (PetriAnalysis.isEnable(net, tr)) {
				case ENABLE:
					Mark dest = PetriAnalysis.doFiring(net, tr);
					if (arcSet.containsKey(dest)) {//発火先がすでに到達済み
						dest = arcSet.get(dest);//ここの意味
					} else {
						visited.push(dest);//到達していないマーキングを挿入(リストの先頭に)
						arcSet.put(dest, dest);//到達済みマーキングとして記録
					}
					new MarkingArc(m, dest, tr);
					
					int[] init_key = {(int) System.currentTimeMillis(), (int) Runtime.getRuntime().freeMemory()};
					Sfmt rnd = new Sfmt(init_key);
					total += rnd.NextExp();
					System.out.print(String.format("%.2f", total));
					if(!marking.containsValue(dest)){//発火先がmarkingになければ追加
						marking.put("M"+count, dest);
						System.out.println(" : M"+count);
						count++;
					}else{//発火先がmarkingにある場合、destと一致するvalueを持つkeyを探して表示
						for(Iterator<String> i = marking.keySet().iterator(); i.hasNext();){
							String k = i.next();
							Mark v = marking.get(k);
							if(dest.equals(v)){
								System.out.println(" : "+k);
							}
						}
					}
					
					break;
				default:
				}
			}*/
		}
		for(Map.Entry<String, Mark> hoge : marking.entrySet()){
			System.out.print(hoge.getKey() + ":");
			for(int i=0;i<net.getNumOfPlace();i++){
				System.out.print(hoge.getValue().get(i));
			}
			System.out.println("");
		}
	}
}
