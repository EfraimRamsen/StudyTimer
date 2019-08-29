package model;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Locale;
import activity.MainActivity;

import com.example.studytimer.R;

public class Timer {

	private static final long START_TIME_IN_MILLIS_WORK = 1500000; //25 min
//	private static final long START_TIME_IN_MILLIS_BREAK = 300000; //5 min
	private static final long START_TIME_IN_MILLIS_BREAK = 3000; // For testing, 3 seconds
	private static final long START_TIME_IN_MILLIS_LONG_BREAK = 1200000; // 20 min

	private MainActivity mMainActivity;
	private CountDownTimer mCountdownTimer;
	private boolean mTimerRunning;
	private long mTimeLeftInMillis;

	private ArrayList<TimerEvent> mTimerEventArrayList = new ArrayList<>();
	private int mActiveTimerEventIndex = 0;

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

	public boolean isTimerRunning() {
		return mTimerRunning;
	}

	public void startTimer(){
		mCountdownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				mTimeLeftInMillis = millisUntilFinished;
				updateCountdownText();
			}

			@Override
			public void onFinish() {
				// Play notification sound when work session or break is over
				Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				Ringtone r = RingtoneManager.getRingtone(mMainActivity.getApplicationContext(), notification);
				r.play();

				nextRound();

			}
		}.start();

		mTimerRunning = true;
		mMainActivity.setStartStopButtonText(R.string.pause_string);
	}

	public void pauseTimer(){
		if(mCountdownTimer != null){
				mCountdownTimer.cancel();
		}
		mTimerRunning = false;
		mMainActivity.setStartStopButtonText(R.string.start_string);
	}

	public void resetTimer(){
		resetToStartTime();
	}

	public void updateCountdownText(){
		int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
		int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

		String timeLeftFormatted = String.format(Locale.getDefault(),
				"%02d:%02d", minutes, seconds);

		mMainActivity.setCountdownText(timeLeftFormatted);
	}

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
//			nextRound();
			startTimer();
		}
		else{
			pauseTimer();
//			nextRound();
			updateCountdownText();
		}

		mMainActivity.setRandomBreakImageNumber();
		mMainActivity.updateImageView();
	}

	public void resetToStartTime(){
		mTimeLeftInMillis = mTimerEventArrayList.get(mActiveTimerEventIndex).getStartTime();
	}

	public void pressStartStopButton(){
		if(isTimerRunning()){
			pauseTimer();
		}
		else{
			startTimer();
		}
	}

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

	public void pressSkipButton(){
		nextRound();
//		if(isTimerRunning()){
//			pauseTimer();
//			nextRound();
//			startTimer();
//		}
//		else{
//			pauseTimer();
//			nextRound();
//			updateCountdownText();
//		}
	}

	public ArrayList<TimerEvent> getTimerEventArrayList() {
		return mTimerEventArrayList;
	}

	public void setTimerEventArrayList(ArrayList<TimerEvent> timerEventArrayList) {
		mTimerEventArrayList = timerEventArrayList;
	}

	public int getActiveTimerEventIndex() {
		return mActiveTimerEventIndex;
	}

	public void setActiveTimerEventIndex(int activeTimerEventIndex) {
		mActiveTimerEventIndex = activeTimerEventIndex;
	}

	public boolean getTimerRunning(){
		return mTimerRunning;
	}

	public void setTimerRunning(boolean timerRunning) {
		mTimerRunning = timerRunning;
	}

	public long getTimeLeftInMillis() {
		return mTimeLeftInMillis;
	}

	public void setTimeLeftInMillis(long timeLeftInMillis) {
		mTimeLeftInMillis = timeLeftInMillis;
	}
}
