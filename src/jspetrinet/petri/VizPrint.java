package jspetrinet.petri;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import jspetrinet.exception.JSPNException;
import jspetrinet.graph.Arc;
import jspetrinet.graph.Component;
import jspetrinet.graph.Visitor;

public class VizPrint implements Visitor {

	private final Set<Component> hash;
	private final LinkedList<Component> allnodes;
	private final Net net;

	private PrintWriter bw;

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
			bw.println("\"" + c + "\" [shape = circle, label = \"" + c.getLabel() + "\"];");
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
			try {
				String g = c.toGuardString(net);
				bw.println("\"" + c + "\" [shape = box, label = \"" + c.getLabel() + "\n[" + g + "] \" width=0.8, height=0.2];");
			} catch (JSPNException e) {
				bw.println("\"" + c + "\" [shape = box, label = \"" + c.getLabel() + "\" width=0.8, height=0.2];");
			}
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
			try {
				String g = c.toGuardString(net);
				bw.println("\"" + c + "\" [shape = box, label = \"" + c.getLabel() + "\n[" + g + "] \" width=0.8, height=0.02, style=\"filled,dashed\"];");
			} catch (JSPNException e) {
				bw.println("\"" + c + "\" [shape = box, label = \"" + c.getLabel() + "\" width=0.8, height=0.02, style=\"filled,dashed\"];");
			}
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
			try {
				String g = c.toGuardString(net);
				bw.println("\"" + c + "\" [shape = box, label = \"" + c.getLabel() + "\n[" + g + "] \" width=0.8, height=0.2, style=filled];");
			} catch (JSPNException e) {
				bw.println("\"" + c + "\" [shape = box, label = \"" + c.getLabel() + "\" width=0.8, height=0.2, style=filled];");
			}
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
			int multi;
			Object obj;
			try {
				multi = ac.getMulti(net);
				obj = ac.getFiring().eval(net);
			} catch (JSPNException e1) {
				multi = 0;
				obj = "Error";
			}
			try {
				int firing = (Integer) obj;
				if (multi != 1 || firing != 1) {
					bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\" [label = \"" + multi + "(" + firing + ")\"];");
				} else {
					bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\";");					
				}
			} catch (Exception e) {
				bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\" [label = \"" + multi + "(" + obj + ")\"];");
			}
			hash.add(component);
			ac.getSrc().accept(this);
			ac.getDest().accept(this);
		} else if (component instanceof OutArc) {
			OutArc ac = (OutArc) component;
			Object obj;
			try {
				obj = ac.getFiring().eval(net);
			} catch (JSPNException e1) {
				obj = "Error";
			}
			try {
				int firing = (Integer) obj;
				if (firing != 1) {
					bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\" [label = \"" + firing + "\"];");
				} else {
					bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\";");					
				}
			} catch (Exception e) {
				bw.println("\"" + ac.getSrc() + "\" -> \"" + ac.getDest() + "\" [label = \"" + obj + "\"];");
			}
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
