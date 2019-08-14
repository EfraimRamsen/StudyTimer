package model;

import android.os.CountDownTimer;

import java.util.Locale;

import activity.MainActivity;

public class Timer {

	private static final long START_TIME_IN_MILLIS = 1500000;
	private CountDownTimer mCountdownTimer;
	private boolean mTimerRunning;

	private long mTimeLeftInMillis = START_TIME_IN_MILLIS; //25 min

	private MainActivity mMainActivity;

	//time (millisekunder?)
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

			}
		}.start();

		mTimerRunning = true;

	}

	public void pauseTimer(){

	}

	public void resetTimer(){

	}

	public void updateCountdownText(){
		int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
		int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

		String timeLeftFormatted = String.format(Locale.getDefault(),
				"%02d:%02d", minutes, seconds);

		mMainActivity.setCountdownText(timeLeftFormatted);
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
