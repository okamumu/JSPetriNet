package jspetrinet.marking;

import java.util.Arrays;

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

	public final static Mark doFiring(Net env, Trans tr) throws ASTException {
		Mark m = env.getCurrentMark();
		int[] nextVec = Arrays.copyOf(m.get(), m.get().length);
		for (Arc arc : tr.getInArc()) {
			Place place = (Place) arc.getSrc();
			ArcBase arcBase = (ArcBase) arc;
			if (nextVec[place.getIndex()] <= place.getMax()) {
				nextVec[place.getIndex()] -= arcBase.firing(env);
			}
		}
		for (Arc arc : tr.getOutArc()) {
			Place place = (Place) arc.getDest();
			ArcBase arcBase = (ArcBase) arc;
			nextVec[place.getIndex()] += arcBase.firing(env);
			if (nextVec[place.getIndex()] > place.getMax()) {
				nextVec[place.getIndex()] = place.getMax() + 1;
			}
		}
		return new Mark(env.markToString(nextVec), nextVec);
	}
}
