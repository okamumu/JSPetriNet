package bddpetri;

import petri.Net;
import petri.Place;

public final class BddNet extends Net {
	
	private int totalBit;

	public BddNet(BddNet outer, String label) {
		super(outer, label);
	}
	
	// getter
	public int getTotalBit() {
		return totalBit;
	}
	
	@Override
	public void setIndex() {
		super.setIndex();
		int i = 0;
		totalBit = 0;
		for (Place p : placeSet.values()) {
			((BddPlace) p).setBddIndex(i);
			totalBit += ((BddPlace) p).getBits();
			i += 2*((BddPlace) p).getBits();
		}
	}

	@Override
	public final Place createPlace(String label, int max) {
		BddPlace tmp = new BddPlace(label, max);
		placeSet.put(label, tmp);
		return tmp;
	}
	
}
