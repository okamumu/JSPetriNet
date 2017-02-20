package jspetrinet.sim;

import jspetrinet.marking.Mark;

public final class EventValue {
	private final Mark event;
	private final double time;
	private final boolean stop;
	
	public EventValue(double time, Mark mark){
		this.event = mark;
		this.time = time;
		stop = false;
	}

	public EventValue(double time, Mark mark, boolean stop){
		this.event = mark;
		this.time = time;
		this.stop = false;
	}

	public final Mark getEvent() {
		return event;
	}

	public final double getTime() {
		return time;
	}
	
	public final boolean isStop() {
		return stop;
	}
}
