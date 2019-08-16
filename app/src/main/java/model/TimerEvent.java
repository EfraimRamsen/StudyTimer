package model;

public class TimerEvent {

	private String eventName;
	private long startTime;
	private boolean isBreak;

	TimerEvent(String eventName, long startTime, boolean isBreak){
		this.eventName = eventName;
		this. startTime = startTime;
		this.isBreak = isBreak;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean isBreak() {
		return isBreak;
	}

	public void setBreak(boolean aBreak) {
		isBreak = aBreak;
	}
}
