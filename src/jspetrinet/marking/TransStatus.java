package jspetrinet.marking;

public enum TransStatus {
	DISABLE(0),
	ENABLE(1),
	PREEMPTION(2);
	
	private int value;
	
	TransStatus(int value) {
		this.value = value;
	}
	
	public int getIntValue() {
		return value;
	}
}
