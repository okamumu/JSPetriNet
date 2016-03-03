package jspetrinet.marking;

public enum TransStatus {
	DISABLE(0),
	ENABLE(1),
	PREEMPTION(2);
	
	private final int value;
	
	TransStatus(int value) {
		this.value = value;
	}
	
	public final int getIntValue() {
		return value;
	}
}
