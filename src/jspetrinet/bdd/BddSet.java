package bdd;

import java.math.BigInteger;
import java.util.BitSet;

public class BddSet {
	
	private Bdd<Boolean> bdd;
	private final AndOperator andOperator;
	private final OrOperator orOperator;
	private final DiffOperator diffOperator;
	private final SelectOperator<Boolean> existOperator;
	private final SelectOperator<Boolean> forallOperator;
	private final ChangeOperator<Boolean> changeOperator;
	private final Count cntOperator;
	private final CountEven cnt2Operator;
	
	private Vertex top;
	
	static public final BitSet toBitSet(int x, int bits) {
		BitSet b = new BitSet();
		for (int i=0; i<bits; i++, x>>=1) {
			if (x % 2 == 1) {
				b.set(i);
			}
		}
		return b;
	}

	static public final BitSet toBitSet(int x, int y, int bits) {
		BitSet b = new BitSet();
		for (int i=0, j=0; i<bits; i++, j+=2, x>>=1, y>>=1) {
			if (x % 2 == 1) {
				b.set(j);
			}
			if (y % 2 == 1) {
				b.set(j+1);
			}
		}
		return b;
	}

	public BddSet(Bdd<Boolean> bdd, boolean value) {
		this.bdd = bdd;
		top = bdd.createTerm(value);
		andOperator = new AndOperator();
		orOperator = new OrOperator();
		diffOperator = new DiffOperator();
		existOperator = new SelectOperator<Boolean>(orOperator);
		forallOperator = new SelectOperator<Boolean>(andOperator);
		changeOperator = new ChangeOperator<Boolean>();
		cntOperator = new Count();
		cnt2Operator = new CountEven();
	}
	
	public BddSet(Bdd<Boolean> bdd, Vertex v) {
		this.bdd = bdd;
		top = v;
		andOperator = new AndOperator();
		orOperator = new OrOperator();
		diffOperator = new DiffOperator();
		existOperator = new SelectOperator<Boolean>(orOperator);
		forallOperator = new SelectOperator<Boolean>(andOperator);
		changeOperator = new ChangeOperator<Boolean>();
		cntOperator = new Count();
		cnt2Operator = new CountEven();
	}

	// getter
	public final Bdd<Boolean> getBdd() {
		return bdd;
	}
	
	public final Vertex getTop() {
		return top;
	}
	
	public final void setTop(Vertex top) {
		this.top = top;
	}

	public final BigInteger count() {
		return cntOperator.count(top);
	}

	public final BigInteger countEven() {
		return cnt2Operator.count(top);
	}

	public final void add(int v, int bits, int index, int skip) {
		Vertex tmp = bdd.createTerm(true);
		Vertex zero = bdd.createTerm(false);
		for (int i=0, j=index; i<bits; i++, j+=skip) {
			if (v % 2 == 1) {
				tmp = bdd.createNonTerm(j, zero, tmp);
			} else {
				tmp = bdd.createNonTerm(j, tmp, zero);
			}
			v >>= 1;
		}
		top = orOperator.apply(top, tmp, bdd);
	}

	public final void add(BitSet b, int bits, int index, int skip) {
		Vertex tmp = bdd.createTerm(true);
		Vertex zero = bdd.createTerm(false);
		for (int i=0, j=index; i<bits; i++, j+=skip) {
//		for (int i=0, j=index; i<bits && i<b.size(); i++, j+=skip) {
			if (b.get(i) == true) {
				tmp = bdd.createNonTerm(j, zero, tmp);
			} else {
				tmp = bdd.createNonTerm(j, tmp, zero);
			}
		}
		top = orOperator.apply(top, tmp, bdd);
	}
	
	@SuppressWarnings("unchecked")
	public final boolean isEmpty() {
		if (top instanceof Terminal<?>) {
			Terminal<Boolean> term = (Terminal<Boolean>) top;
			if (term.getValue() == false) {
				return true;
			}
		}
		return false;
	}

	public final void union(BddSet bs) {
		top = orOperator.apply(top, bs.getTop(), bdd);
	}

	public final void intersection(BddSet bs) {
		top = andOperator.apply(top, bs.getTop(), bdd);
	}

	public final void diff(BddSet bs) {
		top = diffOperator.apply(top, bs.getTop(), bdd);
	}

	public final void exist(BitSet m) {
		top = existOperator.apply(m, top, bdd);
	}

	public final void forall(BitSet m) {
		top = forallOperator.apply(m, top, bdd);
	}

	public final void change(BitSet m) {
		top = changeOperator.apply(m, top, bdd);
	}
}
