package jspetrinet.petri;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import jspetrinet.graph.Arc;
import jspetrinet.graph.Component;
import jspetrinet.graph.Visitor;

public class VizPrint implements Visitor {

	private final Set<Component> hash;
	private final PrintStream bw;

	public VizPrint(PrintStream bw) {
		hash = new HashSet<Component>();
		this.bw = bw;
	}

	@Override
	public final void visit(Component component) {
		if (hash.contains(component)) {
			return;
		}
		if (component instanceof Place) {
			Place c =  (Place) component;
			bw.println("\"" + c + "\" [shape = circle, label = \"" + c.getLabel() + "\"];");
			hash.add(component);
			for (Arc a : c.getInArc()) {
				a.accept(this);
			}
			for (Arc a : c.getOutArc()) {
				a.accept(this);
			}
		} else if (component instanceof ExpTrans) {
			ExpTrans c =  (ExpTrans) component;
			bw.println("\"" + c + "\" [shape = box, label = \"" + c.getLabel() + "\" width=0.8, height=0.2];");
			hash.add(component);
			for (Arc a : c.getInArc()) {
				a.accept(this);
			}
			for (Arc a : c.getOutArc()) {
				a.accept(this);
			}
		} else if (component instanceof ImmTrans) {
			ImmTrans c =  (ImmTrans) component;
			bw.println("\"" + c + "\" [shape = box, label = \"" + c.getLabel() + "\" width=0.8, height=0.02, style=\"filled,dashed\"];");
			hash.add(component);
			for (Arc a : c.getInArc()) {
				a.accept(this);
			}
			for (Arc a : c.getOutArc()) {
				a.accept(this);
			}
		} else if (component instanceof GenTrans) {
			GenTrans c =  (GenTrans) component;
			bw.println("\"" + c + "\" [shape = box, label = \"" + c.getLabel() + "\" width=0.8, height=0.2, style=filled];");
			hash.add(component);
			for (Arc a : c.getInArc()) {
				a.accept(this);
			}
			for (Arc a : c.getOutArc()) {
				a.accept(this);
			}
		} else if (component instanceof InArc) {
			InArc ac = (InArc) component;
			bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\";");
			hash.add(component);
			ac.getSrc().accept(this);
			ac.getDest().accept(this);
		} else if (component instanceof OutArc) {
			OutArc ac = (OutArc) component;			
//			bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\" [color=red];");
			bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\";");
			hash.add(component);
			ac.getSrc().accept(this);
			ac.getDest().accept(this);
		} else if (component instanceof InhibitArc) {
			InhibitArc ac = (InhibitArc) component;			
			bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\" [arrowhead=odot];");
			hash.add(component);
			ac.getSrc().accept(this);
			ac.getDest().accept(this);
		}
	}
}
