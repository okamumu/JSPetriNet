package jspetrinet.petri;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import jspetrinet.ast.AST;
import jspetrinet.ast.ASTEnv;
import jspetrinet.exception.JSPNException;
import jspetrinet.exception.JSPNExceptionType;
import jspetrinet.graph.Arc;
import jspetrinet.graph.Component;
import jspetrinet.graph.Visitor;

public class VizPrint implements Visitor {

	private final Set<Component> hash;
	private final LinkedList<Component> allnodes;
	private final Net net;

	private PrintWriter bw;
	
	private static String ln = "\n";
	private static String placeFMT = "\"%s\" [shape = circle, label = \"%s\"];" + ln;
	private static String expFMT = "\"%s\" [shape = box, label = \"%s\" width=0.8, height=0.2];" + ln;
	private static String immFMT = "\"%s\" [shape = box, label = \"%s\" width=0.8, height=0.02, style=\"filled,dashed\"];" + ln;
	private static String genFMT = "\"%s\" [shape = box, label = \"%s\" width=0.8, height=0.2, style=filled];" + ln;
	private static String arcFMT = "\"%s\" -> \"%s\" [label = \"%s\"];" + ln;
	private static String harcFMT = "\"%s\" -> \"%s\" [label = \"%s\", arrowhead=odot];" + ln;

	private String makeTransLabel(Trans tr) {
		String label = tr.getLabel();

		// guard
		AST guard = tr.getGuard();
		if (guard != null) {
			try {
				Object obj = guard.eval(net);
				if (obj instanceof Boolean) {
					if ((Boolean) obj != true) {
						label += ln + "[" + obj.toString() + "]";
					}
				} else {
					label += ln + "[" + obj.toString() + "]";
				}
			} catch (JSPNException e) {
				label += ln + "[" + guard.toString() + "]";
			}
		}
		
		// update
		AST update = tr.getUpdate();
		if (update != null) {
			label += ln + "{" + update.toString() + "}";
		}
		
		return label;
	}

	private String makeArcLabel(ArcBase arc) {
		Object obj;
		try {
			obj = arc.getMulti().eval(net);
		} catch (JSPNException e) {
			return arc.getMulti().toString();
		}
		int m;
		if (obj instanceof Integer) {
			m = (Integer) obj;
		} else if (obj instanceof Double) {
			m = ((Double) obj).intValue();
		} else {
			return obj.toString();
		}
		if (m != 1) {
			return Integer.toString(m);
		} else {
			return "";
		}
	}

	public VizPrint(Net net) {
		hash = new HashSet<Component>();
		allnodes = new LinkedList<Component>();
		this.net = net;
		net.setCurrentMark(null);
	}
	
	public void toviz(PrintWriter bw) {
		this.bw = bw;
		hash.clear();
		allnodes.clear();
		allnodes.addAll(net.getAllComponent());
		while (!allnodes.isEmpty()) {
			Component c = allnodes.getFirst();
			bw.println("digraph { layout=dot; overlap=false; splines=true; node [fontsize=10];");
			this.visit(c);
			bw.println("}");
			bw.flush();
		}
	}

	@Override
	public final void visit(Component component) {
		if (hash.contains(component)) {
			return;
		}
		if (component instanceof Place) {
			Place c =  (Place) component;
			bw.printf(placeFMT, c, c.getLabel());
			hash.add(component);
			allnodes.remove(component);
			for (Arc a : c.getInArc()) {
				a.accept(this);
			}
			for (Arc a : c.getOutArc()) {
				a.accept(this);
			}
		} else if (component instanceof ExpTrans) {
			ExpTrans c =  (ExpTrans) component;
			bw.printf(expFMT, c, this.makeTransLabel(c));
			hash.add(component);
			allnodes.remove(component);
			for (Arc a : c.getInArc()) {
				a.accept(this);
			}
			for (Arc a : c.getOutArc()) {
				a.accept(this);
			}
		} else if (component instanceof ImmTrans) {
			ImmTrans c =  (ImmTrans) component;
			bw.printf(immFMT, c, this.makeTransLabel(c));
			hash.add(component);
			allnodes.remove(component);
			for (Arc a : c.getInArc()) {
				a.accept(this);
			}
			for (Arc a : c.getOutArc()) {
				a.accept(this);
			}
		} else if (component instanceof GenTrans) {
			GenTrans c =  (GenTrans) component;
			bw.printf(genFMT, c, this.makeTransLabel(c));
			hash.add(component);
			allnodes.remove(component);
			for (Arc a : c.getInArc()) {
				a.accept(this);
			}
			for (Arc a : c.getOutArc()) {
				a.accept(this);
			}
		} else if (component instanceof InArc) {
			InArc ac = (InArc) component;
			bw.printf(arcFMT, ac.getSrc(), ac.getDest(), this.makeArcLabel(ac));
			hash.add(component);
			ac.getSrc().accept(this);
			ac.getDest().accept(this);
		} else if (component instanceof OutArc) {
			OutArc ac = (OutArc) component;
			bw.printf(arcFMT, ac.getSrc(), ac.getDest(), this.makeArcLabel(ac));
			hash.add(component);
			ac.getSrc().accept(this);
			ac.getDest().accept(this);
		} else if (component instanceof InhibitArc) {
			InhibitArc ac = (InhibitArc) component;			
			bw.printf(harcFMT, ac.getSrc(), ac.getDest(), this.makeArcLabel(ac));
			hash.add(component);
			ac.getSrc().accept(this);
			ac.getDest().accept(this);
		}
	}
}
