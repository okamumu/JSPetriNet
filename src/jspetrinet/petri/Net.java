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
			throw new NotFindObjectException(label);
		}
		return child.get(label);
	}

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

	public final int getNumOfGenTrans() {
		return genTransList.size();
	}

	public final int getNumOfImmTrans() {
		return immTransList.size();
	}
	
	public final int getNumOfExpTrans() {
		return expTransList.size();
	}
	
	public final int getNumOfPlace() {
		return placeList.size();
	}

	public void setIndex() {
		int i = 0;
		for (Place p : placeList) {
			p.setIndex(i);
			i++;
		}

		int j = 0;
		for (Trans tr : genTransList) {
			tr.setIndex(j);
			j++;
		}		
		for (Trans tr : expTransList) {
			tr.setIndex(j);
			j++;
		}
	}
	
	// methods	
	public Place createPlace(String label, int max) throws ASTException {
		if (this.contains(label)) {
			throw new AlreadyExistException(label);
		}
		Place tmp = new Place(label, max);
		put(label, tmp);
		placeList.add(tmp);
		return tmp;
	}
	
	public final ExpTrans createExpTrans(String label, ASTree rate) throws ASTException {
		if (this.contains(label)) {
			throw new AlreadyExistException(label);
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
			throw new AlreadyExistException(label);
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
			throw new AlreadyExistException(label);
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
				throw new AlreadyExistException(label);
			}
		}
		ArcBase tmp = new InArc(src, dest, multi);
		return tmp;
	}

	public final ArcBase createNormalOutArc(Trans src, Place dest, ASTree multi) throws ASTException {
		for (Arc a : src.getOutArc()) {
			if (a.getDest().equals(dest)) {
				throw new AlreadyExistException(label);
			}
		}
		ArcBase tmp = new OutArc(src, dest, multi);
		return tmp;
	}

	public final ArcBase createInhibitArc(Place src, Trans dest, ASTree multi) throws ASTException {
		for (Arc a : src.getOutArc()) {
			if (a.getDest().equals(dest)) {
				throw new AlreadyExistException(label);
			}
		}
		ArcBase tmp = new InhibitArc(src, dest, multi);
		return tmp;
	}
}
