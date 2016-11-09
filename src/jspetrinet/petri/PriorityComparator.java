package jspetrinet.petri;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Trans> {
	@Override
	public int compare(Trans o1, Trans o2) {
//		if (o1 != null && o2 != null) {
			int p1 = o1.getPriority();
			int p2 = o2.getPriority();
			if (p1 == p2) {
				boolean v1 = o1.canVanishing();
				boolean v2 = o2.canVanishing();
				if (v1 == v2) { // if (v1 == true && v2 == false) {
					return 0;
				} else if (v1) {
					return 1;
				} else {
					return -1;
				}
			} else if (p1 < p2) {
				return 1;
			} else { // if (p1 > p2) {
				return -1;
			}
//		} else if (o2 != null) { // if (o1 == null && o2 != null) {
//			return -1;
//		} else if (o1 != null) {
//			return 1;
//		} else { // if (o1 == null && o2 == null) {
//			return 0;
//		}
	}
}
