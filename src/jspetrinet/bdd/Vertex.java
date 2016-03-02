package bdd;

abstract public class Vertex {
	
	abstract public void accept(Visitor visitor);
	abstract public boolean isTerminal();
	abstract public int getIndex();

}

