package model;

import android.os.Parcel;
import android.os.Parcelable;

public class TimerEvent implements Parcelable {

	private String eventName;
	private long startTime;
	private boolean isBreak;

	TimerEvent(String eventName, long startTime, boolean isBreak){
		this.eventName = eventName;
		this. startTime = startTime;
		this.isBreak = isBreak;
	}

	protected TimerEvent(Parcel in) {
		eventName = in.readString();
		startTime = in.readLong();
		isBreak = in.readByte() != 0;
	}

	public static final Creator<TimerEvent> CREATOR = new Creator<TimerEvent>() {
		@Override
		public TimerEvent createFromParcel(Parcel in) {
			return new TimerEvent(in);
		}

		@Override
		public TimerEvent[] newArray(int size) {
			return new TimerEvent[size];
		}
	};

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(eventName);
		dest.writeLong(startTime);
		dest.writeByte((byte) (isBreak ? 1 : 0));
	}
}
