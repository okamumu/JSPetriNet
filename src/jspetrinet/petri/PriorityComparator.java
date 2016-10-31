package jspetrinet.petri;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Trans> {
	@Override
	public int compare(Trans o1, Trans o2) {
		int p1 = o1.getPriority();
		int p2 = o2.getPriority();
		
		if (p1 == p2) {
			if (o1.isVanishing() == true && o2.isVanishing() == false) {
				return -1;
			} else if (o1.isVanishing() == false && o2.isVanishing() == true) {
				return 1;
			} else {
				return 0;
			}
		} else if (p1 < p2) {
			return 1;
		} else {
			return -1;
		}
	}
}
