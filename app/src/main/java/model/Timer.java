package model;

import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import activity.MainActivity;

import com.example.studytimer.R;

public class Timer {

	private static final long START_TIME_IN_MILLIS_WORK = 1500000; //25 min
	private static final long START_TIME_IN_MILLIS_BREAK = 300000; //5 min
	private static final long START_TIME_IN_MILLIS_LONG_BREAK = 1200000; // 20 min

//	private static final int TIMER_TYPE_WORK = 0;
//	private static final int TIMER_TYPE_BREAK = 1;
//	private static final int TIMER_TYPE_LONG_BREAK = 2;

	private MainActivity mMainActivity;
	private CountDownTimer mCountdownTimer;
//	private TimerEvent mActiveTimerEvent;
	private boolean mTimerRunning;
	private long mTimeLeftInMillis;

//	private int mActiveTimerType = TIMER_TYPE_WORK;
//	private int mCounter = 0;

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
		//TODO fortsätt här och i "next()"
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
		switch (mActiveTimerType){
			case TIMER_TYPE_WORK:
				mTimeLeftInMillis = START_TIME_IN_MILLIS_WORK;
				break;
			case TIMER_TYPE_BREAK:
				mTimeLeftInMillis = START_TIME_IN_MILLIS_BREAK;
				break;
			case TIMER_TYPE_LONG_BREAK:
				mTimeLeftInMillis = START_TIME_IN_MILLIS_LONG_BREAK;
				break;
			default:
				System.out.println("ERROR in resetTimer()");
		}

	}

	public void updateCountdownText(){
		int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
		int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

		String timeLeftFormatted = String.format(Locale.getDefault(),
				"%02d:%02d", minutes, seconds);

		mMainActivity.setCountdownText(timeLeftFormatted);
	}

	public void nextRound(){


//		if(mCounter == 7){
//			mCounter = 0;
//		}
//		else{
//			mCounter++;
//		}
//
//		switch(mCounter){
//			case 0:
//			case 2:
//			case 4:
//			case 6:
//				mActiveTimerType = TIMER_TYPE_WORK;
//				mTimeLeftInMillis = START_TIME_IN_MILLIS_WORK;
//				break;
//			case 1:
//			case 3:
//			case 5:
//				mActiveTimerType = TIMER_TYPE_BREAK;
//				mTimeLeftInMillis = START_TIME_IN_MILLIS_BREAK;
//				break;
//			case 7:
//				mActiveTimerType = TIMER_TYPE_LONG_BREAK;
//				mTimeLeftInMillis = TIMER_TYPE_LONG_BREAK;
//				break;
//			default:
//				System.out.println("ERROR mCounter switch");
//		}
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
		if(isTimerRunning()){
			pauseTimer();
			nextRound();
			startTimer();
		}
		else{
			pauseTimer();
			nextRound();
			updateCountdownText();
		}
	}
}
