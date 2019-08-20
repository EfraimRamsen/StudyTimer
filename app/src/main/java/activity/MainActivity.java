package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studytimer.R;

import model.Timer;

public class MainActivity extends AppCompatActivity {

	private TextView mCountdownText;
	private ImageView mImageView;
	private Button mStartStopButton, mResetButton, mSkipButton, mCameraButton;
	private Timer mTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCountdownText = findViewById(R.id.text_countdown);
		mStartStopButton = findViewById(R.id.button_start);
		mResetButton = findViewById(R.id.button_reset);
		mSkipButton = findViewById(R.id.button_skip);
		mCameraButton = findViewById(R.id.button_camera);
		mImageView = findViewById(R.id.photo_view);

		//TODO Glöm inte fixa onSaveInstancestate etc.
		mTimer = new Timer(this);

		mStartStopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimer.pressStartStopButton();
//				if(mTimer.isTimerRunning()){
//					mTimer.pauseTimer();
//				}
//				else{
//					mTimer.startTimer();
//				}
			}
		});

		mResetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimer.pressResetButton();
//				if(mTimer.isTimerRunning()){
//					mTimer.pauseTimer();
//					mTimer.resetTimer();
//					mTimer.startTimer();
//				}
//				else{
//					mTimer.pauseTimer();
//					mTimer.resetTimer();
//				}
			}
		});

		mSkipButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimer.pressSkipButton();
//				if (mTimer.isTimerRunning()) {
//					mTimer.pauseTimer();
//					mTimer.nextRound();
//					mTimer.startTimer();
//
//				}
//				else{
//					mTimer.pauseTimer();
//					mTimer.nextRound();
//				}
			}
		});

		mTimer.updateCountdownText();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_menu, menu);
		return true;
	}

	public void setCountdownText(String countdownText) {
		mCountdownText.setText(countdownText);
	}

	public void setStartStopButtonText(int buttonTextId){
		mStartStopButton.setText(buttonTextId);
	}

}
