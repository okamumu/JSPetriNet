package bddpetri;

import petri.Place;

public final class BddPlace extends Place {
	
	private int bddIndex;
	private int bit;

	public BddPlace(String label, int max) {
		super(label, max);
		bit = bits((Integer) max + 1);
	}
	
	// getter
	public final int getBddIndex() {
		return bddIndex;
	}
	
	public final void setBddIndex(int index) {
		this.bddIndex = index;
	}
	
	public final int getBits() {
		return bit;
	}
	
	@Override
	public void setMax(int max) {
		super.setMax(max);
		bit = bits((Integer) max + 1);
	}

	private int bits(int max) {
		int i = 0;
		while (max != 0) {
			max >>= 1;
			i++;
		}
		return i;
	}

}
