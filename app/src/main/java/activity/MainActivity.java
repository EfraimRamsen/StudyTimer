package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studytimer.R;

import model.Timer;

public class MainActivity extends AppCompatActivity {

	private TextView mCountdownText;
	private Button mStartStopButton;
	private Button mResetButton;
	private Timer mTimer;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCountdownText = findViewById(R.id.text_countdown);
		mStartStopButton = findViewById(R.id.button_start);
		mResetButton = findViewById(R.id.button_reset);

		//TODO Glöm inte fixa onSaveInstancestate etc.
		mTimer = new Timer(this);

		mStartStopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mTimer.isTimerRunning()){
					mTimer.pauseTimer();
				}
				else{
					mTimer.startTimer();
				}
			}
		});

		mResetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mTimer.isTimerRunning()){
					mTimer.pauseTimer();
					mTimer.resetTimer();
					mTimer.startTimer();
				}
				else{
					mTimer.pauseTimer();
					mTimer.resetTimer();
				}

			}
		});

		mTimer.updateCountdownText();
	}

	public void setCountdownText(String countdownText) {
		mCountdownText.setText(countdownText);
	}

	public void setStartStopButtonText(int buttonTextId){
		mStartStopButton.setText(buttonTextId);
	}

}
