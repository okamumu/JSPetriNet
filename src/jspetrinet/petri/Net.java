package jspetrinet.petri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jspetrinet.ast.ASTEnv;
import jspetrinet.ast.ASTValue;
import jspetrinet.JSPetriNet;
import jspetrinet.ast.AST;
import jspetrinet.exception.*;
import jspetrinet.graph.Arc;
import jspetrinet.graph.Component;

public class Net extends ASTEnv {
	
	private final String label;
	private final Net outer;
	private final Map<String,Net> child;
	
	protected final List<Place> placeList;
//	protected final List<Trans> immTransList;
//	protected final List<Trans> expTransList;
//	protected final List<Trans> genTransList;
	protected final List<ImmTrans> immTransList;
	protected final List<ExpTrans> expTransList;
	protected final List<GenTrans> genTransList;

	protected final Map<String,AST> imark;

	protected List<AST> assertExpr;

	public Net(Net outer, String label) {
		this.label = label;
		this.outer = outer;

		placeList = new ArrayList<Place>();
		immTransList = new ArrayList<ImmTrans>();
		expTransList = new ArrayList<ExpTrans>();
		genTransList = new ArrayList<GenTrans>();

		child = new HashMap<String,Net>();
		if (outer != null) {
			outer.setChild(label, this);
		}
	
		imark = new HashMap<String,AST>();
		assertExpr = new ArrayList<AST>();
	}

	public Net(String label) {
		this(null, label);
	}

	// getter
	public final Net getOuter() {
		return outer;
	}
	
	public final void setChild(String label, Net net) {
		child.put(label, net);
	}
	
	public final void setIMark(String name, AST value) {
		imark.put(name, value);
	}
	
	public final Map<String,Integer> getIMark() throws JSPNException {
		Map<String,Integer> mark = new HashMap<String,Integer>();
		for (Map.Entry<String, AST> entry : imark.entrySet()) {
			int i = (Integer) entry.getValue().eval(this);
			mark.put(entry.getKey(), i);
		}
		return mark;
	}

	public final boolean containts(String label) {
		return child.containsKey(label);
	}

	public final Net getChild(String label) throws JSPNException {
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
	
	public final List<ImmTrans> getImmTransSet() {
		return immTransList;
	}

	public final List<ExpTrans> getExpTransSet() {
		return expTransList;
	}

	public final List<GenTrans> getGenTransSet() {
		return genTransList;
	}

//	public final int getNumOfGenTrans() {
//		return genTransList.size();
//	}

//	public final int getNumOfImmTrans() {
//		return immTransList.size();
//	}
	
//	public final int getNumOfExpTrans() {
//		return expTransList.size();
//	}
	
//	public final int getNumOfPlace() {
//		return placeList.size();
//	}

	public void setIndexAndSortIMM() {
		
		// sort IMM with Priority
		immTransList.sort(new PriorityComparator());
		
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
	public Place createPlace(String label, int max) throws JSPNException {
		if (this.contains(label)) {
			throw new AlreadyExistException(label);
		}
		Place tmp = new Place(label, max);
		put(label, tmp);
		placeList.add(tmp);
		return tmp;
	}
	
	public final ExpTrans createExpTrans(String label, AST rate) throws JSPNException {
		if (this.contains(label)) {
			throw new AlreadyExistException(label);
		}
		ExpTrans tmp = new ExpTrans(label, rate);
		put(label, tmp);
		expTransList.add(tmp);
		return tmp;
	}

//	public final ExpTrans createExpTrans(String label, double rate) throws JSPNException {
//		return createExpTrans(label, new ASTValue(rate));
//	}

	public final ImmTrans createImmTrans(String label, AST weight) throws JSPNException {
		if (this.contains(label)) {
			throw new AlreadyExistException(label);
		}
		ImmTrans tmp = new ImmTrans(label, weight);
		put(label, tmp);
		immTransList.add(tmp);
		return tmp;
	}

//	public final ImmTrans createImmTrans(String label, double weight) throws JSPNException {
//		return createImmTrans(label, new ASTValue(weight));
//	}

	public final GenTrans createGenTrans(String label, AST dist, GenTransPolicy policy) throws JSPNException {
		if (this.contains(label)) {
			throw new AlreadyExistException(label);
		}
		GenTrans tmp = new GenTrans(label, dist, policy);
		put(label, tmp);
		genTransList.add(tmp);
		return tmp;
	}
	
	//// arc
	
	public final ArcBase createNormalInArc(Place src, Trans dest, AST multi) throws JSPNException {
		for (Arc a : src.getOutArc()) {
			if (a.getDest().equals(dest)) {
				throw new AlreadyExistException(label);
			}
		}
		ArcBase tmp = new InArc(src, dest, multi);
		return tmp;
	}

	public final ArcBase createNormalOutArc(Trans src, Place dest, AST multi) throws JSPNException {
		for (Arc a : src.getOutArc()) {
			if (a.getDest().equals(dest)) {
				throw new AlreadyExistException(label);
			}
		}
		ArcBase tmp = new OutArc(src, dest, multi);
		return tmp;
	}

	public final ArcBase createInhibitArc(Place src, Trans dest, AST multi) throws JSPNException {
		for (Arc a : src.getOutArc()) {
			if (a.getDest().equals(dest)) {
				throw new AlreadyExistException(label);
			}
		}
		ArcBase tmp = new InhibitArc(src, dest, multi);
		return tmp;
	}
	
	/// assert
	
	public final void addAssert(AST assertExpression) {
		this.assertExpr.add(assertExpression);
	}
	
	public final void assertNet() throws JSPNException {
		for (AST a : assertExpr) {
			Object obj = a.eval(this);
			if (obj instanceof Boolean) {
				Boolean b = (Boolean) obj;
				if (b == false) {
					throw new JSPNAssertException("Assert function " + a.toString() + " retures false at mark " + JSPetriNet.markToString(this, this.getCurrentMark()));
				}
			}
		}
	}
}
