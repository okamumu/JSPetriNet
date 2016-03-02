package bddpetri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import marking.Mark;
import exception.*;
import ast.*;
import bdd.Bdd;
import bdd.BddSet;
import petri.ArcBase;
import petri.Place;
import petri.Trans;
import petri.InhibitArc;
import graph.Arc;

public final class BddPetriAnalysis {
	
	public final static List<Place> extractPlace(ASTEnv env, ASTree f) throws ASTException {
		Set<Place> placeSet = new HashSet<Place>();
		Stack<ASTree> next = new Stack<ASTree>();
		Set<ASTree> visited = new HashSet<ASTree>();
		next.push(f);
		while (!next.empty()) {
			ASTree x = next.pop();
			if (visited.contains(x)) {
				continue;
			}
			if (x instanceof ASTBinaryOperator) {
				ASTBinaryOperator elem = (ASTBinaryOperator) x;
				next.push(elem.getLeft());
				next.push(elem.getRight());
			} else if (x instanceof ASTUnaryOperator) {
				ASTUnaryOperator elem = (ASTUnaryOperator) x;
				next.push(elem.getChild());
			} else if (x instanceof ASTNumOfToken) {
				ASTNumOfToken elem = (ASTNumOfToken) x;
				placeSet.add(elem.getPlace());
			} else if (x instanceof ASTVariable) {
				Object r = env.get(((ASTVariable) x).getLabel());
				if (r instanceof ASTree) {
					placeSet.addAll(extractPlace(env, (ASTree) r));
				}
			}
			visited.add(x);
		}
		return new ArrayList<Place>(placeSet);
	}
	
	public final static BddSet createGuradSet(ASTEnv env, Bdd<Boolean> bdd, ASTree guard) throws ASTException {
		List<Place> placeList = extractPlace(env, guard);
		Mark m = env.getCurrentMark();
		int[] vec = Arrays.copyOf(m.get(), m.get().length);
		Mark tmpm = new Mark(vec);
		BddSet bs = new BddSet(bdd, false);
		createGuardSetP(env, bs, tmpm, 0, placeList, guard);
		env.setCurrentMark(m);
		return bs;
	}

	private final static void createGuardSetP(ASTEnv env, BddSet bs,
			Mark m, int index, List<Place> placeList, ASTree guard) throws ASTException {
		int[] vec = Arrays.copyOf(m.get(), m.get().length);
		Mark tmpm = new Mark(vec);
		if (index < placeList.size()) {
			Place p = placeList.get(index);
			for (int i=0; i<=p.getMax()+1; i++) {
				tmpm.get()[p.getIndex()] = i;
				createGuardSetP(env, bs, tmpm, index+1, placeList, guard);
			}
		} else {
			env.setCurrentMark(tmpm);
			Object retval = guard.eval(env);
			if (retval instanceof Boolean && (Boolean) retval == true) {
				BddSet res = new BddSet(bs.getBdd(), true);
				for (Place p : placeList) {
					BddPlace bp = (BddPlace) p;
					BddSet tmp = new BddSet(bs.getBdd(), false);
					tmp.add(tmpm.get()[bp.getIndex()], bp.getBits(), bp.getBddIndex(), 2);
					res.intersection(tmp);
				}
				bs.union(res);
			}
		}
	}

	public final static BddSet createEnableSet(ASTEnv env, Bdd<Boolean> bdd, Trans tr) throws ASTException {
//		BddSet bset = new BddSet(bdd, true);
		BddSet bset = createGuradSet(env, bdd, tr.getGuard());
		for (Arc arc : tr.getInArc()) {
			BddPlace place = (BddPlace) arc.getSrc();
			BddSet tmp = new BddSet(bdd, false);
			if (arc instanceof InhibitArc) {
				InhibitArc narc = (InhibitArc) arc;
				for (int k = 0; k < narc.getMulti(env); k++) {
					tmp.add(k, place.getBits(), place.getBddIndex(), 2);
				}
			} else {
				ArcBase narc = (ArcBase) arc;
				for (int k = narc.getMulti(env); k <= place.getMax()+1; k++) {
					tmp.add(k, place.getBits(), place.getBddIndex(), 2);
				}
			}
			bset.intersection(tmp);
		}
		return bset;
	}

	public final static BddSet createInhibitSet(ASTEnv env, Bdd<Boolean> bdd, Trans tr) throws ASTException {
		BddSet bset = new BddSet(bdd, false);
		for (Arc arc : tr.getInArc()) {
			BddPlace place = (BddPlace) arc.getSrc();
			if (arc instanceof InhibitArc) {
				InhibitArc narc = (InhibitArc) arc;
				for (int k = narc.getMulti(env); k <= place.getMax()+1; k++) {
					bset.add(k, place.getBits(), place.getBddIndex(), 2);
				}
			}
		}
		return bset;
	}

