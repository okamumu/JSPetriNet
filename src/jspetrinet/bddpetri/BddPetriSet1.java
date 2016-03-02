package bddpetri;

import graph.Arc;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import marking.Mark;
import bdd.Bdd;
import bdd.BddSet;
import petri.Place;
import petri.Trans;

public final class BddPetriSet1 {
/*	
	private final Bdd<Boolean> baseBdd;
	private final BddSet inTransSet;
	private final BddSet outTransSet;
	private final BddSet reachableSet;
	
	public BddPetriSet1() {
		baseBdd = new Bdd<Boolean>();
		inTransSet = new BddSet(baseBdd, false);
		outTransSet = new BddSet(baseBdd, false);
		reachableSet = new BddSet(baseBdd, false);
	}

	public final BddSet create(Mark init, BddNet net) {
		net.setIndex();
		
		for (Trans tr: net.getImmTransSet().values()) {
			System.out.println(tr + "01");
			inTransSet.union(BddPetriAnalysis.createInTransSet(baseBdd, tr));
			System.out.println(tr + "02" + inTransSet.count());
			outTransSet.union(BddPetriAnalysis.createOutTransSet(baseBdd, tr));
			System.out.println(tr + "03" + outTransSet.count());
		}
		
		BitSet bset = new BitSet();
		BitSet bset2 = new BitSet();
		for (int k=0, i=0; k<net.getTotalBit(); k++, i+=2) {
			bset.set(i);
			bset2.set(i+1);
		}

		try {
			PrintStream out;
			out = new PrintStream("/Users/okamu/Desktop/bdd.dot");
			out.println("digraph { ");
			inTransSet.getTop().accept(new bdd.VizPrint<Boolean>(out));
			out.println("}");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BddSet novisited = new BddSet(baseBdd, false);
		novisited.add(setInitMark(init, net), net.getTotalBit(), 0, 2);

		reachableSet.union(novisited);
		while (!novisited.isEmpty()) {
			BddSet tmp = new BddSet(baseBdd, true);
			System.out.println("1");
			tmp.intersection(novisited);
			System.out.println("2");
			tmp.intersection(inTransSet);
			System.out.println("3");
			tmp.exist(bset);
			System.out.println("4");
			tmp.change(bset2);
			System.out.println("5");
			tmp.intersection(outTransSet);
			System.out.println("6");
			tmp.exist(bset);
			System.out.println("7");
			tmp.change(bset2);
			System.out.println("8");
			
//			try {
//				PrintStream out;
//				out = new PrintStream("/Users/okamu/Desktop/bdd2.dot");
//				out.println("digraph { ");
//				inTransSet.getTop().accept(new bdd.VizPrint<Boolean>(out));
//				out.println("}");
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			tmp.diff(reachableSet);
			novisited = tmp;
			reachableSet.union(novisited);
			System.out.println("step = " + reachableSet.countEven());
		}
		return reachableSet;
	}
	
	private final BitSet setInitMark(Mark m, BddNet net) {
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
	*/
}
