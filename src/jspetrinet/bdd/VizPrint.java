package bdd;

import java.io.PrintStream;
import java.util.Set;
import java.util.HashSet;

public final class VizPrint<T> implements Visitor {

	private final PrintStream bw;
	private final Set<Vertex> vertexSet;
		
	public VizPrint(PrintStream bw) {
		this.bw = bw;
		vertexSet = new HashSet<Vertex>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(Vertex v) {
		if (vertexSet.contains(v)) {
			return;
		}
		
		if (v instanceof Terminal<?>) {
			Terminal<T> term = (Terminal<T>) v;
			bw.println("\"" + term + "\" [shape = square, label = \"" 
					+ term.getValue() + "\"];");
			vertexSet.add(v);
		} else {
			NonTerminal term = (NonTerminal) v;
			bw.println("\"" + term + "\" [shape = circle, label = \""
					+ term.getIndex() + "\"];");
			bw.println("\"" + term + "\" -> \"" + term.getLow()
					+ "\" [style = dotted];");
			term.getLow().accept(this);
			bw.println("\"" + term + "\" -> \"" + term.getHigh() + "\";");
			term.getHigh().accept(this);
			vertexSet.add(v);
		}
	}

}
