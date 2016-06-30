package jspetrinet.petri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTValue;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.*;
import jspetrinet.graph.Arc;
import jspetrinet.graph.Component;

public class Net extends ASTEnv {
	
	private final String label;
	private final Net outer;
	private final Map<String,Net> child;
	
	protected final List<Place> placeList;
	protected final List<Trans> immTransList;
	protected final List<Trans> expTransList;
	protected final List<Trans> genTransList;

//	protected final Map<String,Place> placeSet;
//	protected final Map<String,Trans> immTransSet;
//	protected final Map<String,Trans> expTransSet;
//	protected final Map<String,Trans> genTransSet;

	protected ASTree reward;
	
	public Net(String label) {
		this(null, label);
	}

	public Net(Net outer, String label) {
		this.label = label;
		this.outer = outer;

		placeList = new ArrayList<Place>();
		immTransList = new ArrayList<Trans>();
		expTransList = new ArrayList<Trans>();
		genTransList = new ArrayList<Trans>();

//		placeSet = new HashMap<String,Place>();
//		immTransSet = new HashMap<String,Trans>();
//		expTransSet = new HashMap<String,Trans>();
//		genTransSet = new HashMap<String,Trans>();
		child = new HashMap<String,Net>();
		if (outer != null) {
			outer.setChild(label, this);
		}
	}

	// getter
	public final Net getOuter() {
		return outer;
	}
	
	public final void setChild(String label, Net net) {
		child.put(label, net);
	}
	
	public final boolean containts(String label) {
		return child.containsKey(label);
	}

	public final Net getChild(String label) throws ASTException {
		if (!child.containsKey(label)) {
			throw new NotFindObjectException();
		}
		return child.get(label);
	}

//	private final List<Component> sortedComponentPlace(Map<String,Place> m) {
//		List<Map.Entry<String,Place>> entries = new ArrayList<Map.Entry<String,Place>>(m.entrySet());
//		Collections.sort(entries, new Comparator<Map.Entry<String,Place>>() {
//			@Override
//			public int compare(Entry<String,Place> entry1, Entry<String,Place> entry2) {
//				return entry2.getKey().compareTo(entry1.getKey());
//			}
//		});
//		List<Component> result = new LinkedList<Component>();
//		for (Entry<String,Place> c : entries) {
//			result.add(c.getValue());
//		}
//		return result;
//	}
//	
//	private final List<Component> sortedComponentTrans(Map<String,Trans> m) {
//		List<Map.Entry<String,Trans>> entries = new ArrayList<Map.Entry<String,Trans>>(m.entrySet());
//		Collections.sort(entries, new Comparator<Map.Entry<String,Trans>>() {
//			@Override
//			public int compare(Entry<String,Trans> entry1, Entry<String,Trans> entry2) {
//				return entry2.getKey().compareTo(entry1.getKey());
//			}
//		});
//		List<Component> result = new LinkedList<Component>();
//		for (Entry<String,Trans> c : entries) {
//			result.add(c.getValue());
//		}
//		return result;
//	}
//
	public final List<Component> getAllComponent() {
		List<Component> all = new LinkedList<Component>();
		for (Net c : child.values()) {
			all.addAll(c.getAllComponent());
		}
		all.addAll(placeList);
		all.addAll(immTransList);
		all.addAll(expTransList);
		all.addAll(genTransList);
		return all;
	}

//	public final Map<String,Place> getPlaceSet() {
//		return placeSet;
//	}
//	
//	public final Map<String,Trans> getImmTransSet() {
//		return immTransSet;
//	}
//
//	public final Map<String,Trans> getExpTransSet() {
//		return expTransSet;
//	}
//
//	public final Map<String,Trans> getGenTransSet() {
//		return genTransSet;
//	}
	
	public final List<Place> getPlaceSet() {
		return placeList;
	}
	
	public final List<Trans> getImmTransSet() {
		return immTransList;
	}

	public final List<Trans> getExpTransSet() {
		return expTransList;
	}

	public final List<Trans> getGenTransSet() {
		return genTransList;
	}

	public final double getReward() throws ASTException {
		Object tmp = reward.eval(this);
		if (tmp instanceof Integer) {
			return ((Integer) tmp).doubleValue();
		} else if (tmp instanceof Double) {
			return ((Double) tmp).doubleValue();
		} else {
			throw new TypeMismatch();
		}
	}
	
	public final int getNumOfGenTrans() {
		return genTransList.size();
	}

