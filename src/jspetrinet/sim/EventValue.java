package jspetrinet.sim;

import jspetrinet.marking.Mark;

public class EventValue {
	private Mark eventMarking;
	private double eventTime;
	
	public EventValue(Mark mark, double time){
		eventMarking = mark;
		eventTime = time;
	}

	public Mark getEventMarking() {
		return eventMarking;
	}

	public double getEventTime() {
		return eventTime;
	}
}