	public final static BddSet createDisableSet(ASTEnv env, Bdd<Boolean> bdd, Trans tr) throws ASTException {
		BddSet bset = new BddSet(bdd, false);
		for (Arc arc : tr.getInArc()) {
			BddPlace place = (BddPlace) arc.getSrc();
			if (!(arc instanceof InhibitArc)) {
				ArcBase narc = (ArcBase) arc;
				for (int k = 0; k < narc.getMulti(env) && k <= place.getMax()+1; k++) {
					bset.add(k, place.getBits(), place.getBddIndex(), 2);
				}
			}
		}
		return bset;
	}

	public final static BddSet createInTransSet(ASTEnv env, Bdd<Boolean> bdd, Trans tr) throws ASTException {
//		BddSet bset = new BddSet(bdd, true);
		BddSet bset = createGuradSet(env, bdd, tr.getGuard());
		Mark m = env.getCurrentMark();
		for (Arc arc : tr.getInArc()) {
			if (!(arc instanceof InhibitArc)) {
				ArcBase arcBase = (ArcBase) arc;
				BddPlace place = (BddPlace) arc.getSrc();
				BddSet tmp = new BddSet(bdd, false);

				int[] vec = Arrays.copyOf(m.get(), m.get().length);
				Mark tmpm = new Mark(vec);
				env.setCurrentMark(tmpm);

				for (Place p : extractPlace(env, arcBase.getFiring())) {
					if (!p.equals(place)) {
						throw new TypeMismatch();
					}
				}

				for (int k=arcBase.getMulti(env); k<=place.getMax()+1; k++) {
					tmpm.get()[place.getIndex()] = k;
					if (k != place.getMax()+1) {
						BitSet b = BddSet.toBitSet(k, k - arcBase.firing(env), place.getBits());
						tmp.add(b, 2*place.getBits(), place.getBddIndex(), 1);
					} else {
						BitSet b = BddSet.toBitSet(k, k, place.getBits());
						tmp.add(b, 2*place.getBits(), place.getBddIndex(), 1);						
					}
				}
				bset.intersection(tmp);
			}
		}
		env.setCurrentMark(m);
		return bset;
	}

	public final static BddSet createOutTransSet(ASTEnv env, Bdd<Boolean> bdd, Trans tr) throws ASTException {
		BddSet bset = new BddSet(bdd, true);
		Mark m = env.getCurrentMark();
		for (Arc arc : tr.getOutArc()) {
			int[] vec = Arrays.copyOf(m.get(), m.get().length);
			Mark tmpm = new Mark(vec);
			BddPlace place = (BddPlace) arc.getDest();
			BddSet tmp = new BddSet(bdd, false);
			env.setCurrentMark(tmpm);

			ArcBase arcBase = (ArcBase) arc;
			for (Place p : extractPlace(env, arcBase.getFiring())) {
				if (!p.equals(place)) {
					throw new TypeMismatch();
				}
			}
			for (int k=0; k<=place.getMax()+1; k++) {
				tmpm.get()[place.getIndex()] = k;
				BitSet b = BddSet.toBitSet(k, k + arcBase.firing(env), place.getBits());
				tmp.add(b, 2*place.getBits(), place.getBddIndex(), 1);
			}
			bset.intersection(tmp);
		}
		env.setCurrentMark(m);
		return bset;
	}

	private final static BddSet inFiringSet(Bdd<Boolean> bdd, Trans tr, BddSet r, BddSet f) {
		BddSet bs = new BddSet(bdd, true);
		bs.intersection(r);
		bs.intersection(f);
		for (Arc arc : tr.getInArc()) {
			BddPlace place = (BddPlace) arc.getSrc();
			BitSet b = new BitSet();
			BitSet m = new BitSet();
			for (int k=0, i=place.getBddIndex(); k<place.getBits(); k++, i+=2) {
				b.set(i);
				m.set(i+1);
			}
			bs.exist(b);
			bs.change(m);
		}
		return bs;
	}

	private final static BddSet outFiringSet(Bdd<Boolean> bdd, Trans tr, BddSet r, BddSet f) {
		BddSet bs = new BddSet(bdd, true);
		bs.intersection(r);
		bs.intersection(f);
		for (Arc arc : tr.getOutArc()) {
			BddPlace place = (BddPlace) arc.getDest();
			BitSet b = new BitSet();
			BitSet m = new BitSet();
			for (int k=0, i=place.getBddIndex(); k<place.getBits(); k++, i+=2) {
				b.set(i);
				m.set(i+1);
			}
			bs.exist(b);
			bs.change(m);
		}
		return bs;
	}

	public final static BddSet firingSet(Bdd<Boolean> bdd, Trans tr, BddSet r, BddSet in, BddSet out) {
		return outFiringSet(bdd, tr, inFiringSet(bdd, tr, r, in), out);
	}

}