	public final int getNumOfPlace() {
		return placeList.size();
	}

//	public final Place getPlace(String label) throws ASTException {
//		this.contains(label)
//		if (this.contains(label)) {
//			return this.get(label)placeSet.get(label);
//		}
//
//		if (outer != null) {
//			return outer.getPlace(label);
//		} else {
//			throw new NotFindObjectException();
//		}
//	}
	
//	public final Trans getTrans(String label) throws ASTException {
//		if (immTransSet.containsKey(label)) {
//			return immTransSet.get(label);
//		}
//		if (expTransSet.containsKey(label)) {
//			return expTransSet.get(label);
//		}
//		if (genTransSet.containsKey(label)) {
//			return genTransSet.get(label);
//		}
//
//		if (outer != null) {
//			return outer.getTrans(label);
//		} else {
//			throw new NotFindObjectException();
//		}
//	}

//	public final Place getPlace(String label, Net global) throws ASTException {
//		String[] strarray = label.split("\\.");
//		Net cur = global;
//		for (int i=0; i<strarray.length-1; i++) {
//			cur = cur.getChild(strarray[i]);
//		}
//		if (placeSet.containsKey(strarray[strarray.length-1])) {
//			return placeSet.get(strarray[strarray.length-1]);			
//		} else {
//			throw new NotFindObjectException();
//		}
//	}

//	public final Trans getTrans(String label, Net global) throws ASTException {
//		String[] strarray = label.split("\\.");
//		Net cur = global;
//		for (int i=0; i<strarray.length-1; i++) {
//			cur = cur.getChild(strarray[i]);
//		}
//		
//		if (immTransSet.containsKey(strarray[strarray.length-1])) {
//			return immTransSet.get(strarray[strarray.length-1]);
//		}
//		if (expTransSet.containsKey(strarray[strarray.length-1])) {
//			return expTransSet.get(strarray[strarray.length-1]);
//		}
//		if (genTransSet.containsKey(strarray[strarray.length-1])) {
//			return genTransSet.get(strarray[strarray.length-1]);
//		}
//		throw new NotFindObjectException();
//	}
	
	// override
	@Override
	public String toString() {
		String res = super.toString();
		String linesep = System.getProperty("line.separator").toString();
		res += linesep + linesep + "Net: " + label;
		res += linesep + "#Place:" + placeList.size() + linesep;
		for (Place p: placeList) {
			res += " " + p.getLabel() + ";"; 
		}
		res += linesep + "#Imm:" + immTransList.size() + linesep;
		for (Trans t: immTransList) {
			res += " " + t.getLabel() + ";"; 
		}
		res += linesep + "#Exp:" + expTransList.size() + linesep;
		for (Trans t: expTransList) {
			res += " " + t.getLabel() + ";"; 
		}
		res += linesep + "#Gen:" + genTransList.size() + linesep;
		for (Trans t: genTransList) {
			res += " " + t.getLabel() + ";"; 
		}
		return res;
	}
	
	// initialize
//	public void init() {
//		setIndex();
//	}
	
	public void setIndex() {
		int i = 0;
		for (Place p : placeList) {
			p.setIndex(i);
//			placeIndex.put(i, p);
			i++;
		}
		
		int j = 0;
		for (Trans tr : genTransList) {
			tr.setIndex(j);
			j++;
		}
	}
	
	// methods
	public void setReward(String reward) throws ASTException {
		Object tmp = this.get(reward);
		if (tmp instanceof ASTree) {
			this.reward = (ASTree) tmp;
		} else {
			throw new TypeMismatch();
		}
	}
	
	public Place createPlace(String label, int max) throws ASTException {
		if (this.contains(label)) {
			throw new AlreadyExistException();
		}
		Place tmp = new Place(label, max);
		put(label, tmp);
		placeList.add(tmp);
		return tmp;
	}
	
	public final ExpTrans createExpTrans(String label, ASTree rate) throws ASTException {
		if (this.contains(label)) {
			throw new AlreadyExistException();
		}
		ExpTrans tmp = new ExpTrans(label, rate);
		put(label, tmp);
		expTransList.add(tmp);
		return tmp;
	}

	public final ExpTrans createExpTrans(String label, double rate) throws ASTException {
		return createExpTrans(label, new ASTValue(rate));
	}

	public final ImmTrans createImmTrans(String label, ASTree weight) throws ASTException {
		if (this.contains(label)) {
			throw new AlreadyExistException();
		}
		ImmTrans tmp = new ImmTrans(label, weight);
		put(label, tmp);
		immTransList.add(tmp);
		return tmp;
	}

	public final ImmTrans createImmTrans(String label, double weight) throws ASTException {
		return createImmTrans(label, new ASTValue(weight));
	}

	public final GenTrans createGenTrans(String label, ASTree dist, GenTransPolicy policy) throws ASTException {
		if (this.contains(label)) {
			throw new AlreadyExistException();
		}
		GenTrans tmp = new GenTrans(label, dist, policy);
		put(label, tmp);
		genTransList.add(tmp);
		return tmp;
	}

	//// arc
	
	public final ArcBase createNormalInArc(Place src, Trans dest, ASTree multi) throws ASTException {
		for (Arc a : src.getOutArc()) {
			if (a.getDest().equals(dest)) {
				throw new AlreadyExistException();
			}
		}
		ArcBase tmp = new InArc(src, dest, multi);
		return tmp;
	}

	public final ArcBase createNormalOutArc(Trans src, Place dest, ASTree multi) throws ASTException {
		for (Arc a : src.getOutArc()) {
			if (a.getDest().equals(dest)) {
				throw new AlreadyExistException();
			}
		}
		ArcBase tmp = new OutArc(src, dest, multi);
		return tmp;
	}

	public final ArcBase createInhibitArc(Place src, Trans dest, ASTree multi) throws ASTException {
		for (Arc a : src.getOutArc()) {
			if (a.getDest().equals(dest)) {
				throw new AlreadyExistException();
			}
		}
		ArcBase tmp = new InhibitArc(src, dest, multi);
		return tmp;
	}
	
}
