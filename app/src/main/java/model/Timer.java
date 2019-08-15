package model;

import android.os.CountDownTimer;

import java.util.Locale;
import activity.MainActivity;

import com.example.studytimer.R;

public class Timer {

	private static final long START_TIME_IN_MILLIS_WORK = 1500000; //25 min
	private static final long START_TIME_IN_MILLIS_BREAK = 300000; //5 min
	private static final long START_TIME_IN_MILLIS_LONG_BREAK = 120000; // 20 min

	private static final int TIMER_TYPE_WORK = 0;
	private static final int TIMER_TYPE_BREAK = 1;
	private static final int TIMER_TYPE_LONG_BREAK = 2;

	private MainActivity mMainActivity;
	private CountDownTimer mCountdownTimer;

	private boolean mTimerRunning;
	private long mTimeLeftInMillis = START_TIME_IN_MILLIS_WORK;

	private int mActiveTimerType = TIMER_TYPE_WORK;
	private int mCounter = 0;

	//section int (första 25 min = section 1, paus 5 min section 2, nästa 25 min = 3, paus = 4 ... efter 4 jobb och 3 paus blir det lång paus
	//status = work, break, longbreak


	public Timer(MainActivity m){
		mMainActivity = m;
	}

	public boolean isTimerRunning() {
		return mTimerRunning;
	}

	public void setTimerRunning(boolean timerRunning) {
		mTimerRunning = timerRunning;
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
//				mTimerRunning = false;
//				mMainActivity.setStartStopButtonText(R.string.start);
				nextRound();
			}
		}.start();

		mTimerRunning = true;
		mMainActivity.setStartStopButtonText(R.string.pause);
	}

	public void pauseTimer(){
		if(mCountdownTimer != null){
				mCountdownTimer.cancel();
		}
		mTimerRunning = false;
		mMainActivity.setStartStopButtonText(R.string.start);


	}

	public void resetTimer(){
		mTimeLeftInMillis = START_TIME_IN_MILLIS_WORK;
		updateCountdownText();

	}

	public void updateCountdownText(){
		int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
		int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

		String timeLeftFormatted = String.format(Locale.getDefault(),
				"%02d:%02d", minutes, seconds);

		mMainActivity.setCountdownText(timeLeftFormatted);
	}

	public void nextRound(){
		if(mCounter == 7){
			mCounter = 0;
		}
		else{
			mCounter++;
		}

		switch(mCounter){
			case 0:
			case 2:
			case 4:
			case 6:
				mActiveTimerType = TIMER_TYPE_WORK;
				mTimeLeftInMillis = START_TIME_IN_MILLIS_WORK;
				break;
			case 1:
			case 3:
			case 5:
				mActiveTimerType = TIMER_TYPE_BREAK;
				mTimeLeftInMillis = START_TIME_IN_MILLIS_BREAK;
				break;
			case 7:
				mActiveTimerType = TIMER_TYPE_LONG_BREAK;
				mTimeLeftInMillis = TIMER_TYPE_LONG_BREAK;
				break;
			default:
				System.out.println("ERROR mCounter switch");
		}

		updateCountdownText();
	}



//	public void startStop() {
//		if(timerRunning){
//			stopTimer();
//		}
//		else{
//			startTimer();
//		}
//	}
//
//	public void startTimer(){
//		countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
//			@Override
//			public void onTick(long millisUntilFinished) {
//				timeLeftInMilliseconds = millisUntilFinished;
//				updateTimer();
//			}
//
//			@Override
//			public void onFinish() {
//
//			}
//		}.start();
//
//		mStartStopButton.setText(R.string.pause);
//		timerRunning = true;
//	}
//
//	public void stopTimer() {
//		countDownTimer.cancel();
//		mStartStopButton.setText(R.string.start);
//		timerRunning = false;
//	}
//
//	public void updateTimer(){
//		int minutes = (int) timeLeftInMilliseconds / 60000;
//		int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;
//
//		String timeLeftText;
//
//		timeLeftText = "" + minutes;
//		timeLeftText += ":";
//
//		if(seconds < 10) timeLeftText += "0";
//		timeLeftText += "" + seconds;
//
//		mCountdownText.setText(timeLeftText);
//	}
}
