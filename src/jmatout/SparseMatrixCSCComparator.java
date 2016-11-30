package jmatout;

import java.util.Comparator;
import java.util.List;

public class SparseMatrixCSCComparator implements Comparator<List<?>> {

	@Override
	public int compare(List<?> o1, List<?> o2) {
		int j1 = (int) o1.get(1);
		int j2 = (int) o2.get(1);
		if (j1 < j2) {
			return -1;
		} else if (j1 > j2) {
			return 1;
		} else {
			int i1 = (int) o1.get(0);
			int i2 = (int) o2.get(0);
			if (i1 < i2) {
				return -1;
			} else if (i1 > i2) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
