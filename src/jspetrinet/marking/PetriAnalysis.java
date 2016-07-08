package jspetrinet.marking;

import jspetrinet.ast.ASTEnv;
import jspetrinet.exception.ASTException;
import jspetrinet.graph.Arc;
import jspetrinet.petri.ArcBase;
import jspetrinet.petri.GenTrans;
import jspetrinet.petri.GenTransPolicy;
import jspetrinet.petri.InhibitArc;
import jspetrinet.petri.Net;
import jspetrinet.petri.Place;
import jspetrinet.petri.Trans;

public final class PetriAnalysis {

	public final static TransStatus isEnable(ASTEnv env, Trans tr) throws ASTException {
		Mark m = env.getCurrentMark();
		if (!tr.guardEval(env)) {
			return TransStatus.DISABLE;
		}
		for (Arc arc : tr.getInArc()) {
			Place place = (Place) arc.getSrc();
			if (arc instanceof InhibitArc) {
				InhibitArc narc = (InhibitArc) arc;
				if (m.get(place.getIndex()) >= narc.getMulti(env)) {
					return TransStatus.DISABLE;
				}				
			} else {
				ArcBase narc = (ArcBase) arc;
				if (m.get(place.getIndex()) < narc.getMulti(env)) {
					return TransStatus.DISABLE;
				}
			}
		}
		return TransStatus.ENABLE;
	}

	public final static TransStatus isEnableGenTrans(ASTEnv env, Trans tr) throws ASTException {
		Boolean maybePreemption = false;
		Mark m = env.getCurrentMark();
		if (!tr.guardEval(env)) {
			maybePreemption = true;
		}
		for (Arc arc : tr.getInArc()) {
			Place place = (Place) arc.getSrc();
			if (arc instanceof InhibitArc) {
				InhibitArc narc = (InhibitArc) arc;
				if (m.get(place.getIndex()) >= narc.getMulti(env)) {
					maybePreemption = true;
				}
			} else {
				ArcBase narc = (ArcBase) arc;
				if (m.get(place.getIndex()) < narc.getMulti(env)) {
					return TransStatus.DISABLE;
				}
			}
		}
		if (maybePreemption == true) {
			if (((GenTrans) tr).getPolicy() == GenTransPolicy.PRD) {
				return TransStatus.DISABLE;
			} else {
				return TransStatus.PREEMPTION;
			}
		} else {
			return TransStatus.ENABLE; // enable
		}
	}

	public final static Mark doFiring(Net net, Trans tr) throws ASTException {
		Mark nextMark = new Mark(net.getCurrentMark());
		for (Arc arc : tr.getInArc()) {
			Place place = (Place) arc.getSrc();
			ArcBase arcBase = (ArcBase) arc;
//			if (nextMark.get(place.getIndex()) <= place.getMax()) {
//				nextMark.set(place.getIndex(), nextMark.get(place.getIndex()) - arcBase.firing(net));
//			}
			nextMark.set(place.getIndex(), nextMark.get(place.getIndex()) - arcBase.firing(net));
		}
		for (Arc arc : tr.getOutArc()) {
			Place place = (Place) arc.getDest();
			ArcBase arcBase = (ArcBase) arc;
			nextMark.set(place.getIndex(), nextMark.get(place.getIndex()) + arcBase.firing(net));
//			if (nextMark.get(place.getIndex()) > place.getMax()) {
//				nextMark.set(place.getIndex(), place.getMax() + 1);
//			}
			if (place.getMax() > 0 && nextMark.get(place.getIndex()) > place.getMax()) {
				nextMark.set(place.getIndex(), place.getMax());
			}
		}
		return nextMark;
	}
}
