package jspetrinet.graph;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

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
		if (component instanceof Node) {
			Node nc =  (Node) component;
			bw.println("\"" + nc + "\" [shape = circle, label = \""
					+ nc.getLabel() + "\"];");
			hash.add(component);
			for (Arc a : nc.getInArc()) {
				a.accept(this);
			}
			for (Arc a : nc.getOutArc()) {
				a.accept(this);
			}
		} else if (component instanceof Arc) {
			Arc ac = (Arc) component;			
			bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\";");
			hash.add(component);
			ac.getSrc().accept(this);
			ac.getDest().accept(this);
		}
	}
}
