package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A TimerEvent is what the Timer is using to set the start time and to see if a break image
 * or work image should be displayed. The TimerEvents used for the Timer are stored in an
 * ArrayList in the Timer-class.
 */
public class TimerEvent implements Parcelable {

	private String eventName;
	private long startTime;
	private boolean isBreak;

	/**
	 * Constructor for TimerEvent
	 * @param eventName, String, name for the event. This is to prepare for further development.
	 * @param startTime, long, the start time in milliseconds that the Timer will cound down from.
	 * @param isBreak, boolean, is the TimerEvent a break? If not, it's a work event.
	 */
	TimerEvent(String eventName, long startTime, boolean isBreak){
		this.eventName = eventName;
		this. startTime = startTime;
		this.isBreak = isBreak;
	}

	/**
	 * Constructor to make it possible for TimerEvent to implement Parcelable so the ArrayList with
	 * the active TimerEvents can be stored in a Bundle on rotation etc.
	 * @param in, Parcel
	 */
	protected TimerEvent(Parcel in) {
		eventName = in.readString();
		startTime = in.readLong();
		isBreak = in.readByte() != 0;
	}

	/**
	 * Creator to make it possible for TimerEvent to implement Parcelable so the ArrayList with
	 * the active TimerEvents can be stored in a Bundle on rotation etc.
	 */
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

	/**
	 * @return startTime, long, the start time in milliseconds for the Timer to use
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * A get-method for boolean variable isBreak so the Timer can find out if the TimerEvent
	 * is a break or a work event to set the right image.
	 */
	public boolean isBreak() {
		return isBreak;
	}

	/**
	 * Used to implement Parcelable
	 * @return 0, no description added
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Used to implement Parcelable, this method selects what values should be saved in the Parcel.
	 * @param dest
	 * @param flags
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(eventName);
		dest.writeLong(startTime);
		dest.writeByte((byte) (isBreak ? 1 : 0));
	}
}
