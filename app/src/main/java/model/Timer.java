package model;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;
import activity.MainActivity;

import com.example.studytimer.R;

/**
 * The Timer class handles everything needed to run the Timer in the app. The ArrayList
 * mTimerEventArrayList holds all the TimerEvents that will be used.
 */
public class Timer {
	private final static String TAG = "Timer";
	private static final long START_TIME_IN_MILLIS_WORK = 1500000; //work will be 25 min
	private static final long START_TIME_IN_MILLIS_BREAK = 300000; //break will be 5 min
	private static final long START_TIME_IN_MILLIS_LONG_BREAK = 1200000; // long break will be 20 min
//	private static final long START_TIME_IN_MILLIS_WORK = 5000; // For testing, 5 seconds
//	private static final long START_TIME_IN_MILLIS_BREAK = 3000; // For testing, 3 seconds

	private MainActivity mMainActivity;
	private CountDownTimer mCountdownTimer;
	private boolean mTimerRunning;
	private long mTimeLeftInMillis;

	private ArrayList<TimerEvent> mTimerEventArrayList = new ArrayList<>();
	private int mActiveTimerEventIndex = 0;

	/**
	 * The constructor for Timer. The TimerEvents that are used in the app will be created here.
	 * @param m, MainActivity is passed as a parameter in the constructor to make it possible for
	 *           Timer to use some methods in MainActivity like updating the text on the start/stop
	 *           button and setting the imageView.
	 */
	public Timer(MainActivity m){
		mMainActivity = m;

		mTimerEventArrayList.add(new TimerEvent("Work", START_TIME_IN_MILLIS_WORK, false));
		mTimerEventArrayList.add(new TimerEvent("Break", START_TIME_IN_MILLIS_BREAK, true));
		mTimerEventArrayList.add(new TimerEvent("Work", START_TIME_IN_MILLIS_WORK, false));
		mTimerEventArrayList.add(new TimerEvent("Break", START_TIME_IN_MILLIS_BREAK, true));
		mTimerEventArrayList.add(new TimerEvent("Work", START_TIME_IN_MILLIS_WORK, false));
		mTimerEventArrayList.add(new TimerEvent("Break", START_TIME_IN_MILLIS_BREAK, true));
		mTimerEventArrayList.add(new TimerEvent("Work", START_TIME_IN_MILLIS_WORK, false));
		mTimerEventArrayList.add(new TimerEvent("Long break", START_TIME_IN_MILLIS_LONG_BREAK, true));


		mTimeLeftInMillis =  mTimerEventArrayList.get(mActiveTimerEventIndex).getStartTime();
	}

	/**
	 * This is a get method to see if the timer is running or not.
	 * @return mTimerRunning, boolean
	 */
	public boolean isTimerRunning() {
		return mTimerRunning;
	}

	/**
	 * This method is used to start the timer and runs until mTimeLeftInMills is 0.
	 */
	public void startTimer(){
		mCountdownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
			/**
			 * Updates the countdown text (25:00 etc.) every second.
			 * @param millisUntilFinished
			 */
			@Override
			public void onTick(long millisUntilFinished) {
				mTimeLeftInMillis = millisUntilFinished;
				updateCountdownText();
			}

			/**
			 * This method runs when the TimerEvent is finished. (Not when the user skips to the
			 * next event. The standard sound for a notification on the users device is played
			 * on finish.
			 */
			@Override
			public void onFinish() {//TODO varför körs onFinish flera gånger när ett event är slut?
				// Play notification sound when work session or break is over
				Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				Ringtone r = RingtoneManager.getRingtone(mMainActivity.getApplicationContext(), notification);
				r.play();
				Log.i(TAG, "Playing notification sound");
				nextRound();
			}
		}.start();

