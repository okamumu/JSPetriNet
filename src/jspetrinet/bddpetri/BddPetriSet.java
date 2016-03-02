package bddpetri;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import exception.ASTException;
import marking.Mark;
import bdd.Bdd;
import bdd.BddSet;
import petri.Place;
import petri.Trans;

public final class BddPetriSet {

	private final Bdd<Boolean> baseBdd;
	private final Map<Trans,BddSet> enableSet;
	private final Map<Trans,BddSet> inTransSet;
	private final Map<Trans,BddSet> outTransSet;
	private final BddSet reachableSet;
	private final BddSet reachableNonImmSet;
	
	public BddPetriSet() {
		enableSet = new HashMap<Trans,BddSet>();
		inTransSet = new HashMap<Trans,BddSet>();
		outTransSet = new HashMap<Trans,BddSet>();
		baseBdd = new Bdd<Boolean>();
		reachableSet = new BddSet(baseBdd, false);
		reachableNonImmSet = new BddSet(baseBdd, false);
	}

	public final BddSet create(Mark init, BddNet net) throws ASTException {
		net.setIndex();
		net.setCurrentMark(init);
		
		BddSet novisited = new BddSet(baseBdd, false);
		novisited.add(setInitMark(init, net), net.getTotalBit(), 0, 2);

		System.out.println("Making in/out sets for imm...");
		for (Trans tr: net.getImmTransSet().values()) {
			System.out.println(" Trans " + tr.getLabel());
			enableSet.put(tr, BddPetriAnalysis.createEnableSet(net, baseBdd, tr));
			inTransSet.put(tr, BddPetriAnalysis.createInTransSet(net, baseBdd, tr));
			outTransSet.put(tr, BddPetriAnalysis.createOutTransSet(net, baseBdd, tr));
		}
		System.out.println("Done");

		System.out.println("Making in/out sets for exp...");
		for (Trans tr: net.getExpTransSet().values()) {
			System.out.println(" Trans " + tr.getLabel());
			enableSet.put(tr, BddPetriAnalysis.createEnableSet(net, baseBdd, tr));
			inTransSet.put(tr, BddPetriAnalysis.createInTransSet(net, baseBdd, tr));
			outTransSet.put(tr, BddPetriAnalysis.createOutTransSet(net, baseBdd, tr));
		}
		System.out.println("Done");

		System.out.println("Making in/out sets for gen...");
		for (Trans tr: net.getGenTransSet().values()) {
			System.out.println(" Trans " + tr.getLabel());
			enableSet.put(tr, BddPetriAnalysis.createEnableSet(net, baseBdd, tr));
			inTransSet.put(tr, BddPetriAnalysis.createInTransSet(net, baseBdd, tr));
			outTransSet.put(tr, BddPetriAnalysis.createOutTransSet(net, baseBdd, tr));
		}
		System.out.println("Done");

		reachableSet.union(novisited);
		while (!novisited.isEmpty()) {
			BddSet bs = new BddSet(baseBdd, false);
			BddSet vs = new BddSet(baseBdd, novisited.getTop());
			for (Trans tr: net.getImmTransSet().values()) {
				BddSet tmp = BddPetriAnalysis.firingSet(baseBdd, tr, novisited,
						inTransSet.get(tr), outTransSet.get(tr));
//				System.out.println(tr.getLabel() + " " + tmp.count());
				bs.union(tmp);
				vs.diff(enableSet.get(tr));
			}
			for (Trans tr: net.getExpTransSet().values()) {
				BddSet tmp = BddPetriAnalysis.firingSet(baseBdd, tr, vs,
						inTransSet.get(tr), outTransSet.get(tr));
//				System.out.println(tr.getLabel() + " " + tmp.count());
				bs.union(tmp);
			}
			for (Trans tr: net.getGenTransSet().values()) {
				BddSet tmp = BddPetriAnalysis.firingSet(baseBdd, tr, vs,
						inTransSet.get(tr), outTransSet.get(tr));
//				System.out.println(tr.getLabel() + " " + tmp.count());
				bs.union(tmp);
			}
			bs.diff(reachableSet);
			novisited = bs;
			reachableSet.union(novisited);
			reachableNonImmSet.union(vs);
			System.out.print("state = " + reachableSet.countEven());
			System.out.println(" expstate = " + reachableNonImmSet.countEven());
		}
		return reachableSet;
	}
	
	public static final BitSet setInitMark(Mark m, BddNet net) {
		BitSet b = new BitSet();
		for (Place place: net.getPlaceSet().values()) {
			BddPlace bplace = (BddPlace) place;
			int x = m.get(bplace.getIndex());
			for (int i=0, j=bplace.getBddIndex()/2; i<bplace.getBits(); i++, j++, x>>=1) {
				if (x % 2 == 1) {
					b.set(j);
				}
			}
		}
		return b;
	}
}
