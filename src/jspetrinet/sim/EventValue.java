package jspetrinet.sim;

import jspetrinet.marking.Mark;

public final class EventValue {
	private final Mark eventMarking;
	private final double eventTime;
	
	public EventValue(Mark mark, double time){
		eventMarking = mark;
		eventTime = time;
	}

	public final Mark getEventMarking() {
		return eventMarking;
	}

	public final double getEventTime() {
		return eventTime;
	}
}