		mTimerRunning = true;
		mMainActivity.setStartStopButtonText(R.string.pause_string);
	}

	/**
	 * Used to pause the timer.
	 */
	public void pauseTimer(){
		if(mCountdownTimer != null){
				mCountdownTimer.cancel();
		}
		mTimerRunning = false;
		mMainActivity.setStartStopButtonText(R.string.start_string);
	}

	/**
	 * Used to reset the timer to the start time for the TimerEvent (25:00, 05:00 etc.).
	 */
	public void resetTimer(){
		resetToStartTime();
	}

	/**
	 * Updates the countdown text (25:00 etc.) and makes sure e.g. five minutes left is displayed
	 * as 05:00 and not 5:00.
	 */
	public void updateCountdownText(){
		int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
		int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

		String timeLeftFormatted = String.format(Locale.getDefault(),
				"%02d:%02d", minutes, seconds);

		mMainActivity.setCountdownText(timeLeftFormatted);
	}

	/**
	 * This method changes the TimerEvent to the next one in the ArrayList of TimerEvents in Timer.
	 * It updates the index of the active timer event so it knows when to start over at the end
	 * of the ArrayList of TimerEvents.
	 */
	public void nextRound(){

		if(mActiveTimerEventIndex == mTimerEventArrayList.size()-1){
			mActiveTimerEventIndex = 0;
		}
		else{
			mActiveTimerEventIndex++;
		}
		resetToStartTime();
		updateCountdownText();

		if(isTimerRunning()){
			pauseTimer();
			startTimer();
		}
		else{
			pauseTimer();
			updateCountdownText();
		}

		mMainActivity.setRandomBreakImageNumber();
		mMainActivity.updateImageView();
	}

	/**
	 * Reset the time left to the start time.
	 */
	public void resetToStartTime(){
		mTimeLeftInMillis = mTimerEventArrayList.get(mActiveTimerEventIndex).getStartTime();
	}

	/**
	 * Used when the start/stop button is pressed.
	 */
	public void pressStartStopButton(){
		if(isTimerRunning()){
			pauseTimer();
		}
		else{
			startTimer();
		}
	}

	/**
	 * Used when the reset button is pressed.
	 */
	public void pressResetButton(){
		if(mTimerRunning){
			pauseTimer();
			resetTimer();
			startTimer();
		}
		else{
			pauseTimer();
			resetTimer();
			updateCountdownText();
		}
	}

	/**
	 * Get method for the mTimerEventArrayList.
	 * @return mTimerEventArrayList, ArrayList
	 */
	public ArrayList<TimerEvent> getTimerEventArrayList() {
		return mTimerEventArrayList;
	}

	/**
	 * Set method for the mTimerEventArrayList. Used when creating from a Bundle after rotation etc.
	 * @param timerEventArrayList, ArrayList
	 */
	public void setTimerEventArrayList(ArrayList<TimerEvent> timerEventArrayList) {
		mTimerEventArrayList = timerEventArrayList;
	}

	/**
	 * Method to get the index of the active TimerEvent.
	 * @return mActiveTimerEventIndex, int
	 */
	public int getActiveTimerEventIndex() {
		return mActiveTimerEventIndex;
	}

	/**
	 * Method to set the active TimerEvent index
	 * @param activeTimerEventIndex, int, the index
	 */
	public void setActiveTimerEventIndex(int activeTimerEventIndex) {
		mActiveTimerEventIndex = activeTimerEventIndex;
	}

	/**
	 * Method to get the boolean to know if the timer is running or not
	 * @return mTimerRunning, boolean
	 */
	public boolean getTimerRunning(){
		return mTimerRunning;
	}

	/**
	 * Method to set the boolean variable that tells if the timer is running or not.
	 * @param timerRunning, boolean
	 */
	public void setTimerRunning(boolean timerRunning) {
		mTimerRunning = timerRunning;
	}

	/**
	 * Method to get the time that is left in milliseconds on the TimerEvent.
	 * @return mTimeLeftInMillis, long
	 */
	public long getTimeLeftInMillis() {
		return mTimeLeftInMillis;
	}

	/**
	 * Method to set the time left in milliseconds on the TimerEvent.
	 * @param timeLeftInMillis, long
	 */
	public void setTimeLeftInMillis(long timeLeftInMillis) {
		mTimeLeftInMillis = timeLeftInMillis;
	}
}
